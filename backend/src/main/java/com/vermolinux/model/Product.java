package com.vermolinux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidade Product - Representa um produto do hortifruti
 * 
 * Relacionado aos requisitos:
 * - RF9: Caixa consulta produtos (sem fornecedor/validade)
 * - RF10: Estoquista/Gerente consulta produtos completos
 * - RF22-RF25: CRUD de produtos (Gerente)
 * - RF8: Validação de estoque em vendas
 * 
 * TODO: Quando integrar com banco de dados, adicionar anotações JPA:
 * @Entity
 * @Table(name = "products")
 * 
 * Estrutura da tabela 'products':
 * - id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
 * - code (VARCHAR(50), UNIQUE, NOT NULL)
 * - name (VARCHAR(100), NOT NULL)
 * - description (TEXT)
 * - price (DECIMAL(10,2), NOT NULL)
 * - unit (VARCHAR(10), NOT NULL) - enum: KG, UNIDADE, CAIXA, DUZIA
 * - stock_quantity (DECIMAL(10,3), NOT NULL, DEFAULT 0)
 * - min_stock (DECIMAL(10,3), DEFAULT 0) - para RF34
 * - supplier_id (BIGINT, FOREIGN KEY references suppliers(id))
 * - expiry_date (DATE)
 * - requires_weighing (BOOLEAN, DEFAULT FALSE) - para RF14
 * - active (BOOLEAN, DEFAULT TRUE)
 * - created_at (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)
 * - updated_at (TIMESTAMP)
 * - created_by (BIGINT, FOREIGN KEY references users(id))
 * - updated_by (BIGINT, FOREIGN KEY references users(id))
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    // TODO: @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: @Column(unique = true, nullable = false, length = 50)
    private String code; // Código de barras ou SKU
    
    // TODO: @Column(nullable = false, length = 100)
    private String name;
    
    // TODO: @Column(columnDefinition = "TEXT")
    private String description;
    
    // TODO: @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    // TODO: @Enumerated(EnumType.STRING)
    // TODO: @Column(nullable = false, length = 10)
    private ProductUnit unit;
    
    // TODO: @Column(nullable = false, precision = 10, scale = 3)
    @Builder.Default
    private BigDecimal stockQuantity = BigDecimal.ZERO;
    
    // TODO: @Column(precision = 10, scale = 3)
    @Builder.Default
    private BigDecimal minStock = BigDecimal.ZERO; // Estoque mínimo para notificação RF34
    
    // TODO: @ManyToOne @JoinColumn(name = "supplier_id")
    private Long supplierId; // Referência ao fornecedor
    
    // TODO: @Column(name = "expiry_date")
    private LocalDate expiryDate; // Data de validade
    
    // TODO: @Column(nullable = false)
    @Builder.Default
    private Boolean requiresWeighing = false; // Se precisa pesar (RF14)
    
    // TODO: @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
    
    // TODO: @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // TODO: @Column(nullable = false)
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // TODO: @ManyToOne @JoinColumn(name = "created_by")
    private Long createdBy;
    
    // TODO: @ManyToOne @JoinColumn(name = "updated_by")
    private Long updatedBy;
    
    /**
     * Enum representando unidades de medida possíveis
     */
    public enum ProductUnit {
        KG,        // Quilograma
        UNIDADE,   // Unidade
        CAIXA,     // Caixa
        DUZIA      // Dúzia
    }
    
    /**
     * Verifica se o produto está com estoque baixo (RF34)
     */
    public boolean isLowStock() {
        return stockQuantity.compareTo(minStock) <= 0;
    }
}
