package com.example.massfitness.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "espacio_horario")
public class EspacioHorario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEspacioHorario;
    @ManyToOne
    @JoinColumn(name = "espacio_id")
    private Espacio espacio;
    @JsonProperty("horario_reserva")
    @Column(name = "horario_reserva", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp horarioReserva;
    @JsonProperty("capacidad_actual")
    private int capacidadActual;

    public EspacioHorario() {

    }
    public EspacioHorario(int idEspacioHorario, Espacio espacio, Timestamp horarioReserva, int capacidadActual) {
        this.idEspacioHorario = idEspacioHorario;
        this.espacio = espacio;
        this.horarioReserva = horarioReserva;
        this.capacidadActual = capacidadActual;
    }

    public int getIdEspacioHorario() {
        return idEspacioHorario;
    }

    public void setIdEspacioHorario(int idEspacioHorario) {
        this.idEspacioHorario = idEspacioHorario;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public Timestamp getHorarioReserva() {
        return horarioReserva;
    }

    public void setHorarioReserva(Timestamp horarioReserva) {
        this.horarioReserva = horarioReserva;
    }

    public int getCapacidadActual() {
        return capacidadActual;
    }

    public void setCapacidadActual(int capacidadActual) {
        this.capacidadActual = capacidadActual;
    }
}
