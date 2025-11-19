package com.vermolinux.controller;

import com.vermolinux.dto.ApiResponse;
import com.vermolinux.dto.SupplierRequest;
import com.vermolinux.dto.SupplierResponse;
import com.vermolinux.service.SupplierService;
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
 * Controller de Fornecedores
 * 
 * RF30: Cadastrar fornecedor (GERENTE, ESTOQUISTA)
 * RF31: Visualizar lista de fornecedores (GERENTE, ESTOQUISTA)
 * RF32: Editar informações de fornecedor (GERENTE, ESTOQUISTA)
 * RF33: Excluir fornecedor (GERENTE, ESTOQUISTA)
 * RF35: Visualizar contato do fornecedor
 */
@RestController
@RequestMapping("/suppliers")
@Tag(name = "Fornecedores", description = "Gerenciamento de fornecedores")
@SecurityRequirement(name = "bearerAuth")
public class SupplierController {
    
    private final SupplierService supplierService;
    
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }
    
    /**
     * RF30: Cadastrar fornecedor (GERENTE, ESTOQUISTA)
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Cadastrar fornecedor", description = "Cadastrar novo fornecedor (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<SupplierResponse>> create(
            @Valid @RequestBody SupplierRequest request,
            Authentication authentication) {
        
        Long createdBy = Long.parseLong(authentication.getName());
        SupplierResponse response = supplierService.create(request, createdBy);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Fornecedor cadastrado com sucesso", response));
    }
    
    /**
     * RF31: Visualizar lista de fornecedores (GERENTE, ESTOQUISTA)
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Listar fornecedores", description = "Listar todos os fornecedores (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<List<SupplierResponse>>> findAll(
            @RequestParam(required = false, defaultValue = "false") Boolean activeOnly) {
        
        List<SupplierResponse> response = activeOnly 
                ? supplierService.findAllActive() 
                : supplierService.findAll();
        
        return ResponseEntity.ok(ApiResponse.success("Fornecedores recuperados com sucesso", response));
    }
    
    /**
     * RF35: Buscar fornecedor por ID (visualizar contato)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Buscar fornecedor", description = "Buscar fornecedor por ID (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<SupplierResponse>> findById(@PathVariable Long id) {
        SupplierResponse response = supplierService.findById(id);
        
        return ResponseEntity.ok(ApiResponse.success("Fornecedor encontrado", response));
    }
    
    /**
     * RF32: Editar informações de fornecedor (GERENTE, ESTOQUISTA)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Atualizar fornecedor", description = "Atualizar informações de fornecedor (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<SupplierResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody SupplierRequest request,
            Authentication authentication) {
        
        Long updatedBy = Long.parseLong(authentication.getName());
        SupplierResponse response = supplierService.update(id, request, updatedBy);
        
        return ResponseEntity.ok(ApiResponse.success("Fornecedor atualizado com sucesso", response));
    }
    
    /**
     * RF33: Excluir fornecedor (GERENTE, ESTOQUISTA)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Excluir fornecedor", description = "Inativar fornecedor (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            Authentication authentication) {
        
        Long deletedBy = Long.parseLong(authentication.getName());
        supplierService.delete(id, deletedBy);
        
        return ResponseEntity.ok(ApiResponse.<Void>success("Fornecedor inativado com sucesso", null));
    }
    
    /**
     * Reativar fornecedor
     */
    @PatchMapping("/{id}/reactivate")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Reativar fornecedor", description = "Reativar fornecedor inativo (apenas GERENTE)")
    public ResponseEntity<ApiResponse<SupplierResponse>> reactivate(
            @PathVariable Long id,
            Authentication authentication) {
        
        Long reactivatedBy = Long.parseLong(authentication.getName());
        SupplierResponse response = supplierService.reactivate(id, reactivatedBy);
        
        return ResponseEntity.ok(ApiResponse.success("Fornecedor reativado com sucesso", response));
    }
}


