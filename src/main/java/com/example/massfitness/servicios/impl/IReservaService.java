package com.example.massfitness.servicios.impl;

import com.example.massfitness.entidades.Reserva;

import java.sql.Timestamp;
import java.util.List;

public interface IReservaService {
    List<Reserva> getReservas();
    int addReserva(Integer usuarioId, Integer espacioId, String tipoReserva, Timestamp horarioReserva, String estadoReserva);
    void actualizarReserva(Reserva reserva);
    void eliminarReserva(int idReserva);
    Reserva buscarReservaPorId(int idReserva);
}
