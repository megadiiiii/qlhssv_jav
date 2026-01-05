// view
package org.example.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RoleView extends JPanel {

    // ===== INPUT (CRUD) =====
    public JTextField txtRoleId;

    public JTextField txtMaSinhVien;
    public JTextField txtTenSinhVien;

    public JComboBox<String> cboVaiTro;

    // ===== INPUT (SEARCH)  =====
    public JTextField txtRoleIdSearch, txtMaSinhVienSearch;
    public JComboBox<String> cboVaiTroSearch;

    // ===== BUTTONS =====
    public JButton btnThem, btnXoa, btnSua, btnTim, btnMoi, btnQuayLai;

    // ===== TABLE =====
    public DefaultTableModel model;
    public JTable table;

    public RoleView() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("Quản lý vai trò sinh viên");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.add(buildFormArea(), BorderLayout.NORTH);
        center.add(buildTableArea(), BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    private JPanel buildFormArea() {
        JPanel wrapper = new JPanel(new BorderLayout(8, 8));

        // ===== FORM CRUD =====
        JPanel formCrud = new JPanel(new GridBagLayout());
        formCrud.setBorder(BorderFactory.createTitledBorder("Thông tin"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 10, 6, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 0;

        int row = 0;
        g.gridy = row;

        // Row 0: Mã SV | Tên SV
        g.gridx = 0; formCrud.add(new JLabel("Mã sinh viên"), g);
        g.gridx = 1; g.weightx = 1.0;
        txtMaSinhVien = new JTextField(12);
        formCrud.add(txtMaSinhVien, g);

        g.gridx = 2; g.weightx = 0;
        formCrud.add(new JLabel("Tên sinh viên"), g);
        g.gridx = 3; g.weightx = 1.0;
        txtTenSinhVien = new JTextField(22);
        txtTenSinhVien.setEditable(false);
        formCrud.add(txtTenSinhVien, g);

        // Row 1: Role ID | Vai trò
        row++;
        g.gridy = row;

        g.gridx = 0; g.weightx = 0;
        formCrud.add(new JLabel("Role ID"), g);
        g.gridx = 1; g.weightx = 1.0;
        txtRoleId = new JTextField(12);
        txtRoleId.setEditable(false);
        formCrud.add(txtRoleId, g);

        g.gridx = 2; g.weightx = 0;
        formCrud.add(new JLabel("Vai trò"), g);
        g.gridx = 3; g.weightx = 1.0;
        cboVaiTro = new JComboBox<>(new String[]{
                "Lớp trưởng",
                "Lớp phó",
                "Bí thư",
                "Cán sự"
        });
        formCrud.add(cboVaiTro, g);

        // ===== FORM SEARCH  =====
        JPanel formSearch = new JPanel(new GridBagLayout());
        formSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(6, 10, 6, 10);
        s.fill = GridBagConstraints.HORIZONTAL;
        s.weightx = 0;

        int srow = 0;
        s.gridy = srow;

        // Search Row 0: Role ID | Mã sinh viên
        s.gridx = 0; formSearch.add(new JLabel("Role ID"), s);
        s.gridx = 1; s.weightx = 1.0;
        txtRoleIdSearch = new JTextField(12);
        formSearch.add(txtRoleIdSearch, s);

        s.gridx = 2; s.weightx = 0;
        formSearch.add(new JLabel("Mã sinh viên"), s);
        s.gridx = 3; s.weightx = 1.0;
        txtMaSinhVienSearch = new JTextField(12);
        formSearch.add(txtMaSinhVienSearch, s);

        // Search Row 1: Vai trò
        srow++;
        s.gridy = srow;

        s.gridx = 0; s.weightx = 0;
        formSearch.add(new JLabel("Vai trò"), s);
        s.gridx = 1; s.weightx = 1.0;
        cboVaiTroSearch = new JComboBox<>(new String[]{
                "", "Lớp trưởng", "Lớp phó", "Bí thư", "Cán sự"
        });
        formSearch.add(cboVaiTroSearch, s);

        // ===== Buttons bar =====
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTim = new JButton("Tìm");
        btnMoi = new JButton("Mới");
        btnQuayLai = new JButton("Quay lại");

        btnBar.add(btnThem);
        btnBar.add(btnSua);
        btnBar.add(btnXoa);
        btnBar.add(btnTim);
        btnBar.add(btnMoi);
        btnBar.add(btnQuayLai);

        wrapper.add(formCrud, BorderLayout.NORTH);
        wrapper.add(formSearch, BorderLayout.CENTER);
        wrapper.add(btnBar, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane buildTableArea() {
        model = new DefaultTableModel(
                new String[]{
                        "Role ID", "Mã sinh viên", "Tên sinh viên", "Vai trò"
                }, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách"));
        return sp;
    }
}
