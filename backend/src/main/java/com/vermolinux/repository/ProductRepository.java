package com.vermolinux.repository;

import com.vermolinux.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository Spring Data JPA para Product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findByCode(String code);
    
    List<Product> findByActiveTrue();
    
    List<Product> findByActive(boolean active);
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity <= p.minStock AND p.active = true")
    List<Product> findLowStockProducts();
    
    boolean existsByCode(String code);
}


