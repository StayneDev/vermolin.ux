package com.vermolinux.controller;

import com.vermolinux.dto.*;
import com.vermolinux.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller de Vendas
 * 
 * RF11: Abrir transação de venda (CAIXA)
 * RF12: Adicionar produtos à venda (CAIXA)
 * RF13: Remover produtos da venda (CAIXA)
 * RF14: Pesar produto (CAIXA)
 * RF15: Cancelar venda (CAIXA, GERENTE)
 * RF16: Registrar forma de pagamento (CAIXA)
 * RF17: Calcular troco automaticamente (CAIXA)
 * RF18: Registrar venda e atualizar estoque (CAIXA)
 * RF7: Histórico de vendas (GERENTE)
 */
@RestController
@RequestMapping("/sales")
@Tag(name = "Vendas", description = "Gerenciamento de vendas e PDV (Ponto de Venda)")
@SecurityRequirement(name = "bearerAuth")
public class SaleController {
    
    private final SaleService saleService;
    
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }
    
    /**
     * RF11: Abrir nova transação de venda (CAIXA)
     */
    @PostMapping
    @PreAuthorize("hasRole('CAIXA')")
    @Operation(summary = "Abrir venda", description = "Iniciar nova transação de venda (apenas CAIXA)")
    public ResponseEntity<ApiResponse<SaleResponse>> createSale(Authentication authentication) {
        Long cashierId = Long.parseLong(authentication.getName());
        SaleResponse response = saleService.createSale(cashierId);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Venda aberta com sucesso", response));
    }
    
    /**
     * RF12: Adicionar produto à venda (CAIXA)
     * RF14: Pesar produto quando aplicável
     */
    @PostMapping("/{saleId}/items")
    @PreAuthorize("hasRole('CAIXA')")
    @Operation(summary = "Adicionar item", description = "Adicionar produto à venda (apenas CAIXA)")
    public ResponseEntity<ApiResponse<SaleResponse>> addItem(
            @PathVariable Long saleId,
            @Valid @RequestBody AddSaleItemRequest request) {
        
        SaleResponse response = saleService.addItem(saleId, request);
        
        return ResponseEntity.ok(ApiResponse.success("Item adicionado à venda", response));
    }
    
    /**
     * RF13: Remover produto da venda (CAIXA)
     */
    @DeleteMapping("/{saleId}/items/{itemId}")
    @PreAuthorize("hasRole('CAIXA')")
    @Operation(summary = "Remover item", description = "Remover produto da venda (apenas CAIXA)")
    public ResponseEntity<ApiResponse<SaleResponse>> removeItem(
            @PathVariable Long saleId,
            @PathVariable Long itemId) {
        
        SaleResponse response = saleService.removeItem(saleId, itemId);
        
        return ResponseEntity.ok(ApiResponse.success("Item removido da venda", response));
    }
    
    /**
     * RF15: Cancelar venda (CAIXA, GERENTE)
     */
    @PostMapping("/{saleId}/cancel")
    @PreAuthorize("hasAnyRole('CAIXA', 'GERENTE')")
    @Operation(summary = "Cancelar venda", description = "Cancelar venda antes da finalização (CAIXA ou GERENTE)")
    public ResponseEntity<ApiResponse<Void>> cancelSale(
            @PathVariable Long saleId,
            @RequestParam(required = false) String reason,
            Authentication authentication) {
        
        Long cancelledBy = Long.parseLong(authentication.getName());
        saleService.cancelSale(saleId, cancelledBy, reason);
        
        return ResponseEntity.ok(ApiResponse.<Void>success("Venda cancelada com sucesso", null));
    }
    
    /**
     * RF16: Registrar forma de pagamento (CAIXA)
     * RF17: Calcular troco automaticamente
     * RF18: Registrar venda e atualizar estoque
     */
    @PostMapping("/{saleId}/finalize")
    @PreAuthorize("hasRole('CAIXA')")
    @Operation(summary = "Finalizar venda", description = "Finalizar venda com pagamento e atualizar estoque (apenas CAIXA)")
    public ResponseEntity<ApiResponse<SaleResponse>> finalizeSale(
            @PathVariable Long saleId,
            @Valid @RequestBody PaymentRequest request) {
        
        SaleResponse response = saleService.finalizeSale(saleId, request);
        
        return ResponseEntity.ok(ApiResponse.success(
                String.format("Venda finalizada - Total: R$ %.2f - Troco: R$ %.2f",
                        response.getTotalAmount(),
                        response.getChangeAmount()),
                response
        ));
    }
    
    /**
     * Buscar venda por ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA', 'CAIXA')")
    @Operation(summary = "Buscar venda", description = "Buscar venda por ID")
    public ResponseEntity<ApiResponse<SaleResponse>> findById(@PathVariable Long id) {
        SaleResponse response = saleService.findById(id);
        
        return ResponseEntity.ok(ApiResponse.success("Venda encontrada", response));
    }
    
    /**
     * RF7: Histórico de vendas (GERENTE)
     */
    @GetMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Histórico de vendas", description = "Listar todas as vendas (apenas GERENTE)")
    public ResponseEntity<ApiResponse<List<SaleResponse>>> findAll() {
        List<SaleResponse> response = saleService.findAll();
        
        return ResponseEntity.ok(ApiResponse.success(
                String.format("%d venda(s) encontrada(s)", response.size()),
                response
        ));
    }
    
    /**
     * Vendas de um caixa específico
     */
    @GetMapping("/cashier/{cashierId}")
    @PreAuthorize("hasAnyRole('GERENTE', 'CAIXA')")
    @Operation(summary = "Vendas por caixa", description = "Listar vendas de um caixa específico")
    public ResponseEntity<ApiResponse<List<SaleResponse>>> findByCashier(
            @PathVariable Long cashierId,
            Authentication authentication) {
        
        // CAIXA só pode ver suas próprias vendas
        Long userId = Long.parseLong(authentication.getName());
        boolean isCashier = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CAIXA"));
        
        if (isCashier && !userId.equals(cashierId)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error("Você só pode visualizar suas próprias vendas"));
        }
        
        List<SaleResponse> response = saleService.findByCashier(cashierId);
        
        return ResponseEntity.ok(ApiResponse.success(
                String.format("%d venda(s) do caixa", response.size()),
                response
        ));
    }
}


