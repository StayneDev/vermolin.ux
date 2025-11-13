# 🎨 Design System - Vermolin.UX

Guia visual e de componentes para o sistema Vermolin.UX.

---

## 🎯 Princípios de Design

### Minimalista
- Interface limpa e focada
- Apenas elementos essenciais
- Hierarquia visual clara

### Profissional
- Tipografia legível
- Cores sóbrias e neutras
- Espaçamento consistente

### Responsivo
- Mobile-first approach
- Breakpoints bem definidos
- Touch-friendly (mínimo 44px para botões)

### Acessível
- Contraste mínimo WCAG AA
- Labels descritivos
- Navegação por teclado

---

## 🎨 Paleta de Cores

### Cores Principais

```css
/* Verde primário (hortifruti) */
--primary: #4CAF50;
--primary-dark: #388E3C;
--primary-light: #81C784;

/* Cinza neutro */
--neutral-900: #212121;
--neutral-700: #616161;
--neutral-500: #9E9E9E;
--neutral-300: #E0E0E0;
--neutral-100: #F5F5F5;
--neutral-50: #FAFAFA;

/* Background */
--bg-primary: #FFFFFF;
--bg-secondary: #F5F5F5;

/* Texto */
--text-primary: #212121;
--text-secondary: #616161;
--text-disabled: #9E9E9E;
```

### Cores Funcionais

```css
/* Sucesso */
--success: #4CAF50;
--success-light: #C8E6C9;

/* Erro */
--error: #F44336;
--error-light: #FFCDD2;

/* Aviso */
--warning: #FF9800;
--warning-light: #FFE0B2;

/* Info */
--info: #2196F3;
--info-light: #BBDEFB;
```

### Cores por Cargo

```css
/* Gerente */
--role-gerente: #1976D2;

/* Estoquista */
--role-estoquista: #FF9800;

/* Caixa */
--role-caixa: #4CAF50;
```

---

## 📝 Tipografia

### Fonte Principal

**Família:** Inter, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif

```css
/* Headings */
--font-h1: 700 32px/40px 'Inter';
--font-h2: 600 24px/32px 'Inter';
--font-h3: 600 20px/28px 'Inter';
--font-h4: 600 18px/24px 'Inter';

/* Body */
--font-body: 400 16px/24px 'Inter';
--font-body-sm: 400 14px/20px 'Inter';
--font-body-xs: 400 12px/16px 'Inter';

/* Labels */
--font-label: 500 14px/20px 'Inter';
--font-label-sm: 500 12px/16px 'Inter';

/* Buttons */
--font-button: 500 16px/24px 'Inter';
```

---

## 📐 Espaçamento

Sistema baseado em 8px (múltiplos de 8).

```css
--space-xs: 4px;   /* 0.5x */
--space-sm: 8px;   /* 1x */
--space-md: 16px;  /* 2x */
--space-lg: 24px;  /* 3x */
--space-xl: 32px;  /* 4x */
--space-2xl: 48px; /* 6x */
--space-3xl: 64px; /* 8x */
```

---

## 🧩 Componentes

### Botões

```css
/* Primário */
.btn-primary {
  background: var(--primary);
  color: white;
  padding: 12px 24px;
  border-radius: 8px;
  font: var(--font-button);
  border: none;
  cursor: pointer;
  transition: all 0.2s ease;
}

.btn-primary:hover {
  background: var(--primary-dark);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
}

/* Secundário */
.btn-secondary {
  background: transparent;
  color: var(--primary);
  border: 2px solid var(--primary);
}

/* Perigo */
.btn-danger {
  background: var(--error);
  color: white;
}
```

### Inputs

```css
.input {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid var(--neutral-300);
  border-radius: 8px;
  font: var(--font-body);
  transition: border-color 0.2s;
}

.input:focus {
  outline: none;
  border-color: var(--primary);
  box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.1);
}

.input:error {
  border-color: var(--error);
}
```

### Cards

```css
.card {
  background: var(--bg-primary);
  border-radius: 12px;
  padding: var(--space-lg);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  transition: box-shadow 0.2s;
}

.card:hover {
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
}
```

### Tabelas

```css
.table {
  width: 100%;
  border-collapse: collapse;
}

.table th {
  background: var(--neutral-100);
  padding: 16px;
  text-align: left;
  font: var(--font-label);
  color: var(--text-secondary);
  border-bottom: 2px solid var(--neutral-300);
}

.table td {
  padding: 16px;
  border-bottom: 1px solid var(--neutral-300);
}

.table tr:hover {
  background: var(--neutral-50);
}
```

### Badges

```css
.badge {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 16px;
  font: var(--font-label-sm);
  font-weight: 600;
}

.badge-success { background: var(--success-light); color: var(--success); }
.badge-error { background: var(--error-light); color: var(--error); }
.badge-warning { background: var(--warning-light); color: var(--warning); }
.badge-info { background: var(--info-light); color: var(--info); }
```

---

## 📱 Breakpoints

```css
/* Mobile */
@media (max-width: 640px) { ... }

/* Tablet */
@media (min-width: 641px) and (max-width: 1024px) { ... }

/* Desktop */
@media (min-width: 1025px) { ... }
```

---

## 🎭 Ícones

**Biblioteca:** Material Icons ou Heroicons

```html
<!-- Produto -->
<mat-icon>inventory_2</mat-icon>

<!-- Venda -->
<mat-icon>shopping_cart</mat-icon>

<!-- Estoque -->
<mat-icon>warehouse</mat-icon>

<!-- Usuário -->
<mat-icon>person</mat-icon>

<!-- Fornecedor -->
<mat-icon>business</mat-icon>
```

---

## 🖼️ Layouts

### Dashboard

```
┌─────────────────────────────────────────┐
│ Header (Logo + User Menu)              │
├──────┬──────────────────────────────────┤
│      │                                  │
│ Side │  Main Content Area               │
│ Nav  │  (Cards, Tables, Forms)          │
│      │                                  │
│      │                                  │
└──────┴──────────────────────────────────┘
```

### PDV (Caixa)

```
┌─────────────────────────────────────────┐
│ Header (Venda #123 - Total: R$ 45,90) │
├──────────────────┬──────────────────────┤
│                  │                      │
│ Lista de Itens   │  Teclado Numérico   │
│                  │  + Pagamento         │
│                  │                      │
└──────────────────┴──────────────────────┘
```

---

## ✅ Checklist de Implementação

### Angular Material
- [ ] Instalar: `ng add @angular/material`
- [ ] Escolher tema personalizado
- [ ] Importar módulos necessários

### TailwindCSS (Alternativa)
- [ ] Instalar: `npm install -D tailwindcss`
- [ ] Configurar `tailwind.config.js`
- [ ] Adicionar diretivas no `styles.css`

### Componentes Reutilizáveis
- [ ] Button component
- [ ] Input/Form controls
- [ ] Card component
- [ ] Table component
- [ ] Modal/Dialog
- [ ] Notification/Toast
- [ ] Loading spinner
- [ ] Badge/Chip

---

## 🌟 Referências de Inspiração

- **Dashboards:** [Vercel Dashboard](https://vercel.com)
- **Tabelas:** [Linear App](https://linear.app)
- **Forms:** [Stripe Dashboard](https://dashboard.stripe.com)
- **PDV:** [Square POS](https://squareup.com)

---
