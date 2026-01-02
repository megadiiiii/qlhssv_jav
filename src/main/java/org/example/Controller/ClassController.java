package org.example.Controller;

import org.example.DAO.*;
import org.example.Model.*;
import org.example.View.ClassView;
import org.example.View.MainFrame;

import javax.swing.*;
import java.util.List;

public class ClassController {
    private ClassView view;
    private ClassDAO classDAO;

    public ClassController(ClassView classView, MainFrame mainFrame, ClassDAO classDAO) {
        this.view = classView;
        this.classDAO = classDAO;

        loadComboBoxData();

        loadTable();
        initTableListener();

        initAction(mainFrame);
    }

    private void loadTable() {
        List<ClassInfo> classList = classDAO.findAllClasses();

        view.model.setRowCount(0);
        for (ClassInfo c : classList) {
            view.model.addRow(new Object[]{
                    c.getClassId(),
                    c.getClassName(),
                    c.getCohort().getCohortName(),
                    c.getMajor().getMajorName(),
                    c.getFaculty().getFacuName(),
                    c.getTeacher().getTeacherName(),
                    c.getStudentCurrent(),
                    c.getStudentMax()
            });

        }
    }

    private void loadComboBoxData() {
        // Faculty
        FacuDAO facuDAO = new FacuDAO();
        List<Faculties> faculties = facuDAO.findAll();

        view.cboFacu.removeAllItems();
        view.cboFacuSearch.removeAllItems();

        view.cboFacu.addItem(new Faculties("", "-- Chọn Khoa/Viện --"));
        view.cboFacuSearch.addItem(new Faculties("", "-- Chọn Khoa/Viện --"));

        view.cboMajor.addItem(new Major("", "-- Chọn Chuyên ngành --", null));
        view.cboMajorSearch.addItem(new Major("", "-- Chọn Chuyên ngành --", null));

        view.cboTeacher.addItem(new Teacher("", "-- Chọn GV --"));
        view.cboTeacherSearch.addItem(new Teacher("", "-- Chọn GV --"));

        for (Faculties f : faculties) {
            view.cboFacu.addItem(f);
            view.cboFacuSearch.addItem(f);
        }

        // Khi chọn 1 khoa, load Major + Teacher theo khoa
        view.cboFacu.addActionListener(e -> {
            Faculties selectedFacu = (Faculties) view.cboFacu.getSelectedItem();
            if (selectedFacu != null) {
                loadMajorByFaculty(selectedFacu.getFacuId());
                loadTeacherByFaculty(selectedFacu.getFacuId());
            }
        });

        //Cũng như trên nhưng dùng cho search
        view.cboFacuSearch.addActionListener(e -> {
            Faculties selectedFacuSearch = (Faculties) view.cboFacuSearch.getSelectedItem();
            if (selectedFacuSearch != null) {
                loadMajorByFacultySearch(selectedFacuSearch.getFacuId());
                loadTeacherByFacultySearch(selectedFacuSearch.getFacuId());
            }
        });

        // Load Cohort (không liên quan Khoa)
        CohortDAO cohortDAO = new CohortDAO();
        List<Cohort> cohorts = cohortDAO.getAllCohorts();
        view.cboCohort.removeAllItems();
        view.cboCohortSearch.removeAllItems();
        view.cboCohort.addItem(new Cohort(0, "--Chọn khóa đào tạo--"));
        view.cboCohortSearch.addItem(new Cohort(0, "--Chọn khóa đào tạo--"));
        for (Cohort c : cohorts) {
            view.cboCohort.addItem(c);
            view.cboCohortSearch.addItem(c);
        }
        ;

        view.cboFacu.addActionListener(e -> {
            if (isAddingNew) updateClassId_ClassName();
        });
        view.cboMajor.addActionListener(e -> {
            if (isAddingNew) updateClassId_ClassName();
        });
        view.cboCohort.addActionListener(e -> {
            if (isAddingNew) updateClassId_ClassName();
        });
    }

    private boolean isAddingNew = false;

    private void updateClassId_ClassName() {
        String classId = generateClassId();
        String className = generateClassName();
        view.txtClassId.setText(classId);
        view.txtClassName.setText(className);
        view.txtClassId.setEditable(false);
        view.txtClassName.setEditable(false);
    }

    private void loadTeacherByFaculty(String facuId) {
        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teachers = teacherDAO.findAllByFaculty(facuId);
        view.cboTeacher.removeAllItems();
        view.cboTeacher.addItem(new Teacher("", "--Chọn GVCN/CVHT--"));
        for (Teacher t : teachers) view.cboTeacher.addItem(t);
    }

    private void loadMajorByFaculty(String facuId) {
        MajorDAO majorDAO = new MajorDAO();
        List<Major> majors = majorDAO.findAllMajorsByFaculty(facuId); // tao sẽ viết DAO
        view.cboMajor.removeAllItems();
        view.cboMajor.addItem(new Major("", "--Chọn chuyên ngành--"));
        for (Major m : majors) view.cboMajor.addItem(m);
    }

    private void loadTeacherByFacultySearch(String facuId) {
        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teachers = teacherDAO.findAllByFaculty(facuId);
        view.cboTeacherSearch.removeAllItems();
        view.cboTeacherSearch.addItem(new Teacher("", "--Chọn GVCN/CVHT--"));
        for (Teacher t : teachers) view.cboTeacherSearch.addItem(t);
    }

    private void loadMajorByFacultySearch(String facuId) {
        MajorDAO majorDAO = new MajorDAO();
        List<Major> majors = majorDAO.findAllMajorsByFaculty(facuId);
        view.cboMajorSearch.removeAllItems();
        view.cboMajorSearch.addItem(new Major("", "--Chọn chuyên ngành--"));
        for (Major m : majors) view.cboMajorSearch.addItem(m);
    }

    private void initAction(MainFrame mainFrame) {
        view.btnAdd.addActionListener(e -> onInsert());

        view.btnUpdate.addActionListener(e -> onUpdate());

        view.btnDelete.addActionListener(e -> onDelete());

        view.btnSearch.addActionListener(e -> onSearch());

        view.btnBack.addActionListener(e -> {
            mainFrame.showView("HOME");
        });
    }

    private void initTableListener() {
        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onRowSelected();
            }
        });
    }

    private String generateClassId() {
        Faculties facu = (Faculties) view.cboFacu.getSelectedItem();
        Major major = (Major) view.cboMajor.getSelectedItem();
        Cohort cohort = (Cohort) view.cboCohort.getSelectedItem();

        if (facu == null || major == null || cohort == null) return "";
        // tránh placeholder
        if (facu.getFacuId().isEmpty() || major.getMajorId().isEmpty() || cohort.getCohortId() == 0)
            return "";

        String majorShort = major.getMajorId().replaceAll("\\s+", "");
        majorShort = majorShort.length() >= 2 ? majorShort.substring(0, 2).toUpperCase() : majorShort.toUpperCase();

        String cohortStr = String.valueOf(cohort.getCohortName());
        cohortStr = cohortStr.length() > 2 ? cohortStr.substring(cohortStr.length() - 2) : cohortStr;

        // đếm số lớp đã có cùng Khoa-Major-Cohort
        int count = classDAO.countClasses(facu.getFacuId(), major.getMajorId(), cohort.getCohortId());
        String seq = String.format("%02d", count + 1); // tăng 1, 01, 02,...

        return cohortStr + majorShort + seq;
    }

    private String generateClassName() {
        Faculties facu = (Faculties) view.cboFacu.getSelectedItem();
        Major major = (Major) view.cboMajor.getSelectedItem();
        Cohort cohort = (Cohort) view.cboCohort.getSelectedItem();

        if (facu == null || major == null || cohort == null) return "";
        // tránh placeholder
        if (facu.getFacuId().isEmpty() || major.getMajorId().isEmpty() || cohort.getCohortId() == 0)
            return "";

        String majorName = major.getMajorName();

        String cohortStr = String.valueOf(cohort.getCohortName());

        // đếm số lớp đã có cùng Khoa-Major-Cohort
        int count = classDAO.countClasses(facu.getFacuId(), major.getMajorId(), cohort.getCohortId());
        String seq = String.format("%02d", count + 1); // tăng 1, 01, 02,...

        return majorName + " " + seq + " - " + cohortStr;
    }

    public void onInsert() {
        isAddingNew = true;
        String classId = generateClassId();
        String className = generateClassName();

        Faculties facu = (Faculties) view.cboFacu.getSelectedItem();
        String facuId = facu != null ? facu.getFacuId() : "";

        Major major = (Major) view.cboMajor.getSelectedItem();
        String majorId = major != null ? major.getMajorId() : "";

        Cohort cohort = (Cohort) view.cboCohort.getSelectedItem();
        String cohortId = cohort != null ? String.valueOf(cohort.getCohortId()) : "";

        Teacher teacher = (Teacher) view.cboTeacher.getSelectedItem();
        String teacherId = teacher != null ? teacher.getTeacherId() : "";

        int studentMax = 0;
        try {
            studentMax = Integer.parseInt(view.txtStudentMax.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Số SV tối đa phải là số nguyên!");
        }

        if (facuId.isEmpty() || majorId.isEmpty() || cohortId.isEmpty() || studentMax <= 0 || teacherId.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng điền đủ thông tin");
            return;
        }

        ClassInfo classInfo = new ClassInfo(classId, className, facu, major, teacher, cohort, 0, studentMax);
        if (JOptionPane.showConfirmDialog(view, "Xác nhận tạo lớp\nMã lớp: " + generateClassId() + "\nTên lớp: " + generateClassName()) == JOptionPane.YES_OPTION) {
            try {
                int result = classDAO.insert(classInfo);
                if (result > 0) {
                    JOptionPane.showMessageDialog(view, "Thêm lớp thành công");
                    loadTable();
                    clearInput();
                } else {
                    JOptionPane.showMessageDialog(view, "Thêm lớp thất bại");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, e.getMessage());
                return;
            }
        }
        isAddingNew = false;
    }

    public void onDelete() {
        int row = view.table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn lớp để xóa");
            return;
        }

        String classId = view.model.getValueAt(row, 0).toString();
        if (JOptionPane.showConfirmDialog(view, "Xác nhận xóa lớp " + classId + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int result = classDAO.delete(classId);
            if (result > 0) {
                JOptionPane.showMessageDialog(view, "Xóa lớp " + classId + " thành công");
                loadTable();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa lớp " + classId + " thất bại");
            }
        }
    }

    public void onUpdate() {
        int row = view.table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn lớp để cập nhật thông tin");
            return;
        }

        String classId = view.model.getValueAt(row, 0).toString();
        String className = view.model.getValueAt(row, 1).toString();

        Faculties facu = (Faculties) view.cboFacu.getSelectedItem();
        String facuId = facu != null ? facu.getFacuId() : "";

        Major major = (Major) view.cboMajor.getSelectedItem();
        String majorId = major != null ? major.getMajorId() : "";

        Cohort cohort = (Cohort) view.cboCohort.getSelectedItem();
        String cohortId = cohort != null ? String.valueOf(cohort.getCohortId()) : "";

        Teacher teacher = (Teacher) view.cboTeacher.getSelectedItem();
        String teacherId = teacher != null ? teacher.getTeacherId() : "";

        int studentMax = 0;
        try {
            studentMax = Integer.parseInt(view.txtStudentMax.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Số SV tối đa phải là số nguyên!");
        }

        if (facuId.isEmpty() || majorId.isEmpty() || cohortId.isEmpty() || studentMax <= 0 || teacherId.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng điền đủ thông tin");
            return;
        }

        ClassInfo classInfo = new ClassInfo(classId, className, facu, major, teacher, cohort, 0, studentMax);
        if (JOptionPane.showConfirmDialog(view, "Xác nhận cập nhật thông tin lớp " + classId + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int result = classDAO.update(classInfo);
            if (result > 0) {
                JOptionPane.showMessageDialog(view, "Cập nhật thông tin lớp " + classId + " thành công");
                loadTable();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thông tin lớp " + classId + " thất bại");
            }
        }
    }

    public void onSearch() {
        String classId = view.txtClassIdSearch.getText().trim();
        String className = view.txtClassNameSearch.getText().trim();
        Faculties facu = (Faculties) view.cboFacuSearch.getSelectedItem();
        Major major = (Major) view.cboMajorSearch.getSelectedItem();
        Cohort cohort = (Cohort) view.cboCohortSearch.getSelectedItem();
        Teacher teacher = (Teacher) view.cboTeacherSearch.getSelectedItem();

        if (classId.isEmpty() && className.isEmpty() && facu == null && teacher == null && major == null && cohort == null) {
            loadTable();
            clearInput();
            return;
        }

        try {
            List<ClassInfo> result = classDAO.search(classId, className, facu, major, cohort, teacher);

                view.model.setRowCount(0);
                for (ClassInfo c : result) {
                    view.model.addRow(new Object[]{
                            c.getClassId(),
                            c.getClassName(),
                            c.getCohort().getCohortName(),
                            c.getMajor().getMajorName(),
                            c.getFaculty().getFacuName(),
                            c.getTeacher().getTeacherName(),
                            c.getStudentCurrent(),
                            c.getStudentMax()
                    });
                }

            if (result.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Không tìm thấy kết quả");

            }

            view.table.clearSelection();
            clearSearchInput();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }


    private void onRowSelected() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtClassId.setText(view.model.getValueAt(row, 0).toString());   // classId thực
        view.txtClassName.setText(view.model.getValueAt(row, 1).toString()); // className thực

        String cohortName = view.model.getValueAt(row, 2).toString();
        String majorName = view.model.getValueAt(row, 3).toString();
        String facuName = view.model.getValueAt(row, 4).toString();
        String teacherName = view.model.getValueAt(row, 5).toString();
        String studentCurrent = view.model.getValueAt(row, 6).toString();

        view.txtStudentCurrent.setText(view.model.getValueAt(row, 6).toString());
        view.txtStudentMax.setText(view.model.getValueAt(row, 7).toString());

        view.txtClassName.setEditable(false);
        view.txtClassId.setEditable(false);
        view.txtStudentCurrent.setText(studentCurrent);

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

        for (int i = 0; i < view.cboCohort.getItemCount(); i++) {
            Cohort cohort = view.cboCohort.getItemAt(i);
            if (cohortName.equals(cohort.getCohortName())) {
                view.cboCohort.setSelectedIndex(i);
                break;
            }

        }

        for (int i = 0; i < view.cboTeacher.getItemCount(); i++) {
            Teacher teacher = view.cboTeacher.getItemAt(i);
            if (teacherName.equals(teacher.getTeacherName())) {
                view.cboTeacher.setSelectedIndex(i);
                break;
            }
        }
    }

    private void clearInput() {
        view.txtClassName.setText("");
        view.txtClassId.setText("");
        view.txtStudentMax.setText("");
        view.txtStudentCurrent.setText("");
        view.cboFacu.setSelectedIndex(0);
    }

    private void clearSearchInput() {
        view.txtClassNameSearch.setText("");
        view.txtClassIdSearch.setText("");
        view.cboFacuSearch.setSelectedIndex(0);
        view.cboMajorSearch.setSelectedIndex(0);
        view.cboTeacherSearch.setSelectedIndex(0);
        view.cboCohortSearch.setSelectedIndex(0);
    }
}