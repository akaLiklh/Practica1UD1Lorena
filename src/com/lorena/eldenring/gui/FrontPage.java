package com.lorena.eldenring.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FrontPage {
    private JPanel FrontPage;
    private JLabel ImageFrontPage;
    private JButton MenuBotton;

    public FrontPage() {
        FrontPage.setBackground(Color.BLACK);
        ImageIcon original = new ImageIcon(
                FrontPage.class.getResource("/com/lorena/eldenring/images/frontPageImage.jpg"));

        Image scaled = original.getImage().getScaledInstance(300, 500, Image.SCALE_SMOOTH);

        ImageFrontPage.setIcon(new ImageIcon(scaled));
        ImageFrontPage.setBorder(new LineBorder(new Color(169, 142, 85), 2));

        MenuBotton.setBackground(new Color(10, 67, 61));
        MenuBotton.setForeground(Color.WHITE);
        MenuBotton.setOpaque(true);
        MenuBotton.setFocusPainted(false);
        MenuBotton.setBorder(new LineBorder(new Color(169, 142, 85), 1));
    }

    public JPanel getPanel() {
        return FrontPage;
    }
}