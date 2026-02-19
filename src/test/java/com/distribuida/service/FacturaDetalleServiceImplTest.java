package com.distribuida.service;


import com.distribuida.dao.FacturaDetalleRepository;
import com.distribuida.model.Cliente;
import com.distribuida.model.Factura;
import com.distribuida.model.FacturaDetalle;
import com.distribuida.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class FacturaDetalleServiceImplTest {

    @Mock
    private FacturaDetalleRepository facturaDetalleRepository;

    @InjectMocks
    private FacturaDetalleServiceImpl facturaDetalleService;

    private FacturaDetalle facturaDetalle;
    private Factura factura;
    private Libro libro;
    private Cliente cliente;

    @BeforeEach
    public void setUp(){
        cliente = new Cliente(1, "1720253263", "Maria", "Romero", "Quito Av. 10 de Agosto", "0999526238","rmaria2026@gmail.com");

        factura = new Factura();
        factura.setIdFactura(1);
        factura.setNumFactura("FAC-0097");
        factura.setFecha(new Date());
        factura.setTotalNeto(150.00);
        factura.setIva(22.50);
        factura.setTotal(175.50);
        factura.setCliente(cliente);

        libro = new Libro();
        libro.setIdLibro(1);
        libro.setTitulo("Cien años de soledad");

        facturaDetalle = new FacturaDetalle();
        facturaDetalle.setIdFacturaDetalle(1);
        facturaDetalle.setCantidad(2);
        facturaDetalle.setSubtotal(150.00);
        facturaDetalle.setFactura(factura);
        facturaDetalle.setLibro(libro);
    }

    @Test
    public void testFindAll(){
        when(facturaDetalleRepository.findAll()).thenReturn(List.of(facturaDetalle));
        List<FacturaDetalle> facturaDetalles = facturaDetalleService.findAll();

        assertNotNull(facturaDetalles);
        assertEquals(1, facturaDetalles.size());
        verify(facturaDetalleRepository, times(1)).findAll();
    }

    @Test
    public void findOneExistente (){
        when(facturaDetalleRepository.findById(1)).thenReturn(Optional.ofNullable(facturaDetalle));

        Optional<FacturaDetalle> facturaDetalle1 = facturaDetalleService.findOne(1);

        assertNotNull(facturaDetalle1);
        assertEquals(2, facturaDetalle1.orElse(null).getCantidad());

    }

    @Test
    public void findOneNoExistente (){
        when(facturaDetalleRepository.findById(999)).thenReturn(null);
        Optional<FacturaDetalle> resultado = facturaDetalleService.findOne(999);

        assertNull(resultado);
    }

    @Test
    public void save(){
        when(facturaDetalleRepository.save(facturaDetalle)).thenReturn(facturaDetalle);
        FacturaDetalle resultado = facturaDetalleService.save(facturaDetalle);
        assertNotNull(resultado);
        assertEquals(2, resultado.getCantidad());
    }

    @Test
    public void updateExistente(){
        FacturaDetalle facturaDetalleActualizada = new FacturaDetalle();

        facturaDetalleActualizada.setCantidad(3);
        facturaDetalleActualizada.setSubtotal(225.00);
        facturaDetalleActualizada.setFactura(factura);
        facturaDetalleActualizada.setLibro(libro);

        when(facturaDetalleRepository.findById(1)).thenReturn(Optional.ofNullable(facturaDetalle));
        when(facturaDetalleRepository.save(any())).thenReturn(facturaDetalleActualizada);

        FacturaDetalle resultado = facturaDetalleService.update(1, facturaDetalleActualizada);

        assertNotNull(resultado);
        assertEquals(3,resultado.getCantidad());

        verify(facturaDetalleRepository, times(1)).save(facturaDetalle);
    }

    @Test
    public void updateNoExistente(){
        FacturaDetalle facturaDetalleNuevo = new FacturaDetalle();
        when(facturaDetalleRepository.findById(999)).thenReturn(Optional.empty());
        FacturaDetalle resultado = facturaDetalleService.update(999,facturaDetalleNuevo);
        assertNull(resultado);
        verify(facturaDetalleRepository, never()).save(any());
    }

    @Test
    public void deleteExistente(){
        when(facturaDetalleRepository.existsById(1)).thenReturn(true);
        facturaDetalleService.delete(1);
        verify(facturaDetalleRepository).deleteById(1);
    }

    @Test
    public void deleteNoExistente (){
        when(facturaDetalleRepository.existsById(999)).thenReturn(false);
        facturaDetalleService.delete(999);
        verify(facturaDetalleRepository, never()).deleteById(999);
    }

}

