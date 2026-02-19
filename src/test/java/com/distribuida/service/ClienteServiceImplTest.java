package com.distribuida.service;


import com.distribuida.dao.ClienteRepository;
import com.distribuida.dao.ClienteTestIntegracion;
import com.distribuida.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import java.util.List;
import java.util.Optional;

//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
//import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;
    private Cliente cliente;

    @BeforeEach
    void setUp(){
        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setCedula("17981529283");
        cliente.setNombre("Daniel");
        cliente.setApellido("Romero");
        cliente.setDireccion("Av. 5 de diciembre");
        cliente.setTelefono("0998195060");
        cliente.setCorreo("dinelromer5@gmail.com");
    }

    @Test
    public void testfindAll(){
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        List<Cliente> clientes = clienteService.findAll();

        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        //verify(clienteRepository, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente(){
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.findOne(1);

        assertNotNull(resultado);
        assertEquals("Daniel",resultado.orElse(null).getNombre());
    }

    @Test
    public void testFindOneNoexistente(){
        when(clienteRepository.findById(2)).thenReturn(null);

        Optional<Cliente> resultado = clienteService.findOne(2);

        assertNull(resultado);

    }

    @Test
    public void testSave(){
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente cliente1 = clienteService.save(cliente);

        assertNotNull(cliente1);
        assertEquals("Daniel", cliente1.getNombre());
    }

    @Test
    public void testUpdateExistente(){
        Cliente clienteActualizado = new Cliente();

        clienteActualizado.setCedula("17981529282");
        clienteActualizado.setNombre("Danny");
        clienteActualizado.setApellido("Ruiz");
        clienteActualizado.setDireccion("Direccion 2");
        clienteActualizado.setTelefono("0999853562");
        clienteActualizado.setCorreo("dannyruiz23@gmail.com");

        when(clienteRepository.findById(1)).thenReturn(Optional.ofNullable(cliente));
        when(clienteRepository.save(any())).thenReturn(clienteActualizado);

        Cliente resultado = clienteService.update(1,clienteActualizado);

        assertNotNull(resultado);
        assertEquals("Danny", resultado.getNombre());
        //verity(clienteRepository, times(1)).save(cliente);

    }

    @Test
    public void testUpdateNoExistente(){
        Cliente clienteNuevo = new Cliente();
        when(clienteRepository.findById(999)).thenReturn(Optional.empty());
        Cliente resultado = clienteService.update(999,clienteNuevo);
        assertNull(resultado);
        verify(clienteRepository, never()).save(any());

    }

    @Test
    public void testDeleteExistente(){
        when(clienteRepository.existsById(1)).thenReturn(true);
        clienteService.delete(1);
        verify(clienteRepository).deleteById(1);
    }

    @Test
    public void testDeleteNoExistente (){
        when(clienteRepository.existsById(999)).thenReturn(false);
        clienteService.delete(999);
        verify(clienteRepository, never()).deleteById(anyInt());
    }



}
