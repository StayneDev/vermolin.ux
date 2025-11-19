package com.vermolinux.dto;

import com.vermolinux.model.User;

/**
 * DTO para resposta de login (RF1, RF3)
 * Contém token JWT e informações do usuário autenticado
 */
public class LoginResponse {
    
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private String fullName;
    private User.UserRole role;
    
    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public User.UserRole getRole() { return role; }
    public void setRole(User.UserRole role) { this.role = role; }
    
    public static LoginResponseBuilder builder() { return new LoginResponseBuilder(); }
    public static class LoginResponseBuilder {
        private LoginResponse i = new LoginResponse();
        public LoginResponseBuilder token(String token) { i.setToken(token); return this; }
        public LoginResponseBuilder type(String type) { i.setType(type); return this; }
        public LoginResponseBuilder userId(Long userId) { i.setUserId(userId); return this; }
        public LoginResponseBuilder username(String username) { i.setUsername(username); return this; }
        public LoginResponseBuilder fullName(String fullName) { i.setFullName(fullName); return this; }
        public LoginResponseBuilder role(User.UserRole role) { i.setRole(role); return this; }
        public LoginResponse build() { return i; }
    }
}


