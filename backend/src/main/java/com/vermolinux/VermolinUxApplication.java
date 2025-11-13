package com.vermolinux;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Vermolin.UX
 * 
 * Sistema de gestão para hortifruti desenvolvido com:
 * - Spring Boot 3.2.0
 * - Java 17
 * - Padrão MVC
 * - Autenticação JWT
 * - Swagger/OpenAPI para documentação
 * 
 * @author Equipe Vermolin.UX
 * @version 1.0.0
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Vermolin.UX API",
        version = "1.0.0",
        description = "API REST para sistema de gestão de hortifruti - Vermolin.UX",
        contact = @Contact(
            name = "Equipe Vermolin.UX",
            email = "contato@vermolinux.com"
        )
    )
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Token JWT obtido através do endpoint /api/auth/login"
)
public class VermolinUxApplication {

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("🥬 Iniciando Vermolin.UX - Sistema de Hortifruti");
        System.out.println("═══════════════════════════════════════════════════════");
        
        SpringApplication.run(VermolinUxApplication.class, args);
        
        System.out.println("\n✅ Aplicação iniciada com sucesso!");
        System.out.println("📚 Swagger UI: http://localhost:8080/api/swagger-ui.html");
        System.out.println("📄 API Docs: http://localhost:8080/api/api-docs");
        System.out.println("\n🔐 Credenciais padrão:");
        System.out.println("   Gerente:    gerente / gerente123");
        System.out.println("   Estoquista: estoquista / estoquista123");
        System.out.println("   Caixa:      caixa / caixa123");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
}
