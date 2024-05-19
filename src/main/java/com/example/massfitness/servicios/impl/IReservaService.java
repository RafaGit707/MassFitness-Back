package com.example.massfitness.servicios.impl;

import com.example.massfitness.entidades.Reserva;

import java.util.List;

public interface IReservaService {
    List<Reserva> getReservas();
    void addReserva(Reserva reserva);
    void actualizarReserva(Reserva reserva);
    void eliminarReserva(int idReserva);
    Reserva buscarReservaPorId(int idReserva);
}
