package com.vermolinux.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidade StockMovement - Representa uma movimentação de estoque
 * 
 * Relacionado aos requisitos:
 * - RF6: Registrar operações de estoque com auditoria
 * - RF7: Histórico de movimentações
 * - RF19: Registrar entrada de produtos
 * - RF20: Registrar saída de produtos
 * - RF21: Editar/ajustar estoque
 */
@Entity
@Table(name = "stock_movements")
public class StockMovement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false, length = 20)
    private MovementType movementType;
    
    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantity;
    
    @Column(name = "previous_quantity", precision = 10, scale = 3)
    private BigDecimal previousQuantity;
    
    @Column(name = "new_quantity", precision = 10, scale = 3)
    private BigDecimal newQuantity;
    
    @Column(columnDefinition = "TEXT")
    private String reason;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "supplier_id")
    private Long supplierId;
    
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "sale_id")
    private Long saleId;
    
    /**
     * Enum representando tipos de movimentação
     */
    public enum MovementType {
        ENTRADA,  // Entrada de mercadoria (RF19)
        SAIDA,    // Saída manual de estoque (RF20)
        AJUSTE,   // Ajuste/correção de estoque (RF21)
        VENDA     // Saída automática por venda (RF18)
    }
    
    // Complete getters
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public MovementType getMovementType() { return movementType; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getPreviousQuantity() { return previousQuantity; }
    public BigDecimal getNewQuantity() { return newQuantity; }
    public String getReason() { return reason; }
    public String getNotes() { return notes; }
    public Long getSupplierId() { return supplierId; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getCreatedBy() { return createdBy; }
    public Long getSaleId() { return saleId; }
    
    // Complete setters
    public void setId(Long id) { this.id = id; }
    public void setProductId(Long productId) { this.productId = productId; }
    public void setMovementType(MovementType movementType) { this.movementType = movementType; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public void setPreviousQuantity(BigDecimal previousQuantity) { this.previousQuantity = previousQuantity; }
    public void setNewQuantity(BigDecimal newQuantity) { this.newQuantity = newQuantity; }
    public void setReason(String reason) { this.reason = reason; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    public void setSaleId(Long saleId) { this.saleId = saleId; }
    
    // Builder
    public static StockMovementBuilder builder() { return new StockMovementBuilder(); }
    public static class StockMovementBuilder {
        private StockMovement i = new StockMovement();
        
        public StockMovementBuilder id(Long id) { i.id = id; return this; }
        public StockMovementBuilder productId(Long productId) { i.productId = productId; return this; }
        public StockMovementBuilder movementType(MovementType movementType) { i.movementType = movementType; return this; }
        public StockMovementBuilder quantity(BigDecimal quantity) { i.quantity = quantity; return this; }
        public StockMovementBuilder previousQuantity(BigDecimal previousQuantity) { i.previousQuantity = previousQuantity; return this; }
        public StockMovementBuilder newQuantity(BigDecimal newQuantity) { i.newQuantity = newQuantity; return this; }
        public StockMovementBuilder reason(String reason) { i.reason = reason; return this; }
        public StockMovementBuilder notes(String notes) { i.notes = notes; return this; }
        public StockMovementBuilder supplierId(Long supplierId) { i.supplierId = supplierId; return this; }
        public StockMovementBuilder expiryDate(LocalDate expiryDate) { i.expiryDate = expiryDate; return this; }
        public StockMovementBuilder createdAt(LocalDateTime createdAt) { i.createdAt = createdAt; return this; }
        public StockMovementBuilder createdBy(Long createdBy) { i.createdBy = createdBy; return this; }
        public StockMovementBuilder saleId(Long saleId) { i.saleId = saleId; return this; }
        public StockMovementBuilder userId(Long userId) { i.createdBy = userId; return this; }
        
        public StockMovement build() { return i; }
    }
}


