package com.lorena.eldenring.gui;

import util.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * VISTA de la portada (formulario {@code FrontPage.form}).
 * <p>
 * Muestra la imagen de portada y el botón "Empezar". El botón lo gestiona el
 * CharacterController, que lleva a la pantalla de creación del personaje.
 *
 */
public class FrontPage {
    private JPanel FrontPage;
    private JLabel ImageFrontPage;
    /** Botón "Empezar" de la portada (lo usa el controlador). */
    JButton MenuButton;

    /** Crea la portada y le aplica los estilos. */
    public FrontPage() {
        FrontPage.setBackground(Color.BLACK);

        ImageIcon original = new ImageIcon(
                getClass().getResource("/com/lorena/eldenring/images/frontPageImage.jpg"));
        Image scaled = original.getImage().getScaledInstance(300, 500, Image.SCALE_SMOOTH);

        ImageFrontPage.setIcon(new ImageIcon(scaled));
        ImageFrontPage.setBorder(new LineBorder(Util.GOLDEN, 2));

        Util.styleButton(MenuButton);
    }

    /** @return panel principal de la portada */
    public JPanel getPanel() {
        return FrontPage;
    }
}
