package com.vermolinux.dto;

import com.vermolinux.model.Product;
import java.math.BigDecimal;

/**
 * DTO para resposta com dados limitados de produto (RF9)
 * Usado por Caixa - não expõe fornecedor nem validade
 */
public class ProductCashierResponse {
    
    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private Product.ProductUnit unit;
    private BigDecimal stockQuantity;
    private Boolean requiresWeighing;
    private Boolean active;
    
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
    
    public Boolean getRequiresWeighing() { return requiresWeighing; }
    public void setRequiresWeighing(Boolean requiresWeighing) { this.requiresWeighing = requiresWeighing; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public static ProductCashierResponseBuilder builder() { return new ProductCashierResponseBuilder(); }
    public static class ProductCashierResponseBuilder {
        private ProductCashierResponse i = new ProductCashierResponse();
        public ProductCashierResponseBuilder id(Long id) { i.setId(id); return this; }
        public ProductCashierResponseBuilder code(String code) { i.setCode(code); return this; }
        public ProductCashierResponseBuilder name(String name) { i.setName(name); return this; }
        public ProductCashierResponseBuilder description(String description) { i.setDescription(description); return this; }
        public ProductCashierResponseBuilder price(BigDecimal price) { i.setPrice(price); return this; }
        public ProductCashierResponseBuilder unit(Product.ProductUnit unit) { i.setUnit(unit); return this; }
        public ProductCashierResponseBuilder stockQuantity(BigDecimal stockQuantity) { i.setStockQuantity(stockQuantity); return this; }
        public ProductCashierResponseBuilder requiresWeighing(Boolean requiresWeighing) { i.setRequiresWeighing(requiresWeighing); return this; }
        public ProductCashierResponseBuilder active(Boolean active) { i.setActive(active); return this; }
        public ProductCashierResponse build() { return i; }
    }
}


