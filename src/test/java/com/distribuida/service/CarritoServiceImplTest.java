package com.distribuida.service;

import com.distribuida.dao.CarritoItemRepository;
import com.distribuida.dao.CarritoRepository;
import com.distribuida.dao.ClienteRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.dto.AddItemRequest;
import com.distribuida.dto.UpdateItemRequest;
import com.distribuida.mapper.CheckoutMapper;
import com.distribuida.model.*;
import org.junit.jupiter.api.BeforeEach;
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
 * Pruebas Unitarias — CarritoServiceImpl
 *
 * Cobertura:
 *  1. obtenerOCrearCarrito  → crea carrito nuevo cuando no existe
 *  2. obtenerOCrearCarrito  → retorna carrito existente
 *  3. agregarItem           → agrega ítem nuevo al carrito
 *  4. agregarItem           → acumula cantidad si el ítem ya existe
 *  5. actualizarItem        → actualiza la cantidad del ítem
 *  6. eliminarItem          → elimina el ítem y recalcula totales
 *  7. vaciarCarrito         → deja el carrito sin ítems
 */
@ExtendWith(MockitoExtension.class)
public class CarritoServiceImplTest {

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
    public void setUp() {
        cliente = new Cliente(1, "1234567890", "Ana", "Pérez",
                "Av. Principal 1", "0991234567", "ana@ejemplo.com");

        carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setCliente(cliente);
        carrito.setToken("token-test-001");
        carrito.setSubtotal(BigDecimal.ZERO);
        carrito.setDescuento(BigDecimal.ZERO);
        carrito.setImpuestos(BigDecimal.ZERO);
        carrito.setTotal(BigDecimal.ZERO);
        carrito.setActualizadoEn(LocalDateTime.now());
        carrito.setItems(new ArrayList<>());

        libro = new Libro();
        libro.setIdLibro(10);
        libro.setTitulo("Fundamentos de Programación");
        libro.setPrecio(19.99);

        item = new CarritoItem();
        item.setIdCarritoItem(1L);
        item.setCarrito(carrito);
        item.setIdLibro(10);
        item.setCantidad(2);
        item.setPrecioUnitario(BigDecimal.valueOf(19.99));
        item.setTotal(BigDecimal.valueOf(39.98));
    }

    // ── TEST 1: crear carrito nuevo ───────────────────────────────────────────
    @Test
    public void obtenerOCrearCarrito_creaCarritoNuevoCuandoNoExiste() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.empty());
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> inv.getArgument(0));

        Carrito resultado = carritoService.obtenerOCrearCarrito(1);

        assertNotNull(resultado);
        assertNotNull(resultado.getToken());
        assertEquals(BigDecimal.ZERO, resultado.getSubtotal());
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }

    // ── TEST 2: retornar carrito existente ────────────────────────────────────
    @Test
    public void obtenerOCrearCarrito_retornaCarritoExistente() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.of(carrito));

        Carrito resultado = carritoService.obtenerOCrearCarrito(1);

        assertNotNull(resultado);
        assertEquals("token-test-001", resultado.getToken());
        verify(carritoRepository, never()).save(any());
    }

    // ── TEST 3: agregar ítem nuevo al carrito ─────────────────────────────────
    @Test
    public void agregarItem_agregaItemNuevoCuandoNoExiste() {
        AddItemRequest request = new AddItemRequest();
        request.setIdLibro(10);
        request.setCantidad(1);

        when(carritoRepository.findByToken("token-test-001")).thenReturn(Optional.of(carrito));
        when(libroRepository.findById(10)).thenReturn(Optional.of(libro));
        when(carritoItemRepository.findByCarritoAndIdLibro(carrito, 10)).thenReturn(Optional.empty());
        when(carritoItemRepository.save(any(CarritoItem.class))).thenAnswer(inv -> inv.getArgument(0));
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> inv.getArgument(0));

        Carrito resultado = carritoService.agregarItem("token-test-001", request);

        assertNotNull(resultado);
        verify(carritoItemRepository, times(1)).save(any(CarritoItem.class));
        verify(carritoRepository, times(1)).save(carrito);
    }

    // ── TEST 4: acumular cantidad si ítem ya existe ───────────────────────────
    @Test
    public void agregarItem_acumulaCantidadCuandoItemYaExiste() {
        AddItemRequest request = new AddItemRequest();
        request.setIdLibro(10);
        request.setCantidad(3);

        when(carritoRepository.findByToken("token-test-001")).thenReturn(Optional.of(carrito));
        when(libroRepository.findById(10)).thenReturn(Optional.of(libro));
        when(carritoItemRepository.findByCarritoAndIdLibro(carrito, 10)).thenReturn(Optional.of(item));
        when(carritoItemRepository.save(any(CarritoItem.class))).thenAnswer(inv -> inv.getArgument(0));
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> inv.getArgument(0));

        carritoService.agregarItem("token-test-001", request);

        // cantidad anterior 2 + nueva 3 = 5
        assertEquals(5, item.getCantidad());
        verify(carritoItemRepository, times(1)).save(item);
    }

    // ── TEST 5: actualizar cantidad de ítem ───────────────────────────────────
    @Test
    public void actualizarItem_actualizaCantidadCorrectamente() {
        carrito.getItems().add(item);
        UpdateItemRequest request = new UpdateItemRequest();
        request.setCantidad(4);

        when(carritoItemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(carritoItemRepository.save(any(CarritoItem.class))).thenAnswer(inv -> inv.getArgument(0));
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> inv.getArgument(0));

        Carrito resultado = carritoService.actualizarItem(1L, request);

        assertEquals(4, item.getCantidad());
        assertNotNull(resultado);
        verify(carritoItemRepository, times(1)).save(item);
    }

    // ── TEST 6: eliminar ítem del carrito ─────────────────────────────────────
    @Test
    public void eliminarItem_eliminaItemYRecalculaTotales() {
        carrito.getItems().add(item);

        when(carritoItemRepository.findById(1L)).thenReturn(Optional.of(item));
        doNothing().when(carritoItemRepository).delete(item);
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> inv.getArgument(0));

        Carrito resultado = carritoService.eliminarItem(1L);

        assertTrue(resultado.getItems().isEmpty());
        verify(carritoItemRepository, times(1)).delete(item);
    }

    // ── TEST 7: vaciar carrito ────────────────────────────────────────────────
    @Test
    public void vaciarCarrito_dejaSinItems() {
        carrito.getItems().add(item);

        when(carritoRepository.findByToken("token-test-001")).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(inv -> inv.getArgument(0));

        carritoService.vaciarCarrito("token-test-001");

        assertTrue(carrito.getItems().isEmpty());
        assertEquals(BigDecimal.ZERO, carrito.getSubtotal());
        assertEquals(BigDecimal.ZERO, carrito.getTotal());
    }
}
