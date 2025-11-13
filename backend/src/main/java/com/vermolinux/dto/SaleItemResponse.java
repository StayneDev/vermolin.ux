package com.vermolinux.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para resposta de item de venda
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemResponse {
    
    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal subtotal;
    private Boolean weighed;
}
