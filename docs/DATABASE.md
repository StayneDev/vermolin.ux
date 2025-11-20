# Database - Documentação

## Visão Geral
- **SGDB oficial:** PostgreSQL 14+
- **Versão do schema:** controlada por Flyway (`db/migration/V1__...` → `V13__update_stock_movements_table.sql`)
- **ORM:** JPA/Hibernate
- **Objetivo:** manter rastreabilidade completa de usuários, produtos, vendas e estoque.

## Conexão

| Ambiente | URL | Usuário | Senha |
|----------|-----|---------|-------|
| Desenvolvimento / Produção | `jdbc:postgresql://localhost:5432/vermolinux` | `postgres` | `Post!Gres!44` |

```powershell
# Testar acesso rápido
psql "postgresql://postgres:Post!Gres!44@localhost:5432/vermolinux"
```

## Principais Tabelas

| Tabela | Campos-chave | Observações |
|--------|--------------|-------------|
| `users` | `username`, `role`, `active`, `created_by` | Guarda credenciais criptografadas (BCrypt) e auditoria completa. |
| `suppliers` | `name`, `cnpj`, `active` | Referenciada por `products.supplier_id`. |
| `products` | `code`, `unit`, `stock_quantity`, `min_stock`, `requires_weighing` | Estoque em `DECIMAL(10,3)`, usa enums no backend. |
| `sales` | `status`, `payment_method`, `cashier_id`, `paid_at` | Fluxo OPEN → PAID/CANCELLED. |
| `sale_items` | `sale_id`, `product_id`, `quantity`, `unit`, `subtotal` | Snapshots de preço/unidade no momento da venda. |
| `stock_movements` | `movement_type`, `quantity`, `previous_quantity`, `new_quantity`, `created_by`, `sale_id` | Usada para entradas/saídas/ajustes e vendas automáticas. |

### DDL Simplificado

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
    created_by BIGINT REFERENCES users(id),
    updated_by BIGINT REFERENCES users(id)
);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    unit VARCHAR(10) NOT NULL,
    stock_quantity DECIMAL(10,3) NOT NULL DEFAULT 0,
    min_stock DECIMAL(10,3) DEFAULT 0,
    supplier_id BIGINT REFERENCES suppliers(id),
    requires_weighing BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT REFERENCES users(id),
    updated_by BIGINT REFERENCES users(id)
);

CREATE TABLE stock_movements (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products(id),
    movement_type VARCHAR(20) NOT NULL,
    quantity DECIMAL(10,3) NOT NULL,
    previous_quantity DECIMAL(10,3),
    new_quantity DECIMAL(10,3),
    reason TEXT,
    notes TEXT,
    supplier_id BIGINT REFERENCES suppliers(id),
    expiry_date DATE,
    sale_id BIGINT REFERENCES sales(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL REFERENCES users(id)
);
```

> Consulte os arquivos `backend/src/main/resources/db/migration/V*.sql` para o DDL completo de `suppliers`, `sales` e `sale_items`.

## Relacionamentos

```
users (1) ──┬─> products.created_by / updated_by
            ├─> sales.cashier_id / cancelled_by
            └─> stock_movements.created_by

suppliers (1) ──> products ──> stock_movements

sales (1) ──> sale_items (many)
sales (1) ──> stock_movements (tipo VENDA)

products (1) ──> sale_items / stock_movements
```

## Índices Relevantes
- `idx_users_username`, `idx_users_role`
- `idx_products_code`, `idx_products_supplier`, `idx_products_low_stock`
- `idx_sales_date`, `idx_sales_cashier`, `idx_sales_status`
- `idx_sale_items_sale`
- `idx_stock_movements_product`, `idx_stock_movements_date`, `idx_stock_movements_created_by`

## Migrações Flyway

| Versão | Descrição |
|--------|-----------|
| `V1` | Cria `users` + seed básico |
| `V2` | `suppliers` |
| `V3` | `products` |
| `V4` | `sales` |
| `V5` | `sale_items` |
| `V6` | `stock_movements` inicial |
| `V7` | Seed de fornecedores/produtos |
| `V8` | Ajuste de `sale_items` |
| `V9`-`V12` | Dados padrão + correções de usuários |
| `V13` | Atualiza `stock_movements` (coluna `created_by`, campos de auditoria completos) |

Adicionar mudanças segue o padrão `V{N}__descricao.sql` dentro de `db/migration`.

## Rotina de Persistência
1. Inicie o backend (`mvn spring-boot:run`).
2. Execute qualquer POST (ex.: `POST /api/products`) ou rode um `INSERT` direto no PostgreSQL.
3. Pare o backend.
4. Reinicie o backend e consulte o registro (`GET /api/products` ou `SELECT ...`).
5. Os dados permanecem porque todo o estado persiste no PostgreSQL – nenhum objeto fica em memória.

## Backups / Reset

```powershell
# Backup
pg_dump -Fc -U postgres vermolinux > backup.dump

# Restore
pg_restore -c -U postgres -d vermolinux backup.dump

# Reset rápido (limpa tudo e reaplica Flyway)
dropdb -U postgres vermolinux
createdb -U postgres vermolinux
```

## Consultas Úteis

```sql
-- Usuários ativos
SELECT id, username, role FROM users WHERE active;

-- Produtos com estoque baixo
SELECT name, stock_quantity, min_stock
FROM products
WHERE stock_quantity <= min_stock;

-- Histórico de movimentações
SELECT product_id, movement_type, quantity, created_at
FROM stock_movements
ORDER BY created_at DESC;

-- Vendas finalizadas no dia
SELECT id, total_amount, payment_method
FROM sales
WHERE status = 'PAID' AND sale_date::date = CURRENT_DATE;
```

## Boas Práticas
- Toda operação crítica usa `@Transactional`, garantindo rollback automático.
- `spring.jpa.hibernate.ddl-auto=none` evita alterações fora do controle do Flyway.
- Use pgAdmin/psql para inspecionar dados; nenhuma dependência de H2 existe mais.
