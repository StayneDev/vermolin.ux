package com.vermolinux.config;

import com.vermolinux.model.User;
import com.vermolinux.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;

/**
 * Initializes default users after application startup
 * This ensures users are created with properly hashed passwords using BCrypt
 */
@Component
public class DataInitializationConfig {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TransactionTemplate transactionTemplate;
    
    public DataInitializationConfig(UserRepository userRepository, PasswordEncoder passwordEncoder, PlatformTransactionManager transactionManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        try {
            transactionTemplate.execute(status -> {
                initializeDefaultUsers();
                return null;
            });
        } catch (Exception e) {
            System.err.println("❌ ERRO CRÍTICO na inicialização: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void initializeDefaultUsers() {
        try {
            System.out.println("\n════════════════════════════════════════");
            System.out.println("🔐 Inicializando usuários padrão...");
            System.out.println("════════════════════════════════════════");

            System.out.println("🔄 Garantindo usuários com senhas criptografadas...\n");

            createOrUpdate("gerente", "gerente123", "Gerente Vermolin", User.UserRole.GERENTE);
            createOrUpdate("estoquista", "estoquista123", "Estoquista Vermolin", User.UserRole.ESTOQUISTA);
            createOrUpdate("caixa", "caixa123", "Caixa Vermolin", User.UserRole.CAIXA);
            
            System.out.println("\n✅ Usuários padrão inicializados com sucesso!");
            System.out.println("════════════════════════════════════════\n");
            System.out.println("\n🔓 CREDENCIAIS PADRÃO:");
            System.out.println("  Gerente: gerente / gerente123");
            System.out.println("  Estoquista: estoquista / estoquista123");
            System.out.println("  Caixa: caixa / caixa123");
            System.out.println("════════════════════════════════════════\n");
        } catch (Exception e) {
            throw new RuntimeException("Erro interno na inicialização", e);
        }
    }

    private void createOrUpdate(String username, String password, String fullName, User.UserRole role) {
        userRepository.findByUsername(username).ifPresentOrElse(existing -> {
            existing.setPassword(passwordEncoder.encode(password));
            existing.setFullName(fullName);
            existing.setRole(role);
            existing.setActive(true);
            existing.setUpdatedAt(LocalDateTime.now());
            userRepository.save(existing);
            System.out.println("✓ Usuário atualizado: " + username);
        }, () -> {
            User user = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .fullName(fullName)
                    .role(role)
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .createdBy(1L)
                    .build();
            userRepository.save(user);
            System.out.println("✓ Usuário criado: " + username + " / " + password);
        });
    }
}
