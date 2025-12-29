package org.example.View;

import org.example.Config.dbConn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentView extends JPanel {
    public DefaultTableModel model;
    public JScrollPane scrollPane;
    public JTable table;
    public StudentFormPanel sfp;
    public JButton btnAdd, btnEdit, btnDelete;

    public StudentView() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("Quản lý thông tin sinh viên");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        add(lblTitle, BorderLayout.NORTH);

        sfp = new StudentFormPanel();
        add(StudentView.this.sfp, BorderLayout.CENTER);
        // Panel phía dưới chứa button và table
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonInit(), BorderLayout.NORTH);
        southPanel.add(tableInit(), BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    private JPanel buttonInit(){
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");

        // Thiết lập kích thước và font cho các nút
        Dimension btnSize = new Dimension(100, 35);
        Font btnFont = new Font("Segoe UI", Font.PLAIN, 14);

        btnAdd.setPreferredSize(btnSize);
        btnAdd.setFont(btnFont);
        btnEdit.setPreferredSize(btnSize);
        btnEdit.setFont(btnFont);
        btnDelete.setPreferredSize(btnSize);
        btnDelete.setFont(btnFont);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        return buttonPanel;
    }

    private JPanel tableInit() {
        model = new DefaultTableModel(new String[]{"Mã SV", "Họ đệm", "Tên", "Ngày sinh", "Giới tính", "Lớp", "Khóa", "Chuyên ngành", "Khoa", "Trạng thái", "Quê quán", "SĐT", "Email", "Số CCCD"}, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(Color.LIGHT_GRAY);
        table.getTableHeader().setForeground(Color.BLACK);

        table.getColumnModel().getColumn(0).setPreferredWidth(120);  // Mã SV
        table.getColumnModel().getColumn(1).setPreferredWidth(150);  // Họ đệm
        table.getColumnModel().getColumn(2).setPreferredWidth(80);   // Tên
        table.getColumnModel().getColumn(3).setPreferredWidth(120);  // Ngày sinh
        table.getColumnModel().getColumn(4).setPreferredWidth(100);  // Giới tính
        table.getColumnModel().getColumn(5).setPreferredWidth(120);  // Lớp
        table.getColumnModel().getColumn(6).setPreferredWidth(100);  // Khóa
        table.getColumnModel().getColumn(7).setPreferredWidth(150);  // Chuyên ngành
        table.getColumnModel().getColumn(8).setPreferredWidth(150);  // Khoa
        table.getColumnModel().getColumn(9).setPreferredWidth(120);  // Trạng thái
        table.getColumnModel().getColumn(10).setPreferredWidth(150); // Quê quán
        table.getColumnModel().getColumn(11).setPreferredWidth(120); // SĐT
        table.getColumnModel().getColumn(12).setPreferredWidth(180); // Email
        table.getColumnModel().getColumn(13).setPreferredWidth(150); // CCCD

        scrollPane = new JScrollPane(table);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);

        return tablePanel;
    }
}