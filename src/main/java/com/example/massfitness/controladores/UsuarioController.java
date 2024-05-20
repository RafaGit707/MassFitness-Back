package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Usuario;
import com.example.massfitness.servicios.impl.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/massfitness/usuarios")
public class UsuarioController {
    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());


    private final IUsuarioService iUsuarioService;
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
    public ResponseEntity<Void> agregarUsuario(@RequestBody Usuario usuario) {
        try {
            iUsuarioService.addUsuario(usuario);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @GetMapping("/correo/{correoElectronico}")
    public ResponseEntity<Usuario> obtenerUsuarioPorCorreo(@PathVariable String correoElectronico) {
        Usuario usuario = iUsuarioService.buscarUsuarioPorCorreo(correoElectronico);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/existe")
    public ResponseEntity<Boolean> usuarioExiste(@RequestParam String correoElectronico, @RequestParam String contrasena) {
        boolean existe = iUsuarioService.usuarioExiste(correoElectronico, contrasena);
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
