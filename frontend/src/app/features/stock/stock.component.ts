import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { StockService } from '../../core/services/stock.service';
import { ProductService } from '../../core/services/product.service';
import { SupplierService } from '../../core/services/supplier.service';
import { AuthService } from '../../core/services/auth.service';
import { StockMovement, Product, Supplier, MovementReason } from '../../core/models/api.models';

interface StockForm {
  productId: number | null;
  quantity: number | null;
  movementKind: 'ENTRY' | 'EXIT' | 'ADJUSTMENT';
  reason: MovementReason;
  notes: string;
  supplierId: number | null;
  expiryDate: string | null;
}

@Component({
  selector: 'app-stock',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="page">
      <header class="header">
        <div class="container">
          <h1>📊 Estoque</h1>
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
            <h2>Registrar movimentação</h2>
          </div>

          <div class="loading" *ngIf="loadingFormData">
            Carregando dados...
          </div>

          <form *ngIf="!loadingFormData" class="form-grid" (ngSubmit)="submitMovement()">
            <label>
              Produto
              <select [(ngModel)]="form.productId" name="product" class="form-control" required>
                <option [ngValue]="null">Selecione um produto</option>
                <option *ngFor="let product of products" [ngValue]="product.id">
                  {{ product.name }} — Estoque: {{ product.stockQuantity }} {{ product.unit }}
                </option>
              </select>
            </label>

            <label>
              Tipo de movimentação
              <select [(ngModel)]="form.movementKind" name="movementKind" class="form-control">
                <option value="ENTRY">Entrada</option>
                <option value="EXIT">Saída</option>
                <option value="ADJUSTMENT">Ajuste</option>
              </select>
            </label>

            <label>
              Quantidade
              <input
                type="number"
                min="0.001"
                step="0.001"
                [(ngModel)]="form.quantity"
                name="quantity"
                class="form-control"
                required
              />
            </label>

            <label>
              Motivo
              <select [(ngModel)]="form.reason" name="reason" class="form-control">
                <option value="COMPRA">Compra</option>
                <option value="VENDA">Venda</option>
                <option value="PERDA">Perda</option>
                <option value="DOACAO">Doação</option>
                <option value="DEVOLUCAO">Devolução</option>
                <option value="AJUSTE">Ajuste</option>
                <option value="OUTROS">Outros</option>
              </select>
            </label>

            <label *ngIf="form.movementKind === 'ENTRY'">
              Fornecedor
              <select [(ngModel)]="form.supplierId" name="supplier" class="form-control">
                <option [ngValue]="null">Selecione</option>
                <option *ngFor="let supplier of suppliers" [ngValue]="supplier.id">
                  {{ supplier.name }}
                </option>
              </select>
            </label>

            <label *ngIf="form.movementKind === 'ENTRY'">
              Validade (opcional)
              <input
                type="date"
                [(ngModel)]="form.expiryDate"
                name="expiryDate"
                class="form-control"
              />
            </label>

            <label class="notes">
              Observações
              <textarea [(ngModel)]="form.notes" name="notes" class="form-control" rows="2"></textarea>
            </label>

            <div class="actions">
              <button class="btn btn-primary" type="submit" [disabled]="submitting">
                {{ submitting ? 'Registrando...' : 'Registrar movimentação' }}
              </button>
              <button class="btn btn-outline" type="button" (click)="resetForm()">Limpar</button>
            </div>
          </form>
        </div>

        <div class="card">
          <div class="card-header">
            <h2>Histórico de movimentações</h2>
            <button class="btn btn-secondary" type="button" (click)="loadMovements()">Atualizar</button>
          </div>

          <div class="loading" *ngIf="loadingMovements">
            Carregando movimentações...
          </div>

          <div *ngIf="!loadingMovements && movements.length === 0" class="empty-state">
            Nenhuma movimentação encontrada.
          </div>

          <table *ngIf="!loadingMovements && movements.length > 0">
            <thead>
              <tr>
                <th>Data</th>
                <th>Produto</th>
                <th>Tipo</th>
                <th>Quantidade</th>
                <th>Novo estoque</th>
                <th>Motivo</th>
                <th>Responsável</th>
                <th>Fornecedor</th>
                <th>Observações</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let movement of movements">
                <td>{{ movement.createdAt | date:'dd/MM/yyyy HH:mm' }}</td>
                <td>{{ movement.productName }}</td>
                <td>
                  <span [class]="'badge badge-' + getMovementBadge(movement.movementType)">
                    {{ translateMovementType(movement.movementType) }}
                  </span>
                </td>
                <td>{{ movement.quantity }}</td>
                <td>{{ movement.newQuantity }}</td>
                <td>{{ translateMovementReason(movement.reason) }}</td>
                <td>{{ movement.createdByName || '-' }}</td>
                <td>{{ movement.supplierName || '-' }}</td>
                <td>{{ movement.notes || '-' }}</td>
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

    .card + .card {
      margin-top: 30px;
    }

    .form-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
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

    .notes {
      grid-column: 1 / -1;
    }

    .actions {
      display: flex;
      gap: 10px;
      align-items: center;
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

    .badge {
      display: inline-block;
      padding: 4px 8px;
      border-radius: 12px;
      font-size: 0.75rem;
      font-weight: 600;
    }

    .badge-entry {
      background-color: #d4edda;
      color: #155724;
    }

    .badge-exit {
      background-color: #f8d7da;
      color: #721c24;
    }

    .badge-adjustment {
      background-color: #fff3cd;
      color: #856404;
    }
  `]
})
export class StockComponent implements OnInit {
  movements: StockMovement[] = [];
  products: Product[] = [];
  suppliers: Supplier[] = [];

  loadingMovements = false;
  loadingFormData = false;
  submitting = false;

  errorMessage = '';
  successMessage = '';

  form: StockForm = {
    productId: null,
    quantity: null,
    movementKind: 'ENTRY',
    reason: 'COMPRA' as MovementReason,
    notes: '',
    supplierId: null,
    expiryDate: null
  };

  constructor(
    private stockService: StockService,
    private productService: ProductService,
    private supplierService: SupplierService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadFormData();
    this.loadMovements();
  }

  loadFormData(): void {
    this.loadingFormData = true;
    this.errorMessage = '';

    this.productService.getAll().subscribe({
      next: productResponse => {
        if (productResponse.success && productResponse.data) {
          this.products = productResponse.data;
        }
          this.supplierService.getAll().subscribe({
          next: supplierResponse => {
            if (supplierResponse.success && supplierResponse.data) {
              this.suppliers = supplierResponse.data;
            }
            this.loadingFormData = false;
          },
            error: (err: any) => {
            this.loadingFormData = false;
          }
        });
      },
        error: (err: any) => {
        this.loadingFormData = false;
      }
    });
  }

  loadMovements(): void {
    this.loadingMovements = true;
    this.stockService.getMovements().subscribe({
      next: response => {
        if (response.success && response.data) {
          this.movements = response.data;
        } else {
          this.errorMessage = response.message || 'Não foi possível carregar as movimentações.';
        }
        this.loadingMovements = false;
      },
        error: (err: any) => {
        this.errorMessage = err.error?.message || 'Erro ao buscar movimentações.';
        this.loadingMovements = false;
      }
    });
  }

  submitMovement(): void {
    if (!this.form.productId) {
      this.errorMessage = 'Selecione um produto.';
      return;
    }

    if (!this.form.quantity || this.form.quantity <= 0) {
      this.errorMessage = 'Informe uma quantidade válida.';
      return;
    }

    const payload = {
      productId: this.form.productId,
      quantity: this.form.quantity,
      reason: this.form.reason as MovementReason,
      notes: this.form.notes || undefined,
      supplierId: this.form.movementKind === 'ENTRY' ? this.form.supplierId || undefined : undefined,
      expiryDate: this.form.movementKind === 'ENTRY' ? this.form.expiryDate || undefined : undefined
    };

    this.submitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    const request$ = this.form.movementKind === 'ENTRY'
      ? this.stockService.registerEntry(payload)
      : this.form.movementKind === 'EXIT'
        ? this.stockService.registerExit(payload)
        : this.stockService.registerAdjustment(payload);

    request$.subscribe({
      next: response => {
        if (response.success) {
          this.successMessage = response.message || 'Movimentação registrada com sucesso.';
          this.resetForm();
          this.loadMovements();
        } else {
          this.errorMessage = response.message || 'Não foi possível registrar a movimentação.';
        }
        this.submitting = false;
      },
        error: (err: any) => {
        this.errorMessage = err.error?.message || 'Erro ao registrar movimentação.';
        this.submitting = false;
      }
    });
  }

  resetForm(): void {
    this.form = {
      productId: null,
      quantity: null,
      movementKind: 'ENTRY',
      reason: 'COMPRA' as MovementReason,
      notes: '',
      supplierId: null,
      expiryDate: null
    };
  }

  translateMovementType(type: StockMovement['movementType']): string {
    const map: Record<string, string> = {
      'ENTRADA': 'Entrada',
      'SAIDA': 'Saída',
      'AJUSTE': 'Ajuste',
      'VENDA': 'Venda'
    };
    return map[type] || type;
  }

  translateMovementReason(reason: StockMovement['reason'] | null): string {
    if (!reason) {
      return '-';
    }
    const map: Record<string, string> = {
      'COMPRA': 'Compra',
      'VENDA': 'Venda',
      'PERDA': 'Perda',
      'DOACAO': 'Doação',
      'DEVOLUCAO': 'Devolução',
      'AJUSTE': 'Ajuste',
      'OUTROS': 'Outros'
    };
    return map[reason] || reason;
  }

  getMovementBadge(type: StockMovement['movementType']): string {
    switch (type) {
      case 'ENTRADA':
        return 'entry';
      case 'SAIDA':
        return 'exit';
      default:
        return 'adjustment';
    }
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }

  logout(): void {
    this.authService.logout();
  }
}
