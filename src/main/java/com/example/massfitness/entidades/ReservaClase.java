package com.example.massfitness.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "reserva_clase")
public class ReservaClase {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int idReservaClase;
        @Column(name = "clases_id")
        @JsonProperty("clases_id")
        private int clasesId;
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "clases_id", referencedColumnName = "idClase", insertable = false, updatable = false)
        private Clases clase;
        @JsonProperty("horario_reserva")
        @Column(name = "horario_reserva", columnDefinition = "TIMESTAMP WITH TIME ZONE")
        private Timestamp horarioReserva;
        @JsonProperty("capacidad_actual")
        private int capacidadActual;

        public ReservaClase() {
        }
        public ReservaClase(int idReservaClase, int clasesId, Timestamp horarioReserva, int capacidadActual) {
                this.idReservaClase = idReservaClase;
                this.clasesId = clasesId;
                this.horarioReserva = horarioReserva;
                this.capacidadActual = capacidadActual;
        }

        public int getIdReservaClase() {
                return idReservaClase;
        }

        public void setIdReservaClase(int idReservaClase) {
                this.idReservaClase = idReservaClase;
        }

        public int getClasesId() {
                return clasesId;
        }

        public void setClasesId(int clasesId) {
                this.clasesId = clasesId;
        }

        public Clases getClase() {
                return clase;
        }

        public void setClase(Clases clase) {
                this.clase = clase;
        }

        public Timestamp getHorarioReserva() {
                return horarioReserva;
        }

        public void setHorarioReserva(Timestamp horarioReserva) {
                this.horarioReserva = horarioReserva;
        }

        public int getCapacidadActual() {
                return capacidadActual;
        }

        public void setCapacidadActual(int capacidadActual) {
                this.capacidadActual = capacidadActual;
        }
}
