package com.distribuida.service;


import com.distribuida.dao.AutorRepository;
import com.distribuida.model.Autor;
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
public class AutorServiceImplTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorServiceImpl autorService;
    private Autor autor;

    @BeforeEach
    void setUp(){
        autor = new Autor();
        autor.setIdAutor(1);
        autor.setNombre("Gabriel");
        autor.setApellido("García Márquez");
        autor.setPais("Colombia");
        autor.setDireccion("Calle Principal 123");
        autor.setTelefono("0991234567");
        autor.setCorreo("gabriel.garcia@example.com");
    }

    @Test
    public void testfindAll(){
        when(autorRepository.findAll()).thenReturn(List.of(autor));
        List<Autor> autores = autorService.findAll();

        assertNotNull(autores);
        assertEquals(1, autores.size());
    }

    @Test
    public void testFindOneExistente(){
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));

        Optional<Autor> resultado = autorService.findOne(1);

        assertNotNull(resultado);
        assertEquals("Gabriel",resultado.orElse(null).getNombre());
    }

    @Test
    public void testFindOneNoexistente(){
        when(autorRepository.findById(2)).thenReturn(null);

        Optional<Autor> resultado = autorService.findOne(2);

        assertNull(resultado);

    }

    @Test
    public void testSave(){
        when(autorRepository.save(autor)).thenReturn(autor);

        Autor autor1 = autorService.save(autor);

        assertNotNull(autor1);
        assertEquals("Gabriel", autor1.getNombre());
    }

    @Test
    public void testUpdateExistente(){
        Autor autorActualizado = new Autor();

        autorActualizado.setNombre("Mario");
        autorActualizado.setApellido("Vargas Llosa");
        autorActualizado.setPais("Perú");
        autorActualizado.setDireccion("Dirección 2");
        autorActualizado.setTelefono("0999876543");
        autorActualizado.setCorreo("mario.vargas@example.com");

        when(autorRepository.findById(1)).thenReturn(Optional.ofNullable(autor));
        when(autorRepository.save(any())).thenReturn(autorActualizado);

        Autor resultado = autorService.update(1,autorActualizado);

        assertNotNull(resultado);
        assertEquals("Mario", resultado.getNombre());

    }

    @Test
    public void testUpdateNoExistente(){
        Autor autorNuevo = new Autor();
        when(autorRepository.findById(999)).thenReturn(Optional.empty());
        Autor resultado = autorService.update(999,autorNuevo);
        assertNull(resultado);
        verify(autorRepository, never()).save(any());

    }

    @Test
    public void testDeleteExistente(){
        when(autorRepository.existsById(1)).thenReturn(true);
        autorService.delete(1);
        verify(autorRepository).deleteById(1);
    }

    @Test
    public void testDeleteNoExistente (){
        when(autorRepository.existsById(999)).thenReturn(false);
        autorService.delete(999);
        verify(autorRepository, never()).deleteById(anyInt());
    }



}

