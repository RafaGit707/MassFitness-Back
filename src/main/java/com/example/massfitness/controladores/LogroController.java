package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Logro;
import com.example.massfitness.entidades.Usuario;
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

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    public ResponseEntity<Integer> addUsuarioLogro(@RequestBody UsuarioLogro usuarioLogro) {
        logger.info("Añadiendo Logro: Usuario ID = {}, logroId ID = {}, fechaObtenido = {}", usuarioLogro);
        int idCreado = iLogroService.addUsuarioLogro(usuarioLogro);
        return new ResponseEntity<>(idCreado, HttpStatus.CREATED);
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