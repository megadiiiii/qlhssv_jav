package org.example.Controller;

import org.example.DAO.RoleDAO;
import org.example.Model.Role;
import org.example.View.MainFrame;
import org.example.View.RoleView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class RoleController {

    private final RoleView view;
    private final RoleDAO dao;

    public RoleController(RoleView view, MainFrame mainFrame, RoleDAO dao) {
        this.view = view;
        this.dao = dao;

        initActions(mainFrame);

        loadClassCombo();
        initClassChange();
        initStudentChange();

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cellclick();
        });

        // load ban đầu theo lớp đầu tiên
        refreshBySelectedClass();
    }

    // ===== load lớp vào combo =====
    private void loadClassCombo() {
        view.cboMaLop.removeAllItems();
        List<String> classIds = dao.getAllClassIds();
        for (String id : classIds) view.cboMaLop.addItem(id);

        if (view.cboMaLop.getItemCount() > 0) {
            view.cboMaLop.setSelectedIndex(0);
        }
    }

    // ===== khi đổi lớp -> hiện tên lớp + load sv theo lớp + load bảng =====
    private void initClassChange() {
        view.cboMaLop.addActionListener(e -> refreshBySelectedClass());
    }

    private void refreshBySelectedClass() {
        String classId = getClassIdFromCombo();
        if (classId.isEmpty()) {
            view.txtTenLop.setText("");
            view.cboMaSinhVien.removeAllItems();
            view.txtTenSinhVien.setText("");
            view.model.setRowCount(0);
            return;
        }

        view.txtTenLop.setText(dao.findClassNameById(classId));
        loadStudentComboByClass(classId);
        fillStudentName();
        loadTableByClass(classId);
    }

    private void loadStudentComboByClass(String classId) {
        view.cboMaSinhVien.removeAllItems();
        List<String> ids = dao.getStudentIdsByClass(classId);
        for (String id : ids) view.cboMaSinhVien.addItem(id);

        if (view.cboMaSinhVien.getItemCount() > 0) {
            view.cboMaSinhVien.setSelectedIndex(0);
        }
    }

    // ===== khi đổi sinh viên -> hiện tên sinh viên =====
    private void initStudentChange() {
        view.cboMaSinhVien.addActionListener(e -> fillStudentName());
    }

    private void fillStudentName() {
        if (!view.cboMaSinhVien.isEnabled()) return;

        String studentId = getStudentIdFromCombo();
        if (studentId.isEmpty()) {
            view.txtTenSinhVien.setText("");
            return;
        }
        view.txtTenSinhVien.setText(dao.findStudentNameById(studentId));
    }

    // ===== load table theo lớp =====
    private void loadTableByClass(String classId) {
        view.model.setRowCount(0);
        List<Role> list = dao.findAllByClass(classId);

        for (Role r : list) {
            view.model.addRow(new Object[]{
                    r.getRoleId(),
                    r.getClassId(),
                    r.getClassName(),
                    r.getStudentId(),
                    r.getStudentName(),
                    r.getStudentRole()
            });
        }
    }

    // ===== load table search trong lớp đang chọn =====
    private void loadTableSearch() {
        view.model.setRowCount(0);

        String classId = getClassIdFromCombo();
        Integer roleId = parseIntOrNull(view.txtRoleIdSearch.getText());
        String studentId = emptyToNull(view.txtMaSinhVienSearch.getText());

        String vaiTro = getVaiTroFromSearchCombo();
        vaiTro = (vaiTro == null || vaiTro.trim().isEmpty()) ? null : vaiTro;

        List<Role> list = dao.search(classId, roleId, studentId, vaiTro);

        for (Role r : list) {
            view.model.addRow(new Object[]{
                    r.getRoleId(),
                    r.getClassId(),
                    r.getClassName(),
                    r.getStudentId(),
                    r.getStudentName(),
                    r.getStudentRole()
            });
        }
    }

    // ===== actions (style giống DisciplineController) =====
    private void initActions(MainFrame mainFrame) {

        // them
        view.btnThem.addActionListener(e -> {
            try {
                String classId = getClassIdFromCombo();
                String studentId = getStudentIdFromCombo();

                if (classId.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn mã lớp");
                    return;
                }
                if (studentId.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn mã sinh viên");
                    return;
                }

                Role role = new Role(
                        0,
                        studentId,
                        null,
                        classId,
                        null,
                        getVaiTroFromCombo()
                );

                dao.insert(role);
                loadTableByClass(classId);
                clearForm();
                JOptionPane.showMessageDialog(view, "Thêm thành công");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Thêm thất bại");
            }
        });

        // xoa
        view.btnXoa.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần xóa");
                return;
            }

            int id = Integer.parseInt(view.model.getValueAt(row, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Bạn có chắc chắn muốn xóa role này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                int affected = dao.delete(new Role(id, null, null, null, null, null));
                if (affected > 0) {
                    loadTableByClass(getClassIdFromCombo());
                    clearForm();
                    JOptionPane.showMessageDialog(view, "Xóa thành công");
                } else {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy dữ liệu để xóa");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Xóa thất bại");
            }
        });

        // sua
        view.btnSua.addActionListener(e -> {
            if (view.txtRoleId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần sửa");
                return;
            }

            int id = Integer.parseInt(view.txtRoleId.getText());

            try {
                String classId = getClassIdFromCombo();
                String studentId = getStudentIdFromCombo();

                if (classId.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn mã lớp");
                    return;
                }
                if (studentId.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn mã sinh viên");
                    return;
                }

                Role role = new Role(
                        id,
                        studentId,
                        null,
                        classId,
                        null,
                        getVaiTroFromCombo()
                );

                int affected = dao.update(role);
                if (affected > 0) {
                    loadTableByClass(classId);
                    JOptionPane.showMessageDialog(view, "Sửa thành công");
                } else {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy dữ liệu để sửa");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Sửa thất bại");
            }
        });

        // tim
        view.btnTim.addActionListener(e -> loadTableSearch());

        // moi
        view.btnMoi.addActionListener(e -> {
            clearForm();
            clearSearch();
            refreshBySelectedClass();
        });

        // back
        view.btnQuayLai.addActionListener(e -> {
            if (mainFrame != null) mainFrame.showView("HOME");
        });
    }

    // ===== cellclick =====
    private void cellclick() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtRoleId.setText(view.model.getValueAt(row, 0).toString());

        String classId = view.model.getValueAt(row, 1).toString();
        String className = view.model.getValueAt(row, 2).toString();
        String studentId = view.model.getValueAt(row, 3).toString();
        String studentName = view.model.getValueAt(row, 4).toString();
        Object vaiTro = view.model.getValueAt(row, 5);

        view.cboMaLop.setSelectedItem(classId);
        view.txtTenLop.setText(className);

        // khi set lớp -> combo sv sẽ load lại, nên set tiếp student
        view.cboMaSinhVien.setSelectedItem(studentId);
        view.txtTenSinhVien.setText(studentName);

        view.cboVaiTro.setSelectedItem(vaiTro);

        // khóa như discipline: id + msv + tên sv (thêm cả lớp để khỏi sai)
        view.txtRoleId.setEditable(false);
        view.cboMaLop.setEnabled(false);
        view.cboMaSinhVien.setEnabled(false);
        view.txtTenSinhVien.setEditable(false);
    }

    private void clearForm() {
        view.txtRoleId.setText("");

        view.cboMaLop.setEnabled(true);
        view.cboMaSinhVien.setEnabled(true);

        // giữ lớp đang chọn, chỉ reset sv/vai trò
        if (view.cboMaSinhVien.getItemCount() > 0) view.cboMaSinhVien.setSelectedIndex(0);
        fillStudentName();

        view.cboVaiTro.setSelectedIndex(0);

        view.table.clearSelection();
    }

    private void clearSearch() {
        view.txtRoleIdSearch.setText("");
        view.txtMaSinhVienSearch.setText("");
        view.cboVaiTroSearch.setSelectedIndex(0);
    }

    // utils
    private String getClassIdFromCombo() {
        Object o = view.cboMaLop.getSelectedItem();
        return o == null ? "" : o.toString().trim();
    }

    private String getStudentIdFromCombo() {
        Object o = view.cboMaSinhVien.getSelectedItem();
        return o == null ? "" : o.toString().trim();
    }

    private String getVaiTroFromCombo() {
        Object o = view.cboVaiTro.getSelectedItem();
        return o == null ? "" : o.toString();
    }

    private String getVaiTroFromSearchCombo() {
        Object o = view.cboVaiTroSearch.getSelectedItem();
        return o == null ? "" : o.toString().trim();
    }

    private String emptyToNull(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }

    private Integer parseIntOrNull(String s) {
        try {
            String t = (s == null) ? "" : s.trim();
            if (t.isEmpty()) return null;
            return Integer.parseInt(t);
        } catch (Exception e) {
            return null;
        }
    }
}
