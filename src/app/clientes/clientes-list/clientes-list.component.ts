import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { ClienteService } from '../../services/cliente.service';
import { Cliente } from '../../models/cliente.model';

/**
 * ClientesListComponent — Muestra el listado de clientes registrados.
 * Consume el endpoint GET /api/clientes del backend Spring Boot.
 */
@Component({
  selector: 'app-clientes-list',
  standalone: false,
  templateUrl: './clientes-list.component.html',
  styleUrls: ['./clientes-list.component.css']
})
export class ClientesListComponent implements OnInit {

  displayedColumns: string[] = ['idCliente', 'cedula', 'nombre', 'apellido', 'telefono', 'correo'];
  dataSource = new MatTableDataSource<Cliente>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.clienteService.findAll().subscribe({
      next: data => {
        this.dataSource.data = data;
        this.dataSource.paginator = this.paginator;
      },
      error: err => console.error('Error al cargar clientes:', err)
    });
  }
}
