package org.example.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RewardView  extends JPanel {
    public RewardView() {
        init();
    }


    public JTextField txtStudentId, txtRewardDate, txtDecision;
    public JTextArea txtReason;
    public JButton btnAdd, btnEdit, btnDelete, btnBack;
    public JTable table;
    public DefaultTableModel model;


    private void init() {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("Quản lý khen thưởng");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        add(lblTitle, BorderLayout.NORTH);
    }
    private JPanel formInit() {
        JPanel container = new JPanel(new BorderLayout());

        JLabel lblInfo = new JLabel("Thông tin khen thưởng");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInfo.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 30));
        container.add(lblInfo, BorderLayout.NORTH);

        JPanel form = new JPanel();
        GroupLayout layout = new GroupLayout(form);
        form.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel lblStudentId = new JLabel("Mã sinh viên");
        JLabel lblRewardDate = new JLabel("Ngày khen thưởng");
        JLabel lblDecision = new JLabel("Quyết định");
        JLabel lblReason = new JLabel("Lý do");

        txtStudentId = new JTextField();
        txtRewardDate = new JTextField();
        txtDecision = new JTextField();
        txtReason = new JTextArea(3, 20);
        JScrollPane reasonScroll = new JScrollPane(txtReason);

        btnAdd = new JButton("Lưu");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnBack = new JButton("Quay lại");

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblStudentId)
                                .addComponent(lblRewardDate)
                                .addComponent(lblDecision)
                                .addComponent(lblReason)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtStudentId)
                                .addComponent(txtRewardDate)
                                .addComponent(txtDecision)
                                .addComponent(reasonScroll)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAdd)
                                        .addComponent(btnEdit)
                                        .addComponent(btnDelete)
                                        .addComponent(btnBack)
                                )
                        )
                        .addGap(30)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblStudentId)
                                .addComponent(txtStudentId)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblRewardDate)
                                .addComponent(txtRewardDate)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDecision)
                                .addComponent(txtDecision)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblReason)
                                .addComponent(reasonScroll)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAdd)
                                .addComponent(btnEdit)
                                .addComponent(btnDelete)
                                .addComponent(btnBack)
                        )
        );

        container.add(form, BorderLayout.CENTER);
        return container;
    }
    private void tableInit() {
        model = new DefaultTableModel(
                new String[]{"Mã SV", "Ngày KT", "Quyết định", "Lý do"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        add(scrollPane, BorderLayout.SOUTH);
    }
}
