import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { LoginResponse } from '../../core/models/api.models';
import { ThemeToggleComponent } from '../../shared/components/theme-toggle/theme-toggle.component';
import { HeaderMenuComponent } from '../../shared/components/header-menu/header-menu.component';
import { RoleNavComponent } from '../../shared/components/role-nav/role-nav.component';
import { ProfileAvatarComponent } from '../../shared/components/profile-avatar/profile-avatar.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, ThemeToggleComponent, HeaderMenuComponent, RoleNavComponent, ProfileAvatarComponent],
  template: `
    <div class="dashboard">
      <header class="header">
        <div class="container header-content">
          <div class="brand">
            <h1>Vermolin.UX</h1>
          </div>
          <div class="user-info">
            <app-header-menu>
              <app-role-nav></app-role-nav>
              <div class="user-badge">
                <span class="user-name">{{ currentUser?.fullName }}</span>
                <span class="user-role">{{ translateRole(currentUser?.role) }}</span>
              </div>
              <app-theme-toggle></app-theme-toggle>
              <app-profile-avatar></app-profile-avatar>
              <button class="btn btn-secondary" (click)="logout()">
                <span class="btn-icon"></span> Sair
              </button>
            </app-header-menu>
          </div>
        </div>
      </header>

      <div class="container">
        <div class="welcome-card card">
          <h2>👋 Bem-vindo ao Sistema Vermolin.UX!</h2>
          <p>Selecione uma opção abaixo para começar:</p>
        </div>

        <div class="menu-grid">
          <div class="menu-card card" (click)="navigate('/products')">
            <div class="card-icon">📦</div>
            <h3>Produtos</h3>
            <p>Gerenciar produtos do estoque</p>
            <div class="card-arrow">→</div>
          </div>

          <div class="menu-card card" (click)="navigate('/sales/new')" *ngIf="currentUser?.role === 'CAIXA'">
            <div class="card-icon">🛒</div>
            <h3>Nova Venda</h3>
            <p>Realizar venda (PDV)</p>
            <div class="card-arrow">→</div>
          </div>

          <div class="menu-card card" (click)="navigate('/sales')" *ngIf="currentUser?.role === 'GERENTE'">
            <div class="card-icon">💰</div>
            <h3>Vendas</h3>
            <p>Consultar histórico</p>
            <div class="card-arrow">→</div>
          </div>

          <div class="menu-card card" (click)="navigate('/stock')" *ngIf="currentUser?.role === 'GERENTE' || currentUser?.role === 'ESTOQUISTA'">
            <div class="card-icon">📊</div>
            <h3>Estoque</h3>
            <p>Controlar movimentações</p>
            <div class="card-arrow">→</div>
          </div>

          <div class="menu-card card" (click)="navigate('/suppliers')" *ngIf="currentUser?.role === 'GERENTE' || currentUser?.role === 'ESTOQUISTA'">
            <div class="card-icon">🚚</div>
            <h3>Fornecedores</h3>
            <p>Cadastro de fornecedores</p>
            <div class="card-arrow">→</div>
          </div>

          <div class="menu-card card" (click)="navigate('/users')" *ngIf="currentUser?.role === 'GERENTE'">
            <div class="card-icon">👥</div>
            <h3>Usuários</h3>
            <p>Gerenciar usuários do sistema</p>
            <div class="card-arrow">→</div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .dashboard {
      min-height: 100vh;
      background: var(--app-gradient);
      color: var(--text-color);
    }

    .header {
      background: var(--header-gradient);
      color: var(--header-text);
      padding: 1.5rem 0;
      box-shadow: var(--shadow-md);
      position: relative;
      overflow: visible;
      z-index: 80;
    }

    .header::before {
      content: '';
      position: absolute;
      top: -50%;
      right: -10%;
      width: 300px;
      height: 300px;
      background: rgba(255, 255, 255, 0.05);
      border-radius: 50%;
    }

    .header::after {
      content: '';
      position: absolute;
      bottom: -30%;
      left: -5%;
      width: 200px;
      height: 200px;
      background: rgba(255, 255, 255, 0.05);
      border-radius: 50%;
    }

    .header-content {
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: 1rem;
      flex-wrap: nowrap;
      position: relative;
      z-index: 90;
    }

    .dashboard > .container {
      position: relative;
      z-index: 0;
    }

    .brand {
      display: flex;
      align-items: center;
      gap: 1rem;
      animation: fadeInLeft 0.6s cubic-bezier(0.16, 1, 0.3, 1);
      flex: 1 1 auto;
      min-width: 0;
    }

    .header-logo {
      height: 60px;
      width: auto;
      filter: drop-shadow(0 2px 8px rgba(0, 0, 0, 0.2));
      transition: transform 0.3s cubic-bezier(0.16, 1, 0.3, 1);
    }

    .header-logo:hover {
      transform: scale(1.1) rotate(-5deg);
    }

    .brand h1 {
      margin: 0;
      font-size: 1.8rem;
      font-weight: 700;
      letter-spacing: -0.5px;
    }

    .user-info {
      display: flex;
      align-items: center;
      gap: 1rem;
      animation: fadeInRight 0.6s cubic-bezier(0.16, 1, 0.3, 1);
      flex-wrap: nowrap;
      justify-content: flex-end;
      flex: 0 0 auto;
    }

    .user-badge {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      padding: 0.5rem 1rem;
      background: rgba(255, 255, 255, 0.1);
      border-radius: var(--radius-md);
      backdrop-filter: blur(10px);
      border: 1px solid rgba(255, 255, 255, 0.2);
      text-align: left;
    }

    .user-name {
      font-weight: 600;
      font-size: 0.95rem;
    }

    .user-role {
      font-size: 0.75rem;
      opacity: 0.9;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }

    .btn-icon {
      display: inline-block;
      margin-right: 0.5rem;
    }

    .welcome-card {
      text-align: center;
      margin: 2rem 0;
      padding: 2rem;
      animation: slideUp 0.6s cubic-bezier(0.16, 1, 0.3, 1) 0.2s backwards;
      background: var(--card-bg);
      border-radius: var(--radius-lg);
      box-shadow: var(--shadow-sm);
      border: 1px solid var(--border-color);
    }

    .welcome-card h2 {
      color: var(--primary-light);
      margin-bottom: 0.5rem;
      font-weight: 700;
      font-size: 1.5rem;
    }

    .welcome-card p {
      color: var(--muted-text);
      font-size: 1rem;
    }

    .menu-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
      gap: 1.5rem;
      margin-bottom: 3rem;
      animation: fadeIn 0.8s cubic-bezier(0.16, 1, 0.3, 1) 0.4s backwards;
    }

    .menu-card {
      text-align: center;
      cursor: pointer;
      transition: all 0.3s cubic-bezier(0.16, 1, 0.3, 1);
      border: 2px solid var(--border-color);
      position: relative;
      overflow: hidden;
      background: var(--card-bg);
      padding: 2rem 1.5rem;
      color: var(--text-color);
    }

    .menu-card::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: linear-gradient(135deg, var(--primary) 0%, #4CAF50 100%);
      opacity: 0;
      transition: opacity 0.3s cubic-bezier(0.16, 1, 0.3, 1);
      z-index: 0;
    }

    .menu-card:hover::before {
      opacity: 0.05;
    }

    .menu-card:hover {
      transform: translateY(-8px) scale(1.02);
      box-shadow: var(--shadow-lg);
      border-color: var(--primary);
    }

    .menu-card > * {
      position: relative;
      z-index: 1;
    }

    .card-icon {
      font-size: 3.5rem;
      margin-bottom: 1rem;
      transition: transform 0.3s cubic-bezier(0.16, 1, 0.3, 1);
      display: inline-block;
    }

    .menu-card:hover .card-icon {
      transform: scale(1.2) rotate(10deg);
    }

    .menu-card h3 {
      color: var(--text-color);
      margin-bottom: 0.5rem;
      font-weight: 700;
      font-size: 1.25rem;
      transition: color 0.3s;
    }

    .menu-card:hover h3 {
      color: var(--primary);
    }

    .menu-card p {
      color: var(--muted-text);
      font-size: 0.9rem;
      margin-bottom: 0.75rem;
    }

    .card-arrow {
      font-size: 1.5rem;
      color: var(--primary);
      font-weight: bold;
      transition: transform 0.3s cubic-bezier(0.16, 1, 0.3, 1);
      display: inline-block;
    }

    .menu-card:hover .card-arrow {
      transform: translateX(8px);
    }

    @keyframes fadeInLeft {
      from {
        opacity: 0;
        transform: translateX(-30px);
      }
      to {
        opacity: 1;
        transform: translateX(0);
      }
    }

    @keyframes fadeInRight {
      from {
        opacity: 0;
        transform: translateX(30px);
      }
      to {
        opacity: 1;
        transform: translateX(0);
      }
    }

    @media (max-width: 768px) {
      .header-content {
        flex-wrap: nowrap;
        gap: 0.75rem;
        text-align: left;
      }

      .brand {
        flex-direction: column;
        align-items: flex-start;
        gap: 0.25rem;
      }

      .brand h1 {
        font-size: 1.4rem;
      }

      .menu-grid {
        grid-template-columns: 1fr;
      }
    }
  `]
})
export class DashboardComponent implements OnInit {
  currentUser: LoginResponse | null = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
  }

  navigate(path: string): void {
    this.router.navigate([path]);
  }

  translateRole(role?: string): string {
    const roleMap: { [key: string]: string } = {
      'GERENTE': 'Gerente',
      'ESTOQUISTA': 'Estoquista',
      'CAIXA': 'Caixa'
    };
    return role ? roleMap[role] || role : '';
  }

  logout(): void {
    this.authService.logout();
  }
}
