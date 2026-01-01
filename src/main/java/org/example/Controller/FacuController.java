package org.example.Controller;

import org.example.DAO.FacuDAO;
import org.example.Model.Faculties;
import org.example.View.FacultiesView;
import org.example.View.MainFrame;

import javax.swing.*;
import java.util.List;

public class FacuController {
    private FacultiesView view;
    private FacuDAO dao;

    public FacuController(FacultiesView view, MainFrame mainFrame, FacuDAO dao) {
        this.view = view;
        this.dao = dao;

        loadTable();
        initTableListener();
        initActions(mainFrame);
    }

    private void initTableListener() {
        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onRowSelected();
            }
        });
    }

    private void loadTable() {
        view.model.setRowCount(0); // xóa cũ
        List<Faculties> list = dao.findAll();
        for (Faculties f : list) {
            view.model.addRow(new Object[]{
                    f.getFacuId(), f.getFacuName()
            });
        }
    }

    private void onRowSelected() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtFacuId.setText(String.valueOf(view.model.getValueAt(row, 0)));
        view.txtFacuName.setText(String.valueOf(view.model.getValueAt(row, 1)));

        view.txtFacuId.setEditable(false); // khóa mã khi chọn
    }

    private void initActions(MainFrame mainFrame) {
        view.btnBack.addActionListener(e -> mainFrame.showView("HOME"));

        view.btnFacuAdd.addActionListener(e -> addFacu());

        view.btnFacuUpdate.addActionListener(e -> updateFacu());

        view.btnFacuDelete.addActionListener(e -> deleteFacu());

        view.btnSearch.addActionListener(e -> searchFacu());
    }

    private void addFacu() {
        String id = view.txtFacuId.getText().trim();
        String name = view.txtFacuName.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin");
            return;
        }

        Faculties facu = new Faculties(id, name);

        try {
            int result = dao.insert(facu);
            if (result > 0) {
                JOptionPane.showMessageDialog(view, "Thêm khoa thành công");
                loadTable();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm: " + ex.getMessage());
        }
    }

    private void updateFacu() {
        int row = view.table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Chọn khoa muốn cập nhật trên bảng!");
            return;
        }

        String id = view.txtFacuId.getText().trim();
        String name = view.txtFacuName.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chưa nhập đầy đủ thông tin");
            return;
        }

        Faculties facu = new Faculties(id, name);

        int confirm = JOptionPane.showConfirmDialog(view, "Xác nhận cập nhật khoa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int result = dao.update(facu);
            if (result > 0) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công");
                loadTable();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(view, "Không tìm thấy khoa để cập nhật");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại: " + ex.getMessage());
        }
    }

    private void deleteFacu() {
        int row = view.table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Chọn khoa muốn xóa trên bảng!");
            return;
        }

        String id = view.txtFacuId.getText().trim();
        Faculties facu = new Faculties(id, null);

        int confirm = JOptionPane.showConfirmDialog(view, "Xác nhận xóa khoa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int result = dao.delete(facu);
            if (result > 0) {
                JOptionPane.showMessageDialog(view, "Xóa thành công");
                loadTable();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(view, "Không tìm thấy khoa để xóa");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Xóa thất bại: " + ex.getMessage());
        }
    }

    public void searchFacu() {}

    private void clearInput() {
        view.txtFacuId.setText("");
        view.txtFacuName.setText("");
        view.txtFacuId.setEditable(true); // mở lại để nhập khi thêm
    }
}
