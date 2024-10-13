package com.example.massfitness.entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReserva;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "espacio_id", insertable = false, updatable = false)
    private Espacio espacio;
    @Column(name = "usuario_id")
    @JsonProperty("usuario_id")
    private int usuarioId;
    @Column(name = "espacio_id")
    @JsonProperty("espacio_id")
    private int espacioId;
    @JsonProperty("tipo_reserva")
    private String tipoReserva;
    @JsonProperty("horario_reserva")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.S")
    @Column(name = "horario_reserva", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp horarioReserva;
    @JsonProperty("estado_reserva")
    private String estadoReserva;

    public Reserva() {
    }
    public Reserva(int idReserva, int usuarioId, int espacioId, String tipoReserva, Timestamp horarioReserva, String estadoReserva) {
        this.idReserva = idReserva;
        this.usuarioId = usuarioId;
        this.espacioId = espacioId;
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

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuario) {
        this.usuarioId = usuarioId;
    }
    public int getEspacioId() {
        return espacioId;
    }

    public void setEspacioId(int espacio) {
        this.espacioId = espacioId;
    }

    public String getTipoReserva() {
        return tipoReserva;
    }

    public void setTipoReserva(String tipoReserva) {
        this.tipoReserva = tipoReserva;
    }

    public Timestamp getHorarioReserva() {
        return horarioReserva;
    }

    public void setHorarioReserva(Timestamp horarioReserva) {
        this.horarioReserva = horarioReserva;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
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
}
