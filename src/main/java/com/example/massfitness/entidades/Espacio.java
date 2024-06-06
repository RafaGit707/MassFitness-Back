package com.example.massfitness.entidades;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "espacios")
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEspacio;
    private String nombre;
    private int capacidadMaxima;
    @Temporal(TemporalType.TIME)
    private Date horarioReserva;
    @ManyToOne
    @JoinColumn(name = "entrenador_id")
    private Entrenador entrenador;

    @OneToMany(mappedBy = "espacio", cascade = CascadeType.ALL)
    private List<Reserva> reservas;
    public Espacio() {}

    public Espacio(int idEspacio) {
        this.idEspacio = idEspacio;
    }

    public Espacio(int idEspacio, String nombre, int capacidadMaxima, Date horarioReserva, Entrenador entrenador, List<Reserva> reservas) {
        this.idEspacio = idEspacio;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.horarioReserva = horarioReserva;
        this.entrenador = entrenador;
        this.reservas = reservas;
    }

    public Espacio(int idEspacio, String nombre, int capacidadMaxima, Date horarioReserva) {
        this.idEspacio = idEspacio;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.horarioReserva = horarioReserva;
    }

    public Espacio(String nombre, int capacidadMaxima, Date horarioReserva) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.horarioReserva = horarioReserva;
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

    public Date getHorarioReserva() {
        return horarioReserva;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public void setHorarioReserva(Date horarioReserva) {
        this.horarioReserva = horarioReserva;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
