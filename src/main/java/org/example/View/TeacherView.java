package org.example.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TeacherView extends JPanel {

    // ===== INPUT (CRUD) =====
    public JTextField txtTeacherId, txtTeacherName, txtFacuName;
    public JComboBox<String> cboFacuId;

    // ===== INPUT (SEARCH) =====
    public JTextField txtTeacherIdSearch, txtTeacherNameSearch;
    public JComboBox<String> cboFacuIdSearch;

    // ===== BUTTONS (5 nút 1 khu) =====
    public JButton btnThem, btnSua, btnXoa, btnSearch, btnMoi;

    // ===== TABLE =====
    public DefaultTableModel model;
    public JTable table;

    public TeacherView() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("Quản lý thông tin giảng viên");
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

        g.gridx = 0; formCrud.add(new JLabel("Mã giảng viên"), g);
        g.gridx = 1; g.weightx = 0.6;
        txtTeacherId = new JTextField(12);
        formCrud.add(txtTeacherId, g);

        g.gridx = 2; g.weightx = 0;
        formCrud.add(new JLabel("Tên giảng viên"), g);
        g.gridx = 3; g.weightx = 1.0;
        txtTeacherName = new JTextField(22);
        formCrud.add(txtTeacherName, g);

        row++;
        g.gridy = row;

        g.gridx = 0; g.weightx = 0;
        formCrud.add(new JLabel("Mã khoa"), g);
        g.gridx = 1; g.weightx = 0.6;
        cboFacuId = new JComboBox<>();
        cboFacuId.setEditable(true);
        formCrud.add(cboFacuId, g);

        g.gridx = 2; g.weightx = 0;
        formCrud.add(new JLabel("Tên khoa"), g);
        g.gridx = 3; g.weightx = 1.0;
        txtFacuName = new JTextField(22);
        txtFacuName.setEditable(false);
        formCrud.add(txtFacuName, g);

        // ===== FORM SEARCH =====
        JPanel formSearch = new JPanel(new GridBagLayout());
        formSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(6, 10, 6, 10);
        s.fill = GridBagConstraints.HORIZONTAL;
        s.weightx = 0;

        int srow = 0;
        s.gridy = srow;

        s.gridx = 0; formSearch.add(new JLabel("Mã giảng viên"), s);
        s.gridx = 1; s.weightx = 0.6;
        txtTeacherIdSearch = new JTextField(12);
        formSearch.add(txtTeacherIdSearch, s);

        s.gridx = 2; s.weightx = 0;
        formSearch.add(new JLabel("Tên giảng viên"), s);
        s.gridx = 3; s.weightx = 1.0;
        txtTeacherNameSearch = new JTextField(22);
        formSearch.add(txtTeacherNameSearch, s);

        srow++;
        s.gridy = srow;

        s.gridx = 0; s.weightx = 0;
        formSearch.add(new JLabel("Mã khoa"), s);
        s.gridx = 1; s.weightx = 0.6;
        cboFacuIdSearch = new JComboBox<>();
        cboFacuIdSearch.setEditable(true);
        formSearch.add(cboFacuIdSearch, s);

        // ===== 5 buttons in one bar =====
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnSearch = new JButton("Tìm");
        btnMoi = new JButton("Mới");

        btnBar.add(btnThem);
        btnBar.add(btnSua);
        btnBar.add(btnXoa);
        btnBar.add(btnSearch);
        btnBar.add(btnMoi);

        wrapper.add(formCrud, BorderLayout.NORTH);
        wrapper.add(formSearch, BorderLayout.CENTER);
        wrapper.add(btnBar, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane buildTableArea() {
        model = new DefaultTableModel(
                new String[]{"Mã giảng viên", "Tên giảng viên", "Mã khoa", "Tên khoa"}, 0
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
