package org.example;

import org.example.Controller.DisciplineController;
import org.example.DAO.DisciplineDAO;
import org.example.View.DisciplineView;
import javax.swing.*;
public class Maintest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý kỷ luật");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 750);
            frame.setLocationRelativeTo(null);
            DisciplineView view = new DisciplineView();
            DisciplineDAO dao = new DisciplineDAO();
            new DisciplineController(view, null, dao);// chưa ghép mainFrame
            frame.setContentPane(view);
            frame.setVisible(true);
        });
    }
}


