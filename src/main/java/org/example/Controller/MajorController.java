package org.example.Controller;

import org.example.DAO.FacuDAO;
import org.example.DAO.MajorDAO;
import org.example.Model.Faculties;
import org.example.Model.Major;
import org.example.View.MainFrame;
import org.example.View.MajorView;

import javax.swing.*;
import java.util.List;

public class MajorController {
    private MajorView view;
    private MajorDAO majorDAO;
    private FacuDAO facuDAO;

    public MajorController(MajorView view, MainFrame mainFrame, MajorDAO majorDAO, FacuDAO facuDAO) {
        this.view = view;
        this.majorDAO = majorDAO;
        this.facuDAO = facuDAO;

        loadFacu();
        loadTable();
        initTableListener();

        initAction(mainFrame);
    }

    private void initTableListener() {
        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onRowSelected();
            }
        });
    }

    private void initAction(MainFrame mainFrame) {
        view.btnMajorAdd.addActionListener(e -> addMajor());
        view.btnMajorDelete.addActionListener(e -> deleteMajor());
        view.btnMajorUpdate.addActionListener(e -> updateMajor());
        view.btnSearch.addActionListener(e -> searchMajor());
        view.btnBack.addActionListener(e -> {
            mainFrame.showView("HOME");
        });
    }

    private void loadTable() {
        view.model.setRowCount(0);

        List<Major> majorList = majorDAO.findAllMajors();

        for (Major m : majorList) {
            view.model.addRow(new Object[]{
                    m.getFaculty().getFacuName(),
                    m.getMajorId(),
                    m.getMajorName()
            });
        }

    }

    private void onRowSelected() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        String facuName = view.model.getValueAt(row, 0).toString();
        String majorId = view.model.getValueAt(row, 1).toString();
        String majorName = view.model.getValueAt(row, 2).toString();

        view.txtMajorId.setText(majorId);
        view.txtMajorName.setText(majorName);

        for (int i = 0; i < view.cboFacu.getItemCount(); i++) {
            Faculties f = view.cboFacu.getItemAt(i);
            if (facuName.equals(f.getFacuName())) {
                view.cboFacu.setSelectedIndex(i);
                break;
            }
        }

        view.txtMajorId.setEditable(false);
    }

    private void addMajor() {
        String majorId = view.txtMajorId.getText().trim();
        String majorName = view.txtMajorName.getText().trim();

        Faculties facu = (Faculties) view.cboFacu.getSelectedItem();

        if (facu == null || facu.getFacuId().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn khoa/viện");
            return;
        }

        if (majorId.isEmpty() || majorName.isEmpty() || facu == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng điền đủ thông tin");
            return;
        }

        Major m = new Major(majorId, majorName, facu);
        int result = majorDAO.insert(m);
        if (result > 0) {
            JOptionPane.showMessageDialog(view, "Thêm chuyên ngành thành công");
            loadTable();
            clearInput();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm chuyên ngành thất bại");
        }
    }

    private void deleteMajor() {
        int row = view.table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn chuyên ngành để xóa");
            return;
        }

        String majorId = view.model.getValueAt(row, 1).toString();

        if (JOptionPane.showConfirmDialog(view, "Xác nhận xóa chuyên ngành?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int result = majorDAO.delete(majorId);
            if (result > 0) {
                JOptionPane.showMessageDialog(view, "Xóa chuyên ngành thành công");
                loadTable();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa chuyên ngành thất bại");
            }
        }
    }

    private void updateMajor() {
        int row = view.table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn chuyên ngành để cập nhật");
            return;
        }

        String majorId = view.model.getValueAt(row, 1).toString();
        String majorName = view.txtMajorName.getText().trim();
        Faculties facu = (Faculties) view.cboFacu.getSelectedItem();

        if (facu == null || majorId == null || majorName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng điền đủ thông tin");
            return;
        }

        Major m = new Major(majorId, majorName, facu);

        if (JOptionPane.showConfirmDialog(view, "Xác nhận cập nhật chuyên ngành?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int result = majorDAO.update(m);
            if (result > 0) {
                JOptionPane.showMessageDialog(view, "Cập nhật thông tin chuyên ngành thành công");
                loadTable();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thông tin chuyên ngành thất bại");
            }
        }
    }

    private void searchMajor() {
        String majorId = view.txtMajorIdSearch.getText().trim();
        String majorName = view.txtMajorNameSearch.getText().trim();
        Faculties facu = (Faculties) view.cboFacuSearch.getSelectedItem();

        if (majorId.isEmpty() && majorName.isEmpty() && facu == null) {
            loadTable();
            clearInput();
            return;
        }
        try {
            List<Major> result = majorDAO.search(facu, majorId, majorName);

            view.model.setRowCount(0);
            for (Major m : result) {
                view.model.addRow(new Object[]{
                        m.getFaculty().getFacuName(),
                        m.getMajorId(),
                        m.getMajorName()
                });
            }

            if(result.isEmpty()){
                JOptionPane.showMessageDialog(view, "Không tìm thấy kết quả");
            }

            view.table.clearSelection();
            clearSearchInput();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    private void loadFacu() {
        view.cboFacu.removeAllItems();
        view.cboFacuSearch.removeAllItems();

        Faculties placeholder = new Faculties("", "-- Chọn khoa/viện --");

        view.cboFacu.addItem(placeholder);
        view.cboFacuSearch.addItem(placeholder);

        List<Faculties> facuList = facuDAO.findAll();

        for (Faculties f : facuList) {
            view.cboFacu.addItem(f);
            view.cboFacuSearch.addItem(f);
        }

        view.cboFacu.setSelectedIndex(0);
        view.cboFacuSearch.setSelectedIndex(0);
    }

    private void clearInput() {
        view.txtMajorId.setText("");
        view.txtMajorName.setText("");
        view.cboFacu.setSelectedIndex(0);
    }

    private void clearSearchInput() {
        view.txtMajorIdSearch.setText("");
        view.txtMajorNameSearch.setText("");
        view.cboFacuSearch.setSelectedIndex(0);
    }
}
