package com.vermolinux.dto;

import com.vermolinux.model.StockMovement;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para registrar movimentação de estoque (RF19, RF20, RF21)
 */
public class StockMovementRequest {
    
    @NotNull(message = "ID do produto é obrigatório")
    private Long productId;
    
    @NotNull(message = "Tipo de movimentação é obrigatório")
    private StockMovement.MovementType movementType;
    
    @NotNull(message = "Quantidade é obrigatória")
    @DecimalMin(value = "0.001", message = "Quantidade deve ser maior que zero")
    private BigDecimal quantity;
    
    private String reason;
    
    private String notes;
    
    private Long supplierId; // Para entradas
    
    private LocalDate expiryDate; // Para entradas
    
    // Getters explícitos
    public Long getProductId() { return productId; }
    public StockMovement.MovementType getMovementType() { return movementType; }
    public BigDecimal getQuantity() { return quantity; }
    public String getReason() { return reason; }
    public String getNotes() { return notes; }
    public Long getSupplierId() { return supplierId; }
    public LocalDate getExpiryDate() { return expiryDate; }
}


