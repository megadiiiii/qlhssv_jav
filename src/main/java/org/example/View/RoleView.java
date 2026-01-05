package org.example.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RoleView extends JPanel {

    // ===== INPUT (CRUD) =====
    public JComboBox<String> cboMaLop;
    public JTextField txtTenLop;

    public JComboBox<String> cboMaSinhVien;
    public JTextField txtTenSinhVien;

    public JComboBox<String> cboVaiTro;

    // ===== BUTTONS =====
    public JButton btnThem, btnXoa, btnSua, btnQuayLai, btnExport;

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

        // Row 0: Mã lớp | Tên lớp
        g.gridx = 0;
        formCrud.add(new JLabel("Mã lớp"), g);
        g.gridx = 1;
        g.weightx = 1.0;
        cboMaLop = new JComboBox<>();
        cboMaLop.setPreferredSize(new Dimension(200, 28));
        formCrud.add(cboMaLop, g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Tên lớp"), g);
        g.gridx = 3;
        g.weightx = 1.0;
        txtTenLop = new JTextField(22);
        txtTenLop.setEditable(false);
        formCrud.add(txtTenLop, g);

        // Row 1: Mã SV | Tên SV
        row++;
        g.gridy = row;

        g.gridx = 0;
        g.weightx = 0;
        formCrud.add(new JLabel("Mã sinh viên"), g);
        g.gridx = 1;
        g.weightx = 1.0;
        cboMaSinhVien = new JComboBox<>();
        cboMaSinhVien.setPreferredSize(new Dimension(200, 28));
        formCrud.add(cboMaSinhVien, g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Tên sinh viên"), g);
        g.gridx = 3;
        g.weightx = 1.0;
        txtTenSinhVien = new JTextField(22);
        txtTenSinhVien.setEditable(false);
        formCrud.add(txtTenSinhVien, g);

        // Row 2: Role ID | Vai trò
        row++;
        g.gridy = row;
        g.gridx = 0;
        g.weightx = 0;
        formCrud.add(new JLabel("Vai trò"), g);
        g.gridx = 1;
        g.weightx = 1.0;
        cboVaiTro = new JComboBox<>(new String[]{"Lớp trưởng", "Lớp phó", "Bí thư"});
        formCrud.add(cboVaiTro, g);


        // ===== Buttons bar =====
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnExport = new JButton("Xuất Excel");
        btnQuayLai = new JButton("Quay lại");

        btnBar.add(btnThem);
        btnBar.add(btnSua);
        btnBar.add(btnXoa);
        btnBar.add(btnExport);
        btnBar.add(btnQuayLai);

        wrapper.add(formCrud, BorderLayout.NORTH);
        wrapper.add(btnBar, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane buildTableArea() {
        model = new DefaultTableModel(new String[]{"Role ID", "Mã lớp", "Tên lớp", "Mã sinh viên", "Tên sinh viên", "Vai trò"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách"));
        return sp;
    }
}
