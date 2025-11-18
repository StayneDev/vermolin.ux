package com.vermolinux.controller;

import com.vermolinux.dto.ApiResponse;
import com.vermolinux.dto.ProductRequest;
import com.vermolinux.dto.ProductResponse;
import com.vermolinux.model.User;
import com.vermolinux.service.ProductService;
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
 * Controller de Produtos
 * 
 * RF9: Caixa visualiza apenas nome, quantidade e preço
 * RF10: Gerente/Estoquista visualizam informações completas
 * RF22: Cadastrar produto (GERENTE, ESTOQUISTA)
 * RF23: Visualizar lista de produtos (todos)
 * RF24: Editar informações de produto (GERENTE, ESTOQUISTA)
 * RF25: Excluir produto (GERENTE, ESTOQUISTA)
 * RF34: Notificar produtos com estoque baixo
 */
@RestController
@RequestMapping("/products")
@Tag(name = "Produtos", description = "Gerenciamento de produtos")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    /**
     * RF22: Cadastrar produto (GERENTE, ESTOQUISTA)
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Cadastrar produto", description = "Cadastrar novo produto (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<ProductResponse>> create(
            @Valid @RequestBody ProductRequest request,
            Authentication authentication) {
        
        Long createdBy = Long.parseLong(authentication.getName());
        ProductResponse response = productService.create(request, createdBy);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Produto cadastrado com sucesso", response));
    }
    
    /**
     * RF23: Visualizar lista de produtos (todos os usuários)
     * RF9: Caixa vê apenas nome, quantidade e preço
     * RF10: Gerente/Estoquista veem informações completas
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA', 'CAIXA')")
    @Operation(summary = "Listar produtos", description = "Listar todos os produtos (dados filtrados por role)")
    public ResponseEntity<ApiResponse<List<?>>> findAll(Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        User.UserRole userRole = User.UserRole.valueOf(role);
        List<?> response = productService.findAll(userRole);
        
        return ResponseEntity.ok(ApiResponse.success("Produtos recuperados com sucesso", response));
    }
    
    /**
     * Buscar produto por ID
     * RF9/RF10: Dados filtrados por role
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA', 'CAIXA')")
    @Operation(summary = "Buscar produto", description = "Buscar produto por ID (dados filtrados por role)")
    public ResponseEntity<ApiResponse<?>> findById(
            @PathVariable Long id,
            Authentication authentication) {
        
        String role = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        User.UserRole userRole = User.UserRole.valueOf(role);
        
        Object response;
        if (userRole == User.UserRole.CAIXA) {
            response = productService.findByIdForCashier(id);
        } else {
            response = productService.findById(id);
        }
        
        return ResponseEntity.ok(ApiResponse.success("Produto encontrado", response));
    }
    
    /**
     * RF24: Editar informações de produto (GERENTE, ESTOQUISTA)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Atualizar produto", description = "Atualizar informações de produto (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request,
            Authentication authentication) {
        
        Long updatedBy = Long.parseLong(authentication.getName());
        ProductResponse response = productService.update(id, request, updatedBy);
        
        return ResponseEntity.ok(ApiResponse.success("Produto atualizado com sucesso", response));
    }
    
    /**
     * RF25: Excluir produto (GERENTE, ESTOQUISTA)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Excluir produto", description = "Excluir produto do sistema (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            Authentication authentication) {
        
        Long deletedBy = Long.parseLong(authentication.getName());
        productService.delete(id, deletedBy);
        
        return ResponseEntity.ok(ApiResponse.<Void>success("Produto excluído com sucesso", null));
    }
    
    /**
     * RF34: Notificar produtos com estoque baixo (GERENTE, ESTOQUISTA)
     */
    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('GERENTE', 'ESTOQUISTA')")
    @Operation(summary = "Produtos com estoque baixo", description = "Listar produtos que precisam reposição (GERENTE ou ESTOQUISTA)")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> findLowStock() {
        List<ProductResponse> response = productService.findLowStockProducts();
        
        return ResponseEntity.ok(ApiResponse.success(
                String.format("%d produto(s) com estoque baixo", response.size()),
                response
        ));
    }
}


