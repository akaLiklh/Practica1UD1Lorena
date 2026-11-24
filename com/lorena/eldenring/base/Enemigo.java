package com.lorena.eldenring.base;

import java.time.LocalDate;

public class Enemigo extends Personaje {
    private String tipoAtaque;
    private int recompensaRunas;

    public Enemigo(String nombre, LocalDate fechaNacimiento, int nivel, double vida, String descripcion, String tipoAtaque, int recompensaRunas) {
        super(nombre, fechaNacimiento, nivel, vida, descripcion);
        this.tipoAtaque = tipoAtaque;
        this.recompensaRunas = recompensaRunas;
    }

    public String getTipoAtaque() {
        return tipoAtaque;
    }

    public void setTipoAtaque(String tipoAtaque) {
        this.tipoAtaque = tipoAtaque;
    }

    public int getRecompensaRunas() {
        return recompensaRunas;
    }

    public void setRecompensaRunas(int recompensaRunas) {
        this.recompensaRunas = recompensaRunas;
    }

    @Override
    public String toString() {
        return "Enemigo: " + super.toString();
    }
}