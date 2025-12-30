package org.example.View;

import com.toedter.calendar.JDateChooser;
import org.example.DAO.ClassDAO;
import org.example.DAO.FacuDAO;
import org.example.DAO.MajorDAO;
import org.example.Model.ClassInfo;
import org.example.Model.Faculties;
import org.example.Model.Major;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class StudentFormPanel extends JPanel {
    // ===== PERSONAL INFO =====
    public JTextField txtId, txtLastName, txtFirstName, txtHometown, txtPhone, txtEmail, txtIdNo;
    public JDateChooser txtDob;
    public List<Faculties> currentFacuList;
    public List<Major> currentMajorList;
    public List<ClassInfo> currentClassList;
    public JComboBox<String> cboGender, cboFacu, cboMajor, cboClass, cboStatus;


    // ===== STUDY INFO =====

    public StudentFormPanel() {
        setLayout(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Thông tin cá nhân", personalPanel());
        tabs.add("Thông tin học tập", studyPanel());

        add(tabs, BorderLayout.CENTER);
        initCombos();
    }

    private JPanel personalPanel() {
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5, 5, 5, 5);
        g.weightx = 1;

        int row = 0;

        // Row 1: Mã SV | Họ đệm | Tên
        addToGrid(formContainer, g, 0, row, new JLabel("Mã SV"));
        addToGrid(formContainer, g, 1, row, txtId = new JTextField(15));
        addToGrid(formContainer, g, 2, row, new JLabel("Họ đệm"));
        addToGrid(formContainer, g, 3, row, txtLastName = new JTextField(15));
        addToGrid(formContainer, g, 4, row, new JLabel("Tên"));
        addToGrid(formContainer, g, 5, row, txtFirstName = new JTextField(15));
        row++;

        // Row 2: Ngày sinh | Giới tính | Quê quán
        addToGrid(formContainer, g, 0, row, new JLabel("Ngày sinh"));
        txtDob = new JDateChooser();
        txtDob.setDate(new Date());
        txtDob.setDateFormatString("dd/MM/yyyy");
        addToGrid(formContainer, g, 1, row, txtDob);

        addToGrid(formContainer, g, 2, row, new JLabel("Giới tính"));
        cboGender = new JComboBox<>(new String[]{"Nam", "Nữ"});
        addToGrid(formContainer, g, 3, row, cboGender);

        addToGrid(formContainer, g, 4, row, new JLabel("Quê quán"));
        addToGrid(formContainer, g, 5, row, txtHometown = new JTextField(15));
        row++;

        // Row 3: SĐT | Email | Số CCCD
        addToGrid(formContainer, g, 0, row, new JLabel("SĐT"));
        addToGrid(formContainer, g, 1, row, txtPhone = new JTextField(15));
        addToGrid(formContainer, g, 2, row, new JLabel("Email"));
        addToGrid(formContainer, g, 3, row, txtEmail = new JTextField(15));
        addToGrid(formContainer, g, 4, row, new JLabel("Số CCCD"));
        addToGrid(formContainer, g, 5, row, txtIdNo = new JTextField(15));

        return formContainer;
    }

    private JPanel studyPanel() {
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5, 5, 5, 5);
        g.weightx = 1;

        int row = 0;

        // Row 1: Khoa | Ngành (major dài)
        addToGrid(formContainer, g, 0, row, new JLabel("Khoa/Viện"));
        cboFacu = new JComboBox<>();
        addToGrid(formContainer, g, 1, row, cboFacu);
        addToGrid(formContainer, g, 2, row, new JLabel("Ngành"));
        cboMajor = new JComboBox<>();
        g.gridwidth = 3; // major rộng 3 cột
        addToGrid(formContainer, g, 3, row, cboMajor);
        g.gridwidth = 1; // reset
        row++;

        // Row 2: Lớp | Trạng thái
        addToGrid(formContainer, g, 0, row, new JLabel("Lớp"));
        cboClass = new JComboBox<>();
        addToGrid(formContainer, g, 1, row, cboClass);

        addToGrid(formContainer, g, 2, row, new JLabel("Trạng thái"));
        cboStatus = new JComboBox<>(new String[]{"Đang theo học", "Bị đình chỉ", "Buộc thôi học", "Đã tốt nghiệp"});
        addToGrid(formContainer, g, 3, row, cboStatus);

        return formContainer;
    }

    private void addToGrid(JPanel panel, GridBagConstraints g, int x, int y, Component comp) {
        g.gridx = x;
        g.gridy = y;
        panel.add(comp, g);
    }

    // ==== TEST RUN ====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý sinh viên");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 300);
            frame.add(new StudentFormPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private void initCombos() {
        currentFacuList = new FacuDAO().findAll();
        List<Faculties> facuList = new FacuDAO().findAll();
        DefaultComboBoxModel<String> facuModel = new DefaultComboBoxModel<>();
        for (Faculties f : facuList) facuModel.addElement(f.getFacuName());
        cboFacu.setModel(facuModel);
        cboFacu.setSelectedIndex(-1); // không chọn mặc định

        // Gắn listener 1 lần duy nhất
        cboFacu.addActionListener(e -> {
            int idx = cboFacu.getSelectedIndex();
            if (idx != -1) {
                String facuId = facuList.get(idx).getFacuId();
                loadMajorCombo(facuId);
            } else {
                cboMajor.removeAllItems();
                cboClass.removeAllItems();
            }
        });

        cboMajor.addActionListener(e -> {
            int idx = cboMajor.getSelectedIndex();
            if (currentMajorList != null && idx != -1) {
                String majorId = currentMajorList.get(idx).getMajorId();
                loadClassCombo(majorId);
            } else {
                cboClass.removeAllItems();
            }
        });
    }

    public void loadMajorCombo(String facuId) {
        try {
            currentMajorList = new MajorDAO().findByFaculty(facuId);
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            for (Major m : currentMajorList) model.addElement(m.getMajorName());
            cboMajor.setModel(model);
            cboMajor.setSelectedIndex(-1);
            cboClass.removeAllItems(); // reset lớp khi đổi ngành
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load Ngành");
        }
    }

    public void loadClassCombo(String majorId) {
        try {
            currentClassList = new ClassDAO().findByMajor(majorId);
            List<ClassInfo> classList = new ClassDAO().findByMajor(majorId);
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            for (ClassInfo c : classList) model.addElement(c.getClassName());
            cboClass.setModel(model);
            cboClass.setSelectedIndex(-1);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load Lớp");
        }
    }
}
