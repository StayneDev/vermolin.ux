import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { NewSaleComponent } from './new-sale/new-sale.component';
import { SaleListComponent } from './sale-list/sale-list.component';

const norm = (s: string) => s.replace(/ /g, ' ').replace(/\s+/g, ' ');

const TOTAL = {
  success: true,
  message: 'Total do dia',
  data: { date: '2026-11-21', count: 3, total: 150.5, byPaymentMethod: { DINHEIRO: 100, CARTAO: 40.5, PIX: 10 } }
};

function setup(role: 'CAIXA' | 'GERENTE') {
  TestBed.configureTestingModule({
    providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])]
  });
  const auth = TestBed.inject(AuthService);
  spyOn(auth, 'getCurrentUser').and.returnValue({ role } as any);
  return TestBed.inject(HttpTestingController);
}

describe('Total de hoje', () => {
  it('nova venda (CAIXA) mostra total, quantidade e formas de pagamento', () => {
    const http = setup('CAIXA');
    const fixture = TestBed.createComponent(NewSaleComponent);
    fixture.detectChanges();
    http.match(r => r.url === '/api/sales' && r.method === 'POST').forEach(r => r.flush({ success: false }));
    http.match('/api/products').forEach(r => r.flush({ success: true, data: [] }));
    http.expectOne('/api/sales/today-total').flush(TOTAL);
    fixture.detectChanges();

    const text = norm(fixture.nativeElement.textContent);
    expect(text).toContain('Total de hoje');
    expect(text).toContain('R$ 150,50');
    expect(text).toContain('Vendas: 3');
    expect(text).toContain('Dinheiro: R$ 100,00');
    expect(text).toContain('Cartão: R$ 40,50');
    expect(text).toContain('PIX: R$ 10,00');
  });

  it('tela Vendas (GERENTE) mostra o total de hoje da loja inteira', () => {
    const http = setup('GERENTE');
    const fixture = TestBed.createComponent(SaleListComponent);
    fixture.detectChanges();
    http.expectOne('/api/sales').flush({ success: true, data: [] });
    http.expectOne('/api/sales/today-total').flush(TOTAL);
    fixture.detectChanges();

    const text = norm(fixture.nativeElement.textContent);
    expect(text).toContain('Total de hoje');
    expect(text).toContain('R$ 150,50');
    expect(text).toContain('Vendas: 3');
    expect(text).toContain('Dinheiro: R$ 100,00');
  });
});
