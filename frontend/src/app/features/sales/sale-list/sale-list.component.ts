import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { SaleService } from '../../../core/services/sale.service';
import { AuthService } from '../../../core/services/auth.service';
import { Sale } from '../../../core/models/api.models';
import { ThemeToggleComponent } from '../../../shared/components/theme-toggle/theme-toggle.component';
import { HeaderMenuComponent } from '../../../shared/components/header-menu/header-menu.component';
import { RoleNavComponent } from '../../../shared/components/role-nav/role-nav.component';
import { ProfileAvatarComponent } from '../../../shared/components/profile-avatar/profile-avatar.component';

@Component({
  selector: 'app-sale-list',
  standalone: true,
  imports: [CommonModule, ThemeToggleComponent, HeaderMenuComponent, RoleNavComponent, ProfileAvatarComponent],
  template: `
    <div class="page">
      <header class="header">
        <div class="container header-content">
          <div class="header-copy">
            <h1>💰 Vendas</h1>
            <p class="header-subtitle">Acompanhe o faturamento e status das vendas</p>
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

          <div class="table-responsive" *ngIf="!loading && sales.length > 0">
            <table>
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
          <p class="table-scroll-hint" *ngIf="!loading && sales.length > 0">
            Deslize para acompanhar todos os campos da venda
          </p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .header {
      background: var(--header-gradient);
      color: var(--header-text);
      padding: 24px 0;
      margin-bottom: 32px;
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
      font-size: 1.9rem;
    }

    .header-subtitle {
      margin: 4px 0 0;
      color: rgba(255, 255, 255, 0.85);
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
      padding: 4px 10px;
      border-radius: 999px;
      font-size: 0.75rem;
      font-weight: 600;
    }

    .badge-success {
      background-color: rgba(76, 175, 80, 0.15);
      color: var(--primary);
    }

    .badge-warning {
      background-color: rgba(255, 193, 7, 0.2);
      color: #b37700;
    }

    .badge-danger {
      background-color: rgba(244, 67, 54, 0.2);
      color: #ff5252;
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
