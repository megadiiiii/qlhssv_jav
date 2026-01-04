package org.example.View;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RewardView extends JPanel {

    public RewardView() {
        initUI();
    }

    ///  form
    public JTextField txtRewardId, txtRewardQuyetDinh, txtRewardNote;
    public JComboBox<String> cboStudentId;
    public JTextField txtStudentName;
    public JTextField txtStudentIdSearch;

    public JDateChooser dcRewardDate;
    public JDateChooser dcRewardDateSearch;

    public JButton btnAddReward, btnDeleteReward, btnEditReward, btnExportReward, btnBack;

    // bảng
    public DefaultTableModel model;
    public JTable table;

    private JPanel tablePanel;

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Quản lý khen thưởng");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(lblTitle, BorderLayout.NORTH);

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
        formCrud.add(new JLabel("Ngày khen thưởng"), g);
        g.gridx = 1;
        g.weightx = 0.5;
        formCrud.add(dcRewardDate = new JDateChooser(), g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Quyết định"), g);
        g.gridx = 3;
        g.weightx = 0.5;
        formCrud.add(txtRewardQuyetDinh = new JTextField(10), g);
        row++;

        // Row 1
        g.gridy = row;
        g.gridx = 0;
        g.weightx = 0;
        formCrud.add(new JLabel("ID Khen thưởng"), g);
        g.gridx = 1;
        g.weightx = 0.5;
        txtRewardId = new JTextField();
        txtRewardId.setEnabled(false);
        formCrud.add(txtRewardId, g);

        g.gridx = 2;
        g.weightx = 0;
        formCrud.add(new JLabel("Lý do"), g);
        g.gridx = 3;
        g.weightx = 0.5;
        txtRewardNote = new JTextField();
        formCrud.add(txtRewardNote, g);

        //5 buttons in one bar
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        btnAddReward = new JButton("Thêm");
        btnEditReward = new JButton("Sửa");
        btnDeleteReward = new JButton("Xóa");
        btnExportReward = new JButton("Xuất Excel");
        btnBack = new JButton("Quay lại");

        btnBar.add(btnAddReward);
        btnBar.add(btnEditReward);
        btnBar.add(btnDeleteReward);
        btnBar.add(btnExportReward);
        btnBar.add(btnBack);

        wrapper.add(formCrud, BorderLayout.NORTH);
        wrapper.add(btnBar, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane tableInit() {
        model = new DefaultTableModel(
                new String[]{
                        "Reward ID", "Mã SV", "Tên SV", "Ngày", "Lý do", "Quyết định"
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
