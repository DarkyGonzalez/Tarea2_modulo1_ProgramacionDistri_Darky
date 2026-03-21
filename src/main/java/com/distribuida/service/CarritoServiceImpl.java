package com.distribuida.service;

import com.distribuida.dao.CarritoItemRepository;
import com.distribuida.dao.CarritoRepository;
import com.distribuida.dao.ClienteRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.dto.AddItemRequest;
import com.distribuida.dto.CheckoutRequest;
import com.distribuida.dto.UpdateItemRequest;
import com.distribuida.mapper.CheckoutMapper;
import com.distribuida.model.Carrito;
import com.distribuida.model.CarritoItem;
import com.distribuida.model.Cliente;
import com.distribuida.model.Factura;
import com.distribuida.model.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CarritoServiceImpl implements CarritoService {

    private static final BigDecimal TASA_IVA = new BigDecimal("0.15");

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private CheckoutMapper checkoutMapper;

    // ── Obtener o crear carrito ────────────────────────────────────────────────
    @Override
    public Carrito obtenerOCrearCarrito(int idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + idCliente));

        return carritoRepository.findByCliente(cliente)
                .orElseGet(() -> {
                    Carrito nuevo = new Carrito();
                    nuevo.setCliente(cliente);
                    nuevo.setToken(UUID.randomUUID().toString());
                    nuevo.setSubtotal(BigDecimal.ZERO);
                    nuevo.setDescuento(BigDecimal.ZERO);
                    nuevo.setImpuestos(BigDecimal.ZERO);
                    nuevo.setTotal(BigDecimal.ZERO);
                    nuevo.setActualizadoEn(LocalDateTime.now());
                    return carritoRepository.save(nuevo);
                });
    }

    // ── Obtener carrito por token ──────────────────────────────────────────────
    @Override
    public Optional<Carrito> obtenerCarrito(String token) {
        return carritoRepository.findByToken(token);
    }

    // ── Agregar ítem al carrito ────────────────────────────────────────────────
    @Override
    public Carrito agregarItem(String token, AddItemRequest request) {
        Carrito carrito = carritoRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + token));

        Libro libro = libroRepository.findById(request.getIdLibro())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado: " + request.getIdLibro()));

        BigDecimal precioUnitario = BigDecimal.valueOf(libro.getPrecio());

        Optional<CarritoItem> itemExistente =
                carritoItemRepository.findByCarritoAndIdLibro(carrito, request.getIdLibro());

        if (itemExistente.isPresent()) {
            CarritoItem item = itemExistente.get();
            int nuevaCantidad = item.getCantidad() + request.getCantidad();
            item.setCantidad(nuevaCantidad);
            item.setTotal(precioUnitario.multiply(BigDecimal.valueOf(nuevaCantidad)));
            carritoItemRepository.save(item);
        } else {
            CarritoItem nuevoItem = new CarritoItem();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setIdLibro(request.getIdLibro());
            nuevoItem.setCantidad(request.getCantidad());
            nuevoItem.setPrecioUnitario(precioUnitario);
            nuevoItem.setTotal(precioUnitario.multiply(BigDecimal.valueOf(request.getCantidad())));
            carritoItemRepository.save(nuevoItem);
            carrito.getItems().add(nuevoItem);
        }

        carrito.recomputarTotales(TASA_IVA);
        return carritoRepository.save(carrito);
    }

    // ── Actualizar cantidad de un ítem ────────────────────────────────────────
    @Override
    public Carrito actualizarItem(Long idCarritoItem, UpdateItemRequest request) {
        CarritoItem item = carritoItemRepository.findById(idCarritoItem)
                .orElseThrow(() -> new RuntimeException("Item no encontrado: " + idCarritoItem));

        item.setCantidad(request.getCantidad());
        item.setTotal(item.getPrecioUnitario().multiply(BigDecimal.valueOf(request.getCantidad())));
        carritoItemRepository.save(item);

        Carrito carrito = item.getCarrito();
        carrito.recomputarTotales(TASA_IVA);
        return carritoRepository.save(carrito);
    }

    // ── Eliminar un ítem ──────────────────────────────────────────────────────
    @Override
    public Carrito eliminarItem(Long idCarritoItem) {
        CarritoItem item = carritoItemRepository.findById(idCarritoItem)
                .orElseThrow(() -> new RuntimeException("Item no encontrado: " + idCarritoItem));

        Carrito carrito = item.getCarrito();
        carrito.getItems().remove(item);
        carritoItemRepository.delete(item);

        carrito.recomputarTotales(TASA_IVA);
        return carritoRepository.save(carrito);
    }

    // ── Vaciar carrito ────────────────────────────────────────────────────────
    @Override
    public void vaciarCarrito(String token) {
        Carrito carrito = carritoRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + token));

        carrito.getItems().clear();
        carrito.setSubtotal(BigDecimal.ZERO);
        carrito.setImpuestos(BigDecimal.ZERO);
        carrito.setTotal(BigDecimal.ZERO);
        carrito.setActualizadoEn(LocalDateTime.now());
        carritoRepository.save(carrito);
    }

    // ── Checkout: genera factura desde el carrito ─────────────────────────────
    @Override
    public Factura checkout(String token, CheckoutRequest request) {
        Carrito carrito = carritoRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + token));

        if (carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío, no se puede realizar el checkout");
        }

        Factura factura = checkoutMapper.convertir(carrito, request);
        vaciarCarrito(token);
        return factura;
    }
}
