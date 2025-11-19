package com.vermolinux.service;

import com.vermolinux.dto.*;
import com.vermolinux.exception.BusinessException;
import com.vermolinux.exception.ResourceNotFoundException;
import com.vermolinux.model.*;
import com.vermolinux.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de Vendas
 * 
 * Implementa:
 * - RF11: Abrir transação de venda
 * - RF12: Adicionar produtos à venda
 * - RF13: Remover produtos da venda
 * - RF14: Pesar produto
 * - RF15: Cancelar venda
 * - RF16: Registrar forma de pagamento
 * - RF17: Calcular troco automaticamente
 * - RF18: Registrar venda e atualizar estoque
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SaleService {
    
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StockMovementRepository stockMovementRepository;
    
    /**
     * RF11: Abrir nova transação de venda
     * RF6: Auditoria - registra data, hora e usuário (caixa)
     */
    @Transactional
    public SaleResponse createSale(Long cashierId) {
        log.info("Abrindo nova venda para caixa ID: {}", cashierId);
        
        // Verificar se caixa existe
        User cashier = userRepository.findById(cashierId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", cashierId));
        
        Sale sale = Sale.builder()
                .status(Sale.SaleStatus.OPEN)
                .cashierId(cashierId)
                .items(new ArrayList<>())
                .build();
        
        sale = saleRepository.save(sale);
        
        log.info("Venda aberta: ID {}", sale.getId());
        
        return mapToResponse(sale);
    }
    
    /**
     * RF12: Adicionar produto à venda
     * RF14: Pesar produto quando aplicável
     * RF8: Validar quantidade em estoque
     */
    @Transactional
    public SaleResponse addItem(Long saleId, AddSaleItemRequest request) {
        log.info("Adicionando item à venda {}: Produto {} x{}", saleId, request.getProductId(), request.getQuantity());
        
        // Buscar venda
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Venda", "id", saleId));
        
        // Verificar se venda está aberta
        if (sale.getStatus() != Sale.SaleStatus.OPEN) {
            throw new BusinessException("Não é possível adicionar itens a uma venda " + sale.getStatus());
        }
        
        // Buscar produto
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "id", request.getProductId()));
        
        // RF8: Validar quantidade em estoque
        if (product.getStockQuantity().compareTo(request.getQuantity()) < 0) {
            throw new BusinessException(String.format(
                    "Estoque insuficiente. Disponível: %s %s",
                    product.getStockQuantity(),
                    product.getUnit()
            ));
        }
        
        // Criar item da venda
        SaleItem item = SaleItem.builder()
                .saleId(sale.getId())
                .productId(product.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .quantity(request.getQuantity())
                .unit(product.getUnit().name())
                .weighed(request.getWeighed() != null && request.getWeighed())
                .build();
        
        item.calculateSubtotal();
        sale.addItem(item);
        
        sale = saleRepository.save(sale);
        
        log.info("Item adicionado: {} x{} = R$ {}", product.getName(), request.getQuantity(), item.getSubtotal());
        
        return mapToResponse(sale);
    }
    
    /**
     * RF13: Remover produto da venda (antes da finalização)
     */
    @Transactional
    public SaleResponse removeItem(Long saleId, Long itemId) {
        log.info("Removendo item {} da venda {}", itemId, saleId);
        
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Venda", "id", saleId));
        
        if (sale.getStatus() != Sale.SaleStatus.OPEN) {
            throw new BusinessException("Não é possível remover itens de uma venda " + sale.getStatus());
        }
        
        SaleItem itemToRemove = sale.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        
        sale.removeItem(itemToRemove);
        sale = saleRepository.save(sale);
        
        log.info("Item removido da venda {}", saleId);
        
        return mapToResponse(sale);
    }
    
    /**
     * RF15: Cancelar venda (antes da finalização)
     * RF6: Registra quem cancelou e quando
     */
    @Transactional
    public void cancelSale(Long saleId, Long cancelledBy, String reason) {
        log.info("Cancelando venda {} por usuário {}", saleId, cancelledBy);
        
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
        
        log.info("Venda {} cancelada", saleId);
        
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
        log.info("Finalizando venda {} - Pagamento: {}", saleId, request.getPaymentMethod());
        
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Venda", "id", saleId));
        
        if (sale.getStatus() != Sale.SaleStatus.OPEN) {
            throw new BusinessException("Apenas vendas abertas podem ser finalizadas");
        }
        
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
        
        log.info("Venda {} finalizada - Total: R$ {} - Troco: R$ {}", 
                saleId, sale.getTotalAmount(), change);
        
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
                .reason(StockMovement.MovementReason.VENDA)
                .notes("Venda #" + saleId)
                .createdBy(userId)
                .saleId(saleId)
                .build();
        
        stockMovementRepository.save(movement);
        
        log.debug("Estoque atualizado: {} - {} {} -> {} {}", 
                product.getName(), previousQuantity, product.getUnit(), newQuantity, product.getUnit());
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
