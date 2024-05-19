package com.example.massfitness.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DatosPersonales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDatosPersonales;
    private int edad;
    private String genero;

    public DatosPersonales() {
    }

    public DatosPersonales(int edad, String genero) {
        this.edad = edad;
        this.genero = genero;
    }

    public int getIdDatosPersonales() {
        return idDatosPersonales;
    }

    public void setIdDatosPersonales(int idDatosPersonales) {
        this.idDatosPersonales = idDatosPersonales;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
