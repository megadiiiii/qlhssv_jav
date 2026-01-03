package org.example.Controller;

import org.example.DAO.LoginDAO;
import org.example.Model.Account;
import org.example.View.LoginView;
import org.example.View.MainFrame;

import javax.swing.*;
import java.sql.SQLException;

public class LoginController {
    private final LoginView view;
    private final LoginDAO dao;

    public LoginController(LoginView view, LoginDAO dao) {
        this.view = view;
        this.dao = dao;

        initAction();
    }


    public void initAction() {
        view.btnLogin.addActionListener(e -> {
            String username = view.txtUsername.getText().trim();
            String password = String.valueOf(view.txtPassword.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập username và password");
                return;
            }

            Account account;
            try {
                account = dao.login(username, password);

                if (account != null) {
                    JOptionPane.showMessageDialog(view,
                            "Xin chào " + account.getName());
                    view.dispose();
                    new MainFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(view, "Sai username hoặc password");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}