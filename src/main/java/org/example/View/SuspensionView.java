package org.example.View;


import org.example.Model.Suspension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class  SuspensionView extends JPanel {


    public JTextField txtSuspensionId,txtStudentId;
    public JSpinner spStartDate, spEndDate;
    public JTextArea txtReason;
    public JComboBox<String> cboStatus;


    public JTextField txtStudentIdSearch;
    public JComboBox<String> cboStatusSearch;

    public JButton btnAdd, btnApprove, btnReject, btnSearch, btnBack;


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

        // ===== FORM CRUD =====
        JPanel formCrud = new JPanel(new GridBagLayout());
        formCrud.setBorder(BorderFactory.createTitledBorder("Thông tin bảo lưu"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 20, 6, 20);
        g.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        g.gridy = row;

        g.gridx = 0;
        formCrud.add(new JLabel("Mã bảo lưu"), g);
        g.gridx = 1;
        txtSuspensionId = new JTextField(12);
        txtSuspensionId.setEditable(false); // ID không cho sửa
        formCrud.add(txtSuspensionId, g);

        g.gridx = 2;
        formCrud.add(new JLabel("Mã sinh viên"), g);
        g.gridx = 3;
        txtStudentId = new JTextField(12);
        formCrud.add(txtStudentId, g);

        // ===== ROW 2 =====
        row++;
        g.gridy = row;

        g.gridx = 0;
        formCrud.add(new JLabel("Ngày bắt đầu"), g);
        g.gridx = 1;
        spStartDate = new JSpinner(new SpinnerDateModel());
        spStartDate.setEditor(new JSpinner.DateEditor(spStartDate, "yyyy-MM-dd"));
        formCrud.add(spStartDate, g);

        g.gridx = 2;
        formCrud.add(new JLabel("Ngày kết thúc"), g);
        g.gridx = 3;
        spEndDate = new JSpinner(new SpinnerDateModel());
        spEndDate.setEditor(new JSpinner.DateEditor(spEndDate, "yyyy-MM-dd"));
        formCrud.add(spEndDate, g);

        // ===== ROW 3 =====
        row++;
        g.gridy = row;

        g.gridx = 0;
        formCrud.add(new JLabel("Trạng thái"), g);
        g.gridx = 1;
        cboStatus = new JComboBox<>(new String[]{
                "PENDING", "APPROVED", "REJECTED"
        });
        formCrud.add(cboStatus, g);

        // ===== ROW 4 =====
        row++;
        g.gridy = row;

        g.gridx = 0;
        g.anchor = GridBagConstraints.NORTH;
        formCrud.add(new JLabel("Lý do"), g);

        g.gridx = 1;
        g.gridwidth = 3;
        txtReason = new JTextArea(3, 30);
        txtReason.setLineWrap(true);
        txtReason.setWrapStyleWord(true);
        formCrud.add(new JScrollPane(txtReason), g);

        wrapper.add(formCrud, BorderLayout.CENTER);

        // ===== FORM SEARCH =====
        JPanel formSearch = new JPanel(new GridBagLayout());
        formSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(6, 20, 6, 20);
        s.fill = GridBagConstraints.HORIZONTAL;

        s.gridy = 0;

        s.gridx = 0;
        formSearch.add(new JLabel("Mã sinh viên"), s);
        s.gridx = 1;
        txtStudentIdSearch = new JTextField(12);
        formSearch.add(txtStudentIdSearch, s);

        s.gridx = 2;
        formSearch.add(new JLabel("Trạng thái"), s);
        s.gridx = 3;
        cboStatusSearch = new JComboBox<>();
        cboStatusSearch.insertItemAt(null, 0);
        cboStatusSearch.setSelectedIndex(0);
        formSearch.add(cboStatusSearch, s);

        // ===== BUTTON BAR =====
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        btnAdd = new JButton("Thêm");
        btnApprove = new JButton("Duyệt");
        btnReject = new JButton("Từ chối");
        btnSearch = new JButton("Tìm kiếm");
        btnBack = new JButton("Quay lại");

        btnBar.add(btnAdd);
        btnBar.add(btnApprove);
        btnBar.add(btnReject);
        btnBar.add(btnSearch);
        btnBar.add(btnBack);

        wrapper.add(formCrud, BorderLayout.NORTH);
        wrapper.add(formSearch, BorderLayout.CENTER);
        wrapper.add(btnBar, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane buildTableArea() {
        model = new DefaultTableModel(
                new String[]{
                        "Mã SV", "Ngày bắt đầu", "Ngày kết thúc",
                        "Lý do", "Trạng thái", "Ngày yêu cầu"
                }, 0
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

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách bảo lưu"));
        return sp;
    }



}
