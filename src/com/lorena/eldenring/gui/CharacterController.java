package com.lorena.eldenring.gui;

import com.lorena.eldenring.base.ClasesPersonaje;
import com.lorena.eldenring.base.ItemInicial;
import com.lorena.eldenring.base.Personaje;
import com.lorena.eldenring.base.PersonajeFemenino;
import com.lorena.eldenring.base.PersonajeMasculino;
import util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;

public class CharacterController implements ActionListener, ListSelectionListener {

    private final CharacterModel modelo;
    private final FrontPage portada;
    private final CharacterInformation formulario;
    private final gestionFicheros gestion;

    /** Personaje que se está modificando (Actualizar); null si se está creando uno nuevo. */
    private Personaje personajeEditando;

    /**
     * Une el modelo con las vistas y registra los listeners.
     *
     * @param modelo     modelo con la lista de personajes
     * @param portada    vista de la portada
     * @param formulario vista del formulario de creación
     * @param gestion    vista de gestión de ficheros
     */
    public CharacterController(CharacterModel modelo, FrontPage portada,
                               CharacterInformation formulario, gestionFicheros gestion) {
        this.modelo = modelo;
        this.portada = portada;
        this.formulario = formulario;
        this.gestion = gestion;

        addActionListeners();
        gestion.listPersonajes.addListSelectionListener(this);
    }

    /** Pone a cada botón su "action command" y este controlador como listener. */
    private void addActionListeners() {
        portada.MenuButton.setActionCommand("Empezar");
        formulario.endCharcButton.setActionCommand("Terminar");
        gestion.nuevoButton.setActionCommand("Nuevo");
        gestion.actualizarButton.setActionCommand("Actualizar");
        gestion.borrarButton.setActionCommand("Borrar");
        gestion.exportarButton.setActionCommand("Exportar");
        gestion.importarButton.setActionCommand("Importar");

        portada.MenuButton.addActionListener(this);
        formulario.endCharcButton.addActionListener(this);
        gestion.nuevoButton.addActionListener(this);
        gestion.actualizarButton.addActionListener(this);
        gestion.borrarButton.addActionListener(this);
        gestion.exportarButton.addActionListener(this);
        gestion.importarButton.addActionListener(this);
    }

    /**
     * Decide qué hacer según el botón pulsado.
     *
     * @param e evento del botón
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton = (JButton) e.getSource();

        switch (e.getActionCommand()) {
            case "Empezar":
            case "Nuevo":
                personajeEditando = null;
                limpiarFormulario();
                Util.switchScreens(boton, formulario.getPanel());
                break;
            case "Terminar":
                guardarPersonaje(boton);
                break;
            case "Actualizar":
                editarSeleccionado(boton);
                break;
            case "Borrar":
                borrarSeleccionado();
                break;
            case "Exportar":
                exportar();
                break;
            case "Importar":
                importar();
                break;
            default:
                break;
        }
    }

    /**
     * Al seleccionar un personaje de la lista se muestra su ficha.
     *
     * @param e evento de la lista
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        // getValueIsAdjusting es true mientras se está haciendo clic: se espera a que termine.
        if (!e.getValueIsAdjusting()) {
            gestion.mostrarFicha(gestion.listPersonajes.getSelectedValue());
        }
    }

    /**
     * Lee el formulario, valida los datos, crea el personaje (clase hija según el
     * radio button) y lo guarda en el modelo. Después va a la pantalla de gestión.
     */
    private void guardarPersonaje(JButton boton) {
        // 1. Leer los datos de la vista.
        String nombre = formulario.textFieldName.getText().trim();
        LocalDate fecha = formulario.datePicker.getDate(); // null si no hay fecha válida
        ClasesPersonaje clase = formulario.getClaseSeleccionada();
        ItemInicial item = formulario.getItemSeleccionado();

        // 2. Validar. Si algo falla se avisa y no se guarda.
        if (nombre.isEmpty()) {
            Util.showError(boton, "Introduce el nombre del personaje.");
            return;
        }
        if (modelo.existeNombre(nombre, personajeEditando)) {
            Util.showError(boton, "Ya existe un personaje llamado " + nombre + ".");
            return;
        }
        if (fecha == null) {
            Util.showError(boton, "Introduce la fecha de nacimiento.");
            return;
        }
        if (fecha.isAfter(LocalDate.now())) {
            Util.showError(boton, "La fecha de nacimiento no puede ser posterior a hoy.");
            return;
        }

        // 3. HERENCIA: según el radio button se crea una clase hija u otra.
        Personaje personaje;
        if (formulario.maleButton.isSelected()) {
            personaje = new PersonajeMasculino(nombre, fecha, clase, item);
        } else {
            personaje = new PersonajeFemenino(nombre, fecha, clase, item);
        }

        // 4. Alta si es nuevo, modificación si venía de Actualizar.
        if (personajeEditando == null) {
            modelo.altaPersonaje(personaje);
        } else {
            modelo.modificarPersonaje(personajeEditando, personaje);
            personajeEditando = null;
        }

        // 5. Ir a la pantalla de gestión con el personaje seleccionado.
        refrescarLista();
        gestion.listPersonajes.setSelectedValue(personaje, true);
        Util.switchScreens(boton, gestion.getPanel());
    }

    /** Deja el formulario vacío para crear un personaje nuevo. */
    private void limpiarFormulario() {
        formulario.textFieldName.setText("");
        formulario.maleButton.setSelected(true);
        formulario.actualizarGenero();
        formulario.setClaseSeleccionada(ClasesPersonaje.values()[0]);
        formulario.setItemSeleccionado(ItemInicial.values()[0]);
        formulario.datePicker.clear();
    }

    /** Pone los datos de un personaje en el formulario (para Actualizar). */
    private void cargarEnFormulario(Personaje p) {
        formulario.textFieldName.setText(p.getNombre());
        // instanceof: comprueba de qué clase hija es el personaje.
        if (p instanceof PersonajeMasculino) {
            formulario.maleButton.setSelected(true);
        } else {
            formulario.femButton.setSelected(true);
        }
        formulario.actualizarGenero();
        formulario.setClaseSeleccionada(p.getClase());
        formulario.setItemSeleccionado(p.getItemInicial());
        formulario.datePicker.setDate(p.getFechaNacimiento());
    }

    /** Vuelve a cargar la lista de la vista con los personajes del modelo. */
    private void refrescarLista() {
        gestion.dlmPersonajes.clear();
        for (Personaje p : modelo.getPersonajes()) {
            gestion.dlmPersonajes.addElement(p);
        }
        gestion.mostrarFicha(null);
    }

    /** Abre el formulario con los datos del personaje seleccionado. */
    private void editarSeleccionado(JButton boton) {
        Personaje seleccionado = gestion.listPersonajes.getSelectedValue();
        if (seleccionado == null) {
            Util.showError(boton, "Selecciona primero el personaje que quieres actualizar.");
            return;
        }
        personajeEditando = seleccionado;
        cargarEnFormulario(seleccionado);
        Util.switchScreens(boton, formulario.getPanel());
    }

    /** Borra el personaje seleccionado tras pedir confirmación. */
    private void borrarSeleccionado() {
        Personaje seleccionado = gestion.listPersonajes.getSelectedValue();
        if (seleccionado == null) {
            Util.showError(gestion.getPanel(), "Selecciona primero el personaje que quieres borrar.");
            return;
        }
        if (Util.confirm(gestion.getPanel(),
                "¿Seguro que quieres borrar a " + seleccionado.getNombre() + "?", "Borrar")) {
            modelo.eliminarPersonaje(seleccionado);
            refrescarLista();
        }
    }

    /** Exporta todos los personajes a un fichero XML elegido por el usuario. */
    private void exportar() {
        if (modelo.getPersonajes().isEmpty()) {
            Util.showError(gestion.getPanel(), "No hay personajes para exportar.");
            return;
        }
        File fichero = Util.chooseFileToSave(gestion.getPanel(), "xml");
        if (fichero == null) {
            return; // ha pulsado Cancelar
        }
        try {
            modelo.exportarXML(fichero);
            Util.showInfo(gestion.getPanel(), "Personajes exportados en " + fichero.getName());
        } catch (Exception ex) {
            Util.showError(gestion.getPanel(), "No se ha podido exportar: " + ex.getMessage());
        }
    }

    /** Importa los personajes de un fichero XML elegido por el usuario. */
    private void importar() {
        if (!modelo.getPersonajes().isEmpty()
                && !Util.confirm(gestion.getPanel(),
                "Se sustituirán los personajes actuales. ¿Continuar?", "Importar")) {
            return;
        }
        File fichero = Util.chooseFileToOpen(gestion.getPanel(), "xml");
        if (fichero == null) {
            return; // ha pulsado Cancelar
        }
        try {
            modelo.importarXML(fichero);
            refrescarLista();
            Util.showInfo(gestion.getPanel(),
                    "Se han importado " + modelo.getPersonajes().size() + " personajes.");
        } catch (Exception ex) {
            Util.showError(gestion.getPanel(), "El fichero no es válido: " + ex.getMessage());
        }
    }
}
