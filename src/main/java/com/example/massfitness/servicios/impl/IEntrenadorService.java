package com.example.massfitness.servicios.impl;

import com.example.massfitness.entidades.Entrenador;

import java.util.List;

public interface IEntrenadorService {
    Entrenador addEntrenador(Entrenador entrenador);
/*    void actualizarEntrenador(Entrenador entrenador);*/
Entrenador actualizarEntrenador(Entrenador entrenador);

    void eliminarEntrenador(int idEntrenador);
    List<Entrenador> getEntrenadores();
    Entrenador buscarEntrenadorPorId(int idEntrenador);
}
