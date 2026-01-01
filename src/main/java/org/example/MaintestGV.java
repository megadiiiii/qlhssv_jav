package org.example;

import org.example.Controller.TeacherController;
import org.example.DAO.TeacherDAO;
import org.example.View.TeacherView;

import javax.swing.*;

public class MaintestGV {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý giảng viên");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 750);
            frame.setLocationRelativeTo(null);

            TeacherView view = new TeacherView();
            TeacherDAO dao = new TeacherDAO();

            new TeacherController(view, null, dao); // tạm

            frame.setContentPane(view);
            frame.setVisible(true);
        });
    }
}
