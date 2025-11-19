package com.vermolinux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade User - Representa um usuário/funcionário do sistema
 * 
 * Mapeada via JPA Hibernate com PostgreSQL
 * 
 * Requisitos Relacionados:
 * - RF1: Login no sistema (autenticação)
 * - RF3: Identificação de cargo (roles)
 * - RF26-RF29: CRUD de usuários (gerenciamento)
 * - RF6, RF7: Auditoria (createdAt, updatedAt, createdBy, updatedBy)
 * 
 * Tabela PostgreSQL: 'users'
 * Campos auditoria: createdAt, updatedAt, createdBy, updatedBy (registrados automaticamente)
 * Soft delete: campo 'active' para manter histórico de usuários deletados
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    /** Identificador único - chave primária auto-incrementada */
    private Long id;
    
    /** Nome de usuário para login - deve ser único (RF1) */
    private String username;
    
    /** Senha criptografada com BCrypt (nunca armazenar em plano) */
    private String password;
    
    /** Nome completo do usuário para exibição */
    private String fullName;
    
    /** Cargo do usuário (GERENTE, ESTOQUISTA, CAIXA) - controla permissões RF3/RF4 */
    private UserRole role;
    
    /** Flag de ativação - false quando usuário é deletado (soft delete) */
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
     * Enum representando os cargos possíveis no sistema
     * Controla permissões conforme RF3 e RF4
     */
    public enum UserRole {
        GERENTE,    // Acesso total
        ESTOQUISTA, // Gerenciamento de estoque
        CAIXA       // Operações de venda
    }
}
