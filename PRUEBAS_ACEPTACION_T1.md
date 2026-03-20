# Pruebas de Aceptación – Panel de Navegación
## PRÁCTICA 6 – Calidad de Software – Módulo 3

### Prueba 1: Carga correcta del Panel de Navegación

**Criterio de aceptación:** Al iniciar la aplicación, el panel lateral debe 
desplegarse mostrando el menú de navegación con el módulo Libros.

**Pasos:**
1. Ejecutar `ng serve` en el directorio del proyecto Angular
2. Abrir el navegador en `http://localhost:4200`
3. Verificar que aparece el panel lateral (MatSidenav) con el ítem "Libros"
4. Verificar que el ícono `menu_book` y el texto "Libros" son visibles

**Resultado esperado:** APROBADO – El sidenav carga correctamente con el ítem
de navegación configurado en app-routing-module.ts.

---

### Prueba 2: Navegación entre rutas

**Criterio de aceptación:** Al hacer clic en el ítem "Libros" del menú, la 
aplicación debe cargar el componente LibrosListComponent mostrando la tabla.

**Pasos:**
1. Con la aplicación en ejecución, hacer clic en "Libros" del menú lateral
2. Verificar que la URL cambia a `/libros`
3. Verificar que se muestra la tabla del catálogo de libros (MatTable)
4. Verificar que se carga el paginador (MatPaginator)

**Resultado esperado:** APROBADO – El router-outlet renderiza correctamente 
LibrosListComponent y la tabla se muestra con paginación.

---

### Prueba 3: Comportamiento responsivo (toggle del sidenav)

**Criterio de aceptación:** El botón de menú (hamburger) debe ocultar y mostrar 
el panel lateral sin afectar el contenido principal.

**Pasos:**
1. Con la aplicación activa, hacer clic en el botón de menú (ícono `menu`)
2. Verificar que el panel lateral se colapsa
3. Hacer clic nuevamente en el botón
4. Verificar que el panel lateral se muestra de nuevo

**Resultado esperado:** APROBADO – El método `drawer.toggle()` en app.ts
funciona correctamente a través de la directiva `(click)="drawer.toggle()"`.
