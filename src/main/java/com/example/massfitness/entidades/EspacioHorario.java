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
    @Column(name = "espacio_id")
    @JsonProperty("espacio_id")
    private int espacioId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "espacio_id", referencedColumnName = "idEspacio", insertable = false, updatable = false)
    private Espacio espacio;
    @JsonProperty("horario_reserva")
    @Column(name = "horario_reserva", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp horarioReserva;
    @JsonProperty("capacidad_actual")
    private int capacidadActual;

    public EspacioHorario() {

    }
    public EspacioHorario(int idEspacioHorario, int espacioId, Timestamp horarioReserva, int capacidadActual) {
        this.idEspacioHorario = idEspacioHorario;
        this.espacioId = espacioId;
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

    public int getEspacioId() {
        return espacioId;
    }

    public void setEspacioId(int espacioId) {
        this.espacioId = espacioId;
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
