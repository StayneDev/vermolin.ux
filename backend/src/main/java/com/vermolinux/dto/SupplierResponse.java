package com.vermolinux.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para resposta com dados de fornecedor (RF31)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponse {
    
    private Long id;
    private String name;
    private String cnpj;
    private String contactName;
    private String phone;
    private String email;
    private String address;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
