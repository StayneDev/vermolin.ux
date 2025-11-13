import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { SaleService } from '../../../core/services/sale.service';
import { AuthService } from '../../../core/services/auth.service';
import { Sale } from '../../../core/models/api.models';

@Component({
  selector: 'app-sale-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="page">
      <header class="header">
        <div class="container">
          <h1>💰 Vendas</h1>
          <div class="header-actions">
            <button class="btn btn-secondary" (click)="goBack()">← Voltar</button>
            <button class="btn btn-primary" (click)="logout()">Sair</button>
          </div>
        </div>
      </header>

      <div class="container">
        <div class="card">
          <div class="card-header">
            <h2>Histórico de Vendas</h2>
          </div>

          <div class="alert alert-error" *ngIf="errorMessage">
            {{ errorMessage }}
          </div>

          <div class="loading" *ngIf="loading">
            Carregando vendas...
          </div>

          <div *ngIf="!loading && sales.length === 0" class="empty-state">
            <p>Nenhuma venda encontrada.</p>
          </div>

          <table *ngIf="!loading && sales.length > 0">
            <thead>
              <tr>
                <th>ID</th>
                <th>Caixa</th>
                <th>Itens</th>
                <th>Total</th>
                <th>Status</th>
                <th>Pagamento</th>
                <th>Data</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let sale of sales">
                <td>{{ sale.id }}</td>
                <td>{{ sale.cashierName }}</td>
                <td>{{ sale.items.length }}</td>
                <td>{{ sale.totalAmount | currency:'BRL' }}</td>
                <td>
                  <span [class]="'badge badge-' + getStatusClass(sale.status)">
                    {{ getStatusText(sale.status) }}
                  </span>
                </td>
                <td>{{ getPaymentMethodText(sale.paymentMethod) }}</td>
                <td>{{ sale.createdAt | date:'dd/MM/yyyy HH:mm' }}</td>
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

    .badge-warning {
      background-color: #fff3cd;
      color: #856404;
    }

    .badge-danger {
      background-color: #f8d7da;
      color: #721c24;
    }
  `]
})
export class SaleListComponent implements OnInit {
  sales: Sale[] = [];
  loading = false;
  errorMessage = '';

  constructor(
    private saleService: SaleService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadSales();
  }

  loadSales(): void {
    this.loading = true;
    this.errorMessage = '';

    this.saleService.getAll().subscribe({
      next: (response) => {
        if (response.success) {
          this.sales = response.data;
        } else {
          this.errorMessage = response.message || 'Erro ao carregar vendas';
        }
        this.loading = false;
      },
      error: (error: any) => {
        this.errorMessage = error.error?.message || 'Erro ao conectar ao servidor';
        this.loading = false;
      }
    });
  }

  getStatusClass(status: string): string {
    const statusMap: { [key: string]: string } = {
      'OPEN': 'warning',
      'PAID': 'success',
      'CANCELLED': 'danger'
    };
    return statusMap[status] || 'warning';
  }

  getStatusText(status: string): string {
    const statusMap: { [key: string]: string } = {
      'OPEN': 'Aberta',
      'PAID': 'Finalizada',
      'CANCELLED': 'Cancelada'
    };
    return statusMap[status] || status;
  }

  getPaymentMethodText(method?: string | null): string {
    if (!method) {
      return '-';
    }

    const map: { [key: string]: string } = {
      'DINHEIRO': 'Dinheiro',
      'CARTAO': 'Cartão',
      'PIX': 'PIX'
    };

    return map[method] || method;
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }

  logout(): void {
    this.authService.logout();
  }
}
