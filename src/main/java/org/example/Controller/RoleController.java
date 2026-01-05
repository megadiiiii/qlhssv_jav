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
        initStudentNameAutoFill();

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cellclick();
        });

        loadTable();
    }

    // load table
    private void loadTable() {
        view.model.setRowCount(0);
        List<Role> list = dao.findAll();

        for (Role r : list) {
            view.model.addRow(new Object[]{
                    r.getRoleId(),
                    r.getStudentId(),
                    r.getStudentName(),
                    r.getStudentRole()
            });
        }
    }

    // load table search
    private void loadTableSearch() {
        view.model.setRowCount(0);

        Integer roleId = parseIntOrNull(view.txtRoleIdSearch.getText());
        String studentId = emptyToNull(view.txtMaSinhVienSearch.getText());

        String vaiTro = getVaiTroFromSearchCombo();
        vaiTro = (vaiTro == null || vaiTro.trim().isEmpty()) ? null : vaiTro;

        List<Role> list = dao.search(roleId, studentId, vaiTro);

        for (Role r : list) {
            view.model.addRow(new Object[]{
                    r.getRoleId(),
                    r.getStudentId(),
                    r.getStudentName(),
                    r.getStudentRole()
            });
        }
    }

    // auto fill tên theo msv
    private void initStudentNameAutoFill() {
        view.txtMaSinhVien.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { fillStudentName(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { fillStudentName(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { fillStudentName(); }
        });
    }

    private void fillStudentName() {
        if (!view.txtMaSinhVien.isEditable()) return;

        String msv = getStudentIdFromField();
        if (msv.isEmpty()) {
            view.txtTenSinhVien.setText("");
            return;
        }
        view.txtTenSinhVien.setText(dao.findStudentNameById(msv));
    }

    // action
    private void initActions(MainFrame mainFrame) {

        // them
        view.btnThem.addActionListener(e -> {
            try {
                Role role = new Role(
                        0,
                        getStudentIdFromField(),
                        null, // name auto fill / join, không cần set khi insert
                        getVaiTroFromCombo()
                );

                if (role.getStudentId() == null || role.getStudentId().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập mã sinh viên");
                    return;
                }

                dao.insert(role);
                loadTable();
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
                int affected = dao.delete(new Role(id, null, null, null));

                if (affected > 0) {
                    loadTable();
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
                Role role = new Role(
                        id,
                        getStudentIdFromField(),
                        null,
                        getVaiTroFromCombo()
                );

                int affected = dao.update(role);
                if (affected > 0) {
                    loadTable();
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

        // rs
        view.btnMoi.addActionListener(e -> {
            clearForm();
            clearSearch();
            loadTable();
        });

        // back
        view.btnQuayLai.addActionListener(e -> {
            if (mainFrame != null) mainFrame.showView("HOME");
        });
    }

    // cellclick
    private void cellclick() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtRoleId.setText(view.model.getValueAt(row, 0).toString());
        view.txtMaSinhVien.setText(view.model.getValueAt(row, 1).toString());
        view.txtTenSinhVien.setText(view.model.getValueAt(row, 2).toString());
        view.cboVaiTro.setSelectedItem(view.model.getValueAt(row, 3));

        // khoa dl khi cell
        view.txtRoleId.setEditable(false);
        view.txtMaSinhVien.setEditable(false);
        view.txtTenSinhVien.setEditable(false);
    }

    private void clearForm() {
        view.txtRoleId.setText("");
        view.txtMaSinhVien.setText("");
        view.txtTenSinhVien.setText("");

        view.txtMaSinhVien.setEditable(true); // mở lại để nhập
        view.cboVaiTro.setSelectedIndex(0);

        view.table.clearSelection();
    }

    private void clearSearch() {
        view.txtRoleIdSearch.setText("");
        view.txtMaSinhVienSearch.setText("");
        view.cboVaiTroSearch.setSelectedIndex(0);
    }

    // utils
    private String getStudentIdFromField() {
        String s = view.txtMaSinhVien.getText();
        return s == null ? "" : s.trim();
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
