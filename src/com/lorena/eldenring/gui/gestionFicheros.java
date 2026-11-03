package com.lorena.eldenring.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.lorena.eldenring.base.Personaje;
import util.Util;

import javax.swing.*;
import java.awt.*;

/**
 * VISTA de la pantalla de gestión de personajes y ficheros ({@code gestionFicheros.form}).
 * <p>
 * Usa los botones del formulario: Nuevo, Actualizar, Borrar, Exportar e Importar.
 * El filtro del formulario (botón "Buscar", etiqueta "Clase" y los dos desplegables)
 * no se usa: se quita de la pantalla al crear la vista. En su lugar se añade por código
 * la lista de personajes y un área de texto con la ficha del personaje seleccionado.
 *
 */
public class gestionFicheros {

    // Componentes enlazados en el .form
    private JPanel panelPrincipal;
    /** Botón Nuevo: abre el formulario vacío. */
    JButton nuevoButton;
    /** Botón Borrar: borra el personaje seleccionado. */
    JButton borrarButton;
    /** Botón Exportar: guarda los personajes en un XML. */
    JButton exportarButton;
    /** Desplegable del filtro (no se usa, se quita de la pantalla). */
    private JComboBox comboBox1;

    // Botones del .form que no tienen campo enlazado: se buscan por su texto.
    /** Botón Actualizar: abre el formulario con el personaje seleccionado. */
    JButton actualizarButton;
    /** Botón Importar: carga los personajes desde un XML. */
    JButton importarButton;

    // Componentes creados por código
    /** Modelo de la lista: lo rellena el controlador. */
    final DefaultListModel<Personaje> dlmPersonajes;
    /** Lista de personajes creados. */
    final JList<Personaje> listPersonajes;
    /** Ficha con todos los datos del personaje seleccionado. */
    final JTextArea txtFicha;

    private final JPanel centeredPanel;

    /** Crea la pantalla: quita el filtro, coloca la lista y la ficha y aplica estilos. */
    public gestionFicheros() {
        actualizarButton = Util.buscarBoton(panelPrincipal, "Actualizar");
        importarButton = Util.buscarBoton(panelPrincipal, "Importar");

        dlmPersonajes = new DefaultListModel<>();
        listPersonajes = new JList<>(dlmPersonajes);
        listPersonajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        txtFicha = new JTextArea(7, 30);
        txtFicha.setEditable(false);

        quitarFiltro();
        colocarComponentes();
        aplicarEstilos();
        mostrarFicha(null);

        centeredPanel = Util.centerPanel(panelPrincipal);
    }

    /**
     * Quita de la pantalla todo lo relacionado con el filtro:
     * el botón "Buscar" y la fila 3 del formulario (etiqueta "Clase" y los dos desplegables).
     */
    private void quitarFiltro() {
        FormLayout layout = (FormLayout) panelPrincipal.getLayout();
        for (Component c : panelPrincipal.getComponents()) {
            boolean filaFiltro = layout.getConstraints(c).gridY == 3;
            boolean botonBuscar = c instanceof JButton && "Buscar".equals(((JButton) c).getText());
            if (filaFiltro || botonBuscar) {
                panelPrincipal.remove(c);
            }
        }
        comboBox1 = null;
    }

    /**
     * Ajusta la rejilla del FormLayout: separa los botones, sube "Importar" al hueco que
     * deja "Buscar" y usa la antigua fila del filtro para la lista. Debajo añade la ficha.
     */
    private void colocarComponentes() {
        FormLayout layout = (FormLayout) panelPrincipal.getLayout();

        // Columnas de botones del mismo ancho.
        for (int col = 1; col <= 3; col++) {
            layout.setColumnSpec(col, ColumnSpec.decode("fill:150px:noGrow"));
        }
        // Importar pasa a la columna 2 (donde estaba Buscar).
        layout.setConstraints(importarButton, new CellConstraints(2, 2));

        // La fila 3 (la del filtro) pasa a ser la de la lista.
        layout.setRowSpec(3, RowSpec.decode("fill:220px:grow"));
        layout.appendRow(RowSpec.decode("fill:140px:noGrow")); // fila 4: ficha

        // Huecos entre columnas y filas (insertar mueve solo los componentes ya colocados).
        layout.insertColumn(3, ColumnSpec.decode("6px"));
        layout.insertColumn(2, ColumnSpec.decode("6px"));
        layout.insertRow(4, RowSpec.decode("8px"));
        layout.insertRow(3, RowSpec.decode("8px"));
        layout.insertRow(2, RowSpec.decode("6px"));
        // Ahora: columnas 1, 3, 5 ; filas 1, 3 (botones), 5 (lista), 7 (ficha).

        JScrollPane scrollLista = new JScrollPane(listPersonajes);
        JScrollPane scrollFicha = new JScrollPane(txtFicha);
        Util.styleScrollPane(scrollLista);
        Util.styleScrollPane(scrollFicha);

        panelPrincipal.add(scrollLista, new CellConstraints(1, 5, 5, 1));
        panelPrincipal.add(scrollFicha, new CellConstraints(1, 7, 5, 1));
    }

    /** Colores y formato de cada componente (todo pasa por Util). */
    private void aplicarEstilos() {
        panelPrincipal.setBackground(Color.BLACK);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        Util.styleList(listPersonajes);
        Util.styleTextArea(txtFicha);

        Util.styleButton(nuevoButton);
        Util.styleButton(actualizarButton);
        Util.styleButton(borrarButton);
        Util.styleButton(exportarButton);
        Util.styleButton(importarButton);
    }

    /**
     * Muestra en el área de texto la ficha del personaje.
     *
     * @param personaje personaje a mostrar, o null si no hay ninguno seleccionado
     */
    void mostrarFicha(Personaje personaje) {
        if (personaje == null) {
            txtFicha.setText(dlmPersonajes.isEmpty()
                    ? "Todavía no hay personajes. Pulsa Nuevo o Importar."
                    : "Selecciona un personaje de la lista para ver su ficha.");
        } else {
            txtFicha.setText(personaje.getFicha());
        }
        txtFicha.setCaretPosition(0);
    }

    /** @return panel de la pantalla centrado sobre fondo negro */
    public JPanel getPanel() {
        return centeredPanel;
    }
}
