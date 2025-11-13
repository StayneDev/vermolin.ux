package com.vermolinux.dto;

import com.vermolinux.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para resposta com dados completos de produto (RF10)
 * Usado por Estoquista e Gerente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
