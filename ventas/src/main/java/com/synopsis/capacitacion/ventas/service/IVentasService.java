package com.synopsis.capacitacion.ventas.service;

import java.util.List;

import com.synopsis.capacitacion.ventas.entity.Ventas;
import com.synopsis.capacitacion.ventas.entity.VentasDTO;

public interface IVentasService {

    List<Ventas> getAllVentas();

    void GuardarVenta(VentasDTO request);
}
