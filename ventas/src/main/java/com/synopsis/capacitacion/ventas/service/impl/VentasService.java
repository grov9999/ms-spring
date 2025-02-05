package com.synopsis.capacitacion.ventas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.synopsis.capacitacion.ventas.entity.Ventas;
import com.synopsis.capacitacion.ventas.entity.VentasDTO;
import com.synopsis.capacitacion.ventas.entity.VentasResponse;
import com.synopsis.capacitacion.ventas.repository.VentasRepository;
import com.synopsis.capacitacion.ventas.service.IVentasService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VentasService implements IVentasService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @Autowired
    private VentasRepository ventasRepository;

    @Override
    public List<Ventas> getAllVentas() {
        return (List<Ventas>) ventasRepository.findAll();
    }

    public void GuardarVenta(VentasDTO request) {
        log.info("Cantidad" + request.getCantidad());
        log.info("Fecha" + request.getFecha());
        log.info("Total" + request.getTotal());
        log.info("Metodo de Pago" + request.getMetodopago());
        VentasResponse response = restTemplate.postForObject(env.getProperty("endpoints.ms-ventas.base-path"), request,
                VentasResponse.class);

        Ventas venta = new Ventas();
        venta.setMensaje(response.getMensajeFormat());
        ventasRepository.save(venta);
    }

}
