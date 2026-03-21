package com.distribuida.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "carrito_item")
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito_item")
    private Long idCarritoItem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_carrito")
    private Carrito carrito;

    @Column(name = "id_libro")
    private int idLibro;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "precio_unitario", precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;

    // Constructores
    public CarritoItem() {}

    public CarritoItem(Long idCarritoItem, Carrito carrito, int idLibro,
                       int cantidad, BigDecimal precioUnitario, BigDecimal total) {
        this.idCarritoItem = idCarritoItem;
        this.carrito = carrito;
        this.idLibro = idLibro;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    // Getters y Setters
    public Long getIdCarritoItem() { return idCarritoItem; }
    public void setIdCarritoItem(Long idCarritoItem) { this.idCarritoItem = idCarritoItem; }

    public Carrito getCarrito() { return carrito; }
    public void setCarrito(Carrito carrito) { this.carrito = carrito; }

    public int getIdLibro() { return idLibro; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    @Override
    public String toString() {
        return "CarritoItem{" + "idCarritoItem=" + idCarritoItem + ", idLibro=" + idLibro +
               ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + '}';
    }
}
