package org.example.View;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ScholarshipView extends JPanel {

    public ScholarshipView() {
        initUI();
    }

    /// ===== FORM =====
    public JTextField txtScholarshipId;
    public JComboBox<String> cboStudentId;
    public JTextField txtScoreLevel, txtDrlLevel, txtScholarshipLevel, txtSemester;
    public JTextField txtSearch;

    public JLabel lblScholarshipId, lblStudentId, lblScoreLevel,
            lblDrlLevel, lblScholarshipLevel, lblSemester;

    public JButton btnAdd, btnDelete, btnEdit, btnBack, btnSearch;

    /// ===== TABLE =====
    public DefaultTableModel model;
    public JScrollPane scrollPane;
    public JTable table;

    private JPanel tablePanel;

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Quản lý học bổng");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(lblTitle, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        center.add(formInfoInit(), BorderLayout.NORTH);

        tableInit();
        center.add(tablePanel, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
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

        lblScholarshipId = new JLabel("Scholarship ID");
        txtScholarshipId = new JTextField();
        txtScholarshipId.setEnabled(false);

        lblStudentId = new JLabel("Mã sinh viên");
        cboStudentId = new JComboBox<>();
        cboStudentId.setEditable(true);

        lblScoreLevel = new JLabel("Mức học lực");
        txtScoreLevel = new JTextField();

        lblDrlLevel = new JLabel("Mức rèn luyện");
        txtDrlLevel = new JTextField();

        lblScholarshipLevel = new JLabel("Mức học bổng");
        txtScholarshipLevel = new JTextField();

        lblSemester = new JLabel("Học kỳ");
        txtSemester = new JTextField();

        btnAdd = new JButton("Thêm");
        btnDelete = new JButton("Xóa");
        btnEdit = new JButton("Sửa");
        btnBack = new JButton("Quay lại");

        // ===== SEARCH =====
        txtSearch = new JTextField(15);
        btnSearch = new JButton("Tìm");

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblScholarshipId)
                                .addComponent(lblStudentId)
                                .addComponent(lblScoreLevel)
                                .addComponent(lblDrlLevel)
                                .addComponent(lblScholarshipLevel)
                                .addComponent(lblSemester)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtScholarshipId)
                                .addComponent(cboStudentId)
                                .addComponent(txtScoreLevel)
                                .addComponent(txtDrlLevel)
                                .addComponent(txtScholarshipLevel)
                                .addComponent(txtSemester)
                                .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                        .addComponent(btnAdd)
                                        .addComponent(btnDelete)
                                        .addComponent(btnEdit)
                                        .addComponent(btnBack)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtSearch)
                                        .addComponent(btnSearch)
                                )
                        )
                        .addGap(30)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblScholarshipId)
                                .addComponent(txtScholarshipId)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblStudentId)
                                .addComponent(cboStudentId)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblScoreLevel)
                                .addComponent(txtScoreLevel)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDrlLevel)
                                .addComponent(txtDrlLevel)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblScholarshipLevel)
                                .addComponent(txtScholarshipLevel)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblSemester)
                                .addComponent(txtSemester)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAdd)
                                .addComponent(btnDelete)
                                .addComponent(btnEdit)
                                .addComponent(btnBack)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSearch)
                                .addComponent(btnSearch)
                        )
        );

        container.add(form, BorderLayout.CENTER);
        return container;
    }

    private void tableInit() {
        model = new DefaultTableModel(
                new String[]{"ID", "Mã SV", "Tên SV","Học lực", "RL", "Mức HB", "Học kỳ"}, 0
        );

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(Color.LIGHT_GRAY);
        table.getTableHeader().setForeground(Color.BLACK);

        // Ẩn cột ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        scrollPane = new JScrollPane(table);

        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
    }
}
