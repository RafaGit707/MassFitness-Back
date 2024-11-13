package com.example.massfitness.servicios.impl;

import java.sql.Timestamp;

public interface IReservaClaseService {
    int obtenerCapacidadActual(String salaNombre, Timestamp horarioReserva);
    int obtenerCapacidadMaxima(String salaNombre);
}
