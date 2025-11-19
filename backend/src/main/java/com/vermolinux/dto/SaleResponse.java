package com.vermolinux.dto;

import com.vermolinux.model.Sale;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para resposta com dados de venda (RF7, RF11)
 */
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
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Sale.SaleStatus getStatus() { return status; }
    public void setStatus(Sale.SaleStatus status) { this.status = status; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public Sale.PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(Sale.PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public BigDecimal getAmountPaid() { return amountPaid; }
    public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }
    
    public BigDecimal getChangeAmount() { return changeAmount; }
    public void setChangeAmount(BigDecimal changeAmount) { this.changeAmount = changeAmount; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
    
    public LocalDateTime getCancelledAt() { return cancelledAt; }
    public void setCancelledAt(LocalDateTime cancelledAt) { this.cancelledAt = cancelledAt; }
    
    public Long getCashierId() { return cashierId; }
    public void setCashierId(Long cashierId) { this.cashierId = cashierId; }
    
    public String getCashierName() { return cashierName; }
    public void setCashierName(String cashierName) { this.cashierName = cashierName; }
    
    public Long getCancelledBy() { return cancelledBy; }
    public void setCancelledBy(Long cancelledBy) { this.cancelledBy = cancelledBy; }
    
    public String getCancelledByName() { return cancelledByName; }
    public void setCancelledByName(String cancelledByName) { this.cancelledByName = cancelledByName; }
    
    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }
    
    public List<SaleItemResponse> getItems() { return items; }
    public void setItems(List<SaleItemResponse> items) { this.items = items; }
    
    public static SaleResponseBuilder builder() { return new SaleResponseBuilder(); }
    public static class SaleResponseBuilder {
        private SaleResponse i = new SaleResponse();
        public SaleResponseBuilder id(Long id) { i.setId(id); return this; }
        public SaleResponseBuilder status(Sale.SaleStatus status) { i.setStatus(status); return this; }
        public SaleResponseBuilder totalAmount(BigDecimal totalAmount) { i.setTotalAmount(totalAmount); return this; }
        public SaleResponseBuilder paymentMethod(Sale.PaymentMethod paymentMethod) { i.setPaymentMethod(paymentMethod); return this; }
        public SaleResponseBuilder amountPaid(BigDecimal amountPaid) { i.setAmountPaid(amountPaid); return this; }
        public SaleResponseBuilder changeAmount(BigDecimal changeAmount) { i.setChangeAmount(changeAmount); return this; }
        public SaleResponseBuilder createdAt(LocalDateTime createdAt) { i.setCreatedAt(createdAt); return this; }
        public SaleResponseBuilder paidAt(LocalDateTime paidAt) { i.setPaidAt(paidAt); return this; }
        public SaleResponseBuilder cancelledAt(LocalDateTime cancelledAt) { i.setCancelledAt(cancelledAt); return this; }
        public SaleResponseBuilder cashierId(Long cashierId) { i.setCashierId(cashierId); return this; }
        public SaleResponseBuilder cashierName(String cashierName) { i.setCashierName(cashierName); return this; }
        public SaleResponseBuilder cancelledBy(Long cancelledBy) { i.setCancelledBy(cancelledBy); return this; }
        public SaleResponseBuilder cancelledByName(String cancelledByName) { i.setCancelledByName(cancelledByName); return this; }
        public SaleResponseBuilder cancellationReason(String cancellationReason) { i.setCancellationReason(cancellationReason); return this; }
        public SaleResponseBuilder items(List<SaleItemResponse> items) { i.setItems(items); return this; }
        public SaleResponse build() { return i; }
    }
}


