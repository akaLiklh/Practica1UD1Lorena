package com.lorena.eldenring;

import com.lorena.eldenring.gui.ControladorPersonaje;
import com.lorena.eldenring.gui.ModeloPersonaje;
import com.lorena.eldenring.gui.Ventana;

public class Main {
    public static void main(String[] args) {
        Ventana vista = new Ventana();
        ModeloPersonaje modelo = new ModeloPersonaje();
        ControladorPersonaje controlador = new ControladorPersonaje(vista, modelo);
    }
}