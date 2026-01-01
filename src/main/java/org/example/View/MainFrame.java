package org.example.View;

import org.example.Controller.FacuController;
import org.example.Controller.MajorController;
import org.example.Controller.MenuNavController;
import org.example.DAO.FacuDAO;
import org.example.DAO.MajorDAO;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainFrame() {
        setTitle("QLSV");

        // NAV GIỮ NGUYÊN
        MenuNavView menu = new MenuNavView();
        setJMenuBar(menu);

        // CONTENT ĐỔI BẰNG CARD
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);


        HomepageView homepage = new HomepageView();
        contentPanel.add(homepage, "HOME");


        contentPanel.add(new StudentView(), "SV");

        FacultiesView facuView = new FacultiesView();
        FacuDAO dao = new FacuDAO();
        new FacuController(facuView, this, dao);
        contentPanel.add(facuView, "FACU");


        MajorView majorView = new MajorView();
        MajorDAO majorDAO = new MajorDAO();
        FacuDAO facuDAO = new FacuDAO();
        new MajorController(majorView, this, majorDAO, facuDAO);
        contentPanel.add(majorView, "MAJOR");
        contentPanel.add(new ClassView(), "CLASS");
        contentPanel.add(new RewardView(), "KT");

        add(contentPanel);

        setSize(1340, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        new MenuNavController(this, menu);
    }

    public void showView(String key) {
        cardLayout.show(contentPanel, key);
    }

    public void setContent(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
    }
}

