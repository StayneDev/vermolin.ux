import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ProductService } from '../../../core/services/product.service';
import { AuthService } from '../../../core/services/auth.service';
import { Product } from '../../../core/models/api.models';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="page">
      <header class="header">
        <div class="container">
          <h1>📦 Produtos</h1>
          <div class="header-actions">
            <button class="btn btn-secondary" (click)="goBack()">← Voltar</button>
            <button class="btn btn-primary" (click)="logout()">Sair</button>
          </div>
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

          <table *ngIf="!loading && products.length > 0">
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
      </div>
    </div>
  `,
  styles: [`
    .header {
      background: #4CAF50;
      color: white;
      padding: 20px 0;
      margin-bottom: 30px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .header .container {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .header h1 {
      margin: 0;
      font-size: 1.8rem;
    }

    .header-actions {
      display: flex;
      gap: 10px;
    }

    .card-header {
      padding-bottom: 15px;
      border-bottom: 2px solid #eee;
      margin-bottom: 20px;
    }

    .card-header h2 {
      margin: 0;
      color: #333;
    }

    .empty-state {
      text-align: center;
      padding: 40px;
      color: #999;
    }

    .badge {
      display: inline-block;
      padding: 4px 8px;
      border-radius: 12px;
      font-size: 0.75rem;
      font-weight: 600;
    }

    .badge-success {
      background-color: #d4edda;
      color: #155724;
    }

    .badge-inactive {
      background-color: #f8d7da;
      color: #721c24;
    }

    .badge-warning {
      background-color: #fff3cd;
      color: #856404;
      margin-left: 5px;
    }

    .low-stock {
      background-color: #fff8e1;
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
