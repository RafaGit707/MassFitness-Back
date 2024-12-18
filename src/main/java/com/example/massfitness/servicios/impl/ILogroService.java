package com.example.massfitness.servicios.impl;

import com.example.massfitness.entidades.Logro;
import com.example.massfitness.entidades.UsuarioLogro;

import java.sql.Timestamp;
import java.util.List;

public interface ILogroService {
    void addLogro(Logro logro);
    void actualizarLogro(Logro logro);
    void eliminarLogro(int idLogro);
    List<Logro> getLogros();
    List<UsuarioLogro> getLogrosByUserId(int idUsuario);
    void addUsuarioLogro(int usuarioId, int logroId, Timestamp fechaObtenido);
    void removeUsuarioLogro(int usuarioId, int logroId);
}
