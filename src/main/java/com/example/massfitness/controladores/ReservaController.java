package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Reserva;
import com.example.massfitness.servicios.EspacioService;
import com.example.massfitness.servicios.ReservaService;
import com.example.massfitness.servicios.UsuarioService;
import com.example.massfitness.servicios.impl.IReservaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/massfitness/reservas")
public class ReservaController {
    @Autowired
    private final IReservaService iReservaService;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EspacioService espacioService;
    @Autowired
    private ReservaService reservaService;

    @Autowired
    public ReservaController(IReservaService iReservaService) {
        this.iReservaService = iReservaService;
    }
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    @PostMapping("/addReserva")
    public ResponseEntity<Integer> addReserva(@RequestParam("usuario_id") Integer usuarioId,
                                              @RequestParam("espacio_id") Integer espacioId,
                                              @RequestParam("tipo_reserva") String tipoReserva,
                                              @RequestParam("horario_reserva") Timestamp horarioReserva,
                                              @RequestParam("estado_reserva") String estadoReserva) {

        logger.info("Añadiendo reserva: Usuario ID = {}, Espacio ID = {}, Tipo = {}, Horario = {}, Estado = {}",
                usuarioId, espacioId, tipoReserva, horarioReserva, estadoReserva);
        int idReserva = iReservaService.addReserva(usuarioId, espacioId, tipoReserva, horarioReserva, estadoReserva);
        return ResponseEntity.ok(idReserva);
    }
    @GetMapping
    public ResponseEntity<List<Reserva>> getReservas() {
        List<Reserva> reservas = iReservaService.getReservas();
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public Reserva getReservaPorId(@PathVariable int id) {
        return iReservaService.buscarReservaPorId(id);
    }

    @PutMapping("/{id}")
    public void actualizarReserva(@PathVariable int id, @RequestBody Reserva reserva) {
        reserva.setIdReserva(id);
        iReservaService.actualizarReserva(reserva);
    }
    @DeleteMapping("/{id}")
    public void eliminarReserva(@PathVariable int id) {
        iReservaService.eliminarReserva(id);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Reserva> getReservasPorUsuario(@PathVariable int idUsuario) {
        List<Reserva> reservas = iReservaService.getReservas();
        return reservas.stream().filter(reserva -> reserva.getUsuarioId() == idUsuario).collect(Collectors.toList());
    }

}
