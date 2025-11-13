package com.vermolinux.repository.inmemory;

import com.vermolinux.model.Sale;
import com.vermolinux.repository.SaleRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementação in-memory do repositório de Sale
 * 
 * TODO: Substituir por implementação JPA quando integrar com banco de dados
 */
@Repository
public class InMemorySaleRepository implements SaleRepository {
    
    private final Map<Long, Sale> sales = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public Sale save(Sale sale) {
        if (sale.getId() == null) {
            sale.setId(idGenerator.getAndIncrement());
        }
        sales.put(sale.getId(), sale);
        return sale;
    }
    
    @Override
    public Optional<Sale> findById(Long id) {
        return Optional.ofNullable(sales.get(id));
    }
    
    @Override
    public List<Sale> findAll() {
        return new ArrayList<>(sales.values());
    }
    
    @Override
    public List<Sale> findByStatus(Sale.SaleStatus status) {
        return sales.values().stream()
                .filter(s -> s.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Sale> findByCashierId(Long cashierId) {
        return sales.values().stream()
                .filter(s -> s.getCashierId().equals(cashierId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Sale> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return sales.values().stream()
                .filter(s -> !s.getCreatedAt().isBefore(start) && !s.getCreatedAt().isAfter(end))
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        sales.remove(id);
    }
}
