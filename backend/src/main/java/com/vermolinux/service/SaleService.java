package com.vermolinux.service;

import com.vermolinux.dto.*;
import com.vermolinux.exception.BusinessException;
import com.vermolinux.exception.ResourceNotFoundException;
import com.vermolinux.model.*;
import com.vermolinux.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SaleService - Gerenciador de Transações de Vendas (PDV - Point of Sale)
 * 
 * RESPONSABILIDADES:
 * - Criar e gerenciar transações de venda (RF11)
 * - Adicionar/remover itens do carrinho (RF12, RF13)
 * - Validar estoque antes de venda (RF8)
 * - Registrar pesagem de produtos (RF14)
 * - Processar pagamento e calcular troco (RF16, RF17)
 * - Finalizar venda e atualizar estoque (RF18)
 * - Cancelar vendas abertas (RF15)
 * 
 * PADRÃO TRANSACIONAL:
 * - Toda venda começa com status OPEN
 * - Cliente pode adicionar/remover itens
 * - Ao finalizar: valida, calcula, atualiza BD, registra movimentação de estoque
 * - Uma vez PAID, não pode ser modificada
 * - Pode ser CANCELLED se ainda estiver OPEN
 * 
 * FLUXO DE VENDA COMPLETO:
 * 1. openSale() → Sale status=OPEN
 * 2. addItem() → SaleItem adicionado, estoque validado
 * 3. addItem() → Múltiplos itens podem ser adicionados
 * 4. (opcional) removeItem() → Remove item do carrinho
 * 5. finalizeSale() → Processa pagamento, atualiza estoque
 *    - Decrementa quantidade no Product
 *    - Cria StockMovement (tipo=VENDA) para rastreabilidade
 *    - Sale status=PAID, timestamp de pagamento
 * 
 * VALIDAÇÕES CRÍTICAS:
 * - Estoque não pode ficar negativo
 * - Forma de pagamento válida
 * - Valor pago >= total (calcula troco automaticamente)
 * - Venda só pode ser finalizada uma vez
 */
@Service
public class SaleService {
    
    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StockMovementRepository stockMovementRepository;
    
    // Dependency Injection via Constructor (mais testável que @Autowired em campos)
    public SaleService(SaleRepository saleRepository,
                      SaleItemRepository saleItemRepository,
                      ProductRepository productRepository,
                      UserRepository userRepository,
                      StockMovementRepository stockMovementRepository) {
        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.stockMovementRepository = stockMovementRepository;
    }
    
    /**
     * RF11: Abrir nova transação de venda
     */
    @Transactional
    public SaleResponse createSale(Long cashierId) {
        // Validar se caixa existe (segurança: previne ID falso)
        User cashier = userRepository.findById(cashierId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", cashierId));
        
        // Criar nova venda com status OPEN
        Sale sale = Sale.builder()
                .status(Sale.SaleStatus.OPEN)        // Pronta para receber itens
                .cashierId(cashierId)                // Rastreabilidade: quem fez a venda
                .items(new ArrayList<>())            // Carrinho vazio
                .build();
        
        sale = saleRepository.save(sale);  // Persiste no banco (gera ID)
        
        System.out.println("Venda aberta: ID " + sale.getId() + " para caixa " + cashier.getUsername());
        
        return mapToResponse(sale);
    }
    
    /**
     * RF12: Adicionar produto à venda (no carrinho)
     * RF14: Registrar pesagem de produto quando aplicável
     * RF8: Validar estoque disponível
     * 
     * Fluxo:
     * 1. Busca venda e valida se está OPEN
     * 2. Busca produto e valida se está ativo
     * 3. Verifica estoque (RF8)
     * 4. Cria item da venda com quantidade e preço atual
     * 5. Se produto requer pesagem (RF14), marca weighed=true
     * 6. Calcula subtotal (quantidade × preço)
     * 7. Persiste e recalcula total da venda
     * 
     * Validações críticas:
     * - Venda deve estar em status OPEN (não pode adicionar a PAID/CANCELLED)
     * - Produto deve estar ativo
     * - Estoque deve ser suficiente (previne sobrevenda)
     * - Quantidade deve ser positiva
     * 
     * @param saleId ID da venda (deve estar OPEN)
     * @param request com productId e quantity
     * @return SaleResponse atualizada com novo item
     * @throws ResourceNotFoundException se venda ou produto não existe
     * @throws BusinessException se estoque insuficiente ou venda não está OPEN
     */
    @Transactional
    public SaleResponse addItem(Long saleId, AddSaleItemRequest request) {
        System.out.println("Adicionando item à venda " + saleId + ": Produto " + request.getProductId() + " x"
            + request.getQuantity());
        
        // 1. Buscar e validar venda
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Venda", "id", saleId));
        
        // Verificar se venda está aberta (regra de negócio)
        if (sale.getStatus() != Sale.SaleStatus.OPEN) {
            throw new BusinessException("Não é possível adicionar itens a uma venda " + sale.getStatus()
                    + ". Apenas vendas OPEN podem receber itens.");
        }
        
        // 2. Buscar produto
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "id", request.getProductId()));
        
        // Validar produto ativo
        if (product.getActive() == null || !product.getActive()) {
            throw new BusinessException("Produto " + product.getName() + " não está disponível para venda");
        }
        
        // 3. RF8: Validar quantidade em estoque (previne sobrevenda)
        if (product.getStockQuantity().compareTo(request.getQuantity()) < 0) {
            throw new BusinessException(String.format(
                    "Estoque insuficiente. Produto: %s. Disponível: %s %s, Solicitado: %s %s",
                    product.getName(),
                    product.getStockQuantity(),
                    product.getUnit(),
                    request.getQuantity(),
                    product.getUnit()
            ));
        }
        
        // 4. Criar item da venda
        SaleItem item = SaleItem.builder()
                .saleId(sale.getId())
                .productId(product.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())        // Preço no momento da venda (pode variar depois)
                .quantity(request.getQuantity())
                .unit(product.getUnit().name())
                .weighed(request.getWeighed() != null && request.getWeighed())  // RF14: marca pesagem
                .build();
        
        // Calcular subtotal (quantidade × preço unitário)
        item.calculateSubtotal();
        
        // Adicionar ao carrinho da venda
        sale.addItem(item);
        
        // Persistir e recarregar para recalcular totais
        sale = saveSaleWithItems(sale);
        
        System.out.println("✓ Item adicionado: " + product.getName() + " x" + request.getQuantity() 
                + " = R$ " + item.getSubtotal());
        
        return mapToResponse(sale);
    }
    
    /**
     * RF13: Remover produto da venda (antes da finalização)
     */
    @Transactional
    public SaleResponse removeItem(Long saleId, Long itemId) {
                Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Venda", "id", saleId));
        
        if (sale.getStatus() != Sale.SaleStatus.OPEN) {
            throw new BusinessException("Não é possível remover itens de uma venda " + sale.getStatus());
        }
        
        loadSaleItems(sale);
        
        SaleItem itemToRemove = sale.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        
        sale.removeItem(itemToRemove);
        saleItemRepository.delete(itemToRemove);
        sale = saleRepository.save(sale);
        
                return mapToResponse(sale);
    }
    
    /**
     * RF15: Cancelar venda (antes da finalização)
     * RF6: Registra quem cancelou e quando
     */
    @Transactional
    public void cancelSale(Long saleId, Long cancelledBy, String reason) {
                Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Venda", "id", saleId));
        
        if (sale.getStatus() != Sale.SaleStatus.OPEN) {
            throw new BusinessException("Apenas vendas abertas podem ser canceladas");
        }
        
        sale.setStatus(Sale.SaleStatus.CANCELLED);
        sale.setCancelledAt(LocalDateTime.now());
        sale.setCancelledBy(cancelledBy);
        sale.setCancellationReason(reason);
        
        saleRepository.save(sale);
        
                // RF6: Registrar operação de cancelamento (auditoria)
        // TODO: Log de auditoria quando integrar com banco
    }
    
    /**
     * RF16: Registrar forma de pagamento
     * RF17: Calcular troco automaticamente
     * RF18: Registrar venda no sistema e atualizar estoque
     * RF6: Auditoria - registra quem finalizou e quando
     */
    @Transactional
    public SaleResponse finalizeSale(Long saleId, PaymentRequest request) {
        System.out.println("Finalizando venda " + saleId + " - Pagamento: " + request.getPaymentMethod());
        
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Venda", "id", saleId));
        
        if (sale.getStatus() != Sale.SaleStatus.OPEN) {
            throw new BusinessException("Apenas vendas abertas podem ser finalizadas");
        }
        
        loadSaleItems(sale);
        
        if (sale.getItems().isEmpty()) {
            throw new BusinessException("Não é possível finalizar uma venda sem itens");
        }
        
        // Validar valor pago
        if (request.getAmountPaid().compareTo(sale.getTotalAmount()) < 0) {
            throw new BusinessException("Valor pago é menor que o total da venda");
        }
        
        // RF17: Calcular troco automaticamente (apenas para DINHEIRO)
        BigDecimal change = BigDecimal.ZERO;
        if (request.getPaymentMethod() == Sale.PaymentMethod.DINHEIRO) {
            change = request.getAmountPaid().subtract(sale.getTotalAmount());
        }
        
        // RF16: Registrar forma de pagamento
        sale.setPaymentMethod(request.getPaymentMethod());
        sale.setAmountPaid(request.getAmountPaid());
        sale.setChangeAmount(change);
        sale.setPaidAt(LocalDateTime.now());
        sale.setStatus(Sale.SaleStatus.PAID);
        
        // RF18: Atualizar estoque automaticamente
        for (SaleItem item : sale.getItems()) {
            updateStock(item, sale.getCashierId(), sale.getId());
        }
        
        sale = saleRepository.save(sale);
        
        System.out.println("Venda " + saleId + " finalizada - Total: R$ " + sale.getTotalAmount() + " - Troco: R$ "
            + change);
        
        // RF6: Registrar operação de venda (auditoria)
        // RF7: Histórico de vendas (já está sendo salvo)
        
        return mapToResponse(sale);
    }
    
    /**
     * RF18: Atualiza estoque após venda
     * RF6: Registra movimentação de estoque para auditoria
     */
    private void updateStock(SaleItem item, Long userId, Long saleId) {
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "id", item.getProductId()));
        
        BigDecimal previousQuantity = product.getStockQuantity();
        BigDecimal newQuantity = previousQuantity.subtract(item.getQuantity());
        
        // Atualizar estoque do produto
        product.setStockQuantity(newQuantity);
        productRepository.save(product);
        
        // Registrar movimentação de estoque (RF6 - auditoria)
        StockMovement movement = StockMovement.builder()
                .productId(product.getId())
                .movementType(StockMovement.MovementType.VENDA)
                .quantity(item.getQuantity())
                .previousQuantity(previousQuantity)
                .newQuantity(newQuantity)
                .reason("Venda #" + saleId)
                .userId(userId)
                .saleId(saleId)
                .build();
        
        stockMovementRepository.save(movement);
        
        System.out.println(
            "Estoque atualizado: " + product.getName() + " - " + previousQuantity + " " + product.getUnit()
                + " -> " + newQuantity + " " + product.getUnit());
    }
    
    /**
     * Buscar venda por ID
     */
    public SaleResponse findById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda", "id", id));
        
        return mapToResponse(sale);
    }
    
    /**
     * RF7: Histórico de vendas
     */
    public List<SaleResponse> findAll() {
        return saleRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public List<SaleResponse> findByCashier(Long cashierId) {
        return saleRepository.findByCashierId(cashierId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Carrega os itens de uma venda do banco de dados
     */
    private void loadSaleItems(Sale sale) {
        if (sale != null && sale.getId() != null) {
            List<SaleItem> items = saleItemRepository.findBySaleId(sale.getId());
            sale.setItems(items);
        }
    }
    
    /**
     * Salva uma venda e seus itens no banco de dados
     */
    private Sale saveSaleWithItems(Sale sale) {
        // Primeiro salva a venda
        Sale savedSale = saleRepository.save(sale);
        
        // Depois salva os itens
        if (savedSale.getItems() != null && !savedSale.getItems().isEmpty()) {
            for (SaleItem item : savedSale.getItems()) {
                item.setSaleId(savedSale.getId());
                saleItemRepository.save(item);
            }
        }
        
        return savedSale;
    }
    
    private SaleResponse mapToResponse(Sale sale) {
        String cashierName = userRepository.findById(sale.getCashierId())
                .map(User::getFullName)
                .orElse(null);
        
        String cancelledByName = null;
        if (sale.getCancelledBy() != null) {
            cancelledByName = userRepository.findById(sale.getCancelledBy())
                    .map(User::getFullName)
                    .orElse(null);
        }
        
        loadSaleItems(sale);
        
        List<SaleItemResponse> items = sale.getItems().stream()
                .map(item -> SaleItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .productPrice(item.getProductPrice())
                        .quantity(item.getQuantity())
                        .unit(item.getUnit())
                        .subtotal(item.getSubtotal())
                        .weighed(item.getWeighed())
                        .build())
                .collect(Collectors.toList());
        
        return SaleResponse.builder()
                .id(sale.getId())
                .status(sale.getStatus())
                .totalAmount(sale.getTotalAmount())
                .paymentMethod(sale.getPaymentMethod())
                .amountPaid(sale.getAmountPaid())
                .changeAmount(sale.getChangeAmount())
                .createdAt(sale.getCreatedAt())
                .paidAt(sale.getPaidAt())
                .cancelledAt(sale.getCancelledAt())
                .cashierId(sale.getCashierId())
                .cashierName(cashierName)
                .cancelledBy(sale.getCancelledBy())
                .cancelledByName(cancelledByName)
                .cancellationReason(sale.getCancellationReason())
                .items(items)
                .build();
    }
}






