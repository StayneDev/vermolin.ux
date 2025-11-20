import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { UserRole } from '../../../core/models/api.models';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

interface RoleNavLink {
  label: string;
  route: string;
  icon: string;
  roles?: UserRole[];
}

@Component({
  selector: 'app-role-nav',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <nav class="role-nav" aria-label="Atalhos de navegação">
      <a
        *ngFor="let link of filteredLinks"
        [routerLink]="link.route"
        class="role-nav__link"
      >
        <span class="role-nav__icon" aria-hidden="true">{{ link.icon }}</span>
        <span>{{ link.label }}</span>
      </a>
    </nav>
  `,
  styles: [`
    .role-nav {
      display: flex;
      gap: 8px;
      flex-wrap: wrap;
    }

    .role-nav__link {
      display: inline-flex;
      align-items: center;
      gap: 6px;
      padding: 8px 12px;
      border-radius: 999px;
      background: rgba(76, 175, 80, 0.08);
      color: var(--text-color);
      font-weight: 600;
      text-decoration: none;
      border: 1px solid rgba(76, 175, 80, 0.2);
      transition: var(--transition);
    }

    .role-nav__link:hover {
      transform: translateY(-1px);
      border-color: var(--primary);
    }

    @media (max-width: 768px) {
      .role-nav {
        flex-direction: column;
        gap: 10px;
        width: 100%;
      }

      .role-nav__link {
        width: 100%;
        justify-content: flex-start;
      }
    }
  `]
})
export class RoleNavComponent {
  private readonly links: RoleNavLink[] = [
    { label: 'Dashboard', route: '/dashboard', icon: '🏠' },
    { label: 'Produtos', route: '/products', icon: '📦' },
    { label: 'Nova Venda', route: '/sales/new', icon: '🛒', roles: ['CAIXA'] },
    { label: 'Vendas', route: '/sales', icon: '💰', roles: ['GERENTE'] },
    { label: 'Estoque', route: '/stock', icon: '📊', roles: ['GERENTE', 'ESTOQUISTA'] },
    { label: 'Fornecedores', route: '/suppliers', icon: '🚚', roles: ['GERENTE', 'ESTOQUISTA'] },
    { label: 'Usuários', route: '/users', icon: '👥', roles: ['GERENTE'] }
  ];

  filteredLinks: RoleNavLink[] = [];

  constructor(private readonly authService: AuthService) {
    this.authService.currentUser$
      .pipe(takeUntilDestroyed())
      .subscribe(user => {
      this.filteredLinks = this.links.filter(link => {
        if (!link.roles || link.roles.length === 0) {
          return true;
        }
        return !!user && link.roles.includes(user.role);
      });
    });
  }
}
