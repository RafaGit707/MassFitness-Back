package com.example.massfitness.entidades;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReserva;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "espacio_id")
    private Espacio espacio;
    private String tipoReserva;
    private Date horarioReserva;
    private String estadoReserva;

    public Reserva() {
    }

    public Reserva(int idReserva, Usuario usuario, String tipoReserva, Date horarioReserva, String estadoReserva) {
        this.idReserva = idReserva;
        this.usuario = usuario;
        this.tipoReserva = tipoReserva;
        this.horarioReserva = horarioReserva;
        this.estadoReserva = estadoReserva;
    }
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public String getTipoReserva() {
        return tipoReserva;
    }

    public void setTipoReserva(String tipoReserva) {
        this.tipoReserva = tipoReserva;
    }

    public Date getHorarioReserva() {
        return horarioReserva;
    }

    public void setHorarioReserva(Date horarioReserva) {
        this.horarioReserva = horarioReserva;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
}
