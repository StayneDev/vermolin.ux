import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, Supplier, SupplierPayload } from '../models/api.models';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {
  private readonly API_URL = '/api/suppliers';

  constructor(private http: HttpClient) {}

  getAll(activeOnly = false): Observable<ApiResponse<Supplier[]>> {
    const params = activeOnly ? '?activeOnly=true' : '';
    return this.http.get<ApiResponse<Supplier[]>>(`${this.API_URL}${params}`);
  }

  getById(id: number): Observable<ApiResponse<Supplier>> {
    return this.http.get<ApiResponse<Supplier>>(`${this.API_URL}/${id}`);
  }

  create(payload: SupplierPayload): Observable<ApiResponse<Supplier>> {
    return this.http.post<ApiResponse<Supplier>>(this.API_URL, payload);
  }

  update(id: number, payload: SupplierPayload): Observable<ApiResponse<Supplier>> {
    return this.http.put<ApiResponse<Supplier>>(`${this.API_URL}/${id}`, payload);
  }

  delete(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.API_URL}/${id}`);
  }

  reactivate(id: number): Observable<ApiResponse<Supplier>> {
    return this.http.patch<ApiResponse<Supplier>>(`${this.API_URL}/${id}/reactivate`, {});
  }
}
