package org.example.Controller;

import org.example.DAO.TeacherDAO;
import org.example.Model.Teacher;
import org.example.View.MainFrame;
import org.example.View.TeacherView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class TeacherController {
    private TeacherView view;
    private TeacherDAO dao;

    public TeacherController(TeacherView view, MainFrame mainFrame, TeacherDAO dao) {
        this.view = view;
        this.dao = dao;

        initActions(mainFrame);
        loadFacultyCombo();
        initFacultyNameAutoFill();

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cellclick();
        });

        loadTable();
    }

    // ===== load table =====
    private void loadTable() {
        view.model.setRowCount(0);
        List<Teacher> list = dao.findAll();

        for (Teacher t : list) {
            view.model.addRow(new Object[]{
                    t.getTeacherId(),
                    t.getTeacherName(),
                    t.getFacuId(),
                    t.getFacuName()
            });
        }
    }

    // ===== load table search =====
    private void loadTableSearch() {
        view.model.setRowCount(0);

        String teacherId = emptyToNull(view.txtTeacherIdSearch.getText());
        String teacherName = emptyToNull(view.txtTeacherNameSearch.getText());

        String facuId = getFacuIdFromSearchCombo();
        facuId = facuId.isEmpty() ? null : facuId;

        List<Teacher> list = dao.search(teacherId, teacherName, facuId);

        for (Teacher t : list) {
            view.model.addRow(new Object[]{
                    t.getTeacherId(),
                    t.getTeacherName(),
                    t.getFacuId(),
                    t.getFacuName()
            });
        }
    }

    // facuid cr se
    private void loadFacultyCombo() {
        // cr cb
        view.cboFacuId.removeAllItems();
        view.cboFacuId.addItem("");

        for (String id : dao.getAllFacultyIds()) {
            view.cboFacuId.addItem(id);
        }
        view.cboFacuId.setEditable(true);

        // se combo
        view.cboFacuIdSearch.removeAllItems();
        view.cboFacuIdSearch.addItem("");

        for (String id : dao.getAllFacultyIds()) {
            view.cboFacuIdSearch.addItem(id);
        }
        view.cboFacuIdSearch.setEditable(true);
    }

    // tu dien ten khoa
    private void initFacultyNameAutoFill() {
        view.cboFacuId.addActionListener(e -> fillFacultyName());

        JTextField editor =
                (JTextField) view.cboFacuId.getEditor().getEditorComponent();

        editor.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { fillFacultyName(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { fillFacultyName(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { fillFacultyName(); }
        });
    }

    private void fillFacultyName() {
        String facuId = getFacuIdFromCrudCombo();
        if (facuId.isEmpty()) {
            view.txtFacuName.setText("");
            return;
        }
        view.txtFacuName.setText(dao.findFacultyNameById(facuId));
    }

    // action
    private void initActions(MainFrame mainFrame) {

        view.btnThem.addActionListener(e -> {
            try {
                Teacher t = new Teacher(
                        emptyToNull(view.txtTeacherId.getText()),
                        emptyToNull(view.txtTeacherName.getText()),
                        getFacuIdFromCrudCombo(),
                        null
                );

                if (t.getTeacherId() == null || t.getTeacherName() == null || t.getFacuId().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin");
                    return;
                }

                dao.insert(t);
                loadTable();
                clearForm();
                JOptionPane.showMessageDialog(view, "Thêm thành công");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Thêm thất bại");
            }
        });

        view.btnXoa.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn giảng viên cần xóa");
                return;
            }

            String teacherId = view.model.getValueAt(row, 0).toString();

            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Bạn có chắc chắn muốn xóa giảng viên này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                int affected = dao.delete(teacherId);
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

        view.btnSua.addActionListener(e -> {
            try {
                Teacher t = new Teacher(
                        emptyToNull(view.txtTeacherId.getText()),
                        emptyToNull(view.txtTeacherName.getText()),
                        getFacuIdFromCrudCombo(),
                        null
                );

                if (t.getTeacherId() == null || t.getTeacherName() == null || t.getFacuId().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin");
                    return;
                }

                int affected = dao.update(t);
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

        // timkiem
        view.btnSearch.addActionListener(e -> loadTableSearch());

        // reset
        view.btnMoi.addActionListener(e -> {
            clearForm();
            clearSearch();
            loadTable();
        });
    }

    // cell click
    private void cellclick() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtTeacherId.setText(view.model.getValueAt(row, 0).toString());
        view.txtTeacherName.setText(view.model.getValueAt(row, 1).toString());
        view.cboFacuId.setSelectedItem(view.model.getValueAt(row, 2));
        view.txtFacuName.setText(view.model.getValueAt(row, 3).toString());

        // khoa ma gv khi click
        view.txtTeacherId.setEditable(false);
    }

    private void clearForm() {
        view.txtTeacherId.setEditable(true);

        view.txtTeacherId.setText("");
        view.txtTeacherName.setText("");
        view.cboFacuId.setSelectedItem("");
        view.txtFacuName.setText("");

        view.table.clearSelection();
    }

    private void clearSearch() {
        view.txtTeacherIdSearch.setText("");
        view.txtTeacherNameSearch.setText("");
        view.cboFacuIdSearch.setSelectedItem("");
    }

    // utils
    private String getFacuIdFromCrudCombo() {
        Object o = view.cboFacuId.getEditor().getItem();
        return o == null ? "" : o.toString().trim();
    }

    private String getFacuIdFromSearchCombo() {
        Object o = view.cboFacuIdSearch.getEditor().getItem();
        return o == null ? "" : o.toString().trim();
    }

    private String emptyToNull(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }
}
