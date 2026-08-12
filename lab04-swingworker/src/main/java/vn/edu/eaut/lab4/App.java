package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new B1CountdownFrame().setVisible(true);
        });
    }
}