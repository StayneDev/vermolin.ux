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
 * Mapeada via JPA Hibernate com PostgreSQL
 * 
 * Requisitos Relacionados:
 * - RF9: Caixa consulta produtos (sem fornecedor/validade - filtrado no DTO)
 * - RF10: Estoquista/Gerente consulta produtos completos
 * - RF22-RF25: CRUD de produtos (Gerente only)
 * - RF8: Validação de quantidade em estoque antes de vendas
 * - RF14: Produtos que precisam pesar
 * - RF34: Notificação de estoque baixo
 * 
 * Tabela PostgreSQL: 'products'
 * Campos auditoria: createdAt, updatedAt, createdBy, updatedBy
 * Soft delete: campo 'active' para manter histórico de produtos deletados
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    /** Identificador único - chave primária auto-incrementada */
    private Long id;
    
    /** Código de barras ou SKU - deve ser único para rastreabilidade */
    private String code;
    
    /** Nome do produto para exibição em vendas e consultas */
    private String name;
    
    /** Descrição detalhada (opcional) */
    private String description;
    
    /** Preço unitário do produto em R$ */
    private BigDecimal price;
    
    /** Unidade de medida (KG, UNIDADE, CAIXA, DUZIA) - para RF14 */
    private ProductUnit unit;
    
    /** Quantidade atual em estoque - decrementada em vendas (RF18) */
    @Builder.Default
    private BigDecimal stockQuantity = BigDecimal.ZERO;
    
    /** Quantidade mínima para disparar alerta de estoque baixo (RF34) */
    @Builder.Default
    private BigDecimal minStock = BigDecimal.ZERO;
    
    /** Referência ao fornecedor (visualizado apenas por Estoquista/Gerente - RF9/RF10) */
    private Long supplierId;
    
    /** Data de validade (se aplicável) - visualizada apenas por Estoquista/Gerente - RF9/RF10 */
    private LocalDate expiryDate;
    
    /** Indica se o produto requer pesagem em vendas (RF14) - ex: frutas/verduras */
    @Builder.Default
    private Boolean requiresWeighing = false;
    
    /** Flag de ativação - false quando produto é deletado (soft delete) */
    @Builder.Default
    private Boolean active = true;
    
    /** Data de criação do registro - não pode ser alterada (RF6) */
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    /** Última atualização do registro (RF6) */
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    /** ID do usuário que criou este registro - para auditoria (RF7) */
    private Long createdBy;
    
    /** ID do último usuário que modificou este registro - para auditoria (RF7) */
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
