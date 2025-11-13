package com.vermolinux.repository;

import com.vermolinux.model.Sale;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface de repositório para Sale
 * TODO: Estender JpaRepository quando integrar com banco
 */
public interface SaleRepository {
    Sale save(Sale sale);
    Optional<Sale> findById(Long id);
    List<Sale> findAll();
    List<Sale> findByStatus(Sale.SaleStatus status);
    List<Sale> findByCashierId(Long cashierId);
    List<Sale> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    void deleteById(Long id);
}
