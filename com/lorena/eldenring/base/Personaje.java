package com.lorena.eldenring.base;

import java.time.LocalDate;

public class Personaje {
    private String nombre;
    private LocalDate fechaNacimiento;
    private int nivel;
    private double vida;
    private String descripcion;

    public Personaje(String nombre, LocalDate fechaNacimiento, int nivel, double vida, String descripcion) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.nivel = nivel;
        this.vida = vida;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public double getVida() {
        return vida;
    }

    public void setVida(double vida) {
        this.vida = vida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre + " (nivel " + nivel + ")";
    }
}
