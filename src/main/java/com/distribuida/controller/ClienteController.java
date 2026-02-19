package com.distribuida.controller;

import com.distribuida.model.Cliente;
import com.distribuida.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")//pag de navegación(url)

public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll(){

        List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);

    }

    @GetMapping("/{id}")//Trae informacion desde la app
    public ResponseEntity<Cliente> findOne(@PathVariable int id){

        Optional<Cliente> cliente = clienteService.findOne(id);
        if(cliente == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente.orElse(null));

    }

    @PostMapping//envía informacion desde la app
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
        Cliente cliente1 = clienteService.save(cliente);
        return ResponseEntity.ok(cliente1);
    }

    @PostMapping("/{id}") // metodo de actualizado
    public ResponseEntity<Cliente> update(@PathVariable int id, @RequestBody Cliente cliente){
        Cliente clienteActualizado = clienteService.update(id, cliente);

        if(clienteActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")// metodo de eliminación
    public ResponseEntity<Void> delete(@PathVariable int id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
