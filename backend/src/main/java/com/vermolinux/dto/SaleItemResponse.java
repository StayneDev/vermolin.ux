package com.vermolinux.dto;

import java.math.BigDecimal;

/**
 * DTO para resposta de item de venda
 */
public class SaleItemResponse {
    
    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal subtotal;
    private Boolean weighed;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }
    
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    
    public Boolean getWeighed() { return weighed; }
    public void setWeighed(Boolean weighed) { this.weighed = weighed; }
    
    public static SaleItemResponseBuilder builder() { return new SaleItemResponseBuilder(); }
    public static class SaleItemResponseBuilder {
        private SaleItemResponse i = new SaleItemResponse();
        public SaleItemResponseBuilder id(Long id) { i.setId(id); return this; }
        public SaleItemResponseBuilder productId(Long productId) { i.setProductId(productId); return this; }
        public SaleItemResponseBuilder productName(String productName) { i.setProductName(productName); return this; }
        public SaleItemResponseBuilder productPrice(BigDecimal productPrice) { i.setProductPrice(productPrice); return this; }
        public SaleItemResponseBuilder quantity(BigDecimal quantity) { i.setQuantity(quantity); return this; }
        public SaleItemResponseBuilder unit(String unit) { i.setUnit(unit); return this; }
        public SaleItemResponseBuilder subtotal(BigDecimal subtotal) { i.setSubtotal(subtotal); return this; }
        public SaleItemResponseBuilder weighed(Boolean weighed) { i.setWeighed(weighed); return this; }
        public SaleItemResponse build() { return i; }
    }
}


