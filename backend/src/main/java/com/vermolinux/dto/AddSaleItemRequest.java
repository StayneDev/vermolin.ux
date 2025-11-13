package com.vermolinux.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO para adicionar item à venda (RF12)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddSaleItemRequest {
    
    @NotNull(message = "ID do produto é obrigatório")
    private Long productId;
    
    @NotNull(message = "Quantidade é obrigatória")
    @DecimalMin(value = "0.001", message = "Quantidade deve ser maior que zero")
    private BigDecimal quantity;
    
    private Boolean weighed; // Se o produto foi pesado (RF14)
}
