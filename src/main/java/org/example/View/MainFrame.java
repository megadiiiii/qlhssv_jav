package org.example.View;

import org.example.Controller.*;
import org.example.DAO.*;

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


        MajorView majorView = new MajorView();
        MajorDAO majorDAO = new MajorDAO();
        FacuDAO facuDAO = new FacuDAO();
        new MajorController(majorView, this, majorDAO, facuDAO);
        contentPanel.add(majorView, "MAJOR");


        ClassView classView = new ClassView();
        ClassDAO classDAO = new ClassDAO();
        new ClassController(classView, this, classDAO);
        contentPanel.add(classView, "CLASS");

        CohortView cohortView = new CohortView();
        CohortDAO cohortDAO = new CohortDAO();
        new CohortController(cohortView, this, cohortDAO);
        contentPanel.add(cohortView, "COHORT");

        TeacherView teacherView = new TeacherView();
        TeacherDAO teacherDAO = new TeacherDAO();
        new TeacherController(teacherView, this, teacherDAO);
        contentPanel.add(teacherView, "TEACHER");

        RewardView rewardView = new RewardView();
        RewardDAO rewardDAO = new RewardDAO();
        new RewardController(rewardView, this, rewardDAO);
        contentPanel.add(rewardView, "KT");

        contentPanel.add(new RewardView(), "KL");

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

