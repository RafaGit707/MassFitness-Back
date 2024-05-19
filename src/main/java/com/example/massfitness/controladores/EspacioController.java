package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Espacio;
import com.example.massfitness.servicios.EspacioService;
import com.example.massfitness.servicios.impl.IEspacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/massfitness/espacios")
public class EspacioController {

    private final IEspacioService iEspacioService;
    @Autowired
    public EspacioController(IEspacioService iEspacioService) {
        this.iEspacioService = iEspacioService;
    }

    @PostMapping
    public void addEspacio(@RequestBody Espacio espacio) {
        iEspacioService.addEspacio(espacio);
    }

    @GetMapping("/{id}")
    public Espacio getEspacioPorId(@PathVariable int id) {
        return iEspacioService.buscarEspacioPorId(id);
    }

    @GetMapping
    public List<Espacio> getEspacios() {
        return iEspacioService.getEspacios();
    }

    @PutMapping("/{id}")
    public void actualizarEspacio(@PathVariable int id, @RequestBody Espacio espacio) {
        espacio.setIdEspacio(id);
        iEspacioService.actualizarEspacio(espacio);
    }

    @DeleteMapping("/{id}")
    public void eliminarEspacio(@PathVariable int id) {
        iEspacioService.eliminarEspacio(id);
    }
}
