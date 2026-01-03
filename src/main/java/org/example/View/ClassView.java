package org.example.View;

import org.example.Model.Cohort;
import org.example.Model.Faculties;
import org.example.Model.Major;
import org.example.Model.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClassView extends JPanel {

    // ===== INPUT (CRUD) =====
    public JTextField txtClassName, txtClassId, txtStudentCurrent, txtStudentMax;
    public JComboBox<Faculties> cboFacu;
    public JComboBox<Faculties> cboFacuSearch;
    public JComboBox<Cohort> cboCohort;
    public JComboBox<Cohort> cboCohortSearch;
    public JComboBox<Teacher> cboTeacher;
    public JComboBox<Teacher> cboTeacherSearch;
    public JComboBox<Major> cboMajor;
    public JComboBox<Major> cboMajorSearch;

    // ===== INPUT (SEARCH) =====
    public JTextField txtClassIdSearch, txtClassNameSearch;

    // ===== BUTTONS (5 nút 1 khu) =====
    public JButton btnAdd, btnDelete, btnUpdate, btnSearch, btnBack, btnExport;

    // ===== TABLE =====
    public DefaultTableModel model;
    public JTable table;

    public ClassView() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("Quản lý thông tin lớp");
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

        // Row 0
        g.gridy = 0;
        g.gridx = 0;
        g.weightx = 0;
        formCrud.add(new JLabel("Khoa/Viện"), g);
        g.gridx = 1;
        g.weightx = 2;
        formCrud.add(cboFacu = new JComboBox<>(), g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Chuyên ngành"), g);
        g.gridx = 3;
        g.weightx = 3;
        formCrud.add(cboMajor = new JComboBox<>(), g);

        g.gridx = 4;
        g.weightx = 0;
        formCrud.add(new JLabel("Khóa"), g);
        g.gridx = 5;
        g.weightx = 2;
        formCrud.add(cboCohort = new JComboBox<>(), g);

        g.gridx = 6;
        g.weightx = 0;
        formCrud.add(new JLabel("GVCN/CVHT"), g);
        g.gridx = 7;
        g.weightx = 3;
        formCrud.add(cboTeacher = new JComboBox<>(), g);

        // Row 1
        g.gridy = 1;
        g.gridx = 0;
        g.weightx = 0;
        formCrud.add(new JLabel("Mã lớp"), g);
        g.gridx = 1;
        g.weightx = 0.5;
        formCrud.add(txtClassId = new JTextField(10), g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Tên lớp"), g);
        g.gridx = 3;
        g.weightx = 0.5;
        formCrud.add(txtClassName = new JTextField(10), g);

        g.gridx = 4;
        g.weightx = 0;
        formCrud.add(new JLabel("Số SV hiện tại"), g);
        g.gridx = 5;
        g.weightx = 0.5;
        txtStudentCurrent = new JTextField(10);
        txtStudentCurrent.setEditable(false);
        formCrud.add(txtStudentCurrent, g);

        g.gridx = 6;
        g.weightx = 0;
        formCrud.add(new JLabel("Số SV tối đa"), g);
        g.gridx = 7;
        g.weightx = 0.5;
        formCrud.add(txtStudentMax = new JTextField(10), g);

        // ===== FORM SEARCH =====
        JPanel formSearch = new JPanel(new GridBagLayout());
        formSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(6, 10, 6, 10);
        s.fill = GridBagConstraints.HORIZONTAL;

        // Row 0
        s.gridy = 0;
        s.gridx = 0;
        s.weightx = 0;
        formSearch.add(new JLabel("Khoa/Viện"), s);
        s.gridx = 1;
        s.weightx = 1.5;
        formSearch.add(cboFacuSearch = new JComboBox<>(), s);

        s.gridx = 2;
        s.weightx = 0;
        formSearch.add(new JLabel("Chuyên ngành"), s);
        s.gridx = 3;
        s.weightx = 2.5;
        formSearch.add(cboMajorSearch = new JComboBox<>(), s);

        s.gridx = 4;
        s.weightx = 0;
        formSearch.add(new JLabel("Khóa"), s);
        s.gridx = 5;
        s.weightx = 1.5;
        formSearch.add(cboCohortSearch = new JComboBox<>(), s);



        // Row 1
        s.gridy = 1;
        s.gridx = 0;
        s.weightx = 0;
        formSearch.add(new JLabel("Mã lớp"), s);
        s.gridx = 1;
        s.weightx = 0.5;
        formSearch.add(txtClassIdSearch = new JTextField(10), s);

        s.gridx = 2;
        s.weightx = 0;
        formSearch.add(new JLabel("Tên lớp"), s);
        s.gridx = 3;
        s.weightx = 0.5;
        formSearch.add(txtClassNameSearch = new JTextField(10), s);

        s.gridx = 4;
        s.weightx = 0;
        formSearch.add(new JLabel("GVCN/CVHT"), s);
        s.gridx = 5;
        s.weightx = 2.5;
        formSearch.add(cboTeacherSearch = new JComboBox<>(), s);

        // ===== Button bar =====
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnSearch = new JButton("Tìm");
        btnExport = new JButton("Xuất Excel");
        btnBack = new JButton("Quay lại");
        btnBar.add(btnAdd);
        btnBar.add(btnUpdate);
        btnBar.add(btnDelete);
        btnBar.add(btnSearch);
        btnBar.add(btnExport);
        btnBar.add(btnBack);

        // ===== Add panels to wrapper =====
        wrapper.add(formCrud, BorderLayout.NORTH);
        wrapper.add(formSearch, BorderLayout.CENTER);
        wrapper.add(btnBar, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane buildTableArea() {
        model = new DefaultTableModel(new String[]{"Mã lớp", "Tên lớp", "Khóa", "Chuyên ngành", "Khoa", "GVCN/CVHT", "Số SV hiện tại", "Số SV tối đa"}, 0) {
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

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách"));
        return sp;
    }
}
