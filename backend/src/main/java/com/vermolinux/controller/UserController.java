package com.vermolinux.controller;

import com.vermolinux.dto.ApiResponse;
import com.vermolinux.dto.UserRequest;
import com.vermolinux.dto.UserResponse;
import com.vermolinux.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller de Usuários
 * 
 * RF26: Cadastrar usuário (apenas GERENTE)
 * RF27: Visualizar lista de usuários (apenas GERENTE)
 * RF28: Editar informações de usuário (apenas GERENTE)
 * RF29: Excluir usuário (apenas GERENTE)
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    
    private final UserService userService;
    
    /**
     * RF26: Cadastrar usuário (apenas GERENTE)
     */
    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Cadastrar usuário", description = "Cadastrar novo usuário no sistema (apenas GERENTE)")
    public ResponseEntity<ApiResponse<UserResponse>> create(
            @Valid @RequestBody UserRequest request,
            Authentication authentication) {
        
        Long createdBy = Long.parseLong(authentication.getName()); // user ID from JWT
        UserResponse response = userService.create(request, createdBy);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuário cadastrado com sucesso", response));
    }
    
    /**
     * RF27: Visualizar lista de usuários (apenas GERENTE)
     */
    @GetMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Listar usuários", description = "Listar todos os usuários (apenas GERENTE)")
    public ResponseEntity<ApiResponse<List<UserResponse>>> findAll() {
        List<UserResponse> response = userService.findAll();
        
        return ResponseEntity.ok(ApiResponse.success("Usuários recuperados com sucesso", response));
    }
    
    /**
     * Buscar usuário por ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Buscar usuário", description = "Buscar usuário por ID (apenas GERENTE)")
    public ResponseEntity<ApiResponse<UserResponse>> findById(@PathVariable Long id) {
        UserResponse response = userService.findById(id);
        
        return ResponseEntity.ok(ApiResponse.success("Usuário encontrado", response));
    }
    
    /**
     * RF28: Editar informações de usuário (apenas GERENTE)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Atualizar usuário", description = "Atualizar informações de usuário (apenas GERENTE)")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request,
            Authentication authentication) {
        
        Long updatedBy = Long.parseLong(authentication.getName());
        UserResponse response = userService.update(id, request, updatedBy);
        
        return ResponseEntity.ok(ApiResponse.success("Usuário atualizado com sucesso", response));
    }
    
    /**
     * RF29: Excluir usuário (apenas GERENTE)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Excluir usuário", description = "Excluir usuário do sistema (apenas GERENTE)")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            Authentication authentication) {
        
        Long deletedBy = Long.parseLong(authentication.getName());
        userService.delete(id, deletedBy);
        
        return ResponseEntity.ok(ApiResponse.<Void>success("Usuário excluído com sucesso", null));
    }
}
