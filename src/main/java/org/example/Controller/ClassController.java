package org.example.Controller;

import org.example.DAO.*;
import org.example.Model.*;
import org.example.View.ClassView;
import org.example.View.MainFrame;

import java.util.List;

public class ClassController {
    private ClassView view;
    private ClassDAO classDAO;

    public ClassController(ClassView classView, MainFrame mainFrame, ClassDAO classDAO) {
        this.view = classView;
        this.classDAO = classDAO;

        loadComboBoxData();

        loadTable();

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
        view.cboFacu.addItem(new Faculties("", "-- Chọn Khoa/Viện --"));
        view.cboMajor.addItem(new Major("", "-- Chọn Chuyên ngành --", null));
        view.cboTeacher.addItem(new Teacher("", "-- Chọn GV --"));
        for (Faculties f : faculties) view.cboFacu.addItem(f);

        // Khi chọn 1 khoa, load Major + Teacher theo khoa
        view.cboFacu.addActionListener(e -> {
            Faculties selectedFacu = (Faculties) view.cboFacu.getSelectedItem();
            if (selectedFacu != null) {
                loadMajorByFaculty(selectedFacu.getFacuId());
                loadTeacherByFaculty(selectedFacu.getFacuId());
            }
        });

        // Load Cohort (không liên quan Khoa)
        CohortDAO cohortDAO = new CohortDAO();
        List<Cohort> cohorts = cohortDAO.getAllCohorts();
        view.cboCohort.removeAllItems();
        view.cboCohort.addItem(new Cohort(0, "--Chọn khóa đào tạo--"));
        for (Cohort c : cohorts) view.cboCohort.addItem(c);
    }

    private void loadTeacherByFaculty(String facuId) {
        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teachers = teacherDAO.findAllByFaculty(facuId);
        view.cboTeacher.removeAllItems();
        for (Teacher t : teachers) view.cboTeacher.addItem(t);
    }


    private void loadMajorByFaculty(String facuId) {
        MajorDAO majorDAO = new MajorDAO();
        List<Major> majors = majorDAO.findAllMajorsByFaculty(facuId); // tao sẽ viết DAO
        view.cboMajor.removeAllItems();
        for (Major m : majors) view.cboMajor.addItem(m);
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


    public void onInsert() {
    }

    public void onDelete() {
    }

    public void onUpdate() {
    }

    public void onSearch() {
    }

}
