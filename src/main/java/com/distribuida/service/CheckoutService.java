package com.distribuida.service;

import com.distribuida.dto.CheckoutRequest;
import com.distribuida.model.Factura;

import java.util.List;
import java.util.Optional;

/**
 * CheckoutService — Interface del servicio de checkout del módulo carrito.
 *
 * Define las operaciones disponibles para procesar compras y consultar
 * el historial de facturas generadas a partir del carrito de compras.
 */
public interface CheckoutService {

    /**
     * Procesa el checkout del carrito identificado por el token,
     * genera una Factura y vacía el carrito.
     */
    Factura procesarCheckout(String token, CheckoutRequest request);

    /**
     * Obtiene una factura por su ID.
     */
    Optional<Factura> obtenerFactura(int idFactura);

    /**
     * Obtiene el historial de facturas de un cliente.
     */
    List<Factura> obtenerHistorialFacturas(int idCliente);
}
