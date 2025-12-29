package org.example.Controller;

import org.example.DAO.FacuDAO;
import org.example.DAO.MajorDAO;
import org.example.Model.Faculties;
import org.example.Model.Major;
import org.example.View.MainFrame;
import org.example.View.MajorView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class MajorController {

    private MajorView view;
    private MajorDAO majorDAO;
    private FacuDAO facuDAO;


    public MajorController(MajorView view, MainFrame mainFrame,
                           MajorDAO majorDAO, FacuDAO facuDAO) {
        this.view = view;
        this.majorDAO = majorDAO;
        this.facuDAO = facuDAO;

        loadFacuCombo();
        loadTable();
        initActions(mainFrame);

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) onRowSelected();
        });
    }

    // ===== LOAD TABLE =====
    private void loadTable() {
        view.model.setRowCount(0);
        List<Major> list = majorDAO.findAll();

        for (Major m : list) {
            view.model.addRow(new Object[]{
                    m.getMajorId(),
                    m.getMajorName(),
                    m.getFacuId(),
            });
        }
    }

    // ===== LOAD COMBOBOX =====
    private void loadFacuCombo() {
        view.cboFacuName.removeAllItems();
        for (Faculties f : facuDAO.findAll()) {
            view.cboFacuName.addItem(f);
        }
    }

    // ===== ACTIONS =====
    private void initActions(MainFrame mainFrame) {

        view.btnBack.addActionListener(e ->
                mainFrame.showView("HOME")
        );

        view.btnAddMajor.addActionListener(e -> {
            try {
                Faculties f = (Faculties) view.cboFacuName.getSelectedItem();
                Major m = new Major(
                        view.txtMajorId.getText(),
                        view.txtMajorName.getText(),
                        f.getFacuId(),
                        f.getFacuName()

                );
                majorDAO.insert(m);
                JOptionPane.showMessageDialog(view, "Thêm thành công");
                loadTable();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Thêm thất bại");
            }
        });

        view.btnEditMajor.addActionListener(e -> {
            try {
                Faculties f = (Faculties) view.cboFacuName.getSelectedItem();
                String facuId = f.getFacuId();

                Major m = new Major(
                        view.txtMajorId.getText(),
                        view.txtMajorName.getText(),
                        f.getFacuId(),
                        f.getFacuName()

                );
                if (majorDAO.update(m) > 0) {
                    JOptionPane.showMessageDialog(view, "Cập nhật thành công");
                    loadTable();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại");
            }
        });

        view.btnDeleteMajor.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Chọn ngành cần xóa");
                return;
            }

            String id = view.table.getValueAt(row, 0).toString();
            Major m = new Major();
            m.setMajorId(id);
            try {
                majorDAO.delete(m);
                JOptionPane.showMessageDialog(view, "Xóa thành công");
                loadTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Xóa thất bại");
            }
        });
    }

    // ===== CLICK TABLE =====
    private void onRowSelected() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtMajorId.setText(view.model.getValueAt(row, 0).toString());
        view.txtMajorName.setText(view.model.getValueAt(row, 1).toString());

        String facuId = view.model.getValueAt(row, 2).toString();
        for (int i = 0; i < view.cboFacuName.getItemCount(); i++) {
            if (view.cboFacuName.getItemAt(i).getFacuId().equals(facuId)) {
                view.cboFacuName.setSelectedIndex(i);
                break;
            }
        }
    }
}
