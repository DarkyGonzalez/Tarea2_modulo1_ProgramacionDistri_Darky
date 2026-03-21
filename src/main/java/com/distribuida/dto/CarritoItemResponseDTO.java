package com.distribuida.dto;

import java.math.BigDecimal;

/**
 * DTO de respuesta para un ítem del carrito.
 * Evita exponer la entidad JPA directamente.
 */
public class CarritoItemResponseDTO {

    private Long idCarritoItem;
    private int idLibro;
    private String tituloLibro;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal total;

    public CarritoItemResponseDTO() {}

    public CarritoItemResponseDTO(Long idCarritoItem, int idLibro, String tituloLibro,
                                   int cantidad, BigDecimal precioUnitario, BigDecimal total) {
        this.idCarritoItem = idCarritoItem;
        this.idLibro = idLibro;
        this.tituloLibro = tituloLibro;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public Long getIdCarritoItem() { return idCarritoItem; }
    public void setIdCarritoItem(Long idCarritoItem) { this.idCarritoItem = idCarritoItem; }

    public int getIdLibro() { return idLibro; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }

    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}
