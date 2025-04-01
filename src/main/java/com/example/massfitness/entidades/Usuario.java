package com.example.massfitness.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    @JsonProperty("nombre")
    @Column(name = "nombre")
    private String nombre;
    @JsonProperty("correo_electronico")
    private String correo_electronico;
    @JsonProperty("contrasena")
    private String contrasena;
    @JsonProperty("cantidad_puntos")
    private int cantidadPuntos;
    @JsonProperty("rol")
    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.USUARIO;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Set<Reserva> reservas;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Set<UsuarioLogro> logros;

    public Usuario() {
    }

    public Usuario(String nombre, String correo_electronico, String contrasena,int cantidadPuntos, Rol rol, Set<Reserva> reservas, Set<UsuarioLogro> logros) {
        this.nombre = nombre;
        this.correo_electronico = correo_electronico;
        this.contrasena = contrasena;
        this.cantidadPuntos = cantidadPuntos;
        this.rol = rol;
        this.reservas = reservas;
        this.logros = logros;
    }

    public Usuario(int idUsuario, String nombre, String correo_electronico, String contrasena, int cantidadPuntos, Rol rol, Set<Reserva> reservas, Set<UsuarioLogro> logros) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo_electronico = correo_electronico;
        this.contrasena = contrasena;
        this.cantidadPuntos = cantidadPuntos;
        this.rol = rol;
        this.reservas = new HashSet<>();
        this.logros = new HashSet<>();
    }

    public Usuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getCantidadPuntos() {
        return cantidadPuntos;
    }

    public void setCantidadPuntos(int cantidadPuntos) {
        this.cantidadPuntos = cantidadPuntos;
    }

    public Set<UsuarioLogro> getLogros() {
        return logros;
    }

    public void setLogros(Set<UsuarioLogro> logros) {
        this.logros = logros;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }
    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
