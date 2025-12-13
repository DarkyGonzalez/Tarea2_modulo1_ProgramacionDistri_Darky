package com.distribuida.service;

import com.distribuida.dao.ClienteRepository;
import com.distribuida.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> findOne(int id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente update(int id, Cliente cliente) {
        Optional<Cliente> cliente2 = clienteRepository.findById(id);
        cliente2.orElse(null).setCedula(cliente.getCedula());
        cliente2.orElse(null).setNombre(cliente.getNombre());
        cliente2.orElse(null).setApellido(cliente.getApellido());
        cliente2.orElse(null).setDireccion(cliente.getDireccion());
        cliente2.orElse(null).setTelefono(cliente.getTelefono());
        cliente2.orElse(null).setCorreo(cliente.getCorreo());

        return clienteRepository.save(cliente2.orElse(null));
    }

    @Override
    public void delete(int id) {
        if(clienteRepository.existsById(id)){
          clienteRepository.deleteById(id);
        }

    }
}
