package com.example.massfitness.servicios.impl;

import com.example.massfitness.entidades.Clase;

import java.util.List;

public interface IClaseService {
    void addClase(Clase clase);

    List<Clase> getClases();

    Clase buscarClasePorId(int idClase);

    void actualizarClase(Clase clase);

    void eliminarClase(int idClase);
}
