import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { AutorService } from '../../services/autor.service';
import { Autor } from '../../models/autor.model';

/**
 * AutoresListComponent — Muestra el catálogo de autores del sistema.
 * Consume el endpoint GET /api/autores del backend Spring Boot.
 */
@Component({
  selector: 'app-autores-list',
  standalone: false,
  templateUrl: './autores-list.component.html',
  styleUrls: ['./autores-list.component.css']
})
export class AutoresListComponent implements OnInit {

  displayedColumns: string[] = ['idAutor', 'nombre', 'apellido', 'pais', 'correo'];
  dataSource = new MatTableDataSource<Autor>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private autorService: AutorService) {}

  ngOnInit(): void {
    this.autorService.findAll().subscribe({
      next: data => {
        this.dataSource.data = data;
        this.dataSource.paginator = this.paginator;
      },
      error: err => console.error('Error al cargar autores:', err)
    });
  }
}
