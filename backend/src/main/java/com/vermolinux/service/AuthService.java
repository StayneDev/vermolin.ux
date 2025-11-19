package com.vermolinux.service;

import com.vermolinux.dto.LoginRequest;
import com.vermolinux.dto.LoginResponse;
import com.vermolinux.exception.UnauthorizedException;
import com.vermolinux.model.User;
import com.vermolinux.repository.UserRepository;
import com.vermolinux.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * Service de Autenticação
 * 
 * Implementa:
 * - RF1: Login no sistema
 * - RF2: Logout do sistema  
 * - RF3: Identificação de cargo
 */
@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    /**
     * Realiza login no sistema
     * RF1: Permite login usando usuário e senha
     * RF3: Identifica cargo do funcionário ao autenticar
     * 
     * @param request Dados de login (username e password)
     * @return Dados do usuário autenticado com token JWT
     * @throws UnauthorizedException se credenciais inválidas
     */
    public LoginResponse login(LoginRequest request) {
        System.out.println("Tentativa de login para usuário: " + request.getUsername());
        
        // Buscar usuário
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    System.out.println("Usuário não encontrado: " + request.getUsername());
                    return new UnauthorizedException("Credenciais inválidas");
                });
        
        // Verificar se usuário está ativo
        if (!user.getActive()) {
            System.out.println("Tentativa de login com usuário inativo: " + request.getUsername());
            throw new UnauthorizedException("Usuário inativo");
        }
        
        // Validar senha
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            System.out.println("Senha incorreta para usuário: " + request.getUsername());
            throw new UnauthorizedException("Credenciais inválidas");
        }
        
        // Gerar token JWT
        String token = jwtTokenProvider.generateToken(
                user.getUsername(),
                user.getId(),
                user.getRole().name()
        );
        
        System.out.println("Login realizado com sucesso: " + user.getUsername() + " (" + user.getRole() + ")");
        
        // RF6: Registrar operação de login (auditoria)
        // TODO: Implementar log de auditoria quando integrar com banco de dados
        
        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }
    
    /**
     * Realiza logout do sistema
     * RF2: Permite logout do sistema
     * 
     * Nota: Como JWT é stateless, o logout é feito no client-side removendo o token.
     * Este método serve apenas para registro de auditoria.
     * 
     * @param username Nome do usuário fazendo logout
     */
    public void logout(String username) {
                // RF6: Registrar operação de logout (auditoria)
        // TODO: Implementar log de auditoria quando integrar com banco de dados
    }
    
    /**
     * Obtém dados do usuário autenticado
     * 
     * @param username Nome do usuário
     * @return Dados do usuário
     */
    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Usuário não encontrado"));
    }
}






