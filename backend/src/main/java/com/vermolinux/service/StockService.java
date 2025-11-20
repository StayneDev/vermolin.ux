package com.vermolinux.service;

import com.vermolinux.dto.StockMovementRequest;
import com.vermolinux.dto.StockMovementResponse;
import com.vermolinux.exception.BusinessException;
import com.vermolinux.exception.ResourceNotFoundException;
import com.vermolinux.model.Product;
import com.vermolinux.model.StockMovement;
import com.vermolinux.model.User;
import com.vermolinux.repository.ProductRepository;
import com.vermolinux.repository.StockMovementRepository;
import com.vermolinux.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de Estoque
 * 
 * Implementa:
 * - RF6: Registrar rastreabilidade completa (quem e quando)
 * - RF8: Validar quantidade disponível em estoque
 * - RF19: Registrar entrada de estoque
 * - RF20: Registrar saída de estoque (perdas, vencimento)
 * - RF21: Registrar ajuste manual de estoque
 */
@Service
public class StockService {
    
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
    public StockService(StockMovementRepository stockMovementRepository, 
                        ProductRepository productRepository,
                        UserRepository userRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    
    /**
     * RF19: Registrar entrada de estoque
     * RF6: Registrar rastreabilidade completa (user, data)
     */
    @Transactional
    public StockMovementResponse registerEntry(StockMovementRequest request, Long userId) {
        System.out.println(
                "Registrando entrada de estoque - Produto: " + request.getProductId() + " - Qtd: " + request.getQuantity());
        
        // Validações
        validateUser(userId);
        Product product = validateProduct(request.getProductId());
        validateQuantity(request.getQuantity(), "entrada");
        
        BigDecimal previousQuantity = product.getStockQuantity();
        BigDecimal quantityToAdd = request.getQuantity();
        BigDecimal newQuantity = previousQuantity.add(quantityToAdd);
        
        return recordMovement(product, request, StockMovement.MovementType.ENTRADA,
                quantityToAdd, previousQuantity, newQuantity, userId);
    }
    
    /**
     * RF20: Registrar saída de estoque (perdas, vencimento, etc.)
     * RF6: Registrar rastreabilidade completa
     * RF8: Validar quantidade disponível
     */
    @Transactional
    public StockMovementResponse registerExit(StockMovementRequest request, Long userId) {
        System.out.println(
                "Registrando saída de estoque - Produto: " + request.getProductId() + " - Qtd: " + request.getQuantity());
        
        // Validações
        validateUser(userId);
        Product product = validateProduct(request.getProductId());
        validateQuantity(request.getQuantity(), "saída");
        
        // RF8: Validar se há estoque suficiente
        if (product.getStockQuantity().compareTo(request.getQuantity()) < 0) {
            throw new BusinessException(String.format(
                    "Estoque insuficiente. Disponível: %s %s, Solicitado: %s %s",
                    product.getStockQuantity(), product.getUnit(),
                    request.getQuantity(), product.getUnit()
            ));
        }
        
        BigDecimal previousQuantity = product.getStockQuantity();
        BigDecimal quantityToSubtract = request.getQuantity();
        BigDecimal newQuantity = previousQuantity.subtract(quantityToSubtract);
        
        return recordMovement(product, request, StockMovement.MovementType.SAIDA,
                quantityToSubtract, previousQuantity, newQuantity, userId);
    }
    
    /**
     * RF21: Registrar ajuste manual de estoque
     * RF6: Registrar rastreabilidade completa
     */
    public StockMovementResponse registerAdjustment(StockMovementRequest request, Long userId) {
        System.out.println(
                "Registrando ajuste de estoque - Produto: " + request.getProductId() + " - Nova Qtd: " + request.getQuantity());
        
        // Validações
        validateUser(userId);
        Product product = validateProduct(request.getProductId());
        
        if (request.getQuantity() == null) {
            throw new BusinessException("Quantidade final é obrigatória para ajuste de estoque");
        }
        
        if (request.getQuantity().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Quantidade final não pode ser negativa");
        }
        
        BigDecimal previousQuantity = product.getStockQuantity();
        BigDecimal desiredQuantity = request.getQuantity();
        BigDecimal difference = desiredQuantity.subtract(previousQuantity);
        
        return recordMovement(product, request, StockMovement.MovementType.AJUSTE,
                difference.abs(), previousQuantity, desiredQuantity, userId);
    }
    
    /**
     * RF6: Buscar histórico completo de movimentações (auditoria)
     */
    public List<StockMovementResponse> findAll() {
                return stockMovementRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * RF6: Buscar histórico de movimentações de um produto específico
     */
    public List<StockMovementResponse> findByProduct(Long productId) {
                // Verificar se produto existe
        validateProduct(productId);
        
        return stockMovementRepository.findByProductIdOrderByCreatedAtDesc(productId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * RF6: Buscar movimentações por usuário (auditoria)
     */
    public List<StockMovementResponse> findByUser(Long userId) {
                // Verificar se usuário existe
        validateUser(userId);
        
        return stockMovementRepository.findByCreatedByOrderByCreatedAtDesc(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * RF8: Verificar se há estoque suficiente de um produto
     */
    public boolean hasStock(Long productId, BigDecimal quantity) {
        Product product = validateProduct(productId);
        return product.getStockQuantity().compareTo(quantity) >= 0;
    }
    
    /**
     * Validações auxiliares
     */
    
    private void validateUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", userId));
    }
    
    private Product validateProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "id", productId));
    }
    
    private void validateQuantity(BigDecimal quantity, String operationType) {
        if (quantity == null) {
            throw new BusinessException("Quantidade é obrigatória");
        }
        
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(String.format(
                    "Quantidade de %s deve ser maior que zero", operationType
            ));
        }
    }
    
    private StockMovementResponse mapToResponse(StockMovement movement) {
        String productName = productRepository.findById(movement.getProductId())
                .map(Product::getName)
                .orElse(null);
        
        String createdByName = userRepository.findById(movement.getCreatedBy())
                .map(User::getFullName)
                .orElse(null);
        
        return StockMovementResponse.builder()
                .id(movement.getId())
                .productId(movement.getProductId())
                .productName(productName)
                .movementType(movement.getMovementType())
                .quantity(movement.getQuantity())
                .previousQuantity(movement.getPreviousQuantity())
                .newQuantity(movement.getNewQuantity())
                .reason(movement.getReason())
                .notes(movement.getNotes())
                .saleId(movement.getSaleId())
                .createdBy(movement.getCreatedBy())
                .createdByName(createdByName)
                .createdAt(movement.getCreatedAt())
                .build();
    }

    /**
     * Persiste a movimentação e mantém consistência das quantidades em um único ponto.
     */
    private StockMovementResponse recordMovement(
            Product product,
            StockMovementRequest request,
            StockMovement.MovementType movementType,
            BigDecimal movementQuantity,
            BigDecimal previousQuantity,
            BigDecimal resultingQuantity,
            Long userId) {
        StockMovement movement = StockMovement.builder()
                .productId(product.getId())
                .movementType(movementType)
                .quantity(movementQuantity)
                .previousQuantity(previousQuantity)
                .newQuantity(resultingQuantity)
                .reason(request.getReason())
                .notes(request.getNotes())
                .supplierId(request.getSupplierId())
                .expiryDate(request.getExpiryDate())
                .createdBy(userId)
                .build();
        
        movement = stockMovementRepository.save(movement);
        
        product.setStockQuantity(resultingQuantity);
        product.setUpdatedBy(userId);
        productRepository.save(product);
        
        String unitLabel = product.getUnit() != null ? product.getUnit().name() : "UN";
        System.out.println(String.format(
                "%s registrada: %s - %s %s -> %s %s",
                movementType.name(),
                product.getName(),
                previousQuantity,
                unitLabel,
                resultingQuantity,
                unitLabel));
        
        return mapToResponse(movement);
    }
}






