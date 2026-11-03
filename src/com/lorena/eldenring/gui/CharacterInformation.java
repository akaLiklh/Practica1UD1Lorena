package com.lorena.eldenring.gui;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import util.Util;

import javax.swing.*;
import java.awt.*;

public class CharacterInformation {

    private JPanel characterInformationPanel;
    private JPanel genderPanel;

    private JTextField textFieldName;
    private JTextArea textAreaClasses;

    private JRadioButton maleButton;
    private JRadioButton femButton;

    private JComboBox JComboBoxF;
    private JComboBox JComboBoxM;

    private JLabel txtGender;
    private JLabel txtName;
    private JLabel txtDate;
    private JLabel txtItem;
    private JLabel txtClasses;

    private JPanel centeredPanel;

    private ComboBoxModel maleModel;
    private ComboBoxModel femModel;


    public CharacterInformation() {
    FormLayout layout = (FormLayout) characterInformationPanel.getLayout();
        layout.setColumnSpec(1, ColumnSpec.decode("400px"));

        characterInformationPanel.setBackground(Color.BLACK);
        characterInformationPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
                );

        genderPanel.setOpaque(false);

        // Estilos
        Util.styleLabel(txtName);
        Util.styleLabel(txtGender);
        Util.styleLabel(txtDate);
        Util.styleLabel(txtItem);
        Util.styleLabel(txtClasses);
        Util.styleTextField(textFieldName);
        Util.styleTextArea(textAreaClasses);
        Util.styleComboBox(JComboBoxM);
        Util.styleComboBox(JComboBoxF);
        Util.styleRadioButton(maleButton);
        Util.styleRadioButton(femButton);

        maleModel = JComboBoxM.getModel();
        femModel = JComboBoxF.getModel();
        JComboBoxF.getParent().remove(JComboBoxF);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femButton);
        maleButton.setSelected(true);
        maleButton.addActionListener(e -> upDateClasses());
        femButton.addActionListener(e -> upDateClasses());
        upDateClasses();

        centeredPanel = Util.centerPanel(characterInformationPanel);
    }

    private void upDateClasses() {
        if (maleButton.isSelected()) {
            JComboBoxM.setModel(maleModel);
        } else if (femButton.isSelected()) {
            JComboBoxM.setModel(femModel);
        }
        characterInformationPanel.revalidate();
        characterInformationPanel.repaint();
    }

    public JPanel getPanel() {
        return centeredPanel;
    }
}