package org.example.View;

import org.example.Model.Faculties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;




public class MajorView extends JPanel {

    public JTextField txtMajorId, txtMajorName;
    public JComboBox<Faculties> cboFacuName;

    public JLabel lblMajorId, lblMajorName, lblFacuName;
    public JButton btnAddMajor, btnBack, btnEditMajor, btnDeleteMajor;
    public DefaultTableModel model;
    public JScrollPane scrollPane;
    public JTable table;

    public MajorView() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Quản lý thông tin chuyên ngành");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(lblTitle, BorderLayout.NORTH);
        topPanel.add(formInfoInit(), BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        tableInit(); // table ở CENTER
    }


    private JPanel formInfoInit() {
        JPanel container = new JPanel(new BorderLayout());

        JLabel lblInfoTitle = new JLabel("Thông tin");
        lblInfoTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInfoTitle.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 30));
        container.add(lblInfoTitle, BorderLayout.NORTH);

        JPanel form = new JPanel();
        GroupLayout layout = new GroupLayout(form);
        form.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        lblMajorId = new JLabel("Mã ngành");
        txtMajorId = new JTextField();

        lblMajorName = new JLabel("Tên ngành");
        txtMajorName = new JTextField();

        lblFacuName = new JLabel("Tên khoa");
        cboFacuName = new JComboBox<>();

        btnAddMajor = new JButton("Thêm");
        btnDeleteMajor = new JButton("Xóa");
        btnEditMajor = new JButton("Sửa");
        btnBack = new JButton("Quay lại");

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblMajorId)
                                .addComponent(lblMajorName)
                                .addComponent(lblFacuName)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtMajorId, 200, 200, 200)
                                .addComponent(txtMajorName, 200, 200, 200)
                                .addComponent(cboFacuName, 200, 200, 200)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAddMajor)
                                        .addComponent(btnDeleteMajor)
                                        .addComponent(btnEditMajor)
                                        .addComponent(btnBack)
                                )
                        )
                        .addGap(30)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblMajorId)
                                .addComponent(txtMajorId)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblMajorName)
                                .addComponent(txtMajorName)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblFacuName)
                                .addComponent(cboFacuName)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAddMajor)
                                .addComponent(btnDeleteMajor)
                                .addComponent(btnEditMajor)
                                .addComponent(btnBack)
                        )
        );

        container.add(form, BorderLayout.CENTER);
        return container;
    }

    private void tableInit() {
        model = new DefaultTableModel(
                new String[]{"Mã ngành", "Tên ngành", "Mã khoa", "Tên khoa"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));

        scrollPane = new JScrollPane(table);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

    }
}