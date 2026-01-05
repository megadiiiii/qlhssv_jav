package org.example.View;

import org.example.Model.Faculties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MajorView extends JPanel {

    //INPUT (CRUD)
    public JTextField txtMajorId, txtMajorName;
    public JComboBox<Faculties> cboFacu;
    public JComboBox<Faculties> cboFacuSearch;

    //
    //INPUT (SEARCH)
    public JTextField txtMajorIdSearch, txtMajorNameSearch;

    //
    //BUTTONS (5 nút 1 khu)
    public JButton btnMajorAdd, btnMajorUpdate, btnMajorDelete, btnSearch, btnBack;

    //
    //TABLE
    public DefaultTableModel model;
    public JTable table;

    public MajorView() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel lblTitle = new JLabel("Quản lý thông tin chuyên ngành");
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

        // Row 0: Name | Start | End
        int row = 0;

        g.gridy = row;

        g.gridx = 0;
        formCrud.add(new JLabel("Khoa/Viện"), g);
        g.gridx = 1;
        g.weightx = 0.5;
        cboFacu = new JComboBox<>();
        formCrud.add(cboFacu, g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Mã chuyên ngành"), g);
        g.gridx = 3;
        g.weightx = 0.1;
        txtMajorId = new JTextField(8);
        formCrud.add(txtMajorId, g);

        g.gridx = 4;
        g.weightx = 0;
        formCrud.add(new JLabel("Tên chuyên ngành"), g);
        g.gridx = 5;
        g.weightx = 0.5;
        txtMajorName = new JTextField(8);
        formCrud.add(txtMajorName, g);

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
        formSearch.add(new JLabel("Khoa/Viện"), s);
        s.gridx = 1;
        s.weightx = 0.5;
        cboFacuSearch = new JComboBox<>();
        formSearch.add(cboFacuSearch, s);

        s.gridx = 2;
        s.weightx = 0;
        formSearch.add(new JLabel("Mã chuyên ngành"), s);
        s.gridx = 3;
        s.weightx = 0.1;
        txtMajorIdSearch = new JTextField(8);
        formSearch.add(txtMajorIdSearch, s);

        s.gridx = 4;
        s.weightx = 0;
        formSearch.add(new JLabel("Tên chuyên ngành"), s);
        s.gridx = 5;
        s.weightx = 0.5;
        txtMajorNameSearch = new JTextField(8);
        formSearch.add(txtMajorNameSearch, s);

        //
        //5 buttons in one bar
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        btnMajorAdd = new JButton("Thêm");
        btnMajorUpdate = new JButton("Sửa");
        btnMajorDelete = new JButton("Xóa");
        btnSearch = new JButton("Tìm");
        btnBack = new JButton("Quay lại");

        btnBar.add(btnMajorAdd);
        btnBar.add(btnMajorUpdate);
        btnBar.add(btnMajorDelete);
        btnBar.add(btnSearch);
        btnBar.add(btnBack);

        wrapper.add(formCrud, BorderLayout.NORTH);
        wrapper.add(formSearch, BorderLayout.CENTER);
        wrapper.add(btnBar, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane buildTableArea() {
        model = new DefaultTableModel(
                new String[]{"Khoa", "Mã chuyên ngành", "Tên chuyên ngành"}, 0
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
            JFrame f = new JFrame("MajorView Test");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(1100, 650);
            f.setLocationRelativeTo(null);
            f.setContentPane(new MajorView());
            f.setVisible(true);
        });
    }
}
