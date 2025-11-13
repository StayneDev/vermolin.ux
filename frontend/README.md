# 🥬 Vermolin.UX Frontend

Frontend Angular para o sistema de gestão de hortifruti Vermolin.UX.

## 📋 Pré-requisitos

- Node.js 18+ 
- npm 9+
- Backend rodando em `http://localhost:8080`

## 🚀 Instalação

```bash
# Instalar dependências
npm install
```

## ▶️ Executar Aplicação

```bash
# Iniciar servidor de desenvolvimento
npm start
```

A aplicação estará disponível em `http://localhost:4200`

## 🔐 Credenciais de Teste

- **Gerente:** gerente / gerente123
- **Estoquista:** estoquista / estoquista123
- **Caixa:** caixa / caixa123

## 📁 Estrutura do Projeto

```
src/
├── app/
│   ├── core/              # Serviços, guards, interceptors
│   │   ├── guards/        # Auth guard
│   │   ├── interceptors/  # HTTP interceptor para JWT
│   │   ├── models/        # Interfaces TypeScript
│   │   └── services/      # Serviços (auth, products, sales)
│   ├── features/          # Componentes de features
│   │   ├── auth/          # Login
│   │   ├── dashboard/     # Dashboard principal
│   │   ├── products/      # Listagem de produtos
│   │   └── sales/         # Listagem de vendas
│   ├── app.component.ts   # Componente raiz
│   └── app.routes.ts      # Configuração de rotas
├── index.html             # HTML principal
├── main.ts                # Bootstrap da aplicação
└── styles.css             # Estilos globais
```

## 🔧 Configuração

### Proxy para API

O arquivo `proxy.conf.json` redireciona `/api` para `http://localhost:8080`:

```json
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true
  }
}
```

## 📦 Funcionalidades Implementadas

- ✅ Login com autenticação JWT
- ✅ Dashboard com menu baseado em permissões
- ✅ Listagem de produtos
- ✅ Listagem de vendas (apenas CAIXA e GERENTE)
- ✅ Guard de autenticação para rotas protegidas
- ✅ Interceptor HTTP para adicionar token JWT
- ✅ Logout

## 🎨 Tecnologias

- **Angular 17** - Framework standalone components
- **TypeScript** - Linguagem
- **RxJS** - Programação reativa
- **Angular Router** - Roteamento
- **HttpClient** - Comunicação com API

## 📝 Comandos Úteis

```bash
# Instalar dependências
npm install

# Iniciar desenvolvimento
npm start

# Build para produção
npm run build

# Executar testes
npm test
```

## 🔗 Integração com Backend

O frontend consome a API REST do backend Spring Boot:

- **Login:** `POST /api/auth/login`
- **Produtos:** `GET /api/products`
- **Vendas:** `GET /api/sales`

Todas as requisições autenticadas incluem o header:
```
Authorization: Bearer <token-jwt>
```

## 👨‍💻 Desenvolvimento

O projeto usa **Angular Standalone Components** (sem módulos NgModule).

### Adicionar nova feature:

1. Criar componente em `src/app/features/`
2. Adicionar rota em `app.routes.ts`
3. Criar service em `core/services/` se necessário
4. Atualizar modelos em `core/models/` se necessário
