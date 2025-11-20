import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../core/services/user.service';
import { AuthService } from '../../core/services/auth.service';
import { User, UserPayload, UserRole } from '../../core/models/api.models';
import { ThemeToggleComponent } from '../../shared/components/theme-toggle/theme-toggle.component';
import { HeaderMenuComponent } from '../../shared/components/header-menu/header-menu.component';
import { RoleNavComponent } from '../../shared/components/role-nav/role-nav.component';
import { ProfileAvatarComponent } from '../../shared/components/profile-avatar/profile-avatar.component';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, FormsModule, ThemeToggleComponent, HeaderMenuComponent, RoleNavComponent, ProfileAvatarComponent],
  template: `
    <div class="page">
      <header class="header">
        <div class="container header-content">
          <div class="header-copy">
            <h1>👥 Usuários</h1>
            <p class="header-subtitle">Gerencie acessos e perfis da equipe</p>
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
              <h2>{{ editingUser ? 'Editar usuário' : 'Cadastrar usuário' }}</h2>
              <p *ngIf="editingUser">Editando: {{ editingUser.fullName }} ({{ editingUser.username }})</p>
            </div>
            <button *ngIf="editingUser" class="btn btn-outline" type="button" (click)="cancelEdit()">Cancelar edição</button>
          </div>

          <form class="form-grid" (ngSubmit)="submit()">
            <label>
              Usuário
              <input type="text" class="form-control" [(ngModel)]="form.username" name="username" required />
            </label>

            <label>
              Nome completo
              <input type="text" class="form-control" [(ngModel)]="form.fullName" name="fullName" required />
            </label>

            <label>
              Senha {{ editingUser ? '(opcional)' : '' }}
              <input type="password" class="form-control" [(ngModel)]="form.password" name="password" [required]="!editingUser" />
            </label>

            <label>
              Perfil
              <select class="form-control" [(ngModel)]="form.role" name="role" required>
                <option value="GERENTE">Gerente</option>
                <option value="ESTOQUISTA">Estoquista</option>
                <option value="CAIXA">Caixa</option>
              </select>
            </label>

            <label class="checkbox">
              <input type="checkbox" [(ngModel)]="form.active" name="active" />
              Usuário ativo
            </label>

            <div class="actions">
              <button class="btn btn-primary" type="submit" [disabled]="submitting">
                {{ submitting ? 'Salvando...' : editingUser ? 'Atualizar usuário' : 'Cadastrar usuário' }}
              </button>
              <button class="btn btn-outline" type="button" (click)="resetForm()">Limpar</button>
            </div>
          </form>
        </div>

        <div class="card">
          <div class="card-header">
            <h2>Lista de usuários</h2>
            <button class="btn btn-secondary" type="button" (click)="loadUsers()">Atualizar</button>
          </div>

          <div class="loading" *ngIf="loading">
            Carregando usuários...
          </div>

          <div *ngIf="!loading && users.length === 0" class="empty-state">
            Nenhum usuário cadastrado.
          </div>

          <div class="table-responsive" *ngIf="!loading && users.length > 0">
            <table>
              <thead>
                <tr>
                  <th>Usuário</th>
                  <th>Nome</th>
                  <th>Perfil</th>
                  <th>Status</th>
                  <th>Criado em</th>
                  <th>Ações</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let user of users">
                  <td>{{ user.username }}</td>
                  <td>{{ user.fullName }}</td>
                  <td>{{ translateRole(user.role) }}</td>
                  <td>
                    <span [class]="'badge ' + (user.active ? 'badge-success' : 'badge-inactive')">
                      {{ user.active ? 'Ativo' : 'Inativo' }}
                    </span>
                  </td>
                  <td>{{ user.createdAt | date:'dd/MM/yyyy HH:mm' }}</td>
                  <td class="actions">
                    <button class="btn btn-link" type="button" (click)="editUser(user)">Editar</button>
                    <button class="btn btn-link" type="button" (click)="toggleActive(user)">
                      {{ user.active ? 'Inativar' : 'Reativar' }}
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <p class="table-scroll-hint" *ngIf="!loading && users.length > 0">
            Deslize para ver todas as ações disponíveis
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
export class UserListComponent implements OnInit {
  users: User[] = [];
  loading = false;
  submitting = false;
  editingUser: User | null = null;

  errorMessage = '';
  successMessage = '';

  form: UserPayload = {
    username: '',
    password: '',
    fullName: '',
    role: 'ESTOQUISTA',
    active: true
  };

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading = true;
    this.errorMessage = '';

    this.userService.getAll().subscribe({
      next: response => {
        if (response.success && response.data) {
          this.users = response.data;
        } else {
          this.errorMessage = response.message || 'Não foi possível carregar os usuários.';
        }
        this.loading = false;
      },
      error: (err: any) => {
        this.errorMessage = err.error?.message || 'Erro ao carregar usuários.';
        this.loading = false;
      }
    });
  }

  submit(): void {
    if (!this.form.username || !this.form.fullName) {
      this.errorMessage = 'Usuário e nome completo são obrigatórios.';
      return;
    }

    if (!this.editingUser && !this.form.password) {
      this.errorMessage = 'Informe uma senha para o novo usuário.';
      return;
    }

    this.submitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    const payload: UserPayload = {
      username: this.form.username.trim(),
      fullName: this.form.fullName.trim(),
      role: this.form.role,
      active: this.form.active,
      password: this.form.password ? this.form.password : undefined
    };

    const request$ = this.editingUser
      ? this.userService.update(this.editingUser.id, payload)
      : this.userService.create(payload);

    request$.subscribe({
      next: response => {
        if (response.success && response.data) {
          this.successMessage = response.message || 'Usuário salvo com sucesso.';
          this.loadUsers();
          this.resetForm();
        } else {
          this.errorMessage = response.message || 'Não foi possível salvar o usuário.';
        }
        this.submitting = false;
      },
      error: (err: any) => {
        this.errorMessage = err.error?.message || 'Erro ao salvar usuário.';
        this.submitting = false;
      }
    });
  }

  editUser(user: User): void {
    this.editingUser = user;
    this.form = {
      username: user.username,
      fullName: user.fullName,
      role: user.role,
      active: user.active,
      password: ''
    };
  }

  cancelEdit(): void {
    this.editingUser = null;
    this.resetForm();
  }

  resetForm(): void {
    this.form = {
      username: '',
      password: '',
      fullName: '',
      role: 'ESTOQUISTA',
      active: true
    };
    this.editingUser = null;
  }

  toggleActive(user: User): void {
    if (user.active) {
      this.userService.delete(user.id).subscribe({
        next: response => {
          if (response.success) {
            this.successMessage = response.message || 'Usuário inativado com sucesso.';
            this.loadUsers();
          } else {
            this.errorMessage = response.message || 'Não foi possível inativar o usuário.';
          }
        },
        error: (err: any) => {
          this.errorMessage = err.error?.message || 'Erro ao inativar usuário.';
        }
      });
    } else {
      const payload: UserPayload = {
        username: user.username,
        fullName: user.fullName,
        role: user.role,
        active: true
      };
      this.userService.update(user.id, payload).subscribe({
        next: response => {
          if (response.success) {
            this.successMessage = response.message || 'Usuário reativado com sucesso.';
            this.loadUsers();
          } else {
            this.errorMessage = response.message || 'Não foi possível reativar o usuário.';
          }
        },
        error: (err: any) => {
          this.errorMessage = err.error?.message || 'Erro ao reativar usuário.';
        }
      });
    }
  }

  translateRole(role: UserRole): string {
    const map: Record<UserRole, string> = {
      'GERENTE': 'Gerente',
      'ESTOQUISTA': 'Estoquista',
      'CAIXA': 'Caixa'
    };
    return map[role];
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }

  logout(): void {
    this.authService.logout();
  }
}
