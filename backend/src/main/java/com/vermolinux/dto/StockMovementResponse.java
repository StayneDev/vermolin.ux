package com.vermolinux.dto;

import com.vermolinux.model.StockMovement;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para resposta de movimentação de estoque (RF7)
 */
public class StockMovementResponse {
    
    private Long id;
    private Long productId;
    private String productName;
    private StockMovement.MovementType movementType;
    private BigDecimal quantity;
    private BigDecimal previousQuantity;
    private BigDecimal newQuantity;
    private String reason;
    private String notes;
    private Long saleId;
    private Long createdBy;
    private String createdByName;
    private LocalDateTime createdAt;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public StockMovement.MovementType getMovementType() { return movementType; }
    public void setMovementType(StockMovement.MovementType movementType) { this.movementType = movementType; }
    
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    
    public BigDecimal getPreviousQuantity() { return previousQuantity; }
    public void setPreviousQuantity(BigDecimal previousQuantity) { this.previousQuantity = previousQuantity; }
    
    public BigDecimal getNewQuantity() { return newQuantity; }
    public void setNewQuantity(BigDecimal newQuantity) { this.newQuantity = newQuantity; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public Long getSaleId() { return saleId; }
    public void setSaleId(Long saleId) { this.saleId = saleId; }
    
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    
    public String getCreatedByName() { return createdByName; }
    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public static StockMovementResponseBuilder builder() { return new StockMovementResponseBuilder(); }
    public static class StockMovementResponseBuilder {
        private StockMovementResponse i = new StockMovementResponse();
        public StockMovementResponseBuilder id(Long id) { i.setId(id); return this; }
        public StockMovementResponseBuilder productId(Long productId) { i.setProductId(productId); return this; }
        public StockMovementResponseBuilder productName(String productName) { i.setProductName(productName); return this; }
        public StockMovementResponseBuilder movementType(StockMovement.MovementType movementType) { i.setMovementType(movementType); return this; }
        public StockMovementResponseBuilder quantity(BigDecimal quantity) { i.setQuantity(quantity); return this; }
        public StockMovementResponseBuilder previousQuantity(BigDecimal previousQuantity) { i.setPreviousQuantity(previousQuantity); return this; }
        public StockMovementResponseBuilder newQuantity(BigDecimal newQuantity) { i.setNewQuantity(newQuantity); return this; }
        public StockMovementResponseBuilder reason(String reason) { i.setReason(reason); return this; }
        public StockMovementResponseBuilder notes(String notes) { i.setNotes(notes); return this; }
        public StockMovementResponseBuilder saleId(Long saleId) { i.setSaleId(saleId); return this; }
        public StockMovementResponseBuilder createdBy(Long createdBy) { i.setCreatedBy(createdBy); return this; }
        public StockMovementResponseBuilder createdByName(String createdByName) { i.setCreatedByName(createdByName); return this; }
        public StockMovementResponseBuilder createdAt(LocalDateTime createdAt) { i.setCreatedAt(createdAt); return this; }
        public StockMovementResponse build() { return i; }
    }
}


