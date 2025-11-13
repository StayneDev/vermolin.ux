# 🗄️ Guia de Integração com Banco de Dados

Este documento descreve como migrar o sistema Vermolin.UX do armazenamento **in-memory** para um banco de dados real utilizando **JPA/Hibernate** e **PostgreSQL** (ou H2 para desenvolvimento).

---

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Estrutura de Tabelas](#estrutura-de-tabelas)
- [Configuração do Projeto](#configuração-do-projeto)
- [Migrations com Flyway](#migrations-com-flyway)
- [Atualização dos Repositories](#atualização-dos-repositories)
- [Testes](#testes)

---

## 🎯 Visão Geral

### Estado Atual
- Repositórios in-memory (`InMemoryUserRepository`, etc.)
- Dados armazenados em `ConcurrentHashMap`
- **Sem persistência** após reiniciar a aplicação

### Estado Futuro
- Spring Data JPA
- PostgreSQL ou H2
- **Persistência real** com relacionamentos
- Migrations versionadas com Flyway

---

## 🗂️ Estrutura de Tabelas

### Tabela: `users`

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- Hash BCrypt
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL, -- GERENTE, ESTOQUISTA, CAIXA
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT REFERENCES users(id),
    updated_by BIGINT REFERENCES users(id)
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_role ON users(role);
```

### Tabela: `suppliers`

```sql
CREATE TABLE suppliers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cnpj VARCHAR(14) UNIQUE NOT NULL,
    contact_name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT REFERENCES users(id),
    updated_by BIGINT REFERENCES users(id)
);

CREATE INDEX idx_suppliers_cnpj ON suppliers(cnpj);
```

### Tabela: `products`

```sql
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    unit VARCHAR(10) NOT NULL, -- KG, UNIDADE, CAIXA, DUZIA
    stock_quantity DECIMAL(10,3) NOT NULL DEFAULT 0,
    min_stock DECIMAL(10,3) DEFAULT 0,
    supplier_id BIGINT REFERENCES suppliers(id),
    expiry_date DATE,
    requires_weighing BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT REFERENCES users(id),
    updated_by BIGINT REFERENCES users(id)
);

CREATE INDEX idx_products_code ON products(code);
CREATE INDEX idx_products_supplier ON products(supplier_id);
CREATE INDEX idx_products_low_stock ON products(stock_quantity, min_stock);
```

### Tabela: `sales`

```sql
CREATE TABLE sales (
    id BIGSERIAL PRIMARY KEY,
    status VARCHAR(20) NOT NULL, -- OPEN, PAID, CANCELLED
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0,
    payment_method VARCHAR(20), -- DINHEIRO, CARTAO, PIX
    amount_paid DECIMAL(10,2),
    change_amount DECIMAL(10,2),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP,
    cancelled_at TIMESTAMP,
    cashier_id BIGINT NOT NULL REFERENCES users(id),
    cancelled_by BIGINT REFERENCES users(id),
    cancellation_reason TEXT
);

CREATE INDEX idx_sales_cashier ON sales(cashier_id);
CREATE INDEX idx_sales_created_at ON sales(created_at);
CREATE INDEX idx_sales_status ON sales(status);
```

### Tabela: `sale_items`

```sql
CREATE TABLE sale_items (
    id BIGSERIAL PRIMARY KEY,
    sale_id BIGINT NOT NULL REFERENCES sales(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id),
    product_name VARCHAR(100) NOT NULL, -- Snapshot
    product_price DECIMAL(10,2) NOT NULL, -- Snapshot
    quantity DECIMAL(10,3) NOT NULL,
    unit VARCHAR(10) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    weighed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_sale_items_sale ON sale_items(sale_id);
CREATE INDEX idx_sale_items_product ON sale_items(product_id);
```

### Tabela: `stock_movements`

```sql
CREATE TABLE stock_movements (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products(id),
    movement_type VARCHAR(20) NOT NULL, -- ENTRADA, SAIDA, AJUSTE, VENDA
    quantity DECIMAL(10,3) NOT NULL,
    previous_quantity DECIMAL(10,3) NOT NULL,
    new_quantity DECIMAL(10,3) NOT NULL,
    reason VARCHAR(50), -- COMPRA, VENDA, PERDA, DOACAO, etc
    notes TEXT,
    supplier_id BIGINT REFERENCES suppliers(id),
    expiry_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL REFERENCES users(id),
    sale_id BIGINT REFERENCES sales(id)
);

CREATE INDEX idx_stock_movements_product ON stock_movements(product_id);
CREATE INDEX idx_stock_movements_created_at ON stock_movements(created_at);
CREATE INDEX idx_stock_movements_created_by ON stock_movements(created_by);
```

---

## ⚙️ Configuração do Projeto

### 1. Descomente dependências no `pom.xml`

```xml
<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- PostgreSQL Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Flyway Migrations -->
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

### 2. Configure `application.properties`

```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/vermolinux
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
```

**Para H2 (desenvolvimento):**

```properties
spring.datasource.url=jdbc:h2:mem:vermolinux
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=create-drop
```

---

## 🚀 Migrations com Flyway

### Estrutura de arquivos

```
src/main/resources/db/migration/
├── V1__create_users_table.sql
├── V2__create_suppliers_table.sql
├── V3__create_products_table.sql
├── V4__create_sales_table.sql
├── V5__create_sale_items_table.sql
├── V6__create_stock_movements_table.sql
└── V7__insert_default_users.sql
```

### Exemplo: `V1__create_users_table.sql`

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT
);

CREATE INDEX idx_users_username ON users(username);
```

### Exemplo: `V7__insert_default_users.sql`

```sql
-- Senha: gerente123
INSERT INTO users (username, password, full_name, role, active) VALUES
('gerente', '$2a$10$...hash...', 'Gerente do Sistema', 'GERENTE', true);

-- Senha: estoquista123
INSERT INTO users (username, password, full_name, role, active) VALUES
('estoquista', '$2a$10$...hash...', 'Estoquista do Sistema', 'ESTOQUISTA', true);

-- Senha: caixa123
INSERT INTO users (username, password, full_name, role, active) VALUES
('caixa', '$2a$10$...hash...', 'Caixa do Sistema', 'CAIXA', true);
```

---

## 🔄 Atualização dos Repositories

### Antes (In-Memory)

```java
@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    // ... implementação manual
}
```

### Depois (JPA)

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(User.UserRole role);
    List<User> findByActiveTrue();
    boolean existsByUsername(String username);
}
```

### Atualização das Entidades

```java
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false, length = 100)
    private String fullName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;
    
    public enum UserRole {
        GERENTE, ESTOQUISTA, CAIXA
    }
}
```

---

## 🧪 Testes

### Teste de Repository

```java
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void shouldFindUserByUsername() {
        User user = User.builder()
            .username("test")
            .password("hash")
            .fullName("Test User")
            .role(User.UserRole.CAIXA)
            .build();
        
        userRepository.save(user);
        
        Optional<User> found = userRepository.findByUsername("test");
        assertThat(found).isPresent();
        assertThat(found.get().getFullName()).isEqualTo("Test User");
    }
}
```

---

## ✅ Checklist de Migração

- [ ] Descomentar dependências JPA no `pom.xml`
- [ ] Configurar `application.properties` com dados do PostgreSQL
- [ ] Criar arquivos de migration em `src/main/resources/db/migration/`
- [ ] Adicionar anotações JPA nas entidades (@Entity, @Table, @Id, etc.)
- [ ] Atualizar repositories para estender `JpaRepository`
- [ ] Remover implementações in-memory
- [ ] Executar `mvn flyway:migrate` para aplicar migrations
- [ ] Testar todas as funcionalidades
- [ ] Atualizar testes unitários e de integração

---

## 📚 Recursos

- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Flyway Documentation](https://flywaydb.org/documentation/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

---

**✅ Após seguir este guia, o sistema terá persistência real de dados!**
