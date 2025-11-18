package com.vermolinux.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

/**
 * JwtTokenProvider - Gerador e Validador de Tokens JWT
 * 
 * Responsabilidades:
 * - Gerar tokens JWT assinados para usuários autenticados (RF1 - Autenticação)
 * - Validar tokens em requisições (RF3 - Autorização)
 * - Extrair claims (username, userId, role) do token
 * 
 * Fluxo:
 * 1. Usuário faz login com credentials
 * 2. AuthService valida no banco
 * 3. JwtTokenProvider.generateToken() cria JWT assinado com secret key
 * 4. Frontend recebe token e armazena em localStorage
 * 5. Para cada requisição: Authorization: Bearer <token>
 * 6. JwtAuthenticationFilter extrai token e chama validateToken()
 * 7. Se válido, extrai username/role e popula SecurityContext
 * 8. @PreAuthorize verifica permissões do usuário
 * 
 * Segurança:
 * - Algoritmo: HMAC-SHA512 (HS512)
 * - Secret key: 32+ caracteres (vem de application.properties)
 * - Expiração: 24 horas (86.400 segundos)
 * - Assinatura garante que token não foi alterado no cliente
 */
@Component
public class JwtTokenProvider {
    
    // Secret key carregado do application.properties (produção usa variável de ambiente)
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    // Tempo de expiração em milissegundos (86400000ms = 24 horas)
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    
    /**
     * Converte a secret string em uma Key criptográfica
     * Usa HMAC-SHA512 que requer no mínimo 32 caracteres
     * 
     * @return SecretKey para assinar/validar JWTs
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    
    /**
     * Gera um token JWT assinado para o usuário autenticado
     * 
     * JWT Payload exemplo:
     * {
     *   "sub": "gerente",        // username
     *   "userId": 1,             // ID do usuário
     *   "role": "GERENTE",       // Cargo/Role
     *   "iat": 1700000000,       // Issued at (unix timestamp)
     *   "exp": 1700086400        // Expiration (24 horas depois)
     * }
     * 
     * O token inteiro é criptografado e assinado com a secret key.
     * Cliente não pode alterar sem invalidar a assinatura.
     * 
     * @param username Nome de usuário (primary identifier)
     * @param userId ID único do usuário (para rastreabilidade)
     * @param role Cargo do usuário (GERENTE, CAIXA, ESTOQUISTA) - usado em @PreAuthorize
     * @return Token JWT completo para retornar ao cliente
     */
    public String generateToken(String username, Long userId, String role) {
        Date now = new Date();
        // Calcula data de expiração (now + 24 horas)
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        
        return Jwts.builder()
                .subject(username)                    // Campo "sub" - principal identifier
                .claim("userId", userId)              // Claim customizado para rastreamento
                .claim("role", role)                  // Claim customizado para autorização
                .issuedAt(now)                        // Campo "iat" - quando foi gerado
                .expiration(expiryDate)               // Campo "exp" - quando expira
                .signWith(getSigningKey())            // Assina com HMAC-SHA512
                .compact();                           // Retorna token em formato compacto (3 partes separadas por .)
    }
    
    /**
     * Extrai o username (subject) do token JWT
     * Usado por JwtAuthenticationFilter para carregar UserDetails
     * 
     * @param token Token JWT a decodificar
     * @return Username (stored as "sub" claim)
     * @throws JwtException se token inválido
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())  // Verifica assinatura
                .build()
                .parseSignedClaims(token)                 // Decodifica e valida
                .getPayload();                            // Extrai claims
        
        return claims.getSubject();  // Retorna o "sub" (username)
    }
    
    /**
     * Extrai o userId do token JWT
     * Útil para auditoria (saber qual usuário fez cada ação)
     * 
     * @param token Token JWT
     * @return User ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        return claims.get("userId", Long.class);  // Type-safe extraction
    }
    
    /**
     * Extrai o role do token JWT
     * Usado por @PreAuthorize para verificar permissões sem consultar BD
     * Reduz latência pois role já está no token
     * 
     * @param token Token JWT
     * @return Role (GERENTE, CAIXA, ESTOQUISTA)
     */
    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        return claims.get("role", String.class);
    }
    
    /**
     * Valida se um token JWT é legítimo e não expirou
     * 
     * Verificações realizadas:
     * 1. Assinatura é válida (token não foi alterado)
     * 2. Token não expirou (exp > agora)
     * 3. Formato é válido (3 partes separadas por .)
     * 4. Claims obrigatórios estão presentes
     * 
     * Exceções capturadas:
     * - SignatureException: Token foi alterado
     * - ExpiredJwtException: Token expirou
     * - UnsupportedJwtException: Formato inválido
     * - MalformedJwtException: Token malformado
     * - IllegalArgumentException: Token vazio/null
     * 
     * @param token Token JWT a validar
     * @return true se válido, false caso contrário (nunca lança exceção)
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())  // Valida assinatura HMAC
                .build()
                .parseSignedClaims(token);                // Tenta decodificar
            return true;  // Se chegou aqui, token é válido
        } catch (JwtException | IllegalArgumentException e) {
            // Log para debugging (importante em produção para detectar ataques)
            System.out.println("Token inválido: " + e.getMessage());
            return false;
        }
    }
}


