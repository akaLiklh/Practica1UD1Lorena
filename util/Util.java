package util;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class Util {
    public static void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void mensajeInformacion(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }

    public static int mensajeConfirmacion(String mensaje, String titulo) {
        return JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION);
    }

    public static JFileChooser crearSelectorFichero(String tipoArchivos, String extension) {
        JFileChooser selector = new JFileChooser();
        selector.setCurrentDirectory(new File("."));
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(tipoArchivos, extension);
        selector.setFileFilter(filtro);
        return selector;
    }
}