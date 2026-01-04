package org.example.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ScholarshipView extends JPanel {

    public ScholarshipView() {
        initUI();
    }

    /// ===== FORM =====
    public JComboBox<String> cboStudentId;
    public JComboBox<String> cboScoreLevel, cboDrlLevel, cboScholarshipLevel, cboSemester;
    public JTextField txtStudentIdSearch, txtStudentName;

    public JButton btnAdd, btnDelete, btnEdit, btnBack, btnSearch, btnExport;

    /// ===== TABLE =====
    public DefaultTableModel model;
    public JTable table;

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Quản lý học bổng");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(lblTitle, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.add(buildFormArea(), BorderLayout.NORTH);
        center.add(tableInit(), BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    private JPanel buildFormArea() {
        JPanel wrapper = new JPanel(new BorderLayout(8, 8));

        //
        //Form CRUD
        JPanel formCrud = new JPanel(new GridBagLayout());
        formCrud.setBorder(BorderFactory.createTitledBorder("Thông tin"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 20, 6, 20);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 0;

        // Row 0: Name | Start | End
        int row = 0;

        g.gridy = row;

        g.gridx = 0;
        formCrud.add(new JLabel("Mã SV"), g);
        g.gridx = 1;
        g.weightx = 0.5;
        cboStudentId = new JComboBox<>();
        formCrud.add(cboStudentId, g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Tên SV"), g);
        g.gridx = 3;
        g.weightx = 0.1;
        txtStudentName = new JTextField(8);
        txtStudentName.setEditable(false);
        formCrud.add(txtStudentName, g);
        row++;

        // Row 1
        g.gridy = row;
        g.gridx = 0;
        g.weightx = 0;
        formCrud.add(new JLabel("Kết quả học tập"), g);
        g.gridx = 1;
        g.weightx = 0.5;
        formCrud.add(cboScoreLevel = new JComboBox<>(new String[]{"Khá", "Giỏi", "Xuất sắc"}), g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Kết quả rèn luyện"), g);
        g.gridx = 3;
        g.weightx = 0.5;
        formCrud.add(cboDrlLevel = new JComboBox<>(new String[]{"Khá", "Tốt", "Xuất sắc"}), g);
        row++;

        // Row 1
        g.gridy = row;
        g.gridx = 0;
        g.weightx = 0;
        formCrud.add(new JLabel("Kết quả học bổng"), g);
        g.gridx = 1;
        g.weightx = 0.5;
        formCrud.add(cboScholarshipLevel = new JComboBox<>(new String[]{"Khá", "Giỏi", "Xuất sắc"}), g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Kì học"), g);
        g.gridx = 3;
        g.weightx = 0.5;
        formCrud.add(cboSemester = new JComboBox<>(new String[]{"Kì 1", "Kì 2", "Kì 3", "Kì 4", "Kì 5", "Kì 6", "Kì 7", "Kì 8"}), g);

        JPanel formSearch = new JPanel(new GridBagLayout());
        formSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(6, 10, 6, 10);
        s.fill = GridBagConstraints.HORIZONTAL;
        s.weightx = 0;

        int srow = 0;
        s.gridy = srow;

        s.gridx = 0; formSearch.add(new JLabel("Mã SV"), s);
        s.gridx = 1; s.weightx = 1.0;
        txtStudentIdSearch = new JTextField(22);
        formSearch.add(txtStudentIdSearch, s);

        //5 buttons in one bar
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnSearch = new JButton("Tìm kiếm");
        btnExport = new JButton("Xuất Excel");
        btnBack = new JButton("Quay lại");

        btnBar.add(btnAdd);
        btnBar.add(btnEdit);
        btnBar.add(btnDelete);
        btnBar.add(btnSearch);
        btnBar.add(btnExport);
        btnBar.add(btnBack);

        wrapper.add(formCrud, BorderLayout.NORTH);
        wrapper.add(formSearch, BorderLayout.CENTER);
        wrapper.add(btnBar, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane tableInit() {
        model = new DefaultTableModel(new String[]{"ID", "Mã SV", "Tên SV", "Học lực", "RL", "Mức HB", "Học kỳ"}, 0) {
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

        // Ẩn cột ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách"));
        return sp;
    }
}
