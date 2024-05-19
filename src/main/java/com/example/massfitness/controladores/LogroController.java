package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Logro;
import com.example.massfitness.servicios.LogroService;
import com.example.massfitness.servicios.impl.ILogroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/massfitness/logros")
public class LogroController {

    private final ILogroService iLogroService;

    public LogroController() {
        ILogroService iLogroService = new LogroService();
        this.iLogroService = iLogroService;
    }

    @PostMapping
    public void addLogro(@RequestBody Logro logro) {
        iLogroService.addLogro(logro);
    }

    @GetMapping("/{id}")
    public Logro getLogroPorId(@PathVariable int id) {
        return iLogroService.buscarLogroPorId(id);
    }

    @GetMapping
    public List<Logro> getLogros() {
        return iLogroService.getLogros();
    }

    @PutMapping("/{id}")
    public void actualizarLogro(@PathVariable int id, @RequestBody Logro logro) {
        logro.setIdLogro(id);
        iLogroService.actualizarLogro(logro);
    }

    @DeleteMapping("/{id}")
    public void eliminarLogro(@PathVariable int id) {
        iLogroService.eliminarLogro(id);
    }
}