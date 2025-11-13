package com.vermolinux.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO para criação/atualização de fornecedor (RF30, RF32)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String name;
    
    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos")
    private String cnpj;
    
    @Size(max = 100, message = "Nome do contato deve ter no máximo 100 caracteres")
    private String contactName;
    
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String phone;
    
    @Email(message = "Email inválido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;
    
    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    private String address;
    
    private Boolean active;
}
