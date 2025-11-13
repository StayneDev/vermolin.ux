# 🍎 vermolin.ux - Sistema de Gestão para Hortifruti

[![Backend](https://img.shields.io/badge/Backend-100%25-success)](docs/STATUS.md)
[![Frontend](https://img.shields.io/badge/Frontend-Angular-red)](frontend/README.md)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)](https://spring.io)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

Sistema completo de gestão para hortifruti com **PDV (Ponto de Venda)**, controle de estoque, gestão de fornecedores e autenticação multi-nível.

---

## 🚀 Início Rápido

### ⚠️ Pré-requisitos

**Backend:**
1. **Java JDK 17+**
   - Download: https://adoptium.net/
   - Verifique: `java -version`

2. **Apache Maven**
   - Download: https://maven.apache.org/download.cgi
   - Verifique: `mvn -version`

**Frontend:**
3. **Node.js 18+**
   - Download: https://nodejs.org/
   - Verifique: `node -version`

> 📖 **Guia completo de instalação:** [SETUP.md](SETUP.md)

### 💻 Executar o Sistema

#### 🔹 Backend (Spring Boot)

**Windows:**
```cmd
# Opção 1: Script batch (RECOMENDADO - sem problemas de permissão)
start-backend.bat

# Opção 2: PowerShell
powershell -ExecutionPolicy Bypass -File .\start-backend.ps1

# Opção 3: Manual
cd backend
mvn clean install -DskipTests
mvn spring-boot:run
```

**Linux/Mac:**
```bash
cd backend
mvn clean install -DskipTests
mvn spring-boot:run
```

**✅ Backend rodando em:** http://localhost:8080/api  
**📚 Swagger UI:** http://localhost:8080/api/swagger-ui.html

#### 🔹 Frontend (Angular)

```cmd
# 1. Instalar dependências (primeira vez)
cd frontend
install-frontend.bat

# 2. Iniciar servidor de desenvolvimento
start-frontend.bat
```

**✅ Frontend rodando em:** http://localhost:4200


### 🌐 Acessar o Sistema

Após iniciar, acesse:
- **Swagger UI:** http://localhost:8080/api/swagger-ui.html
- **API Base:** http://localhost:8080/api

---

## 👥 Usuários de Teste

| Perfil | Username | Senha | Permissões |
|--------|----------|-------|------------|
| 🎩 **GERENTE** | `gerente` | `gerente123` | Acesso total ao sistema |
| 📦 **ESTOQUISTA** | `estoquista` | `estoque123` | Produtos, Estoque, Fornecedores |
| 💰 **CAIXA** | `caixa` | `caixa123` | Vendas (PDV) |

---

## ✨ Principais Funcionalidades

### 🔐 Autenticação Segura
- Login com **JWT (JSON Web Token)**
- 3 níveis de acesso com permissões diferenciadas
- Senhas criptografadas com **BCrypt**

### 💰 PDV - Ponto de Venda
- ✅ Abrir e gerenciar vendas
- ✅ Adicionar/remover produtos
- ✅ Múltiplas formas de pagamento (Dinheiro, PIX, Débito, Crédito)
- ✅ **Cálculo automático de troco**
- ✅ **Atualização automática de estoque**

### 📦 Gestão de Produtos
- ✅ CRUD completo (Criar, Listar, Editar, Excluir)
- ✅ **Visualização filtrada por perfil:**
  - Caixa: vê apenas nome, preço e quantidade
  - Gerente/Estoquista: veem todas as informações
- ✅ Alerta de produtos com estoque baixo

### 📊 Gestão de Estoque
- ✅ Entrada, saída e ajuste de inventário
- ✅ **Rastreabilidade completa** (quem fez, quando fez)
- ✅ Histórico de movimentações

### 🏪 Gestão de Fornecedores
- ✅ Cadastro completo (CNPJ, telefone, email, endereço)
- ✅ Vínculo com produtos

### 👤 Gestão de Usuários (Gerente)
- ✅ Cadastro de novos usuários
- ✅ Atribuição de perfis de acesso

---

## 🧪 Testando a API no Swagger

### 1️⃣ Fazer Login

1. Acesse: http://localhost:8080/api/swagger-ui.html
2. Encontre `POST /api/auth/login`
3. Clique em **"Try it out"**
4. Use as credenciais:
```json
{
  "username": "caixa",
  "password": "caixa123"
}
```
5. Copie o `token` da resposta

### 2️⃣ Autorizar no Swagger

1. Clique no botão **"Authorize"** (cadeado no topo)
2. Cole: `Bearer <seu-token-aqui>`
3. Clique em **"Authorize"**

### 3️⃣ Exemplo: Fluxo de Venda Completo

**a) Abrir venda:**
```
POST /api/sales
```

**b) Adicionar produto:**
```json
POST /api/sales/1/items
{
  "productId": 1,
  "quantity": 2.5,
  "weighed": true
}
```

**c) Finalizar com pagamento:**
```json
POST /api/sales/1/finalize
{
  "paymentMethod": "DINHEIRO",
  "amountPaid": 20.00
}
```

**✅ Resultado:** Troco calculado automaticamente e estoque atualizado!

---

## 🎯 Requisitos Funcionais

**Implementados:** 35/35 (100%) ✅

- ✅ **RF1-RF5:** Autenticação e Autorização
- ✅ **RF6-RF8:** Auditoria e Validações
- ✅ **RF9-RF10:** Visualização por Perfil
- ✅ **RF11-RF18:** Fluxo Completo de Vendas (PDV)
- ✅ **RF19-RF21:** Gestão de Estoque
- ✅ **RF22-RF25:** Gestão de Produtos
- ✅ **RF26-RF29:** Gestão de Usuários
- ✅ **RF30-RF35:** Gestão de Fornecedores

---

## 💾 Dados Pré-cadastrados

### Produtos
- 🍌 Banana Prata - R$ 5,50/kg (100 kg)
- 🍅 Tomate - R$ 7,00/kg (50 kg)
- 🥬 Alface Crespa - R$ 2,50/un (30 un)
- 🍊 Laranja - R$ 4,00/kg (8 kg) ⚠️ **ESTOQUE BAIXO**
- 🥚 Ovos - R$ 12,00/dz (20 dz)

### Fornecedores
- 📞 Frutas Silva - (11) 98765-4321
- 📞 Hortifruti Bom Jardim - (11) 91234-5678
- 📞 Distribuidora Verde Vale - (11) 99999-0000

---

## 📚 Documentação

| Documento | Descrição |
|-----------|-----------|
| [**APRESENTACAO.md**](APRESENTACAO.md) | 🎤 Roteiro para apresentação/seminário (15 min) |
| [**SETUP.md**](SETUP.md) | ✅ Guia de instalação (Java, Maven, troubleshooting) |
| [docs/STATUS.md](docs/STATUS.md) | 📊 Status de implementação dos requisitos |
| [docs/DB_INTEGRATION.md](docs/DB_INTEGRATION.md) | 🗄️ Migração para banco de dados |
| [docs/design.md](docs/design.md) | 🎨 Design System (cores, tipografia, componentes) |
| [docs/requisitos/](docs/requisitos/) | 📋 Requisitos funcionais detalhados |
| [docs/diagramas/](docs/diagramas/) | 📐 Diagramas UML (casos de uso, classes, sequência) |

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Uso |
|------------|--------|-----|
| **Java** | 17 | Linguagem principal |
| **Spring Boot** | 3.2.0 | Framework backend |
| **Spring Security** | 6.x | Autenticação JWT |
| **Swagger/OpenAPI** | 3.0 | Documentação da API |
| **Maven** | 3.x | Build tool |
| **Lombok** | 1.18.30 | Redução de boilerplate |
| **JJWT** | 0.12.3 | Geração e validação de tokens |

---

## 🎓 Sobre o Projeto

Desenvolvido como **Projeto Integrado Multidisciplinar III (PIM III)** do curso de **Análise e Desenvolvimento de Sistemas** da **UNIP**.

### Objetivos Alcançados:
- ✅ Arquitetura MVC bem definida
- ✅ API REST completa e documentada
- ✅ Autenticação JWT segura
- ✅ Padrões de Projeto aplicados
- ✅ Boas Práticas de desenvolvimento
- ✅ 35 Requisitos Funcionais implementados

---

## 📞 Suporte

- **Documentação Interativa:** http://localhost:8080/api/swagger-ui.html
- **Guia de Apresentação:** [APRESENTACAO.md](APRESENTACAO.md)
- **Instalação/Configuração:** [SETUP.md](SETUP.md)
- **Issues:** GitHub Issues

---

## 📄 Licença

Este projeto está sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.

---

<div align="center">

**Desenvolvido com ❤️ pelo melhor grupo do PIM existente - UNIP**

[![GitHub](https://img.shields.io/badge/GitHub-makalyster-blue?logo=github)](https://github.com/makalyster)

</div>
