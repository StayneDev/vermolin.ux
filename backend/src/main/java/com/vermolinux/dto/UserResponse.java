package com.vermolinux.dto;

import com.vermolinux.model.User;
import java.time.LocalDateTime;

/**
 * DTO para resposta com dados de usuário (RF27)
 * Não expõe a senha
 */
public class UserResponse {
    
    private Long id;
    private String username;
    private String fullName;
    private User.UserRole role;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public User.UserRole getRole() { return role; }
    public void setRole(User.UserRole role) { this.role = role; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    
    public Long getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Long updatedBy) { this.updatedBy = updatedBy; }
    
    public static UserResponseBuilder builder() { return new UserResponseBuilder(); }
    public static class UserResponseBuilder {
        private UserResponse i = new UserResponse();
        public UserResponseBuilder id(Long id) { i.setId(id); return this; }
        public UserResponseBuilder username(String username) { i.setUsername(username); return this; }
        public UserResponseBuilder fullName(String fullName) { i.setFullName(fullName); return this; }
        public UserResponseBuilder role(User.UserRole role) { i.setRole(role); return this; }
        public UserResponseBuilder active(Boolean active) { i.setActive(active); return this; }
        public UserResponseBuilder createdAt(LocalDateTime createdAt) { i.setCreatedAt(createdAt); return this; }
        public UserResponseBuilder updatedAt(LocalDateTime updatedAt) { i.setUpdatedAt(updatedAt); return this; }
        public UserResponseBuilder createdBy(Long createdBy) { i.setCreatedBy(createdBy); return this; }
        public UserResponseBuilder updatedBy(Long updatedBy) { i.setUpdatedBy(updatedBy); return this; }
        public UserResponse build() { return i; }
    }
}


