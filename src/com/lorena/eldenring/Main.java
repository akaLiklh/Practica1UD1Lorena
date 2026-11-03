package com.lorena.eldenring;

import com.lorena.eldenring.gui.CharacterController;
import com.lorena.eldenring.gui.CharacterInformation;
import com.lorena.eldenring.gui.CharacterModel;
import com.lorena.eldenring.gui.FrontPage;
import com.lorena.eldenring.gui.gestionFicheros;
import util.Util;

import javax.swing.*;
import java.awt.*;

/**
 * Punto de entrada de la aplicación "Elden Ring - Creación de personajes".
 * <p>
 * Crea el modelo, las tres vistas y el controlador (MVC) y muestra la portada.
 *
 */
public class Main {

    /**
     * Arranca la aplicación en el hilo de Swing.
     *
     * @param args no se usan
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Quita el recuadro de foco de los botones (antes de crear las vistas).
            UIManager.put("Button.focus", new javax.swing.plaf.ColorUIResource(new Color(0, 0, 0, 0)));

            CharacterModel modelo = new CharacterModel();
            FrontPage portada = new FrontPage();
            CharacterInformation formulario = new CharacterInformation();
            gestionFicheros gestion = new gestionFicheros();
            new CharacterController(modelo, portada, formulario, gestion);

            // Se muestra la misma portada que escucha el controlador.
            Util.show("Elden Ring", portada.getPanel(), false);
        });
    }
}
