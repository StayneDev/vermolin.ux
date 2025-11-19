package com.vermolinux.dto;

import com.vermolinux.model.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para resposta com dados completos de produto (RF10)
 * Usado por Estoquista e Gerente
 */
public class ProductResponse {
    
    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private Product.ProductUnit unit;
    private BigDecimal stockQuantity;
    private BigDecimal minStock;
    private Long supplierId;
    private String supplierName; // Nome do fornecedor para facilitar
    private LocalDate expiryDate;
    private Boolean requiresWeighing;
    private Boolean active;
    private Boolean lowStock; // Calculado: indica se está abaixo do estoque mínimo
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public Product.ProductUnit getUnit() { return unit; }
    public void setUnit(Product.ProductUnit unit) { this.unit = unit; }
    
    public BigDecimal getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(BigDecimal stockQuantity) { this.stockQuantity = stockQuantity; }
    
    public BigDecimal getMinStock() { return minStock; }
    public void setMinStock(BigDecimal minStock) { this.minStock = minStock; }
    
    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    
    public Boolean getRequiresWeighing() { return requiresWeighing; }
    public void setRequiresWeighing(Boolean requiresWeighing) { this.requiresWeighing = requiresWeighing; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public Boolean getLowStock() { return lowStock; }
    public void setLowStock(Boolean lowStock) { this.lowStock = lowStock; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Builder manual para compatibilidade
    public static ProductResponseBuilder builder() {
        return new ProductResponseBuilder();
    }
    
    public static class ProductResponseBuilder {
        private ProductResponse instance = new ProductResponse();
        
        public ProductResponseBuilder id(Long id) { instance.setId(id); return this; }
        public ProductResponseBuilder code(String code) { instance.setCode(code); return this; }
        public ProductResponseBuilder name(String name) { instance.setName(name); return this; }
        public ProductResponseBuilder description(String description) { instance.setDescription(description); return this; }
        public ProductResponseBuilder price(BigDecimal price) { instance.setPrice(price); return this; }
        public ProductResponseBuilder unit(Product.ProductUnit unit) { instance.setUnit(unit); return this; }
        public ProductResponseBuilder stockQuantity(BigDecimal stockQuantity) { instance.setStockQuantity(stockQuantity); return this; }
        public ProductResponseBuilder minStock(BigDecimal minStock) { instance.setMinStock(minStock); return this; }
        public ProductResponseBuilder supplierId(Long supplierId) { instance.setSupplierId(supplierId); return this; }
        public ProductResponseBuilder supplierName(String supplierName) { instance.setSupplierName(supplierName); return this; }
        public ProductResponseBuilder expiryDate(LocalDate expiryDate) { instance.setExpiryDate(expiryDate); return this; }
        public ProductResponseBuilder requiresWeighing(Boolean requiresWeighing) { instance.setRequiresWeighing(requiresWeighing); return this; }
        public ProductResponseBuilder active(Boolean active) { instance.setActive(active); return this; }
        public ProductResponseBuilder lowStock(Boolean lowStock) { instance.setLowStock(lowStock); return this; }
        public ProductResponseBuilder createdAt(LocalDateTime createdAt) { instance.setCreatedAt(createdAt); return this; }
        public ProductResponseBuilder updatedAt(LocalDateTime updatedAt) { instance.setUpdatedAt(updatedAt); return this; }
        
        public ProductResponse build() { return instance; }
    }
}


