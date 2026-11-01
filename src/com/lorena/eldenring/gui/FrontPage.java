package com.lorena.eldenring.gui;


import util.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FrontPage {
    private JPanel FrontPage;
    private JLabel ImageFrontPage;
    private JButton MenuButton;

    public FrontPage() {
        FrontPage.setBackground(Color.BLACK);

        ImageIcon original = new ImageIcon(
                getClass().getResource("/com/lorena/eldenring/images/frontPageImage.jpg"));
        Image scaled = original.getImage().getScaledInstance(300, 500, Image.SCALE_SMOOTH);

        ImageFrontPage.setIcon(new ImageIcon(scaled));
        ImageFrontPage.setBorder(new LineBorder(new Color(169, 142, 85), 2));

        Util.styleButton(MenuButton);
        MenuButton.addActionListener(e -> {
            JFrame ventana = Util.show("Información del personaje",
                    new CharacterInformation().getPanel());
            ventana.setSize(700, 600);
            ventana.setLocationRelativeTo(null);
        });
    }

    public JPanel getPanel() {
        return FrontPage;
    }
}