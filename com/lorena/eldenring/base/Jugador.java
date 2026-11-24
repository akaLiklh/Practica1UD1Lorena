package com.lorena.eldenring.base;

import java.time.LocalDate;

public class Jugador extends Personaje {
    private String arma;
    private int mana;

    public Jugador(String nombre, LocalDate fechaNacimiento, int nivel, double vida, String descripcion, String arma, int mana) {
        super(nombre, fechaNacimiento, nivel, vida, descripcion);
        this.arma = arma;
        this.mana = mana;
    }

    public String getArma() {
        return arma;
    }

    public void setArma(String arma) {
        this.arma = arma;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    @Override
    public String toString() {
        return "Jugador: " + super.toString();
    }
}