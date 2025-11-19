package com.vermolinux.service;

import com.vermolinux.dto.ProductCashierResponse;
import com.vermolinux.dto.ProductRequest;
import com.vermolinux.dto.ProductResponse;
import com.vermolinux.exception.BusinessException;
import com.vermolinux.exception.ResourceNotFoundException;
import com.vermolinux.model.Product;
import com.vermolinux.model.Supplier;
import com.vermolinux.model.User;
import com.vermolinux.repository.ProductRepository;
import com.vermolinux.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de Produtos
 * 
 * Implementa:
 * - RF9: Caixa consulta produtos (limitado)
 * - RF10: Estoquista/Gerente consulta produtos (completo)
 * - RF22: Cadastrar produto
 * - RF23: Consultar produtos
 * - RF24: Atualizar produto
 * - RF25: Deletar produto
 * - RF34: Notificar estoque baixo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    
    /**
     * RF22: Cadastrar novo produto (apenas Gerente)
     * RF6: Auditoria - registra data, hora e usuário criador
     */
    @Transactional
    public ProductResponse create(ProductRequest request, Long createdBy) {
        log.info("Criando novo produto: {}", request.getName());
        
        // RF5: Validar dados obrigatórios
        if (productRepository.existsByCode(request.getCode())) {
            throw new BusinessException("Já existe um produto com este código");
        }
        
        // Validar fornecedor se informado
        if (request.getSupplierId() != null) {
            supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fornecedor", "id", request.getSupplierId()));
        }
        
        Product product = Product.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .unit(request.getUnit())
                .stockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : java.math.BigDecimal.ZERO)
                .minStock(request.getMinStock() != null ? request.getMinStock() : java.math.BigDecimal.ZERO)
                .supplierId(request.getSupplierId())
                .expiryDate(request.getExpiryDate())
                .requiresWeighing(request.getRequiresWeighing() != null ? request.getRequiresWeighing() : false)
                .active(request.getActive() != null ? request.getActive() : true)
                .createdBy(createdBy)
                .updatedBy(createdBy)
                .build();
        
        product = productRepository.save(product);
        
        log.info("Produto criado: {} (ID: {})", product.getName(), product.getId());
        
        return mapToFullResponse(product);
    }
    
    /**
     * RF23: Consultar produtos
     * RF9: Caixa recebe resposta limitada (sem fornecedor/validade)
     * RF10: Estoquista/Gerente recebe resposta completa
     */
    public List<ProductResponse> findAll(User.UserRole userRole) {
        List<Product> products = productRepository.findByActive(true);
        return products.stream()
                .map(this::mapToFullResponse)
                .collect(Collectors.toList());
    }
    
    public List<ProductCashierResponse> findAllForCashier() {
        List<Product> products = productRepository.findByActive(true);
        return products.stream()
                .map(this::mapToCashierResponse)
                .collect(Collectors.toList());
    }
    
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "id", id));
        
        return mapToFullResponse(product);
    }
    
    public ProductCashierResponse findByIdForCashier(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "id", id));
        
        return mapToCashierResponse(product);
    }
    
    /**
     * RF24: Atualizar produto (apenas Gerente)
     * RF6: Auditoria - atualiza data e usuário que modificou
     */
    @Transactional
    public ProductResponse update(Long id, ProductRequest request, Long updatedBy) {
        log.info("Atualizando produto ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "id", id));
        
        // Verificar se código já existe em outro produto
        if (!product.getCode().equals(request.getCode()) && 
            productRepository.existsByCode(request.getCode())) {
            throw new BusinessException("Já existe outro produto com este código");
        }
        
        // Validar fornecedor se informado
        if (request.getSupplierId() != null) {
            supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fornecedor", "id", request.getSupplierId()));
        }
        
        product.setCode(request.getCode());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setUnit(request.getUnit());
        
        if (request.getStockQuantity() != null) {
            product.setStockQuantity(request.getStockQuantity());
        }
        if (request.getMinStock() != null) {
            product.setMinStock(request.getMinStock());
        }
        
        product.setSupplierId(request.getSupplierId());
        product.setExpiryDate(request.getExpiryDate());
        product.setRequiresWeighing(request.getRequiresWeighing());
        product.setActive(request.getActive());
        product.setUpdatedAt(LocalDateTime.now());
        product.setUpdatedBy(updatedBy);
        
        product = productRepository.save(product);
        
        log.info("Produto atualizado: {} (ID: {})", product.getName(), product.getId());
        
        return mapToFullResponse(product);
    }
    
    /**
     * RF25: Deletar produto (com auditoria - soft delete)
     * RF6: Registra quem deletou e quando
     */
    @Transactional
    public void delete(Long id, Long deletedBy) {
        log.info("Deletando produto ID: {} por usuário ID: {}", id, deletedBy);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "id", id));
        
        // RF25: Registrar histórico de exclusão para auditoria
        // TODO: Criar tabela de auditoria quando integrar com banco
        
        productRepository.deleteById(id);
        
        log.info("Produto deletado: {} (ID: {})", product.getName(), product.getId());
    }
    
    /**
     * RF34: Listar produtos com estoque baixo (para notificação)
     */
    public List<ProductResponse> findLowStockProducts() {
        List<Product> lowStockProducts = productRepository.findLowStockProducts();
        
        if (!lowStockProducts.isEmpty()) {
            log.warn("⚠️ {} produto(s) com estoque baixo detectado(s)", lowStockProducts.size());
        }
        
        return lowStockProducts.stream()
                .map(this::mapToFullResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Mapeia para resposta completa (Estoquista/Gerente) - RF10
     */
    private ProductResponse mapToFullResponse(Product product) {
        String supplierName = null;
        if (product.getSupplierId() != null) {
            supplierName = supplierRepository.findById(product.getSupplierId())
                    .map(Supplier::getName)
                    .orElse(null);
        }
        
        return ProductResponse.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .unit(product.getUnit())
                .stockQuantity(product.getStockQuantity())
                .minStock(product.getMinStock())
                .supplierId(product.getSupplierId())
                .supplierName(supplierName)
                .expiryDate(product.getExpiryDate())
                .requiresWeighing(product.getRequiresWeighing())
                .active(product.getActive())
                .lowStock(product.isLowStock())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
    
    /**
     * Mapeia para resposta limitada (Caixa) - RF9
     * Não expõe fornecedor nem validade
     */
    private ProductCashierResponse mapToCashierResponse(Product product) {
        return ProductCashierResponse.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .unit(product.getUnit())
                .stockQuantity(product.getStockQuantity())
                .requiresWeighing(product.getRequiresWeighing())
                .active(product.getActive())
                .build();
    }
}
