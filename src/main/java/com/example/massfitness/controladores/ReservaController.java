package com.example.massfitness.controladores;

import com.example.massfitness.entidades.Reserva;
import com.example.massfitness.servicios.ReservaService;
import com.example.massfitness.servicios.UsuarioService;
import com.example.massfitness.servicios.impl.IReservaService;
import com.example.massfitness.servicios.impl.IUsuarioService;
import com.example.massfitness.util.AccesoBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/massfitness/reservas")
public class ReservaController {
    private final IReservaService iReservaService;
    @Autowired
    public ReservaController(IReservaService iReservaService) {
        this.iReservaService = iReservaService;
    }

    @PostMapping
    public void addReserva(@RequestBody Reserva reserva) {
        iReservaService.addReserva(reserva);
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

    @GetMapping
    public List<Reserva> getReservas() {
        return iReservaService.getReservas();
    }
    @GetMapping("/usuario/{idUsuario}")
    public List<Reserva> getReservasPorUsuario(@PathVariable int idUsuario) {
        List<Reserva> reservas = iReservaService.getReservas();
        return reservas.stream().filter(reserva -> reserva.getUsuario().getIdUsuario() == idUsuario).collect(Collectors.toList());
    }

}
