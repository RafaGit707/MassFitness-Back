package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Entrenador;
import com.example.massfitness.servicios.EntrenadorService;
import com.example.massfitness.servicios.impl.IEntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/massfitness/entrenadores")
public class EntrenadorController {

    private final IEntrenadorService iEntrenadorService;
    @Autowired
    public EntrenadorController(IEntrenadorService iEntrenadorService) {
        this.iEntrenadorService = iEntrenadorService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Entrenador> agregarEntrenador(@RequestBody Entrenador entrenador) {
        Entrenador nuevoEntrenador = iEntrenadorService.addEntrenador(entrenador);
        return ResponseEntity.ok(nuevoEntrenador);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public Entrenador getEntrenadorPorId(@PathVariable int id) {
        return iEntrenadorService.buscarEntrenadorPorId(id);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<Entrenador> getEntrenadores() {
        return iEntrenadorService.getEntrenadores();
    }
/*    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public void actualizarEntrenador(@PathVariable int id, @RequestBody Entrenador entrenador) {
        entrenador.setIdEntrenador(id);
        iEntrenadorService.actualizarEntrenador(entrenador);
    }*/

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Entrenador> actualizarEntrenador(@PathVariable int id, @RequestBody Entrenador entrenador) {
        entrenador.setIdEntrenador(id);
        Entrenador actualizado = iEntrenadorService.actualizarEntrenador(entrenador);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarEntrenador(@PathVariable int id) {
        iEntrenadorService.eliminarEntrenador(id);
    }
}