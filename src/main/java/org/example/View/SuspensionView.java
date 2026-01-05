package org.example.View;


import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SuspensionView extends JPanel {


    public JTextField txtStudentName;
    public JDateChooser dcStartDate, dcEndDate;
    public JTextArea txtReason;
    public JComboBox<String> cboStatus;
    public JComboBox<String> cboStudentId;


    public JTextField txtStudentIdSearch;

    public JButton btnAdd, btnApprove, btnReject, btnSearch, btnBack, btnExport, btnDelete;


    public DefaultTableModel model;
    public JTable table;

    public SuspensionView() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("Quản lý bảo lưu học viên");
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

        //
        //Form CRUD
        JPanel formCrud = new JPanel(new GridBagLayout());
        formCrud.setBorder(BorderFactory.createTitledBorder("Thông tin"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 20, 6, 20);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 0;

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
        formCrud.add(txtStudentName, g);
        row++;

        // Row 1
        g.gridy = row;
        g.gridx = 0;
        g.weightx = 0;
        formCrud.add(new JLabel("Ngày bắt đầu"), g);
        g.gridx = 1;
        g.weightx = 0.5;
        formCrud.add(dcStartDate = new JDateChooser(), g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Ngày kết thúc"), g);
        g.gridx = 3;
        g.weightx = 0.5;
        formCrud.add(dcEndDate = new JDateChooser(), g);
        row++;

        // Row 1
        g.gridy = row;
        g.gridx = 0;
        g.weightx = 0;
        formCrud.add(new JLabel("Trạng thái"), g);
        g.gridx = 1;
        g.weightx = 0.5;
        formCrud.add(cboStatus = new JComboBox<>(new String[]{"Bị từ chối", "Chờ duyệt", "Đã được duyệt"}), g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Lý do"), g);
        g.gridx = 3;
        g.weightx = 0.5;
        formCrud.add(txtReason = new JTextArea(), g);

//Form SEARCH
        JPanel formSearch = new JPanel(new GridBagLayout());
        formSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(6, 10, 6, 10);
        s.fill = GridBagConstraints.HORIZONTAL;
        s.weightx = 0;

        int srow = 0;
        s.gridy = srow;

        s.gridx = 0;
        formSearch.add(new JLabel("Mã SV"), s);
        s.gridx = 1;
        s.weightx = 3;
        txtStudentIdSearch = new JTextField(44);
        formSearch.add(txtStudentIdSearch, s);

        //5 buttons in one bar
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        btnAdd = new JButton("Thêm");
        btnApprove = new JButton("Duyệt");
        btnReject = new JButton("Từ chối");
        btnSearch = new JButton("Tìm kiếm");
        btnExport = new JButton("Xuất Excel");
        btnDelete = new JButton("Xóa");
        btnBack = new JButton("Quay lại");

        btnBar.add(btnAdd);
        btnBar.add(btnApprove);
        btnBar.add(btnReject);
        btnBar.add(btnSearch);
        btnBar.add(btnDelete);
        btnBar.add(btnExport);
        btnBar.add(btnBack);

        wrapper.add(formCrud, BorderLayout.NORTH);
        wrapper.add(formSearch, BorderLayout.CENTER);
        wrapper.add(btnBar, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane buildTableArea() {
        model = new DefaultTableModel(
                new String[]{"SUS_ID", "Mã SV", "Tên SV", "Ngày bắt đầu", "Ngày kết thúc", "Lý do", "Trạng thái"}, 0
        ) {
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
