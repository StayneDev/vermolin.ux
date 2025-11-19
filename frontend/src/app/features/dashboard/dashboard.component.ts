import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { LoginResponse } from '../../core/models/api.models';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="dashboard">
      <header class="header">
        <div class="container header-content">
          <div class="brand">
            <img src="assets/logo-vermole.png" alt="Vermolin.UX" class="header-logo">
            <h1>Vermolin.UX</h1>
          </div>
          <div class="user-info">
            <div class="user-badge">
              <span class="user-name">{{ currentUser?.fullName }}</span>
              <span class="user-role">{{ translateRole(currentUser?.role) }}</span>
            </div>
            <button class="btn btn-secondary" (click)="logout()">
              <span class="btn-icon">🚪</span> Sair
            </button>
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
      background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
    }

    .header {
      background: linear-gradient(135deg, #1B5E20 0%, #2E7D32 50%, #4CAF50 100%);
      color: white;
      padding: 1.5rem 0;
      box-shadow: var(--shadow-md);
      position: relative;
      overflow: hidden;
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
      position: relative;
      z-index: 1;
    }

    .brand {
      display: flex;
      align-items: center;
      gap: 1rem;
      animation: fadeInLeft 0.6s cubic-bezier(0.16, 1, 0.3, 1);
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
    }

    .user-badge {
      display: flex;
      flex-direction: column;
      align-items: flex-end;
      padding: 0.5rem 1rem;
      background: rgba(255, 255, 255, 0.1);
      border-radius: var(--radius-md);
      backdrop-filter: blur(10px);
      border: 1px solid rgba(255, 255, 255, 0.2);
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
      background: white;
      border-radius: var(--radius-lg);
      box-shadow: var(--shadow-sm);
    }

    .welcome-card h2 {
      color: var(--primary);
      margin-bottom: 0.5rem;
      font-weight: 700;
      font-size: 1.5rem;
    }

    .welcome-card p {
      color: var(--gray-600);
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
      border: 2px solid var(--gray-200);
      position: relative;
      overflow: hidden;
      background: white;
      padding: 2rem 1.5rem;
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
      color: var(--gray-800);
      margin-bottom: 0.5rem;
      font-weight: 700;
      font-size: 1.25rem;
      transition: color 0.3s;
    }

    .menu-card:hover h3 {
      color: var(--primary);
    }

    .menu-card p {
      color: var(--gray-600);
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
        flex-direction: column;
        gap: 1rem;
        text-align: center;
      }

      .brand {
        flex-direction: column;
        gap: 0.5rem;
      }

      .user-badge {
        align-items: center;
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
