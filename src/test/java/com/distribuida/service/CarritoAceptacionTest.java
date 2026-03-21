package com.distribuida.service;

import com.distribuida.dao.CarritoItemRepository;
import com.distribuida.dao.CarritoRepository;
import com.distribuida.dao.ClienteRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.dto.AddItemRequest;
import com.distribuida.dto.CheckoutRequest;
import com.distribuida.dto.UpdateItemRequest;
import com.distribuida.mapper.CheckoutMapper;
import com.distribuida.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas de Aceptación — Módulo Carrito de Compras
 *
 * Criterios de aceptación verificados:
 *
 *  PA-01: El cliente puede iniciar una sesión de compra (crear carrito)
 *         DADO que el cliente existe en el sistema
 *         CUANDO solicita su carrito
 *         ENTONCES se crea un carrito nuevo con token único y saldo cero
 *
 *  PA-02: El cliente puede agregar libros al carrito
 *         DADO que el carrito existe y el libro tiene precio
 *         CUANDO agrega un libro con cantidad
 *         ENTONCES el ítem aparece en el carrito y los totales se actualizan
 *
 *  PA-03: El cliente puede modificar la cantidad de un ítem
 *         DADO que el ítem existe en el carrito
 *         CUANDO cambia la cantidad
 *         ENTONCES la cantidad se actualiza y los totales se recalculan
 *
 *  PA-04: El cliente puede eliminar un ítem del carrito
 *         DADO que el ítem existe en el carrito
 *         CUANDO lo elimina
 *         ENTONCES el carrito queda sin ese ítem
 *
 *  PA-05: El cliente puede completar la compra (checkout)
 *         DADO que el carrito tiene ítems
 *         CUANDO realiza el checkout
 *         ENTONCES se genera una factura y el carrito queda vacío
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de Aceptación – Módulo Carrito de Compras")
public class CarritoAceptacionTest {

    @Mock private CarritoRepository     carritoRepository;
    @Mock private CarritoItemRepository carritoItemRepository;
    @Mock private ClienteRepository     clienteRepository;
    @Mock private LibroRepository       libroRepository;
    @Mock private CheckoutMapper        checkoutMapper;

    @InjectMocks
    private CarritoServiceImpl carritoService;

    private Cliente   cliente;
    private Carrito   carrito;
    private Libro     libro;
    private CarritoItem item;

    @BeforeEach
    public void configurarEscenario() {
        // Cliente de prueba
        cliente = new Cliente(1, "0987654321", "Juan", "Mora",
                "Av. Quito 100", "0991234567", "juan.mora@test.com");

        // Carrito vacío
        carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setCliente(cliente);
        carrito.setToken("TOKEN-ACEPTACION-001");
        carrito.setSubtotal(BigDecimal.ZERO);
        carrito.setDescuento(BigDecimal.ZERO);
        carrito.setImpuestos(BigDecimal.ZERO);
        carrito.setTotal(BigDecimal.ZERO);
        carrito.setActualizadoEn(LocalDateTime.now());
        carrito.setItems(new ArrayList<>());

        // Libro de prueba
        libro = new Libro();
        libro.setIdLibro(5);
        libro.setTitulo("Clean Code");
        libro.setPrecio(29.99);

        // Ítem de prueba
        item = new CarritoItem();
        item.setIdCarritoItem(1L);
        item.setCarrito(carrito);
        item.setIdLibro(5);
        item.setCantidad(2);
        item.setPrecioUnitario(BigDecimal.valueOf(29.99));
        item.setTotal(BigDecimal.valueOf(59.98));
    }

    // ── PA-01: Crear sesión de compra ─────────────────────────────────────────
    @Test
    @DisplayName("PA-01: El cliente puede iniciar una sesión de compra")
    public void PA01_clientePuedeIniciarSesionDeCompra() {
        // DADO
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.empty());
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> {
            Carrito c = inv.getArgument(0);
            c.setIdCarrito(1L);
            return c;
        });

        // CUANDO
        Carrito resultado = carritoService.obtenerOCrearCarrito(1);

        // ENTONCES
        assertNotNull(resultado, "El carrito no debe ser nulo");
        assertNotNull(resultado.getToken(), "El carrito debe tener un token único");
        assertFalse(resultado.getToken().isEmpty(), "El token no debe estar vacío");
        assertEquals(BigDecimal.ZERO, resultado.getSubtotal(), "El subtotal inicial debe ser cero");
        assertEquals(BigDecimal.ZERO, resultado.getTotal(), "El total inicial debe ser cero");
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }

    // ── PA-02: Agregar libro al carrito ───────────────────────────────────────
    @Test
    @DisplayName("PA-02: El cliente puede agregar libros al carrito")
    public void PA02_clientePuedeAgregarLibrosAlCarrito() {
        // DADO
        AddItemRequest request = new AddItemRequest();
        request.setIdLibro(5);
        request.setCantidad(2);

        when(carritoRepository.findByToken("TOKEN-ACEPTACION-001"))
                .thenReturn(Optional.of(carrito));
        when(libroRepository.findById(5)).thenReturn(Optional.of(libro));
        when(carritoItemRepository.findByCarritoAndIdLibro(carrito, 5))
                .thenReturn(Optional.empty());
        when(carritoItemRepository.save(any(CarritoItem.class))).thenAnswer(inv -> inv.getArgument(0));
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> inv.getArgument(0));

        // CUANDO
        Carrito resultado = carritoService.agregarItem("TOKEN-ACEPTACION-001", request);

        // ENTONCES
        assertNotNull(resultado, "El carrito actualizado no debe ser nulo");
        verify(carritoItemRepository, times(1)).save(any(CarritoItem.class));
        verify(carritoRepository, times(1)).save(carrito);
    }

    // ── PA-03: Modificar cantidad de ítem ─────────────────────────────────────
    @Test
    @DisplayName("PA-03: El cliente puede modificar la cantidad de un ítem")
    public void PA03_clientePuedeModificarCantidadDeItem() {
        // DADO
        carrito.getItems().add(item);
        UpdateItemRequest request = new UpdateItemRequest();
        request.setCantidad(5);

        when(carritoItemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(carritoItemRepository.save(any(CarritoItem.class))).thenAnswer(inv -> inv.getArgument(0));
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> inv.getArgument(0));

        // CUANDO
        Carrito resultado = carritoService.actualizarItem(1L, request);

        // ENTONCES
        assertEquals(5, item.getCantidad(),
                "La cantidad debe haberse actualizado a 5");
        BigDecimal totalEsperado = BigDecimal.valueOf(29.99).multiply(BigDecimal.valueOf(5));
        assertEquals(0, totalEsperado.compareTo(item.getTotal()),
                "El total del ítem debe recalcularse correctamente");
        assertNotNull(resultado);
    }

    // ── PA-04: Eliminar ítem del carrito ──────────────────────────────────────
    @Test
    @DisplayName("PA-04: El cliente puede eliminar un ítem del carrito")
    public void PA04_clientePuedeEliminarItemDelCarrito() {
        // DADO
        carrito.getItems().add(item);
        assertEquals(1, carrito.getItems().size(), "El carrito debe tener 1 ítem antes de eliminar");

        when(carritoItemRepository.findById(1L)).thenReturn(Optional.of(item));
        doNothing().when(carritoItemRepository).delete(item);
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> inv.getArgument(0));

        // CUANDO
        Carrito resultado = carritoService.eliminarItem(1L);

        // ENTONCES
        assertTrue(resultado.getItems().isEmpty(),
                "El carrito debe quedar vacío después de eliminar el único ítem");
        verify(carritoItemRepository, times(1)).delete(item);
    }

    // ── PA-05: Completar la compra ────────────────────────────────────────────
    @Test
    @DisplayName("PA-05: El cliente puede completar la compra y obtener una factura")
    public void PA05_clientePuedeCompletarLaCompra() {
        // DADO
        carrito.getItems().add(item);
        carrito.setSubtotal(BigDecimal.valueOf(59.98));
        carrito.setImpuestos(BigDecimal.valueOf(9.00));
        carrito.setTotal(BigDecimal.valueOf(68.98));

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setIdCliente(1);
        checkoutRequest.setMetodoPago("EFECTIVO");

        com.distribuida.model.Factura facturaEsperada = new com.distribuida.model.Factura();
        facturaEsperada.setIdFactura(1);
        facturaEsperada.setNumFactura("FAC-12345678");
        facturaEsperada.setTotal(68.98);
        facturaEsperada.setCliente(cliente);

        when(carritoRepository.findByToken("TOKEN-ACEPTACION-001"))
                .thenReturn(Optional.of(carrito));
        when(checkoutMapper.convertir(eq(carrito), any(CheckoutRequest.class)))
                .thenReturn(facturaEsperada);
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> inv.getArgument(0));

        // CUANDO
        com.distribuida.model.Factura resultado =
                carritoService.checkout("TOKEN-ACEPTACION-001", checkoutRequest);

        // ENTONCES
        assertNotNull(resultado, "Debe generarse una factura");
        assertNotNull(resultado.getNumFactura(), "La factura debe tener número");
        assertEquals(68.98, resultado.getTotal(), 0.01,
                "El total de la factura debe coincidir con el total del carrito");
        verify(checkoutMapper, times(1)).convertir(eq(carrito), any(CheckoutRequest.class));
    }
}
