package com.distribuida.service;

import com.distribuida.model.Autor;

import java.util.List;
import java.util.Optional;

public interface AutorService {

    public List<Autor> findAll();

    public Optional<Autor> findOne(int id);

    public Autor save(Autor autor);

    public Autor update(int id, Autor autor);

    public void delete(int id);

}

