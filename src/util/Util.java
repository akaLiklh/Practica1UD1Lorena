package util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class Util {

    // Paleta de colores fija y personalización acorde a la temática.
    public static final Color GREEN = new Color(10, 67, 61);
    public static final Color GOLDEN = new Color(169, 142, 85);

    public static void styleButton(JButton b) {
        b.setBackground(GREEN);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(GOLDEN, 1));
    }

    public static void styleLabel(JLabel l) {
        l.setForeground(GOLDEN);
    }

    public static void styleTextField(JTextField t) {
        t.setBackground(GREEN);
        t.setForeground(Color.WHITE);
        t.setCaretColor(GOLDEN);
        t.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(GOLDEN, 1), BorderFactory.createEmptyBorder(2, 6, 2, 6)));
    }

    public static void styleTextArea(JTextArea t) {
        t.setBackground(GREEN);
        t.setForeground(Color.WHITE);
        t.setCaretColor(GOLDEN);
        t.setLineWrap(true);
        t.setWrapStyleWord(true);
        t.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(GOLDEN, 1), BorderFactory.createEmptyBorder(4, 6, 4, 6)));
    }

    public static void styleComboBox(JComboBox<?> c) {
        c.setBackground(GREEN);
        c.setForeground(Color.WHITE);
        c.setBorder(new LineBorder(GOLDEN, 1));
    }

    public static void styleRadioButton(JRadioButton r) {
        r.setOpaque(false);
        r.setForeground(GOLDEN);
        r.setFocusPainted(false);
    }

    private static final Dimension SCREEN_DIMENSION = new Dimension(800, 650);

    public static void switchScreens(Component origin, JPanel newPanel) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(origin);
        frame.setContentPane(newPanel);
        frame.setResizable(true);
        frame.setSize(SCREEN_DIMENSION);
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    public static void goFrontPage(Component origin, JPanel frontPage) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(origin);
        frame.setContentPane(frontPage);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    public static JPanel centerPanel(JPanel panel) {
        JPanel centeredPanel = new JPanel(new GridBagLayout());
        centeredPanel.setBackground(Color.BLACK);
        centeredPanel.add(panel);

        return centeredPanel;
    }

    // Ventana portada.
    public static JFrame show(String title, JPanel panel, boolean resizable) {
        JFrame frame = new JFrame(title);
        frame.setIconImage(new ImageIcon(
                Util.class.getResource("/com/lorena/eldenring/images/icon.png")).getImage());
        frame.setContentPane(panel);
        frame.setResizable(resizable);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
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

    // Ventana normal.
    public static JFrame show(String title, JPanel panel) {
        return show(title, panel, true);
    }

    // Mensajes de error y de información.
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirmExit(Component parent) {
        return JOptionPane.showConfirmDialog(parent, "¿Seguro que quieres salir de la aplicación?",
                "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    // Gestión de los ficheros.
    public static boolean confirmOverwrite(Component parent, File file) {
        return JOptionPane.showConfirmDialog(parent,
                "El fichero " + file.getName() + " ya existe. ¿Quieres sobrescribirlo?",
                "Sobrescribir", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public static JFileChooser createFileChooser(File defaultPath, String fileType, String extension) {
        JFileChooser fileChooser = new JFileChooser();
        if (defaultPath != null) {
            fileChooser.setCurrentDirectory(defaultPath);
        }
        if (extension != null) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType, extension);
            fileChooser.setFileFilter(filter);
        }
        return fileChooser;
    }

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

    public static File chooseFileToOpen(Component parent, String extension) {
        JFileChooser fileChooser = createFileChooser(new File("."), "Ficheros " + extension.toUpperCase(), extension);
        return fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile() : null;
    }
}