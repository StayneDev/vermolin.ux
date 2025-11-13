package com.vermolinux.dto;

import com.vermolinux.model.Sale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para resposta com dados de venda (RF7, RF11)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {
    
    private Long id;
    private Sale.SaleStatus status;
    private BigDecimal totalAmount;
    private Sale.PaymentMethod paymentMethod;
    private BigDecimal amountPaid;
    private BigDecimal changeAmount;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
    private LocalDateTime cancelledAt;
    private Long cashierId;
    private String cashierName;
    private Long cancelledBy;
    private String cancelledByName;
    private String cancellationReason;
    private List<SaleItemResponse> items;
}
