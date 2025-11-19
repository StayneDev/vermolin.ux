package com.vermolinux.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidade Product - Representa um produto do hortifruti
 * 
 * Relacionado aos requisitos:
 * - RF9: Caixa consulta produtos (sem fornecedor/validade)
 * - RF10: Estoquista/Gerente consulta produtos completos
 * - RF22-RF25: CRUD de produtos (Gerente)
 * - RF8: Validação de estoque em vendas
 * 
 * Entidade Product - Representa um produto do hortifruti
 */
@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String code;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ProductUnit unit;
    
    @Column(name = "stock_quantity", nullable = false, precision = 10, scale = 3)
    private BigDecimal stockQuantity = BigDecimal.ZERO;
    
    @Column(name = "min_stock", precision = 10, scale = 3)
    private BigDecimal minStock = BigDecimal.ZERO;
    
    @Column(name = "supplier_id")
    private Long supplierId;
    
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    
    @Column(name = "requires_weighing", nullable = false)
    private Boolean requiresWeighing = false;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "updated_by")
    private Long updatedBy;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public ProductUnit getUnit() {
        return unit;
    }
    
    public BigDecimal getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(BigDecimal stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public BigDecimal getMinStock() {
        return minStock;
    }
    
    public Long getSupplierId() {
        return supplierId;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public Boolean getRequiresWeighing() {
        return requiresWeighing;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Long getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public Long getUpdatedBy() {
        return updatedBy;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public void setUnit(ProductUnit unit) {
        this.unit = unit;
    }
    
    public void setMinStock(BigDecimal minStock) {
        this.minStock = minStock;
    }
    
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public void setRequiresWeighing(Boolean requiresWeighing) {
        this.requiresWeighing = requiresWeighing;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Enum representando unidades de medida possíveis
     */
    public enum ProductUnit {
        KG,
        UNIDADE,
        CAIXA,
        DUZIA
    }
    
    /**
     * Verifica se o produto está com estoque baixo (RF34)
     */
    public boolean isLowStock() {
        return stockQuantity.compareTo(minStock) <= 0;
    }
    
    // Builder manual para compatibilidade
    public static ProductBuilder builder() { return new ProductBuilder(); }
    public static class ProductBuilder {
        private Product i = new Product();
        
        public ProductBuilder id(Long id) { i.setId(id); return this; }
        public ProductBuilder code(String code) { i.setCode(code); return this; }
        public ProductBuilder name(String name) { i.setName(name); return this; }
        public ProductBuilder description(String description) { i.setDescription(description); return this; }
        public ProductBuilder price(BigDecimal price) { i.setPrice(price); return this; }
        public ProductBuilder unit(ProductUnit unit) { i.setUnit(unit); return this; }
        public ProductBuilder stockQuantity(BigDecimal stockQuantity) { i.setStockQuantity(stockQuantity); return this; }
        public ProductBuilder minStock(BigDecimal minStock) { i.setMinStock(minStock); return this; }
        public ProductBuilder supplierId(Long supplierId) { i.setSupplierId(supplierId); return this; }
        public ProductBuilder expiryDate(LocalDate expiryDate) { i.setExpiryDate(expiryDate); return this; }
        public ProductBuilder requiresWeighing(Boolean requiresWeighing) { i.setRequiresWeighing(requiresWeighing); return this; }
        public ProductBuilder active(Boolean active) { i.setActive(active); return this; }
        public ProductBuilder createdAt(LocalDateTime createdAt) { i.setCreatedAt(createdAt); return this; }
        public ProductBuilder updatedAt(LocalDateTime updatedAt) { i.setUpdatedAt(updatedAt); return this; }
        public ProductBuilder createdBy(Long createdBy) { i.setCreatedBy(createdBy); return this; }
        public ProductBuilder updatedBy(Long updatedBy) { i.setUpdatedBy(updatedBy); return this; }
        
        public Product build() { return i; }
    }
}


