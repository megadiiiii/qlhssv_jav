package org.example.View;

import javax.swing.*;
import java.awt.*;

public class HomepageView extends JPanel {

    public JButton btnClass, btnCohort, btnDiscipline, btnFaculties, btnMajor, btnReward, btnRole, btnScholarship, btnStudent, btnSuspension, btnTeacher;

    public HomepageView() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        // ===== TITLE =====
        JLabel lblTitle = new JLabel("TRANG CHỦ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(lblTitle, BorderLayout.NORTH);

        // ===== CENTER PANEL =====
        JPanel centerPanel = new JPanel(new GridLayout(2, 5, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        btnStudent = new JButton("Quản lý sinh viên");
        btnClass = new JButton("Quản lý lớp");
        btnCohort = new JButton("Quản lý khóa đào tạo");
        btnMajor = new JButton("Quản lý chuyên ngành");
        btnFaculties = new JButton("Quản lý khoa");

        btnTeacher = new JButton("Quản lý giảng viên");
        btnSuspension = new JButton("Quản lý bảo lưu");
        btnReward = new JButton("Quản lý khen thưởng");
        btnDiscipline = new JButton("Quản lý kỷ luật");
        btnScholarship = new JButton("Quản lý học bổng");
        btnRole = new JButton("Quản lý cán sự");

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 14);

        JButton[] buttons = {
                btnStudent, btnClass, btnCohort, btnMajor, btnFaculties,
                btnTeacher, btnSuspension, btnReward, btnDiscipline, btnScholarship, btnRole
        };

        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            centerPanel.add(btn);
        }


        add(centerPanel, BorderLayout.CENTER);
    }
}
