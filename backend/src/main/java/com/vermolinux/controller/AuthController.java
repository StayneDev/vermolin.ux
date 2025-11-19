package com.vermolinux.controller;

import com.vermolinux.dto.ApiResponse;
import com.vermolinux.dto.LoginRequest;
import com.vermolinux.dto.LoginResponse;
import com.vermolinux.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller de Autenticação
 * 
 * Endpoints públicos (não requerem autenticação):
 * - POST /api/auth/login - RF1, RF2, RF3
 * - POST /api/auth/logout
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Gerenciamento de autenticação e login")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    /**
     * RF1: Autenticar usuário no sistema
     * RF2: Retornar JWT token
     * RF3: Identificar tipo de usuário (GERENTE, ESTOQUISTA, CAIXA)
     */
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autenticar usuário e obter token JWT")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        
        return ResponseEntity.ok(ApiResponse.success(
                "Login realizado com sucesso",
                response
        ));
    }
    
    /**
     * Logout (informativo - JWT é stateless)
     */
    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Desconectar usuário (cliente deve descartar o token)")
    public ResponseEntity<ApiResponse<Void>> logout() {
        return ResponseEntity.ok(ApiResponse.<Void>success(
                "Logout realizado com sucesso. Descarte o token JWT.",
                null
        ));
    }
}


