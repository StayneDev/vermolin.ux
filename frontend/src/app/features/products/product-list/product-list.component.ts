import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ProductService } from '../../../core/services/product.service';
import { AuthService } from '../../../core/services/auth.service';
import { Product } from '../../../core/models/api.models';
import { ThemeToggleComponent } from '../../../shared/components/theme-toggle/theme-toggle.component';
import { HeaderMenuComponent } from '../../../shared/components/header-menu/header-menu.component';
import { RoleNavComponent } from '../../../shared/components/role-nav/role-nav.component';
import { ProfileAvatarComponent } from '../../../shared/components/profile-avatar/profile-avatar.component';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, ThemeToggleComponent, HeaderMenuComponent, RoleNavComponent, ProfileAvatarComponent],
  template: `
    <div class="page">
      <header class="header">
        <div class="container header-content">
          <div class="header-copy">
            <h1>📦 Produtos</h1>
            <p class="header-subtitle">Acompanhe o catálogo em tempo real</p>
          </div>
          <app-header-menu>
            <app-role-nav></app-role-nav>
            <app-theme-toggle></app-theme-toggle>
            <app-profile-avatar></app-profile-avatar>
            <button class="btn btn-secondary" (click)="goBack()">← Voltar</button>
            <button class="btn btn-primary" (click)="logout()">Sair</button>
          </app-header-menu>
        </div>
      </header>

      <div class="container">
        <div class="card">
          <div class="card-header">
            <h2>Lista de Produtos</h2>
          </div>

          <div class="alert alert-error" *ngIf="errorMessage">
            {{ errorMessage }}
          </div>

          <div class="loading" *ngIf="loading">
            Carregando produtos...
          </div>

          <div *ngIf="!loading && products.length === 0" class="empty-state">
            <p>Nenhum produto encontrado.</p>
          </div>

          <div class="table-responsive" *ngIf="!loading && products.length > 0">
            <table>
              <thead>
                <tr>
                  <th>Código</th>
                  <th>Nome</th>
                  <th>Preço</th>
                  <th>Quantidade</th>
                  <th>Unidade</th>
                  <th>Peso</th>
                  <th>Fornecedor</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let product of products" [class.low-stock]="isLowStock(product)">
                  <td>{{ product.code }}</td>
                  <td>{{ product.name }}</td>
                  <td>{{ product.price | currency:'BRL' }}</td>
                  <td>
                    {{ product.stockQuantity }}
                    <span *ngIf="isLowStock(product)" class="badge badge-warning">
                      ⚠️ Baixo
                    </span>
                  </td>
                  <td>{{ product.unit }}</td>
                  <td>{{ product.requiresWeighing ? 'Sim' : 'Não' }}</td>
                  <td>{{ product.supplierName || '-' }}</td>
                  <td>
                    <span [class]="'badge ' + (product.active ? 'badge-success' : 'badge-inactive')">
                      {{ product.active ? 'Ativo' : 'Inativo' }}
                    </span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <p class="table-scroll-hint" *ngIf="!loading && products.length > 0">
            Arraste lateralmente para visualizar todas as colunas
          </p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .header {
      background: var(--header-gradient);
      color: var(--header-text);
      padding: 20px 0;
      margin-bottom: 30px;
      box-shadow: var(--shadow-md);
    }

    .header-content {
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: 1rem;
      flex-wrap: wrap;
    }

    .header-copy h1 {
      margin: 0;
      font-size: 1.8rem;
    }

    .header-subtitle {
      margin: 0;
      color: rgba(255, 255, 255, 0.8);
      font-size: 0.95rem;
    }

    .card-header {
      padding-bottom: 15px;
      border-bottom: 2px solid var(--border-color);
      margin-bottom: 20px;
    }

    .card-header h2 {
      margin: 0;
      color: var(--text-color);
    }

    .empty-state {
      text-align: center;
      padding: 40px;
      color: var(--muted-text);
    }

    .badge {
      display: inline-block;
      padding: 4px 8px;
      border-radius: 12px;
      font-size: 0.75rem;
      font-weight: 600;
    }

    .badge-success {
      background-color: rgba(76, 175, 80, 0.15);
      color: var(--primary);
    }

    .badge-inactive {
      background-color: rgba(244, 67, 54, 0.15);
      color: #f44336;
    }

    .badge-warning {
      background-color: rgba(255, 193, 7, 0.2);
      color: #b37700;
      margin-left: 5px;
    }

    .low-stock {
      background-color: var(--table-hover-bg);
    }

    @media (max-width: 768px) {
      .header-content {
        flex-wrap: nowrap;
        align-items: center;
        justify-content: space-between;
        gap: 0.75rem;
      }

      .header-copy {
        flex: 1 1 auto;
        min-width: 0;
      }

      .header-subtitle {
        display: none;
      }

      app-header-menu {
        width: auto;
        flex: 0 0 auto;
      }
    }
  `]
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  loading = false;
  errorMessage = '';

  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.loading = true;
    this.errorMessage = '';

    this.productService.getAll().subscribe({
      next: (response) => {
        if (response.success) {
          this.products = response.data;
        } else {
          this.errorMessage = response.message || 'Erro ao carregar produtos';
        }
        this.loading = false;
      },
      error: (error: any) => {
        this.errorMessage = error.error?.message || 'Erro ao conectar ao servidor';
        this.loading = false;
      }
    });
  }

  isLowStock(product: Product): boolean {
    if (typeof product.lowStock === 'boolean') {
      return product.lowStock;
    }

    if (product.minStock !== null && product.minStock !== undefined) {
      return product.stockQuantity < product.minStock;
    }

    return false;
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }

  logout(): void {
    this.authService.logout();
  }
}
