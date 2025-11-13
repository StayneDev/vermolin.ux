package com.vermolinux.dto;

import com.vermolinux.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para resposta com dados limitados de produto (RF9)
 * Usado por Caixa - não expõe fornecedor nem validade
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
