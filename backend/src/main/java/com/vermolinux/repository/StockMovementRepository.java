package com.vermolinux.repository;

import com.vermolinux.model.StockMovement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface de repositório para StockMovement
 * TODO: Estender JpaRepository quando integrar com banco
 */
public interface StockMovementRepository {
    StockMovement save(StockMovement movement);
    Optional<StockMovement> findById(Long id);
    List<StockMovement> findAll();
    List<StockMovement> findByProductId(Long productId);
    List<StockMovement> findByProductIdOrderByCreatedAtDesc(Long productId);
    List<StockMovement> findByCreatedBy(Long userId);
    List<StockMovement> findByCreatedByOrderByCreatedAtDesc(Long userId);
    List<StockMovement> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    void deleteById(Long id);
}
