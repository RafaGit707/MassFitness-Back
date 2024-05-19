package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Entrenador;
import com.example.massfitness.servicios.EntrenadorService;
import com.example.massfitness.servicios.impl.IEntrenadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/massfitness/entrenadores")
public class EntrenadorController {

    private final IEntrenadorService iEntrenadorService;

    public EntrenadorController(IEntrenadorService iEntrenadorService) {
        this.iEntrenadorService = iEntrenadorService;
    }

    @PostMapping
    public void addEntrenador(@RequestBody Entrenador entrenador) {
        iEntrenadorService.addEntrenador(entrenador);
    }

    @GetMapping("/{id}")
    public Entrenador getEntrenadorPorId(@PathVariable int id) {
        return iEntrenadorService.buscarEntrenadorPorId(id);
    }

    @GetMapping
    public List<Entrenador> getEntrenadores() {
        return iEntrenadorService.getEntrenadores();
    }

    @PutMapping("/{id}")
    public void actualizarEntrenador(@PathVariable int id, @RequestBody Entrenador entrenador) {
        entrenador.setIdEntrenador(id);
        iEntrenadorService.actualizarEntrenador(entrenador);
    }

    @DeleteMapping("/{id}")
    public void eliminarEntrenador(@PathVariable int id) {
        iEntrenadorService.eliminarEntrenador(id);
    }
}