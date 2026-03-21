import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  CarritoResponse,
  AddItemRequest,
  UpdateItemRequest,
  CheckoutRequest
} from '../models/carrito.model';

/**
 * CarritoService — Servicio HTTP que consume la API REST del carrito de compras.
 *
 * Métodos:
 *  - obtenerOCrearCarrito(idCliente) → POST /api/carrito/{idCliente}
 *  - obtenerCarrito(token)           → GET  /api/carrito/{token}
 *  - agregarItem(token, request)     → POST /api/carrito/{token}/items
 *  - actualizarItem(idItem, req)     → PUT  /api/carrito/items/{idItem}
 *  - eliminarItem(idItem)            → DELETE /api/carrito/items/{idItem}
 *  - vaciarCarrito(token)            → DELETE /api/carrito/{token}/vaciar
 *  - checkout(token, request)        → POST /api/carrito/{token}/checkout
 */
@Injectable({
  providedIn: 'root'
})
export class CarritoService {

  private apiUrl = 'http://localhost:8080/api/carrito';

  constructor(private http: HttpClient) {}

  obtenerOCrearCarrito(idCliente: number): Observable<CarritoResponse> {
    return this.http.post<CarritoResponse>(`${this.apiUrl}/${idCliente}`, {});
  }

  obtenerCarrito(token: string): Observable<CarritoResponse> {
    return this.http.get<CarritoResponse>(`${this.apiUrl}/${token}`);
  }

  agregarItem(token: string, request: AddItemRequest): Observable<CarritoResponse> {
    return this.http.post<CarritoResponse>(`${this.apiUrl}/${token}/items`, request);
  }

  actualizarItem(idCarritoItem: number, request: UpdateItemRequest): Observable<CarritoResponse> {
    return this.http.put<CarritoResponse>(`${this.apiUrl}/items/${idCarritoItem}`, request);
  }

  eliminarItem(idCarritoItem: number): Observable<CarritoResponse> {
    return this.http.delete<CarritoResponse>(`${this.apiUrl}/items/${idCarritoItem}`);
  }

  vaciarCarrito(token: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${token}/vaciar`);
  }

  checkout(token: string, request: CheckoutRequest): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/${token}/checkout`, request);
  }
}
