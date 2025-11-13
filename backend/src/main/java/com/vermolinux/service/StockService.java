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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
@Slf4j
public class StockService {
    
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
    /**
     * RF19: Registrar entrada de estoque
     * RF6: Registrar rastreabilidade completa
     */
    public StockMovementResponse registerEntry(StockMovementRequest request, Long userId) {
        log.info("Registrando entrada de estoque - Produto: {} - Qtd: {}", 
                request.getProductId(), request.getQuantity());
        
        // Validações
        validateUser(userId);
        Product product = validateProduct(request.getProductId());
        validateQuantity(request.getQuantity(), "entrada");
        
        // Calcular nova quantidade
        BigDecimal previousQuantity = product.getStockQuantity();
        BigDecimal newQuantity = previousQuantity.add(request.getQuantity());
        
        // Criar movimentação
        StockMovement movement = StockMovement.builder()
                .productId(product.getId())
                .movementType(StockMovement.MovementType.ENTRADA)
                .quantity(request.getQuantity())
                .previousQuantity(previousQuantity)
                .newQuantity(newQuantity)
                .reason(request.getReason())
                .notes(request.getNotes())
                .createdBy(userId)
                .build();
        
        movement = stockMovementRepository.save(movement);
        
        // Atualizar estoque do produto
        product.setStockQuantity(newQuantity);
        product.setUpdatedBy(userId);
        productRepository.save(product);
        
        log.info("Entrada registrada: {} - {} {} -> {} {}", 
                product.getName(), previousQuantity, product.getUnit(), newQuantity, product.getUnit());
        
        return mapToResponse(movement);
    }
    
    /**
     * RF20: Registrar saída de estoque (perdas, vencimento, etc.)
     * RF6: Registrar rastreabilidade completa
     * RF8: Validar quantidade disponível
     */
    public StockMovementResponse registerExit(StockMovementRequest request, Long userId) {
        log.info("Registrando saída de estoque - Produto: {} - Qtd: {}", 
                request.getProductId(), request.getQuantity());
        
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
        
        // Calcular nova quantidade
        BigDecimal previousQuantity = product.getStockQuantity();
        BigDecimal newQuantity = previousQuantity.subtract(request.getQuantity());
        
        // Criar movimentação
        StockMovement movement = StockMovement.builder()
                .productId(product.getId())
                .movementType(StockMovement.MovementType.SAIDA)
                .quantity(request.getQuantity())
                .previousQuantity(previousQuantity)
                .newQuantity(newQuantity)
                .reason(request.getReason())
                .notes(request.getNotes())
                .createdBy(userId)
                .build();
        
        movement = stockMovementRepository.save(movement);
        
        // Atualizar estoque do produto
        product.setStockQuantity(newQuantity);
        product.setUpdatedBy(userId);
        productRepository.save(product);
        
        log.info("Saída registrada: {} - {} {} -> {} {}", 
                product.getName(), previousQuantity, product.getUnit(), newQuantity, product.getUnit());
        
        return mapToResponse(movement);
    }
    
    /**
     * RF21: Registrar ajuste manual de estoque
     * RF6: Registrar rastreabilidade completa
     */
    public StockMovementResponse registerAdjustment(StockMovementRequest request, Long userId) {
        log.info("Registrando ajuste de estoque - Produto: {} - Nova Qtd: {}", 
                request.getProductId(), request.getQuantity());
        
        // Validações
        validateUser(userId);
        Product product = validateProduct(request.getProductId());
        
        if (request.getQuantity() == null) {
            throw new BusinessException("Quantidade final é obrigatória para ajuste de estoque");
        }
        
        if (request.getQuantity().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Quantidade final não pode ser negativa");
        }
        
        // Calcular diferença
        BigDecimal previousQuantity = product.getStockQuantity();
        BigDecimal difference = request.getQuantity().subtract(previousQuantity);
        
        // Criar movimentação
        StockMovement movement = StockMovement.builder()
                .productId(product.getId())
                .movementType(StockMovement.MovementType.AJUSTE)
                .quantity(difference.abs())
                .previousQuantity(previousQuantity)
                .newQuantity(request.getQuantity())
                .reason(request.getReason())
                .notes(request.getNotes())
                .createdBy(userId)
                .build();
        
        movement = stockMovementRepository.save(movement);
        
        // Atualizar estoque do produto
        product.setStockQuantity(request.getQuantity());
        product.setUpdatedBy(userId);
        productRepository.save(product);
        
        log.info("Ajuste registrado: {} - {} {} -> {} {} (diferença: {})", 
                product.getName(), 
                previousQuantity, product.getUnit(), 
                request.getQuantity(), product.getUnit(),
                difference);
        
        return mapToResponse(movement);
    }
    
    /**
     * RF6: Buscar histórico completo de movimentações (auditoria)
     */
    public List<StockMovementResponse> findAll() {
        log.info("Buscando todas as movimentações de estoque");
        
        return stockMovementRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * RF6: Buscar histórico de movimentações de um produto específico
     */
    public List<StockMovementResponse> findByProduct(Long productId) {
        log.info("Buscando movimentações do produto ID: {}", productId);
        
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
        log.info("Buscando movimentações do usuário ID: {}", userId);
        
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
}
