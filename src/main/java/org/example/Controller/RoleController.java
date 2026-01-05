package org.example.Controller;

import org.example.DAO.RoleDAO;
import org.example.DAO.StudentDAO;
import org.example.Model.Role;
import org.example.Model.Student;
import org.example.View.RoleView;

import javax.swing.*;
import java.util.List;

public class RoleController {

    private final RoleView view;
    private final RoleDAO roleDAO;
    private final StudentDAO studentDAO;

    public RoleController(RoleView view) {
        this.view = view;
        this.roleDAO = new RoleDAO();
        this.studentDAO = new StudentDAO();

        loadStudents();
        loadTable();
        initEvent();
    }

    private void loadStudents() {
        view.cboStudent.removeAllItems();
        for (Student s : studentDAO.getAll()) {
            view.cboStudent.addItem(s);
        }
    }

    private void loadTable() {
        view.model.setRowCount(0);
        List<Role> list = roleDAO.findAll();

        for (Role r : list) {
            Student s = r.getStudent();
            view.model.addRow(new Object[]{
                    r.getRoleId(),
                    s.getStudentId(),
                    s.getLastName() + " " + s.getFirstName(),
                    s.getClassName(),
                    r.getStudentRole()
            });
        }
    }

    private void initEvent() {

        view.btnAdd.addActionListener(e -> {
            Student s = (Student) view.cboStudent.getSelectedItem();
            String roleName = view.cboRole.getSelectedItem().toString();

            if (s == null) {
                JOptionPane.showMessageDialog(view, "Chưa chọn sinh viên");
                return;
            }

            Role r = new Role();
            r.setStudent(s);
            r.setStudentRole(roleName);

            if (roleDAO.insert(r) > 0) {
                JOptionPane.showMessageDialog(view, "Thêm thành công");
                loadTable();
            }
        });

        view.btnUpdate.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Chọn dòng cần sửa");
                return;
            }

            int roleId = Integer.parseInt(view.txtRoleId.getText());
            Student s = (Student) view.cboStudent.getSelectedItem();
            String roleName = view.cboRole.getSelectedItem().toString();

            Role r = new Role();
            r.setRoleId(roleId);
            r.setStudent(s);
            r.setStudentRole(roleName);

            if (roleDAO.update(r) > 0) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công");
                loadTable();
            }
        });

        view.btnDelete.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row == -1) return;

            int roleId = Integer.parseInt(
                    view.model.getValueAt(row, 0).toString()
            );

            if (JOptionPane.showConfirmDialog(
                    view, "Xóa vai trò này?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            ) == JOptionPane.YES_OPTION) {

                if (roleDAO.delete(roleId) > 0) {
                    JOptionPane.showMessageDialog(view, "Đã xóa");
                    loadTable();
                }
            }
        });

        view.table.getSelectionModel().addListSelectionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row == -1) return;

            view.txtRoleId.setText(view.model.getValueAt(row, 0).toString());
            view.cboRole.setSelectedItem(
                    view.model.getValueAt(row, 4).toString()
            );
        });
    }
}
