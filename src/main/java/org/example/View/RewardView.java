package org.example.View;

import javax.swing.*;
import java.awt.*;

public class RewardView  extends JPanel {
    public RewardView() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("Quản lý khen thưởng");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        add(lblTitle, BorderLayout.NORTH);
    }
}
