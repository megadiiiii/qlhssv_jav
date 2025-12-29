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

    private ClassView v;
    private ClassDAO classDAO = new ClassDAO();
    private FacuDAO facuDAO = new FacuDAO();
    private MajorDAO majorDAO = new MajorDAO();

    public ClassController(ClassView v) {
        this.v = v;


        for (Faculties f : facuDAO.findAll()) {
            v.cboFacu.addItem(f);
        }


        loadTable();


        v.cboFacu.addActionListener(e -> loadMajor());

        v.btnAdd.addActionListener(e -> save(false));
        v.btnUpdate.addActionListener(e -> save(true));
        v.btnDelete.addActionListener(e -> delete());
        v.btnSearch.addActionListener(e -> search());

        v.tblClass.getSelectionModel()
                .addListSelectionListener(e -> tableClick());
    }

    private void tableClick() {
        int row = v.tblClass.getSelectedRow();
        if (row == -1) return;

        String classId = v.tableModel.getValueAt(row, 0).toString();
        String className = v.tableModel.getValueAt(row, 1).toString();
        String facuName = v.tableModel.getValueAt(row, 2).toString();
        String majorName = v.tableModel.getValueAt(row, 3).toString();

        v.txtClassId.setText(classId);
        v.txtClassName.setText(className);
        v.txtClassId.setEditable(false);


        for (int i = 0; i < v.cboFacu.getItemCount(); i++) {
            Faculties f = (Faculties) v.cboFacu.getItemAt(i);
            if (f.getFacuName().equals(facuName)) {
                v.cboFacu.setSelectedIndex(i);
                break;
            }
        }


        loadMajor();
        for (int i = 0; i < v.cboMajor.getItemCount(); i++) {
            Major m = (Major) v.cboMajor.getItemAt(i);
            if (m.getMajorName().equals(majorName)) {
                v.cboMajor.setSelectedIndex(i);
                break;
            }
        }
    }

    private void save(boolean update) {
        Faculties f = (Faculties) v.cboFacu.getSelectedItem();
        Major m = (Major) v.cboMajor.getSelectedItem();

        if (f == null || m == null) {
            JOptionPane.showMessageDialog(null, "Chọn khoa và ngành");
            return;
        }

        if (v.txtClassId.getText().trim().isEmpty()
                || v.txtClassName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không được để trống");
            return;
        }

        ClassInfo c = new ClassInfo(
                v.txtClassId.getText().trim(),
                v.txtClassName.getText().trim(),
                f.getFacuId(),
                m.getMajorId(),
                null,
                null
        );

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
        int row = v.tblClass.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Chọn lớp cần xóa");
            return;
        }

        classDAO.delete(v.txtClassId.getText());
        loadTable();
        clearForm();
    }

    private void search() {
        v.tableModel.setRowCount(0);
        for (ClassInfo c : classDAO.search(
                v.txtSearchId.getText(),
                v.txtSearchName.getText()
        )) {
            v.tableModel.addRow(new Object[]{
                    c.getClassId(),
                    c.getClassName(),
                    c.getFacuName(),
                    c.getMajorName()
            });
        }
    }

    private void loadMajor() {
        v.cboMajor.removeAllItems();
        Faculties f = (Faculties) v.cboFacu.getSelectedItem();
        if (f != null) {
            for (Major m : majorDAO.getByFacu(f.getFacuId())) {
                v.cboMajor.addItem(m);
            }
        }
    }

    private void loadTable() {
        v.tableModel.setRowCount(0);
        for (ClassInfo c : classDAO.getAllClass()) {
            v.tableModel.addRow(new Object[]{
                    c.getClassId(),
                    c.getClassName(),
                    c.getFacuName(),
                    c.getMajorName()
            });
        }
    }

    private void clearForm() {
        v.txtClassId.setText("");
        v.txtClassName.setText("");
        v.txtClassId.setEditable(true);
        v.tblClass.clearSelection();
    }
}
