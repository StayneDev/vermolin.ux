package com.vermolinux.repository;

import com.vermolinux.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Interface de repositório para Product
 * TODO: Estender JpaRepository quando integrar com banco
 */
public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    Optional<Product> findByCode(String code);
    List<Product> findAll();
    List<Product> findByActive(Boolean active);
    List<Product> findLowStockProducts();
    void deleteById(Long id);
    boolean existsByCode(String code);
}
