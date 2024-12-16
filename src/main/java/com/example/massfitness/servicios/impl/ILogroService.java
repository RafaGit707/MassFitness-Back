package com.example.massfitness.servicios.impl;

import com.example.massfitness.entidades.Logro;

import java.util.List;

public interface ILogroService {
    void addLogro(Logro logro);
    void actualizarLogro(Logro logro);
    void eliminarLogro(int idLogro);
    List<Logro> getLogros();
    List<Logro> getLogrosByUserId(int idUsuario);
}
