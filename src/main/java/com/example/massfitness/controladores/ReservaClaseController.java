package com.example.massfitness.controladores;

import com.example.massfitness.servicios.UsuarioService;
import com.example.massfitness.servicios.impl.IReservaClaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/massfitness/reserva_clase")
public class ReservaClaseController {

    private final IReservaClaseService iReservaClaseService;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    @Autowired
    public ReservaClaseController(IReservaClaseService iReservaClaseService) {
        this.iReservaClaseService = iReservaClaseService;
    }
    @GetMapping("/{salaNombre}/{horarioReserva}")
    public ResponseEntity<Map<String, Integer>> obtenerCapacidad(@PathVariable String salaNombre, @PathVariable String horarioReserva) {
        try {
            Timestamp timestamp = Timestamp.valueOf(horarioReserva + ":00");
            int capacidadActual = iReservaClaseService.obtenerCapacidadActual(salaNombre, timestamp);
            int capacidadMaxima = iReservaClaseService.obtenerCapacidadMaxima(salaNombre);
            logger.debug("Capacidad Actual: {}, Capacidad Maxima: {}", capacidadActual, capacidadMaxima);
            Map<String, Integer> response = new HashMap<>();
            response.put("capacidad_actual", capacidadActual);
            response.put("capacidad_maxima", capacidadMaxima);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}