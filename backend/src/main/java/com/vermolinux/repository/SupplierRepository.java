package com.vermolinux.repository;

import com.vermolinux.model.Supplier;
import java.util.List;
import java.util.Optional;

/**
 * Interface de repositório para Supplier
 * TODO: Estender JpaRepository quando integrar com banco
 */
public interface SupplierRepository {
    Supplier save(Supplier supplier);
    Optional<Supplier> findById(Long id);
    Optional<Supplier> findByCnpj(String cnpj);
    List<Supplier> findAll();
    List<Supplier> findByActive(Boolean active);
    List<Supplier> findByActiveTrue();
    void deleteById(Long id);
    boolean existsByCnpj(String cnpj);
}
