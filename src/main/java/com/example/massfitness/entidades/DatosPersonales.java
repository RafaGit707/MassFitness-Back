package com.example.massfitness.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DatosPersonales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_datos_personales;
    private int edad;
    private String genero;

    public DatosPersonales() {
    }

    public DatosPersonales(int edad, String genero) {
        this.edad = edad;
        this.genero = genero;
    }
    public DatosPersonales(int id_datos_personales) {
        this.id_datos_personales = id_datos_personales;
    }
    public int getIdDatosPersonales() {
        return id_datos_personales;
    }

    public void setIdDatosPersonales(int id_datos_personales) {
        this.id_datos_personales = id_datos_personales;
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
