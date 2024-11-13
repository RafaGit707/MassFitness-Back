package com.example.massfitness.servicios.impl;

import com.example.massfitness.entidades.Usuario;

import java.util.List;

public interface IUsuarioService {
    int addUsuario(Usuario usuario);
    List<Usuario> getUsuarios();
    void actualizarUsuario(Usuario usuario);
    void eliminarUsuario(int idUsuario);
    Usuario buscarUsuarioPorId(int idUsuario);
    Usuario buscarUsuarioPorCorreo(String correo_electronico);
    boolean usuarioExiste(String correo_electronico, String contrasena);
    int getCantidadPuntosUsuario(int idUsuario);
    void actualizarCantidadPuntosUsuario(int idUsuario, int nuevaCantidadPuntos);
}
