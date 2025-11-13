import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="login-container">
      <div class="login-card card">
        <h1>🥬 Vermolin.UX</h1>
        <h2>Sistema de Hortifruti</h2>
        
        <div class="alert alert-error" *ngIf="errorMessage">
          {{ errorMessage }}
        </div>

        <form (ngSubmit)="onLogin()">
          <div class="form-group">
            <label for="username">Usuário</label>
            <input 
              type="text" 
              id="username" 
              [(ngModel)]="credentials.username" 
              name="username"
              placeholder="Digite seu usuário"
              required>
          </div>

          <div class="form-group">
            <label for="password">Senha</label>
            <input 
              type="password" 
              id="password" 
              [(ngModel)]="credentials.password" 
              name="password"
              placeholder="Digite sua senha"
              required>
          </div>

          <button type="submit" class="btn btn-primary btn-block" [disabled]="loading">
            {{ loading ? 'Entrando...' : 'Entrar' }}
          </button>
        </form>

        <div class="credentials-info">
          <h3>👤 Credenciais de Teste:</h3>
          <ul>
            <li><strong>Gerente:</strong> gerente / gerente123</li>
            <li><strong>Estoquista:</strong> estoquista / estoquista123</li>
            <li><strong>Caixa:</strong> caixa / caixa123</li>
          </ul>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .login-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      padding: 20px;
    }

    .login-card {
      width: 100%;
      max-width: 400px;
      text-align: center;
    }

    h1 {
      font-size: 2.5rem;
      margin-bottom: 0;
      color: #4CAF50;
    }

    h2 {
      font-size: 1.2rem;
      color: #666;
      margin-bottom: 30px;
    }

    .btn-block {
      width: 100%;
      margin-top: 10px;
    }

    .credentials-info {
      margin-top: 30px;
      padding-top: 20px;
      border-top: 1px solid #eee;
      text-align: left;
    }

    .credentials-info h3 {
      font-size: 0.9rem;
      margin-bottom: 10px;
      color: #666;
    }

    .credentials-info ul {
      list-style: none;
      padding: 0;
    }

    .credentials-info li {
      padding: 5px 0;
      font-size: 0.85rem;
      color: #777;
    }
  `]
})
export class LoginComponent {
  credentials = {
    username: '',
    password: ''
  };
  errorMessage = '';
  loading = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onLogin(): void {
    if (!this.credentials.username || !this.credentials.password) {
      this.errorMessage = 'Preencha todos os campos';
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        if (response.success) {
          this.router.navigate(['/dashboard']);
        } else {
          this.errorMessage = response.message || 'Erro ao fazer login';
        }
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Erro ao conectar ao servidor';
        this.loading = false;
      }
    });
  }
}
