package org.example.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FacultiesView extends JPanel {
    //INPUT (CRUD)
    public JTextField txtFacuId, txtFacuName;

    //INPUT (SEARCH)
    public JTextField txtFacuIdSearch, txtFacuNameSearch;

    //BUTTONS (5 nút 1 khu)
    public JButton btnFacuAdd, btnFacuUpdate, btnFacuDelete, btnSearch, btnBack;

    //
    //TABLE
    public DefaultTableModel model;
    public JTable table;

    public FacultiesView() {
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
        g.insets = new Insets(6, 20, 6, 20);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 0;

        // Row 0
        int row = 0;

        g.gridy = row;

        g.gridx = 0;
        formCrud.add(new JLabel("Mã khoa"), g);
        g.gridx = 1;
        g.weightx = 0.7;
        txtFacuId = new JTextField(8);
        formCrud.add(txtFacuId, g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Tên khoa"), g);
        g.gridx = 3;
        g.weightx = 1;
        txtFacuName = new JTextField(22);
        formCrud.add(txtFacuName, g);

        //
        //Form SEARCH
        JPanel formSearch = new JPanel(new GridBagLayout());
        formSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(6, 20, 6, 20);
        s.fill = GridBagConstraints.HORIZONTAL;
        s.weightx = 0;

        int srow = 0;
        s.gridy = srow;

        s.gridx = 0;
        formSearch.add(new JLabel("Mã khoa"), s);
        s.gridx = 1;
        s.weightx = 0.7;
        txtFacuIdSearch = new JTextField(8);
        formSearch.add(txtFacuIdSearch, s);

        s.gridx = 2;
        s.weightx = 0;
        formSearch.add(new JLabel("Tên khoa"), s);
        s.gridx = 3;
        s.weightx = 1;
        txtFacuNameSearch = new JTextField(22);
        formSearch.add(txtFacuNameSearch, s);

        //
        //5 buttons in one bar
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        btnFacuAdd = new JButton("Thêm");
        btnFacuUpdate = new JButton("Cập nhật");
        btnFacuDelete = new JButton("Xóa");
        btnSearch = new JButton("Tìm");
        btnBack = new JButton("Quay lại");

        btnBar.add(btnFacuAdd);
        btnBar.add(btnFacuUpdate);
        btnBar.add(btnFacuDelete);
        btnBar.add(btnSearch);
        btnBar.add(btnBack);

        wrapper.add(formCrud, BorderLayout.NORTH);
        wrapper.add(formSearch, BorderLayout.CENTER);
        wrapper.add(btnBar, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane buildTableArea() {
        model = new DefaultTableModel(
                new String[]{"Mã khoa", "Tên khoa"}, 0
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

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách"));
        return sp;
    }

    // Quick test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("FacultiesView Test");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(1100, 650);
            f.setLocationRelativeTo(null);
            f.setContentPane(new FacultiesView());
            f.setVisible(true);
        });
    }
}
