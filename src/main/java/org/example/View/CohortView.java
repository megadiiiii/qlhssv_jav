package org.example.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CohortView extends JPanel {

    //
    //INPUT (CRUD)
    public JTextField txtCohortName, txtCohortStartYear, txtCohortEndYear;

    //
    //INPUT (SEARCH)
    public JTextField txtCohortNameSearch, txtCohortStartYearSearch, txtCohortEndYearSearch;

    //
    //BUTTONS (5 nút 1 khu)
    public JButton btnCohortAdd, btnCohortUpdate, btnCohortDelete, btnSearch, btnBack;

    //
    //TABLE
    public DefaultTableModel model;
    public JTable table;

    public CohortView() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel lblTitle = new JLabel("Quản lý thông tin khóa đào tạo");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        // Center = form + table
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
        g.insets = new Insets(6, 10, 6, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 0;

        // Row 0: Name | Start | End
        int row = 0;

        g.gridy = row;

        g.gridx = 0; formCrud.add(new JLabel("Khóa đào tạo"), g);
        g.gridx = 1; g.weightx = 1.0;
        txtCohortName = new JTextField(22);
        formCrud.add(txtCohortName, g);

        g.gridx = 2; g.weightx = 0;
        formCrud.add(new JLabel("Từ năm"), g);
        g.gridx = 3; g.weightx = 0.3;
        txtCohortStartYear = new JTextField(8);
        formCrud.add(txtCohortStartYear, g);

        g.gridx = 4; g.weightx = 0;
        formCrud.add(new JLabel("Đến năm"), g);
        g.gridx = 5; g.weightx = 0.3;
        txtCohortEndYear = new JTextField(8);
        formCrud.add(txtCohortEndYear, g);

        //
        //Form SEARCH
        JPanel formSearch = new JPanel(new GridBagLayout());
        formSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(6, 10, 6, 10);
        s.fill = GridBagConstraints.HORIZONTAL;
        s.weightx = 0;

        int srow = 0;
        s.gridy = srow;

        s.gridx = 0; formSearch.add(new JLabel("Khóa đào tạo"), s);
        s.gridx = 1; s.weightx = 1.0;
        txtCohortNameSearch = new JTextField(22);
        formSearch.add(txtCohortNameSearch, s);

        s.gridx = 2; s.weightx = 0;
        formSearch.add(new JLabel("Từ năm"), s);
        s.gridx = 3; s.weightx = 0.3;
        txtCohortStartYearSearch = new JTextField(8);
        formSearch.add(txtCohortStartYearSearch, s);

        s.gridx = 4; s.weightx = 0;
        formSearch.add(new JLabel("Đến năm"), s);
        s.gridx = 5; s.weightx = 0.3;
        txtCohortEndYearSearch = new JTextField(8);
        formSearch.add(txtCohortEndYearSearch, s);

        //
        //5 buttons in one bar
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        btnCohortAdd = new JButton("Thêm");
        btnCohortUpdate = new JButton("Sửa");
        btnCohortDelete = new JButton("Xóa");
        btnSearch = new JButton("Tìm");
        btnBack = new JButton("Quay lại");

        btnBar.add(btnCohortAdd);
        btnBar.add(btnCohortUpdate);
        btnBar.add(btnCohortDelete);
        btnBar.add(btnSearch);
        btnBar.add(btnBack);

        wrapper.add(formCrud, BorderLayout.NORTH);
        wrapper.add(formSearch, BorderLayout.CENTER);
        wrapper.add(btnBar, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane buildTableArea() {
        model = new DefaultTableModel(
                new String[]{"ID", "Tên khóa đào tạo", "Năm nhập học", "Năm kết thúc"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
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

    // Quick test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("CohortView Test");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(1100, 650);
            f.setLocationRelativeTo(null);
            f.setContentPane(new CohortView());
            f.setVisible(true);
        });
    }
}
