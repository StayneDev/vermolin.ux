package com.vermolinux.controller;

import com.vermolinux.dto.ApiResponse;
import com.vermolinux.dto.StockMovementRequest;
import com.vermolinux.dto.StockMovementResponse;
import com.vermolinux.service.StockService;
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
 * Controller de Estoque
 * 
 * RF6: Registrar rastreabilidade completa (quem e quando)
 * RF8: Validar quantidade disponível
 * RF19: Registrar entrada de estoque (GERENTE, ESTOQUISTA)
 * RF20: Registrar saída de estoque (GERENTE, ESTOQUISTA)
 * RF21: Registrar ajuste manual de estoque (GERENTE, ESTOQUISTA)
 */
@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@Tag(name = "Estoque", description = "Gerenciamento de movimentações de estoque")
@SecurityRequirement(name = "bearerAuth")
public class StockController {
    
    private final StockService stockService;
    
    /**
     * RF19: Registrar entrada de estoque (GERENTE, ESTOQUISTA)
     */
    @PostMapping("/entry")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Registrar entrada", description = "Registrar entrada de estoque (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<StockMovementResponse>> registerEntry(
            @Valid @RequestBody StockMovementRequest request,
            Authentication authentication) {
        
        Long userId = Long.parseLong(authentication.getName());
        StockMovementResponse response = stockService.registerEntry(request, userId);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Entrada de estoque registrada com sucesso", response));
    }
    
    /**
     * RF20: Registrar saída de estoque - perdas, vencimento (GERENTE, ESTOQUISTA)
     */
    @PostMapping("/exit")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Registrar saída", description = "Registrar saída de estoque - perdas, vencimento (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<StockMovementResponse>> registerExit(
            @Valid @RequestBody StockMovementRequest request,
            Authentication authentication) {
        
        Long userId = Long.parseLong(authentication.getName());
        StockMovementResponse response = stockService.registerExit(request, userId);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Saída de estoque registrada com sucesso", response));
    }
    
    /**
     * RF21: Registrar ajuste manual de estoque (GERENTE, ESTOQUISTA)
     */
    @PostMapping("/adjustment")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Ajustar estoque", description = "Registrar ajuste manual de estoque (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<StockMovementResponse>> registerAdjustment(
            @Valid @RequestBody StockMovementRequest request,
            Authentication authentication) {
        
        Long userId = Long.parseLong(authentication.getName());
        StockMovementResponse response = stockService.registerAdjustment(request, userId);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Ajuste de estoque registrado com sucesso", response));
    }
    
    /**
     * RF6: Histórico completo de movimentações (auditoria)
     */
    @GetMapping("/movements")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Histórico de movimentações", description = "Listar todas as movimentações de estoque (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<List<StockMovementResponse>>> findAll() {
        List<StockMovementResponse> response = stockService.findAll();
        
        return ResponseEntity.ok(ApiResponse.success("Movimentações recuperadas com sucesso", response));
    }
    
    /**
     * RF6: Histórico de movimentações de um produto específico
     */
    @GetMapping("/movements/product/{productId}")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Histórico por produto", description = "Listar movimentações de um produto específico (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<List<StockMovementResponse>>> findByProduct(@PathVariable Long productId) {
        List<StockMovementResponse> response = stockService.findByProduct(productId);
        
        return ResponseEntity.ok(ApiResponse.success(
                String.format("Histórico do produto: %d movimentação(ões)", response.size()),
                response
        ));
    }
    
    /**
     * RF6: Movimentações por usuário (auditoria)
     */
    @GetMapping("/movements/user/{userId}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Movimentações por usuário", description = "Listar movimentações realizadas por um usuário (apenas GERENTE)")
    public ResponseEntity<ApiResponse<List<StockMovementResponse>>> findByUser(@PathVariable Long userId) {
        List<StockMovementResponse> response = stockService.findByUser(userId);
        
        return ResponseEntity.ok(ApiResponse.success(
                String.format("Movimentações do usuário: %d registro(s)", response.size()),
                response
        ));
    }
}
