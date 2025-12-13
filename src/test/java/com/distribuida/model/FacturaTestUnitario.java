package com.distribuida.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FacturaTestUnitario {

    private Cliente cliente;
    private Factura factura;

    @BeforeEach
    public void setUp(){
        cliente = new Cliente(1,"01524252631","Maria",
                "Romero","El arenal","0998748596","mariar@correo.com");
        factura = new Factura();
        factura.setIdFactura(1);
        factura.setNumFactura("FAC-0001");
        factura.setFecha(new Date());
        factura.setTotalNeto(100.5);
        factura.setIva(15.01);
        factura.setTotal(115.51);

        // inyección de dependencias
        factura.setCliente(cliente);
    }

    @Test
    public void testFacturaConstructor(){
        assertAll("Validar constructor - Factura",
                () -> assertEquals(1,factura.getIdFactura()),
                () -> assertEquals("FAC-0001",factura.getNumFactura()),
                //para la fecha por cuestiones de que se toma la fecha al momento de ejetutar el programa no va a coincidir y se comenta para evitar el error
                // () -> assertEquals(new Date(),factura.getFecha()),
                () -> assertEquals(100.5,factura.getTotalNeto()),
                () -> assertEquals(15.01,factura.getIva()),
                () -> assertEquals(115.51,factura.getTotal()),
                () -> assertEquals("Maria",factura.getCliente().getNombre())
                );
    }

    @Test
    public void testFacturaSetters(){
        cliente = new Cliente(2,"01524252622","Maria22",
                "Romero22","El arenal22","0998748522","mariar22@correo.com");

        factura.setIdFactura(2);
        factura.setNumFactura("FAC-0002");
        factura.setFecha(new Date());
        factura.setTotalNeto(200.5);
        factura.setIva(30.02);
        factura.setTotal(230.52);

        // inyección de dependencias
        factura.setCliente(cliente);

        assertAll("Validar Setters - Factura",
                () -> assertEquals(2,factura.getIdFactura()),
                () -> assertEquals("FAC-0002",factura.getNumFactura()),
                //para la fecha por cuestiones de que se toma la fecha al momento de ejetutar el programa no va a coincidir y se comenta para evitar el error
                // () -> assertEquals(new Date(),factura.getFecha()),
                () -> assertEquals(200.5,factura.getTotalNeto()),
                () -> assertEquals(30.02,factura.getIva()),
                () -> assertEquals(230.52,factura.getTotal()),
                () -> assertEquals("Maria22",factura.getCliente().getNombre())
        );
    }

    @Test
    public void testFacturaToString(){
        String str = factura.toString();
        assertAll("Validar tostring - Factura",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("FAC-0001")),
                () -> assertTrue(str.contains("100.5")),
                () -> assertTrue(str.contains("15.01")),
                () -> assertTrue(str.contains("115.51")),
                () -> assertTrue(str.contains("Maria"))
                );
    }

    @Test
    public void testFacturaInyeccion(){
        assertAll("Validar inyector - Factura",
                () -> assertNotNull(factura.getCliente()),
                () -> assertEquals("Romero", factura.getCliente().getApellido())
                );
    }

    @Test
    public void testFacturaValoresNegativos(){
        factura.setTotal(-100.5);
        assertAll("Validar valores negativos - Factura",
                () -> assertEquals(-100.5, factura.getTotal())
                );

        //Validar los valores numericos en la factura para que no sean negativos
    }
}
