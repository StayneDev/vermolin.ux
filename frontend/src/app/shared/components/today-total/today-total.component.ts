import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SaleService } from '../../../core/services/sale.service';
import { DailyTotal } from '../../../core/models/api.models';

@Component({
  selector: 'app-today-total',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card today-total" *ngIf="data">
      <h3>Total de hoje</h3>
      <p>
        <strong class="today-total-amount">{{ data.total | currency:'BRL' }}</strong>
        • <span class="today-total-qty">{{ data.quantity }}</span> venda(s)
      </p>
      <p class="today-total-methods">
        Dinheiro: <strong>{{ data.totalByPaymentMethod.DINHEIRO | currency:'BRL' }}</strong> •
        Cartão: <strong>{{ data.totalByPaymentMethod.CARTAO | currency:'BRL' }}</strong> •
        PIX: <strong>{{ data.totalByPaymentMethod.PIX | currency:'BRL' }}</strong>
      </p>
    </div>
  `
})
export class TodayTotalComponent implements OnInit {
  data: DailyTotal | null = null;

  constructor(private saleService: SaleService) {}

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.saleService.getTodayTotal().subscribe({
      next: response => {
        if (response.success && response.data) {
          this.data = response.data;
        }
      },
      error: () => {}
    });
  }
}
