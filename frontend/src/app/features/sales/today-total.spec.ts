import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { NewSaleComponent } from './new-sale/new-sale.component';
import { SaleListComponent } from './sale-list/sale-list.component';

const TODAY = {
  success: true,
  message: 'Total do dia',
  data: {
    quantity: 3,
    total: 150.5,
    totalByPaymentMethod: { DINHEIRO: 50, CARTAO: 70.5, PIX: 30 }
  }
};

function setup(role: string, component: any) {
  localStorage.setItem('currentUser', JSON.stringify({ token: 't', role, name: 'x' }));
  localStorage.setItem('token', 't');
  TestBed.configureTestingModule({
    imports: [component, HttpClientTestingModule],
    providers: [provideRouter([])]
  });
  const http = TestBed.inject(HttpTestingController);
  const fixture = TestBed.createComponent(component);
  fixture.detectChanges();
  http.match(r => r.url === '/api/sales/today-total').forEach(r => r.flush(TODAY));
  http.match(() => true).forEach(r => r.flush({
    success: true,
    data: r.request.method === 'POST' ? { id: 1, status: 'OPEN', totalAmount: 0, items: [] } : []
  }));
  fixture.detectChanges();
  return fixture.nativeElement as HTMLElement;
}

describe('Total de hoje', () => {
  afterEach(() => localStorage.clear());

  it('nova venda (CAIXA) mostra total, quantidade e formas de pagamento', () => {
    const el = setup('CAIXA', NewSaleComponent);
    const text = el.querySelector('.today-total')?.textContent || '';
    expect(text).toContain('Total de hoje');
    expect(text).toContain('150.50');
    expect(text).toContain('3');
    expect(text).toContain('50.00');
    expect(text).toContain('70.50');
    expect(text).toContain('30.00');
  });

  it('vendas (GERENTE) mostra o total de hoje da loja', () => {
    const el = setup('GERENTE', SaleListComponent);
    const text = el.querySelector('.today-total')?.textContent || '';
    expect(text).toContain('Total de hoje');
    expect(text).toContain('150.50');
    expect(text).toContain('3');
  });
});
