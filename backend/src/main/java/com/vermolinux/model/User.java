package com.vermolinux.model;

import jakarta.persistence.*;
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
@Entity
@Table(name = "users")
public class User {
    
    /** Identificador único - chave primária auto-incrementada */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Nome de usuário para login - deve ser único (RF1) */
    private String username;
    
    /** Senha criptografada com BCrypt (nunca armazenar em plano) */
    private String password;
    
    /** Nome completo do usuário para exibição */
    private String fullName;
    
    /** Cargo do usuário (GERENTE, ESTOQUISTA, CAIXA) - controla permissões RF3/RF4 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole role;
    
    /** Flag de ativação - false quando usuário é deletado (soft delete) */
    private Boolean active = true;
    
    /** Data de criação do registro - não pode ser alterada (RF6) */
    private LocalDateTime createdAt = LocalDateTime.now();
    
    /** Última atualização do registro (RF6) */
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    /** ID do usuário que criou este registro - para auditoria (RF7) */
    private Long createdBy;
    
    /** ID do último usuário que modificou este registro - para auditoria (RF7) */
    private Long updatedBy;
    
    public String getFullName() {
        return fullName;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
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
    
    public Long getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public Long getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Enum representando os cargos possíveis no sistema
     */
    public enum UserRole {
        GERENTE,
        ESTOQUISTA,
        CAIXA
    }
    
    // Builder manual para compatibilidade
    public static UserBuilder builder() { return new UserBuilder(); }
    public static class UserBuilder {
        private User i = new User();
        
        public UserBuilder id(Long id) { i.setId(id); return this; }
        public UserBuilder username(String username) { i.setUsername(username); return this; }
        public UserBuilder password(String password) { i.setPassword(password); return this; }
        public UserBuilder fullName(String fullName) { i.setFullName(fullName); return this; }
        public UserBuilder role(UserRole role) { i.setRole(role); return this; }
        public UserBuilder active(Boolean active) { i.setActive(active); return this; }
        public UserBuilder createdAt(LocalDateTime createdAt) { i.createdAt = createdAt; return this; }
        public UserBuilder updatedAt(LocalDateTime updatedAt) { i.setUpdatedAt(updatedAt); return this; }
        public UserBuilder createdBy(Long createdBy) { i.createdBy = createdBy; return this; }
        public UserBuilder updatedBy(Long updatedBy) { i.setUpdatedBy(updatedBy); return this; }
        
        public User build() { return i; }
    }
}


