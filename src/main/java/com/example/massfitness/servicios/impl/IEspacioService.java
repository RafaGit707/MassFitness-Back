package com.example.massfitness.servicios.impl;

import com.example.massfitness.entidades.Espacio;

import java.util.List;

public interface IEspacioService {
    void addEspacio(Espacio espacio);
    void actualizarEspacio(Espacio espacio);
    void eliminarEspacio(int idEspacio);
    List<Espacio> getEspacios();
    Espacio buscarEspacioPorId(int idEspacio);
}
