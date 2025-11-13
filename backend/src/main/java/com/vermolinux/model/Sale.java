package com.vermolinux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Sale - Representa uma venda/transação
 * 
 * Relacionado aos requisitos:
 * - RF11: Abrir transação de venda
 * - RF12-RF13: Adicionar/remover produtos
 * - RF15: Cancelar venda
 * - RF16-RF18: Pagamento e finalização
 * - RF7: Histórico de vendas
 * 
 * TODO: Quando integrar com banco de dados, adicionar anotações JPA:
 * @Entity
 * @Table(name = "sales")
 * 
 * Estrutura da tabela 'sales':
 * - id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
 * - status (VARCHAR(20), NOT NULL) - enum: OPEN, PAID, CANCELLED
 * - total_amount (DECIMAL(10,2), NOT NULL, DEFAULT 0)
 * - payment_method (VARCHAR(20)) - enum: DINHEIRO, CARTAO, PIX
 * - amount_paid (DECIMAL(10,2))
 * - change_amount (DECIMAL(10,2))
 * - created_at (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)
 * - paid_at (TIMESTAMP)
 * - cancelled_at (TIMESTAMP)
 * - cashier_id (BIGINT, NOT NULL, FOREIGN KEY references users(id))
 * - cancelled_by (BIGINT, FOREIGN KEY references users(id))
 * - cancellation_reason (TEXT)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    
    // TODO: @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: @Enumerated(EnumType.STRING)
    // TODO: @Column(nullable = false, length = 20)
    @Builder.Default
    private SaleStatus status = SaleStatus.OPEN;
    
    // TODO: @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;
    
    // TODO: @Enumerated(EnumType.STRING)
    // TODO: @Column(length = 20)
    private PaymentMethod paymentMethod;
    
    // TODO: @Column(precision = 10, scale = 2)
    private BigDecimal amountPaid;
    
    // TODO: @Column(precision = 10, scale = 2)
    private BigDecimal changeAmount;
    
    // TODO: @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // TODO: @Column
    private LocalDateTime paidAt;
    
    // TODO: @Column
    private LocalDateTime cancelledAt;
    
    // TODO: @ManyToOne @JoinColumn(name = "cashier_id", nullable = false)
    private Long cashierId; // ID do caixa que iniciou a venda
    
    // TODO: @ManyToOne @JoinColumn(name = "cancelled_by")
    private Long cancelledBy; // ID do usuário que cancelou
    
    // TODO: @Column(columnDefinition = "TEXT")
    private String cancellationReason;
    
    // TODO: @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SaleItem> items = new ArrayList<>();
    
    /**
     * Enum representando os status possíveis de uma venda
     */
    public enum SaleStatus {
        OPEN,      // Aberta, sendo montada
        PAID,      // Paga e finalizada
        CANCELLED  // Cancelada
    }
    
    /**
     * Enum representando métodos de pagamento (RF16)
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
}
