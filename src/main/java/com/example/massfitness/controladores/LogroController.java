package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Logro;
import com.example.massfitness.entidades.UsuarioLogro;
import com.example.massfitness.servicios.LogroService;
import com.example.massfitness.servicios.UsuarioService;
import com.example.massfitness.servicios.impl.ILogroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/massfitness/logros")
public class LogroController {

    private final ILogroService iLogroService;
    private static final Logger logger = LoggerFactory.getLogger(LogroService.class);
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
    @PostMapping("/addLogro/{usuarioId}/logro/{logroId}")
    public ResponseEntity<Integer> addUsuarioLogro(@PathVariable  int usuarioId,
                                @PathVariable int logroId,
                                @RequestParam Timestamp fechaObtenido) {
        logger.info("Añadiendo reserva: Usuario ID = {}, logroId ID = {}, fechaObtenido = {}",
                usuarioId, logroId, fechaObtenido);
        int idUsuarioLogro = iLogroService.addUsuarioLogro(usuarioId, logroId, fechaObtenido);
        return ResponseEntity.ok(idUsuarioLogro);
    }
    @DeleteMapping("/eliminarLogro/{usuarioId}/logro/{logroId}")
    public void removeUsuarioLogro(@PathVariable int usuarioId, @PathVariable int logroId) {
        iLogroService.removeUsuarioLogro(usuarioId, logroId);
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