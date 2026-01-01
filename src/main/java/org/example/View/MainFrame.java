package org.example.View;

import org.example.Controller.*;
import org.example.DAO.CohortDAO;
import org.example.DAO.DisciplineDAO;
import org.example.DAO.FacuDAO;
//import org.example.DAO.StudentDAO;

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

//        StudentView studentView = new StudentView();
//        StudentDAO studentDAO = new StudentDAO();
//        new StudentController(studentView, this, studentDAO);
//        contentPanel.add(studentView, "SV");

        FacultiesView facuView = new FacultiesView();
        FacuDAO dao = new FacuDAO();
        new FacuController(facuView, this, dao);
        contentPanel.add(facuView, "FACU");

        contentPanel.add(new MajorView(), "MAJOR");
        contentPanel.add(new ClassView(), "CLASS");

        CohortView cohortView = new CohortView();
        CohortDAO cohortDAO = new CohortDAO();
        new CohortController(cohortView, this, cohortDAO);
        contentPanel.add(cohortView, "COHORT");

        RewardView rewardView = new RewardView();
        new RewardController(rewardView);
        contentPanel.add(rewardView, "KT");

        DisciplineView disciplineView = new DisciplineView();
        DisciplineDAO disciplineDAO = new DisciplineDAO();
        new DisciplineController(disciplineView, this, disciplineDAO);
        contentPanel.add(disciplineView, "KL");

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

