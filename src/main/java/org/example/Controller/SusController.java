package org.example.Controller;

import org.example.DAO.SuspensionDAO;
import org.example.Model.Suspension;
import org.example.View.SuspensionView;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SusController {

    private final SuspensionView view;
    private final SuspensionDAO dao;

    public SusController(SuspensionView view, SuspensionDAO dao) {
        this.view = view;
        this.dao = dao;

        initAction();
        loadTable();

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) onRowSelected();
        });
    }

    // ================= INIT ACTION =================
    private void initAction() {

        // ===== ADD =====
        view.btnAdd.addActionListener(e -> {
            try {
                if (!validateForm()) return;

                Suspension s = buildSuspensionFromForm();
                dao.insert(s);

                JOptionPane.showMessageDialog(view, "Thêm bảo lưu thành công!");
                loadTable();
                clearForm();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Lỗi thêm bảo lưu!");
            }
        });

        // ===== APPROVE =====
        view.btnApprove.addActionListener(e -> updateStatus("APPROVED"));

        // ===== REJECT =====
        view.btnReject.addActionListener(e -> updateStatus("REJECTED"));

        // ===== SEARCH =====
        view.btnSearch.addActionListener(e -> search());

        // ===== BACK =====
        view.btnBack.addActionListener(e -> clearForm());
    }

    // ================= LOAD TABLE =================
    private void loadTable() {
        view.model.setRowCount(0);

        List<Suspension> list = dao.findAllSus();
        for (Suspension s : list) {
            view.model.addRow(new Object[]{
                    s.getStudentId(),
                    formatDate(s.getStartDate()),
                    formatDate(s.getEndDate()),
                    s.getReason(),
                    s.getStatus()
            });
        }
    }

    // ================= SEARCH =================
    private void search() {
        String studentId = view.txtStudentIdSearch.getText().trim();
        String status = (view.cboStatusSearch.getSelectedItem() == null)
                ? null
                : view.cboStatusSearch.getSelectedItem().toString();

        view.model.setRowCount(0);
        List<Suspension> list = dao.search(studentId, status);

        for (Suspension s : list) {
            view.model.addRow(new Object[]{
                    s.getStudentId(),
                    formatDate(s.getStartDate()),
                    formatDate(s.getEndDate()),
                    s.getReason(),
                    s.getStatus()
            });
        }
    }

    // ================= UPDATE STATUS =================
    private void updateStatus(String status) {
        int row = view.table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một dòng!");
            return;
        }

        String suspensionId = view.txtSuspensionId.getText();
        dao.updateStatus(suspensionId, status);

        JOptionPane.showMessageDialog(view, "Cập nhật trạng thái thành công!");
        loadTable();
    }

    // ================= ROW SELECT =================
    private void onRowSelected() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtStudentId.setText(view.model.getValueAt(row, 0).toString());

        try {
            view.spStartDate.setValue(
                    new SimpleDateFormat("dd/MM/yyyy")
                            .parse(view.model.getValueAt(row, 1).toString())
            );
            view.spEndDate.setValue(
                    new SimpleDateFormat("dd/MM/yyyy")
                            .parse(view.model.getValueAt(row, 2).toString())
            );
        } catch (Exception ignored) {}

        view.txtReason.setText(view.model.getValueAt(row, 3).toString());
        view.cboStatus.setSelectedItem(view.model.getValueAt(row, 4).toString());
    }

    // ================= BUILD MODEL =================
    private Suspension buildSuspensionFromForm() {
        return new Suspension(
                view.txtSuspensionId.getText().trim(),
                view.txtStudentId.getText().trim(),
                new java.sql.Date(((Date) view.spStartDate.getValue()).getTime()),
                new java.sql.Date(((Date) view.spEndDate.getValue()).getTime()),
                view.txtReason.getText().trim(),
                view.cboStatus.getSelectedItem().toString()
        );
    }

    // ================= VALIDATE =================
    private boolean validateForm() {
        if (view.txtStudentId.getText().isBlank()) {
            JOptionPane.showMessageDialog(view, "Chưa nhập mã sinh viên!");
            return false;
        }

        Date start = (Date) view.spStartDate.getValue();
        Date end = (Date) view.spEndDate.getValue();

        if (!start.before(end)) {
            JOptionPane.showMessageDialog(view, "Ngày bắt đầu phải trước ngày kết thúc!");
            return false;
        }
        return true;
    }

    // ================= UTIL =================
    private String formatDate(java.sql.Date d) {
        return new SimpleDateFormat("dd/MM/yyyy").format(d);
    }

    private void clearForm() {
        view.txtSuspensionId.setText("");
        view.txtStudentId.setText("");
        view.spStartDate.setValue(new Date());
        view.spEndDate.setValue(new Date());
        view.txtReason.setText("");
        view.cboStatus.setSelectedIndex(0);
        view.table.clearSelection();
    }
}

