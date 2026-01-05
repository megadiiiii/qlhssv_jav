package org.example.Controller;

import org.example.DAO.ClassDAO;
import org.example.DAO.FacuDAO;
import org.example.DAO.MajorDAO;
import org.example.Model.ClassInfo;
import org.example.Model.Faculties;
import org.example.Model.Major;
import org.example.View.ClassView;

import javax.swing.*;

public class ClassController {

    private ClassView classView;
    private ClassDAO classDAO = new ClassDAO();
    private FacuDAO facuDAO = new FacuDAO();
    private MajorDAO majorDAO = new MajorDAO();

    public ClassController(ClassView classView) {
        this.classView = classView;


        for (Faculties f : facuDAO.findAll()) {
            classView.cboFacu.addItem(f);
        }


        loadTable();


        classView.cboFacu.addActionListener(e -> loadMajor());

        classView.btnAdd.addActionListener(e -> save(false));
        classView.btnUpdate.addActionListener(e -> save(true));
        classView.btnDelete.addActionListener(e -> delete());
        classView.btnSearch.addActionListener(e -> search());

        classView.tblClass.getSelectionModel()
                .addListSelectionListener(e -> tableClick());
    }

    private void tableClick() {
        int row = classView.tblClass.getSelectedRow();
        if (row == -1) return;

        String classId = classView.tableModel.getValueAt(row, 0).toString();
        String className = classView.tableModel.getValueAt(row, 1).toString();
        String facuName = classView.tableModel.getValueAt(row, 2).toString();
        String majorName = classView.tableModel.getValueAt(row, 3).toString();
        String cohort = classView.tableModel.getValueAt(row, 4).toString();

        classView.txtClassId.setText(classId);
        classView.txtClassName.setText(className);
        classView.txtClassId.setEditable(false);
        classView.txtCohort.setText(cohort);


        for (int i = 0; i < classView.cboFacu.getItemCount(); i++) {
            Faculties f = (Faculties) classView.cboFacu.getItemAt(i);
            if (f.getFacuName().equals(facuName)) {
                classView.cboFacu.setSelectedIndex(i);
                break;
            }
        }


        loadMajor();
        for (int i = 0; i < classView.cboMajor.getItemCount(); i++) {
            Major m = (Major) classView.cboMajor.getItemAt(i);
            if (m.getMajorName().equals(majorName)) {
                classView.cboMajor.setSelectedIndex(i);
                break;
            }
        }
    }

    private void save(boolean update) {
        Faculties f = (Faculties) classView.cboFacu.getSelectedItem();
        Major m = (Major) classView.cboMajor.getSelectedItem();

        if (f == null || m == null) {
            JOptionPane.showMessageDialog(null, "Chọn khoa và ngành");
            return;
        }

        if (classView.txtClassId.getText().trim().isEmpty()
                || classView.txtClassName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không được để trống");
            return;
        }

        ClassInfo c = new ClassInfo();
        c.setClassId(classView.txtClassId.getText().trim());
        c.setClassName(classView.txtClassName.getText().trim());
        c.setFacuId(f.getFacuId());
        c.setMajorId(m.getMajorId());
        int cohort = Integer.parseInt(classView.txtCohort.getText().trim());
        c.setCohort(cohort);
        c.setFacuName(null);
        c.setMajorName(null);


        boolean ok;
        if (update) {
            ok = classDAO.update(c);
        } else {
            ok = classDAO.insert(c);
        }

        if (!ok) {
            JOptionPane.showMessageDialog(null, "Thao tác thất bại");
            return;
        }

        loadTable();
        clearForm();
    }

    private void delete() {
        int row = classView.tblClass.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Chọn lớp cần xóa");
            return;
        }

        classDAO.delete(classView.txtClassId.getText());
        loadTable();
        clearForm();
    }

    private void search() {
        classView.tableModel.setRowCount(0);
        for (ClassInfo c : classDAO.search(
                classView.txtSearchId.getText(),
                classView.txtSearchName.getText()
        )) {
            classView.tableModel.addRow(new Object[]{
                    c.getClassId(),
                    c.getClassName(),
                    c.getFacuName(),
                    c.getMajorName(),
                    c.getCohort()
            });
        }
    }

    private void loadMajor() {
        classView.cboMajor.removeAllItems();
        Faculties f = (Faculties) classView.cboFacu.getSelectedItem();
        if (f != null) {
            for (Major m : majorDAO.getByFacu(f.getFacuId())) {
                classView.cboMajor.addItem(m);
            }
        }
    }

    private void loadTable() {
        classView.tableModel.setRowCount(0);
        for (ClassInfo c : classDAO.getAllClass()) {
            classView.tableModel.addRow(new Object[]{
                    c.getClassId(),
                    c.getClassName(),
                    c.getFacuName(),
                    c.getMajorName(),
                    c.getCohort()
            });
        }
    }

    private void clearForm() {
        classView.txtClassId.setText("");
        classView.txtClassName.setText("");
        classView.txtClassId.setEditable(true);
        classView.tblClass.clearSelection();
        classView.txtCohort.setText("");
    }
}
