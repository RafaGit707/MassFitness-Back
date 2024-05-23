package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Usuario;
import com.example.massfitness.servicios.impl.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/massfitness/usuarios")
public class UsuarioController {
    private final IUsuarioService iUsuarioService;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    public UsuarioController(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> usuarios = iUsuarioService.getUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PostMapping("/addUsuario")
    public ResponseEntity<Integer> agregarUsuario(@RequestBody Usuario usuario) {
        int idCreado = iUsuarioService.addUsuario(usuario);
        return new ResponseEntity<>(idCreado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable int id) {
        Usuario usuario = iUsuarioService.buscarUsuarioPorId(id);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        if (usuario.getIdUsuario() == id) {
            iUsuarioService.actualizarUsuario(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable int id) {
        iUsuarioService.eliminarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/correo/{correo_electronico}")
    public ResponseEntity<Usuario> obtenerUsuarioPorCorreo(@PathVariable String correo_electronico) {
        Usuario usuario = iUsuarioService.buscarUsuarioPorCorreo(correo_electronico);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/existe")
    public ResponseEntity<Boolean> usuarioExiste(@RequestParam String correo_electronico, @RequestParam String contrasena) {
        boolean existe = iUsuarioService.usuarioExiste(correo_electronico, contrasena);
        return new ResponseEntity<>(existe, HttpStatus.OK);
    }

    @GetMapping("/{id}/progreso-fitness")
    public ResponseEntity<Integer> getProgresoFitnessUsuario(@PathVariable int id) {
        int progreso = iUsuarioService.getProgresoFitnessUsuario(id);
        return new ResponseEntity<>(progreso, HttpStatus.OK);
    }

    @PutMapping("/{id}/progreso-fitness")
    public ResponseEntity<Void> actualizarProgresoFitnessUsuario(@PathVariable int id, @RequestParam int nuevoProgresoFitness) {
        iUsuarioService.actualizarProgresoFitnessUsuario(id, nuevoProgresoFitness);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/cantidad-puntos")
    public ResponseEntity<Integer> getCantidadPuntosUsuario(@PathVariable int id) {
        int puntos = iUsuarioService.getCantidadPuntosUsuario(id);
        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @PutMapping("/{id}/cantidad-puntos")
    public ResponseEntity<Void> actualizarCantidadPuntosUsuario(@PathVariable int id, @RequestParam int nuevaCantidadPuntos) {
        iUsuarioService.actualizarCantidadPuntosUsuario(id, nuevaCantidadPuntos);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
