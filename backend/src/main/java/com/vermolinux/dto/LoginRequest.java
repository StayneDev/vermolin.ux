package com.vermolinux.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para requisição de login (RF1)
 */
public class LoginRequest {
    
    @NotBlank(message = "Usuário é obrigatório")
    @Size(min = 3, max = 50, message = "Usuário deve ter entre 3 e 50 caracteres")
    private String username;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 4, message = "Senha deve ter no mínimo 4 caracteres")
    private String password;
    
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}


