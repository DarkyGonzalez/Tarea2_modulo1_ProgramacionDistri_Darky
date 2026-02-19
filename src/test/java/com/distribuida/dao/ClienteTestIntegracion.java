package com.distribuida.dao;

import com.distribuida.model.Cliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class ClienteTestIntegracion {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void findAll(){
        List<Cliente> clientes = clienteRepository.findAll();

        assertNotNull(clientes); //Validamos si la lista de clientes tiene datos

        assertTrue(clientes.size() > 0);

        for (Cliente item: clientes){
            System.out.println(item.toString());
        }
    }

    //consulta
    @Test
    public void findOne(){
        Optional<Cliente> cliente =clienteRepository.findById(1);

        assertTrue(cliente.isPresent());
        assertEquals("Puro", cliente.orElse(null).getNombre());
        assertEquals("Hueso", cliente.orElse(null).getApellido());

        System.out.println(cliente.toString());

    }

    //guardado para esto siempre en ID se pone 0
    @Test
    public void save(){
        Cliente cliente = new Cliente(0,"1725222325","Andres",
                "Veloz","Gonzalo de Vera","0974878589","andresv@correo.com");
        Cliente clienteGuardado = clienteRepository.save(cliente);
        assertEquals("1725222325", clienteGuardado.getCedula());
        assertEquals("Andres", clienteGuardado.getNombre());
    }

    //metodo de actualización
    @Test
    public void update(){
        Optional<Cliente> cliente = clienteRepository.findById(40);

        assertNotNull(cliente.isPresent());

        cliente.orElse(null).setCedula("0125352645");
        cliente.orElse(null).setNombre("Juan");
        cliente.orElse(null).setApellido("Romero");
        cliente.orElse(null).setDireccion("Direccion777");
        cliente.orElse(null).setTelefono("0981777777");
        cliente.orElse(null).setCorreo("romerojuan5@gmail.com");

        //Actualización
        Cliente clienteActrualizado = clienteRepository.save(cliente.orElse(null));
        assertEquals("Juan", clienteActrualizado.getNombre());
        assertEquals("Direccion777", clienteActrualizado.getDireccion());

    }

    //metodo de borrado
    @Test
    public void deleta(){

        if(clienteRepository.existsById(40)){
            clienteRepository.deleteById(40);
        }
        assertFalse(clienteRepository.existsById(40), "El dato fue elimidado");

    }
}
