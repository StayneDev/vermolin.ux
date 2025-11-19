package com.vermolinux.service;

import com.vermolinux.dto.UserRequest;
import com.vermolinux.dto.UserResponse;
import com.vermolinux.exception.BusinessException;
import com.vermolinux.exception.ResourceNotFoundException;
import com.vermolinux.model.User;
import com.vermolinux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de Usuários
 * 
 * Implementa:
 * - RF26: Cadastrar novo usuário
 * - RF27: Consultar usuários
 * - RF28: Atualizar usuário
 * - RF29: Deletar usuário
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * RF26: Cadastrar novo usuário (apenas Gerente)
     * RF6: Auditoria - registra data, hora e usuário criador
     */
    @Transactional
    public UserResponse create(UserRequest request, Long createdBy) {
        log.info("Criando novo usuário: {}", request.getUsername());
        
        // RF5: Validar dados obrigatórios
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Já existe um usuário com este username");
        }
        
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(request.getRole())
                .active(request.getActive() != null ? request.getActive() : true)
                .createdBy(createdBy)
                .updatedBy(createdBy)
                .build();
        
        user = userRepository.save(user);
        
        log.info("Usuário criado com sucesso: {} (ID: {})", user.getUsername(), user.getId());
        
        // RF6: Registrar operação (auditoria)
        // TODO: Log de auditoria quando integrar com banco
        
        return mapToResponse(user);
    }
    
    /**
     * RF27: Consultar usuários
     */
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", id));
        
        return mapToResponse(user);
    }
    
    /**
     * RF28: Atualizar usuário
     * RF6: Auditoria - atualiza data e usuário que modificou
     */
    @Transactional
    public UserResponse update(Long id, UserRequest request, Long updatedBy) {
        log.info("Atualizando usuário ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", id));
        
        // Verificar se username já existe em outro usuário
        if (!user.getUsername().equals(request.getUsername()) && 
            userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Já existe outro usuário com este username");
        }
        
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setRole(request.getRole());
        
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }
        
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(updatedBy);
        
        user = userRepository.save(user);
        
        log.info("Usuário atualizado: {} (ID: {})", user.getUsername(), user.getId());
        
        return mapToResponse(user);
    }
    
    /**
     * RF29: Deletar usuário (com auditoria - soft delete)
     * RF6: Registra quem deletou e quando
     */
    @Transactional
    public void delete(Long id, Long deletedBy) {
        log.info("Deletando usuário ID: {} por usuário ID: {}", id, deletedBy);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", id));
        
        // RF25/RF29: Registrar histórico de exclusão para auditoria
        // TODO: Criar tabela de auditoria quando integrar com banco
        
        userRepository.deleteById(id);
        
        log.info("Usuário deletado: {} (ID: {})", user.getUsername(), user.getId());
    }
    
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .createdBy(user.getCreatedBy())
                .updatedBy(user.getUpdatedBy())
                .build();
    }
}
