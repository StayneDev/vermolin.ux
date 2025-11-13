package com.vermolinux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade User - Representa um usuário/funcionário do sistema
 * 
 * Relacionado aos requisitos:
 * - RF1: Login no sistema
 * - RF3: Identificação de cargo
 * - RF26-RF29: CRUD de usuários (Gerente)
 * 
 * TODO: Quando integrar com banco de dados, adicionar anotações JPA:
 * @Entity
 * @Table(name = "users")
 * 
 * Estrutura da tabela 'users':
 * - id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
 * - username (VARCHAR(50), UNIQUE, NOT NULL)
 * - password (VARCHAR(255), NOT NULL) - hash BCrypt
 * - full_name (VARCHAR(100), NOT NULL)
 * - role (VARCHAR(20), NOT NULL) - enum: GERENTE, ESTOQUISTA, CAIXA
 * - active (BOOLEAN, DEFAULT TRUE)
 * - created_at (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)
 * - updated_at (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP ON UPDATE)
 * - created_by (BIGINT, FOREIGN KEY references users(id))
 * - updated_by (BIGINT, FOREIGN KEY references users(id))
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    // TODO: @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    // TODO: @Column(nullable = false)
    private String password; // Armazenar hash BCrypt
    
    // TODO: @Column(nullable = false, length = 100)
    private String fullName;
    
    // TODO: @Enumerated(EnumType.STRING)
    // TODO: @Column(nullable = false, length = 20)
    private UserRole role;
    
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
     * Enum representando os cargos possíveis no sistema
     * Controla permissões conforme RF3 e RF4
     */
    public enum UserRole {
        GERENTE,    // Acesso total
        ESTOQUISTA, // Gerenciamento de estoque
        CAIXA       // Operações de venda
    }
}
