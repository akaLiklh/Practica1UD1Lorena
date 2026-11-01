package util;

import javax.swing.*;
import java.awt.*;

public class Util {
    public static JFrame show(String titulo, JPanel panel) {
        JFrame frame = new JFrame(titulo);
        frame.setContentPane(panel);
        frame.setSize(400,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setBackground(new Color(10, 67, 61));
        frame.setVisible(true);
        return frame;
    }
}
