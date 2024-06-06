package com.example.massfitness.entidades;

import java.util.Date;

public class EspacioHorario {
    private int idEspacioHorario;
    private Espacio espacio;
    private Date horarioReserva;
    private int capacidadActual;

    public EspacioHorario(int idEspacioHorario, Espacio espacio, Date horarioReserva, int capacidadActual) {
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

    public Date getHorarioReserva() {
        return horarioReserva;
    }

    public void setHorarioReserva(Date horarioReserva) {
        this.horarioReserva = horarioReserva;
    }

    public int getCapacidadActual() {
        return capacidadActual;
    }

    public void setCapacidadActual(int capacidadActual) {
        this.capacidadActual = capacidadActual;
    }
}
