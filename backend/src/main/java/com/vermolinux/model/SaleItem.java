package com.vermolinux.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade SaleItem - Representa um item dentro de uma venda
 */
@Entity
@Table(name = "sale_items")
public class SaleItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "sale_id", nullable = false)
    private Long saleId;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;
    
    @Column(name = "product_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal productPrice;
    
    @Column(name = "quantity", nullable = false, precision = 10, scale = 3)
    private BigDecimal quantity;
    
    @Column(name = "unit", nullable = false, length = 10)
    private String unit;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
    
    @Column(nullable = false)
    private Boolean weighed = false;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Explicit getters for compilation compatibility
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getSaleId() {
        return saleId;
    }
    
    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public BigDecimal getProductPrice() {
        return productPrice;
    }
    
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    
    public BigDecimal getQuantity() {
        return quantity;
    }
    
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
    
    public Boolean getWeighed() {
        return weighed;
    }
    
    public void setWeighed(Boolean weighed) {
        this.weighed = weighed;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Calcula o subtotal do item (quantidade * preço)
     */
    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {
        if (productPrice != null && quantity != null) {
            subtotal = productPrice.multiply(quantity);
        }
    }
    
    // Builder manual para compatibilidade
    public static SaleItemBuilder builder() { return new SaleItemBuilder(); }
    public static class SaleItemBuilder {
        private SaleItem i = new SaleItem();
        
        public SaleItemBuilder id(Long id) { i.id = id; return this; }
        public SaleItemBuilder saleId(Long saleId) { i.saleId = saleId; return this; }
        public SaleItemBuilder productId(Long productId) { i.setProductId(productId); return this; }
        public SaleItemBuilder productName(String productName) { i.setProductName(productName); return this; }
        public SaleItemBuilder productPrice(BigDecimal productPrice) { i.setProductPrice(productPrice); return this; }
        public SaleItemBuilder quantity(BigDecimal quantity) { i.quantity = quantity; return this; }
        public SaleItemBuilder unit(String unit) { i.unit = unit; return this; }
        public SaleItemBuilder subtotal(BigDecimal subtotal) { i.subtotal = subtotal; return this; }
        public SaleItemBuilder weighed(Boolean weighed) { i.weighed = weighed; return this; }
        public SaleItemBuilder createdAt(LocalDateTime createdAt) { i.createdAt = createdAt; return this; }
        
        public SaleItem build() { return i; }
    }
}


