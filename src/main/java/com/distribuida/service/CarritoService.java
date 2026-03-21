package com.distribuida.service;

import com.distribuida.dto.AddItemRequest;
import com.distribuida.dto.CheckoutRequest;
import com.distribuida.dto.UpdateItemRequest;
import com.distribuida.model.Carrito;
import com.distribuida.model.Factura;

import java.util.Optional;

public interface CarritoService {

    Carrito obtenerOCrearCarrito(int idCliente);

    Optional<Carrito> obtenerCarrito(String token);

    Carrito agregarItem(String token, AddItemRequest request);

    Carrito actualizarItem(Long idCarritoItem, UpdateItemRequest request);

    Carrito eliminarItem(Long idCarritoItem);

    void vaciarCarrito(String token);

    Factura checkout(String token, CheckoutRequest request);
}
