package org.example;

import org.example.Controller.RoleController;
import org.example.DAO.RoleDAO;
import org.example.View.MainFrame;
import org.example.View.RoleView;

import javax.swing.*;

public class MainTest
{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            // MainFrame của project m
            MainFrame mainFrame = new MainFrame();

            // View + DAO + Controller
            RoleView roleView = new RoleView();
            RoleDAO roleDAO = new RoleDAO();
            new RoleController(roleView, mainFrame, roleDAO);

            // Gắn RoleView vào frame để test
            mainFrame.setTitle("TEST ROLE");
            mainFrame.setContentPane(roleView);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(1100, 700);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }
}
