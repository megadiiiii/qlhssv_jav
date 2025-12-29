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
        loadStudentCombo();
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

    // load cbo
    private void loadStudentCombo() {
        view.cboMaSinhVien.removeAllItems();
        view.cboMaSinhVien.addItem("");

        for (String id : dao.getAllStudentIds()) {
            view.cboMaSinhVien.addItem(id);
        }

        view.cboMaSinhVien.setEditable(true);
    }

    // ===== auto fill tên SV =====
    private void initStudentNameAutoFill() {
        view.cboMaSinhVien.addActionListener(e -> fillStudentName());

        JTextField editor =
                (JTextField) view.cboMaSinhVien.getEditor().getEditorComponent();

        editor.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { fillStudentName(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { fillStudentName(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { fillStudentName(); }
        });
    }

    private void fillStudentName() {
        if (!view.cboMaSinhVien.isEnabled()) return;

        String msv = getStudentIdFromCombo();
        if (msv.isEmpty()) {
            view.txtTenSinhVien.setText("");
            return;
        }

        view.txtTenSinhVien.setText(dao.findStudentNameById(msv));
    }

    // cac su kiem
    private void initActions(MainFrame mainFrame) {

        view.btnThem.addActionListener(e -> {
            try {
                Discipline dis = new Discipline(
                        0,
                        getStudentIdFromCombo(),
                        getHinhThucFromCombo(),
                        emptyToNull(view.txtSoQuyetDinh.getText()),
                        getSqlDate(view.dateNgayKyLuat),
                        getSqlDate(view.dateNgayKetThuc),
                        view.txtLyDo.getText()
                );

                dao.insert(dis);
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
            if (row == -1) return;

            int id = Integer.parseInt(view.model.getValueAt(row, 0).toString());
            try {
                dao.delete(new Discipline(id, null, null, null, null, null, null));
                loadTable();
                clearForm();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        view.btnSua.addActionListener(e -> {
            int id = Integer.parseInt(view.txtIdKyLuat.getText());

            try {
                dao.update(new Discipline(
                        id,
                        getStudentIdFromCombo(),
                        getHinhThucFromCombo(),
                        emptyToNull(view.txtSoQuyetDinh.getText()),
                        getSqlDate(view.dateNgayKyLuat),
                        getSqlDate(view.dateNgayKetThuc),
                        view.txtLyDo.getText()
                ));
                loadTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    // cellclick
    private void cellclick() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtIdKyLuat.setText(view.model.getValueAt(row, 0).toString());
        view.cboMaSinhVien.setSelectedItem(view.model.getValueAt(row, 1));
        view.txtTenSinhVien.setText(view.model.getValueAt(row, 2).toString());
        view.cboHinhThuc.setSelectedItem(view.model.getValueAt(row, 3));
        view.dateNgayKyLuat.setDate((java.util.Date) view.model.getValueAt(row, 4));
        view.dateNgayKetThuc.setDate((java.util.Date) view.model.getValueAt(row, 5));
        view.txtSoQuyetDinh.setText(view.model.getValueAt(row, 6).toString());
        view.txtLyDo.setText(view.model.getValueAt(row, 7).toString());

        // khoa 3 truong dl
        view.txtIdKyLuat.setEditable(false);
        view.cboMaSinhVien.setEnabled(false);
        view.txtTenSinhVien.setEditable(false);
    }

    private void clearForm() {
        view.cboMaSinhVien.setEnabled(true);
        view.txtIdKyLuat.setText("");
        view.cboMaSinhVien.setSelectedItem("");
        view.txtTenSinhVien.setText("");
        view.cboHinhThuc.setSelectedIndex(0);
        view.txtSoQuyetDinh.setText("");
        view.txtLyDo.setText("");
        view.dateNgayKyLuat.setDate(null);
        view.dateNgayKetThuc.setDate(null);
    }

    // ===== utils =====
    private String getStudentIdFromCombo() {
        Object o = view.cboMaSinhVien.getEditor().getItem();
        return o == null ? "" : o.toString().trim();
    }

    private String getHinhThucFromCombo() {
        Object o = view.cboHinhThuc.getSelectedItem();
        return o == null ? "" : o.toString();
    }

    private Date getSqlDate(com.toedter.calendar.JDateChooser c) {
        return c.getDate() == null ? null : new Date(c.getDate().getTime());
    }

    private String emptyToNull(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }
}
