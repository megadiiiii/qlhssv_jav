package org.example.View;

import javax.swing.*;
import java.awt.*;

public class MajorView extends JPanel {
    public MajorView() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("Quản lý thông tin chuyên ngành");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        add(lblTitle, BorderLayout.NORTH);
    }
}
