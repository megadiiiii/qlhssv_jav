package org.example.View;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.toedter.calendar.JDateChooser;
public class Discipline extends JPanel {
        public Discipline() {
            initUI();
        }
        public JTextField txtIdKyLuat, txtHinhThuc, txtLyDo, txtSoQuyetDinh, txtMaSinhVien;
        public JDateChooser dateNgayKyLuat, dateNgayKetThuc;
        public JLabel lblIdKyLuat, lblHinhThuc, lblNgayKyLuat, lblLyDo, lblNgayKetThuc, lblSoQuyetDinh, lblMaSinhVien;
        public JButton btnThem, btnXoa, btnSua, btnQuayLai;

        public DefaultTableModel model;
        public JScrollPane scrollPane;
        public JTable table;

        private void initUI() {
            setLayout(new BorderLayout());

            JLabel lblTitle = new JLabel("Quản lý thông tin kỷ luật");
            lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
            lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            add(lblTitle, BorderLayout.NORTH);

            add(formInfoInit(), BorderLayout.CENTER);
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

            lblIdKyLuat = new JLabel("Mã kỷ luật");
            txtIdKyLuat = new JTextField();
            txtIdKyLuat.setEditable(false); // auto_increment

            lblHinhThuc = new JLabel("Hình thức");
            txtHinhThuc = new JTextField();

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

            lblMaSinhVien = new JLabel("Mã sinh viên");
            txtMaSinhVien = new JTextField();

            btnThem = new JButton("Thêm");
            btnXoa = new JButton("Xóa");
            btnSua = new JButton("Sửa");
            btnQuayLai = new JButton("Quay lại");

            layout.setHorizontalGroup(
                    layout.createSequentialGroup()
                            .addGap(30)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIdKyLuat)
                                    .addComponent(lblHinhThuc)
                                    .addComponent(lblNgayKyLuat)
                                    .addComponent(lblLyDo)
                                    .addComponent(lblNgayKetThuc)
                                    .addComponent(lblSoQuyetDinh)
                                    .addComponent(lblMaSinhVien)
                            )
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIdKyLuat)
                                    .addComponent(txtHinhThuc)
                                    .addComponent(dateNgayKyLuat)
                                    .addComponent(txtLyDo)
                                    .addComponent(dateNgayKetThuc)
                                    .addComponent(txtSoQuyetDinh)
                                    .addComponent(txtMaSinhVien)
                                    .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                            .addComponent(btnThem)
                                            .addComponent(btnXoa)
                                            .addComponent(btnSua)
                                            .addComponent(btnQuayLai)
                                    )
                            )
                            .addGap(30)
            );
            layout.setVerticalGroup(
                    layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblIdKyLuat)
                                    .addComponent(txtIdKyLuat)
                            )
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblHinhThuc)
                                    .addComponent(txtHinhThuc)
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
                                    .addComponent(lblMaSinhVien)
                                    .addComponent(txtMaSinhVien)
                            )
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnThem)
                                    .addComponent(btnXoa)
                                    .addComponent(btnSua)
                                    .addComponent(btnQuayLai)
                            )
            );
            container.add(form, BorderLayout.CENTER);
            return container;
        }
        private void tableInit() {
            model = new DefaultTableModel(
                    new String[]{
                            "Mã kỷ luật","Hình thức","Ngày kỷ luật", "Lý do",  "Ngày kết thúc", "Số quyết định", "Mã sinh viên"
                    }, 0
            );
            table = new JTable(model);
            table.setFillsViewportHeight(true);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            table.setRowHeight(30);
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
            table.getTableHeader().setBackground(Color.LIGHT_GRAY);
            table.getTableHeader().setForeground(Color.BLACK);
            scrollPane = new JScrollPane(table);
            JPanel tablePanel = new JPanel(new BorderLayout());
            tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
            tablePanel.add(scrollPane, BorderLayout.CENTER);
            add(tablePanel, BorderLayout.SOUTH);
        }
    }
