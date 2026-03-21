package com.distribuida.mapper;

import com.distribuida.dao.FacturaDetalleRepository;
import com.distribuida.dao.FacturaRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.dto.CheckoutRequest;
import com.distribuida.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * CheckoutMapper — convierte un Carrito + CheckoutRequest en una Factura
 * persistida con sus FacturaDetalle.
 *
 * Responsabilidades:
 *  1. Construir la cabecera de la Factura (número, fecha, totales, cliente).
 *  2. Recorrer los ítems del carrito y generar cada FacturaDetalle.
 *  3. Persistir la Factura y sus detalles usando los repositorios inyectados.
 */
@Component
public class CheckoutMapper {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private FacturaDetalleRepository facturaDetalleRepository;

    @Autowired
    private LibroRepository libroRepository;

    /**
     * Convierte el carrito activo en una Factura persistida.
     *
     * @param carrito  Carrito con ítems cargados.
     * @param request  Datos adicionales del checkout (idCliente, metodoPago).
     * @return         Factura creada y almacenada en base de datos.
     */
    public Factura convertir(Carrito carrito, CheckoutRequest request) {

        // ── Cabecera de factura ───────────────────────────────────────────────
        Factura factura = new Factura();
        factura.setNumFactura("FAC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        factura.setFecha(new Date());
        factura.setCliente(carrito.getCliente());

        double subtotal = carrito.getSubtotal() != null
                ? carrito.getSubtotal().doubleValue() : 0.0;
        double impuestos = carrito.getImpuestos() != null
                ? carrito.getImpuestos().doubleValue() : 0.0;
        double total = carrito.getTotal() != null
                ? carrito.getTotal().doubleValue() : 0.0;

        factura.setTotalNeto(subtotal);
        factura.setIva(impuestos);
        factura.setTotal(total);

        Factura facturaGuardada = facturaRepository.save(factura);

        // ── Detalles de factura ───────────────────────────────────────────────
        for (CarritoItem item : carrito.getItems()) {
            Libro libro = libroRepository.findById(item.getIdLibro())
                    .orElseThrow(() -> new RuntimeException(
                            "Libro no encontrado al generar factura: " + item.getIdLibro()));

            FacturaDetalle detalle = new FacturaDetalle();
            detalle.setFactura(facturaGuardada);
            detalle.setLibro(libro);
            detalle.setCantidad(item.getCantidad());
            detalle.setSubtotal(item.getTotal() != null
                    ? item.getTotal().doubleValue() : 0.0);

            facturaDetalleRepository.save(detalle);
        }

        return facturaGuardada;
    }
}
