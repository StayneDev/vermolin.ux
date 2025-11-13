import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, StockMovement, StockMovementPayload } from '../models/api.models';

@Injectable({
  providedIn: 'root'
})
export class StockService {
  private readonly API_URL = '/api/stock';

  constructor(private http: HttpClient) {}

  getMovements(): Observable<ApiResponse<StockMovement[]>> {
    return this.http.get<ApiResponse<StockMovement[]>>(`${this.API_URL}/movements`);
  }

  registerEntry(payload: StockMovementPayload): Observable<ApiResponse<StockMovement>> {
    return this.http.post<ApiResponse<StockMovement>>(`${this.API_URL}/entry`, {
      ...payload,
      movementType: 'ENTRADA'
    });
  }

  registerExit(payload: StockMovementPayload): Observable<ApiResponse<StockMovement>> {
    return this.http.post<ApiResponse<StockMovement>>(`${this.API_URL}/exit`, {
      ...payload,
      movementType: 'SAIDA'
    });
  }

  registerAdjustment(payload: StockMovementPayload): Observable<ApiResponse<StockMovement>> {
    return this.http.post<ApiResponse<StockMovement>>(`${this.API_URL}/adjustment`, {
      ...payload,
      movementType: 'AJUSTE'
    });
  }
}
