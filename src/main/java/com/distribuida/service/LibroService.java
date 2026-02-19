package com.distribuida.service;

import com.distribuida.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {

    public List<Libro> findAll();

    public Optional<Libro> findOne(int id);

    public Libro save(Libro libro);

    public Libro update(int id, Libro libro);

    public void delete(int id);

}

