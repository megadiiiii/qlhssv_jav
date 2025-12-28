package org.example;
import javax.swing.*;
import org.example.View.Discipline;
public class Maintest {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Quản lý kỷ luật");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1100, 750);
                frame.setLocationRelativeTo(null);
                frame.setContentPane(new Discipline());
                frame.setVisible(true);
            });
        }
    }


