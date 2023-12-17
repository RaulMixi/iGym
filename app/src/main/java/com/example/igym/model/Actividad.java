package com.example.igym.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Actividad {

        private String nombreActividad;
        private String horario;
        private double precio;
        private String descr;

        public Actividad(String nombreActividad, String descr, String horario, double precio) {
        this.nombreActividad = nombreActividad;
        this.descr = descr;
        this.horario = horario;
        this.precio = precio;
        }

        public Actividad(){}


        public String getNombreActividad() {
            return nombreActividad;
        }

        public String getHorario() {
            return horario;
        }

        public double getPrecio(){return precio;}

        public void setHorario(String horario) {
        this.horario = horario;
    }

        public void setNombreActividad(String nombreActividad) {
                this.nombreActividad = nombreActividad;
        }

        public void setPrecio(double precio) {
        this.precio = precio;
    }

        public void setDescr(String descr) {
        this.descr = descr;
    }

        public String getDescr() {
        return descr;
    }

        public String toString()
    {
        return this.getNombreActividad();
    }

}
