package com.distribuida.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTestUnitario {

    private Cliente cliente;

    @BeforeEach
    public void setup(){
        cliente = new Cliente(1,"1724545288","Diego",
                "Trujillo","Tumbaco","0981529283","trujito4545@gmail.com");
    }

    @Test
    public void testClienteConstructor(){
        assertAll("Validar datos del cliente - Constructor",
                () -> assertEquals(1,cliente.getIdCliente()),
                () -> assertEquals("1724545288",cliente.getCedula()),
                () -> assertEquals("Diego",cliente.getNombre()),
                () -> assertEquals("Trujillo",cliente.getApellido()),
                () -> assertEquals("Tumbaco",cliente.getDireccion()),
                () -> assertEquals("0981529283",cliente.getTelefono()),
                () -> assertEquals("trujito4545@gmail.com",cliente.getCorreo())
                );

    }

    @Test
    public void testClienteSetters(){
        cliente.setIdCliente(2);
        cliente.setCedula("1724545222");
        cliente.setNombre("Carlos");
        cliente.setApellido("Gonzalez");
        cliente.setDireccion("Pifo");
        cliente.setTelefono("0981529222");
        cliente.setCorreo("carlosg2025@gmail.com");

        assertAll("Validar datos del cliente - Setters",
                () -> assertEquals(2, cliente.getIdCliente()),
                () -> assertEquals("1724545222", cliente.getCedula()),
                () -> assertEquals("Carlos", cliente.getNombre()),
                () -> assertEquals("Gonzalez", cliente.getApellido()),
                () -> assertEquals("Pifo", cliente.getDireccion()),
                () -> assertEquals("0981529222", cliente.getTelefono()),
                () -> assertEquals("carlosg2025@gmail.com", cliente.getCorreo())
        );
    }

    @Test
    public void testClienteToString(){
        String str = cliente.toString();
        assertAll("Validar datos del cliente - ToString",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("17245452")),
                () -> assertTrue(str.contains("Die")),
                () -> assertTrue(str.contains("llo")),
                () -> assertTrue(str.contains("Tu")),
                () -> assertTrue(str.contains("0981")),
                () -> assertTrue(str.contains("truji"))
        );

    }
}
