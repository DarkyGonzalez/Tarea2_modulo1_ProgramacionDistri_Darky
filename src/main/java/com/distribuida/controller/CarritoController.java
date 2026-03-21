package com.distribuida.controller;

import com.distribuida.dto.AddItemRequest;
import com.distribuida.dto.CarritoItemResponseDTO;
import com.distribuida.dto.CarritoResponseDTO;
import com.distribuida.dto.CheckoutRequest;
import com.distribuida.dto.UpdateItemRequest;
import com.distribuida.model.Carrito;
import com.distribuida.model.CarritoItem;
import com.distribuida.model.Factura;
import com.distribuida.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CarritoController — expone los endpoints REST del módulo carrito de compras.
 *
 * Endpoints disponibles:
 *  POST   /api/carrito/{idCliente}             → Obtener o crear carrito
 *  GET    /api/carrito/{token}                 → Consultar carrito por token
 *  POST   /api/carrito/{token}/items           → Agregar ítem
 *  PUT    /api/carrito/items/{idCarritoItem}   → Actualizar cantidad
 *  DELETE /api/carrito/items/{idCarritoItem}   → Eliminar ítem
 *  DELETE /api/carrito/{token}/vaciar          → Vaciar carrito
 *  POST   /api/carrito/{token}/checkout        → Realizar checkout
 */
@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // ── POST /api/carrito/{idCliente} ─────────────────────────────────────────
    @PostMapping("/{idCliente}")
    public ResponseEntity<CarritoResponseDTO> obtenerOCrear(@PathVariable int idCliente) {
        try {
            Carrito carrito = carritoService.obtenerOCrearCarrito(idCliente);
            return ResponseEntity.ok(toDTO(carrito));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ── GET /api/carrito/{token} ──────────────────────────────────────────────
    @GetMapping("/{token}")
    public ResponseEntity<CarritoResponseDTO> obtenerCarrito(@PathVariable String token) {
        Optional<Carrito> carritoOpt = carritoService.obtenerCarrito(token);
        return carritoOpt
                .map(c -> ResponseEntity.ok(toDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ── POST /api/carrito/{token}/items ───────────────────────────────────────
    @PostMapping("/{token}/items")
    public ResponseEntity<CarritoResponseDTO> agregarItem(
            @PathVariable String token,
            @RequestBody AddItemRequest request) {
        try {
            Carrito carrito = carritoService.agregarItem(token, request);
            return ResponseEntity.ok(toDTO(carrito));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ── PUT /api/carrito/items/{idCarritoItem} ────────────────────────────────
    @PutMapping("/items/{idCarritoItem}")
    public ResponseEntity<CarritoResponseDTO> actualizarItem(
            @PathVariable Long idCarritoItem,
            @RequestBody UpdateItemRequest request) {
        try {
            Carrito carrito = carritoService.actualizarItem(idCarritoItem, request);
            return ResponseEntity.ok(toDTO(carrito));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ── DELETE /api/carrito/items/{idCarritoItem} ─────────────────────────────
    @DeleteMapping("/items/{idCarritoItem}")
    public ResponseEntity<CarritoResponseDTO> eliminarItem(@PathVariable Long idCarritoItem) {
        try {
            Carrito carrito = carritoService.eliminarItem(idCarritoItem);
            return ResponseEntity.ok(toDTO(carrito));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ── DELETE /api/carrito/{token}/vaciar ────────────────────────────────────
    @DeleteMapping("/{token}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable String token) {
        try {
            carritoService.vaciarCarrito(token);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ── POST /api/carrito/{token}/checkout ────────────────────────────────────
    @PostMapping("/{token}/checkout")
    public ResponseEntity<Factura> checkout(
            @PathVariable String token,
            @RequestBody CheckoutRequest request) {
        try {
            Factura factura = carritoService.checkout(token, request);
            return ResponseEntity.ok(factura);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ── Mapper interno: Carrito → CarritoResponseDTO ──────────────────────────
    private CarritoResponseDTO toDTO(Carrito carrito) {
        CarritoResponseDTO dto = new CarritoResponseDTO();
        dto.setIdCarrito(carrito.getIdCarrito());
        dto.setToken(carrito.getToken());
        dto.setNombreCliente(
                carrito.getCliente() != null
                        ? carrito.getCliente().getNombre() + " " + carrito.getCliente().getApellido()
                        : "");
        dto.setSubtotal(carrito.getSubtotal());
        dto.setDescuento(carrito.getDescuento());
        dto.setImpuestos(carrito.getImpuestos());
        dto.setTotal(carrito.getTotal());
        dto.setActualizadoEn(carrito.getActualizadoEn());

        List<CarritoItemResponseDTO> itemsDTO = carrito.getItems().stream()
                .map(this::itemToDTO)
                .collect(Collectors.toList());
        dto.setItems(itemsDTO);

        return dto;
    }

    private CarritoItemResponseDTO itemToDTO(CarritoItem item) {
        CarritoItemResponseDTO dto = new CarritoItemResponseDTO();
        dto.setIdCarritoItem(item.getIdCarritoItem());
        dto.setIdLibro(item.getIdLibro());
        dto.setTituloLibro("Libro #" + item.getIdLibro());
        dto.setCantidad(item.getCantidad());
        dto.setPrecioUnitario(item.getPrecioUnitario());
        dto.setTotal(item.getTotal());
        return dto;
    }
}
