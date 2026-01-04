package org.example.Controller;


import org.example.DAO.ScholarshipDAO;
import org.example.Model.Scholarship;
import org.example.View.MainFrame;
import org.example.View.ScholarshipView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;
public class ScholarshipController {
    private ScholarshipView view;
    private ScholarshipDAO dao;

    public ScholarshipController(ScholarshipView view, MainFrame mainFrame, ScholarshipDAO dao) {
        this.view = view;
        this.dao = dao;

        initActions(mainFrame);
        loadStudentCombo();

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cellClick();
        });

        loadTable();
    }

    // ===== load table =====
    private void loadTable() {
        view.model.setRowCount(0);
        List<Scholarship> list = dao.findAll();

        for (Scholarship s : list) {
            view.model.addRow(new Object[]{
                    s.getScholarshipId(),
                    s.getStudentId(),
                    s.getStudentName(),
                    s.getScoreLevel(),
                    s.getDrlLevel(),
                    s.getScholarshipLevel(),
                    s.getSemester()
            });
        }
    }

    // ===== load table search =====
    private void loadTableSearch() {
        view.model.setRowCount(0);

        String studentId = getStudentIdFromSearchCombo();
        studentId = studentId.isEmpty() ? null : studentId;

        String semester = emptyToNull(view.txtSemester.getText());
        String scholarshipLevel = emptyToNull(view.txtScholarshipLevel.getText());

        List<Scholarship> list = dao.search(studentId, semester, scholarshipLevel);

        for (Scholarship s : list) {
            view.model.addRow(new Object[]{
                    s.getScholarshipId(),
                    s.getStudentId(),
                    s.getStudentName(),
                    s.getScoreLevel(),
                    s.getDrlLevel(),
                    s.getScholarshipLevel(),
                    s.getSemester()
            });
        }
    }

    // ===== load student_id vào combobox =====
    private void loadStudentCombo() {
        view.cboStudentId.removeAllItems();
        view.cboStudentId.addItem("");

        for (String id : dao.getAllStudentIds()) {
            view.cboStudentId.addItem(id);
        }
        view.cboStudentId.setEditable(true);
    }

    // ===== action =====
    private void initActions(MainFrame mainFrame) {

        // THEM
        view.btnAdd.addActionListener(e -> {
            try {
                Scholarship s = new Scholarship();
                s.setStudentId(getStudentIdFromCrudCombo());
                s.setScoreLevel(emptyToNull(view.txtScoreLevel.getText()));
                s.setDrlLevel(emptyToNull(view.txtDrlLevel.getText()));
                s.setScholarshipLevel(emptyToNull(view.txtScholarshipLevel.getText()));
                s.setSemester(emptyToNull(view.txtSemester.getText()));

                if (s.getStudentId().isEmpty()
                        || s.getScholarshipLevel() == null
                        || s.getSemester() == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin");
                    return;
                }

                dao.insert(s);
                loadTable();
                clearForm();
                JOptionPane.showMessageDialog(view, "Thêm thành công");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Thêm thất bại");
            }
        });

        // XOA
        view.btnDelete.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn học bổng cần xóa");
                return;
            }

            String scholarshipId = view.model.getValueAt(row, 0).toString();

            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Bạn có chắc chắn muốn xóa học bổng này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                int affected = dao.delete(scholarshipId);
                if (affected > 0) {
                    loadTable();
                    clearForm();
                    JOptionPane.showMessageDialog(view, "Xóa thành công");
                } else {
                    JOptionPane.showMessageDialog(view, "Không có dữ liệu nào bị xóa");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Xóa thất bại");
            }
        });

        // SUA
        view.btnEdit.addActionListener(e -> {
            try {
                Scholarship s = new Scholarship();
                s.setScholarshipId(view.txtScholarshipId.getText());
                s.setStudentId(getStudentIdFromCrudCombo());
                s.setScoreLevel(emptyToNull(view.txtScoreLevel.getText()));
                s.setDrlLevel(emptyToNull(view.txtDrlLevel.getText()));
                s.setScholarshipLevel(emptyToNull(view.txtScholarshipLevel.getText()));
                s.setSemester(emptyToNull(view.txtSemester.getText()));

                if (s.getScholarshipId() == null
                        || s.getStudentId().isEmpty()
                        || s.getScholarshipLevel() == null
                        || s.getSemester() == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin");
                    return;
                }

                int affected = dao.update(s);
                if (affected > 0) {
                    loadTable();
                    JOptionPane.showMessageDialog(view, "Sửa thành công");
                } else {
                    JOptionPane.showMessageDialog(view, "Không có dữ liệu nào được sửa");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Sửa thất bại");
            }
        });

        // TIM KIEM
        view.btnSearch.addActionListener(e -> loadTableSearch());

        // RESET
        view.btnBack.addActionListener(e -> {
            clearForm();
            loadTable();
        });
    }

    // ===== cell click =====
    private void cellClick() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtScholarshipId.setText(view.model.getValueAt(row, 0).toString());
        view.cboStudentId.setSelectedItem(view.model.getValueAt(row, 1));
        view.txtScoreLevel.setText(view.model.getValueAt(row, 3).toString());
        view.txtDrlLevel.setText(view.model.getValueAt(row, 4).toString());
        view.txtScholarshipLevel.setText(view.model.getValueAt(row, 5).toString());
        view.txtSemester.setText(view.model.getValueAt(row, 6).toString());

        view.txtScholarshipId.setEditable(false);
    }

    // ===== clear =====
    private void clearForm() {
        view.txtScholarshipId.setEditable(true);

        view.txtScholarshipId.setText("");
        view.cboStudentId.setSelectedItem("");
        view.txtScoreLevel.setText("");
        view.txtDrlLevel.setText("");
        view.txtScholarshipLevel.setText("");
        view.txtSemester.setText("");

        view.table.clearSelection();
    }

    // ===== utils =====
    private String getStudentIdFromCrudCombo() {
        Object o = view.cboStudentId.getEditor().getItem();
        return o == null ? "" : o.toString().trim();
    }

    private String getStudentIdFromSearchCombo() {
        Object o = view.cboStudentId.getEditor().getItem();
        return o == null ? "" : o.toString().trim();
    }

    private String emptyToNull(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }
}
