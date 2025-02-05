package com.synopsis.capacitacion.ventas.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentasResponse {
    private Long id;
    private String cantidad;
    private String fecha;
    private String total;
    private String metodopago;
    public String getMensajeFormat() {
        return "{"+"id= " +id+ ",cantidad= " +cantidad+ ", fecha= " +fecha+ ",total= " +total+ ",metodopago= " +metodopago+ "}";
    }
}
