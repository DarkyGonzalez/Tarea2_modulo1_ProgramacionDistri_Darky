import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { CategoriaService } from '../../services/categoria.service';
import { Categoria } from '../../models/categoria.model';

/**
 * CategoriasListComponent — Muestra el catálogo de categorías de libros.
 * Consume el endpoint GET /api/categorias del backend Spring Boot.
 */
@Component({
  selector: 'app-categorias-list',
  standalone: false,
  templateUrl: './categorias-list.component.html',
  styleUrls: ['./categorias-list.component.css']
})
export class CategoriasListComponent implements OnInit {

  displayedColumns: string[] = ['idCategoria', 'categoria', 'descripcion'];
  dataSource = new MatTableDataSource<Categoria>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.categoriaService.findAll().subscribe({
      next: data => {
        this.dataSource.data = data;
        this.dataSource.paginator = this.paginator;
      },
      error: err => console.error('Error al cargar categorías:', err)
    });
  }
}
