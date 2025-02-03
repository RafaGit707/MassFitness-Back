package com.example.massfitness.entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "usuario_logro")
public class UsuarioLogro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_logro")
    @JsonProperty("id_usuario_logro")
    private int idUsuarioLogro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    @JsonIgnore
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logro_id", insertable = false, updatable = false)
    @JsonIgnore
    private Logro logro;
    @Column(name = "usuario_id")
    @JsonProperty("usuario_id")
    private int usuarioId;
    @Column(name = "logro_id")
    @JsonProperty("logro_id")
    private int logroId;
    @JsonProperty("fecha_obtenido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.S", timezone = "UTC")
    @Column(name = "fecha_obtenido", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp fecha_obtenido;

    public UsuarioLogro() {
    }
    public UsuarioLogro(Usuario usuario, Logro logro, Timestamp fecha_obtenido) {
        this.usuario = usuario;
        this.logro = logro;
        this.fecha_obtenido = fecha_obtenido;
    }

    public UsuarioLogro(int usuarioId, int logroId, Timestamp fecha_obtenido) {
        this.usuarioId = usuarioId;
        this.logroId = logroId;
        this.fecha_obtenido = fecha_obtenido;
    }

    public UsuarioLogro(int idUsuarioLogro, int usuarioId, int logroId, Timestamp fecha_obtenido) {
        this.idUsuarioLogro = idUsuarioLogro;
        this.usuarioId = usuarioId;
        this.logroId = logroId;
        this.fecha_obtenido = fecha_obtenido;
    }

    public int getIdUsuarioLogro() {
        return idUsuarioLogro;
    }

    public void setIdUsuarioLogro(int idUsuarioLogro) {
        this.idUsuarioLogro = idUsuarioLogro;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getLogroId() {
        return logroId;
    }

    public void setLogroId(int logroId) {
        this.logroId = logroId;
    }

    public Timestamp getFecha_obtenido() {
        return fecha_obtenido;
    }

    public void setFecha_obtenido(Timestamp fecha_obtenido) {
        this.fecha_obtenido = fecha_obtenido;
    }
}
