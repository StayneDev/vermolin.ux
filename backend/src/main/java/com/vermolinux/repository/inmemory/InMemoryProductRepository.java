package com.vermolinux.repository.inmemory;

import com.vermolinux.model.Product;
import com.vermolinux.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementação in-memory do repositório de Product
 * 
 * TODO: Substituir por implementação JPA quando integrar com banco de dados
 */
@Repository
public class InMemoryProductRepository implements ProductRepository {
    
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @PostConstruct
    public void init() {
        // Produtos de exemplo
        save(Product.builder()
                .code("001")
                .name("Banana Prata")
                .description("Banana prata in natura")
                .price(new BigDecimal("5.99"))
                .unit(Product.ProductUnit.KG)
                .stockQuantity(new BigDecimal("50.500"))
                .minStock(new BigDecimal("10.000"))
                .supplierId(1L)
                .expiryDate(LocalDate.now().plusDays(7))
                .requiresWeighing(true)
                .active(true)
                .build());
        
        save(Product.builder()
                .code("002")
                .name("Tomate")
                .description("Tomate orgânico")
                .price(new BigDecimal("8.90"))
                .unit(Product.ProductUnit.KG)
                .stockQuantity(new BigDecimal("30.000"))
                .minStock(new BigDecimal("5.000"))
                .supplierId(1L)
                .expiryDate(LocalDate.now().plusDays(5))
                .requiresWeighing(true)
                .active(true)
                .build());
        
        save(Product.builder()
                .code("003")
                .name("Alface Crespa")
                .description("Alface crespa fresca")
                .price(new BigDecimal("3.50"))
                .unit(Product.ProductUnit.UNIDADE)
                .stockQuantity(new BigDecimal("20.000"))
                .minStock(new BigDecimal("5.000"))
                .supplierId(1L)
                .expiryDate(LocalDate.now().plusDays(3))
                .requiresWeighing(false)
                .active(true)
                .build());
        
        save(Product.builder()
                .code("004")
                .name("Laranja")
                .description("Laranja pera")
                .price(new BigDecimal("4.50"))
                .unit(Product.ProductUnit.KG)
                .stockQuantity(new BigDecimal("3.000"))
                .minStock(new BigDecimal("10.000"))
                .supplierId(1L)
                .expiryDate(LocalDate.now().plusDays(10))
                .requiresWeighing(true)
                .active(true)
                .build()); // Este produto está com estoque baixo
        
        save(Product.builder()
                .code("005")
                .name("Ovos Brancos")
                .description("Ovos brancos - dúzia")
                .price(new BigDecimal("12.90"))
                .unit(Product.ProductUnit.DUZIA)
                .stockQuantity(new BigDecimal("15.000"))
                .minStock(new BigDecimal("5.000"))
                .supplierId(1L)
                .expiryDate(LocalDate.now().plusDays(20))
                .requiresWeighing(false)
                .active(true)
                .build());
        
        System.out.println("✅ " + products.size() + " produtos de exemplo criados");
    }
    
    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(idGenerator.getAndIncrement());
        }
        products.put(product.getId(), product);
        return product;
    }
    
    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }
    
    @Override
    public Optional<Product> findByCode(String code) {
        return products.values().stream()
                .filter(p -> p.getCode().equals(code))
                .findFirst();
    }
    
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
    
    @Override
    public List<Product> findByActive(Boolean active) {
        return products.values().stream()
                .filter(p -> p.getActive().equals(active))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Product> findLowStockProducts() {
        return products.values().stream()
                .filter(Product::isLowStock)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        products.remove(id);
    }
    
    @Override
    public boolean existsByCode(String code) {
        return products.values().stream()
                .anyMatch(p -> p.getCode().equals(code));
    }
}
