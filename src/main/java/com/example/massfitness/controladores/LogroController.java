package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Logro;
import com.example.massfitness.entidades.UsuarioLogro;
import com.example.massfitness.servicios.LogroService;
import com.example.massfitness.servicios.impl.ILogroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/massfitness/logros")
public class LogroController {

    private final ILogroService iLogroService;
    @Autowired
    public LogroController(ILogroService iLogroService) {
        this.iLogroService = iLogroService;
    }

    @PostMapping
    public void addLogro(@RequestBody Logro logro) {
        iLogroService.addLogro(logro);
    }

    @GetMapping("/{id}")
    public List<UsuarioLogro> getLogroPorUsuarioId(@PathVariable int id) {
        return iLogroService.getLogrosByUserId(id);
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