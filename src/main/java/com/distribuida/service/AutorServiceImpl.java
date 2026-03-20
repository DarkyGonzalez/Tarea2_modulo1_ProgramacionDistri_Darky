package com.distribuida.service;

import com.distribuida.dao.AutorRepository;
import com.distribuida.model.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    @Override
    public Optional<Autor> findOne(int id) {
        return autorRepository.findById(id);
    }

    @Override
    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    @Override
    public Autor update(int id, Autor autor) {
        Optional<Autor> autorOptional = autorRepository.findById(id);
        if (autorOptional.isPresent()) {
            Autor autorExistente = autorOptional.get();
            autorExistente.setNombre(autor.getNombre());
            autorExistente.setApellido(autor.getApellido());
            autorExistente.setPais(autor.getPais());
            autorExistente.setDireccion(autor.getDireccion());
            autorExistente.setTelefono(autor.getTelefono());
            autorExistente.setCorreo(autor.getCorreo());
            return autorRepository.save(autorExistente);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
        }
    }
}

