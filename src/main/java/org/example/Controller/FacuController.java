package org.example.Controller;

import org.example.DAO.FacuDAO;
import org.example.Model.Faculties;
import org.example.View.FacultiesView;
import org.example.View.MainFrame;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class FacuController {
    private FacultiesView view;
    private FacuDAO dao;

    public FacuController(FacultiesView view, MainFrame mainFrame, FacuDAO dao) {
        this.view = view;
        this.dao = dao;

        initActions(mainFrame);

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) onRowSelected();
        });

        loadTable();
    }

    private void loadTable() {
        view.model.setRowCount(0);
        List<Faculties> list = dao.findAll();
        for (Faculties f : list) {
            view.model.addRow(new Object[]{
                    f.getFacuId(), f.getFacuName()
            });
        }
    }

    private void initActions(MainFrame mainFrame) {
        view.btnBack.addActionListener(e -> {
            mainFrame.showView("HOME");
        });

        view.btnAddFacu.addActionListener(e -> {
            try {
                Faculties facu = new Faculties(
                        view.txtFacuId.getText(),
                        view.txtFacuName.getText()
                );
                new FacuDAO().insert(facu);
                JOptionPane.showMessageDialog(view, "Lưu thành công");
                loadTable();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Lưu thất bại");
            }
        });
        view.btnDeleteFacu.addActionListener(e -> {
            int selectedRow = view.table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Chọn khoa muốn xóa trên bảng!");
                return;
            }

            String id = view.table.getValueAt(selectedRow, 0).toString();
            Faculties facu = new Faculties(id, null);

            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Bạn có chắc muốn xóa khoa này không?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int row = dao.delete(facu);
                    if (row > 0) {
                        JOptionPane.showMessageDialog(view, "Xóa thành công");
                        loadTable();
                        view.txtFacuName.setText("");
                        view.txtFacuId.setText("");
                    } else {
                        JOptionPane.showMessageDialog(view, "Không tìm thấy khoa để xóa");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Xóa thất bại");
                }
            }
        });
        view.btnEditFacu.addActionListener(e -> {
            String id = view.txtFacuId.getText().trim();
            String name = view.txtFacuName.getText().trim();

            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Chưa nhập đầy đủ thông tin");
                return;
            }

            Faculties facu = new Faculties(id, name);

            try {
                int row = dao.update(facu);
                if (row > 0) {
                    loadTable(); // reload JTable sau khi cập nhật
                    JOptionPane.showMessageDialog(view, "Cập nhật thành công");
                } else {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy khoa để cập nhật");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại: " + ex.getMessage());
            }
        });

    }

    private void onRowSelected() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtFacuId.setText(String.valueOf(view.model.getValueAt(row, 0)));
        view.txtFacuName.setText(String.valueOf(view.model.getValueAt(row, 1)));
    }
}
