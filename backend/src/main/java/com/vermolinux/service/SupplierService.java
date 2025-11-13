package com.vermolinux.service;

import com.vermolinux.dto.SupplierRequest;
import com.vermolinux.dto.SupplierResponse;
import com.vermolinux.exception.BusinessException;
import com.vermolinux.exception.ResourceNotFoundException;
import com.vermolinux.model.Supplier;
import com.vermolinux.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de Fornecedores
 * 
 * Implementa:
 * - RF30: Cadastrar fornecedor
 * - RF31: Visualizar lista de fornecedores
 * - RF32: Editar informações de fornecedor
 * - RF33: Excluir fornecedor
 * - RF35: Visualizar contato do fornecedor (ao consultar produtos com estoque baixo)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierService {
    
    private final SupplierRepository supplierRepository;
    
    /**
     * RF30: Cadastrar novo fornecedor
     */
    public SupplierResponse create(SupplierRequest request, Long createdBy) {
        log.info("Cadastrando novo fornecedor: {}", request.getName());
        
        // Validar se já existe fornecedor com mesmo CNPJ
        if (request.getCnpj() != null && !request.getCnpj().isBlank()) {
            supplierRepository.findByCnpj(request.getCnpj()).ifPresent(existing -> {
                throw new BusinessException("Já existe um fornecedor cadastrado com este CNPJ");
            });
        }
        
        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .cnpj(request.getCnpj())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .active(true)
                .createdBy(createdBy)
                .build();
        
        supplier = supplierRepository.save(supplier);
        
        log.info("Fornecedor cadastrado: ID {}", supplier.getId());
        
        return mapToResponse(supplier);
    }
    
    /**
     * RF31: Visualizar lista de fornecedores
     */
    public List<SupplierResponse> findAll() {
        log.info("Buscando todos os fornecedores");
        
        return supplierRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * RF31: Visualizar apenas fornecedores ativos
     */
    public List<SupplierResponse> findAllActive() {
        log.info("Buscando fornecedores ativos");
        
        return supplierRepository.findByActiveTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * RF35: Buscar fornecedor por ID (usado ao visualizar produtos com estoque baixo)
     */
    public SupplierResponse findById(Long id) {
        log.info("Buscando fornecedor ID: {}", id);
        
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor", "id", id));
        
        return mapToResponse(supplier);
    }
    
    /**
     * RF32: Editar informações de fornecedor
     */
    public SupplierResponse update(Long id, SupplierRequest request, Long updatedBy) {
        log.info("Atualizando fornecedor ID: {}", id);
        
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor", "id", id));
        
        // Validar CNPJ duplicado (exceto o próprio fornecedor)
        if (request.getCnpj() != null && !request.getCnpj().isBlank()) {
            supplierRepository.findByCnpj(request.getCnpj()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new BusinessException("Já existe outro fornecedor cadastrado com este CNPJ");
                }
            });
        }
        
        supplier.setName(request.getName());
        supplier.setCnpj(request.getCnpj());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setAddress(request.getAddress());
        supplier.setUpdatedBy(updatedBy);
        supplier.setUpdatedAt(LocalDateTime.now());
        
        supplier = supplierRepository.save(supplier);
        
        log.info("Fornecedor {} atualizado", id);
        
        return mapToResponse(supplier);
    }
    
    /**
     * RF33: Excluir fornecedor (inativação lógica)
     */
    public void delete(Long id, Long deletedBy) {
        log.info("Inativando fornecedor ID: {}", id);
        
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor", "id", id));
        
        // Inativação lógica para manter histórico
        supplier.setActive(false);
        supplier.setUpdatedBy(deletedBy);
        supplier.setUpdatedAt(LocalDateTime.now());
        
        supplierRepository.save(supplier);
        
        log.info("Fornecedor {} inativado", id);
    }
    
    /**
     * RF33: Reativar fornecedor
     */
    public SupplierResponse reactivate(Long id, Long reactivatedBy) {
        log.info("Reativando fornecedor ID: {}", id);
        
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor", "id", id));
        
        supplier.setActive(true);
        supplier.setUpdatedBy(reactivatedBy);
        supplier.setUpdatedAt(LocalDateTime.now());
        
        supplier = supplierRepository.save(supplier);
        
        log.info("Fornecedor {} reativado", id);
        
        return mapToResponse(supplier);
    }
    
    private SupplierResponse mapToResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .cnpj(supplier.getCnpj())
                .phone(supplier.getPhone())
                .email(supplier.getEmail())
                .address(supplier.getAddress())
                .active(supplier.getActive())
                .createdAt(supplier.getCreatedAt())
                .updatedAt(supplier.getUpdatedAt())
                .build();
    }
}
