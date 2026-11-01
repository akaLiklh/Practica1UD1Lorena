package com.lorena.eldenring;

import util.Util;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                Util.show("Elden Ring", new FrontPage().getPanel()));
    }
}