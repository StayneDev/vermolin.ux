package com.vermolinux.dto;

import com.vermolinux.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para criação/atualização de usuário (RF26, RF28)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    
    @NotBlank(message = "Usuário é obrigatório")
    @Size(min = 3, max = 50, message = "Usuário deve ter entre 3 e 50 caracteres")
    private String username;
    
    @Size(min = 4, message = "Senha deve ter no mínimo 4 caracteres")
    private String password; // Opcional em updates
    
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 100, message = "Nome completo deve ter no máximo 100 caracteres")
    private String fullName;
    
    @NotNull(message = "Cargo é obrigatório")
    private User.UserRole role;
    
    private Boolean active;
}
