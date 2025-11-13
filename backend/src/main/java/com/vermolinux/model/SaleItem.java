package com.vermolinux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidade SaleItem - Representa um item dentro de uma venda
 * 
 * Relacionado aos requisitos:
 * - RF12: Adicionar produtos à venda
 * - RF13: Remover produtos da venda
 * - RF14: Pesar produto quando aplicável
 * 
 * TODO: Quando integrar com banco de dados, adicionar anotações JPA:
 * @Entity
 * @Table(name = "sale_items")
 * 
 * Estrutura da tabela 'sale_items':
 * - id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
 * - sale_id (BIGINT, NOT NULL, FOREIGN KEY references sales(id))
 * - product_id (BIGINT, NOT NULL, FOREIGN KEY references products(id))
 * - product_name (VARCHAR(100), NOT NULL) - snapshot do nome
 * - product_price (DECIMAL(10,2), NOT NULL) - snapshot do preço
 * - quantity (DECIMAL(10,3), NOT NULL)
 * - unit (VARCHAR(10), NOT NULL)
 * - subtotal (DECIMAL(10,2), NOT NULL)
 * - weighed (BOOLEAN, DEFAULT FALSE)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem {
    
    // TODO: @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: @ManyToOne @JoinColumn(name = "sale_id", nullable = false)
    private Long saleId;
    
    // TODO: @ManyToOne @JoinColumn(name = "product_id", nullable = false)
    private Long productId;
    
    // TODO: @Column(nullable = false, length = 100)
    private String productName; // Snapshot do nome do produto no momento da venda
    
    // TODO: @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal productPrice; // Snapshot do preço no momento da venda
    
    // TODO: @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantity;
    
    // TODO: @Column(nullable = false, length = 10)
    private String unit; // Unidade de medida
    
    // TODO: @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
    
    // TODO: @Column(nullable = false)
    @Builder.Default
    private Boolean weighed = false; // Se o produto foi pesado (RF14)
    
    /**
     * Calcula o subtotal do item (quantidade * preço)
     */
    public void calculateSubtotal() {
        if (productPrice != null && quantity != null) {
            subtotal = productPrice.multiply(quantity);
        }
    }
}
