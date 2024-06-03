package com.example.massfitness.entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "entrenadores")
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEntrenador;
    private String nombre;
    private String especializacion;
    @OneToMany(mappedBy = "entrenador", cascade = CascadeType.ALL)
    private List<Espacio> espacios;

    public Entrenador() {
    }

    public Entrenador(int idEntrenador, String nombre, String especializacion) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.especializacion = especializacion;
    }

    public Entrenador(int idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public int getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(int idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public List<Espacio> getEspacios() {
        return espacios;
    }

    public void setEspacios(List<Espacio> espacios) {
        this.espacios = espacios;
    }
}
