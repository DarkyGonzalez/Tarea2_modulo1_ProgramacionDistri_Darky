import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Libro } from '../models/libro.model';

@Injectable({
  providedIn: 'root'
})
export class LibroService {

  private apiUrl = 'http://localhost:8080/libros';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Libro[]> {
    return this.http.get<Libro[]>(`${this.apiUrl}/findAll`);
  }

  getById(id: number): Observable<Libro> {
    return this.http.get<Libro>(`${this.apiUrl}/findById/${id}`);
  }
}
