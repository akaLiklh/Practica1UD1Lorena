package com.lorena.eldenring.gui;

import javafx.scene.control.MenuButton;
import util.Util;

import javax.swing.*;

public class CharacterInformation {
    private JPanel characterInformationPanel;
    private JPanel genderPanel;
    private JTextField textFieldName;
    private JTextArea textAreaClasses;
    private JRadioButton maleButton;
    private JRadioButton femButton;
    private JComboBox JComboBoxF;
    private JTextArea txtAreaClasses;
    private JComboBox JComboBoxM;
    private JLabel txtGender;
    private JLabel txtName;
    private JLabel txtDate;
    private JLabel txtItem;
    private JLabel txtClasses;


    public CharacterInformation() {
    }

    public JPanel getPanel() {
        return characterInformationPanel;
    }
}
