package org.example.View;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DisciplineView extends JPanel {

    public DisciplineView() {
        initUI();
    }

    public JTextField txtSoQuyetDinh, txtStudentId;
    public JTextArea txtLyDo;
    public JComboBox<String> cboMaSinhVien;
    public JTextField txtTenSinhVien;
    public JComboBox<String> cboHinhThuc;
    public JComboBox<String> cboHinhThucSearch;

    public JDateChooser dateNgayKyLuat, dateNgayKetThuc;

    public JButton btnThem, btnXoa, btnSua, btnQuayLai, btnExport;

    public DefaultTableModel model;
    public JTable table;

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel lblTitle = new JLabel("Quản lý kỉ luật");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        // Center = form + table
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
        cboMaSinhVien = new JComboBox<>();
        formCrud.add(cboMaSinhVien, g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Tên SV"), g);
        g.gridx = 3;
        g.weightx = 0.1;
        txtTenSinhVien = new JTextField(8);
        formCrud.add(txtTenSinhVien, g);
        row++;

        // Row 1
        g.gridy = row;
        g.gridx = 0;
        g.weightx = 0;
        formCrud.add(new JLabel("Ngày kỉ luật"), g);
        g.gridx = 1;
        g.weightx = 0.5;
        formCrud.add(dateNgayKyLuat = new JDateChooser(), g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Ngày kết thúc"), g);
        g.gridx = 3;
        g.weightx = 0.5;
        formCrud.add(dateNgayKetThuc = new JDateChooser(), g);
        row++;

        // Row 1
        g.gridy = row;
        g.gridx = 0;
        g.weightx = 0;
        formCrud.add(new JLabel("Hình thức"), g);
        g.gridx = 1;
        g.weightx = 0.5;
        formCrud.add(cboHinhThuc = new JComboBox<>(new String[]{
                "Khiển trách",
                "Cảnh cáo",
                "Đình chỉ học tập",
                "Buộc thôi học"
        }), g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Số quyết định"), g);
        g.gridx = 3;
        g.weightx = 0.5;
        formCrud.add(txtSoQuyetDinh = new JTextField(10), g);
        row++;


        // Row 1
        g.gridy = row;
        g.gridx = 0;
        g.weightx = 0;
        formCrud.add(new JLabel("Lý do"), g);
        g.gridx = 1;
        g.weightx = 1;
        g.gridwidth = 3;
        formCrud.add(txtLyDo = new JTextArea(), g);

        g.gridwidth = 1;

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
        s.weightx = 0;
        formSearch.add(new JLabel("Mã SV"), s);
        s.gridx = 1;
        s.weightx = 0.5;
        txtStudentId = new JTextField();
        formSearch.add(txtStudentId, s);

        s.gridx = 2;
        s.weightx = 0;
        formSearch.add(new JLabel("Hình thức"), s);
        s.gridx = 3;
        s.weightx = 0.5;
        cboHinhThucSearch = new JComboBox<>();
        formSearch.add(cboHinhThucSearch = new JComboBox<>(new String[]{
                "--Chọn hình thức--",
                "Khiển trách",
                "Cảnh cáo",
                "Đình chỉ học tập",
                "Buộc thôi học"
        }), s);

        //5 buttons in one bar
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
        wrapper.add(formSearch, BorderLayout.CENTER);
        wrapper.add(btnBar, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane tableInit() {
        model = new DefaultTableModel(
                new String[]{
                        "Mã kỷ luật", "Mã sinh viên", "Tên sinh viên", "Hình thức",
                        "Ngày kỷ luật", "Ngày kết thúc", "Số quyết định", "Lý do"
                }, 0) {
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
