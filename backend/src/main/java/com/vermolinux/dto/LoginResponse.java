package com.vermolinux.dto;

import com.vermolinux.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de login (RF1, RF3)
 * Contém token JWT e informações do usuário autenticado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private String fullName;
    private User.UserRole role;
}
