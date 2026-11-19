package com.vermolinux.repository;

import com.vermolinux.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository Spring Data JPA para Sale
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    
    List<Sale> findByStatus(Sale.SaleStatus status);
    
    List<Sale> findByCashierId(Long cashierId);
    
    List<Sale> findBySaleDateBetweenOrderBySaleDateDesc(LocalDateTime start, LocalDateTime end);
    
    List<Sale> findAllByOrderBySaleDateDesc();

    List<Sale> findByStatusInAndPaidAtGreaterThanEqualAndPaidAtLessThan(
            java.util.Collection<Sale.SaleStatus> statuses, LocalDateTime start, LocalDateTime end);

    List<Sale> findByCashierIdAndStatusInAndPaidAtGreaterThanEqualAndPaidAtLessThan(
            Long cashierId, java.util.Collection<Sale.SaleStatus> statuses, LocalDateTime start, LocalDateTime end);
}


