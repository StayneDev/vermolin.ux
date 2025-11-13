package com.vermolinux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade Supplier - Representa um fornecedor de produtos
 * 
 * Relacionado aos requisitos:
 * - RF30-RF33: CRUD de fornecedores (Gerente)
 * - RF35: Contato com fornecedor quando estoque baixo
 * 
 * TODO: Quando integrar com banco de dados, adicionar anotações JPA:
 * @Entity
 * @Table(name = "suppliers")
 * 
 * Estrutura da tabela 'suppliers':
 * - id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
 * - name (VARCHAR(100), NOT NULL)
 * - cnpj (VARCHAR(14), UNIQUE, NOT NULL)
 * - contact_name (VARCHAR(100))
 * - phone (VARCHAR(20))
 * - email (VARCHAR(100))
 * - address (VARCHAR(255))
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
public class Supplier {
    
    // TODO: @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: @Column(nullable = false, length = 100)
    private String name;
    
    // TODO: @Column(unique = true, nullable = false, length = 14)
    private String cnpj; // Apenas números
    
    // TODO: @Column(length = 100)
    private String contactName;
    
    // TODO: @Column(length = 20)
    private String phone;
    
    // TODO: @Column(length = 100)
    private String email;
    
    // TODO: @Column(length = 255)
    private String address;
    
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
}
