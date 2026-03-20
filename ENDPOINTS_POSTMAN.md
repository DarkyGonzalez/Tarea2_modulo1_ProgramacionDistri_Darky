# Endpoints REST API - Librería Spring Módulo 2

## Base URL
```
http://localhost:8080
```

## 1. Autor Controller (`/api/autores`)

### GET - Obtener todos los autores
```
GET http://localhost:8080/api/autores
```

### GET - Obtener autor por ID
```
GET http://localhost:8080/api/autores/{id}
```
Ejemplo: `GET http://localhost:8080/api/autores/1`

### POST - Crear nuevo autor
```
POST http://localhost:8080/api/autores
Content-Type: application/json

{
  "nombre": "Gabriel",
  "apellido": "García Márquez",
  "pais": "Colombia",
  "direccion": "Aracataca",
  "telefono": "123456789",
  "correo": "gabriel@example.com"
}
```

### PUT - Actualizar autor
```
PUT http://localhost:8080/api/autores/{id}
Content-Type: application/json

{
  "nombre": "Gabriel",
  "apellido": "García Márquez",
  "pais": "Colombia",
  "direccion": "Aracataca",
  "telefono": "987654321",
  "correo": "gabriel@example.com"
}
```

### DELETE - Eliminar autor
```
DELETE http://localhost:8080/api/autores/{id}
```

---

## 2. Categoria Controller (`/api/categorias`)

### GET - Obtener todas las categorías
```
GET http://localhost:8080/api/categorias
```

### GET - Obtener categoría por ID
```
GET http://localhost:8080/api/categorias/{id}
```

### POST - Crear nueva categoría
```
POST http://localhost:8080/api/categorias
Content-Type: application/json

{
  "categoria": "Ficción",
  "descripcion": "Libros de ficción y novelas"
}
```

### PUT - Actualizar categoría
```
PUT http://localhost:8080/api/categorias/{id}
Content-Type: application/json

{
  "categoria": "Ficción Literaria",
  "descripcion": "Libros de ficción y novelas literarias"
}
```

### DELETE - Eliminar categoría
```
DELETE http://localhost:8080/api/categorias/{id}
```

---

## 3. Libro Controller (`/api/libros`)

### GET - Obtener todos los libros
```
GET http://localhost:8080/api/libros
```

### GET - Obtener libro por ID
```
GET http://localhost:8080/api/libros/{id}
```

### POST - Crear nuevo libro
```
POST http://localhost:8080/api/libros
Content-Type: application/json

{
  "titulo": "Cien años de soledad",
  "editorial": "Editorial Sudamericana",
  "numPagina": 471,
  "edicion": "Primera",
  "idioma": "Español",
  "fechaPublicacion": "1967-05-30",
  "descripcion": "Novela del realismo mágico",
  "tipoPasta": "Tapa dura",
  "isbn": "978-84-376-0494-7",
  "numEjemplares": 100,
  "portada": "portada.jpg",
  "presentacion": "Físico",
  "precio": 25.99,
  "categoria": {
    "idCategoria": 1
  },
  "autor": {
    "idAutor": 1
  }
}
```

### PUT - Actualizar libro
```
PUT http://localhost:8080/api/libros/{id}
Content-Type: application/json

{
  "titulo": "Cien años de soledad",
  "editorial": "Editorial Sudamericana",
  "numPagina": 471,
  "edicion": "Segunda",
  "idioma": "Español",
  "fechaPublicacion": "1967-05-30",
  "descripcion": "Novela del realismo mágico",
  "tipoPasta": "Tapa blanda",
  "isbn": "978-84-376-0494-7",
  "numEjemplares": 150,
  "portada": "portada.jpg",
  "presentacion": "Físico",
  "precio": 20.99,
  "categoria": {
    "idCategoria": 1
  },
  "autor": {
    "idAutor": 1
  }
}
```

### DELETE - Eliminar libro
```
DELETE http://localhost:8080/api/libros/{id}
```

---

## 4. Factura Controller (`/api/facturas`)

### GET - Obtener todas las facturas
```
GET http://localhost:8080/api/facturas
```

### GET - Obtener factura por ID
```
GET http://localhost:8080/api/facturas/{id}
```

### POST - Crear nueva factura
```
POST http://localhost:8080/api/facturas
Content-Type: application/json

{
  "numFactura": "FAC-001",
  "fecha": "2026-02-18",
  "totalNeto": 100.00,
  "iva": 19.00,
  "total": 119.00,
  "cliente": {
    "idCliente": 1
  }
}
```

### PUT - Actualizar factura
```
PUT http://localhost:8080/api/facturas/{id}
Content-Type: application/json

{
  "numFactura": "FAC-001",
  "fecha": "2026-02-18",
  "totalNeto": 150.00,
  "iva": 28.50,
  "total": 178.50,
  "cliente": {
    "idCliente": 1
  }
}
```

### DELETE - Eliminar factura
```
DELETE http://localhost:8080/api/facturas/{id}
```

---

## 5. FacturaDetalle Controller (`/api/factura-detalles`)

### GET - Obtener todos los detalles de factura
```
GET http://localhost:8080/api/factura-detalles
```

### GET - Obtener detalle de factura por ID
```
GET http://localhost:8080/api/factura-detalles/{id}
```

### POST - Crear nuevo detalle de factura
```
POST http://localhost:8080/api/factura-detalles
Content-Type: application/json

{
  "cantidad": 2,
  "subtotal": 51.98,
  "factura": {
    "idFactura": 1
  },
  "libro": {
    "idLibro": 1
  }
}
```

### PUT - Actualizar detalle de factura
```
PUT http://localhost:8080/api/factura-detalles/{id}
Content-Type: application/json

{
  "cantidad": 3,
  "subtotal": 77.97,
  "factura": {
    "idFactura": 1
  },
  "libro": {
    "idLibro": 1
  }
}
```

### DELETE - Eliminar detalle de factura
```
DELETE http://localhost:8080/api/factura-detalles/{id}
```

---

## 6. Cliente Controller (`/api/clientes`) - Ya existente

### GET - Obtener todos los clientes
```
GET http://localhost:8080/api/clientes
```

### GET - Obtener cliente por ID
```
GET http://localhost:8080/api/clientes/{id}
```

### POST - Crear nuevo cliente
```
POST http://localhost:8080/api/clientes
Content-Type: application/json

{
  "cedula": "1234567890",
  "nombre": "Juan",
  "apellido": "Pérez",
  "direccion": "Calle 123",
  "telefono": "3001234567",
  "correo": "juan@example.com"
}
```

### PUT - Actualizar cliente
```
PUT http://localhost:8080/api/clientes/{id}
Content-Type: application/json

{
  "cedula": "1234567890",
  "nombre": "Juan",
  "apellido": "Pérez",
  "direccion": "Calle 456",
  "telefono": "3001234567",
  "correo": "juan.perez@example.com"
}
```

### DELETE - Eliminar cliente
```
DELETE http://localhost:8080/api/clientes/{id}
```

---

## Notas importantes:

1. **Fechas**: El formato de fecha debe ser `YYYY-MM-DD` para campos de tipo Date
2. **Relaciones**: Al crear/actualizar entidades con relaciones (Libro, Factura, FacturaDetalle), solo necesitas enviar el ID de la entidad relacionada
3. **IDs**: Los IDs se generan automáticamente, no es necesario enviarlos en POST
4. **Respuestas**:
   - **200 OK**: Operación exitosa
   - **201 Created**: Recurso creado (aunque en este caso se usa 200)
   - **204 No Content**: Eliminación exitosa
   - **404 Not Found**: Recurso no encontrado

## Orden sugerido para pruebas:

1. Primero crear **Autor** y **Categoria**
2. Luego crear **Libro** (requiere Autor y Categoria)
3. Crear **Cliente**
4. Crear **Factura** (requiere Cliente)
5. Finalmente crear **FacturaDetalle** (requiere Factura y Libro)

