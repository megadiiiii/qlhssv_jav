package org.example;


import org.example.Controller.LoginController;
import org.example.DAO.LoginDAO;
import org.example.View.LoginView;
import org.example.View.MainFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            LoginView view = new LoginView();
//            LoginDAO dao = new LoginDAO();
//            new LoginController(view, dao);
//            view.setVisible(true);
//        });
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
