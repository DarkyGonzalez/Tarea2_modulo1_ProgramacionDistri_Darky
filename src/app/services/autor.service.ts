import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Autor } from '../models/autor.model';

@Injectable({ providedIn: 'root' })
export class AutorService {
  private apiUrl = 'http://localhost:8080/api/autores';
  constructor(private http: HttpClient) {}
  findAll(): Observable<Autor[]> { return this.http.get<Autor[]>(this.apiUrl); }
}
