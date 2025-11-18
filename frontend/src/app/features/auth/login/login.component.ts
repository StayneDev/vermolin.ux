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
        <div class="logo-container">
          <img src="assets/logo-vermole.png" alt="Vermolin.UX" class="logo">
        </div>
        <h1>Vermolin.UX</h1>
        <h2>Sistema de Gestão Hortifruti</h2>
        
        <div class="alert alert-error" *ngIf="errorMessage">
          {{ errorMessage }}
        </div>

        <form (ngSubmit)="onLogin()">
          <div class="form-group">
            <label for="username">
              <span class="icon">👤</span> Usuário
            </label>
            <input 
              type="text" 
              id="username" 
              [(ngModel)]="credentials.username" 
              name="username"
              placeholder="Digite seu usuário"
              required>
          </div>

          <div class="form-group">
            <label for="password">
              <span class="icon">🔒</span> Senha
            </label>
            <input 
              type="password" 
              id="password" 
              [(ngModel)]="credentials.password" 
              name="password"
              placeholder="Digite sua senha"
              required>
          </div>

          <button type="submit" class="btn btn-primary btn-block" [disabled]="loading">
            <span *ngIf="!loading">Entrar</span>
            <span *ngIf="loading" class="loading-spinner"></span>
          </button>
        </form>

        <div class="credentials-info">
          <h3>Credenciais de Teste</h3>
          <div class="credentials-grid">
            <div class="credential-item">
              <span class="role">👔 Gerente</span>
              <span class="cred">gerente / gerente123</span>
            </div>
            <div class="credential-item">
              <span class="role">📦 Estoquista</span>
              <span class="cred">estoquista / estoquista123</span>
            </div>
            <div class="credential-item">
              <span class="role">💰 Caixa</span>
              <span class="cred">caixa / caixa123</span>
            </div>
          </div>
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
      background: linear-gradient(135deg, #1B5E20 0%, #2E7D32 50%, #4CAF50 100%);
      padding: 20px;
      position: relative;
      overflow: hidden;
    }

    .login-container::before {
      content: '';
      position: absolute;
      width: 400px;
      height: 400px;
      background: rgba(255, 255, 255, 0.05);
      border-radius: 50%;
      top: -200px;
      right: -100px;
    }

    .login-container::after {
      content: '';
      position: absolute;
      width: 300px;
      height: 300px;
      background: rgba(255, 255, 255, 0.03);
      border-radius: 50%;
      bottom: -150px;
      left: -50px;
    }

    .login-card {
      width: 100%;
      max-width: 440px;
      text-align: center;
      backdrop-filter: blur(10px);
      position: relative;
      z-index: 1;
      animation: slideUp 0.5s ease-out;
    }

    @keyframes slideUp {
      from {
        opacity: 0;
        transform: translateY(30px);
      }
      to {
        opacity: 1;
        transform: translateY(0);
      }
    }

    .logo-container {
      margin-bottom: 20px;
      animation: fadeIn 0.6s ease-out 0.2s both;
    }

    @keyframes fadeIn {
      from { opacity: 0; }
      to { opacity: 1; }
    }

    .logo {
      max-width: 120px;
      height: auto;
      filter: drop-shadow(0 4px 12px rgba(0,0,0,0.1));
      transition: transform 0.3s ease;
    }

    .logo:hover {
      transform: scale(1.05);
    }

    h1 {
      font-size: 2rem;
      margin-bottom: 8px;
      color: var(--gray-900);
      font-weight: 800;
      letter-spacing: -0.02em;
    }

    h2 {
      font-size: 1rem;
      color: var(--gray-600);
      margin-bottom: 32px;
      font-weight: 500;
    }

    .form-group label {
      display: flex;
      align-items: center;
      gap: 8px;
      justify-content: flex-start;
    }

    .icon {
      font-size: 16px;
    }

    .btn-block {
      width: 100%;
      margin-top: 24px;
      padding: 14px;
      font-size: 15px;
    }

    .loading-spinner {
      display: inline-block;
      width: 16px;
      height: 16px;
      border: 2px solid rgba(255, 255, 255, 0.3);
      border-top-color: white;
      border-radius: 50%;
      animation: spin 0.8s linear infinite;
    }

    @keyframes spin {
      to { transform: rotate(360deg); }
    }

    .credentials-info {
      margin-top: 32px;
      padding-top: 24px;
      border-top: 2px solid var(--gray-100);
      text-align: left;
    }

    .credentials-info h3 {
      font-size: 0.85rem;
      margin-bottom: 16px;
      color: var(--gray-600);
      text-transform: uppercase;
      letter-spacing: 0.05em;
      font-weight: 700;
      text-align: center;
    }

    .credentials-grid {
      display: flex;
      flex-direction: column;
      gap: 10px;
    }

    .credential-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 12px 16px;
      background: var(--gray-50);
      border-radius: var(--radius-sm);
      transition: var(--transition);
      border: 1px solid var(--gray-200);
    }

    .credential-item:hover {
      background: white;
      border-color: var(--primary-light);
      transform: translateX(4px);
    }

    .role {
      font-weight: 600;
      color: var(--gray-800);
      font-size: 0.9rem;
    }

    .cred {
      font-size: 0.85rem;
      color: var(--gray-600);
      font-family: 'Courier New', monospace;
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
