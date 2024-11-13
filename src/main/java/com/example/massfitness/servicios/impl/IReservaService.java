package com.example.massfitness.servicios.impl;

import com.example.massfitness.entidades.Reserva;

import java.sql.Timestamp;
import java.util.List;

public interface IReservaService {
    List<Reserva> getReservas();
    int addReservaEspacio(Integer usuarioId, Integer espacioId, String tipoReserva, Timestamp horarioReserva, String estadoReserva);
    int addReservaClase(Integer usuarioId, Integer espacioId, String tipoReserva, Timestamp horarioReserva, String estadoReserva);
    void actualizarReserva(Reserva reserva);
    void eliminarReservaEspacio(int idReserva);
    void eliminarReservaClase(int idReserva);
    Reserva buscarReservaPorId(int idReserva);
}
