import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Sale, Product, PaymentMethod } from '../../../core/models/api.models';
import { SaleService } from '../../../core/services/sale.service';
import { ProductService } from '../../../core/services/product.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-new-sale',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="page">
      <header class="header">
        <div class="container">
          <h1>🛒 Nova Venda</h1>
          <div class="header-actions">
            <button class="btn btn-secondary" (click)="goBack()">← Voltar</button>
            <button class="btn btn-primary" (click)="logout()">Sair</button>
          </div>
        </div>
      </header>

      <div class="container">
        <div class="alert alert-error" *ngIf="errorMessage">
          {{ errorMessage }}
        </div>

        <div class="alert alert-success" *ngIf="successMessage">
          {{ successMessage }}
        </div>

        <div class="card">
          <div class="card-header">
            <div>
              <h2>Montagem da Venda</h2>
              <p *ngIf="sale">Venda #{{ sale.id }} • {{ getStatusText(sale.status) }}</p>
            </div>
            <div class="totals" *ngIf="sale">
              <span>Total: <strong>{{ sale.totalAmount | currency:'BRL' }}</strong></span>
              <span *ngIf="sale.changeAmount !== undefined && sale.changeAmount !== null">
                Troco: <strong>{{ sale.changeAmount | currency:'BRL' }}</strong>
              </span>
            </div>
          </div>

          <div class="loading" *ngIf="loadingSale">
            Abrindo PDV...
          </div>

          <div *ngIf="!loadingSale && !sale">
            <p class="empty-state">Não foi possível abrir uma nova venda. Tente novamente.</p>
            <button class="btn btn-primary" (click)="startSale()">Abrir nova venda</button>
          </div>

          <ng-container *ngIf="!loadingSale && sale">
            <section class="form-section" *ngIf="sale.status === 'OPEN'">
              <h3>Adicionar produto</h3>
              <div class="form-grid">
                <label>
                  Produto
                  <select [(ngModel)]="selectedProductId" name="product" class="form-control">
                    <option [ngValue]="null">Selecione um produto</option>
                    <option *ngFor="let product of products" [ngValue]="product.id">
                      {{ product.name }} — {{ product.price | currency:'BRL' }} ({{ product.unit }})
                    </option>
                  </select>
                </label>

                <label>
                  Quantidade
                  <input
                    type="number"
                    min="0.001"
                    step="0.001"
                    [(ngModel)]="quantity"
                    name="quantity"
                    class="form-control"
                  />
                </label>

                <label class="checkbox">
                  <input type="checkbox" [(ngModel)]="weighed" name="weighed" />
                  Produto pesado
                </label>

                <button
                  class="btn btn-primary"
                  type="button"
                  (click)="addItem()"
                  [disabled]="addingItem"
                >
                  {{ addingItem ? 'Adicionando...' : 'Adicionar Item' }}
                </button>
              </div>
            </section>

            <section class="items-section" *ngIf="sale.items.length > 0; else emptyCart">
              <h3>Itens da venda</h3>
              <table>
                <thead>
                  <tr>
                    <th>Produto</th>
                    <th>Quantidade</th>
                    <th>Preço</th>
                    <th>Subtotal</th>
                    <th *ngIf="sale.status === 'OPEN'">Ações</th>
                  </tr>
                </thead>
                <tbody>
                  <tr *ngFor="let item of sale.items">
                    <td>{{ item.productName }}</td>
                    <td>{{ item.quantity }} {{ item.unit }}</td>
                    <td>{{ item.productPrice | currency:'BRL' }}</td>
                    <td>{{ item.subtotal | currency:'BRL' }}</td>
                    <td *ngIf="sale.status === 'OPEN'">
                      <button class="btn btn-link" (click)="removeItem(item.id)">Remover</button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </section>

            <ng-template #emptyCart>
              <div class="empty-state">
                Nenhum item adicionado. Utilize o formulário acima para iniciar a venda.
              </div>
            </ng-template>

            <section class="payment-section" *ngIf="sale.status === 'OPEN'">
              <h3>Pagamento</h3>
              <div class="form-grid">
                <label>
                  Forma de pagamento
                  <select [(ngModel)]="paymentMethod" name="paymentMethod" class="form-control">
                    <option value="DINHEIRO">Dinheiro</option>
                    <option value="CARTAO">Cartão</option>
                    <option value="PIX">PIX</option>
                  </select>
                </label>

                <label>
                  Valor recebido
                  <input
                    type="number"
                    min="0"
                    step="0.01"
                    [(ngModel)]="amountPaid"
                    name="amountPaid"
                    class="form-control"
                  />
                </label>

                <button
                  class="btn btn-success"
                  type="button"
                  (click)="finalizeSale()"
                  [disabled]="finalizing || sale.items.length === 0"
                >
                  {{ finalizing ? 'Finalizando...' : 'Finalizar venda' }}
                </button>
              </div>
              <p class="hint" *ngIf="sale.items.length === 0">
                Adicione pelo menos um item para habilitar a finalização.
              </p>
            </section>

            <section class="summary" *ngIf="sale.status !== 'OPEN'">
              <h3>Status da venda</h3>
              <p>
                Pagamento: <strong>{{ getPaymentMethodText(sale.paymentMethod) }}</strong>
                <span *ngIf="sale.amountPaid !== undefined && sale.amountPaid !== null">
                  • Valor pago: <strong>{{ sale.amountPaid | currency:'BRL' }}</strong>
                </span>
              </p>
              <p *ngIf="sale.changeAmount !== undefined && sale.changeAmount !== null">
                Troco: <strong>{{ sale.changeAmount | currency:'BRL' }}</strong>
              </p>
              <div class="actions">
                <button class="btn btn-primary" (click)="startSale()">Abrir nova venda</button>
              </div>
            </section>

            <section class="cancel-section" *ngIf="sale.status === 'OPEN'">
              <h3>Cancelar venda</h3>
              <div class="form-grid">
                <label>
                  Motivo
                  <input
                    type="text"
                    [(ngModel)]="cancelReason"
                    name="cancelReason"
                    class="form-control"
                    placeholder="Opcional"
                  />
                </label>
                <button
                  class="btn btn-outline"
                  type="button"
                  (click)="cancelSale()"
                  [disabled]="cancelling"
                >
                  {{ cancelling ? 'Cancelando...' : 'Cancelar venda' }}
                </button>
              </div>
            </section>
          </ng-container>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .header {
      background: #4CAF50;
      color: #fff;
      padding: 20px 0;
      margin-bottom: 30px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .header .container {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: 20px;
      padding-bottom: 15px;
      border-bottom: 2px solid #eee;
      margin-bottom: 20px;
    }

    .totals {
      display: flex;
      flex-direction: column;
      gap: 4px;
      text-align: right;
    }

    .form-section,
    .payment-section,
    .cancel-section,
    .items-section,
    .summary {
      margin-bottom: 30px;
    }

    .form-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 15px;
      align-items: end;
    }

    .form-control {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 6px;
      font-size: 1rem;
    }

    .checkbox {
      display: flex;
      align-items: center;
      gap: 8px;
      font-weight: 500;
    }

    table {
      width: 100%;
      border-collapse: collapse;
    }

    th, td {
      padding: 12px;
      border-bottom: 1px solid #eee;
      text-align: left;
    }

    .btn-link {
      background: none;
      border: none;
      color: #d9534f;
      cursor: pointer;
      text-decoration: underline;
      padding: 0;
      font-size: 0.9rem;
    }

    .summary {
      background: #f7fdf7;
      padding: 20px;
      border-radius: 8px;
      border: 1px solid #cde8ce;
    }

    .summary .actions {
      margin-top: 15px;
    }
  `]
})
export class NewSaleComponent implements OnInit {
  sale: Sale | null = null;
  products: Product[] = [];
  selectedProductId: number | null = null;
  quantity = 1;
  weighed = false;
  paymentMethod: PaymentMethod = 'DINHEIRO';
  amountPaid: number | null = null;
  cancelReason = '';

  loadingSale = false;
  loadingProducts = false;
  addingItem = false;
  finalizing = false;
  cancelling = false;

  errorMessage = '';
  successMessage = '';

  constructor(
    private saleService: SaleService,
    private productService: ProductService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadProducts();
    this.startSale();
  }

  startSale(preserveMessage = false): void {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser || currentUser.role !== 'CAIXA') {
      this.errorMessage = 'Apenas usuários com perfil CAIXA podem abrir uma venda.';
      this.sale = null;
      return;
    }

    this.loadingSale = true;
    this.errorMessage = '';
    if (!preserveMessage) {
      this.successMessage = '';
    }

    this.saleService.create().subscribe({
      next: response => {
        if (response.success && response.data) {
          this.sale = response.data;
          this.amountPaid = this.sale.totalAmount;
        } else {
          this.errorMessage = response.message || 'Não foi possível abrir a venda.';
          this.sale = null;
        }
        this.loadingSale = false;
      },
      error: (err: any) => {
        this.errorMessage = err.error?.message || 'Erro ao abrir nova venda.';
        this.loadingSale = false;
        this.sale = null;
      }
    });
  }

  loadProducts(): void {
    this.loadingProducts = true;
    this.productService.getAll().subscribe({
      next: response => {
        if (response.success && response.data) {
          this.products = response.data.filter(product => product.active);
        }
        this.loadingProducts = false;
      },
      error: () => {
        this.loadingProducts = false;
      }
    });
  }

  addItem(): void {
    if (!this.sale) {
      return;
    }

    if (!this.selectedProductId) {
      this.errorMessage = 'Selecione um produto.';
      return;
    }

    if (!this.quantity || this.quantity <= 0) {
      this.errorMessage = 'Informe uma quantidade válida.';
      return;
    }

    this.addingItem = true;
    this.errorMessage = '';

    this.saleService.addItem(this.sale.id, this.selectedProductId, this.quantity, this.weighed).subscribe({
      next: response => {
        if (response.success && response.data) {
          this.sale = response.data;
          this.amountPaid = this.sale.totalAmount;
          this.successMessage = 'Item adicionado com sucesso.';
          this.clearItemForm();
        } else {
          this.errorMessage = response.message || 'Não foi possível adicionar o item.';
        }
        this.addingItem = false;
      },
      error: (err: any) => {
        this.errorMessage = err.error?.message || 'Erro ao adicionar item.';
        this.addingItem = false;
      }
    });
  }

  clearItemForm(): void {
    this.selectedProductId = null;
    this.quantity = 1;
    this.weighed = false;
  }

  removeItem(itemId: number): void {
    if (!this.sale) {
      return;
    }

    this.saleService.removeItem(this.sale.id, itemId).subscribe({
      next: response => {
        if (response.success && response.data) {
          this.sale = response.data;
          this.amountPaid = this.sale.totalAmount;
          this.successMessage = 'Item removido com sucesso.';
        }
      },
      error: (err: any) => {
        this.errorMessage = err.error?.message || 'Erro ao remover item.';
      }
    });
  }

  finalizeSale(): void {
    if (!this.sale) {
      return;
    }

    if (!this.amountPaid || this.amountPaid < this.sale.totalAmount) {
      this.errorMessage = 'O valor recebido deve ser maior ou igual ao total da venda.';
      return;
    }

    this.finalizing = true;
    this.errorMessage = '';

    this.saleService.finalize(this.sale.id, this.paymentMethod, this.amountPaid).subscribe({
      next: response => {
        if (response.success && response.data) {
          this.sale = response.data;
          this.successMessage = response.message || 'Venda finalizada com sucesso.';
        } else {
          this.errorMessage = response.message || 'Não foi possível finalizar a venda.';
        }
        this.finalizing = false;
      },
      error: (err: any) => {
        this.errorMessage = err.error?.message || 'Erro ao finalizar venda.';
        this.finalizing = false;
      }
    });
  }

  cancelSale(): void {
    if (!this.sale) {
      return;
    }

    this.cancelling = true;
    this.errorMessage = '';

    this.saleService.cancel(this.sale.id, this.cancelReason).subscribe({
      next: response => {
        if (response.success) {
          this.successMessage = response.message || 'Venda cancelada.';
          this.sale = null;
          this.cancelReason = '';
          this.startSale(true);
        } else {
          this.errorMessage = response.message || 'Não foi possível cancelar a venda.';
        }
        this.cancelling = false;
      },
      error: (err: any) => {
        this.errorMessage = err.error?.message || 'Erro ao cancelar venda.';
        this.cancelling = false;
      }
    });
  }

  getStatusText(status: Sale['status']): string {
    const map: Record<Sale['status'], string> = {
      'OPEN': 'Em andamento',
      'PAID': 'Finalizada',
      'CANCELLED': 'Cancelada'
    };
    return map[status] || status;
  }

  getPaymentMethodText(method?: PaymentMethod | null): string {
    if (!method) {
      return '-';
    }
    const map: Record<PaymentMethod, string> = {
      'DINHEIRO': 'Dinheiro',
      'CARTAO': 'Cartão',
      'PIX': 'PIX'
    };
    return map[method];
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }

  logout(): void {
    this.authService.logout();
  }
}
