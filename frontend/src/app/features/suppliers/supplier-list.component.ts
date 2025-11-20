import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SupplierService } from '../../core/services/supplier.service';
import { AuthService } from '../../core/services/auth.service';
import { Supplier, SupplierPayload } from '../../core/models/api.models';
import { ThemeToggleComponent } from '../../shared/components/theme-toggle/theme-toggle.component';
import { HeaderMenuComponent } from '../../shared/components/header-menu/header-menu.component';
import { RoleNavComponent } from '../../shared/components/role-nav/role-nav.component';
import { ProfileAvatarComponent } from '../../shared/components/profile-avatar/profile-avatar.component';

@Component({
  selector: 'app-supplier-list',
  standalone: true,
  imports: [CommonModule, FormsModule, ThemeToggleComponent, HeaderMenuComponent, RoleNavComponent, ProfileAvatarComponent],
  template: `
    <div class="page">
      <header class="header">
        <div class="container header-content">
          <div class="header-copy">
            <h1>🚚 Fornecedores</h1>
            <p class="header-subtitle">Cadastre parceiros e mantenha o contato atualizado</p>
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
        <div class="alert alert-error" *ngIf="errorMessage">
          {{ errorMessage }}
        </div>
        <div class="alert alert-success" *ngIf="successMessage">
          {{ successMessage }}
        </div>

        <div class="card">
          <div class="card-header">
            <div>
              <h2>{{ editingSupplier ? 'Editar fornecedor' : 'Cadastrar fornecedor' }}</h2>
              <p *ngIf="editingSupplier">Editando: {{ editingSupplier.name }}</p>
            </div>
            <button *ngIf="editingSupplier" class="btn btn-outline" type="button" (click)="cancelEdit()">Cancelar edição</button>
          </div>

          <form class="form-grid" (ngSubmit)="submit()">
            <label>
              Nome
              <input type="text" class="form-control" [(ngModel)]="form.name" name="name" required />
            </label>

            <label>
              CNPJ (apenas números)
              <input type="text" class="form-control" [(ngModel)]="form.cnpj" name="cnpj" maxlength="14" required />
            </label>

            <label>
              Contato
              <input type="text" class="form-control" [(ngModel)]="form.contactName" name="contactName" />
            </label>

            <label>
              Telefone
              <input type="text" class="form-control" [(ngModel)]="form.phone" name="phone" />
            </label>

            <label>
              Email
              <input type="email" class="form-control" [(ngModel)]="form.email" name="email" />
            </label>

            <label class="full-width">
              Endereço
              <input type="text" class="form-control" [(ngModel)]="form.address" name="address" />
            </label>

            <label class="checkbox">
              <input type="checkbox" [(ngModel)]="form.active" name="active" />
              Fornecedor ativo
            </label>

            <div class="actions">
              <button class="btn btn-primary" type="submit" [disabled]="submitting">
                {{ submitting ? 'Salvando...' : editingSupplier ? 'Atualizar fornecedor' : 'Cadastrar fornecedor' }}
              </button>
              <button class="btn btn-outline" type="button" (click)="resetForm()">Limpar</button>
            </div>
          </form>
        </div>

        <div class="card">
          <div class="card-header">
            <h2>Lista de fornecedores</h2>
            <button class="btn btn-secondary" type="button" (click)="loadSuppliers()">Atualizar</button>
          </div>

          <div class="loading" *ngIf="loading">
            Carregando fornecedores...
          </div>

          <div *ngIf="!loading && suppliers.length === 0" class="empty-state">
            Nenhum fornecedor cadastrado.
          </div>

          <div class="table-responsive" *ngIf="!loading && suppliers.length > 0">
            <table>
              <thead>
                <tr>
                  <th>Nome</th>
                  <th>CNPJ</th>
                  <th>Contato</th>
                  <th>Telefone</th>
                  <th>Email</th>
                  <th>Status</th>
                  <th>Ações</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let supplier of suppliers">
                  <td>{{ supplier.name }}</td>
                  <td>{{ supplier.cnpj }}</td>
                  <td>{{ supplier.contactName || '-' }}</td>
                  <td>{{ supplier.phone || '-' }}</td>
                  <td>{{ supplier.email || '-' }}</td>
                  <td>
                    <span [class]="'badge ' + (supplier.active ? 'badge-success' : 'badge-inactive')">
                      {{ supplier.active ? 'Ativo' : 'Inativo' }}
                    </span>
                  </td>
                  <td class="actions">
                    <button class="btn btn-link" type="button" (click)="editSupplier(supplier)">Editar</button>
                    <button class="btn btn-link" type="button" (click)="toggleActive(supplier)">
                      {{ supplier.active ? 'Inativar' : 'Reativar' }}
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <p class="table-scroll-hint" *ngIf="!loading && suppliers.length > 0">
            Arraste para visualizar os detalhes de contato
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

    .form-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
      gap: 16px;
      align-items: end;
    }

    .form-control {
      width: 100%;
      padding: 12px;
      border: 2px solid var(--input-border);
      border-radius: var(--radius-sm);
      font-size: 1rem;
      background: var(--input-bg);
      color: var(--text-color);
    }

    .checkbox {
      display: flex;
      align-items: center;
      gap: 8px;
      font-weight: 500;
    }

    .full-width {
      grid-column: 1 / -1;
    }

    .actions {
      display: flex;
      gap: 12px;
      align-items: center;
      flex-wrap: wrap;
    }

    .btn-link {
      background: none;
      border: none;
      color: var(--primary-light);
      cursor: pointer;
      text-decoration: underline;
      padding: 0;
      font-weight: 600;
    }

    .badge-success {
      background-color: rgba(76, 175, 80, 0.15);
      color: var(--primary);
    }

    .badge-inactive {
      background-color: rgba(244, 67, 54, 0.15);
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

      .form-grid {
        grid-template-columns: 1fr;
      }

      .actions {
        flex-direction: column;
        align-items: stretch;
      }
    }
  `]
})
export class SupplierListComponent implements OnInit {
  suppliers: Supplier[] = [];
  loading = false;
  submitting = false;
  editingSupplier: Supplier | null = null;

  errorMessage = '';
  successMessage = '';

  form: SupplierPayload = {
    name: '',
    cnpj: '',
    contactName: '',
    phone: '',
    email: '',
    address: '',
    active: true
  };

  constructor(
    private supplierService: SupplierService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadSuppliers();
  }

  loadSuppliers(): void {
    this.loading = true;
    this.errorMessage = '';

    this.supplierService.getAll().subscribe({
      next: response => {
        if (response.success && response.data) {
          this.suppliers = response.data;
        } else {
          this.errorMessage = response.message || 'Não foi possível carregar os fornecedores.';
        }
        this.loading = false;
      },
      error: (err: any) => {
        this.errorMessage = err.error?.message || 'Erro ao carregar fornecedores.';
        this.loading = false;
      }
    });
  }

  submit(): void {
    if (!this.form.name || !this.form.cnpj) {
      this.errorMessage = 'Nome e CNPJ são obrigatórios.';
      return;
    }

    this.submitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    const payload: SupplierPayload = {
      name: this.form.name.trim(),
      cnpj: this.form.cnpj.replace(/\D/g, ''),
      contactName: this.form.contactName?.trim() || undefined,
      phone: this.form.phone?.trim() || undefined,
      email: this.form.email?.trim() || undefined,
      address: this.form.address?.trim() || undefined,
      active: this.form.active
    };

    const request$ = this.editingSupplier
      ? this.supplierService.update(this.editingSupplier.id, payload)
      : this.supplierService.create(payload);

    request$.subscribe({
      next: response => {
        if (response.success && response.data) {
          this.successMessage = response.message || 'Fornecedor salvo com sucesso.';
          this.loadSuppliers();
          this.resetForm();
        } else {
          this.errorMessage = response.message || 'Não foi possível salvar o fornecedor.';
        }
        this.submitting = false;
      },
      error: (err: any) => {
        this.errorMessage = err.error?.message || 'Erro ao salvar fornecedor.';
        this.submitting = false;
      }
    });
  }

  editSupplier(supplier: Supplier): void {
    this.editingSupplier = supplier;
    this.form = {
      name: supplier.name,
      cnpj: supplier.cnpj,
      contactName: supplier.contactName || '',
      phone: supplier.phone || '',
      email: supplier.email || '',
      address: supplier.address || '',
      active: supplier.active
    };
  }

  cancelEdit(): void {
    this.editingSupplier = null;
    this.resetForm();
  }

  resetForm(): void {
    this.form = {
      name: '',
      cnpj: '',
      contactName: '',
      phone: '',
      email: '',
      address: '',
      active: true
    };
    this.editingSupplier = null;
  }

  toggleActive(supplier: Supplier): void {
    if (supplier.active) {
      this.supplierService.delete(supplier.id).subscribe({
        next: response => {
          if (response.success) {
            this.successMessage = response.message || 'Fornecedor inativado.';
            this.loadSuppliers();
          } else {
            this.errorMessage = response.message || 'Não foi possível inativar o fornecedor.';
          }
        },
        error: (err: any) => {
          this.errorMessage = err.error?.message || 'Erro ao inativar fornecedor.';
        }
      });
    } else {
      this.supplierService.reactivate(supplier.id).subscribe({
        next: response => {
          if (response.success) {
            this.successMessage = response.message || 'Fornecedor reativado.';
            this.loadSuppliers();
          } else {
            this.errorMessage = response.message || 'Não foi possível reativar o fornecedor.';
          }
        },
        error: (err: any) => {
          this.errorMessage = err.error?.message || 'Erro ao reativar fornecedor.';
        }
      });
    }
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }

  logout(): void {
    this.authService.logout();
  }
}
