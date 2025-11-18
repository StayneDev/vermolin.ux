# Database - Documentação

## Stack
- **Desenvolvimento:** H2 (Embedded)
- **Produção:** PostgreSQL 13+
- **ORM:** JPA/Hibernate
- **Migrations:** Flyway (quando necessário)

## Conexão

### H2 (Desenvolvimento)
```
URL: jdbc:h2:mem:testdb
Username: sa
Password: (vazio)
Console: http://localhost:8080/h2-console
```

### PostgreSQL (Produção)
```
URL: jdbc:postgresql://localhost:5432/vemolin
Username: postgres
Password: seu-password-aqui
```

## Tabelas Principais

### 1. USUARIOS
```sql
CREATE TABLE usuarios (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL (BCrypt),
  full_name VARCHAR(100) NOT NULL,
  role VARCHAR(20) NOT NULL (GERENTE, CAIXA, ESTOQUISTA),
  active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by BIGINT
);
```

**Default Data:**
- gerente / gerente123 (Role: GERENTE)
- caixa / caixa123 (Role: CAIXA)
- estoquista / estoquista123 (Role: ESTOQUISTA)

### 2. PRODUTOS
```sql
CREATE TABLE produtos (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(50) UNIQUE NOT NULL,
  name VARCHAR(100) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  unit VARCHAR(20) NOT NULL (KG, UN, L, etc),
  stock_quantity DECIMAL(10,2) NOT NULL,
  min_stock DECIMAL(10,2),
  supplier_id BIGINT FOREIGN KEY,
  expiry_date DATE,
  requires_weighing BOOLEAN DEFAULT false,
  active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by BIGINT FOREIGN KEY USUARIOS(id)
);
```

### 3. VENDAS
```sql
CREATE TABLE vendas (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  status VARCHAR(20) NOT NULL (OPEN, PAID, CANCELLED),
  total_amount DECIMAL(10,2),
  payment_method VARCHAR(20) (DINHEIRO, CARTAO, PIX),
  amount_paid DECIMAL(10,2),
  change_amount DECIMAL(10,2),
  cashier_id BIGINT NOT NULL FOREIGN KEY USUARIOS(id),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  paid_at TIMESTAMP,
  created_by BIGINT FOREIGN KEY USUARIOS(id)
);
```

### 4. VENDA_ITEMS
```sql
CREATE TABLE venda_items (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  venda_id BIGINT NOT NULL FOREIGN KEY VENDAS(id),
  product_id BIGINT NOT NULL FOREIGN KEY PRODUTOS(id),
  product_name VARCHAR(100),
  product_price DECIMAL(10,2),
  quantity DECIMAL(10,2) NOT NULL,
  unit VARCHAR(20),
  subtotal DECIMAL(10,2),
  weighed BOOLEAN DEFAULT false,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 5. STOCK_MOVEMENTS
```sql
CREATE TABLE stock_movements (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  product_id BIGINT NOT NULL FOREIGN KEY PRODUTOS(id),
  movement_type VARCHAR(20) NOT NULL (ENTRADA, SAIDA, VENDA, AJUSTE),
  quantity DECIMAL(10,2) NOT NULL,
  previous_quantity DECIMAL(10,2),
  new_quantity DECIMAL(10,2),
  reason VARCHAR(255),
  notes TEXT,
  supplier_id BIGINT FOREIGN KEY FORNECEDORES(id),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by BIGINT NOT NULL FOREIGN KEY USUARIOS(id)
);
```

### 6. FORNECEDORES
```sql
CREATE TABLE fornecedores (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  cnpj VARCHAR(20) UNIQUE,
  contact_name VARCHAR(100),
  phone VARCHAR(20),
  email VARCHAR(100),
  address VARCHAR(255),
  active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Relacionamentos

```
USUARIOS (1) ────→ (Many) VENDAS
         ────→ (Many) STOCK_MOVEMENTS
         ────→ (Many) PRODUTOS (created_by)

FORNECEDORES (1) ──→ (Many) PRODUTOS

PRODUTOS (1) ────→ (Many) VENDA_ITEMS
        ────→ (Many) STOCK_MOVEMENTS

VENDAS (1) ────→ (Many) VENDA_ITEMS
       ────→ (Many) STOCK_MOVEMENTS (via items)
```

## Índices (Performance)

```sql
CREATE INDEX idx_usuarios_username ON usuarios(username);
CREATE INDEX idx_produtos_code ON produtos(code);
CREATE INDEX idx_produtos_supplier ON produtos(supplier_id);
CREATE INDEX idx_vendas_status ON vendas(status);
CREATE INDEX idx_vendas_cashier ON vendas(cashier_id);
CREATE INDEX idx_venda_items_venda ON venda_items(venda_id);
CREATE INDEX idx_stock_product ON stock_movements(product_id);
CREATE INDEX idx_stock_created ON stock_movements(created_at);
```

## Constraints

- PRIMARY KEY em todas as tabelas
- FOREIGN KEY com integridade referencial
- UNIQUE em: username, code, cnpj
- NOT NULL em campos obrigatórios
- DEFAULT values para timestamps

## Seed Data (Inicial)

```sql
-- Usuários default
INSERT INTO usuarios (username, password, full_name, role, active, created_at)
VALUES 
  ('gerente', '$2a$10$...', 'Gerente System', 'GERENTE', true, now()),
  ('caixa', '$2a$10$...', 'Caixa System', 'CAIXA', true, now()),
  ('estoquista', '$2a$10$...', 'Estoquista System', 'ESTOQUISTA', true, now());

-- Fornecedor exemplo
INSERT INTO fornecedores (name, cnpj, contact_name)
VALUES ('Fornecedor XYZ', '12.345.678/0001-90', 'João Silva');

-- Produtos exemplo
INSERT INTO produtos (code, name, price, unit, stock_quantity, min_stock, supplier_id, active)
VALUES 
  ('TOMATE001', 'Tomate Carioca', 5.50, 'KG', 100, 10, 1, true),
  ('ALFACE001', 'Alface Americana', 3.00, 'UN', 50, 5, 1, true);
```

## Auto-Initialization

```
Na primeira execução do backend:
1. Spring cria todas as tabelas automaticamente
2. Insere dados default (usuários, fornecedores, produtos)
3. Pronto para usar!

Configuração em application.properties:
spring.jpa.hibernate.ddl-auto=create-drop
```

## Migrações (Flyway)

Se precisar adicionar campos/tabelas:

```
1. Criar arquivo: db/migration/V001__Initial_schema.sql
2. Adicionar: db/migration/V002__Add_new_field.sql
3. Spring executa automaticamente na startup
```

## Backups

### Desenvolvimento (H2)
- Arquivo: `testdb.h2.db` no `/target`
- Temporário (perde ao recompilr)

### Produção (PostgreSQL)
```bash
# Backup
pg_dump -U postgres vemolin > backup.sql

# Restore
psql -U postgres vemolin < backup.sql
```

## Gerenciamento

### Ver Dados em Desenvolvimento
1. Acesse: http://localhost:8080/h2-console
2. URL: jdbc:h2:mem:testdb
3. Username: sa
4. Clique em "Connect"

### Consultas Úteis

```sql
-- Ver todos os usuários
SELECT * FROM usuarios;

-- Ver produtos ativos
SELECT * FROM produtos WHERE active = true;

-- Ver histórico de vendas
SELECT * FROM vendas ORDER BY created_at DESC;

-- Ver movimentações de estoque
SELECT * FROM stock_movements ORDER BY created_at DESC;

-- Ver estoque atual de cada produto
SELECT name, stock_quantity, min_stock FROM produtos;

-- Produtos com estoque baixo
SELECT * FROM produtos WHERE stock_quantity <= min_stock;
```

## Transações ACID

Todas as operações críticas mantêm integridade:

```
Venda finalizada:
1. Atualiza status da venda → PAID
2. Decrementa estoque do produto
3. Cria registro de movimentação
4. Se erro em qualquer passo → Rollback completo
```

Garante consistência total.
