package com.lorena.eldenring.base;

import java.time.LocalDate;

public class NPC extends Personaje {
    private String ubicacion;
    private int misiones;

    public NPC(String nombre, LocalDate fechaNacimiento, int nivel, double vida, String descripcion, String ubicacion, int misiones) {
        super(nombre, fechaNacimiento, nivel, vida, descripcion);
        this.ubicacion = ubicacion;
        this.misiones = misiones;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getMisiones() {
        return misiones;
    }

    public void setMisiones(int misiones) {
        this.misiones = misiones;
    }

    @Override
    public String toString() {
        return "NPC: " + super.toString();
    }
}
