package com.example.massfitness.entidades;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "clases")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClase;

    @ManyToOne
    @JoinColumn(name = "entrenador_id")
    private Entrenador entrenador;
    @ManyToOne
    @JoinColumn(name = "espacio_id")
    private Espacio espacio;

    private String nombre;
    private String descripcion;
    private Date horarioClase;
    private int capacidadMaxima;
    private int capacidadActual;

    public Clase() {
    }

    public Clase(int idClase, Entrenador entrenador, String nombre, String descripcion, Date horarioClase, int capacidadMaxima, int capacidadActual) {
        this.idClase = idClase;
        this.entrenador = entrenador;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horarioClase = horarioClase;
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadActual = capacidadActual;
    }

    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getHorarioClase() {
        return horarioClase;
    }

    public void setHorarioClase(Date horarioClase) {
        this.horarioClase = horarioClase;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getCapacidadActual() {
        return capacidadActual;
    }

    public void setCapacidadActual(int capacidadActual) {
        this.capacidadActual = capacidadActual;
    }
}
