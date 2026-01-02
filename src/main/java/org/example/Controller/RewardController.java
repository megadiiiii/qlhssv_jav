package org.example.Controller;

import org.example.DAO.RewardDAO;
import org.example.Model.Reward;
import org.example.View.MainFrame;
import org.example.View.RewardView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;

public class RewardController {
    private final RewardView view;
    private final RewardDAO dao;
    public RewardController(RewardView view, MainFrame mainFrame, RewardDAO dao) {
        this.view = view;
        this.dao = dao;

        loadStudentCombo();
        loadTable();
        addEvents();
        clearForm();
    }

    private void loadStudentCombo() {
        view.cboStudentId.removeAllItems();
        for (String id : dao.getStudentIds()) {
            view.cboStudentId.addItem(id);
        }
        view.cboStudentId.setEditable(true);
    }

    private void loadTable() {
        view.model.setRowCount(0);
        for (Reward r : dao.findAll()) {
            view.model.addRow(new Object[]{
                    r.getRewardId(),
                    r.getStudentId(),
                    r.getStudentName(),
                    r.getRewardDate(),
                    r.getRewardNote(),
                    r.getRewardQuyetDinh()
            });
        }
    }

    private void addEvents() {
        view.btnAddReward.addActionListener(e -> addReward());
        view.btnEditReward.addActionListener(e -> editReward());
        view.btnDeleteReward.addActionListener(e -> deleteReward());

        view.table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) return;
            fillForm();
        });

        view.cboStudentId.addActionListener(e -> fillStudentName());
    }

    private void fillStudentName() {
        if (!view.cboStudentId.isEnabled()) return;

        Object val = view.cboStudentId.getEditor().getItem();
        if (val == null) return;

        String studentId = val.toString().trim();
        if (studentId.isEmpty()) {
            view.txtStudentName.setText("");
            return;
        }

        String name = dao.getStudentNameById(studentId);
        view.txtStudentName.setText(name);
    }

    private void addReward() {
        Reward r = readForm(false);
        if (r == null) return;

        try {
            dao.insert(r);
            loadTable();
            clearForm();
            JOptionPane.showMessageDialog(view, "Thêm khen thưởng thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi thêm dữ liệu!");
        }
    }

    private void editReward() {
        Reward r = readForm(true);
        if (r == null) return;

        try {
            if (dao.update(r) > 0) {
                loadTable();
                clearForm();
                JOptionPane.showMessageDialog(view, "Sửa thành công!");
            } else {
                JOptionPane.showMessageDialog(view, "Không có dòng nào được sửa!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi sửa dữ liệu!");
        }
    }

    private void deleteReward() {
        String idText = view.txtRewardId.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn dòng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Xóa khen thưởng này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Reward r = new Reward();
            r.setRewardId(Integer.parseInt(idText));

            if (dao.delete(r) > 0) {
                loadTable();
                clearForm();
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
            } else {
                JOptionPane.showMessageDialog(view, "Không có dòng nào bị xóa!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi xóa dữ liệu!");
        }
    }


    private void fillForm() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtRewardId.setText(Objects.toString(view.model.getValueAt(row, 0), ""));
        view.cboStudentId.setSelectedItem(Objects.toString(view.model.getValueAt(row, 1), ""));
        view.txtStudentName.setText(Objects.toString(view.model.getValueAt(row, 2), ""));

        Object dateObj = view.model.getValueAt(row, 3);
        if (dateObj instanceof java.sql.Date) {
            view.dcRewardDate.setDate(new java.util.Date(((java.sql.Date) dateObj).getTime()));
        }

        view.txtRewardNote.setText(Objects.toString(view.model.getValueAt(row, 4), ""));
        view.txtRewardQuyetDinh.setText(Objects.toString(view.model.getValueAt(row, 5), ""));

        view.cboStudentId.setEnabled(false);
        view.txtStudentName.setEnabled(false);
    }

    private Reward readForm(boolean requireId) {
        if (requireId && view.txtRewardId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn dòng để sửa!");
            return null;
        }

        Object val = view.cboStudentId.getEditor().getItem();
        if (val == null || val.toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chưa có mã sinh viên!");
            return null;
        }

        if (view.dcRewardDate.getDate() == null) {
            JOptionPane.showMessageDialog(view, "Chọn ngày khen thưởng!");
            return null;
        }

        String qd = view.txtRewardQuyetDinh.getText().trim();
        if (qd.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Quyết định không được để trống!");
            return null;
        }

        Reward r = new Reward();
        if (requireId) {
            r.setRewardId(Integer.parseInt(view.txtRewardId.getText().trim()));
        }

        r.setStudentId(val.toString().trim());
        r.setRewardDate(new Date(view.dcRewardDate.getDate().getTime()));
        r.setRewardNote(view.txtRewardNote.getText());
        r.setRewardQuyetDinh(qd);

        return r;
    }
    private void clearForm() {
        view.txtRewardId.setText("");

        if (view.cboStudentId.getItemCount() > 0) {
            view.cboStudentId.setSelectedIndex(0);
        }

        view.cboStudentId.setEnabled(true);
        view.txtStudentName.setText("");
        view.txtStudentName.setEnabled(false);
        view.dcRewardDate.setDate(new java.util.Date());
        view.txtRewardNote.setText("");
        view.txtRewardQuyetDinh.setText("");
        view.table.clearSelection();
    }
}
