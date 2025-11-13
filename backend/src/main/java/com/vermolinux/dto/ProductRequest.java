package com.vermolinux.dto;

import com.vermolinux.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para criação/atualização de produto (RF22, RF24)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    
    @NotBlank(message = "Código é obrigatório")
    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    private String code;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String name;
    
    private String description;
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    private BigDecimal price;
    
    @NotNull(message = "Unidade de medida é obrigatória")
    private Product.ProductUnit unit;
    
    @DecimalMin(value = "0", message = "Quantidade em estoque não pode ser negativa")
    private BigDecimal stockQuantity;
    
    @DecimalMin(value = "0", message = "Estoque mínimo não pode ser negativo")
    private BigDecimal minStock;
    
    private Long supplierId;
    
    private LocalDate expiryDate;
    
    private Boolean requiresWeighing;
    
    private Boolean active;
}
