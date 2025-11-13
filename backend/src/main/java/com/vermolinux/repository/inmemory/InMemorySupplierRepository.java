package com.vermolinux.repository.inmemory;

import com.vermolinux.model.Supplier;
import com.vermolinux.repository.SupplierRepository;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementação in-memory do repositório de Supplier
 * 
 * TODO: Substituir por implementação JPA quando integrar com banco de dados
 */
@Repository
public class InMemorySupplierRepository implements SupplierRepository {
    
    private final Map<Long, Supplier> suppliers = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @PostConstruct
    public void init() {
        // Fornecedores de exemplo
        save(Supplier.builder()
                .name("Frutas Silva LTDA")
                .cnpj("12345678000190")
                .contactName("Carlos Silva")
                .phone("11987654321")
                .email("contato@frutassilva.com.br")
                .address("Rua das Frutas, 123 - CEAGESP - São Paulo/SP")
                .active(true)
                .build());
        
        save(Supplier.builder()
                .name("Hortifruti Bom Jardim")
                .cnpj("98765432000100")
                .contactName("Maria Oliveira")
                .phone("11912345678")
                .email("maria@bomjardim.com.br")
                .address("Av. dos Verduras, 456 - Guarulhos/SP")
                .active(true)
                .build());
        
        save(Supplier.builder()
                .name("Distribuidora Verde Vale")
                .cnpj("11223344000155")
                .contactName("João Santos")
                .phone("11998877665")
                .email("joao@verdevale.com.br")
                .address("Rodovia dos Agricultores, Km 12 - Mogi das Cruzes/SP")
                .active(true)
                .build());
        
        System.out.println("✅ " + suppliers.size() + " fornecedores de exemplo criados");
    }
    
    @Override
    public Supplier save(Supplier supplier) {
        if (supplier.getId() == null) {
            supplier.setId(idGenerator.getAndIncrement());
        }
        suppliers.put(supplier.getId(), supplier);
        return supplier;
    }
    
    @Override
    public Optional<Supplier> findById(Long id) {
        return Optional.ofNullable(suppliers.get(id));
    }
    
    @Override
    public Optional<Supplier> findByCnpj(String cnpj) {
        return suppliers.values().stream()
                .filter(s -> s.getCnpj().equals(cnpj))
                .findFirst();
    }
    
    @Override
    public List<Supplier> findAll() {
        return new ArrayList<>(suppliers.values());
    }
    
    @Override
    public List<Supplier> findByActive(Boolean active) {
        return suppliers.values().stream()
                .filter(s -> s.getActive().equals(active))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Supplier> findByActiveTrue() {
        return findByActive(true);
    }
    
    @Override
    public void deleteById(Long id) {
        suppliers.remove(id);
    }
    
    @Override
    public boolean existsByCnpj(String cnpj) {
        return suppliers.values().stream()
                .anyMatch(s -> s.getCnpj().equals(cnpj));
    }
}
