package org.example.View;

import org.example.Controller.LoginController;
import org.example.DAO.LoginDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginView extends JFrame {
    public JLabel lblUsername, lblPassword;
    public JTextField txtUsername;
    public JPasswordField txtPassword;
    public JButton btnLogin;

    public LoginView() {
        setTitle("Đăng nhập");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(400, 170);
        init();
        setVisible(true);
    }

    public void init() {
        JPanel container = new JPanel(new BorderLayout());

        lblUsername = new JLabel("Username:");
        txtUsername = new JTextField();
        lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField();

        btnLogin = new JButton("Login");

        JPanel formPanel = new JPanel();
        GroupLayout formLayout = new GroupLayout(formPanel);
        formPanel.setLayout(formLayout);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formLayout.setAutoCreateGaps(true);
        formLayout.setAutoCreateContainerGaps(true);

        formLayout.setHorizontalGroup(
                formLayout.createSequentialGroup()
                        .addGroup(
                                formLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblUsername)
                                        .addComponent(lblPassword)
                        )
                        .addGroup(
                                formLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(txtUsername, 200, 200, Short.MAX_VALUE)
                                        .addComponent(txtPassword, 200, 200, Short.MAX_VALUE)
                        )
        );



        formLayout.setVerticalGroup(
                formLayout.createSequentialGroup()
                        .addGroup(
                                formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblUsername)
                                        .addComponent(txtUsername)
                        )
                        .addGroup(
                                formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblPassword)
                                        .addComponent(txtPassword)
                        )
                        .addGap(10)
        );

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(btnLogin);


        container.add(formPanel, BorderLayout.CENTER);
        container.add(btnPanel, BorderLayout.SOUTH);
        add(container);
    }

    public static void main(String[] args) throws SQLException {
        LoginView view = new LoginView();
        LoginDAO dao = new LoginDAO();
        new LoginController(view, dao);
    }

}
