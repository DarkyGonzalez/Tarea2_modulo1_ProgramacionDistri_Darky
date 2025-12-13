package com.distribuida.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoriaTestUnitario {
    private Categoria categoria;

    @BeforeEach
    public void setup(){
        categoria = new Categoria(1,"Romance","Mil amores");
    }

    @Test
    public void testCategoriaConstructor(){
        assertAll("Validar datos del categoria - Constructor",
                () -> assertEquals(1,categoria.getIdCategoria()),
                () -> assertEquals("Romance",categoria.getCategoria()),
                () -> assertEquals("Mil amores",categoria.getDescripcion())
        );

    }

    @Test
    public void testCategoriaSetters(){
        categoria.setIdCategoria(2);
        categoria.setCategoria("Novela");
        categoria.setDescripcion("Catalina");


        assertAll("Validar datos del categoria - Setters",
                () -> assertEquals(2, categoria.getIdCategoria()),
                () -> assertEquals("Novela", categoria.getCategoria()),
                () -> assertEquals("Catalina", categoria.getDescripcion())
        );
    }

    @Test
    public void testCategoriaToString(){
        String str = categoria.toString();
        assertAll("Validar datos del categoria - ToString",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("Romance")),
                () -> assertTrue(str.contains("Mil amores"))
        );

    }
}
