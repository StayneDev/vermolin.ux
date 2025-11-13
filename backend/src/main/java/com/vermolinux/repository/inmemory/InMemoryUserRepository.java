package com.vermolinux.repository.inmemory;

import com.vermolinux.model.User;
import com.vermolinux.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementação in-memory do repositório de User
 * 
 * TODO: Substituir por implementação JPA quando integrar com banco de dados
 * Esta implementação usa ConcurrentHashMap para simular persistência em memória
 */
@Repository
public class InMemoryUserRepository implements UserRepository {
    
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    /**
     * Inicializa com usuários padrão para teste
     * RF26: Usuários cadastrados pelo sistema
     */
    @PostConstruct
    public void init() {
        // Usuário Gerente padrão
        User gerente = User.builder()
                .id(idGenerator.getAndIncrement())
                .username("gerente")
                .password(passwordEncoder.encode("gerente123"))
                .fullName("Gerente do Sistema")
                .role(User.UserRole.GERENTE)
                .active(true)
                .build();
        users.put(gerente.getId(), gerente);
        
        // Usuário Estoquista padrão
        User estoquista = User.builder()
                .id(idGenerator.getAndIncrement())
                .username("estoquista")
                .password(passwordEncoder.encode("estoquista123"))
                .fullName("Estoquista do Sistema")
                .role(User.UserRole.ESTOQUISTA)
                .active(true)
                .build();
        users.put(estoquista.getId(), estoquista);
        
        // Usuário Caixa padrão
        User caixa = User.builder()
                .id(idGenerator.getAndIncrement())
                .username("caixa")
                .password(passwordEncoder.encode("caixa123"))
                .fullName("Caixa do Sistema")
                .role(User.UserRole.CAIXA)
                .active(true)
                .build();
        users.put(caixa.getId(), caixa);
        
        System.out.println("✅ Usuários padrão criados:");
        System.out.println("   - gerente / gerente123");
        System.out.println("   - estoquista / estoquista123");
        System.out.println("   - caixa / caixa123");
    }
    
    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        users.put(user.getId(), user);
        return user;
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
    
    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
    
    @Override
    public List<User> findByRole(User.UserRole role) {
        return users.values().stream()
                .filter(user -> user.getRole() == role)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<User> findByActive(Boolean active) {
        return users.values().stream()
                .filter(user -> user.getActive().equals(active))
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        users.remove(id);
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return users.values().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
}
