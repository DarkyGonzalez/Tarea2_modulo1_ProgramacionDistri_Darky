package com.distribuida.service;


import com.distribuida.dao.CategoriaRepository;
import com.distribuida.model.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;
    private Categoria categoria;

    @BeforeEach
    void setUp(){
        categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setCategoria("Ficción");
        categoria.setDescripcion("Libros de ficción y novelas");
    }

    @Test
    public void testfindAll(){
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));
        List<Categoria> categorias = categoriaService.findAll();

        assertNotNull(categorias);
        assertEquals(1, categorias.size());
    }

    @Test
    public void testFindOneExistente(){
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));

        Optional<Categoria> resultado = categoriaService.findOne(1);

        assertNotNull(resultado);
        assertEquals("Ficción",resultado.orElse(null).getCategoria());
    }

    @Test
    public void testFindOneNoexistente(){
        when(categoriaRepository.findById(2)).thenReturn(null);

        Optional<Categoria> resultado = categoriaService.findOne(2);

        assertNull(resultado);

    }

    @Test
    public void testSave(){
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        Categoria categoria1 = categoriaService.save(categoria);

        assertNotNull(categoria1);
        assertEquals("Ficción", categoria1.getCategoria());
    }

    @Test
    public void testUpdateExistente(){
        Categoria categoriaActualizada = new Categoria();

        categoriaActualizada.setCategoria("Ciencia Ficción");
        categoriaActualizada.setDescripcion("Libros de ciencia ficción");

        when(categoriaRepository.findById(1)).thenReturn(Optional.ofNullable(categoria));
        when(categoriaRepository.save(any())).thenReturn(categoriaActualizada);

        Categoria resultado = categoriaService.update(1,categoriaActualizada);

        assertNotNull(resultado);
        assertEquals("Ciencia Ficción", resultado.getCategoria());

    }

    @Test
    public void testUpdateNoExistente(){
        Categoria categoriaNuevo = new Categoria();
        when(categoriaRepository.findById(999)).thenReturn(Optional.empty());
        Categoria resultado = categoriaService.update(999,categoriaNuevo);
        assertNull(resultado);
        verify(categoriaRepository, never()).save(any());

    }

    @Test
    public void testDeleteExistente(){
        when(categoriaRepository.existsById(1)).thenReturn(true);
        categoriaService.delete(1);
        verify(categoriaRepository).deleteById(1);
    }

    @Test
    public void testDeleteNoExistente (){
        when(categoriaRepository.existsById(999)).thenReturn(false);
        categoriaService.delete(999);
        verify(categoriaRepository, never()).deleteById(anyInt());
    }



}

