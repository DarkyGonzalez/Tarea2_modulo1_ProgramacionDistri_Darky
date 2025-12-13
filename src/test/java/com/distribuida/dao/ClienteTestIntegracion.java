package com.distribuida.dao;

import com.distribuida.model.Cliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class ClienteTestIntegracion {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void findAll(){
        List<Cliente> clientes = clienteRepository.findAll();
        for (Cliente item: clientes){
            System.out.println(item.toString());
        }
    }

    //consulta
    @Test
    public void findOne(){
        Optional<Cliente> cliente =clienteRepository.findById(1);
        System.out.println(cliente.toString());

    }

    //guardado para esto siempre en ID se pone 0
    @Test
    public void save(){
        Cliente cliente = new Cliente(0,"1725222325","Andres",
                "Veloz","Gonzalo de Vera","0974878589","andresv@correo.com");
        clienteRepository.save(cliente);
    }

    //metodo de actualización
    @Test
    public void update(){
        Optional<Cliente> cliente = clienteRepository.findById(39);

        cliente.orElse(null).setCedula("0125352645");
        cliente.orElse(null).setNombre("Juan");
        cliente.orElse(null).setApellido("Romero");

        //Actualización
        clienteRepository.save(cliente.orElse(null));

    }

    //metodo de borrado
    @Test
    public void deleta(){
        clienteRepository.deleteById(39);
    }
}
