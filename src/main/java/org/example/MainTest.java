package org.example;

import org.example.Controller.RoleController;
import org.example.DAO.RoleDAO;
import org.example.View.RoleView;

import javax.swing.*;

public class MainTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý vai trò sinh viên");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 750);
            frame.setLocationRelativeTo(null);

            RoleView view = new RoleView();
            RoleDAO dao = new RoleDAO();

            // MainFrame = null giống MaintestGV
            new RoleController(view, null, dao);

            frame.setContentPane(view);
            frame.setVisible(true);
        });
    }
}
