package util;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Clase de utilidades usada por todas las vistas.
 * <p>
 * Contiene:
 * <ul>
 *     <li>Estilos (colores de la temática) para botones, etiquetas, campos, listas...</li>
 *     <li>Creación de la ventana y cambio de pantallas.</li>
 *     <li>Mensajes de error, información y confirmación (incluido el de cerrar la aplicación).</li>
 *     <li>Selectores de ficheros para importar y exportar.</li>
 * </ul>
 *
 * @author Lorena
 */
public class Util {

    /** Verde oscuro de la temática (fondos de componentes). */
    public static final Color GREEN = new Color(10, 67, 61);
    /** Dorado de la temática (textos y bordes). */
    public static final Color GOLDEN = new Color(169, 142, 85);

    /** Tamaño de la ventana en las pantallas de formulario y de gestión. */
    private static final Dimension SCREEN_DIMENSION = new Dimension(800, 650);

    /** Clase de utilidades: no se crean objetos. */
    private Util() {
    }

    // ------------------------------------------------------------------ Estilos

    /** @param b botón al que se aplica el estilo verde con borde dorado */
    public static void styleButton(JButton b) {
        b.setBackground(GREEN);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(GOLDEN, 1));
    }

    /** @param l etiqueta a la que se pone el texto dorado */
    public static void styleLabel(JLabel l) {
        l.setForeground(GOLDEN);
    }

    /** @param t campo de texto con fondo verde y borde dorado */
    public static void styleTextField(JTextField t) {
        t.setBackground(GREEN);
        t.setForeground(Color.WHITE);
        t.setCaretColor(GOLDEN);
        t.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(GOLDEN, 1), BorderFactory.createEmptyBorder(2, 6, 2, 6)));
    }

    /** @param t área de texto con fondo verde, ajuste de línea y margen interior */
    public static void styleTextArea(JTextArea t) {
        t.setBackground(GREEN);
        t.setForeground(Color.WHITE);
        t.setCaretColor(GOLDEN);
        t.setLineWrap(true);
        t.setWrapStyleWord(true);
        t.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
    }

    /** @param c desplegable con fondo verde y borde dorado */
    public static void styleComboBox(JComboBox<?> c) {
        c.setBackground(GREEN);
        c.setForeground(Color.WHITE);
        c.setBorder(new LineBorder(GOLDEN, 1));
    }

    /** @param list lista con fondo verde y selección dorada */
    public static void styleList(JList<?> list) {
        list.setBackground(GREEN);
        list.setForeground(Color.WHITE);
        list.setSelectionBackground(GOLDEN);
        list.setSelectionForeground(Color.BLACK);
    }

    /** @param scroll panel de desplazamiento con borde dorado */
    public static void styleScrollPane(JScrollPane scroll) {
        scroll.setBorder(new LineBorder(GOLDEN, 1));
        scroll.getViewport().setBackground(GREEN);
    }

    /** @param datePicker selector de fecha: se estiliza su campo de texto y su botón */
    public static void styleDatePicker(DatePicker datePicker) {
        styleTextField(datePicker.getComponentDateTextField());
        styleButton(datePicker.getComponentToggleCalendarButton());
    }

    /** @param r radio button transparente con texto dorado */
    public static void styleRadioButton(JRadioButton r) {
        r.setOpaque(false);
        r.setForeground(GOLDEN);
        r.setFocusPainted(false);
    }

    // ---------------------------------------------------------------- Pantallas

    /**
     * Cambia el contenido de la ventana por otro panel (formulario o gestión).
     *
     * @param origin   cualquier componente de la ventana actual
     * @param newPanel panel que se va a mostrar
     */
    public static void switchScreens(Component origin, JPanel newPanel) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(origin);
        frame.setContentPane(newPanel);
        frame.setResizable(true);
        frame.setSize(SCREEN_DIMENSION);
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Mete un panel en el centro de otro panel negro, para que quede centrado en la ventana.
     *
     * @param panel panel a centrar
     * @return panel negro que contiene al anterior en el centro
     */
    public static JPanel centerPanel(JPanel panel) {
        JPanel centeredPanel = new JPanel(new GridBagLayout());
        centeredPanel.setBackground(Color.BLACK);
        centeredPanel.add(panel);
        return centeredPanel;
    }

    /**
     * Busca dentro de un contenedor el botón que tiene un texto. Se usa para los botones
     * del .form que no tienen un campo enlazado en la clase.
     *
     * @param contenedor panel donde buscar (también busca en sus subpaneles)
     * @param texto      texto exacto del botón
     * @return el botón encontrado
     * @throws IllegalStateException si no hay ningún botón con ese texto
     */
    public static JButton buscarBoton(Container contenedor, String texto) {
        JButton boton = buscarBotonRecursivo(contenedor, texto);
        if (boton == null) {
            throw new IllegalStateException("No se encuentra el botón \"" + texto + "\"");
        }
        return boton;
    }

    /** Recorre el contenedor y sus subpaneles; devuelve null si no encuentra el botón. */
    private static JButton buscarBotonRecursivo(Container contenedor, String texto) {
        for (Component c : contenedor.getComponents()) {
            if (c instanceof JButton && texto.equals(((JButton) c).getText())) {
                return (JButton) c;
            }
            if (c instanceof JPanel) {
                JButton encontrado = buscarBotonRecursivo((JPanel) c, texto);
                if (encontrado != null) {
                    return encontrado;
                }
            }
        }
        return null;
    }

    /**
     * Crea la ventana de la aplicación con el icono y el aviso al cerrar.
     *
     * @param title     título de la ventana
     * @param panel     panel inicial (la portada)
     * @param resizable si se puede cambiar el tamaño
     * @return la ventana creada y visible
     */
    public static JFrame show(String title, JPanel panel, boolean resizable) {
        JFrame frame = new JFrame(title);
        frame.setIconImage(new ImageIcon(
                Util.class.getResource("/com/lorena/eldenring/images/icon.png")).getImage());
        frame.setContentPane(panel);
        frame.setResizable(resizable);
        frame.pack();
        // Al pulsar la X se pregunta antes de salir.
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (confirmExit(frame)) {
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    /**
     * Crea una ventana redimensionable.
     *
     * @param title título de la ventana
     * @param panel panel a mostrar
     * @return la ventana creada
     */
    public static JFrame show(String title, JPanel panel) {
        return show(title, panel, true);
    }

    // ----------------------------------------------------------------- Mensajes

    /**
     * @param parent  componente sobre el que se centra el mensaje
     * @param message texto del error
     */
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @param parent  componente sobre el que se centra el mensaje
     * @param message texto informativo
     */
    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Pregunta Sí / No.
     *
     * @param parent  componente sobre el que se centra el mensaje
     * @param message pregunta
     * @param title   título del cuadro de diálogo
     * @return true si se pulsa Sí
     */
    public static boolean confirm(Component parent, String message, String title) {
        return JOptionPane.showConfirmDialog(parent, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    /**
     * Cuadro de diálogo del botón cerrar.
     *
     * @param parent ventana
     * @return true si se confirma la salida
     */
    public static boolean confirmExit(Component parent) {
        return confirm(parent, "¿Seguro que quieres salir de la aplicación?", "Salir");
    }

    // ----------------------------------------------------------------- Ficheros

    /**
     * @param parent componente sobre el que se centra el mensaje
     * @param file   fichero que ya existe
     * @return true si se quiere sobrescribir
     */
    public static boolean confirmOverwrite(Component parent, File file) {
        return JOptionPane.showConfirmDialog(parent,
                "El fichero " + file.getName() + " ya existe. ¿Quieres sobrescribirlo?",
                "Sobrescribir", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    /**
     * Crea un selector de ficheros con un filtro por extensión.
     *
     * @param defaultPath carpeta inicial (puede ser null)
     * @param fileType    descripción del filtro
     * @param extension   extensión sin punto (puede ser null para no filtrar)
     * @return el selector configurado
     */
    public static JFileChooser createFileChooser(File defaultPath, String fileType, String extension) {
        JFileChooser fileChooser = new JFileChooser();
        if (defaultPath != null) {
            fileChooser.setCurrentDirectory(defaultPath);
        }
        if (extension != null) {
            fileChooser.setFileFilter(new FileNameExtensionFilter(fileType, extension));
        }
        return fileChooser;
    }

    /**
     * Pide un fichero para guardar. Añade la extensión si falta y pregunta si se sobrescribe.
     *
     * @param parent    componente padre del diálogo
     * @param extension extensión sin punto, por ejemplo "xml"
     * @return fichero elegido o null si se cancela
     */
    public static File chooseFileToSave(Component parent, String extension) {
        JFileChooser fileChooser = createFileChooser(new File("."), "Ficheros " + extension.toUpperCase(), extension);
        if (fileChooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        File file = fileChooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith("." + extension)) {
            file = new File(file.getAbsolutePath() + "." + extension);
        }
        if (file.exists() && !confirmOverwrite(parent, file)) {
            return null;
        }
        return file;
    }

    /**
     * Pide un fichero para abrir.
     *
     * @param parent    componente padre del diálogo
     * @param extension extensión sin punto, por ejemplo "xml"
     * @return fichero elegido o null si se cancela
     */
    public static File chooseFileToOpen(Component parent, String extension) {
        JFileChooser fileChooser = createFileChooser(new File("."), "Ficheros " + extension.toUpperCase(), extension);
        return fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile() : null;
    }
}
