# Migração para PostgreSQL - Vermolin.UX

## 📋 Resumo

A aplicação Vermolin.UX foi migrada de H2 (banco em memória) para **PostgreSQL** (banco de dados produção).

## 🔧 Configurações Realizadas

### 1. application.properties
**Arquivo:** `backend/src/main/resources/application.properties`

```properties
# Antes (H2)
spring.datasource.url=jdbc:h2:mem:vermolinux
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create
spring.flyway.enabled=false

# Depois (PostgreSQL)
spring.datasource.url=jdbc:postgresql://localhost:5432/vermolinux
spring.datasource.username=postgres
spring.datasource.password=Post!Gress!44
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
```

### 2. DataLoader.java
- Desabilitado (comentado)
- Dados iniciais agora gerenciados via **Flyway Migrations**

### 3. Flyway Migrations Ativas

```
V1__create_users_table.sql              - Tabela de usuários com usuários padrão
V2__create_suppliers_table.sql          - Tabela de fornecedores
V3__create_products_table.sql           - Tabela de produtos
V4__create_sales_table.sql              - Tabela de vendas
V5__create_sale_items_table.sql         - Tabela de itens de venda
V6__create_stock_movements_table.sql    - Tabela de movimentações de estoque
V7__insert_initial_data.sql             - Inserção de dados iniciais (NEW!)
```

## 📦 Banco de Dados PostgreSQL

**Banco de dados criado:** `vermolinux`  
**Usuário:** `postgres`  
**Senha:** `Post!Gress!44`  
**Host:** `localhost`  
**Porta:** `5432`

## 🚀 Como Iniciar

### Pré-requisitos
1. PostgreSQL 12+ instalado e rodando
2. Banco de dados `vermolinux` criado
3. Maven 3.9+
4. Java 17+

### Passos

#### 1. Verificar conexão com PostgreSQL
```bash
# Windows (psql deve estar no PATH)
psql -h localhost -U postgres -d vermolinux -c "SELECT version();"

# Ou usar pgAdmin
```

#### 2. Compilar backend
```bash
cd backend
mvn clean package -DskipTests
```

#### 3. Iniciar backend
**Opção 1 - Windows Batch:**
```bash
backend/start-backend.bat
```

**Opção 2 - PowerShell:**
```powershell
cd backend
java -jar target/vermolin-ux-1.0.0.jar
```

**Opção 3 - Maven Spring Boot:**
```bash
cd backend
mvn spring-boot:run
```

#### 4. Iniciar frontend
```bash
cd frontend
npm start
```

Backend estará em: **http://localhost:8080/api**  
Frontend estará em: **http://localhost:4200**

## 📊 Dados Iniciais Inseridos

### Usuários (V1)
- **admin** / admin123 (GERENTE)
- **estoquista1** / senha123 (ESTOQUISTA)
- **caixa1** / senha123 (CAIXA)

### Fornecedores (V7)
- Produções Agrícolas Silva
- Exportadora Hortaliças Brasil
- Distribuidora Frutas Premium

### Produtos (V7)
- 10 produtos distribuídos em categorias:
  - 3 Hortaliças (Alface, Espinafre, Rúcula)
  - 4 Frutas (Maçã, Banana, Laranja, Morango)
  - 3 Raízes (Batata Inglesa, Cenoura, Batata Doce)

### Movimentações de Estoque (V7)
- Entradas iniciais de todos os produtos
- Amostra de movimento (saída, ajuste)

### Vendas de Teste (V7)
- 2 vendas de exemplo com 3 itens cada

## ✅ Verificações

### Verificar se Flyway rodou corretamente
Na inicialização do backend, você verá:
```
Flyway has migrated 7 migrations
```

### Conectar no banco e verificar tabelas
```sql
\dt                           -- Listar tabelas
SELECT * FROM users;         -- Ver usuários
SELECT * FROM products;      -- Ver produtos
SELECT * FROM sales;         -- Ver vendas
```

## 🔄 Diferenças H2 vs PostgreSQL

| Aspecto | H2 | PostgreSQL |
|---------|--|----|
| Tipo | Em memória/Arquivo | Servidor dedicado |
| Persistência | Sessão (ddl-auto=create) | Permanente |
| Migração | DataLoader.java | Flyway SQL |
| Performance | Desenvolvimento | Produção |
| Segurança | Básica | Completa |
| Escalabilidade | Limitada | Ilimitada |

## 🐛 Troubleshooting

### Erro: "Connection refused"
- Verificar se PostgreSQL está rodando
- Verificar host/porta na application.properties

### Erro: "Database vermolinux does not exist"
```sql
CREATE DATABASE vermolinux;
```

### Erro ao executar Flyway
```properties
# Se quiser resetar e reexecutar todas as migrations
spring.flyway.baseline-on-migrate=true
```

### Aplicação não inicia
- Verificar logs do backend
- Validar credenciais PostgreSQL
- Verificar se banco está acessível

## 📝 Próximos Passos

- [ ] Configurar backups do PostgreSQL
- [ ] Implementar replicação/HA
- [ ] Otimizar índices de banco de dados
- [ ] Implementar testes de integração
- [ ] Configurar monitoramento
- [ ] Fazer deploy em produção

---

**Data de Migração:** 19/11/2025  
**Versão Backend:** 1.0.0  
**Banco de Dados:** PostgreSQL 14+
