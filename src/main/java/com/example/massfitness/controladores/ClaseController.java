package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Clases;
import com.example.massfitness.servicios.impl.IClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/massfitness/clases")
public class ClaseController {

    private final IClaseService iClaseService;

    @Autowired
    public ClaseController(IClaseService iClaseService) {
        this.iClaseService = iClaseService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Clases> agregarClase(@RequestBody Clases clase) {
        Clases nuevaClase = iClaseService.addClase(clase);
        return ResponseEntity.ok(nuevaClase);
    }

    @GetMapping("/{id}")
    public Clases getClasePorId(@PathVariable int id) {
        return iClaseService.buscarClasePorId(id);
    }

    @GetMapping
    public List<Clases> getClases() {
        return iClaseService.getClases();
    }

    @PutMapping("/{id}")
    public void actualizarClase(@PathVariable int id, @RequestBody Clases clase) {
        clase.setIdClase(id);
        iClaseService.actualizarClase(clase);
    }

    @DeleteMapping("/{id}")
    public void eliminarClase(@PathVariable int id) {
        iClaseService.eliminarClase(id);
    }
}