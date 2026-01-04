package org.example.Controller;

import org.example.DAO.DisciplineDAO;
import org.example.Model.Discipline;
import org.example.View.DisciplineView;
import org.example.View.MainFrame;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class DisciplineController {

    private DisciplineView view;
    private DisciplineDAO dao;

    public DisciplineController(DisciplineView view, MainFrame mainFrame, DisciplineDAO dao) {
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
        List<Discipline> list = dao.findAll();

        for (Discipline d : list) {
            view.model.addRow(new Object[]{
                    d.getIdkyluat(),
                    d.getStudentId(),
                    d.getStudentName(),
                    d.getHinhThuc(),
                    d.getKyluatDate(),
                    d.getNgayKetThuc(),
                    d.getSoQuyetDinh(),
                    d.getLyDo()
            });
        }
    }

    // load table search
    private void loadTableSearch() {
        view.model.setRowCount(0);

        Integer idKyLuat = parseIntOrNull(view.txtIdKyLuatSearch.getText());
        String studentId = emptyToNull(view.txtMaSinhVienSearch.getText());
        String hinhThuc = getHinhThucFromSearchCombo();
        hinhThuc = (hinhThuc == null || hinhThuc.trim().isEmpty()) ? null : hinhThuc;

        List<Discipline> list = dao.search(idKyLuat, studentId, hinhThuc);

        for (Discipline d : list) {
            view.model.addRow(new Object[]{
                    d.getIdkyluat(),
                    d.getStudentId(),
                    d.getStudentName(),
                    d.getHinhThuc(),
                    d.getKyluatDate(),
                    d.getNgayKetThuc(),
                    d.getSoQuyetDinh(),
                    d.getLyDo()
            });
        }
    }

    //auto fill tên theo msv
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
                Discipline dis = new Discipline(
                        0,
                        getStudentIdFromField(),
                        getHinhThucFromCombo(),
                        emptyToNull(view.txtSoQuyetDinh.getText()),
                        getSqlDate(view.dateNgayKyLuat),
                        getSqlDate(view.dateNgayKetThuc),
                        view.txtLyDo.getText()
                );

                if (dis.getStudentId() == null || dis.getStudentId().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập mã sinh viên");
                    return;
                }

                dao.insert(dis);
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
                    "Bạn có chắc chắn muốn xóa kỷ luật này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                int affected = dao.delete(new Discipline(id, null, null, null, null, null, null));
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
            if (view.txtIdKyLuat.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần sửa");
                return;
            }

            int id = Integer.parseInt(view.txtIdKyLuat.getText());

            try {
                Discipline dis = new Discipline(
                        id,
                        getStudentIdFromField(),
                        getHinhThucFromCombo(),
                        emptyToNull(view.txtSoQuyetDinh.getText()),
                        getSqlDate(view.dateNgayKyLuat),
                        getSqlDate(view.dateNgayKetThuc),
                        view.txtLyDo.getText()
                );

                int affected = dao.update(dis);
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

    // cellclik
    private void cellclick() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtIdKyLuat.setText(view.model.getValueAt(row, 0).toString());
        view.txtMaSinhVien.setText(view.model.getValueAt(row, 1).toString());
        view.txtTenSinhVien.setText(view.model.getValueAt(row, 2).toString());
        view.cboHinhThuc.setSelectedItem(view.model.getValueAt(row, 3));

        view.dateNgayKyLuat.setDate((java.util.Date) view.model.getValueAt(row, 4));
        view.dateNgayKetThuc.setDate((java.util.Date) view.model.getValueAt(row, 5));

        Object sqd = view.model.getValueAt(row, 6);
        view.txtSoQuyetDinh.setText(sqd == null ? "" : sqd.toString());

        Object ld = view.model.getValueAt(row, 7);
        view.txtLyDo.setText(ld == null ? "" : ld.toString());

        // khoa dl khi cell
        view.txtIdKyLuat.setEditable(false);
        view.txtMaSinhVien.setEditable(false);
        view.txtTenSinhVien.setEditable(false);
    }

    private void clearForm() {
        view.txtIdKyLuat.setText("");
        view.txtMaSinhVien.setText("");
        view.txtTenSinhVien.setText("");

        view.txtMaSinhVien.setEditable(true); // mở lại để nhập
        view.cboHinhThuc.setSelectedIndex(0);

        view.txtSoQuyetDinh.setText("");
        view.txtLyDo.setText("");
        view.dateNgayKyLuat.setDate(null);
        view.dateNgayKetThuc.setDate(null);

        view.table.clearSelection();
    }

    private void clearSearch() {
        view.txtIdKyLuatSearch.setText("");
        view.txtMaSinhVienSearch.setText("");
        view.cboHinhThucSearch.setSelectedIndex(0);
    }

    // utils
    private String getStudentIdFromField() {
        String s = view.txtMaSinhVien.getText();
        return s == null ? "" : s.trim();
    }

    private String getHinhThucFromCombo() {
        Object o = view.cboHinhThuc.getSelectedItem();
        return o == null ? "" : o.toString();
    }

    private String getHinhThucFromSearchCombo() {
        Object o = view.cboHinhThucSearch.getSelectedItem();
        return o == null ? "" : o.toString().trim();
    }

    private Date getSqlDate(com.toedter.calendar.JDateChooser c) {
        return (c == null || c.getDate() == null) ? null : new Date(c.getDate().getTime());
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
