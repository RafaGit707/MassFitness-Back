package com.example.massfitness.servicios.impl;

import com.example.massfitness.entidades.Clases;

import java.util.List;

public interface IClaseService {
    Clases addClase(Clases clase);
    void actualizarClase(Clases clase);
    void eliminarClase(int idClase);
    List<Clases> getClases();
    Clases buscarClasePorId(int idClase);
}
