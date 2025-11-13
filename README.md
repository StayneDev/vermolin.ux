# 🍎 Vermolin.UX - Sistema de Gestão para Hortifruti# 🍎 Vermolin.UX - Sistema de Gestão para Hortifruti# 🍎 Vermolin.UX - Sistema de Gestão para Hortifruti# 🍎 Vermolin.UX - Sistema de Gestão para Hortifruti# 🥬 Vermolin.UX - Sistema de Gestão de Hortifruti



[![Backend](https://img.shields.io/badge/Backend-100%25-success)](docs/STATUS.md)

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)](https://spring.io)

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/)[![Backend](https://img.shields.io/badge/Backend-100%25-success)](docs/STATUS.md)



Sistema completo de gestão para hortifruti com **PDV (Ponto de Venda)**, controle de estoque, gestão de fornecedores e autenticação multi-nível.[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)](https://spring.io)



---[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/)[![Backend](https://img.shields.io/badge/Backend-100%25-success)](docs/STATUS.md)



## 🚀 Início Rápido[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)



### ⚠️ Pré-requisitos[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)](https://spring.io)



**Antes de executar, certifique-se de ter instalado:**Sistema completo de gestão para hortifruti com **PDV (Ponto de Venda)**, controle de estoque, gestão de fornecedores e autenticação multi-nível.



1. **Java JDK 17+** [![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/)[![Backend](https://img.shields.io/badge/Backend-100%25_Completo-success)](./BACKEND_COMPLETO.md)![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)

   - Download: https://adoptium.net/

   - Verifique: `java -version`---



2. **Apache Maven**[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

   - Download: https://maven.apache.org/download.cgi

   - Verifique: `mvn -version`## 🚀 Início Rápido



> 📖 **Guia completo de instalação:** [SETUP.md](SETUP.md)[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)](https://spring.io/projects/spring-boot)![Java](https://img.shields.io/badge/Java-17-orange)



### Executar o Sistema### Pré-requisitos



**Windows:**Sistema completo de gestão para hortifruti com **PDV (Ponto de Venda)**, controle de estoque, gestão de fornecedores e autenticação multi-nível.

```cmd

# Opção 1: Script batch (RECOMENDADO)- **Java JDK 17+** ([Download](https://adoptium.net/))

start-backend.bat

- **Apache Maven** ([Download](https://maven.apache.org/download.cgi))[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/)![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)

# Opção 2: PowerShell com permissões

powershell -ExecutionPolicy Bypass -File start-backend.ps1



# Opção 3: Manualmente### Executar o Sistema---

cd backend

mvn clean install -DskipTests

mvn spring-boot:run

```**Windows:**[![Angular](https://img.shields.io/badge/Angular-17-red)](https://angular.io/)![Angular](https://img.shields.io/badge/Angular-17-red)



**Linux/Mac:**```cmd

```bash

cd backend# Opção 1: Script batch (RECOMENDADO)## 🚀 Início Rápido

mvn clean install -DskipTests

mvn spring-boot:runstart-backend.bat

```

[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)![License](https://img.shields.io/badge/license-MIT-blue)

### Acessar o Sistema

# Opção 2: PowerShell com permissões

Após iniciar, acesse:

- **Swagger UI:** http://localhost:8080/api/swagger-ui.htmlpowershell -ExecutionPolicy Bypass -File start-backend.ps1### Pré-requisitos

- **API Base:** http://localhost:8080/api



---

# Opção 3: Manualmente

## 👥 Usuários de Teste

cd backend

| Perfil | Username | Senha | Permissões |

|--------|----------|-------|------------|mvn clean install -DskipTests- **Java JDK 17+** ([Download](https://adoptium.net/))

| 🎩 **GERENTE** | `gerente` | `gerente123` | Acesso total ao sistema |

| 📦 **ESTOQUISTA** | `estoquista` | `estoque123` | Produtos, Estoque, Fornecedores |mvn spring-boot:run

| 💰 **CAIXA** | `caixa` | `caixa123` | Vendas (PDV) |

```- **Apache Maven** ([Download](https://maven.apache.org/download.cgi))Sistema completo de gestão para estabelecimentos de hortifruti, incluindo **PDV (Ponto de Venda)**, controle de estoque, gestão de fornecedores e relatórios gerenciais.Sistema completo de gestão para hortifruti, desenvolvido com **Spring Boot (backend)** e **Angular (frontend)**, seguindo o padrão **MVC** e as melhores práticas de desenvolvimento.

---



## ✨ Principais Funcionalidades

**Linux/Mac:**

### 🔐 Autenticação Segura

- Login com **JWT (JSON Web Token)**```bash

- 3 níveis de acesso com permissões diferenciadas

- Senhas criptografadas com **BCrypt**cd backend### Executar o Sistema



### 💰 PDV - Ponto de Vendamvn clean install -DskipTests

- ✅ Abrir e gerenciar vendas

- ✅ Adicionar/remover produtosmvn spring-boot:run

- ✅ Múltiplas formas de pagamento (Dinheiro, PIX, Débito, Crédito)

- ✅ **Cálculo automático de troco**```

- ✅ **Atualização automática de estoque**

```powershell------

### 📦 Gestão de Produtos

- ✅ CRUD completo (Criar, Listar, Editar, Excluir)### Acessar o Sistema

- ✅ **Visualização filtrada por perfil:**

  - Caixa: vê apenas nome, preço e quantidade# Clone o repositório

  - Gerente/Estoquista: veem todas as informações

- ✅ Alerta de produtos com estoque baixoApós iniciar, acesse:



### 📊 Gestão de Estoque- **Swagger UI:** http://localhost:8080/api/swagger-ui.htmlgit clone https://github.com/makalyster/vemolin.ux.git

- ✅ Entrada, saída e ajuste de inventário

- ✅ **Rastreabilidade completa** (quem fez, quando fez)- **API Base:** http://localhost:8080/api

- ✅ Histórico de movimentações

cd vemolin.ux

### 🏪 Gestão de Fornecedores

- ✅ Cadastro completo (CNPJ, telefone, email, endereço)---

- ✅ Vínculo com produtos

## ✨ Status Atual## 📋 Índice

### 👤 Gestão de Usuários (Gerente)

- ✅ Cadastro de novos usuários## 👥 Usuários de Teste

- ✅ Atribuição de perfis de acesso

# Execute o script de inicialização

---

| Perfil | Username | Senha | Permissões |

## 🧪 Testando a API

|--------|----------|-------|------------|.\start-backend.ps1

### 1. Fazer Login no Swagger

| 🎩 **GERENTE** | `gerente` | `gerente123` | Acesso total ao sistema |

1. Acesse: http://localhost:8080/api/swagger-ui.html

2. Encontre `POST /api/auth/login`| 📦 **ESTOQUISTA** | `estoquista` | `estoque123` | Produtos, Estoque, Fornecedores |```

3. Clique em **"Try it out"**

4. Use as credenciais:| 💰 **CAIXA** | `caixa` | `caixa123` | Vendas (PDV) |

```json

{✅ **Backend Spring Boot:** 100% Completo - 35/35 Requisitos Funcionais  - [Sobre o Projeto](#sobre-o-projeto)

  "username": "caixa",

  "password": "caixa123"---

}

```### Acessar o Sistema

5. Copie o `token` da resposta

## ✨ Principais Funcionalidades

### 2. Autorizar no Swagger

⏳ **Frontend Angular:** Em desenvolvimento  - [Tecnologias Utilizadas](#tecnologias-utilizadas)

1. Clique no botão **"Authorize"** (cadeado no topo)

2. Cole: `Bearer <seu-token-aqui>`### 🔐 Autenticação Segura

3. Clique em **"Authorize"**

- Login com **JWT (JSON Web Token)**Após iniciar, acesse:

### 3. Fluxo de Venda Completo

- 3 níveis de acesso com permissões diferenciadas

**a) Abrir venda:** `POST /api/sales`

- Senhas criptografadas com **BCrypt**- **Swagger UI (Documentação Interativa):** http://localhost:8080/api/swagger-ui.html📚 **Documentação:** Completa com Swagger/OpenAPI  - [Arquitetura](#arquitetura)

**b) Adicionar produto:**

```json

POST /api/sales/1/items

{### 💰 PDV - Ponto de Venda- **API Base:** http://localhost:8080/api

  "productId": 1,

  "quantity": 2.5,- ✅ Abrir e gerenciar vendas

  "weighed": true

}- ✅ Adicionar/remover produtos- [Requisitos Funcionais](#requisitos-funcionais)

```

- ✅ Múltiplas formas de pagamento (Dinheiro, PIX, Débito, Crédito)

**c) Finalizar com pagamento:**

```json- ✅ **Cálculo automático de troco**---

POST /api/sales/1/finalize

{- ✅ **Atualização automática de estoque**

  "paymentMethod": "DINHEIRO",

  "amountPaid": 20.00Veja detalhes em: [`BACKEND_COMPLETO.md`](./BACKEND_COMPLETO.md)- [Instalação e Execução](#instalação-e-execução)

}

```### 📦 Gestão de Produtos



**✅ Resultado:** Troco calculado automaticamente e estoque atualizado!- ✅ CRUD completo (Criar, Listar, Editar, Excluir)## 👥 Usuários de Teste



---- ✅ **Visualização filtrada por perfil:**



## 🎯 Requisitos Funcionais  - Caixa: vê apenas nome, preço e quantidade- [Endpoints da API](#endpoints-da-api)



**Implementados:** 35/35 (100%) ✅  - Gerente/Estoquista: veem todas as informações



- ✅ RF1-RF5: Autenticação e Autorização- ✅ Alerta de produtos com estoque baixo| Perfil | Username | Senha | Permissões |

- ✅ RF6-RF8: Auditoria e Validações

- ✅ RF9-RF10: Visualização por Perfil

- ✅ RF11-RF18: Fluxo Completo de Vendas (PDV)

- ✅ RF19-RF21: Gestão de Estoque### 📊 Gestão de Estoque|--------|----------|-------|------------|---- [Estrutura do Projeto](#estrutura-do-projeto)

- ✅ RF22-RF25: Gestão de Produtos

- ✅ RF26-RF29: Gestão de Usuários- ✅ Entrada, saída e ajuste de inventário

- ✅ RF30-RF35: Gestão de Fornecedores

- ✅ **Rastreabilidade completa** (quem fez, quando fez)| 🎩 **GERENTE** | `gerente` | `gerente123` | Acesso total ao sistema |

---

- ✅ Histórico de movimentações

## 💾 Dados Pré-cadastrados

| 📦 **ESTOQUISTA** | `estoquista` | `estoque123` | Produtos, Estoque, Fornecedores |- [Integração com Banco de Dados](#integração-com-banco-de-dados)

### Produtos

- 🍌 Banana Prata - R$ 5,50/kg (100 kg)### 🏪 Gestão de Fornecedores

- 🍅 Tomate - R$ 7,00/kg (50 kg)

- 🥬 Alface Crespa - R$ 2,50/un (30 un)- ✅ Cadastro completo (CNPJ, telefone, email, endereço)| 💰 **CAIXA** | `caixa` | `caixa123` | Vendas (PDV) |

- 🍊 Laranja - R$ 4,00/kg (8 kg) ⚠️ **ESTOQUE BAIXO**

- 🥚 Ovos - R$ 12,00/dz (20 dz)- ✅ Vínculo com produtos



### Fornecedores## 🚀 Quick Start- [Roadmap](#roadmap)

- 📞 Frutas Silva - (11) 98765-4321

- 📞 Hortifruti Bom Jardim - (11) 91234-5678### 👤 Gestão de Usuários (Gerente)

- 📞 Distribuidora Verde Vale - (11) 99999-0000

- ✅ Cadastro de novos usuários---

---

- ✅ Atribuição de perfis de acesso

## 📚 Documentação

- [Licença](#licença)

| Documento | Descrição |

|-----------|-----------|---

| [**APRESENTACAO.md**](APRESENTACAO.md) | 🎤 Roteiro para apresentação/seminário |

| [**SETUP.md**](SETUP.md) | ✅ Checklist de configuração e instalação do Maven |## ✨ Principais Funcionalidades

| [docs/STATUS.md](docs/STATUS.md) | 📊 Status de implementação |

| [docs/DB_INTEGRATION.md](docs/DB_INTEGRATION.md) | 🗄️ Migração para banco de dados |## 🧪 Testando a API

| [docs/requisitos/](docs/requisitos/) | 📋 Requisitos funcionais |

| [docs/diagramas/](docs/diagramas/) | 📐 Diagramas UML |### Executar o Backend



---### 1. Fazer Login no Swagger



## 🛠️ Tecnologias### 🔐 Autenticação Segura



| Tecnologia | Versão | Uso |1. Acesse: http://localhost:8080/api/swagger-ui.html

|------------|--------|-----|

| Java | 17 | Linguagem principal |2. Encontre `POST /api/auth/login`- Login com **JWT (JSON Web Token)**---

| Spring Boot | 3.2.0 | Framework backend |

| Spring Security | 6.x | Autenticação JWT |3. Clique em **"Try it out"**

| Swagger/OpenAPI | 3.0 | Documentação da API |

| Maven | 3.x | Build tool |4. Use as credenciais:- 3 níveis de acesso com permissões diferenciadas



---```json



## 🎓 Sobre o Projeto{- Senhas criptografadas com **BCrypt**```powershell



Desenvolvido como **Projeto Integrado Multidisciplinar III (PIM III)** do curso de **Análise e Desenvolvimento de Sistemas** da **UNIP**.  "username": "caixa",



**Objetivos:**  "password": "caixa123"

- ✅ Arquitetura MVC

- ✅ API REST}

- ✅ Autenticação JWT

- ✅ Padrões de Projeto```### 💰 PDV - Ponto de Venda# Opção 1: Usar script PowerShell## 🎯 Sobre o Projeto

- ✅ Boas Práticas

5. Copie o `token` da resposta

---

- ✅ Abrir e gerenciar vendas

## 📞 Suporte

### 2. Autorizar no Swagger

- **Documentação:** http://localhost:8080/api/swagger-ui.html

- **Guia de Apresentação:** [APRESENTACAO.md](APRESENTACAO.md)- ✅ Adicionar/remover produtos.\start-backend.ps1

- **Instalação Maven:** [SETUP.md](SETUP.md)

1. Clique no botão **"Authorize"** (cadeado no topo)

---

2. Cole: `Bearer <seu-token-aqui>`- ✅ Múltiplas formas de pagamento (Dinheiro, PIX, Débito, Crédito)

<div align="center">

3. Clique em **"Authorize"**

**Desenvolvido com ❤️ para o PIM III - UNIP**

- ✅ **Cálculo automático de troco**O **Vermolin.UX** é um sistema completo de gestão desenvolvido especificamente para hortifrútis, com foco em:

[![GitHub](https://img.shields.io/badge/GitHub-makalyster-blue?logo=github)](https://github.com/makalyster)

### 3. Fluxo de Venda Completo

</div>

- ✅ **Atualização automática de estoque**

**a) Abrir venda:** `POST /api/sales`

# Opção 2: Manualmente

**b) Adicionar produto:**

```json### 📦 Gestão de Produtos

POST /api/sales/1/items

{- ✅ CRUD completo (Criar, Listar, Editar, Excluir)cd backend- ✅ **Controle de vendas** (PDV - Ponto de Venda)

  "productId": 1,

  "quantity": 2.5,- ✅ Controle de validade e estoque mínimo

  "weighed": true

}- ✅ **Visualização filtrada por perfil:**mvn clean install- ✅ **Gestão de estoque** (entradas, saídas, ajustes)

```

  - Caixa: vê apenas nome, preço e quantidade

**c) Finalizar com pagamento:**

```json  - Gerente/Estoquista: veem todas as informaçõesmvn spring-boot:run- ✅ **Cadastro de produtos** com informações detalhadas

POST /api/sales/1/finalize

{- ✅ Alerta de produtos com estoque baixo

  "paymentMethod": "DINHEIRO",

  "amountPaid": 20.00```- ✅ **Gestão de fornecedores**

}

```### 📊 Gestão de Estoque



**✅ Resultado:** Troco calculado automaticamente e estoque atualizado!- ✅ Entrada de mercadorias- ✅ **Controle de usuários** com permissões por cargo



---- ✅ Saída por perdas/vencimento



## 🎯 Requisitos Funcionais- ✅ Ajuste manual de inventário**Acesse:**- ✅ **Histórico e auditoria** de operações



**Implementados:** 35/35 (100%) ✅- ✅ **Rastreabilidade completa** (quem fez, quando fez)



- ✅ RF1-RF5: Autenticação e Autorização- ✅ Histórico de movimentações- API: http://localhost:8080/api- ✅ **Notificações de estoque baixo**

- ✅ RF6-RF8: Auditoria e Validações

- ✅ RF9-RF10: Visualização por Perfil

- ✅ RF11-RF18: Fluxo Completo de Vendas (PDV)

- ✅ RF19-RF21: Gestão de Estoque### 🏪 Gestão de Fornecedores- Swagger UI: http://localhost:8080/api/swagger-ui.html

- ✅ RF22-RF25: Gestão de Produtos

- ✅ RF26-RF29: Gestão de Usuários- ✅ Cadastro completo (CNPJ, telefone, email, endereço)

- ✅ RF30-RF35: Gestão de Fornecedores

- ✅ Vínculo com produtos### Cargos e Permissões

---

- ✅ Consulta de contato para reposição

## 💾 Dados Pré-cadastrados

### Usuários Pré-cadastrados

### Produtos

- 🍌 Banana Prata - R$ 5,50/kg (100 kg)### 👤 Gestão de Usuários (Gerente)

- 🍅 Tomate - R$ 7,00/kg (50 kg)

- 🥬 Alface Crespa - R$ 2,50/un (30 un)- ✅ Cadastro de novos usuáriosO sistema implementa **3 perfis de acesso**:

- 🍊 Laranja - R$ 4,00/kg (8 kg) ⚠️ **ESTOQUE BAIXO**

- 🥚 Ovos - R$ 12,00/dz (20 dz)- ✅ Atribuição de perfis de acesso



### Fornecedores- ✅ Ativação/desativação de contas| Role | Username | Senha | Permissões |

- 📞 Frutas Silva - (11) 98765-4321

- 📞 Hortifruti Bom Jardim - (11) 91234-5678

- 📞 Distribuidora Verde Vale - (11) 99999-0000

---|------|----------|-------|------------|| Cargo | Permissões |

---



## 📚 Documentação

## 🏗️ Arquitetura| GERENTE | `gerente` | `gerente123` | Acesso total ||-------|-----------|

| Documento | Descrição |

|-----------|-----------|

| [**APRESENTACAO.md**](APRESENTACAO.md) | 🎤 Roteiro para apresentação/seminário |

| [**SETUP.md**](SETUP.md) | ✅ Checklist de configuração |### Stack Tecnológico| ESTOQUISTA | `estoquista` | `estoque123` | Produtos, Estoque, Fornecedores || **Gerente** | Acesso total: CRUD de produtos, usuários e fornecedores; visualização de relatórios |

| [docs/STATUS.md](docs/STATUS.md) | 📊 Status de implementação |

| [docs/DB_INTEGRATION.md](docs/DB_INTEGRATION.md) | 🗄️ Migração para banco de dados |

| [docs/requisitos/](docs/requisitos/) | 📋 Requisitos funcionais |

| [docs/diagramas/](docs/diagramas/) | 📐 Diagramas UML |```| CAIXA | `caixa` | `caixa123` | Vendas (PDV) || **Estoquista** | Gerenciamento de estoque: entradas, saídas e ajustes; consulta completa de produtos |



---Spring Boot 3.2.0 (Java 17)



## 🛠️ Tecnologias├── Spring Security + JWT| **Caixa** | Operações de venda (PDV); consulta limitada de produtos (sem fornecedor/validade) |



| Tecnologia | Versão | Uso |├── Spring Web (REST API)

|------------|--------|-----|

| Java | 17 | Linguagem principal |├── Bean Validation---

| Spring Boot | 3.2.0 | Framework backend |

| Spring Security | 6.x | Autenticação JWT |├── Lombok

| Swagger/OpenAPI | 3.0 | Documentação da API |

| Maven | 3.x | Build tool |└── Swagger/OpenAPI 3---



---```



## 🎓 Sobre o Projeto## ✨ Funcionalidades



Desenvolvido como **Projeto Integrado Multidisciplinar III (PIM III)** do curso de **Análise e Desenvolvimento de Sistemas** da **UNIP**.### Padrão de Projeto



**Objetivos:**## 🚀 Tecnologias Utilizadas

- ✅ Arquitetura MVC

- ✅ API REST```

- ✅ Autenticação JWT

- ✅ Padrões de Projeto📁 backend/### 🔐 Autenticação e Autorização

- ✅ Boas Práticas

├── 📄 controllers/    → Endpoints REST

---

├── 📄 services/       → Lógica de negócio- ✅ Login com JWT (RF1, RF2, RF3)### Backend

## 📞 Suporte

├── 📄 repositories/   → Acesso a dados

- **Documentação:** http://localhost:8080/api/swagger-ui.html

- **Guia de Apresentação:** [APRESENTACAO.md](APRESENTACAO.md)├── 📄 models/         → Entidades- ✅ 3 tipos de usuário: GERENTE, ESTOQUISTA, CAIXA- **Java 17** + **Spring Boot 3.2.0**

- **Configuração:** [SETUP.md](SETUP.md)

├── 📄 dto/            → Data Transfer Objects

---

├── 📄 security/       → JWT + Spring Security- ✅ Controle granular de permissões por endpoint (RF4, RF5)- **Spring Security** + **JWT**

## 📄 Licença

└── 📄 exceptions/     → Tratamento de erros

MIT License - Consulte [LICENSE](LICENSE)

```- **Swagger/OpenAPI 3**

---



<div align="center">

---### 💰 PDV - Ponto de Venda- **Lombok** + **Maven**

**Desenvolvido com ❤️ para o PIM III - UNIP**



[![GitHub](https://img.shields.io/badge/GitHub-makalyster-blue?logo=github)](https://github.com/makalyster)

## 📚 Documentação- ✅ Abrir transação de venda (RF11)

</div>



| Documento | Descrição |- ✅ Adicionar/remover produtos (RF12, RF13)### Frontend

|-----------|-----------|

| [**APRESENTACAO.md**](APRESENTACAO.md) | 🎤 Roteiro completo para apresentação/seminário |- ✅ Pesar produtos em tempo real (RF14)- **Angular 17** + **TypeScript**

| [docs/STATUS.md](docs/STATUS.md) | 📊 Status de implementação (35/35 RFs) |

| [docs/DB_INTEGRATION.md](docs/DB_INTEGRATION.md) | 🗄️ Guia de migração para banco de dados |- ✅ Cancelar vendas (RF15)- **RxJS** + **Angular Material**

| [docs/requisitos/](docs/requisitos/) | 📋 Requisitos funcionais detalhados |

| [docs/diagramas/](docs/diagramas/) | 📐 Diagramas UML (casos de uso, classes, sequência) |- ✅ Múltiplas formas de pagamento: Dinheiro, PIX, Débito, Crédito (RF16)- **TailwindCSS**

| [docs/casos-de-uso/](docs/casos-de-uso/) | 📖 Casos de uso detalhados |

- ✅ **Cálculo automático de troco** (RF17)

---

- ✅ **Atualização automática de estoque** (RF18)### Futuro

## 🧪 Testando a API

- **JPA/Hibernate** + **PostgreSQL**

### 1. Fazer Login no Swagger

### 📦 Gestão de Produtos- **Flyway Migrations**

1. Acesse: http://localhost:8080/api/swagger-ui.html

2. Encontre o endpoint `POST /api/auth/login`- ✅ CRUD completo (RF22-RF25)

3. Clique em **"Try it out"**

4. Use as credenciais:- ✅ **Filtro de visualização por role:**---

```json

{  - Caixa vê: nome, preço, quantidade (RF9)

  "username": "caixa",

  "password": "caixa123"  - Gerente/Estoquista veem: tudo + fornecedor + validade (RF10)## 🏗️ Arquitetura

}

```- ✅ Unidades: KG, UNIDADE, DUZIA, LITRO

5. Clique em **"Execute"**

6. Copie o `token` da resposta- ✅ Controle de validade---



### 2. Autorizar no Swagger- ✅ **Alerta de estoque baixo** (RF34)



1. Clique no botão **"Authorize"** (cadeado no topo)## 📊 Requisitos Funcionais

2. Cole o token: `Bearer <seu-token-aqui>`

3. Clique em **"Authorize"**### 📊 Gestão de Estoque

4. Agora você pode testar todos os endpoints!

- ✅ Entrada de mercadorias (RF19)O sistema implementa **35 requisitos funcionais** completos (RF1 a RF35):

### 3. Exemplo: Fluxo de Venda Completo

- ✅ Saída por perdas/vencimento (RF20)

**a) Abrir venda:**

```- ✅ Ajuste manual de inventário (RF21)### 🔐 Autenticação (RF1-RF4)

POST /api/sales

```- ✅ **Rastreabilidade completa** - quem/quando (RF6)- Login/Logout com validação



**b) Adicionar produto (Banana - 2.5 kg):**- ✅ Validação automática de quantidade (RF8)- Identificação de cargo e controle de permissões

```json

POST /api/sales/1/items- ✅ Histórico completo de movimentações

{

  "productId": 1,### ✅ Validação e Auditoria (RF5-RF8)

  "quantity": 2.5,

  "weighed": true### 🏪 Gestão de Fornecedores- Validação de campos obrigatórios

}

```- ✅ CRUD completo (RF30-RF33)- Registro de operações com timestamp e responsável



**c) Finalizar com pagamento:**- ✅ Dados: CNPJ, telefone, email, endereço- Histórico de vendas e movimentações

```json

POST /api/sales/1/finalize- ✅ Vínculo com produtos (RF35)

{

  "paymentMethod": "DINHEIRO",- ✅ Inativação lógica (mantém histórico)### 📦 Gestão de Produtos (RF9-RF10, RF22-RF25)

  "amountPaid": 20.00

}- CRUD completo de produtos

```

### 👥 Gestão de Usuários- Consulta diferenciada por cargo (Caixa vs Estoquista/Gerente)

**Resultado:** Troco calculado automaticamente e estoque atualizado! ✅

- ✅ CRUD completo - apenas GERENTE (RF26-RF29)

---

- ✅ Atribuição de roles### 🛒 Vendas / PDV (RF11-RF18)

## 🎯 Requisitos Funcionais

- ✅ Senhas hasheadas com BCrypt- Abertura de venda, adição/remoção de itens

**Implementados:** 35/35 (100%) ✅

- Suporte a pesagem de produtos

<details>

<summary><b>📋 Ver lista completa</b></summary>### 📈 Relatórios e Auditoria- Múltiplas formas de pagamento



### Autenticação e Autorização- ✅ Histórico completo de vendas (RF7)- Cálculo automático de troco

- ✅ RF1: Login de usuário

- ✅ RF2: Geração de token JWT- ✅ Movimentações de estoque- Atualização automática de estoque

- ✅ RF3: Identificação de tipo de usuário

- ✅ RF4: Controle de acesso por perfil- ✅ Auditoria por usuário

- ✅ RF5: Permissões diferenciadas

- ✅ Produtos com estoque baixo### 📊 Gestão de Estoque (RF19-RF21)

### Auditoria

- ✅ RF6: Rastreabilidade completa (quem/quando)- Entrada/Saída/Ajuste de produtos

- ✅ RF7: Histórico de vendas

---- Rastreabilidade completa

### Validações

- ✅ RF8: Validar quantidade em estoque



### Produtos## 🏗️ Arquitetura### 👥 Gestão de Usuários (RF26-RF29)

- ✅ RF9: Caixa vê dados limitados

- ✅ RF10: Gerente/Estoquista veem tudo- CRUD de funcionários (apenas Gerente)

- ✅ RF22-RF25: CRUD de produtos

- ✅ RF34: Alerta de estoque baixo### Backend ✅



### Vendas (PDV)```### 🏭 Gestão de Fornecedores (RF30-RF33)

- ✅ RF11-RF18: Fluxo completo de vendas

  - Abrir vendaSpring Boot 3.2.0- CRUD de fornecedores

  - Adicionar/remover produtos

  - Pesar produtos├── Models (6 entidades)

  - Cancelar venda

  - Registrar pagamento├── DTOs (10 classes)### 🔔 Notificações (RF34-RF35)

  - Calcular troco automaticamente

  - Atualizar estoque automaticamente├── Repositories (5 interfaces + 5 in-memory)- Alerta de estoque baixo



### Estoque├── Services (6 classes)- Contato facilitado com fornecedores

- ✅ RF19: Entrada de estoque

- ✅ RF20: Saída de estoque├── Controllers (6 classes)

- ✅ RF21: Ajuste manual

├── Security (JWT + Spring Security)**Detalhes**: [`docs/requisitos/requisitosFuncionais.md`](docs/requisitos/requisitosFuncionais.md)

### Usuários

- ✅ RF26-RF29: CRUD de usuários (apenas GERENTE)└── Swagger/OpenAPI 3



### Fornecedores```---

- ✅ RF30-RF33: CRUD de fornecedores

- ✅ RF35: Visualizar contato do fornecedor



</details>**Tecnologias:**## 🔧 Instalação e Execução



---- Java 17



## 🔌 Principais Endpoints- Spring Boot 3.2.0### Pré-requisitos



### Autenticação- Spring Security + JWT- Java 17+

```

POST   /api/auth/login      # Login (retorna JWT)- Lombok (redução de boilerplate)- Maven 3.8+

POST   /api/auth/logout     # Logout

```- Bean Validation- Node.js 18+ e npm



### Vendas (PDV)- Swagger/OpenAPI- Angular CLI

```

POST   /api/sales                     # Abrir venda- Maven

POST   /api/sales/{id}/items          # Adicionar produto

DELETE /api/sales/{id}/items/{itemId} # Remover produto### Backend

POST   /api/sales/{id}/finalize       # Finalizar com pagamento

POST   /api/sales/{id}/cancel         # Cancelar venda### Frontend ⏳

```

``````bash

### Produtos

```Angular 17 (em desenvolvimento)cd backend

GET    /api/products             # Listar (filtrado por perfil)

POST   /api/products             # Cadastrar├── Módulosmvn clean install

PUT    /api/products/{id}        # Editar

DELETE /api/products/{id}        # Excluir│   ├── Authmvn spring-boot:run

GET    /api/products/low-stock   # Produtos com estoque baixo

```│   ├── PDV```



### Estoque│   ├── Produtos

```

POST   /api/stock/entry       # Registrar entrada│   ├── Estoque**API**: http://localhost:8080/api  

POST   /api/stock/exit        # Registrar saída

POST   /api/stock/adjustment  # Ajuste manual│   ├── Fornecedores**Swagger**: http://localhost:8080/api/swagger-ui.html

GET    /api/stock/movements   # Histórico

```│   ├── Usuários



**Documentação completa com exemplos:** http://localhost:8080/api/swagger-ui.html│   └── Relatórios**Credenciais padrão:**



---└── Shared- Gerente: `gerente` / `gerente123`



## 💾 Dados Pré-cadastrados```- Estoquista: `estoquista` / `estoquista123`



### Produtos- Caixa: `caixa` / `caixa123`

- 🍌 Banana Prata - R$ 5,50/kg (100 kg em estoque)

- 🍅 Tomate - R$ 7,00/kg (50 kg em estoque)---

- 🥬 Alface Crespa - R$ 2,50/unidade (30 unidades)

- 🍊 Laranja - R$ 4,00/kg (8 kg - **ESTOQUE BAIXO** ⚠️)### Frontend

- 🥚 Ovos - R$ 12,00/dúzia (20 dúzias)

## 📂 Estrutura do Projeto

### Fornecedores

- 📞 Frutas Silva - (11) 98765-4321```bash

- 📞 Hortifruti Bom Jardim - (11) 91234-5678

- 📞 Distribuidora Verde Vale - (11) 99999-0000```cd frontend



---vemolin.ux/npm install



## 🛠️ Tecnologias Utilizadas├── backend/ng serve



| Tecnologia | Versão | Uso |│   ├── src/main/java/com/vermolinux/```

|------------|--------|-----|

| Java | 17 | Linguagem principal |│   │   ├── model/          # Entidades

| Spring Boot | 3.2.0 | Framework backend |

| Spring Security | 6.x | Autenticação e autorização |│   │   ├── dto/            # Data Transfer Objects**App**: http://localhost:4200

| JWT | 0.12.3 | Tokens de autenticação |

| Lombok | - | Redução de boilerplate |│   │   ├── repository/     # Camada de dados

| Swagger/OpenAPI | 3.0 | Documentação da API |

| Maven | - | Gerenciamento de dependências |│   │   ├── service/        # Lógica de negócio---



---│   │   ├── controller/     # Endpoints REST



## 📈 Próximos Passos│   │   ├── security/       # JWT + Spring Security## 📡 Endpoints da API



### Backend│   │   ├── exception/      # Tratamento de exceções

- [ ] Migrar para PostgreSQL (guia em `docs/DB_INTEGRATION.md`)

- [ ] Adicionar paginação nas listagens│   │   └── VermolinUxApplication.java### Autenticação

- [ ] Implementar testes automatizados

- [ ] Gerar relatórios em PDF│   ├── src/main/resources/- `POST /auth/login` - Login



### Frontend│   │   └── application.properties- `POST /auth/logout` - Logout

- [ ] Desenvolver interface Angular

- [ ] Dashboard gerencial│   └── pom.xml- `GET /auth/me` - Usuário atual

- [ ] PDV otimizado para touch screen

├── frontend/               # Angular (em desenvolvimento)

---

├── docs/### Produtos (todos os cargos)

## 🎓 Sobre o Projeto

│   ├── requisitos/- `GET /products` - Listar

Este sistema foi desenvolvido como **Projeto Integrado Multidisciplinar III (PIM III)** do curso de **Análise e Desenvolvimento de Sistemas** da **UNIP**.

│   │   └── requisitosFuncionais.md- `GET /products/{id}` - Buscar

**Objetivos de Aprendizado:**

- ✅ Arquitetura MVC│   ├── diagramas/- `POST /products` - Criar (Gerente)

- ✅ API REST

- ✅ Autenticação JWT│   ├── casos-de-uso/- `PUT /products/{id}` - Atualizar (Gerente)

- ✅ Padrões de Projeto

- ✅ Boas Práticas de Desenvolvimento│   ├── DB_INTEGRATION.md- `DELETE /products/{id}` - Deletar (Gerente)



---│   ├── design.md- `GET /products/low-stock` - Estoque baixo (Gerente)



## 📞 Suporte│   └── STATUS.md



- **Documentação Interativa:** http://localhost:8080/api/swagger-ui.html├── BACKEND_COMPLETO.md     # 📄 Documentação do backend### Vendas (Caixa)

- **Guia de Apresentação:** [APRESENTACAO.md](APRESENTACAO.md)

- **Issues:** GitHub Issues├── COMO_CONTINUAR.md       # 📄 Guia de próximos passos- `POST /sales` - Abrir venda



---├── start-backend.ps1       # 🚀 Script de inicialização- `POST /sales/{id}/items` - Adicionar item



## 📄 Licença└── README.md               # Este arquivo- `DELETE /sales/{id}/items/{itemId}` - Remover item



Este projeto está sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.```- `POST /sales/{id}/payment` - Finalizar pagamento



---- `DELETE /sales/{id}` - Cancelar venda



<div align="center">---



**Desenvolvido com ❤️ para o PIM III - UNIP**### Estoque (Estoquista)



[![GitHub](https://img.shields.io/badge/GitHub-makalyster-blue?logo=github)](https://github.com/makalyster)## 📋 Requisitos Funcionais- `POST /stock/entry` - Entrada



</div>- `POST /stock/exit` - Saída


**Implementados:** 35/35 (100%) ✅- `POST /stock/adjust` - Ajuste

- `GET /stock/movements` - Histórico

<details>

<summary><b>Ver lista completa de RFs</b></summary>### Usuários (Gerente)

- `GET /users` - Listar

### Autenticação (RF1-RF3)- `POST /users` - Criar

- ✅ RF1: Login de usuário- `PUT /users/{id}` - Atualizar

- ✅ RF2: Token JWT- `DELETE /users/{id}` - Deletar

- ✅ RF3: Identificação de tipo de usuário

### Fornecedores (Gerente)

### Autorização (RF4-RF5)- `GET /suppliers` - Listar

- ✅ RF4: Controle de acesso por role- `POST /suppliers` - Criar

- ✅ RF5: Permissões diferenciadas- `PUT /suppliers/{id}` - Atualizar

- `DELETE /suppliers/{id}` - Deletar

### Auditoria (RF6-RF7)

- ✅ RF6: Rastreabilidade completa**Exemplos detalhados**: [`README-backend.md`](README-backend.md)

- ✅ RF7: Histórico de vendas

---

### Validações (RF8)

- ✅ RF8: Validar estoque## 🗄️ Integração com Banco de Dados



### Produtos (RF9-RF10, RF22-RF25, RF34)**Status**: Atualmente usando **armazenamento in-memory** (sem persistência real).

- ✅ RF9: Caixa vê dados limitados

- ✅ RF10: Gerente/Estoquista veem tudo**Próximos passos**: Consulte [`DB_INTEGRATION.md`](DB_INTEGRATION.md) para:

- ✅ RF22-RF25: CRUD de produtos- Estrutura de tabelas SQL

- ✅ RF34: Notificar estoque baixo- Configuração JPA

- Migrations Flyway

### Vendas (RF11-RF18)- Passo a passo completo

- ✅ RF11: Abrir venda

- ✅ RF12: Adicionar produto---

- ✅ RF13: Remover produto

- ✅ RF14: Pesar produto## 🗺️ Roadmap

- ✅ RF15: Cancelar venda

- ✅ RF16: Forma de pagamento### ✅ Fase 1: Fundação (Atual)

- ✅ RF17: Calcular troco- [x] Backend Spring Boot completo

- ✅ RF18: Finalizar e atualizar estoque- [x] Frontend Angular estruturado

- [x] Autenticação JWT

### Estoque (RF19-RF21)- [x] Todos os RF1-RF35 implementados

- ✅ RF19: Entrada- [x] Documentação Swagger

- ✅ RF20: Saída

- ✅ RF21: Ajuste manual### 🔄 Fase 2: Persistência (Próxima)

- [ ] Integração PostgreSQL

### Usuários (RF26-RF29)- [ ] Migrations Flyway

- ✅ RF26-RF29: CRUD de usuários- [ ] Testes de integração



### Fornecedores (RF30-RF33, RF35)### 📈 Fase 3: Evolução

- ✅ RF30-RF33: CRUD de fornecedores- [ ] Relatórios e dashboards

- ✅ RF35: Visualizar contato- [ ] Exportação PDF/Excel

- [ ] Notificações push

Veja detalhes em: [`docs/requisitos/requisitosFuncionais.md`](docs/requisitos/requisitosFuncionais.md)- [ ] App mobile



</details>---



---## 📄 Licença



## 🔌 Endpoints da APIMIT License - consulte `LICENSE`



### Autenticação---

```

POST   /api/auth/login      # Login (público)**Desenvolvido com ❤️ pela equipe Vermolin.UX**

POST   /api/auth/logout     # Logout

``````

vermolin-ux/

### Usuários (GERENTE)├── backend/                    # Spring Boot API

```│   ├── src/main/java/com/vermolinux/

GET    /api/users           # Listar│   │   ├── controller/        # REST Controllers

POST   /api/users           # Cadastrar│   │   ├── service/           # Business Logic

GET    /api/users/{id}      # Buscar│   │   ├── repository/        # Data Access Layer

PUT    /api/users/{id}      # Atualizar│   │   │   └── inmemory/      # In-memory implementations

DELETE /api/users/{id}      # Excluir│   │   ├── model/             # Domain Entities

```│   │   ├── dto/               # Data Transfer Objects

│   │   ├── security/          # Security & JWT

### Produtos (GERENTE, ESTOQUISTA, CAIXA)│   │   ├── config/            # Configurations

```│   │   └── exception/         # Exception Handling

GET    /api/products             # Listar (filtrado por role)│   ├── src/main/resources/

POST   /api/products             # Cadastrar (GERENTE, ESTOQUISTA)│   └── pom.xml

GET    /api/products/{id}        # Buscar (filtrado por role)│

PUT    /api/products/{id}        # Atualizar (GERENTE, ESTOQUISTA)├── frontend/                   # Angular Application

DELETE /api/products/{id}        # Excluir (GERENTE, ESTOQUISTA)│   ├── src/app/

GET    /api/products/low-stock   # Estoque baixo (GERENTE, ESTOQUISTA)│   │   ├── core/              # Singletons, guards

```│   │   ├── shared/            # Reusable components

│   │   ├── auth/              # Authentication

### Vendas (CAIXA, GERENTE)│   │   ├── products/          # Product management

```│   │   ├── stock/             # Stock management

POST   /api/sales                    # Abrir venda (CAIXA)│   │   ├── sales/             # POS/Sales

POST   /api/sales/{id}/items         # Adicionar item (CAIXA)│   │   ├── users/             # User management

DELETE /api/sales/{id}/items/{itemId} # Remover item (CAIXA)│   │   └── suppliers/         # Supplier management

POST   /api/sales/{id}/cancel        # Cancelar (CAIXA, GERENTE)│   └── package.json

POST   /api/sales/{id}/finalize      # Finalizar (CAIXA)│

GET    /api/sales                    # Histórico (GERENTE)├── docs/                       # Documentation

GET    /api/sales/{id}               # Buscar venda│   ├── requisitos/

GET    /api/sales/cashier/{id}       # Vendas do caixa│   ├── casos-de-uso/

```│   └── diagramas/

│

### Estoque (GERENTE, ESTOQUISTA)├── README.md

```├── README-backend.md

POST   /api/stock/entry              # Entrada├── README-frontend.md

POST   /api/stock/exit               # Saída└── DB_INTEGRATION.md

POST   /api/stock/adjustment         # Ajuste
GET    /api/stock/movements          # Histórico completo
GET    /api/stock/movements/product/{id}  # Por produto
GET    /api/stock/movements/user/{id}     # Por usuário (GERENTE)
```

### Fornecedores (GERENTE, ESTOQUISTA)
```
GET    /api/suppliers           # Listar
POST   /api/suppliers           # Cadastrar
GET    /api/suppliers/{id}      # Buscar
PUT    /api/suppliers/{id}      # Atualizar
DELETE /api/suppliers/{id}      # Inativar
PATCH  /api/suppliers/{id}/reactivate  # Reativar (GERENTE)
```

**Documentação completa:** http://localhost:8080/api/swagger-ui.html

---

## 🧪 Testando a API

### 1. Login
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "caixa",
  "password": "caixa123"
}
```

**Resposta:**
```json
{
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userId": 3,
    "username": "caixa",
    "fullName": "Maria Caixa",
    "role": "CAIXA"
  }
}
```

### 2. Usar Token
Adicione em todas as requisições:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 3. Fluxo Completo de Venda

```bash
# 1. Abrir venda
POST /api/sales
Authorization: Bearer <token-caixa>

# 2. Adicionar produto
POST /api/sales/1/items
{
  "productId": 1,
  "quantity": 2.5,
  "weighed": true
}

# 3. Finalizar com pagamento
POST /api/sales/1/finalize
{
  "paymentMethod": "DINHEIRO",
  "amountPaid": 20.00
}
# Troco calculado automaticamente!
```

---

## 💾 Dados Pré-cadastrados

### Produtos
- 🍌 Banana Prata (KG) - R$ 5,50
- 🍅 Tomate (KG) - R$ 7,00
- 🥬 Alface Crespa (UNIDADE) - R$ 2,50
- 🍊 Laranja (KG) - R$ 4,00 ⚠️ **ESTOQUE BAIXO**
- 🥚 Ovos (DUZIA) - R$ 12,00

### Fornecedores
- Frutas Silva - (11) 98765-4321
- Hortifruti Bom Jardim - (11) 91234-5678
- Distribuidora Verde Vale - (11) 99999-0000

---

## 🗄️ Migração para Banco de Dados

O sistema atualmente usa **in-memory storage** para facilitar desenvolvimento. Para migrar para banco de dados real:

**Siga o guia:** [`docs/DB_INTEGRATION.md`](docs/DB_INTEGRATION.md)

**Bancos suportados:**
- PostgreSQL (recomendado)
- MySQL/MariaDB
- Oracle
- SQL Server

---

## 📚 Documentação

| Documento | Descrição |
|-----------|-----------|
| [`BACKEND_COMPLETO.md`](BACKEND_COMPLETO.md) | Documentação completa do backend |
| [`COMO_CONTINUAR.md`](COMO_CONTINUAR.md) | Próximos passos de desenvolvimento |
| [`docs/STATUS.md`](docs/STATUS.md) | Status de implementação |
| [`docs/DB_INTEGRATION.md`](docs/DB_INTEGRATION.md) | Guia de migração para DB |
| [`docs/design.md`](docs/design.md) | Decisões de arquitetura |
| [`docs/requisitos/`](docs/requisitos/) | Requisitos funcionais |
| [`docs/diagramas/`](docs/diagramas/) | Diagramas UML |
| [`docs/casos-de-uso/`](docs/casos-de-uso/) | Casos de uso detalhados |

---

## 🛠️ Tecnologias

### Backend
- **Java:** 17
- **Spring Boot:** 3.2.0
- **Spring Security:** JWT Authentication
- **Spring Data:** Repositories
- **Spring Web:** REST Controllers
- **Lombok:** Redução de boilerplate
- **Jakarta Validation:** Bean Validation
- **Swagger/OpenAPI:** Documentação automática
- **SLF4J:** Logging
- **BCrypt:** Password hashing
- **Maven:** Build tool

### Frontend (planejado)
- **Angular:** 17
- **Angular Material:** Componentes UI
- **TailwindCSS:** Utility-first CSS
- **RxJS:** Reactive programming
- **TypeScript:** Type-safe JavaScript

---

## 🎯 Próximos Passos

### Backend
- [ ] Migrar para banco de dados PostgreSQL
- [ ] Adicionar paginação em listagens
- [ ] Implementar filtros avançados
- [ ] Adicionar relatórios em PDF
- [ ] Implementar testes unitários
- [ ] Adicionar testes de integração

### Frontend
- [ ] Criar módulo de autenticação
- [ ] Implementar PDV
- [ ] Criar gestão de produtos
- [ ] Criar gestão de estoque
- [ ] Criar gestão de fornecedores
- [ ] Criar gestão de usuários
- [ ] Criar dashboard gerencial
- [ ] Implementar relatórios

---

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 📞 Suporte

- **Documentação:** Consulte os arquivos na pasta `docs/`
- **Swagger UI:** http://localhost:8080/api/swagger-ui.html
- **Issues:** Reporte bugs ou solicite features via GitHub Issues

---

## ✨ Destaques Técnicos

### 1. Filtro de Dados por Role
```java
// ProductService.java
if (isCashier) {
    // Caixa vê apenas: nome, preço, quantidade
    return ProductCashierResponse.builder()
        .name(product.getName())
        .price(product.getPrice())
        .stockQuantity(product.getStockQuantity())
        .build();
}
// Gerente/Estoquista veem tudo
```

### 2. Cálculo Automático de Troco
```java
// SaleService.java
if (paymentMethod == DINHEIRO) {
    change = amountPaid - totalAmount;
}
```

### 3. Rastreabilidade Completa
```java
// StockMovement.java
movement.setCreatedBy(userId);      // Quem
movement.setCreatedAt(LocalDateTime.now());  // Quando
movement.setPreviousQuantity(old);  // De
movement.setNewQuantity(updated);   // Para
```

### 4. Validação de Estoque
```java
// StockService.java
if (product.getStockQuantity() < requestedQuantity) {
    throw new BusinessException("Estoque insuficiente");
}
```

---

<div align="center">

**Desenvolvido com ❤️ para facilitar a gestão de hortifrútis**

[⬆ Voltar ao topo](#-vermolinux---sistema-de-gestão-para-hortifruti)

</div>
