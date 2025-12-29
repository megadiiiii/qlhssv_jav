package org.example.View;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class RewardView extends JPanel {

    public RewardView() {
        initUI();
    }

    ///  form
    public JTextField txtRewardId, txtRewardQuyetDinh;
    public JComboBox<String> cboStudentId;
    public JTextField txtStudentName;
    public JTextArea txtRewardNote;

    public JDateChooser dcRewardDate;

    public JLabel lblRewardId, lblStudentId, lblStudentName, lblRewardDate, lblRewardNote, lblRewardQuyetDinh;

    public JButton btnAddReward, btnDeleteReward, btnEditReward, btnBack;

    // bảng
    public DefaultTableModel model;
    public JScrollPane scrollPane;
    public JTable table;

    private JPanel tablePanel;

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Quản lý khen thưởng");
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

        lblRewardId = new JLabel("Reward ID");
        txtRewardId = new JTextField();
        txtRewardId.setEnabled(false);

        lblStudentId = new JLabel("Mã sinh viên");
        cboStudentId = new JComboBox<>();
        cboStudentId.setEditable(true); // nhap chon deu dc

        lblStudentName = new JLabel("Tên sinh viên");
        txtStudentName = new JTextField();
        txtStudentName.setEnabled(false);

        lblRewardDate = new JLabel("Ngày");
        dcRewardDate = new JDateChooser();
        dcRewardDate.setDateFormatString("yyyy-MM-dd");
        dcRewardDate.setDate(new Date());

        lblRewardQuyetDinh = new JLabel("Quyết định");
        txtRewardQuyetDinh = new JTextField();

        lblRewardNote = new JLabel("Lý do");
        txtRewardNote = new JTextArea(3, 20);
        txtRewardNote.setLineWrap(true);
        txtRewardNote.setWrapStyleWord(true);
        JScrollPane noteScroll = new JScrollPane(txtRewardNote);

        btnAddReward = new JButton("Thêm");
        btnDeleteReward = new JButton("Xóa");
        btnEditReward = new JButton("Sửa");
        btnBack = new JButton("Quay lại");

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblRewardId)
                                .addComponent(lblStudentId)
                                .addComponent(lblStudentName)
                                .addComponent(lblRewardDate)
                                .addComponent(lblRewardQuyetDinh)
                                .addComponent(lblRewardNote)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtRewardId)
                                .addComponent(cboStudentId)
                                .addComponent(txtStudentName)
                                .addComponent(dcRewardDate)
                                .addComponent(txtRewardQuyetDinh)
                                .addComponent(noteScroll)
                                .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                        .addComponent(btnAddReward)
                                        .addComponent(btnDeleteReward)
                                        .addComponent(btnEditReward)
                                        .addComponent(btnBack)
                                )
                        )
                        .addGap(30)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblRewardId)
                                .addComponent(txtRewardId)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblStudentId)
                                .addComponent(cboStudentId)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblStudentName)
                                .addComponent(txtStudentName)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblRewardDate)
                                .addComponent(dcRewardDate)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblRewardQuyetDinh)
                                .addComponent(txtRewardQuyetDinh)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblRewardNote)
                                .addComponent(noteScroll)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAddReward)
                                .addComponent(btnDeleteReward)
                                .addComponent(btnEditReward)
                                .addComponent(btnBack)
                        )
        );

        container.add(form, BorderLayout.CENTER);
        return container;
    }

    private void tableInit() {
        model = new DefaultTableModel(
                new String[]{"Reward ID", "Mã SV", "Tên SV", "Ngày", "Lý do", "Quyết định"}, 0
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

        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
    }
}
