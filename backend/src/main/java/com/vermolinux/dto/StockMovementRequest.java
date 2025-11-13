package com.vermolinux.dto;

import com.vermolinux.model.StockMovement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para registrar movimentação de estoque (RF19, RF20, RF21)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementRequest {
    
    @NotNull(message = "ID do produto é obrigatório")
    private Long productId;
    
    @NotNull(message = "Tipo de movimentação é obrigatório")
    private StockMovement.MovementType movementType;
    
    @NotNull(message = "Quantidade é obrigatória")
    @DecimalMin(value = "0.001", message = "Quantidade deve ser maior que zero")
    private BigDecimal quantity;
    
    private StockMovement.MovementReason reason;
    
    private String notes;
    
    private Long supplierId; // Para entradas
    
    private LocalDate expiryDate; // Para entradas
}
