package org.example.View;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DisciplineView extends JPanel {

    public DisciplineView() {
        initUI();
    }

    public JTextField txtIdKyLuat, txtLyDo, txtSoQuyetDinh;
    public JComboBox<String> cboMaSinhVien;
    public JTextField txtTenSinhVien;
    public JComboBox<String> cboHinhThuc;

    public JDateChooser dateNgayKyLuat, dateNgayKetThuc;

    public JLabel lblIdKyLuat, lblHinhThuc, lblNgayKyLuat, lblLyDo, lblNgayKetThuc,
            lblSoQuyetDinh, lblMaSinhVien, lblTenSinhVien;

    public JButton btnThem, btnXoa, btnSua, btnQuayLai, btnTimKiem, btnExport;

    public DefaultTableModel model;
    public JScrollPane scrollPane;
    public JTable table;

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Quản lý thông tin kỷ luật");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(lblTitle, BorderLayout.NORTH);

        add(formInfoInit(), BorderLayout.NORTH);
        tableInit();
    }

    private JPanel formInfoInit() {
        JPanel container = new JPanel(new BorderLayout());

        JLabel lblInfoTitle = new JLabel("Thông tin");
        lblInfoTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInfoTitle.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 30));
        container.add(lblInfoTitle, BorderLayout.NORTH);

        JPanel form = new JPanel();
        GroupLayout layout = new GroupLayout(form);
        form.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        lblMaSinhVien = new JLabel("Mã sinh viên");
        cboMaSinhVien = new JComboBox<>();
        cboMaSinhVien.setEditable(true);

        lblTenSinhVien = new JLabel("Tên sinh viên");
        txtTenSinhVien = new JTextField();
        txtTenSinhVien.setEditable(false);

        // ===== KỶ LUẬT =====
        lblIdKyLuat = new JLabel("Mã kỷ luật");
        txtIdKyLuat = new JTextField();
        txtIdKyLuat.setEditable(false);

        lblHinhThuc = new JLabel("Hình thức");
        cboHinhThuc = new JComboBox<>(new String[]{
                "Khiển trách",
                "Cảnh cáo",
                "Đình chỉ học tập",
                "Buộc thôi học"
        });

        lblNgayKyLuat = new JLabel("Ngày kỷ luật");
        dateNgayKyLuat = new JDateChooser();
        dateNgayKyLuat.setDateFormatString("yyyy-MM-dd");

        lblLyDo = new JLabel("Lý do");
        txtLyDo = new JTextField();

        lblNgayKetThuc = new JLabel("Ngày kết thúc");
        dateNgayKetThuc = new JDateChooser();
        dateNgayKetThuc.setDateFormatString("yyyy-MM-dd");

        lblSoQuyetDinh = new JLabel("Số quyết định");
        txtSoQuyetDinh = new JTextField();

        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnSua = new JButton("Sửa");
        btnTimKiem = new JButton("Tìm kiếm");
        btnExport = new JButton("Xuất Excel");
        btnQuayLai = new JButton("Quay lại");

        // Fix chiều cao date
        Dimension d = new Dimension(200, 28);
        dateNgayKyLuat.setPreferredSize(d);
        dateNgayKetThuc.setPreferredSize(d);


        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblMaSinhVien)
                                .addComponent(lblTenSinhVien)
                                .addComponent(lblIdKyLuat)
                                .addComponent(lblHinhThuc)
                                .addComponent(lblNgayKyLuat)
                                .addComponent(lblLyDo)
                                .addComponent(lblNgayKetThuc)
                                .addComponent(lblSoQuyetDinh)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(cboMaSinhVien)
                                .addComponent(txtTenSinhVien)
                                .addComponent(txtIdKyLuat)
                                .addComponent(cboHinhThuc)
                                .addComponent(dateNgayKyLuat)
                                .addComponent(txtLyDo)
                                .addComponent(dateNgayKetThuc)
                                .addComponent(txtSoQuyetDinh)
                                .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                        .addComponent(btnThem)
                                        .addComponent(btnXoa)
                                        .addComponent(btnSua)
                                        .addComponent(btnTimKiem)
                                        .addComponent(btnExport)
                                        .addComponent(btnQuayLai)
                                )
                        )
                        .addGap(30)
        );


        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblMaSinhVien)
                                .addComponent(cboMaSinhVien)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTenSinhVien)
                                .addComponent(txtTenSinhVien)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblIdKyLuat)
                                .addComponent(txtIdKyLuat)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblHinhThuc)
                                .addComponent(cboHinhThuc)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNgayKyLuat)
                                .addComponent(dateNgayKyLuat)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblLyDo)
                                .addComponent(txtLyDo)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNgayKetThuc)
                                .addComponent(dateNgayKetThuc)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblSoQuyetDinh)
                                .addComponent(txtSoQuyetDinh)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnThem)
                                .addComponent(btnXoa)
                                .addComponent(btnSua)
                                .addComponent(btnTimKiem)
                                .addComponent(btnExport)
                                .addComponent(btnQuayLai)
                        )
        );

        container.add(form, BorderLayout.CENTER);
        return container;
    }

    private void tableInit() {
        model = new DefaultTableModel(
                new String[]{
                        "Mã kỷ luật", "Mã sinh viên", "Tên sinh viên", "Hình thức",
                        "Ngày kỷ luật", "Ngày kết thúc", "Số quyết định", "Lý do"
                }, 0
        );

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        scrollPane = new JScrollPane(table);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
    }
}
