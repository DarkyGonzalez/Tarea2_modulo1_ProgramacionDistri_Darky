package com.distribuida.service;

import com.distribuida.dao.LibroRepository;
import com.distribuida.model.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    @Override
    public Optional<Libro> findOne(int id) {
        return libroRepository.findById(id);
    }

    @Override
    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public Libro update(int id, Libro libro) {
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (libroOptional.isPresent()) {
            Libro libroExistente = libroOptional.get();
            libroExistente.setTitulo(libro.getTitulo());
            libroExistente.setEditorial(libro.getEditorial());
            libroExistente.setNumPagina(libro.getNumPagina());
            libroExistente.setEdicion(libro.getEdicion());
            libroExistente.setIdioma(libro.getIdioma());
            libroExistente.setFechaPublicacion(libro.getFechaPublicacion());
            libroExistente.setDescripcion(libro.getDescripcion());
            libroExistente.setTipoPasta(libro.getTipoPasta());
            libroExistente.setIsbn(libro.getIsbn());
            libroExistente.setNumEjemplares(libro.getNumEjemplares());
            libroExistente.setPortada(libro.getPortada());
            libroExistente.setPresentacion(libro.getPresentacion());
            libroExistente.setPrecio(libro.getPrecio());
            libroExistente.setCategoria(libro.getCategoria());
            libroExistente.setAutor(libro.getAutor());
            return libroRepository.save(libroExistente);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
        }
    }
}

