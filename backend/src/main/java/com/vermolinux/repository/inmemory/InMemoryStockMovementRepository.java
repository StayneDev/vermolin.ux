package com.vermolinux.repository.inmemory;

import com.vermolinux.model.StockMovement;
import com.vermolinux.repository.StockMovementRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementação in-memory do repositório de StockMovement
 * 
 * TODO: Substituir por implementação JPA quando integrar com banco de dados
 */
@Repository
public class InMemoryStockMovementRepository implements StockMovementRepository {
    
    private final Map<Long, StockMovement> movements = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public StockMovement save(StockMovement movement) {
        if (movement.getId() == null) {
            movement.setId(idGenerator.getAndIncrement());
        }
        movements.put(movement.getId(), movement);
        return movement;
    }
    
    @Override
    public Optional<StockMovement> findById(Long id) {
        return Optional.ofNullable(movements.get(id));
    }
    
    @Override
    public List<StockMovement> findAll() {
        return new ArrayList<>(movements.values());
    }
    
    @Override
    public List<StockMovement> findByProductId(Long productId) {
        return movements.values().stream()
                .filter(m -> m.getProductId().equals(productId))
                .sorted(Comparator.comparing(StockMovement::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<StockMovement> findByProductIdOrderByCreatedAtDesc(Long productId) {
        return findByProductId(productId);
    }
    
    @Override
    public List<StockMovement> findByCreatedBy(Long userId) {
        return movements.values().stream()
                .filter(m -> m.getCreatedBy().equals(userId))
                .sorted(Comparator.comparing(StockMovement::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<StockMovement> findByCreatedByOrderByCreatedAtDesc(Long userId) {
        return findByCreatedBy(userId);
    }
    
    @Override
    public List<StockMovement> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return movements.values().stream()
                .filter(m -> !m.getCreatedAt().isBefore(start) && !m.getCreatedAt().isAfter(end))
                .sorted(Comparator.comparing(StockMovement::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        movements.remove(id);
    }
}
