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
        <div class="container">
          <h1>🥬 Vermolin.UX</h1>
          <div class="user-info">
            <span>{{ currentUser?.fullName }} ({{ currentUser?.role }})</span>
            <button class="btn btn-secondary" (click)="logout()">Sair</button>
          </div>
        </div>
      </header>

      <div class="container">
        <div class="welcome-card card">
          <h2>Bem-vindo ao Sistema Vermolin.UX!</h2>
          <p>Selecione uma opção abaixo:</p>
        </div>

        <div class="menu-grid">
          <div class="menu-card card" (click)="navigate('/products')">
            <div class="menu-icon">📦</div>
            <h3>Produtos</h3>
            <p>Gerenciar produtos do estoque</p>
          </div>

          <div class="menu-card card" (click)="navigate('/sales/new')" *ngIf="currentUser?.role === 'CAIXA'">
            <div class="menu-icon">🛒</div>
            <h3>Nova Venda</h3>
            <p>Realizar venda (PDV)</p>
          </div>

          <div class="menu-card card" (click)="navigate('/sales')" *ngIf="currentUser?.role === 'GERENTE'">
            <div class="menu-icon">💰</div>
            <h3>Vendas</h3>
            <p>Consultar histórico</p>
          </div>

          <div class="menu-card card" (click)="navigate('/stock')" *ngIf="currentUser?.role === 'GERENTE' || currentUser?.role === 'ESTOQUISTA'">
            <div class="menu-icon">📊</div>
            <h3>Estoque</h3>
            <p>Controlar movimentações</p>
          </div>

          <div class="menu-card card" (click)="navigate('/suppliers')" *ngIf="currentUser?.role === 'GERENTE' || currentUser?.role === 'ESTOQUISTA'">
            <div class="menu-icon">🚚</div>
            <h3>Fornecedores</h3>
            <p>Cadastro de fornecedores</p>
          </div>

          <div class="menu-card card" (click)="navigate('/users')" *ngIf="currentUser?.role === 'GERENTE'">
            <div class="menu-icon">👥</div>
            <h3>Usuários</h3>
            <p>Gerenciar usuários do sistema</p>
          </div>
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

    .user-info {
      display: flex;
      align-items: center;
      gap: 15px;
    }

    .welcome-card {
      text-align: center;
      margin-bottom: 30px;
    }

    .welcome-card h2 {
      color: #4CAF50;
      margin-bottom: 10px;
    }

    .menu-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
      gap: 20px;
      margin-bottom: 40px;
    }

    .menu-card {
      text-align: center;
      cursor: pointer;
      transition: all 0.3s;
      border: 2px solid transparent;
    }

    .menu-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 8px rgba(0,0,0,0.15);
      border-color: #4CAF50;
    }

    .menu-icon {
      font-size: 3rem;
      margin-bottom: 15px;
    }

    .menu-card h3 {
      color: #333;
      margin-bottom: 10px;
    }

    .menu-card p {
      color: #666;
      font-size: 0.9rem;
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

  logout(): void {
    this.authService.logout();
  }
}
