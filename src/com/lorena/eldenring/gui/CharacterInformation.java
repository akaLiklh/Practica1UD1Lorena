package com.lorena.eldenring.gui;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.github.lgooddatepicker.components.DatePicker;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class CharacterInformation {

    private JPanel characterInformationPanel;
    private JPanel genderPanel;

    private JTextField textFieldName;
    private JTextArea txtDescription;

    private JRadioButton maleButton;
    private JRadioButton femButton;

    private JComboBox JComboBoxF;
    private JComboBox JComboBoxM;

    private JLabel txtGender;
    private JLabel txtName;
    private JLabel txtDate;
    private JLabel txtItem;
    private JLabel txtClasses;
    private JLabel lblImagen;

    private JButton endCharcButton;
    private JComboBox JComboBoxItems;
    private DatePicker datePicker;

    private JPanel centeredPanel;

    private ComboBoxModel maleModel;
    private ComboBoxModel femModel;

    private ImageIcon originalImage;

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
        Util.styleComboBox(JComboBoxM);
        Util.styleComboBox(JComboBoxF);
        Util.styleComboBox(JComboBoxItems);
        Util.styleRadioButton(maleButton);
        Util.styleRadioButton(femButton);
        Util.styleDatePicker(datePicker);
        Util.styleButton(endCharcButton);

        // Configuración del calendario
        datePicker.getSettings().setFormatForDatesCommonEra("dd/MM/yyyy");

        datePicker.getSettings().setDateRangeLimits(
                LocalDate.of(1, 1, 1),
                LocalDate.now()
        );

        // Modelos de las clases
        maleModel = JComboBoxM.getModel();
        femModel = JComboBoxF.getModel();

        JComboBoxF.getParent().remove(JComboBoxF);

        // Selección de género
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femButton);

        maleButton.setSelected(true);

        maleButton.addActionListener(e -> upDateClasses());
        femButton.addActionListener(e -> upDateClasses());

        // Configuración de la imagen
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(220, 280));

        // Redimensionar cuando cambie el tamaño del JLabel
        lblImagen.addComponentListener(
                new java.awt.event.ComponentAdapter() {
                    @Override
                    public void componentResized(java.awt.event.ComponentEvent e) {
                        resizeImage();
                    }
                }
        );

        upDateClasses();

        centeredPanel = Util.centerPanel(characterInformationPanel);
    }

    private void upDateClasses() {

        if (maleButton.isSelected()) {

            JComboBoxM.setModel(maleModel);

            originalImage = new ImageIcon(
                    getClass().getResource(
                            "/com/lorena/eldenring/images/male.png"
                    )
            );

        } else if (femButton.isSelected()) {

            JComboBoxM.setModel(femModel);

            originalImage = new ImageIcon(
                    getClass().getResource(
                            "/com/lorena/eldenring/images/fem.png"
                    )
            );
        }

        resizeImage();

        characterInformationPanel.revalidate();
        characterInformationPanel.repaint();
    }

    private void resizeImage() {

        if (originalImage == null) {
            return;
        }

        int labelWidth = lblImagen.getWidth();
        int labelHeight = lblImagen.getHeight();

        if (labelWidth <= 0 || labelHeight <= 0) {
            return;
        }

        int originalWidth = originalImage.getIconWidth();
        int originalHeight = originalImage.getIconHeight();

        double scale = Math.min(
                (double) labelWidth / originalWidth,
                (double) labelHeight / originalHeight
        );

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        Image resizedImage = originalImage.getImage().getScaledInstance(
                newWidth,
                newHeight,
                Image.SCALE_SMOOTH
        );

        lblImagen.setIcon(new ImageIcon(resizedImage));
    }

    public JPanel getPanel() {
        return centeredPanel;
    }
}