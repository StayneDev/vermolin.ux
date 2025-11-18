package com.vermolinux.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Sale - Representa uma venda/transação
 */
@Entity
@Table(name = "sales")
public class Sale {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate = LocalDateTime.now();
    
    @Column(name = "cashier_id", nullable = false)
    private Long cashierId;
    
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 20)
    private PaymentMethod paymentMethod;
    
    @Column(name = "amount_paid", precision = 10, scale = 2)
    private BigDecimal amountPaid;
    
    @Column(name = "change_amount", precision = 10, scale = 2)
    private BigDecimal changeAmount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SaleStatus status = SaleStatus.OPEN;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
    
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
    
    @Column(name = "cancelled_by")
    private Long cancelledBy;
    
    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @Transient
    private List<SaleItem> items = new ArrayList<>();
    
    // Explicit getters and setters for compilation compatibility
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getSaleDate() {
        return saleDate;
    }
    
    public Long getCashierId() {
        return cashierId;
    }
    
    public void setCashierId(Long cashierId) {
        this.cashierId = cashierId;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }
    
    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    public BigDecimal getChangeAmount() {
        return changeAmount;
    }
    
    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }
    
    public SaleStatus getStatus() {
        return status;
    }
    
    public void setStatus(SaleStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public LocalDateTime getPaidAt() {
        return paidAt;
    }
    
    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
    
    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }
    
    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }
    
    public Long getCancelledBy() {
        return cancelledBy;
    }
    
    public void setCancelledBy(Long cancelledBy) {
        this.cancelledBy = cancelledBy;
    }
    
    public String getCancellationReason() {
        return cancellationReason;
    }
    
    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
    
    public List<SaleItem> getItems() {
        return items;
    }
    
    public void setItems(List<SaleItem> items) {
        this.items = items;
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Enum representando os status possíveis de uma venda
     */
    public enum SaleStatus {
        OPEN,
        PAID,
        COMPLETED,
        CANCELLED
    }
    
    /**
     * Enum representando métodos de pagamento
     */
    public enum PaymentMethod {
        DINHEIRO,
        CARTAO,
        PIX
    }
    
    /**
     * Adiciona um item à venda e recalcula o total
     */
    public void addItem(SaleItem item) {
        items.add(item);
        recalculateTotal();
    }
    
    /**
     * Remove um item da venda e recalcula o total
     */
    public void removeItem(SaleItem item) {
        items.remove(item);
        recalculateTotal();
    }
    
    /**
     * Recalcula o valor total da venda
     */
    public void recalculateTotal() {
        totalAmount = items.stream()
            .map(SaleItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Builder manual para compatibilidade
    public static SaleBuilder builder() { return new SaleBuilder(); }
    public static class SaleBuilder {
        private Sale i = new Sale();
        
        public SaleBuilder id(Long id) { i.id = id; return this; }
        public SaleBuilder cashierId(Long cashierId) { i.setCashierId(cashierId); return this; }
        public SaleBuilder totalAmount(BigDecimal totalAmount) { i.setTotalAmount(totalAmount); return this; }
        public SaleBuilder paymentMethod(PaymentMethod paymentMethod) { i.setPaymentMethod(paymentMethod); return this; }
        public SaleBuilder amountPaid(BigDecimal amountPaid) { i.setAmountPaid(amountPaid); return this; }
        public SaleBuilder changeAmount(BigDecimal changeAmount) { i.setChangeAmount(changeAmount); return this; }
        public SaleBuilder status(SaleStatus status) { i.setStatus(status); return this; }
        public SaleBuilder createdAt(LocalDateTime createdAt) { i.createdAt = createdAt; return this; }
        public SaleBuilder paidAt(LocalDateTime paidAt) { i.setPaidAt(paidAt); return this; }
        public SaleBuilder cancelledAt(LocalDateTime cancelledAt) { i.setCancelledAt(cancelledAt); return this; }
        public SaleBuilder cancelledBy(Long cancelledBy) { i.setCancelledBy(cancelledBy); return this; }
        public SaleBuilder cancellationReason(String cancellationReason) { i.setCancellationReason(cancellationReason); return this; }
        public SaleBuilder updatedAt(LocalDateTime updatedAt) { i.setUpdatedAt(updatedAt); return this; }
        public SaleBuilder items(List<SaleItem> items) { i.items = items; return this; }
        
        public Sale build() { return i; }
    }
}


