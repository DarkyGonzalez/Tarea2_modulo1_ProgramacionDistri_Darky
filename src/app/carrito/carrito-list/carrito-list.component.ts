import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CarritoService } from '../../services/carrito.service';
import { CarritoResponse, CarritoItemResponse } from '../../models/carrito.model';

/**
 * CarritoListComponent — Componente del módulo web carrito de compras.
 *
 * Funcionalidades:
 *  - Cargar o crear el carrito del cliente activo (idCliente hardcodeado = 1 para demo)
 *  - Mostrar los ítems en una MatTable con columnas: Libro, Cantidad, P.Unitario, Total
 *  - Permitir actualizar cantidad de un ítem
 *  - Permitir eliminar un ítem
 *  - Vaciar el carrito completo
 *  - Realizar el checkout generando una factura
 */
@Component({
  selector: 'app-carrito-list',
  templateUrl: './carrito-list.component.html',
  styleUrls: ['./carrito-list.component.css']
})
export class CarritoListComponent implements OnInit {

  carrito: CarritoResponse | null = null;
  dataSource = new MatTableDataSource<CarritoItemResponse>([]);
  displayedColumns: string[] = ['idLibro', 'tituloLibro', 'cantidad', 'precioUnitario', 'total', 'acciones'];

  cargando = false;
  mensajeError = '';

  // Para demo: ID del cliente activo
  idClienteActivo = 1;

  constructor(
    private carritoService: CarritoService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.cargarCarrito();
  }

  /** Obtiene o crea el carrito del cliente activo */
  cargarCarrito(): void {
    this.cargando = true;
    this.carritoService.obtenerOCrearCarrito(this.idClienteActivo).subscribe({
      next: (data) => {
        this.carrito = data;
        this.dataSource.data = data.items;
        this.cargando = false;
      },
      error: (err) => {
        this.mensajeError = 'Error al cargar el carrito: ' + err.message;
        this.cargando = false;
      }
    });
  }

  /** Actualiza la cantidad de un ítem */
  actualizarCantidad(item: CarritoItemResponse, nuevaCantidad: number): void {
    if (nuevaCantidad < 1) return;
    this.carritoService.actualizarItem(item.idCarritoItem, { cantidad: nuevaCantidad }).subscribe({
      next: (data) => {
        this.carrito = data;
        this.dataSource.data = data.items;
        this.snackBar.open('Cantidad actualizada', 'Cerrar', { duration: 2000 });
      },
      error: () => this.snackBar.open('Error al actualizar', 'Cerrar', { duration: 2000 })
    });
  }

  /** Elimina un ítem del carrito */
  eliminarItem(idCarritoItem: number): void {
    this.carritoService.eliminarItem(idCarritoItem).subscribe({
      next: (data) => {
        this.carrito = data;
        this.dataSource.data = data.items;
        this.snackBar.open('Ítem eliminado', 'Cerrar', { duration: 2000 });
      },
      error: () => this.snackBar.open('Error al eliminar', 'Cerrar', { duration: 2000 })
    });
  }

  /** Vacía todos los ítems del carrito */
  vaciarCarrito(): void {
    if (!this.carrito) return;
    this.carritoService.vaciarCarrito(this.carrito.token).subscribe({
      next: () => {
        this.cargarCarrito();
        this.snackBar.open('Carrito vaciado', 'Cerrar', { duration: 2000 });
      },
      error: () => this.snackBar.open('Error al vaciar carrito', 'Cerrar', { duration: 2000 })
    });
  }

  /** Realiza el checkout y genera la factura */
  realizarCheckout(): void {
    if (!this.carrito || this.carrito.items.length === 0) {
      this.snackBar.open('El carrito está vacío', 'Cerrar', { duration: 2000 });
      return;
    }

    const request = {
      idCliente: this.idClienteActivo,
      metodoPago: 'EFECTIVO'
    };

    this.carritoService.checkout(this.carrito.token, request).subscribe({
      next: (factura) => {
        this.snackBar.open(
          `¡Compra exitosa! Factura: ${factura.numFactura}  Total: $${factura.total}`,
          'Cerrar',
          { duration: 5000 }
        );
        this.cargarCarrito();
      },
      error: () => this.snackBar.open('Error al procesar el checkout', 'Cerrar', { duration: 3000 })
    });
  }
}
