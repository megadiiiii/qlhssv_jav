package org.example.View;

import org.example.Model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RoleView extends JPanel {

    // ===== INPUT =====
    public JTextField txtRoleId;
    public JComboBox<Student> cboStudent;
    public JComboBox<String> cboRole;

    // ===== BUTTON =====
    public JButton btnAdd, btnUpdate, btnDelete, btnBack;

    // ===== TABLE =====
    public JTable table;
    public DefaultTableModel model;

    public RoleView() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("Quản lý cán bộ lớp");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(lblTitle, BorderLayout.NORTH);

        add(buildForm(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
    }

    private JPanel buildForm() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Thông tin"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 10, 6, 10);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Row 0
        g.gridx = 0; g.gridy = 0;
        p.add(new JLabel("Role ID"), g);
        g.gridx = 1;
        txtRoleId = new JTextField();
        txtRoleId.setEditable(false);
        p.add(txtRoleId, g);

        g.gridx = 2;
        p.add(new JLabel("Sinh viên"), g);
        g.gridx = 3;
        cboStudent = new JComboBox<>();
        p.add(cboStudent, g);

        // Row 1
        g.gridx = 0; g.gridy = 1;
        p.add(new JLabel("Vai trò"), g);
        g.gridx = 1;
        cboRole = new JComboBox<>(new String[]{
                "Lớp trưởng",
                "Lớp phó",
                "Bí thư",
                "Phó bí thư",
                "Cán sự"
        });
        p.add(cboRole, g);

        // Buttons
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnBack = new JButton("Quay lại");

        btnBar.add(btnAdd);
        btnBar.add(btnUpdate);
        btnBar.add(btnDelete);
        btnBar.add(btnBack);

        g.gridx = 0; g.gridy = 2; g.gridwidth = 4;
        p.add(btnBar, g);

        return p;
    }

    private JScrollPane buildTable() {
        model = new DefaultTableModel(
                new String[]{"Role ID", "Mã SV", "Họ tên", "Lớp", "Vai trò"}, 0
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

        return new JScrollPane(table);
    }
}
