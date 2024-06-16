package com.example.massfitness.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "espacios")
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEspacio;
    private String nombre;
    @JsonProperty("capacidad_maxima")
    private int capacidadMaxima;
    @ManyToOne
    @JoinColumn(name = "entrenador_id")
    private Entrenador entrenador;
    @OneToMany(mappedBy = "espacio", fetch = FetchType.LAZY)
    private Set<Reserva> reservas;
    public Espacio() {}

    public Espacio(int idEspacio) {
        this.idEspacio = idEspacio;
    }

    public Espacio(int idEspacio, String nombre, int capacidadMaxima, Entrenador entrenador, Set<Reserva> reservas) {
        this.idEspacio = idEspacio;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.entrenador = entrenador;
        this.reservas = reservas;
    }

    public Espacio(int idEspacio, String nombre, int capacidadMaxima) {
        this.idEspacio = idEspacio;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
    }

    public Espacio(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
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
