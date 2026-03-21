import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LibrosListComponent } from './libros/libros-list/libros-list.component';
import { CarritoListComponent } from './carrito/carrito-list/carrito-list.component';
import { CategoriasListComponent } from './categorias/categorias-list/categorias-list.component';
import { AutoresListComponent } from './autores/autores-list/autores-list.component';
import { ClientesListComponent } from './clientes/clientes-list/clientes-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'libros', pathMatch: 'full' },
  { path: 'libros',     component: LibrosListComponent },
  { path: 'categorias', component: CategoriasListComponent },
  { path: 'autores',    component: AutoresListComponent },
  { path: 'clientes',   component: ClientesListComponent },
  { path: 'carrito',    component: CarritoListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
