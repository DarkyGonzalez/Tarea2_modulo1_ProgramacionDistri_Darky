package com.distribuida.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FacturaDetalleTestUnitario {

    private Factura factura;
    private Libro libro;
    //private Categoria categoria;
    //private Autor autor;
    private FacturaDetalle facturaDetalle;

    @BeforeEach
    public void setUp(){
        factura = new Factura(1,"FAC-0001",new Date(),100.5,15.01,115.01,new Cliente());
        libro = new Libro(1,"La culpa es de la vaca","Saleciana",115,"7ma","Español",new Date(),"suspenso",
                "Dura","N/A",1,"Dibujo","Nueva",30.5,new Categoria(),new Autor());
        facturaDetalle = new FacturaDetalle();
        facturaDetalle.setIdFacturaDetalle(1);
        facturaDetalle.setCantidad(10);
        facturaDetalle.setSubtotal(305.0);

        // inyección de dependencias
        facturaDetalle.setFactura(factura);
        facturaDetalle.setLibro(libro);
    }

    @Test
    public void testFacturaDetalleConstructor(){
        assertAll("Validar constructor - FacturaDetalle",
                () -> assertEquals(1,facturaDetalle.getIdFacturaDetalle()),
                () -> assertEquals(10,facturaDetalle.getCantidad()),
                () -> assertEquals(305.0,facturaDetalle.getSubtotal()),
                () -> assertEquals("FAC-0001",facturaDetalle.getFactura().getNumFactura()),
                () -> assertEquals(1,facturaDetalle.getLibro().getIdLibro())
        );
    }


}
