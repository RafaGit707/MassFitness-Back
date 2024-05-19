package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Clase;
import com.example.massfitness.servicios.ClaseService;
import com.example.massfitness.servicios.ReservaService;
import com.example.massfitness.servicios.impl.IClaseService;
import com.example.massfitness.servicios.impl.IReservaService;
import com.example.massfitness.util.AccesoBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/massfitness/clases")
public class ClaseController {

    private final IClaseService iClaseService;

    public ClaseController() {
        IClaseService iClaseService = new ClaseService();
        this.iClaseService = iClaseService;
    }

    @PostMapping
    public void addClase(@RequestBody Clase clase) {
        iClaseService.addClase(clase);
    }

    @GetMapping("/{id}")
    public Clase getClasePorId(@PathVariable int id) {
        return iClaseService.buscarClasePorId(id);
    }

    @GetMapping
    public List<Clase> getClases() {
        return iClaseService.getClases();
    }

    @PutMapping("/{id}")
    public void actualizarClase(@PathVariable int id, @RequestBody Clase clase) {
        clase.setIdClase(id);
        iClaseService.actualizarClase(clase);
    }

    @DeleteMapping("/{id}")
    public void eliminarClase(@PathVariable int id) {
        iClaseService.eliminarClase(id);
    }
}
