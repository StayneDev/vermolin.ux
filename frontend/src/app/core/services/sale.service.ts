import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, PaymentMethod, Sale } from '../models/api.models';

@Injectable({
  providedIn: 'root'
})
export class SaleService {
  private readonly API_URL = '/api/sales';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ApiResponse<Sale[]>> {
    return this.http.get<ApiResponse<Sale[]>>(this.API_URL);
  }

  getById(id: number): Observable<ApiResponse<Sale>> {
    return this.http.get<ApiResponse<Sale>>(`${this.API_URL}/${id}`);
  }

  create(): Observable<ApiResponse<Sale>> {
    return this.http.post<ApiResponse<Sale>>(this.API_URL, {});
  }

  addItem(saleId: number, productId: number, quantity: number, weighed = false): Observable<ApiResponse<Sale>> {
    return this.http.post<ApiResponse<Sale>>(`${this.API_URL}/${saleId}/items`, {
      productId,
      quantity,
      weighed
    });
  }

  removeItem(saleId: number, itemId: number): Observable<ApiResponse<Sale>> {
    return this.http.delete<ApiResponse<Sale>>(`${this.API_URL}/${saleId}/items/${itemId}`);
  }

  finalize(saleId: number, paymentMethod: PaymentMethod, amountPaid: number): Observable<ApiResponse<Sale>> {
    return this.http.post<ApiResponse<Sale>>(`${this.API_URL}/${saleId}/finalize`, {
      paymentMethod,
      amountPaid
    });
  }

  cancel(saleId: number, reason?: string): Observable<ApiResponse<void>> {
    const url = reason
      ? `${this.API_URL}/${saleId}/cancel?reason=${encodeURIComponent(reason)}`
      : `${this.API_URL}/${saleId}/cancel`;
    return this.http.post<ApiResponse<void>>(url, null);
  }
}
