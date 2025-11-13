package com.vermolinux.security;

import com.vermolinux.model.User;
import com.vermolinux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementação de UserDetailsService para Spring Security
 * Carrega usuário do repositório para autenticação (RF1, RF3)
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        
        if (!user.getActive()) {
            throw new UsernameNotFoundException("Usuário inativo: " + username);
        }
        
        return new UserPrincipal(user);
    }
}
