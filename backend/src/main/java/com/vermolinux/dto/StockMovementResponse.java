package com.vermolinux.dto;

import com.vermolinux.model.StockMovement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para resposta de movimentação de estoque (RF7)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementResponse {
    
    private Long id;
    private Long productId;
    private String productName;
    private StockMovement.MovementType movementType;
    private BigDecimal quantity;
    private BigDecimal previousQuantity;
    private BigDecimal newQuantity;
    private StockMovement.MovementReason reason;
    private String notes;
    private Long supplierId;
    private String supplierName;
    private LocalDate expiryDate;
    private LocalDateTime createdAt;
    private Long createdBy;
    private String createdByName;
    private Long saleId;
}
