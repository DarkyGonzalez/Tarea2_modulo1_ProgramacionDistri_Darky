package com.distribuida.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutorTestUnitario {

    private Autor autor;

    @BeforeEach
    public void setup(){
        autor = new Autor(1,"Miguel","Sevantes","España",
                "España","2222222","miguels@correo.com");
    }

    @Test
    public void testAutorConstructor(){
        assertAll("Validar datos del autor - Constructor",
                () -> assertEquals(1,autor.getIdAutor()),
                () -> assertEquals("Miguel",autor.getNombre()),
                () -> assertEquals("Sevantes",autor.getApellido()),
                () -> assertEquals("España",autor.getPais()),
                () -> assertEquals("España",autor.getDireccion()),
                () -> assertEquals("2222222",autor.getTelefono()),
                () -> assertEquals("miguels@correo.com",autor.getCorreo())
        );

    }

    @Test
    public void testAutorSetters(){
        autor.setIdAutor(2);
        autor.setNombre("Gabriel");
        autor.setApellido("Garcia");
        autor.setPais("España");
        autor.setDireccion("Cataluña");
        autor.setTelefono("022222221");
        autor.setCorreo("gabrielg2025@correo.com");

        assertAll("Validar datos del autor - Setters",
                () -> assertEquals(2, autor.getIdAutor()),
                () -> assertEquals("Gabriel", autor.getNombre()),
                () -> assertEquals("Garcia", autor.getApellido()),
                () -> assertEquals("España", autor.getPais()),
                () -> assertEquals("Cataluña", autor.getDireccion()),
                () -> assertEquals("022222221", autor.getTelefono()),
                () -> assertEquals("gabrielg2025@correo.com", autor.getCorreo())
        );
    }

    @Test
    public void testAutorToString(){
        String str = autor.toString();
        assertAll("Validar datos del autor - ToString",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("Miguel")),
                () -> assertTrue(str.contains("Sevantes")),
                () -> assertTrue(str.contains("España")),
                () -> assertTrue(str.contains("Espa")),
                () -> assertTrue(str.contains("22222")),
                () -> assertTrue(str.contains("miguels@correo.com"))
        );

    }
}

