package com.vermolinux.repository;

import com.vermolinux.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Interface de repositório para User
 * 
 * TODO: Quando integrar com banco de dados, estender JpaRepository:
 * public interface UserRepository extends JpaRepository<User, Long> {
 *     Optional<User> findByUsername(String username);
 *     List<User> findByRole(User.UserRole role);
 *     List<User> findByActiveTrue();
 * }
 */
public interface UserRepository {
    
    User save(User user);
    
    Optional<User> findById(Long id);
    
    Optional<User> findByUsername(String username);
    
    List<User> findAll();
    
    List<User> findByRole(User.UserRole role);
    
    List<User> findByActive(Boolean active);
    
    void deleteById(Long id);
    
    boolean existsByUsername(String username);
}
