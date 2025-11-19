# ⚙️ SETUP - Vermolin.UX

**Guia completo de instalação, configuração e verificação do sistema.**

---

## 📋 PASSO 1: PRÉ-REQUISITOS

### Java JDK 17+
```powershell
# Verificar
java -version

# Esperado: openjdk version "17.0.x" ou superior
# Se não tiver: https://adoptium.net/ → Download JDK 17
# Após instalar: Reinicie o terminal
```

### Apache Maven 3.8+
```powershell
# Verificar
mvn -version

# Esperado: Apache Maven 3.8.x ou superior
# Se não tiver: https://maven.apache.org/download.cgi
# Extraia e adicione \bin ao PATH
# Após instalar: Reinicie o terminal
```

### PostgreSQL 14+
```powershell
# Verificar
psql --version

# Esperado: psql (PostgreSQL) 14.x ou superior
# Se não tiver: https://www.postgresql.org/download/
# Após instalar: Reinicie o terminal
```

### Node.js 18+ (Frontend)
```powershell
# Verificar
node --version
npm --version

# Esperado: v18.x ou superior
# Se não tiver: https://nodejs.org/
```

---

## 📦 PASSO 2: CONFIGURAR BANCO DE DADOS

### 1. Criar Database PostgreSQL

```sql
-- Abra o pgAdmin ou use comando:
psql -U postgres

-- Execute:
CREATE DATABASE vermolinux
  WITH ENCODING 'UTF8'
  LOCALE 'pt_BR.UTF-8';

-- Confirme:
\l
-- Deve listar 'vermolinux' em VERDE
```

### 2. Configuração Padrão (IMPORTANTE)
- **Host:** `localhost`
- **Port:** `5432`
- **Database:** `vermolinux`
- **User:** `postgres`
- **Password:** `Post!Gress!44` (Se pedisse durante instalação)

**Arquivo:** `backend/src/main/resources/application.properties`
```properties
# Banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/vermolinux
spring.datasource.username=postgres
spring.datasource.password=Post!Gress!44

# Flyway migrations automáticas
spring.flyway.enabled=true
spring.flyway.baselineOnMigrate=true

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
```

---

## 🚀 PASSO 3: EXECUTAR O BACKEND

**Windows:**
```powershell
cd backend
mvn clean install -DskipTests
mvn spring-boot:run
```

### Verificar Execução

```
✅ Esperado (após ~30 segundos):
  - "Tomcat started on port(s): 8080"
  - "Started VermolinUxApplication"
```

**Testar Backend:**
```powershell
# Em outro terminal, execute:
curl http://localhost:8080/api/auth/login -X POST -H "Content-Type: application/json" -d "{\"username\":\"gerente\",\"password\":\"gerente123\"}"

# Deve retornar: token JWT com sucesso
```

---

## 🎨 PASSO 4: EXECUTAR O FRONTEND

**Windows:**

```powershell
cd frontend
npm install           # (primeira vez apenas)
npm start             # ou: ng serve
```

### Verificar Execução

```
✅ Esperado (após ~20 segundos):
  - "✔ Compiled successfully"
  - "Application bundle generation complete"
  - "Local: http://localhost:4200"
```

**Acessar:** http://localhost:4200

---

## 🔐 PASSO 5: TESTAR O SISTEMA

### 1. Login no Swagger

1. Acesse: http://localhost:8080/api/swagger-ui.html
2. Clique em "Authorize" (cadeado no topo)
3. Use credenciais:

| Perfil | Username | Senha | Permissões |
|--------|----------|-------|------------|
| Gerente | `gerente` | `gerente123` | Acesso total |
| Estoquista | `estoquista` | `estoque123` | Estoque + Produtos |
| Caixa | `caixa` | `caixa123` | Vendas (PDV) |

### 2. Teste de Fluxo de Venda

**a) POST /api/sales** (abrir venda)
```json
{}
```

**b) POST /api/sales/{id}/items** (adicionar produto)
```json
{
  "productId": 1,
  "quantity": 2.5
}
```

**c) POST /api/sales/{id}/finalize** (finalizar)
```json
{
  "paymentMethod": "DINHEIRO",
  "amountPaid": 20.00
}
```

## 🛠️ TROUBLESHOOTING

### Erro: "java command not found"
```powershell
# Solução: Adicione Java ao PATH
# Windows: Painel de Controle → Sistema → Variáveis de Ambiente
# Procure por C:\Program Files\Eclipse Adoptium\jdk-17.x.x
# Copie o caminho e adicione em PATH

# Reinicie o terminal e tente novamente
```

### Erro: "mvn command not found"
```powershell
# Solução: Mesma que acima, mas para Maven
# C:\Program Files\Apache\maven\bin
```

### Erro: "PostgreSQL connection refused"
```powershell
# Verificar se PostgreSQL está rodando:
# Windows: Services → postgresql-x64-14 → Running?

# Se não estiver:
# 1. Abra "Services" (services.msc)
# 2. Procure "postgresql-x64-14"
# 3. Clique direito → Start
```

### Erro: "Port 8080 already in use"
```powershell
# Encontre o processo usando porta 8080:
netstat -ano | findstr :8080

# Mate o processo:
taskkill /PID <PID> /F

# Ou use outra porta em application.properties:
# server.port=8081
```

### Erro: "Database 'vermolinux' does not exist"
```powershell
# Crie o banco manualmente:
psql -U postgres -c "CREATE DATABASE vermolinux;"

# Ou execute via pgAdmin:
# 1. Abra pgAdmin
# 2. Clique direito em Databases
# 3. Create → Database
# 4. Nome: vermolinux
# 5. Save
```

### Erro: "Flyway migration failed"
```powershell
# Solução: Limpe o banco e deixe Flyway recriar:
# 1. Abra pgAdmin
# 2. Clique direito em 'vermolinux'
# 3. Delete/Drop
# 4. Crie database novamente
# 5. Reinicie backend (Flyway recriará automaticamente)
```

### Erro: "npm ERR! 404"
```powershell
# Limpe cache npm e reinstale
cd frontend
npm cache clean --force
npm install
npm start
```

## 🎯 ENDPOINTS PRINCIPAIS

| Método | Endpoint | Permissão | Descrição |
|--------|----------|-----------|-----------|
| POST | `/api/auth/login` | Público | Login com JWT |
| POST | `/api/auth/logout` | Público | Logout |
| GET | `/api/products` | Todos | Listar produtos (filtrado por perfil) |
| POST | `/api/sales` | CAIXA | Abrir nova venda |
| POST | `/api/sales/{id}/finalize` | CAIXA | Finalizar com pagamento |
| GET | `/api/sales` | GERENTE | Histórico de vendas |
| POST | `/api/stock` | ESTOQUISTA | Registrar movimentação |
| POST | `/api/users` | GERENTE | Criar usuário |
| POST | `/api/suppliers` | GERENTE | Cadastrar fornecedor |

**API Completa:** http://localhost:8080/api/swagger-ui.html

---

## 💡 DICAS DE PRODUTIVIDADE

### Reload automático do Frontend
```powershell
cd frontend
ng serve --poll=2000  # Detecta mudanças automaticamente
```

### Rebuild rápido do Backend
```powershell
cd backend
mvn install -DskipTests  # Pula testes, mais rápido
```

### Ver logs do Backend
```powershell
# Em real-time:
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx512m"

# Arquivo de log:
# backend/logs/application.log
```

### Resetar banco de dados
```powershell
# Opção 1: Via pgAdmin
# Delete database, crie nova, reinicie backend

# Opção 2: Via Flyway (automático ao resetar)
# Backend detectará tabelas vazias e executará V1-V7
```

---

**Versão:** 3.0.0 | **Data:** 19/11/2025 | **Status:** ✅ Pronto para produção

