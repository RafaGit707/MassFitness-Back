package com.example.massfitness.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "usuario_logro")
public class UsuarioLogro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuarioLogro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logro_id")
    private Logro logro;
    @JsonProperty("fecha_obtenido")
    @Column(name = "fecha_obtenido", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp fechaObtenido;

    public UsuarioLogro() {
    }
    public UsuarioLogro(Usuario usuario, Logro logro, Timestamp fechaObtenido) {
        this.usuario = usuario;
        this.logro = logro;
        this.fechaObtenido = fechaObtenido;
    }

    public int getIdUsuarioLogro() {
        return idUsuarioLogro;
    }

    public void setIdUsuarioLogro(int idUsuarioLogro) {
        this.idUsuarioLogro = idUsuarioLogro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Logro getLogro() {
        return logro;
    }

    public void setLogro(Logro logro) {
        this.logro = logro;
    }

    public Timestamp getFechaObtenido() {
        return fechaObtenido;
    }

    public void setFechaObtenido(Timestamp fechaObtenido) {
        this.fechaObtenido = fechaObtenido;
    }
}
