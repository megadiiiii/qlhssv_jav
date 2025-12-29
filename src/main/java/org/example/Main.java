package org.example;

import org.example.Controller.ClassController;
import org.example.View.ClassView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý lớp");

            ClassView view = new ClassView();
            new ClassController(view);

            frame.setContentPane(view);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
