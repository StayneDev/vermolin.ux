package com.vermolinux.security;

import com.vermolinux.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Principal customizado que armazena userId e outros dados do usuário
 */
public class UserPrincipal implements UserDetails {
    
    private final Long userId;
    private final String username;
    private final String password;
    private final User.UserRole role;
    private final Boolean active;
    
    public UserPrincipal(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.active = user.getActive();
    }
    
    // Getters
    public Long getUserId() {
        return userId;
    }
    
    public User.UserRole getRole() {
        return role;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return active;
    }
    
    /**
     * Override getName() para retornar userId como String
     * Isso permite que authentication.getName() retorne o ID do usuário
     */
    public String getName() {
        return userId.toString();
    }
}


