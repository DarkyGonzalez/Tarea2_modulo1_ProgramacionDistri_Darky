package com.distribuida.model;

public class FacturaDetalle {

    //atributos
    private int idFacturaDetalle;
    private int cantidad;
    private Double subtotal;

    //inyección de dependencias
    private Factura factura;
    private Libro libro;

    //Constructor vacio
    public FacturaDetalle( ){ }

    //Constructor con parámetros (metodo inyector por constructor)
    public FacturaDetalle(int idFacturaDetalle, int cantidad, Double subtotal, Factura factura, Libro libro) {
        this.idFacturaDetalle = idFacturaDetalle;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.factura = factura;
        this.libro = libro;
    }

    //Getter and Setter
    public int getIdFacturaDetalle() {
        return idFacturaDetalle;
    }

    public void setIdFacturaDetalle(int idFacturaDetalle) {
        this.idFacturaDetalle = idFacturaDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Factura getFactura() {
        return factura;
    }
    //metodo inyector por setters
    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Libro getLibro() {
        return libro;
    }
    //metodo inyector por setters
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    //toString

    @Override
    public String toString() {
        return "FacturaDetalle{" +
                "idFacturaDetalle=" + idFacturaDetalle +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                ", factura=" + factura +
                ", libro=" + libro +
                '}';
    }
}
