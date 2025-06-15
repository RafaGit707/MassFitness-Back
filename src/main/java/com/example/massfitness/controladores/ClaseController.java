package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Clases;
import com.example.massfitness.servicios.impl.IClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public Clases getClasePorId(@PathVariable int id) {
        return iClaseService.buscarClasePorId(id);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<Clases> getClases() {
        return iClaseService.getClases();
    }
/*    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public void actualizarClase(@PathVariable int id, @RequestBody Clases clase) {
        clase.setIdClase(id);
        iClaseService.actualizarClase(clase);
    }*/

/*    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarClase( // Cambiado a ResponseEntity para dar feedback
                                                 @PathVariable int id, // El ID de la clase a actualizar viene de la URL
                                                 @RequestParam(name="nombre") String nombre, // Recibe 'nombre' como parámetro de formulario
                                                 @RequestParam(name="capacidad_maxima") int capacidadMaxima, // Recibe 'capacidad_maxima'
                                                 @RequestParam(name = "entrenador_id", required = false) String entrenadorIdStr // Recibe 'entrenador_id', puede ser vacío o no venir
    ) {
        try {
            // Llamamos al método del servicio que sabe manejar estos parámetros
            boolean actualizado = iClaseService.actualizarClase(id, nombre, capacidadMaxima, entrenadorIdStr);

            if (actualizado) {
                return ResponseEntity.ok().build(); // HTTP 200 OK si se actualizó
            } else {
                return ResponseEntity.notFound().build(); // HTTP 404 si no se encontró la clase
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build(); // HTTP 400 si entrenador_id no es número válido
        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar clase: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 Error interno
        } catch (Exception e) { // Captura genérica por si acaso
            System.err.println("Error inesperado al actualizar clase: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Clases> actualizarClase(@PathVariable int id, @RequestBody Clases clase) {
        clase.setIdClase(id);
        Clases actualizada = iClaseService.actualizarClase(clase);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarClase(@PathVariable int id) {
        iClaseService.eliminarClase(id);
    }
}