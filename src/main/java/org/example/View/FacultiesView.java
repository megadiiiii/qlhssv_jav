package org.example.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FacultiesView extends JPanel {
    public FacultiesView() {
        initUI();
    }

    public JTextField txtFacuId, txtFacuName;
    public JLabel lblFacuId, lblFacuName;
    public JButton btnAddFacu, btnBack, btnEditFacu, btnDeleteFacu;
    public DefaultTableModel model;
    public JScrollPane scrollPane;
    public JTable table;

    private void initUI() {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("Quản lý thông tin khoa");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        add(lblTitle, BorderLayout.NORTH);

        add(formInfoInit(), BorderLayout.CENTER);
        tableInit();
    }

    private JPanel formInfoInit() {
        JPanel container = new JPanel(new BorderLayout());

        // ===== TITLE =====
        JLabel lblInfoTitle = new JLabel("Thông tin");
        lblInfoTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInfoTitle.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 30));
        container.add(lblInfoTitle, BorderLayout.NORTH);

        JPanel form = new JPanel();
        GroupLayout layout = new GroupLayout(form);
        form.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        lblFacuId = new JLabel("Mã khoa");
        txtFacuId = new JTextField();

        lblFacuName = new JLabel("Tên khoa");
        txtFacuName = new JTextField();

        btnAddFacu = new JButton("Thêm");
        btnDeleteFacu = new JButton("Xóa");
        btnEditFacu = new JButton("Sửa");
        btnBack = new JButton("Quay lại");


        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblFacuId)
                                .addComponent(lblFacuName)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtFacuId)
                                .addComponent(txtFacuName)
                                .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                        .addComponent(btnAddFacu)
                                        .addComponent(btnDeleteFacu)
                                        .addComponent(btnEditFacu)
                                        .addComponent(btnBack)
                                )
                        )
                        .addGap(30)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblFacuId)
                                .addComponent(txtFacuId)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblFacuName)
                                .addComponent(txtFacuName)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAddFacu)
                                .addComponent(btnDeleteFacu)
                                .addComponent(btnEditFacu)
                                .addComponent(btnBack)
                        )
        );
        container.add(form, BorderLayout.CENTER);
        return container;
    }

    private void tableInit() {
        model = new DefaultTableModel(
                new String[]{"Mã khoa", "Tên khoa"}, 0
        );

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16)); // font, đậm
        table.getTableHeader().setBackground(Color.LIGHT_GRAY);
        table.getTableHeader().setForeground(Color.BLACK);

        scrollPane = new JScrollPane(table);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.SOUTH);
    }

}
