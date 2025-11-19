package com.vermolinux.repository;

import com.vermolinux.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository Spring Data JPA para User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    List<User> findByRole(User.UserRole role);
    
    List<User> findByActiveTrue();
    
    boolean existsByUsername(String username);
}


