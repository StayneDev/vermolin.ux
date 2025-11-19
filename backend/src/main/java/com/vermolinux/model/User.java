package com.vermolinux.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade User - Representa um usuário/funcionário do sistema
 * 
 * Relacionado aos requisitos:
 * - RF1: Login no sistema
 * - RF3: Identificação de cargo
 * - RF26-RF29: CRUD de usuários (Gerente)
 */
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "updated_by")
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


