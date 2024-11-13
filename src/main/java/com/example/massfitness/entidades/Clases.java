package com.example.massfitness.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Set;
@Entity
@Table(name = "clases")
public class Clases {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClase;
    private String nombre;
    @JsonProperty("capacidad_maxima")
    private int capacidadMaxima;
    @ManyToOne
    @JoinColumn(name = "entrenador_id")
    private Entrenador entrenador;
    @OneToMany(mappedBy = "clase", fetch = FetchType.LAZY)
    private Set<Reserva> reservas;

    public Clases() {
    }

    public Clases(int idClase) {
        this.idClase = idClase;
    }

    public Clases(int idClase, String nombre, int capacidadMaxima, Entrenador entrenador, Set<Reserva> reservas) {
        this.idClase = idClase;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.entrenador = entrenador;
        this.reservas = reservas;
    }

    public Clases(int idClase, String nombre, int capacidadMaxima) {
        this.idClase = idClase;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
    }

    public Clases(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

}