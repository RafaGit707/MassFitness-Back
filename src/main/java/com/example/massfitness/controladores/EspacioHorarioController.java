package com.example.massfitness.controladores;

import com.example.massfitness.servicios.impl.IEspacioHorarioService;
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
@RequestMapping("/massfitness/espacio_horario")
public class EspacioHorarioController {
    private final IEspacioHorarioService iEspacioHorarioService;
    @Autowired
    public EspacioHorarioController(IEspacioHorarioService iEspacioHorarioService) {
        this.iEspacioHorarioService = iEspacioHorarioService;
    }

    @GetMapping("/{salaNombre}/{horarioReserva}")
    public ResponseEntity<Map<String, Integer>> obtenerCapacidad(@PathVariable String salaNombre, @PathVariable String horarioReserva) {
        try {
            Timestamp timestamp = Timestamp.valueOf(horarioReserva + ":00");
            int capacidadActual = iEspacioHorarioService.obtenerCapacidadActual(salaNombre, timestamp);
            int capacidadMaxima = iEspacioHorarioService.obtenerCapacidadMaxima(salaNombre);
            Map<String, Integer> response = new HashMap<>();
            response.put("capacidad_actual", capacidadActual);
            response.put("capacidad_maxima", capacidadMaxima);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
