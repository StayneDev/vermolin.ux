# Backend - Documentação

## Stack
- **Spring Boot 3.2.0**
- **Java 17**
- **Maven 3.9+**
- **JPA/Hibernate** (ORM)
- **Spring Security** (Autenticação)

## Estrutura

```
backend/src/main/java/com/vermolinux/
├── controller/           - REST Endpoints
│   ├── AuthController.java
│   ├── ProductController.java
│   ├── SaleController.java
│   ├── StockController.java
│   ├── UserController.java
│   └── SupplierController.java
├── service/              - Business Logic
│   ├── AuthService.java
│   ├── ProductService.java
│   ├── SaleService.java
│   ├── StockService.java
│   ├── UserService.java
│   └── SupplierService.java
├── repository/           - Data Access (JPA)
│   ├── UserRepository.java
│   ├── ProductRepository.java
│   ├── SaleRepository.java
│   ├── StockMovementRepository.java
│   └── ... (outros)
├── entity/               - Models + DTOs
│   ├── User.java
│   ├── Product.java
│   ├── Sale.java
│   ├── SaleItem.java
│   ├── StockMovement.java
│   ├── Supplier.java
│   └── dto/ (Request/Response)
├── security/             - JWT + Auth
│   ├── JwtTokenProvider.java
│   ├── JwtAuthenticationFilter.java
│   ├── UserDetailsServiceImpl.java
│   └── SecurityConfig.java
├── exception/            - Error Handling
│   ├── GlobalExceptionHandler.java
│   ├── BusinessException.java
│   └── ResourceNotFoundException.java
├── config/               - Configurações
│   └── ... (beans, properties)
└── VermolinUxApplication.java (Main)
```

## Execução

```bash
cd backend
mvn clean install       # Primeira vez
mvn spring-boot:run    # Desenvolver
mvn clean package      # Build JAR para produção
```

## Portas
- **Desenvolvimento:** http://localhost:8080
- **Swagger UI:** http://localhost:8080/api/swagger-ui.html
- **H2 Console:** http://localhost:8080/h2-console

## Autenticação

### JWT
- **Algoritmo:** HMAC-SHA512
- **Secret:** Configurado em `application.properties`
- **Expiry:** 24 horas
- **Header:** `Authorization: Bearer <token>`

### Roles (3 níveis)
- **GERENTE** - Acesso total
- **CAIXA** - Vendas e leitura de produtos
- **ESTOQUISTA** - Estoque e leitura de produtos

## Controllers Principais

### AuthController
```
POST /api/auth/login
├─ Input: {username, password}
└─ Output: {token, userId, username, role}
```

### ProductController
```
GET    /api/products              - Listar
POST   /api/products              - Criar
PUT    /api/products/{id}         - Atualizar
DELETE /api/products/{id}         - Deletar
```

### SaleController
```
POST   /api/sales                 - Abrir venda
POST   /api/sales/{id}/items      - Adicionar item
DELETE /api/sales/{id}/items/{itemId} - Remover item
POST   /api/sales/{id}/finalize   - Finalizar venda
POST   /api/sales/{id}/cancel     - Cancelar venda
GET    /api/sales                 - Listar
GET    /api/sales/{id}            - Detalhes
```

### StockController
```
GET    /api/stock/movements       - Histórico
POST   /api/stock/entry           - Entrada
POST   /api/stock/adjustment      - Ajuste
```

### UserController (Admin)
```
GET    /api/users                 - Listar
POST   /api/users                 - Criar
PUT    /api/users/{id}            - Atualizar
DELETE /api/users/{id}            - Deletar
```

## Services

### AuthService
```java
authenticate(username, password): User
generateToken(user): String
```

### ProductService
```java
getAllProducts(page, size): Page<Product>
saveProduct(productRequest): Product
updateProduct(id, productRequest): Product
deleteProduct(id): void
```

### SaleService
```java
createSale(cashierId): Sale
addItem(saleId, item): Sale
removeItem(saleId, itemId): Sale
finalizeSale(saleId, payment): Sale
cancelSale(saleId, reason): Sale
```

### StockService
```java
recordEntry(productId, quantity, reason): StockMovement
recordAdjustment(productId, quantity, reason): StockMovement
getMovements(productId): List<StockMovement>
```

## Validações

### Multi-camadas
1. **Client:** Validação frontend (tipos, tamanho)
2. **Controller:** `@Valid` + Bean Validation
3. **Service:** Lógica de negócio

### Exemplos
- Username: obrigatório, 3-50 chars, ÚNICO
- Password: mínimo 8 caracteres
- Email: formato válido (opcional)
- Quantidade: > 0
- Estoque não pode ficar negativo

## Tratamento de Erros

```java
// Global Exception Handler
- BusinessException → 400 Bad Request
- ResourceNotFoundException → 404 Not Found
- DataIntegrityViolationException → 409 Conflict
- Exception genérica → 500 Internal Server Error

Resposta padrão:
{
  "message": "Descrição do erro",
  "error": "Detalhes técnicos",
  "timestamp": "2025-11-18T10:30:00"
}
```

## Configuração (application.properties)

```properties
# Servidor
server.port=8080

# JWT
jwt.secret=sua-chave-de-32-caracteres-aqui
jwt.expiration=86400000

# Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true

# JPA
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
```

## Build para Produção

```bash
mvn clean package
# Gera: target/vemolin-ux-1.0.jar
# Executar: java -jar vemolin-ux-1.0.jar
```

## Testes

```bash
mvn test                 # Unit tests
mvn integration-test     # Integration tests
```

## Transações

```java
// Operações críticas usam @Transactional
@Transactional
public Sale finalizeSale(Long saleId, PaymentRequest payment) {
  // Se erro em qualquer ponto, faz rollback completo
  // Garante integridade dos dados
}
```

## Auditoria

Todas as entidades têm:
```java
@CreationTimestamp
private LocalDateTime createdAt;

@Column(name = "created_by")
private Long createdBy;  // ID do usuário que criou
```

Rastreabilidade completa de quem fez o quê e quando.
