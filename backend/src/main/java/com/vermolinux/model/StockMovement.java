package com.vermolinux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidade StockMovement - Representa uma movimentação de estoque
 * 
 * Relacionado aos requisitos:
 * - RF6: Registrar operações de estoque com auditoria
 * - RF7: Histórico de movimentações
 * - RF19: Registrar entrada de produtos
 * - RF20: Registrar saída de produtos
 * - RF21: Editar/ajustar estoque
 * 
 * TODO: Quando integrar com banco de dados, adicionar anotações JPA:
 * @Entity
 * @Table(name = "stock_movements")
 * 
 * Estrutura da tabela 'stock_movements':
 * - id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
 * - product_id (BIGINT, NOT NULL, FOREIGN KEY references products(id))
 * - movement_type (VARCHAR(20), NOT NULL) - enum: ENTRADA, SAIDA, AJUSTE, VENDA
 * - quantity (DECIMAL(10,3), NOT NULL)
 * - previous_quantity (DECIMAL(10,3), NOT NULL)
 * - new_quantity (DECIMAL(10,3), NOT NULL)
 * - reason (VARCHAR(50)) - VENDA, PERDA, DOACAO, COMPRA, etc
 * - notes (TEXT)
 * - supplier_id (BIGINT, FOREIGN KEY references suppliers(id))
 * - expiry_date (DATE)
 * - created_at (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)
 * - created_by (BIGINT, NOT NULL, FOREIGN KEY references users(id))
 * - sale_id (BIGINT, FOREIGN KEY references sales(id))
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement {
    
    // TODO: @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: @ManyToOne @JoinColumn(name = "product_id", nullable = false)
    private Long productId;
    
    // TODO: @Enumerated(EnumType.STRING)
    // TODO: @Column(nullable = false, length = 20)
    private MovementType movementType;
    
    // TODO: @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantity;
    
    // TODO: @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal previousQuantity; // Quantidade antes da movimentação
    
    // TODO: @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal newQuantity; // Quantidade após a movimentação
    
    // TODO: @Enumerated(EnumType.STRING)
    // TODO: @Column(length = 50)
    private MovementReason reason;
    
    // TODO: @Column(columnDefinition = "TEXT")
    private String notes; // Observações adicionais
    
    // TODO: @ManyToOne @JoinColumn(name = "supplier_id")
    private Long supplierId; // Para entradas de produtos
    
    // TODO: @Column(name = "expiry_date")
    private LocalDate expiryDate; // Data de validade para entradas
    
    // TODO: @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // TODO: @ManyToOne @JoinColumn(name = "created_by", nullable = false)
    private Long createdBy; // Usuário responsável pela movimentação
    
    // TODO: @ManyToOne @JoinColumn(name = "sale_id")
    private Long saleId; // Referência à venda, se aplicável
    
    /**
     * Enum representando tipos de movimentação
     */
    public enum MovementType {
        ENTRADA,  // Entrada de mercadoria (RF19)
        SAIDA,    // Saída manual de estoque (RF20)
        AJUSTE,   // Ajuste/correção de estoque (RF21)
        VENDA     // Saída automática por venda (RF18)
    }
    
    /**
     * Enum representando motivos de movimentação (RF20)
     */
    public enum MovementReason {
        COMPRA,     // Compra de fornecedor
        VENDA,      // Venda ao cliente
        PERDA,      // Perda/deterioração
        DOACAO,     // Doação
        DEVOLUCAO,  // Devolução
        AJUSTE,     // Ajuste de inventário
        OUTROS      // Outros motivos
    }
}
