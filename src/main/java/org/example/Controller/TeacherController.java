package org.example.Controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DAO.TeacherDAO;
import org.example.Model.Teacher;
import org.example.View.MainFrame;
import org.example.View.TeacherView;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;

public class TeacherController {
    private TeacherView view;
    private TeacherDAO dao;

    public TeacherController(TeacherView view, MainFrame mainFrame, TeacherDAO dao) {
        this.view = view;
        this.dao = dao;

        initActions(mainFrame);
        loadFacultyCombo();
        initFacultyNameAutoFill();

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cellclick();
        });

        loadTable();
    }

    // ===== load table =====
    private void loadTable() {
        view.model.setRowCount(0);
        List<Teacher> list = dao.findAll();

        for (Teacher t : list) {
            view.model.addRow(new Object[]{
                    t.getTeacherId(),
                    t.getTeacherName(),
                    t.getFacuId(),
                    t.getFacuName()
            });
        }
    }

    // ===== load table search =====
    private void loadTableSearch() {
        view.model.setRowCount(0);

        String teacherId = emptyToNull(view.txtTeacherIdSearch.getText());
        String teacherName = emptyToNull(view.txtTeacherNameSearch.getText());

        String facuId = getFacuIdFromSearchCombo();
        facuId = facuId.isEmpty() ? null : facuId;

        List<Teacher> list = dao.search(teacherId, teacherName, facuId);

        for (Teacher t : list) {
            view.model.addRow(new Object[]{
                    t.getTeacherId(),
                    t.getTeacherName(),
                    t.getFacuId(),
                    t.getFacuName()
            });
        }
    }

    // facuid cr se
    private void loadFacultyCombo() {
        // cr cb
        view.cboFacuId.removeAllItems();
        view.cboFacuId.addItem("");

        for (String id : dao.getAllFacultyIds()) {
            view.cboFacuId.addItem(id);
        }
        view.cboFacuId.setEditable(true);

        // se combo
        view.cboFacuIdSearch.removeAllItems();
        view.cboFacuIdSearch.addItem("");

        for (String id : dao.getAllFacultyIds()) {
            view.cboFacuIdSearch.addItem(id);
        }
        view.cboFacuIdSearch.setEditable(true);
    }

    // tu dien ten khoa
    private void initFacultyNameAutoFill() {
        view.cboFacuId.addActionListener(e -> fillFacultyName());

        JTextField editor =
                (JTextField) view.cboFacuId.getEditor().getEditorComponent();

        editor.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                fillFacultyName();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                fillFacultyName();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                fillFacultyName();
            }
        });
    }

    private void fillFacultyName() {
        String facuId = getFacuIdFromCrudCombo();
        if (facuId.isEmpty()) {
            view.txtFacuName.setText("");
            return;
        }
        view.txtFacuName.setText(dao.findFacultyNameById(facuId));
    }

    // action
    private void initActions(MainFrame mainFrame) {

        view.btnThem.addActionListener(e -> {
            try {
                Teacher t = new Teacher(
                        emptyToNull(view.txtTeacherId.getText()),
                        emptyToNull(view.txtTeacherName.getText()),
                        getFacuIdFromCrudCombo(),
                        null
                );

                if (t.getTeacherId() == null || t.getTeacherName() == null || t.getFacuId().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin");
                    return;
                }

                dao.insert(t);
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
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn giảng viên cần xóa");
                return;
            }

            String teacherId = view.model.getValueAt(row, 0).toString();

            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Bạn có chắc chắn muốn xóa giảng viên này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                int affected = dao.delete(teacherId);
                if (affected > 0) {
                    loadTable();
                    clearForm();
                    JOptionPane.showMessageDialog(view, "Xóa thành công");
                } else {
                    JOptionPane.showMessageDialog(view, "Không có dữ liệu nào bị xóa");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Xóa thất bại");
            }
        });

        view.btnSua.addActionListener(e -> {
            try {
                Teacher t = new Teacher(
                        emptyToNull(view.txtTeacherId.getText()),
                        emptyToNull(view.txtTeacherName.getText()),
                        getFacuIdFromCrudCombo(),
                        null
                );

                if (t.getTeacherId() == null || t.getTeacherName() == null || t.getFacuId().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin");
                    return;
                }

                int affected = dao.update(t);
                if (affected > 0) {
                    loadTable();
                    JOptionPane.showMessageDialog(view, "Sửa thành công");
                } else {
                    JOptionPane.showMessageDialog(view, "Không có dữ liệu nào được sửa");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Sửa thất bại");
            }
        });
        // timkiem
        view.btnSearch.addActionListener(e -> loadTableSearch());

        view.btnExport.addActionListener(e -> onExport());

        // reset
        view.btnMoi.addActionListener(e -> {
            clearForm();
            clearSearch();
            loadTable();
        });

        view.btnBack.addActionListener(e -> mainFrame.showView("HOME"));
    }

    // cell click
    private void cellclick() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtTeacherId.setText(view.model.getValueAt(row, 0).toString());
        view.txtTeacherName.setText(view.model.getValueAt(row, 1).toString());
        view.cboFacuId.setSelectedItem(view.model.getValueAt(row, 2));
        view.txtFacuName.setText(view.model.getValueAt(row, 3).toString());

        // khoa ma gv khi click
        view.txtTeacherId.setEditable(false);
    }

    private void clearForm() {
        view.txtTeacherId.setEditable(true);

        view.txtTeacherId.setText("");
        view.txtTeacherName.setText("");
        view.cboFacuId.setSelectedItem("");
        view.txtFacuName.setText("");

        view.table.clearSelection();
    }

    private void clearSearch() {
        view.txtTeacherIdSearch.setText("");
        view.txtTeacherNameSearch.setText("");
        view.cboFacuIdSearch.setSelectedItem("");
    }

    // utils
    private String getFacuIdFromCrudCombo() {
        Object o = view.cboFacuId.getEditor().getItem();
        return o == null ? "" : o.toString().trim();
    }

    private String getFacuIdFromSearchCombo() {
        Object o = view.cboFacuIdSearch.getEditor().getItem();
        return o == null ? "" : o.toString().trim();
    }

    private String emptyToNull(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }

    private void onExport() {
        List<Teacher> teacherList = dao.findAll();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("Danh_sach_GV.xlsx")); //
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            java.io.File filePath = fileChooser.getSelectedFile();
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Danh sách GV");

                // === TITLE ===
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("DANH SÁCH GIẢNG VIÊN");

                // Style title
                CellStyle titleStyle = workbook.createCellStyle();
                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleCell.setCellStyle(titleStyle);

                // Merge title across all columns
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

                // === HEADER ===
                Row headerRow = sheet.createRow(1);
                String[] headers = {"STT", "Mã giảng viên", "Tên giảng viên", "Mã khoa", "Tên khoa"};
                CellStyle headerStyle = workbook.createCellStyle();
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);
                headerStyle.setAlignment(HorizontalAlignment.CENTER);
                headerStyle.setBorderBottom(BorderStyle.THIN);
                headerStyle.setBorderTop(BorderStyle.THIN);
                headerStyle.setBorderLeft(BorderStyle.THIN);
                headerStyle.setBorderRight(BorderStyle.THIN);

                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // === DATA ===
                CellStyle dataStyle = workbook.createCellStyle();
                dataStyle.setBorderBottom(BorderStyle.THIN);
                dataStyle.setBorderTop(BorderStyle.THIN);
                dataStyle.setBorderLeft(BorderStyle.THIN);
                dataStyle.setBorderRight(BorderStyle.THIN);

                int no = 1; //STT
                int rowIndex = 2;
                for (Teacher teacher : teacherList) {
                    Row row = sheet.createRow(rowIndex++);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(no++);
                    cell0.setCellStyle(dataStyle);

                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(teacher.getTeacherId());
                    cell1.setCellStyle(dataStyle);

                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue(teacher.getTeacherName());
                    cell2.setCellStyle(dataStyle);

                    Cell cell3 = row.createCell(3);
                    cell3.setCellValue(teacher.getFacuId());
                    cell3.setCellStyle(dataStyle);

                    Cell cell4 = row.createCell(4);
                    cell4.setCellValue(teacher.getFacuName());
                    cell4.setCellStyle(dataStyle);
                }

                // === AUTO FILTER ===
                sheet.setAutoFilter(new CellRangeAddress(
                        1,
                        sheet.getLastRowNum(),
                        0,
                        headers.length - 1
                ));

                // === FREEZE HEADER (title + header) ===
                sheet.createFreezePane(0, 2);

                // === AUTO SIZE COLUMNS ===
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                // Auto-size columns
                // Ghi file
                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    workbook.write(fos);
                }

                JOptionPane.showMessageDialog(view, "Xuất Excel thành công!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Xuất Excel thất bại: " + ex.getMessage());
            }
        }
    }
}
