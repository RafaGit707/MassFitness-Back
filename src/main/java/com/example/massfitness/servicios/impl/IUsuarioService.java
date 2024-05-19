package com.example.massfitness.servicios.impl;

import com.example.massfitness.entidades.Usuario;

import java.util.List;

public interface IUsuarioService {
    void addUsuario(Usuario usuario);
    List<Usuario> getUsuarios();
    void actualizarUsuario(Usuario usuario);
    void eliminarUsuario(int idUsuario);
    Usuario buscarUsuarioPorId(int idUsuario);
    Usuario buscarUsuarioPorCorreo(String correoElectronico);
    boolean usuarioExiste(String correoElectronico, String contrasena);
    int getProgresoFitnessUsuario(int idUsuario);
    void actualizarProgresoFitnessUsuario(int idUsuario, int nuevoProgresoFitness);
    int getCantidadPuntosUsuario(int idUsuario);
    void actualizarCantidadPuntosUsuario(int idUsuario, int nuevaCantidadPuntos);
}
