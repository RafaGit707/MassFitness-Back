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

    private String tipoEspacio;
    private int capacidadMaxima;
    private int capacidadActual;

    @Temporal(TemporalType.TIME)
    private Date horarioReserva;

    @OneToMany(mappedBy = "espacio")
    private List<Reserva> reservas;
    @OneToMany(mappedBy = "espacio")
    private List<Clase> clases;

    public Espacio() {
    }

    public Espacio(int idEspacio, String tipoEspacio, int capacidadMaxima, int capacidadActual, Date horarioReserva) {
        this.idEspacio = idEspacio;
        this.tipoEspacio = tipoEspacio;
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadActual = capacidadActual;
        this.horarioReserva = horarioReserva;
    }

    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getTipoEspacio() {
        return tipoEspacio;
    }

    public void setTipoEspacio(String tipoEspacio) {
        this.tipoEspacio = tipoEspacio;
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

    public Date getHorarioReserva() {
        return horarioReserva;
    }

    public void setHorarioReserva(Date horarioReserva) {
        this.horarioReserva = horarioReserva;
    }
    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
