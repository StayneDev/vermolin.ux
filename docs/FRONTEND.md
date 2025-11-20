# Frontend - Documentação

## Stack
- **Angular 17**
- **TypeScript 5.2**
- **RxJS** (Reactive Programming)
- **Build:** Angular CLI

## Estrutura

```
frontend/src/app/
├── components/
│   ├── auth/              - Autenticação
│   ├── dashboard/         - Página inicial
│   ├── products/          - Gestão de produtos
│   ├── sales/             - PDV (vendas)
│   ├── stock/             - Gestão de estoque
│   └── users/             - Gestão de usuários
├── services/
│   ├── auth.service.ts    - Login, tokens, roles
│   ├── api.service.ts     - Chamadas HTTP
│   ├── product.service.ts
│   ├── sale.service.ts
│   ├── stock.service.ts
│   ├── user.service.ts
│   └── supplier.service.ts
├── guards/
│   ├── auth.guard.ts      - Verifica login
│   └── role.guard.ts      - Verifica permissão
├── interceptors/
│   └── jwt.interceptor.ts - Injeta token JWT
├── models/
│   ├── user.model.ts
│   ├── product.model.ts
│   ├── sale.model.ts
│   └── ... (outros)
└── app.module.ts
```

## Execução

```bash
cd frontend
npm install          # Primeira vez
npm start           # Desenvolver
npm run build       # Produção
```

> 💡 No Windows você pode usar `install-frontend.bat` (setup inicial) e `start-frontend.bat` (servidor dev) para evitar digitar comandos.

## Portas
- **Desenvolvimento:** http://localhost:4200
- **Produção:** Servirá via nginx/apache

## Autenticação
- Token JWT armazenado em `localStorage`
- Injetado automaticamente via `JwtInterceptor`
- Expira em 24 horas

## Componentes Principais

### AuthComponent
- Login
- Logout
- Exibição de usuário

### DashboardComponent
- Menu principal
- Acesso por role (GERENTE, CAIXA, ESTOQUISTA)

### PdvComponent (Vendas)
- Abrir venda
- Adicionar itens
- Calcular total
- Registrar pagamento
- Calcular troco

### ProductComponent
- Listar produtos
- Criar/editar produto
- Deletar produto

### StockComponent
- Entrada de produtos
- Ajuste manual
- Histórico de movimentações

### UserComponent (Admin)
- CRUD de usuários
- Associar roles

## Services

### AuthService
```typescript
login(username, password): Observable<LoginResponse>
logout(): void
getToken(): string
isAuthenticated(): boolean
getUserRole(): string
```

### ApiService
```typescript
get(url): Observable<any>
post(url, data): Observable<any>
put(url, data): Observable<any>
delete(url): Observable<any>
```

### SaleService
```typescript
createSale(): Observable<SaleResponse>
addItem(saleId, item): Observable<SaleResponse>
removeItem(saleId, itemId): Observable<any>
finalizeSale(saleId, payment): Observable<SaleResponse>
```

## Roteamento

```typescript
const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'products', component: ProductComponent, canActivate: [AuthGuard] },
  { path: 'sales', component: SalesComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'CAIXA' } },
  { path: 'stock', component: StockComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ESTOQUISTA' } },
  { path: 'users', component: UserComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'GERENTE' } }
];
```

## Tratamento de Erros

```typescript
// Global error handler
- 401: Logout e redireciona para login
- 403: Exibe "sem permissão"
- 404: Exibe "não encontrado"
- 500: Exibe erro do servidor
```

## Build para Produção

```bash
npm run build
# Gera dist/vemolin/ pronto para servir
```

## Testes

```bash
npm test           # Unit tests
npm run e2e        # End-to-end tests
```
