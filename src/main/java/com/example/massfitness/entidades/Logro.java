package com.example.massfitness.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "logros")
public class Logro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLogro;
    @JsonProperty("nombre_logro")
    private String nombreLogro;
    private String descripcion;
    private int requisitosPuntos;
    private String recompensa;

    public Logro() {
    }

    public Logro(int idLogro, String nombreLogro, String descripcion, int requisitosPuntos, String recompensa) {
        this.idLogro = idLogro;
        this.nombreLogro = nombreLogro;
        this.descripcion = descripcion;
        this.requisitosPuntos = requisitosPuntos;
        this.recompensa = recompensa;
    }

    public int getIdLogro() {
        return idLogro;
    }

    public void setIdLogro(int idLogro) {
        this.idLogro = idLogro;
    }

    public String getNombreLogro() {
        return nombreLogro;
    }

    public void setNombreLogro(String nombreLogro) {
        this.nombreLogro = nombreLogro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getRequisitosPuntos() {
        return requisitosPuntos;
    }

    public void setRequisitosPuntos(int requisitosPuntos) {
        this.requisitosPuntos = requisitosPuntos;
    }

    public String getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(String recompensa) {
        this.recompensa = recompensa;
    }
}
