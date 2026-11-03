package com.lorena.eldenring.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.lorena.eldenring.base.ClasesPersonaje;
import com.lorena.eldenring.base.ItemInicial;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;

/**
 * VISTA del formulario de creación del personaje ({@code CharacterInformation.form}).
 * <p>
 * Contiene el nombre, el género (radio buttons), la clase, la fecha de nacimiento
 * (DatePicker de LGoodDatePicker) y el objeto inicial. Al cambiar el género se
 * cambia la imagen y los nombres de las clases (masculino / femenino).
 * <p>
 * Los componentes que necesita el controlador no son privados (visibilidad de paquete).
 *
 */
public class CharacterInformation {

    private JPanel characterInformationPanel;
    private JPanel genderPanel;

    /** Campo de texto con el nombre del personaje. */
    JTextField textFieldName;

    /** Radio button de género masculino. */
    JRadioButton maleButton;
    /** Radio button de género femenino. */
    JRadioButton femButton;

    /** Desplegable de clases que se ve en pantalla (muestra las masculinas o las femeninas). */
    JComboBox JComboBoxM;
    /** Desplegable de clases femeninas: solo se usa para sacar su modelo, se quita de la pantalla. */
    private JComboBox JComboBoxF;
    /** Desplegable con los objetos iniciales. */
    JComboBox JComboBoxItems;

    private JLabel txtGender;
    private JLabel txtName;
    private JLabel txtDate;
    private JLabel txtItem;
    private JLabel txtClasses;
    private JLabel lblImagen;

    /** Botón que termina la creación del personaje. */
    JButton endCharcButton;
    /** Selector de fecha de nacimiento. */
    DatePicker datePicker;

    private final JPanel centeredPanel;

    private final ComboBoxModel maleModel;
    private final ComboBoxModel femModel;

    private ImageIcon originalImage;

    /** Crea el formulario, aplica estilos y prepara el cambio de género. */
    public CharacterInformation() {

        FormLayout layout = (FormLayout) characterInformationPanel.getLayout();
        layout.setColumnSpec(1, ColumnSpec.decode("400px"));

        characterInformationPanel.setBackground(Color.BLACK);
        characterInformationPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

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

        // Calendario: formato dd/MM/yyyy y no se pueden elegir fechas futuras.
        datePicker.getSettings().setFormatForDatesCommonEra("dd/MM/yyyy");
        datePicker.getSettings().setDateRangeLimits(LocalDate.of(1, 1, 1), LocalDate.now());

        // Se guardan los modelos de los dos desplegables y se quita el femenino de la pantalla:
        // el desplegable masculino es el único visible y se le cambia el modelo según el género.
        maleModel = JComboBoxM.getModel();
        femModel = JComboBoxF.getModel();
        JComboBoxF.getParent().remove(JComboBoxF);

        // Selección de género (el ButtonGroup ya viene del .form, se vuelve a asegurar aquí).
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femButton);
        maleButton.setSelected(true);

        maleButton.addActionListener(e -> actualizarGenero());
        femButton.addActionListener(e -> actualizarGenero());

        // Imagen del género
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(220, 280));
        lblImagen.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeImage();
            }
        });

        actualizarGenero();

        centeredPanel = Util.centerPanel(characterInformationPanel);
    }

    /**
     * Cambia los nombres de las clases y la imagen según el radio button marcado.
     * Mantiene seleccionada la misma clase (misma posición) al cambiar de género.
     */
    void actualizarGenero() {
        int indice = JComboBoxM.getSelectedIndex();
        String ruta;

        if (femButton.isSelected()) {
            JComboBoxM.setModel(femModel);
            ruta = "/com/lorena/eldenring/images/fem.png";
        } else {
            JComboBoxM.setModel(maleModel);
            ruta = "/com/lorena/eldenring/images/male.png";
        }
        if (indice >= 0) {
            JComboBoxM.setSelectedIndex(indice);
        }

        originalImage = new ImageIcon(getClass().getResource(ruta));
        resizeImage();

        characterInformationPanel.revalidate();
        characterInformationPanel.repaint();
    }

    /** Escala la imagen al tamaño de la etiqueta manteniendo la proporción. */
    private void resizeImage() {
        if (originalImage == null) {
            return;
        }
        int labelWidth = lblImagen.getWidth();
        int labelHeight = lblImagen.getHeight();
        if (labelWidth <= 0 || labelHeight <= 0) {
            return;
        }
        double scale = Math.min(
                (double) labelWidth / originalImage.getIconWidth(),
                (double) labelHeight / originalImage.getIconHeight());

        int newWidth = (int) (originalImage.getIconWidth() * scale);
        int newHeight = (int) (originalImage.getIconHeight() * scale);

        Image resizedImage = originalImage.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        lblImagen.setIcon(new ImageIcon(resizedImage));
    }

    /**
     * La posición elegida en el desplegable coincide con el orden del enum.
     *
     * @return clase elegida
     */
    ClasesPersonaje getClaseSeleccionada() {
        return ClasesPersonaje.values()[Math.max(0, JComboBoxM.getSelectedIndex())];
    }

    /** @param clase clase que se selecciona en el desplegable */
    void setClaseSeleccionada(ClasesPersonaje clase) {
        JComboBoxM.setSelectedIndex(clase.ordinal());
    }

    /** @return objeto inicial elegido */
    ItemInicial getItemSeleccionado() {
        return ItemInicial.values()[Math.max(0, JComboBoxItems.getSelectedIndex())];
    }

    /** @param item objeto inicial que se selecciona en el desplegable */
    void setItemSeleccionado(ItemInicial item) {
        JComboBoxItems.setSelectedIndex(item.ordinal());
    }

    /** @return panel del formulario centrado sobre fondo negro */
    public JPanel getPanel() {
        return centeredPanel;
    }
}
