import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent),
    canActivate: [authGuard]
  },
  {
    path: 'products',
    loadComponent: () => import('./features/products/product-list/product-list.component').then(m => m.ProductListComponent),
    canActivate: [authGuard]
  },
  {
    path: 'sales',
    loadComponent: () => import('./features/sales/sale-list/sale-list.component').then(m => m.SaleListComponent),
    canActivate: [authGuard]
  },
  {
    path: 'sales/new',
    loadComponent: () => import('./features/sales/new-sale/new-sale.component').then(m => m.NewSaleComponent),
    canActivate: [authGuard]
  },
  {
    path: 'stock',
    loadComponent: () => import('./features/stock/stock.component').then(m => m.StockComponent),
    canActivate: [authGuard]
  },
  {
    path: 'suppliers',
    loadComponent: () => import('./features/suppliers/supplier-list.component').then(m => m.SupplierListComponent),
    canActivate: [authGuard]
  },
  {
    path: 'users',
    loadComponent: () => import('./features/users/user-list.component').then(m => m.UserListComponent),
    canActivate: [authGuard]
  },
  {
    path: 'profile',
    loadComponent: () => import('./features/profile/profile.component').then(m => m.ProfileComponent),
    canActivate: [authGuard]
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  }
];
