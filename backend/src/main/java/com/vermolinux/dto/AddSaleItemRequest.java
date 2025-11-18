package com.vermolinux.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO para adicionar item à venda (RF12)
 */
public class AddSaleItemRequest {
    
    @NotNull(message = "ID do produto é obrigatório")
    private Long productId;
    
    @NotNull(message = "Quantidade é obrigatória")
    @DecimalMin(value = "0.001", message = "Quantidade deve ser maior que zero")
    private BigDecimal quantity;
    
    private Boolean weighed; // Se o produto foi pesado (RF14)
    
    // Getters and Setters
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public BigDecimal getQuantity() {
        return quantity;
    }
    
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    
    public Boolean getWeighed() {
        return weighed;
    }
    
    public void setWeighed(Boolean weighed) {
        this.weighed = weighed;
    }
}


