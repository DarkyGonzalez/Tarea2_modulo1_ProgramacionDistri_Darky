package com.distribuida.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrito")
@JsonIgnoreProperties({"hibernate.lazy.initializer", "handler"})
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Long idCarrito;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "descuento", precision = 12, scale = 2)
    private BigDecimal descuento;

    @Column(name = "impuestos", precision = 12, scale = 2)
    private BigDecimal impuestos;

    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL,
               orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CarritoItem> items = new ArrayList<>();

    // Constructores
    public Carrito() {}

    public Carrito(Long idCarrito, Cliente cliente, String token, BigDecimal subtotal,
                   BigDecimal descuento, BigDecimal impuestos, BigDecimal total,
                   LocalDateTime actualizadoEn) {
        this.idCarrito = idCarrito;
        this.cliente = cliente;
        this.token = token;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.impuestos = impuestos;
        this.total = total;
        this.actualizadoEn = actualizadoEn;
    }

    // Método de negocio: recalcular totales
    public void recomputarTotales(BigDecimal tasaIva) {
        this.subtotal = items.stream()
            .map(it -> it.getPrecioUnitario() != null
                       ? it.getPrecioUnitario().multiply(BigDecimal.valueOf(it.getCantidad()))
                       : BigDecimal.ZERO)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.impuestos = this.subtotal.multiply(tasaIva);
        this.total = this.subtotal.add(this.impuestos)
                        .subtract(this.descuento != null ? this.descuento : BigDecimal.ZERO);
        this.actualizadoEn = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getIdCarrito() { return idCarrito; }
    public void setIdCarrito(Long idCarrito) { this.idCarrito = idCarrito; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }

    public BigDecimal getImpuestos() { return impuestos; }
    public void setImpuestos(BigDecimal impuestos) { this.impuestos = impuestos; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }

    public List<CarritoItem> getItems() { return items; }
    public void setItems(List<CarritoItem> items) { this.items = items; }

    @Override
    public String toString() {
        return "Carrito{" + "idCarrito=" + idCarrito + ", token='" + token + '\'' +
               ", subtotal=" + subtotal + ", total=" + total + '}';
    }
}
