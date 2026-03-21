package com.distribuida.service;

import com.distribuida.dao.ClienteRepository;
import com.distribuida.dao.FacturaRepository;
import com.distribuida.dto.CheckoutRequest;
import com.distribuida.model.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CheckoutServiceImpl — Implementación del servicio de checkout.
 *
 * Responsabilidades:
 *  - Delegar el proceso de pago a CarritoService.checkout()
 *  - Consultar el historial de facturas de un cliente
 *  - Recuperar una factura individual por ID
 *
 * La separación de CheckoutService respecto a CarritoService permite
 * aplicar el principio de responsabilidad única (SRP):
 *  - CarritoService: gestiona el estado del carrito (agregar, eliminar, vaciar)
 *  - CheckoutService: gestiona el proceso de pago y consulta de facturas
 */
@Service
@Transactional
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // ── Procesar checkout ─────────────────────────────────────────────────────
    @Override
    public Factura procesarCheckout(String token, CheckoutRequest request) {
        return carritoService.checkout(token, request);
    }

    // ── Obtener factura por ID ────────────────────────────────────────────────
    @Override
    public Optional<Factura> obtenerFactura(int idFactura) {
        return facturaRepository.findById(idFactura);
    }

    // ── Historial de facturas por cliente ─────────────────────────────────────
    @Override
    public List<Factura> obtenerHistorialFacturas(int idCliente) {
        return clienteRepository.findById(idCliente)
                .map(cliente -> facturaRepository.findByCliente(cliente))
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + idCliente));
    }
}
