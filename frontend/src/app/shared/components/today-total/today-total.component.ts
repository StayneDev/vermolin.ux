import { Component, OnInit } from '@angular/core';
import { CommonModule, registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';
import { DailyTotal } from '../../../core/models/api.models';
import { SaleService } from '../../../core/services/sale.service';

registerLocaleData(localePt, 'pt-BR');

@Component({
  selector: 'app-today-total',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card today-total">
      <h3>Total de hoje</h3>
      <p class="error" *ngIf="error">{{ error }}</p>
      <ng-container *ngIf="data">
        <p>Total: <strong class="tt-total">{{ data.total | currency:'BRL':'symbol':'1.2-2':'pt-BR' }}</strong></p>
        <p>Vendas: <strong class="tt-count">{{ data.count }}</strong></p>
        <ul>
          <li *ngFor="let m of methods" class="tt-method">
            {{ m.label }}: <strong>{{ (data.byPaymentMethod[m.key] || 0) | currency:'BRL':'symbol':'1.2-2':'pt-BR' }}</strong>
          </li>
        </ul>
      </ng-container>
    </div>
  `,
  styles: [`
    .today-total { margin-bottom: 20px; }
    .today-total ul { list-style: none; padding: 0; margin: 0; }
  `]
})
export class TodayTotalComponent implements OnInit {
  data: DailyTotal | null = null;
  error = '';
  methods = [
    { key: 'DINHEIRO', label: 'Dinheiro' },
    { key: 'CARTAO', label: 'Cartão' },
    { key: 'PIX', label: 'PIX' }
  ];

  constructor(private saleService: SaleService) {}

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.saleService.getTodayTotal().subscribe({
      next: r => {
        if (r.success && r.data) {
          this.data = r.data;
          this.error = '';
        } else {
          this.error = r.message || 'Erro ao carregar o total de hoje';
        }
      },
      error: () => { this.error = 'Erro ao carregar o total de hoje'; }
    });
  }
}
