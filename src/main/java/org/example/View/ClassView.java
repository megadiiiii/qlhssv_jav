package org.example.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClassView extends JPanel {

    public JTextField txtClassId, txtClassName, txtSearchId, txtSearchName, txtCohort;
    public JComboBox cboFacu, cboMajor;
    public JButton btnAdd, btnUpdate, btnDelete, btnSearch;
    public JTable tblClass;
    public DefaultTableModel tableModel;

    public ClassView() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(15, 20, 15, 20));


        JLabel lblTitle = new JLabel("QUẢN LÝ THÔNG TIN LỚP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBorder(new EmptyBorder(10, 0, 20, 0));
        add(lblTitle, BorderLayout.NORTH);


        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin lớp"));
        pnlForm.setPreferredSize(new Dimension(900, 170));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 12, 8, 12);
        g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.WEST;

        txtClassId = new JTextField(15);
        txtClassName = new JTextField(15);
        txtSearchId = new JTextField(15);
        txtSearchName = new JTextField(15);
        txtCohort = new JTextField(15);
        cboFacu = new JComboBox();
        cboMajor = new JComboBox();

        int y = 0;

        g.gridx = 0; g.gridy = y; pnlForm.add(new JLabel("Mã lớp"), g);
        g.gridx = 1; pnlForm.add(txtClassId, g);
        g.gridx = 2; pnlForm.add(new JLabel("Tên lớp"), g);
        g.gridx = 3; pnlForm.add(txtClassName, g);
        g.gridx = 4; pnlForm.add(new JLabel("Khóa"), g);
        g.gridx = 5; pnlForm.add(txtCohort, g);

        y++;
        g.gridx = 0; g.gridy = y; pnlForm.add(new JLabel("Khoa"), g);
        g.gridx = 1; pnlForm.add(cboFacu, g);
        g.gridx = 2; pnlForm.add(new JLabel("Ngành"), g);
        g.gridx = 3; pnlForm.add(cboMajor, g);

        y++;
        g.gridx = 0; g.gridy = y; pnlForm.add(new JLabel("Tìm mã lớp"), g);
        g.gridx = 1; pnlForm.add(txtSearchId, g);
        g.gridx = 2; pnlForm.add(new JLabel("Tìm tên lớp"), g);
        g.gridx = 3; pnlForm.add(txtSearchName, g);

        /* ================= BUTTON ================= */
        JPanel pnlButton = new JPanel();
        pnlButton.setBorder(new EmptyBorder(10, 0, 10, 0));

        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnSearch = new JButton("Tìm kiếm");

        Dimension btnSize = new Dimension(100, 32);
        btnAdd.setPreferredSize(btnSize);
        btnUpdate.setPreferredSize(btnSize);
        btnDelete.setPreferredSize(btnSize);
        btnSearch.setPreferredSize(btnSize);

        pnlButton.add(btnAdd);
        pnlButton.add(btnUpdate);
        pnlButton.add(btnDelete);
        pnlButton.add(btnSearch);

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(pnlForm, BorderLayout.CENTER);
        pnlTop.add(pnlButton, BorderLayout.SOUTH);

        add(pnlTop, BorderLayout.CENTER);


        tableModel = new DefaultTableModel(
                new Object[]{"Mã lớp", "Tên lớp", "Khoa", "Chuyên ngành", "Khóa"}, 0
        );

        tblClass = new JTable(tableModel);
        tblClass.setRowHeight(28);
        tblClass.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblClass.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblClass.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tblClass);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách lớp"));
        scrollPane.setPreferredSize(new Dimension(900, 260));

        add(scrollPane, BorderLayout.SOUTH);
    }
}
