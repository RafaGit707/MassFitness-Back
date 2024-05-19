package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Usuario;
import com.example.massfitness.servicios.impl.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/massfitness/usuarios")
public class UsuarioController {

    private final IUsuarioService iUsuarioService;
    @Autowired
    public UsuarioController(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return iUsuarioService.getUsuarios();
    }

    @PostMapping("/addUsuario")
    public ResponseEntity<Integer> agregarUsuario(@RequestBody Usuario usuario) {
        iUsuarioService.addUsuario(usuario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable int id) {
        return iUsuarioService.buscarUsuarioPorId(id);
    }

    @PutMapping("/{id}")
    public void actualizarUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        if (usuario.getIdUsuario() == id) {
            iUsuarioService.actualizarUsuario(usuario);
        }
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable int id) {
        iUsuarioService.eliminarUsuario(id);
    }

    @GetMapping("/correo/{correoElectronico}")
    public Usuario obtenerUsuarioPorCorreo(@PathVariable String correoElectronico) {
        return iUsuarioService.buscarUsuarioPorCorreo(correoElectronico);
    }

    @GetMapping("/existe")
    public boolean usuarioExiste(@RequestParam String correoElectronico, @RequestParam String contrasena) {
        return iUsuarioService.usuarioExiste(correoElectronico, contrasena);
    }

    @GetMapping("/{id}/progreso-fitness")
    public int getProgresoFitnessUsuario(@PathVariable int id) {
        return iUsuarioService.getProgresoFitnessUsuario(id);
    }

    @PutMapping("/{id}/progreso-fitness")
    public void actualizarProgresoFitnessUsuario(@PathVariable int id, @RequestParam int nuevoProgresoFitness) {
        iUsuarioService.actualizarProgresoFitnessUsuario(id, nuevoProgresoFitness);
    }

    @GetMapping("/{id}/cantidad-puntos")
    public int getCantidadPuntosUsuario(@PathVariable int id) {
        return iUsuarioService.getCantidadPuntosUsuario(id);
    }

    @PutMapping("/{id}/cantidad-puntos")
    public void actualizarCantidadPuntosUsuario(@PathVariable int id, @RequestParam int nuevaCantidadPuntos) {
        iUsuarioService.actualizarCantidadPuntosUsuario(id, nuevaCantidadPuntos);
    }
}
