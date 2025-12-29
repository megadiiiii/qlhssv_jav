package org.example.Controller;

import org.example.DAO.RewardDAO;
import org.example.Model.Reward;
import org.example.View.RewardView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class RewardController {

    private final RewardView view;
    private final RewardDAO dao;

    public RewardController(RewardView view) {
        this.view = view;
        this.dao = new RewardDAO();

        initComboStudent();
        loadTable();
        bindEvents();
        clearForm();
    }

    private void initComboStudent() {
        List<String> ids = dao.getStudentIds();
        view.cboStudentId.removeAllItems();
        for (String id : ids) {
            view.cboStudentId.addItem(id);
        }
    }

    private void loadTable() {
        DefaultTableModel model = view.model;
        model.setRowCount(0);

        for (Reward r : dao.findAll()) {
            model.addRow(new Object[]{
                    r.getRewardId(),
                    r.getStudentId(),
                    r.getRewardDate(),
                    r.getRewardNote(),
                    r.getRewardQuyetDinh()
            });
        }
    }

    private void bindEvents() {
        view.btnAddReward.addActionListener(e -> onAdd());
        view.btnEditReward.addActionListener(e -> onEdit());
        view.btnDeleteReward.addActionListener(e -> onDelete());
        view.btnBack.addActionListener(e -> onBack());

        view.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;
                onRowClick();
            }
        });
    }

    private void onAdd() {
        Reward r = readForm(false);
        if (r == null) return;

        if (dao.insert(r)) {
            JOptionPane.showMessageDialog(view, "Thêm khen thưởng thành công!");
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại!");
        }
    }

    private void onEdit() {
        Reward r = readForm(true);
        if (r == null) return;

        if (dao.update(r)) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
        }
    }

    private void onDelete() {
        String idText = view.txtRewardId.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Xóa khen thưởng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int rewardId = Integer.parseInt(idText);
        if (dao.delete(rewardId)) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!");
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(view, "Xóa thất bại!");
        }
    }

    private void onRowClick() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        Object rewardId = view.model.getValueAt(row, 0);
        Object studentId = view.model.getValueAt(row, 1);
        Object rewardDate = view.model.getValueAt(row, 2);
        Object note = view.model.getValueAt(row, 3);
        Object qd = view.model.getValueAt(row, 4);

        view.txtRewardId.setText(String.valueOf(rewardId));
        view.cboStudentId.setSelectedItem(String.valueOf(studentId));
        view.txtRewardDate.setText(String.valueOf(rewardDate));
        view.txtRewardNote.setText(note == null ? "" : String.valueOf(note));
        view.txtRewardQuyetDinh.setText(qd == null ? "" : String.valueOf(qd));
    }

    private void clearForm() {
        view.txtRewardId.setText("");
        if (view.cboStudentId.getItemCount() > 0) view.cboStudentId.setSelectedIndex(0);

        view.txtRewardDate.setText(LocalDate.now().toString());
        view.txtRewardNote.setText("");
        view.txtRewardQuyetDinh.setText("");

        view.table.clearSelection();
    }

    private Reward readForm(boolean requireId) {
        String idText = view.txtRewardId.getText().trim();
        String studentId = (String) view.cboStudentId.getSelectedItem();
        String dateText = view.txtRewardDate.getText().trim();
        String note = view.txtRewardNote.getText().trim();
        String qd = view.txtRewardQuyetDinh.getText().trim();

        if (studentId == null || studentId.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chưa có mã sinh viên!");
            return null;
        }
        if (dateText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Ngày không được để trống (yyyy-MM-dd)!");
            return null;
        }
        if (qd.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Quyết định không được để trống!");
            return null;
        }

        Date sqlDate;
        try {
            sqlDate = Date.valueOf(LocalDate.parse(dateText)); // yyyy-MM-dd
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(view, "Sai định dạng ngày. Nhập yyyy-MM-dd (VD: 2025-12-29)");
            return null;
        }

        Reward r = new Reward();

        if (requireId) {
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
                return null;
            }
            r.setRewardId(Integer.parseInt(idText));
        }

        r.setStudentId(studentId);
        r.setRewardDate(sqlDate);
        r.setRewardNote(note);
        r.setRewardQuyetDinh(qd);
        return r;
    }

    private void onBack() {
        // TODO: sửa theo project m
        // Ví dụ:
        // MainFrame.getInstance().setView(new HomeView());
        // hoặc CardLayout: mainPanelLayout.show(mainPanel, "HOME");

        JOptionPane.showMessageDialog(view, "Nút Quay lại: m sửa onBack() theo màn trước của project.");
    }
}
