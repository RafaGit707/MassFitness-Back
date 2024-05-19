package com.example.massfitness.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "logros")
public class Logro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLogro;

    private String nombre;
    private String descripcion;
    private int requisitosPuntos;
    private String recompensa;

    public Logro() {
    }

    public Logro(int idLogro, String nombre, String descripcion, int requisitosPuntos, String recompensa) {
        this.idLogro = idLogro;
        this.nombre = nombre;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
