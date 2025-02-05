package com.synopsis.capacitacion.ventas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synopsis.capacitacion.ventas.entity.Ventas;
import com.synopsis.capacitacion.ventas.entity.VentasDTO;
import com.synopsis.capacitacion.ventas.service.IVentasService;

@RestController
@RequestMapping("/ventas")
public class VentasController {

    @Autowired
    private IVentasService iVentasService;

    // Listar ventas
    @GetMapping("/listar")
    public List<Ventas> list() {
        return iVentasService.getAllVentas();
    }

    // Insertar ventas
    @PostMapping("/insertar")
    public ResponseEntity<?> save(@RequestBody VentasDTO input) {
        iVentasService.GuardarVenta(input);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
