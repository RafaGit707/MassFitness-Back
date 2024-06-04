package com.example.massfitness.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    @JsonProperty("correo_electronico")
    private String correo_electronico;
    private String contrasena;
    private int progresoFitness;
    private int cantidadPuntos;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "datos_personales_id")
    private DatosPersonales datos_personales;
    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_logro",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "logro_id"))
    private List<Logro> logrosConseguidos;

    public Usuario() {
    }

    public Usuario(String nombre, String correo_electronico, String contrasena, DatosPersonales datos_personales, int progresoFitness, int cantidadPuntos, List<Logro> logrosConseguidos) {
        this.nombre = nombre;
        this.correo_electronico = correo_electronico;
        this.contrasena = contrasena;
        this.datos_personales = datos_personales;
        this.progresoFitness = progresoFitness;
        this.cantidadPuntos = cantidadPuntos;
        this.logrosConseguidos = logrosConseguidos;
    }

    public Usuario(int id, String nombre, String correo_electronico, String contrasena, DatosPersonales datos_personales, int progresoFitness, int cantidadPuntos, List<Logro> logrosConseguidos) {
        this.id = id;
        this.nombre = nombre;
        this.correo_electronico = correo_electronico;
        this.contrasena = contrasena;
        this.datos_personales = datos_personales;
        this.progresoFitness = progresoFitness;
        this.cantidadPuntos = cantidadPuntos;
        this.logrosConseguidos = logrosConseguidos;
    }

    public Usuario(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return id;
    }

    public void setIdUsuario(int id) {
        this.id = id;
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

    public DatosPersonales getDatosPersonales() {
        return datos_personales;
    }

    public void setDatosPersonales(DatosPersonales datos_personales) {
        this.datos_personales = datos_personales;
    }

    public int getProgresoFitness() {
        return progresoFitness;
    }

    public void setProgresoFitness(int progresoFitness) {
        this.progresoFitness = progresoFitness;
    }

    public int getCantidadPuntos() {
        return cantidadPuntos;
    }

    public void setCantidadPuntos(int cantidadPuntos) {
        this.cantidadPuntos = cantidadPuntos;
    }

    public List<Logro> getLogrosConseguidos() {
        return logrosConseguidos;
    }

    public void setLogrosConseguidos(List<Logro> logrosConseguidos) {
        this.logrosConseguidos = logrosConseguidos;
    }
}
