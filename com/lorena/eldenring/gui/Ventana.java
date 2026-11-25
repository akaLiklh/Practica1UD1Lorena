package com.lorena.eldenring.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.lorena.eldenring.base.Personaje;

import javax.swing.*;

public class Ventana {
    public JPanel panel;
    public JRadioButton rbJugador;
    public JRadioButton rbNPC;
    public JRadioButton rbEnemigo;
    public JTextField txtNombre;
    public DatePicker datePicker;
    public JTextField txtNivel;
    public JTextField txtVida;
    public JLabel lblCampo1;
    public JTextField txtCampo1;
    public JLabel lblCampo2;
    public JTextField txtCampo2;
    public JTextArea txtDescripcion;
    public JList<Personaje> listPersonajes;
    public JButton btnNuevo;
    public JButton btnModificar;
    public JButton btnEliminar;
    public JButton btnLimpiar;
    public JButton btnImportar;
    public JButton btnExportar;
    public JFrame frame;
    public DefaultListModel<Personaje> dlmPersonajes;

    public Ventana() {
        frame = new JFrame("Elden Ring - Gestion de personajes");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rbJugador);
        grupoTipo.add(rbNPC);
        grupoTipo.add(rbEnemigo);
        rbJugador.setSelected(true);

        dlmPersonajes = new DefaultListModel<Personaje>();
        listPersonajes.setModel(dlmPersonajes);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}