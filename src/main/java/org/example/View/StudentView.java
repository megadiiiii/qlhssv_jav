package org.example.View;

import com.toedter.calendar.JDateChooser;
import org.example.Model.ClassInfo;
import org.example.Model.Faculties;
import org.example.Model.Major;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class StudentView extends JPanel {
    public DefaultTableModel model;

    public JTable table;
    public JComboBox<String> cboGender, cboStatus;
    public JComboBox<Faculties> cboFacu;
    public JComboBox<Major> cboMajor;
    public JComboBox<ClassInfo> cboClass;
    public JComboBox<Faculties> cboFacuSearch;
    public JComboBox<Major> cboMajorSearch;
    public JComboBox<ClassInfo> cboClassSearch;
    public JTextField txtId, txtLastName, txtFirstName, txtHometown, txtPhone, txtEmail, txtCitizenId, txtCohort;
    public JTextField txtIdSearch, txtFullNameSearch;
    public JDateChooser dcDob;
    public JButton btnAdd, btnEdit, btnDelete, btnBack, btnExport, btnSearch;

    public StudentView() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("Quản lý thông tin sinh viên");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        add(lblTitle, BorderLayout.NORTH);


        add(inputPanel(), BorderLayout.CENTER);
        // Panel phía dưới chứa button và table
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonInit(), BorderLayout.NORTH);
        southPanel.add(tableInit(), BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    private JPanel buttonInit() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnExport = new JButton("Xuất Excel");
        btnSearch = new JButton("Tìm kiếm");
        btnBack = new JButton("Quay lại");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnExport);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnBack);

        return buttonPanel;
    }

    private JPanel inputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        inputPanel.add(tabbedInfo());

        // Tạo search panel với title border
        JPanel searchWrapper = new JPanel(new BorderLayout());
        searchWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 30, 5, 30),
                BorderFactory.createTitledBorder("Tìm kiếm")
        ));
        searchWrapper.add(searchPanel(), BorderLayout.CENTER);

        inputPanel.add(searchWrapper);

        return inputPanel;
    }

    public JTabbedPane tabbedInfo() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        tabs.add("Thông tin cá nhân", personalPanel());
        tabs.add("Thông tin học tập", studyPanel());

        return tabs;
    }

    private JPanel personalPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5, 5, 5, 5);
        g.weightx = 1;

        int row = 0;

        // Row 1: Mã SV | Họ đệm | Tên
        addToGrid(panel, g, 0, row, new JLabel("Mã SV"));
        addToGrid(panel, g, 1, row, txtId = new JTextField(15));
        txtId.setEditable(false);
        addToGrid(panel, g, 2, row, new JLabel("Họ đệm"));
        addToGrid(panel, g, 3, row, txtLastName = new JTextField(15));
        addToGrid(panel, g, 4, row, new JLabel("Tên"));
        addToGrid(panel, g, 5, row, txtFirstName = new JTextField(15));
        row++;

        // Row 2: Ngày sinh | Giới tính | Quê quán
        addToGrid(panel, g, 0, row, new JLabel("Ngày sinh"));
        dcDob = new JDateChooser();
        dcDob.setDate(new Date());
        dcDob.setDateFormatString("dd/MM/yyyy");
        addToGrid(panel, g, 1, row, dcDob);

        addToGrid(panel, g, 2, row, new JLabel("Giới tính"));
        cboGender = new JComboBox<>(new String[]{"Nam", "Nữ"});
        addToGrid(panel, g, 3, row, cboGender);

        addToGrid(panel, g, 4, row, new JLabel("Quê quán"));
        addToGrid(panel, g, 5, row, txtHometown = new JTextField(15));
        row++;

        // Row 3: SĐT | Email | Số CCCD
        addToGrid(panel, g, 0, row, new JLabel("SĐT"));
        addToGrid(panel, g, 1, row, txtPhone = new JTextField(15));
        addToGrid(panel, g, 2, row, new JLabel("Email"));
        addToGrid(panel, g, 3, row, txtEmail = new JTextField(15));
        addToGrid(panel, g, 4, row, new JLabel("Số CCCD"));
        addToGrid(panel, g, 5, row, txtCitizenId = new JTextField(15));
        row++;

        return panel;
    }

    private JPanel studyPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5, 5, 5, 5);

        int row = 0;

        // Row 1: Khoa/Viện | Ngành
        g.weightx = 0.2; // nhãn nhỏ
        addToGrid(panel, g, 0, row, new JLabel("Khoa/Viện"));
        g.weightx = 0.8; // combobox giãn ra
        cboFacu = new JComboBox<>();
        addToGrid(panel, g, 1, row, cboFacu);

        g.weightx = 0.2;
        addToGrid(panel, g, 2, row, new JLabel("Ngành"));
        g.weightx = 0.8;
        cboMajor = new JComboBox<>();
        g.gridwidth = 3; // major rộng 3 cột
        addToGrid(panel, g, 3, row, cboMajor);
        g.gridwidth = 1;
        row++;

        // Row 2: Lớp | Trạng thái
        g.weightx = 0.2;
        addToGrid(panel, g, 0, row, new JLabel("Lớp"));
        g.weightx = 0.8;
        cboClass = new JComboBox<>();
        addToGrid(panel, g, 1, row, cboClass);

        g.weightx = 0.2;
        addToGrid(panel, g, 2, row, new JLabel("Khóa đào tạo"));
        g.weightx = 0.3;
        addToGrid(panel, g, 3, row, txtCohort = new JTextField(15));
        txtCohort.setEditable(false);

        g.weightx = 0.2;
        addToGrid(panel, g, 4, row, new JLabel("Trạng thái"));
        g.weightx = 0.8;
        cboStatus = new JComboBox<>(new String[]{"Đang theo học", "Bị đình chỉ", "Buộc thôi học", "Đã tốt nghiệp"});
        addToGrid(panel, g, 5, row, cboStatus);
        row++;

        return panel;
    }

    private void addToGrid(JPanel panel, GridBagConstraints g, int x, int y, Component comp) {
        g.gridx = x;
        g.gridy = y;
        panel.add(comp, g);
    }

    public JPanel searchPanel() {
        JPanel formSearch = new JPanel(new GridBagLayout());
        formSearch.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(5, 5, 5, 5);
        s.fill = GridBagConstraints.HORIZONTAL;
        s.anchor = GridBagConstraints.WEST;

        // Row 0
        s.gridy = 0;
        s.gridx = 0;
        s.weightx = 1;
        formSearch.add(new JLabel("Mã SV"), s);

        s.gridx = 1;
        s.weightx = 2;
        s.insets = new Insets(5, 5, 5, 30);
        formSearch.add(txtIdSearch = new JTextField(15), s);

        s.gridx = 2;
        s.weightx = 1;
        formSearch.add(new JLabel("Tên SV"), s);

        s.gridx = 3;
        s.weightx = 2;
        s.insets = new Insets(5, 5, 5, 30);
        formSearch.add(txtFullNameSearch = new JTextField(15), s);

        s.gridx = 4;
        s.weightx = 1.0;
        formSearch.add(new JLabel(""), s); // spacer

        // Row 1
        s.gridy = 1;
        s.gridx = 0;
        s.weightx = 1;
        formSearch.add(new JLabel("Khoa/Viện"), s);

        s.gridx = 1;
        s.weightx = 2;
        s.insets = new Insets(5, 5, 5, 30);
        formSearch.add(cboFacuSearch = new JComboBox<>(), s);

        s.gridx = 2;
        s.weightx = 1;
        formSearch.add(new JLabel("Chuyên ngành"), s);

        s.gridx = 3;
        s.weightx = 2;
        s.insets = new Insets(5, 5, 5, 30);
        formSearch.add(cboMajorSearch = new JComboBox<>(), s);

        s.gridx = 4;
        s.weightx = 1;
        formSearch.add(new JLabel("Lớp"), s);

        s.gridx = 5;
        s.weightx = 2;
        s.insets = new Insets(5, 5, 5, 30);
        formSearch.add(cboClassSearch = new JComboBox<>(), s);

        return formSearch;
    }

    private JPanel tableInit() {
        // 1. Tạo model table
        model = new DefaultTableModel(new String[]{
                "Mã SV", "Họ đệm", "Tên", "Ngày sinh", "Giới tính",
                "Lớp", "Khóa", "Chuyên ngành", "Khoa", "Trạng thái",
                "Quê quán", "SĐT", "Email", "Số CCCD"}, 0);

        // 2. Tạo JTable
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        int[] widths = {120, 150, 80, 120, 80, 200, 50, 250, 150, 150, 100, 120, 180, 150};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        // 3. Tạo JScrollPane cho table
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách"));

        // 4. Tạo JPanel bọc ngoài để padding bên ngoài
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30)); // top, left, bottom, right
        wrapper.add(sp, BorderLayout.CENTER);

        return wrapper;
    }
}