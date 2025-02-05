package com.synopsis.capacitacion.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synopsis.capacitacion.ventas.entity.Ventas;

@Repository
public interface VentasRepository extends JpaRepository<Ventas, Long>{

}
