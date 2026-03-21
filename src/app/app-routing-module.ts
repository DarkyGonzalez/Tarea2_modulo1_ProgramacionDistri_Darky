import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LibrosListComponent } from './libros/libros-list/libros-list.component';
import { CarritoListComponent } from './carrito/carrito-list/carrito-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'libros', pathMatch: 'full' },
  { path: 'libros', component: LibrosListComponent },
  { path: 'carrito', component: CarritoListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
