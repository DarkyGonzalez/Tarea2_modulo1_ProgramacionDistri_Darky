package com.distribuida.service;


import com.distribuida.dao.LibroRepository;
import com.distribuida.model.Autor;
import com.distribuida.model.Categoria;
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
public class LibroServiceImplTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    private Libro libro;
    private Autor autor;
    private Categoria categoria;

    @BeforeEach
    public void setUp(){
        autor = new Autor(1, "Gabriel", "García Márquez", "Colombia", "Calle Principal 123", "0991234567", "gabriel.garcia@example.com");

        categoria = new Categoria(1, "Ficción", "Libros de ficción y novelas");

        libro = new Libro();
        libro.setIdLibro(1);
        libro.setTitulo("Cien años de soledad");
        libro.setEditorial("Sudamericana");
        libro.setNumPagina(471);
        libro.setEdicion("Primera");
        libro.setIdioma("Español");
        libro.setFechaPublicacion(new Date());
        libro.setDescripcion("Novela del realismo mágico");
        libro.setTipoPasta("Dura");
        libro.setIsbn("978-84-376-0494-7");
        libro.setNumEjemplares(10);
        libro.setPortada("portada.jpg");
        libro.setPresentacion("Físico");
        libro.setPrecio(25.99);
        libro.setCategoria(categoria);
        libro.setAutor(autor);
    }

    @Test
    public void testFindAll(){
        when(libroRepository.findAll()).thenReturn(List.of(libro));
        List<Libro> libros = libroService.findAll();

        assertNotNull(libros);
        assertEquals(1, libros.size());
        verify(libroRepository, times(1)).findAll();
    }

    @Test
    public void findOneExistente (){
        when(libroRepository.findById(1)).thenReturn(Optional.ofNullable(libro));

        Optional<Libro> libro1 = libroService.findOne(1);

        assertNotNull(libro1);
        assertEquals("Cien años de soledad", libro1.orElse(null).getTitulo());

    }

    @Test
    public void findOneNoExistente (){
        when(libroRepository.findById(999)).thenReturn(null);
        Optional<Libro> resultado = libroService.findOne(999);

        assertNull(resultado);
    }

    @Test
    public void save(){
        when(libroRepository.save(libro)).thenReturn(libro);
        Libro resultado = libroService.save(libro);
        assertNotNull(resultado);
        assertEquals("Cien años de soledad", resultado.getTitulo());
    }

    @Test
    public void updateExistente(){
        Libro libroActualizado = new Libro();

        libroActualizado.setTitulo("El amor en los tiempos del cólera");
        libroActualizado.setEditorial("Oveja Negra");
        libroActualizado.setNumPagina(464);
        libroActualizado.setEdicion("Segunda");
        libroActualizado.setIdioma("Español");
        libroActualizado.setFechaPublicacion(new Date());
        libroActualizado.setDescripcion("Novela romántica");
        libroActualizado.setTipoPasta("Blanda");
        libroActualizado.setIsbn("978-84-376-0495-8");
        libroActualizado.setNumEjemplares(15);
        libroActualizado.setPortada("portada2.jpg");
        libroActualizado.setPresentacion("Físico");
        libroActualizado.setPrecio(22.99);
        libroActualizado.setCategoria(categoria);
        libroActualizado.setAutor(autor);

        when(libroRepository.findById(1)).thenReturn(Optional.ofNullable(libro));
        when(libroRepository.save(any())).thenReturn(libroActualizado);

        Libro resultado = libroService.update(1, libroActualizado);

        assertNotNull(resultado);
        assertEquals("El amor en los tiempos del cólera",resultado.getTitulo());

        verify(libroRepository, times(1)).save(libro);
    }

    @Test
    public void updateNoExistente(){
        Libro libroNuevo = new Libro();
        when(libroRepository.findById(999)).thenReturn(Optional.empty());
        Libro resultado = libroService.update(999,libroNuevo);
        assertNull(resultado);
        verify(libroRepository, never()).save(any());
    }

    @Test
    public void deleteExistente(){
        when(libroRepository.existsById(1)).thenReturn(true);
        libroService.delete(1);
        verify(libroRepository).deleteById(1);
    }

    @Test
    public void deleteNoExistente (){
        when(libroRepository.existsById(999)).thenReturn(false);
        libroService.delete(999);
        verify(libroRepository, never()).deleteById(999);
    }

}

