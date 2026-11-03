package com.lorena.eldenring;

import com.lorena.eldenring.gui.FrontPage;
import util.Util;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        UIManager.put("Button.focus", new javax.swing.plaf.ColorUIResource(new Color(0, 0, 0, 0)));

        SwingUtilities.invokeLater(() ->
                Util.show("Elden Ring", new FrontPage().getPanel(), false));
    }
}