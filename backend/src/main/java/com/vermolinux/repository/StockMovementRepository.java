package com.vermolinux.repository;

import com.vermolinux.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository Spring Data JPA para StockMovement
 */
@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    
    List<StockMovement> findByProductIdOrderByCreatedAtDesc(Long productId);
    
    List<StockMovement> findByCreatedByOrderByCreatedAtDesc(Long createdBy);
    
    List<StockMovement> findAllByOrderByCreatedAtDesc();
}


