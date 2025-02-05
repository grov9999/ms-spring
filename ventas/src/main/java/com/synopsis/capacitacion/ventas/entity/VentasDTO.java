package com.synopsis.capacitacion.ventas.entity;

public class VentasDTO {

    private String cantidad;
    private String fecha;
    private String total;
    private String metodopago;

    // Getters y setters
    public String getCantidad() {
        return cantidad;
    }
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public String getMetodopago() {
        return metodopago;
    }
    public void setMetodopago(String metodopago) {
        this.metodopago = metodopago;
    }    

}
