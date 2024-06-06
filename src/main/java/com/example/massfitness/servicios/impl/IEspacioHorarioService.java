package com.example.massfitness.servicios.impl;

public interface IEspacioHorarioService {
    int obtenerCapacidadActual(String salaNombre, String horarioReserva);
    int obtenerCapacidadMaxima(String salaNombre);
}
