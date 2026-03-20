import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { LibroService } from '../../services/libro.service';
import { Libro } from '../../models/libro.model';

@Component({
  selector: 'app-libros-list',
  templateUrl: './libros-list.component.html',
  styleUrls: ['./libros-list.component.css'],
  standalone: false
})
export class LibrosListComponent implements OnInit {

  displayedColumns: string[] = ['idLibro', 'titulo', 'descripcion', 'precio', 'stock', 'acciones'];
  dataSource = new MatTableDataSource<Libro>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private libroService: LibroService) {}

  ngOnInit(): void {
    this.libroService.getAll().subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.dataSource.paginator = this.paginator;
      },
      error: (err) => {
        console.error('Error al cargar libros:', err);
      }
    });
  }

  agregarAlCarrito(libro: Libro): void {
    console.log('Libro agregado al carrito:', libro.titulo);
    // TODO: Integrar con CarritoService (Módulo 3 - Tarea 2)
  }
}
