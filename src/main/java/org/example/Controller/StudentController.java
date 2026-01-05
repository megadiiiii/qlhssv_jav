package org.example.Controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DAO.ClassDAO;
import org.example.DAO.FacuDAO;
import org.example.DAO.MajorDAO;
import org.example.DAO.StudentDAO;
import org.example.Model.*;
import org.example.View.MainFrame;
import org.example.View.StudentView;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentController {
    private final StudentView view;
    private final StudentDAO dao;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public StudentController(StudentView view, MainFrame mainFrame, StudentDAO dao) {
        this.view = view;
        this.dao = dao;
        initAction(mainFrame);

        loadCombobox();

        loadTable();
        initTableListener();
    }

    private void initTableListener() {
        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) onRowSelected();
        });
    }

    private void initAction(MainFrame mainFrame) {
        view.btnAdd.addActionListener(e -> onInsert());
        view.btnEdit.addActionListener(e -> onUpdate());
        view.btnDelete.addActionListener(e -> onDelete());
        view.btnSearch.addActionListener(e -> onSearch());
        view.btnExport.addActionListener(e -> onExport());
        view.btnBack.addActionListener(e -> mainFrame.showView("HOME"));
    }

    private void loadTable() {
        List<Student> studentList = dao.getAllStudents();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        view.model.setRowCount(0);
        for (Student s : studentList) {
            view.model.addRow(new Object[]{
                    s.getStudentId(),
                    s.getLastName(),
                    s.getFirstName(),
                    sdf.format(s.getDob()),
                    s.getGender(),
                    s.getClassInfo().getClassName(),
                    s.getCohort().getCohortName(),
                    s.getMajor().getMajorName(),
                    s.getFaculty().getFacuName(),
                    s.getStatus(),
                    s.getHometown(),
                    s.getPhone(),
                    s.getEmail(),
                    s.getCitizenId()
            });
        }
    }

    private void onRowSelected() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtId.setText(view.model.getValueAt(view.table.getSelectedRow(), 0).toString());
        view.txtLastName.setText(view.model.getValueAt(view.table.getSelectedRow(), 1).toString());
        view.txtFirstName.setText(view.model.getValueAt(view.table.getSelectedRow(), 2).toString());

        Object dobObj = view.model.getValueAt(row, 3);
        if (dobObj != null && !dobObj.toString().isEmpty()) {
            try {
                Date dob = new SimpleDateFormat("dd/MM/yyyy").parse(dobObj.toString());
                view.dcDob.setDate(dob);
            } catch (ParseException ex) {
                view.dcDob.setDate(null);
                ex.printStackTrace();
            }
        } else {
            view.dcDob.setDate(null);
        }
        view.dcDob.setDateFormatString("dd/MM/yyyy");


        String gender = view.model.getValueAt(row, 4).toString();
        view.cboGender.setSelectedItem(gender);

        String className = view.model.getValueAt(row, 5).toString();

        view.txtCohort.setText(view.model.getValueAt(row, 6).toString());

        String majorName = view.model.getValueAt(row, 7).toString();
        String facuName = view.model.getValueAt(row, 8).toString();

        view.cboStatus.setSelectedItem(view.model.getValueAt(row, 9).toString());

        view.txtHometown.setText(view.model.getValueAt(row, 10).toString());

        view.txtPhone.setText(view.model.getValueAt(row, 11).toString());

        view.txtEmail.setText(view.model.getValueAt(row, 12).toString());

        view.txtCitizenId.setText(view.model.getValueAt(row, 13).toString());

        for (int i = 0; i < view.cboFacu.getItemCount(); i++) {
            Faculties facu = view.cboFacu.getItemAt(i);
            if (facuName.equals(facu.getFacuName())) {
                view.cboFacu.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < view.cboMajor.getItemCount(); i++) {
            Major major = view.cboMajor.getItemAt(i);
            if (majorName.equals(major.getMajorName())) {
                view.cboMajor.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < view.cboClass.getItemCount(); i++) {
            ClassInfo cl = view.cboClass.getItemAt(i);
            if (className.equals(cl.getClassName())) {
                view.cboClass.setSelectedIndex(i);
                break;
            }
        }
    }

    public void loadCombobox() {
        FacuDAO facuDAO = new FacuDAO();
        List<Faculties> faculties = facuDAO.findAll();

        view.cboFacu.removeAllItems();
        view.cboFacuSearch.removeAllItems();

        view.cboFacu.addItem(new Faculties("", "--Chọn Khoa/Viện--"));
        view.cboFacuSearch.addItem(new Faculties("", "--Chọn Khoa/Viện--"));

        view.cboMajor.addItem(new Major("", "--Chọn Chuyên ngành--", null));
        view.cboMajorSearch.addItem(new Major("", "--Chọn Chuyên ngành--", null));

        view.cboClass.addItem(new ClassInfo("", "--Chọn lớp--"));
        view.cboClassSearch.addItem(new ClassInfo("", "--Chọn lớp--"));

        for (Faculties f : faculties) {
            view.cboFacu.addItem(f);
            view.cboFacuSearch.addItem(f);
        }

        view.cboFacu.addActionListener(e -> {
            Faculties selectedFacu = (Faculties) view.cboFacu.getSelectedItem();
            if (selectedFacu != null) {
                loadMajorByFaculty(selectedFacu.getFacuId());
            }
        });

        view.cboMajor.addActionListener(e -> {
            Major selectedMajor = (Major) view.cboMajor.getSelectedItem();
            if (selectedMajor != null && !selectedMajor.getMajorId().isEmpty()) {
                loadClassByMajor(selectedMajor.getMajorId());
            }
        });

        view.cboClass.addActionListener(e -> {
            ClassInfo selectedClassInfo = (ClassInfo) view.cboClass.getSelectedItem();

            if (selectedClassInfo == null) return;
            if (selectedClassInfo.getClassId() == null || selectedClassInfo.getClassId().isEmpty()) {
                view.txtCohort.setText("");
                return;
            }

            if (selectedClassInfo.getCohort() != null) {
                view.txtCohort.setText(selectedClassInfo.getCohort().getCohortName());
                view.txtCohort.setEditable(false);
            }
        });

        //Cũng như trên nhưng dùng cho search
        view.cboFacuSearch.addActionListener(e -> {
            Faculties selectedFacuSearch = (Faculties) view.cboFacuSearch.getSelectedItem();
            if (selectedFacuSearch != null) {
                loadMajorByFacultySearch(selectedFacuSearch.getFacuId());
            }
        });

        view.cboMajorSearch.addActionListener(e -> {
            Major selectedMajorSearch = (Major) view.cboMajorSearch.getSelectedItem();
            if (selectedMajorSearch != null && !selectedMajorSearch.getMajorId().isEmpty()) {
                loadClassByMajorSearch(selectedMajorSearch.getMajorId());
            }
        });
    }

    private void loadClassByMajor(String majorId) {
        ClassDAO classDAO = new ClassDAO();
        List<ClassInfo> classList = classDAO.findAllClassesByMajors(majorId);
        view.cboClass.removeAllItems();
        view.cboClass.addItem(new ClassInfo("", "--Chọn lớp--"));
        for (ClassInfo cl : classList) {
            view.cboClass.addItem(cl);
        }
    }

    private void loadMajorByFaculty(String facuId) {
        MajorDAO majorDAO = new MajorDAO();
        List<Major> majors = majorDAO.findAllMajorsByFaculty(facuId);
        view.cboMajor.removeAllItems();
        view.cboMajor.addItem(new Major("", "--Chọn Chuyên ngành--"));
        for (Major m : majors) view.cboMajor.addItem(m);
    }

    private void loadClassByMajorSearch(String majorId) {
        ClassDAO classDAO = new ClassDAO();
        List<ClassInfo> classList = classDAO.findAllClassesByMajors(majorId);
        view.cboClassSearch.removeAllItems();
        view.cboClassSearch.addItem(new ClassInfo("", "--Chọn lớp--"));
        for (ClassInfo cl : classList) {
            view.cboClassSearch.addItem(cl);
        }
    }

    private void loadMajorByFacultySearch(String facuId) {
        MajorDAO majorDAO = new MajorDAO();
        List<Major> majors = majorDAO.findAllMajorsByFaculty(facuId);
        view.cboMajorSearch.removeAllItems();
        view.cboMajorSearch.addItem(new Major("", "--Chọn Chuyên ngành--"));
        for (Major m : majors) view.cboMajorSearch.addItem(m);
    }

    private String generateStudentId() {
        Faculties facu = (Faculties) view.cboFacu.getSelectedItem();
        Major major = (Major) view.cboMajor.getSelectedItem();
        ClassInfo clazz = (ClassInfo) view.cboClass.getSelectedItem();

        if (facu == null || major == null || clazz == null) return "";

        // bỏ placeholder
        if (facu.getFacuId().isEmpty()
                || major.getMajorId().isEmpty()
                || clazz.getClassId().isEmpty())
            return "";

        Cohort cohort = clazz.getCohort();
        if (cohort == null) return "";

        String majorCode = major.getMajorId();
        String cohortCode = String.valueOf(cohort.getCohortName());

        // đếm số SV hiện có trong cohort
        int studentCnt = dao.countStudentsByCohort(cohort.getCohortId());
        String count = String.format("%04d", studentCnt + 1);

        return cohortCode + majorCode + count;
    }


    public void onInsert() {
        String studentId = generateStudentId();
        String lastName = view.txtLastName.getText().trim();
        String firstName = view.txtFirstName.getText().trim();
        Date dob = view.dcDob.getDate();
        if (dob == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày sinh");
            return;
        }

        String gender = view.cboGender.getSelectedItem().toString();
        String email = view.txtEmail.getText().trim();
        String hometown = view.txtHometown.getText().trim();
        String phone = view.txtPhone.getText().trim();
        String citizenId = view.txtCitizenId.getText().trim();

        Faculties facu = (Faculties) view.cboFacu.getSelectedItem();
        Major major = (Major) view.cboMajor.getSelectedItem();
        ClassInfo cl = (ClassInfo) view.cboClass.getSelectedItem();

        if (facu == null || facu.getFacuId().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn khoa/viện");
            return;
        }

        if (major == null || major.getMajorId().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chuyên ngành");
            return;
        }

        if (cl == null || cl.getClassId().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng lớp");
            return;
        }

        String status = view.cboStatus.getSelectedItem().toString();

        if (lastName.isEmpty() || firstName.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập họ tên");
            return;
        }

        Student st = new Student(studentId, lastName, firstName, dob, gender, email, hometown, phone, citizenId, facu, major, cl, cl.getCohort(), status);

        if (dao.isClassFull(cl.getClassId())) {
            JOptionPane.showMessageDialog(view, "Lớp " + cl.getClassName() + "đã đủ sinh viên, vui lòng chọn lớp khác", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (JOptionPane.showConfirmDialog(view, "Xác nhận thêm SV \nMã SV: " + studentId + "\nTên SV: " + lastName + " " + firstName + "\nLớp: " + cl.getClassName()) == JOptionPane.YES_OPTION) {
                try {
                    int result = dao.insert(st);
                    if (result > 0) {
                        JOptionPane.showMessageDialog(view, "Thêm SV thành công");
                        loadTable();
                        clearInput();
                    } else {
                        JOptionPane.showMessageDialog(view, "Thêm SV thất bại");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view, e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    public void onUpdate() {
        int row = view.table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn SV để xóa");
            return;
        }

        String studentId = view.model.getValueAt(row, 0).toString();
        String lastName = view.txtLastName.getText().trim();
        String firstName = view.txtFirstName.getText().trim();
        Date dob = view.dcDob.getDate();
        if (dob == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày sinh");
            return;
        }

        String gender = view.cboGender.getSelectedItem().toString();
        String email = view.txtEmail.getText().trim();
        String hometown = view.txtHometown.getText().trim();
        String phone = view.txtPhone.getText().trim();
        String citizenId = view.txtCitizenId.getText().trim();

        Faculties facu = (Faculties) view.cboFacu.getSelectedItem();
        Major major = (Major) view.cboMajor.getSelectedItem();
        ClassInfo cl = (ClassInfo) view.cboClass.getSelectedItem();

        if (facu == null || facu.getFacuId().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn khoa/viện");
            return;
        }

        if (major == null || major.getMajorId().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chuyên ngành");
            return;
        }

        if (cl == null || cl.getClassId().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng lớp");
            return;
        }

        String status = view.cboStatus.getSelectedItem().toString();

        if (lastName.isEmpty() || firstName.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập họ tên");
            return;
        }

        Student st = new Student(studentId, lastName, firstName, dob, gender, email, hometown, phone, citizenId, facu, major, cl, cl.getCohort(), status);
        try {
            if (JOptionPane.showConfirmDialog(view, "Xác nhận cập nhật thông tin SV \nMã SV: " + studentId + "\nTên SV: " + lastName + " " + firstName + "\nLớp: " + cl.getClassName()) == JOptionPane.YES_OPTION) {
                try {
                    int result = dao.update(st);
                    if (result > 0) {
                        JOptionPane.showMessageDialog(view, "Cập nhật thành công");
                        loadTable();
                        clearInput();
                    } else {
                        JOptionPane.showMessageDialog(view, "Cập nhật thất bại");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view, e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    public void onDelete() {
        int row = view.table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn SV để xóa");
            return;
        }

        String studentId = view.model.getValueAt(row, 0).toString();
        if (JOptionPane.showConfirmDialog(view, "Xác nhận xóa sinh viên" + studentId + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int result = dao.delete(studentId);
            if (result > 0) {
                JOptionPane.showMessageDialog(view, "Xóa lớp " + studentId + " thành công");
                loadTable();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa lớp " + studentId + " thất bại");
            }
        }
    }

    public void onSearch() {
        String studentId = view.txtIdSearch.getText().trim();
        String studentName = view.txtFullNameSearch.getText().trim();
        Faculties facu = (Faculties) view.cboFacuSearch.getSelectedItem();
        Major major = (Major) view.cboMajorSearch.getSelectedItem();
        ClassInfo cl = (ClassInfo) view.cboClassSearch.getSelectedItem();

        if (studentName.isEmpty() && studentId.isEmpty() && facu == null && major == null && cl == null) {
            loadTable();
            clearInput();
            return;
        }

        try {
            List<Student> result = dao.search(studentId, studentName, facu, major, cl);

            view.model.setRowCount(0);
            for (Student s : result) {
                view.model.addRow(new Object[]{
                        s.getStudentId(),
                        s.getLastName(),
                        s.getFirstName(),
                        sdf.format(s.getDob()),
                        s.getGender(),
                        s.getClassInfo().getClassName(),
                        s.getCohort().getCohortName(),
                        s.getMajor().getMajorName(),
                        s.getFaculty().getFacuName(),
                        s.getStatus(),
                        s.getHometown(),
                        s.getPhone(),
                        s.getEmail(),
                        s.getCitizenId()
                });
            }

            if (result.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Không tìm thấy kết quả");

            }

            view.table.clearSelection();
            clearSearchInput();
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    private void onExport() {
        List<Student> studentList = dao.getAllStudents();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("Danh_sach_sinh_vien.xlsx")); //
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            java.io.File filePath = fileChooser.getSelectedFile();
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Danh sách sinh viên");

                // === TITLE ===
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("BẢNG DANH SÁCH SINH VIÊN");

                // Style title
                CellStyle titleStyle = workbook.createCellStyle();
                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleCell.setCellStyle(titleStyle);

                // Merge title across all columns
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));

                // === HEADER ===
                Row headerRow = sheet.createRow(1);
                String[] headers = {"STT", "Mã SV", "Họ đệm", "Tên", "Ngày sinh", "Giới tính", "Lớp", "Khóa",
                        "Chuyên ngành", "Khoa", "Trạng thái", "Quê quán", "SĐT", "Email", "Số CCCD"};
                CellStyle headerStyle = workbook.createCellStyle();
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);
                headerStyle.setAlignment(HorizontalAlignment.CENTER);
                headerStyle.setBorderBottom(BorderStyle.THIN);
                headerStyle.setBorderTop(BorderStyle.THIN);
                headerStyle.setBorderLeft(BorderStyle.THIN);
                headerStyle.setBorderRight(BorderStyle.THIN);

                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // === DATA ===
                CellStyle dataStyle = workbook.createCellStyle();
                dataStyle.setBorderBottom(BorderStyle.THIN);
                dataStyle.setBorderTop(BorderStyle.THIN);
                dataStyle.setBorderLeft(BorderStyle.THIN);
                dataStyle.setBorderRight(BorderStyle.THIN);

                int no = 1; //STT
                int rowIndex = 2;
                for (Student st : studentList) {
                    Row row = sheet.createRow(rowIndex++);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(no++);
                    cell0.setCellStyle(dataStyle);

                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(st.getStudentId());
                    cell1.setCellStyle(dataStyle);

                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue(st.getLastName());
                    cell2.setCellStyle(dataStyle);

                    Cell cell3 = row.createCell(3);
                    cell3.setCellValue(st.getFirstName());
                    cell3.setCellStyle(dataStyle);

                    Cell cell4 = row.createCell(4);
                    cell4.setCellValue(sdf.format(st.getDob()));
                    cell4.setCellStyle(dataStyle);

                    Cell cell5 = row.createCell(5);
                    cell5.setCellValue(st.getGender());
                    cell5.setCellStyle(dataStyle);

                    Cell cell6 = row.createCell(6);
                    cell6.setCellValue(st.getClassInfo().getClassName());
                    cell6.setCellStyle(dataStyle);

                    Cell cell7 = row.createCell(7);
                    cell7.setCellValue(st.getCohort().getCohortName());
                    cell7.setCellStyle(dataStyle);

                    Cell cell8 = row.createCell(8);
                    cell8.setCellValue(st.getMajor().getMajorName());
                    cell8.setCellStyle(dataStyle);

                    Cell cell9 = row.createCell(9);
                    cell9.setCellValue(st.getFaculty().getFacuName());
                    cell9.setCellStyle(dataStyle);

                    Cell cell10 = row.createCell(10);
                    cell10.setCellValue(st.getStatus());
                    cell10.setCellStyle(dataStyle);

                    Cell cell11 = row.createCell(11);
                    cell11.setCellValue(st.getHometown());
                    cell11.setCellStyle(dataStyle);

                    Cell cell12 = row.createCell(12);
                    cell12.setCellValue(st.getPhone());
                    cell12.setCellStyle(dataStyle);

                    Cell cell13 = row.createCell(13);
                    cell13.setCellValue(st.getEmail());
                    cell13.setCellStyle(dataStyle);

                    Cell cell14 = row.createCell(14);
                    cell14.setCellValue(st.getCitizenId());
                    cell14.setCellStyle(dataStyle);
                }

                // === AUTO FILTER ===
                sheet.setAutoFilter(new CellRangeAddress(
                        1,
                        sheet.getLastRowNum(),
                        0,
                        headers.length - 1
                ));

                // === FREEZE HEADER (title + header) ===
                sheet.createFreezePane(0, 2);

                // === AUTO SIZE COLUMNS ===
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }


                // Auto-size columns
                // Ghi file
                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    workbook.write(fos);
                }

                JOptionPane.showMessageDialog(view, "Xuất Excel thành công!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Xuất Excel thất bại: " + ex.getMessage());
            }
        }
    }

    private void clearInput() {
        view.txtId.setText("");
        view.txtLastName.setText("");
        view.txtFirstName.setText("");
        view.dcDob.setDate(new java.util.Date());
        view.cboGender.setSelectedIndex(0);
        view.txtHometown.setText("");
        view.txtPhone.setText("");
        view.txtEmail.setText("");
        view.txtCitizenId.setText("");
        view.cboFacu.setSelectedIndex(-1);
        view.cboMajor.removeAllItems();
        view.cboClass.removeAllItems();
        view.cboStatus.setSelectedIndex(0);
    }

    private void clearSearchInput() {
        view.txtIdSearch.setText("");
        view.txtFullNameSearch.setText("");
        view.cboFacuSearch.setSelectedIndex(-1);
        view.cboMajorSearch.removeAllItems();
        view.cboClassSearch.removeAllItems();
    }
}