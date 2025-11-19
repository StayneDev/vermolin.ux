package com.vermolinux.repository;

import com.vermolinux.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository Spring Data JPA para Supplier
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    
    Optional<Supplier> findByCnpj(String cnpj);
    
    List<Supplier> findByActiveTrue();
    
    boolean existsByCnpj(String cnpj);
}


