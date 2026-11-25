package com.lorena.eldenring.gui;

import com.lorena.eldenring.base.Enemigo;
import com.lorena.eldenring.base.Jugador;
import com.lorena.eldenring.base.NPC;
import com.lorena.eldenring.base.Personaje;
import util.Util;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.time.LocalDate;


public class ControladorPersonaje implements ActionListener, ListSelectionListener, WindowListener {
    private Ventana vista;
    private ModeloPersonaje modelo;

    public ControladorPersonaje(Ventana vista, ModeloPersonaje modelo) {
        this.vista = vista;
        this.modelo = modelo;

        anadirActionListener(this);
        vista.listPersonajes.addListSelectionListener(this);
        vista.frame.addWindowListener(this);

        cambiarCampos();
    }

    private void anadirActionListener(ActionListener listener) {
        vista.btnNuevo.setActionCommand("Nuevo");
        vista.btnModificar.setActionCommand("Modificar");
        vista.btnEliminar.setActionCommand("Eliminar");
        vista.btnLimpiar.setActionCommand("Limpiar");
        vista.btnImportar.setActionCommand("Importar");
        vista.btnExportar.setActionCommand("Exportar");
        vista.rbJugador.setActionCommand("Jugador");
        vista.rbNPC.setActionCommand("NPC");
        vista.rbEnemigo.setActionCommand("Enemigo");

        vista.btnNuevo.addActionListener(listener);
        vista.btnModificar.addActionListener(listener);
        vista.btnEliminar.addActionListener(listener);
        vista.btnLimpiar.addActionListener(listener);
        vista.btnImportar.addActionListener(listener);
        vista.btnExportar.addActionListener(listener);
        vista.rbJugador.addActionListener(listener);
        vista.rbNPC.addActionListener(listener);
        vista.rbEnemigo.addActionListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "Nuevo":
                nuevoPersonaje();
                break;
            case "Modificar":
                modificarPersonaje();
                break;
            case "Eliminar":
                eliminarPersonaje();
                break;
            case "Limpiar":
                limpiarCampos();
                break;
            case "Importar":
                importar();
                break;
            case "Exportar":
                exportar();
                break;
            case "Jugador":
            case "NPC":
            case "Enemigo":
                cambiarCampos();
                break;
        }
    }

    private void cambiarCampos() {
        if (vista.rbJugador.isSelected()) {
            vista.lblCampo1.setText("Arma:");
            vista.lblCampo2.setText("Mana:");
        } else if (vista.rbNPC.isSelected()) {
            vista.lblCampo1.setText("Ubicacion:");
            vista.lblCampo2.setText("Misiones:");
        } else {
            vista.lblCampo1.setText("Tipo de ataque:");
            vista.lblCampo2.setText("Runas que da:");
        }
    }

    private void nuevoPersonaje() {
        if (!datosCorrectos()) {
            return;
        }
        if (modelo.existeNombre(vista.txtNombre.getText().trim())) {
            Util.mensajeError("Ya existe un personaje con ese nombre");
            return;
        }
        modelo.altaPersonaje(leerFormulario());
        refrescarLista();
        limpiarCampos();
    }

    private void modificarPersonaje() {
        int posicion = vista.listPersonajes.getSelectedIndex();
        if (posicion == -1) {
            Util.mensajeError("Selecciona un personaje de la lista");
            return;
        }
        if (!datosCorrectos()) {
            return;
        }
        // Si le cambia el nombre, compruebo que el nuevo no lo tenga otro personaje
        String nombreNuevo = vista.txtNombre.getText().trim();
        String nombreActual = modelo.obtenerPersonajes().get(posicion).getNombre();
        if (!nombreNuevo.equalsIgnoreCase(nombreActual) && modelo.existeNombre(nombreNuevo)) {
            Util.mensajeError("Ya existe un personaje con ese nombre");
            return;
        }
        modelo.modificarPersonaje(posicion, leerFormulario());
        refrescarLista();
        limpiarCampos();
    }

    private void eliminarPersonaje() {
        int posicion = vista.listPersonajes.getSelectedIndex();
        if (posicion == -1) {
            Util.mensajeError("Selecciona un personaje de la lista");
            return;
        }
        int respuesta = Util.mensajeConfirmacion("¿Seguro que quieres eliminar este personaje?", "Eliminar");
        if (respuesta == JOptionPane.YES_OPTION) {
            modelo.eliminarPersonaje(posicion);
            refrescarLista();
            limpiarCampos();
        }
    }

    private void exportar() {
        if (modelo.obtenerPersonajes().isEmpty()) {
            Util.mensajeError("No hay personajes para exportar");
            return;
        }
        JFileChooser selector = Util.crearSelectorFichero("Ficheros XML", "xml");
        int opcion = selector.showSaveDialog(null);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File fichero = selector.getSelectedFile();
            // Si el usuario no escribe la extension, se la pongo yo
            if (!fichero.getName().endsWith(".xml")) {
                fichero = new File(fichero.getPath() + ".xml");
            }
            try {
                modelo.exportarXML(fichero);
                Util.mensajeInformacion("Personajes exportados correctamente");
            } catch (Exception ex) {
                Util.mensajeError("No se ha podido exportar el fichero");
            }
        }
    }

    private void importar() {
        JFileChooser selector = Util.crearSelectorFichero("Ficheros XML", "xml");
        int opcion = selector.showOpenDialog(null);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            try {
                modelo.importarXML(selector.getSelectedFile());
                refrescarLista();
                limpiarCampos();
                Util.mensajeInformacion("Personajes importados correctamente");
            } catch (Exception ex) {
                Util.mensajeError("El fichero no es correcto o no se puede leer");
            }
        }
    }

    private Personaje leerFormulario() {
        String nombre = vista.txtNombre.getText().trim();
        LocalDate fecha = vista.datePicker.getDate();
        int nivel = Integer.parseInt(vista.txtNivel.getText().trim());
        double vida = Double.parseDouble(vista.txtVida.getText().trim());
        String descripcion = vista.txtDescripcion.getText().trim();
        String campo1 = vista.txtCampo1.getText().trim();
        int campo2 = Integer.parseInt(vista.txtCampo2.getText().trim());

        Personaje personaje;
        if (vista.rbJugador.isSelected()) {
            personaje = new Jugador(nombre, fecha, nivel, vida, descripcion, campo1, campo2);
        } else if (vista.rbNPC.isSelected()) {
            personaje = new NPC(nombre, fecha, nivel, vida, descripcion, campo1, campo2);
        } else {
            personaje = new Enemigo(nombre, fecha, nivel, vida, descripcion, campo1, campo2);
        }
        return personaje;
    }

    private boolean datosCorrectos() {
        if (vista.txtNombre.getText().trim().isEmpty()
                || vista.datePicker.getDate() == null
                || vista.txtNivel.getText().trim().isEmpty()
                || vista.txtVida.getText().trim().isEmpty()
                || vista.txtCampo1.getText().trim().isEmpty()
                || vista.txtCampo2.getText().trim().isEmpty()) {
            Util.mensajeError("Rellena todos los campos (la descripcion es opcional)");
            return false;
        }

        if (vista.datePicker.getDate().isAfter(LocalDate.now())) {
            Util.mensajeError("La fecha no puede ser posterior a hoy");
            return false;
        }

        try {
            Integer.parseInt(vista.txtNivel.getText().trim());
            Double.parseDouble(vista.txtVida.getText().trim());
            Integer.parseInt(vista.txtCampo2.getText().trim());
        } catch (NumberFormatException ex) {
            Util.mensajeError("Nivel, vida y " + vista.lblCampo2.getText() + " tienen que ser numeros (la vida con punto, ej: 150.5)");
            return false;
        }
        return true;
    }

    private void refrescarLista() {
        vista.dlmPersonajes.clear();
        for (Personaje personaje : modelo.obtenerPersonajes()) {
            vista.dlmPersonajes.addElement(personaje);
        }
    }

    private void limpiarCampos() {
        vista.txtNombre.setText("");
        vista.datePicker.setDate(null);
        vista.txtNivel.setText("");
        vista.txtVida.setText("");
        vista.txtCampo1.setText("");
        vista.txtCampo2.setText("");
        vista.txtDescripcion.setText("");
        vista.listPersonajes.clearSelection();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        Personaje personaje = vista.listPersonajes.getSelectedValue();
        if (personaje == null) {
            return;
        }

        vista.txtNombre.setText(personaje.getNombre());
        vista.datePicker.setDate(personaje.getFechaNacimiento());
        vista.txtNivel.setText(String.valueOf(personaje.getNivel()));
        vista.txtVida.setText(String.valueOf(personaje.getVida()));
        vista.txtDescripcion.setText(personaje.getDescripcion());

        if (personaje instanceof Jugador) {
            Jugador jugador = (Jugador) personaje;
            vista.rbJugador.setSelected(true);
            vista.txtCampo1.setText(jugador.getArma());
            vista.txtCampo2.setText(String.valueOf(jugador.getMana()));
        } else if (personaje instanceof NPC) {
            NPC npc = (NPC) personaje;
            vista.rbNPC.setSelected(true);
            vista.txtCampo1.setText(npc.getUbicacion());
            vista.txtCampo2.setText(String.valueOf(npc.getMisiones()));
        } else {
            Enemigo enemigo = (Enemigo) personaje;
            vista.rbEnemigo.setSelected(true);
            vista.txtCampo1.setText(enemigo.getTipoAtaque());
            vista.txtCampo2.setText(String.valueOf(enemigo.getRecompensaRunas()));
        }
        cambiarCampos();
    }
        @Override
        public void windowClosing (WindowEvent e){
            int respuesta = Util.mensajeConfirmacion("¿Seguro que quieres salir?", "Salir");
            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }

        // Metodos de WindowListener que no uso, pero hay que ponerlos por implementar la interfaz
        @Override
        public void windowOpened (WindowEvent e){
        }

        @Override
        public void windowClosed (WindowEvent e){
        }

        @Override
        public void windowIconified (WindowEvent e){
        }

        @Override
        public void windowDeiconified (WindowEvent e){
        }

        @Override
        public void windowActivated (WindowEvent e){
        }

        @Override
        public void windowDeactivated (WindowEvent e){
        }
    }