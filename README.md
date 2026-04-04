# vermolin.ux

> Sistema de gestão para hortifruti com PDV, controle de estoque, fornecedores e autenticação multi-nível.

## Status

`Produção`

## Stack

| Componente | Papel |
|---|---|
| Java 17 + Spring Boot 3.2 | Backend REST + JWT |
| Angular 17 | Frontend |
| PostgreSQL 14+ | Banco de dados (Flyway migrations) |
| Swagger / OpenAPI 3.0 | Documentação da API |

## Pré-requisitos

- Java JDK 17+
- Maven 3.8+
- PostgreSQL 14+ com database `vermolinux`, user `postgres`
- Node.js 18+ (frontend)

## Setup

```bash
# Backend
cd backend
mvn clean package -DskipTests
mvn spring-boot:run
# Disponível em: http://localhost:8080/api
# Swagger: http://localhost:8080/api/swagger-ui.html

# Frontend
cd frontend
npm install
npm start
# Disponível em: http://localhost:4200
```

> Guia completo: [SETUP.md](SETUP.md)

### Usuários de teste

| Perfil | Username | Senha |
|--------|----------|-------|
| Gerente | `gerente` | `gerente123` |
| Estoquista | `estoquista` | `estoque123` |
| Caixa | `caixa` | `caixa123` |

## Estrutura

```
backend/            # Spring Boot — API REST
frontend/           # Angular — interface web
docs/
  requisitos/       # Requisitos funcionais (RF1-RF35)
  diagramas/        # UML (casos de uso, classes, sequência)
SETUP.md            # Guia completo de instalação e troubleshooting
ARCHITECTURE.md     # Arquitetura MVC e decisões técnicas
```

## Links

- [Issues](../../issues)
- [Setup detalhado](SETUP.md)
- [Roadmap](/opt/infra-backup/docs/roadmap.md)
