package org.example.Controller;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DAO.ScholarshipDAO;
import org.example.Model.ClassInfo;
import org.example.Model.Scholarship;
import org.example.View.MainFrame;
import org.example.View.ScholarshipView;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;
public class ScholarshipController {
    private ScholarshipView view;
    private ScholarshipDAO dao;

    public ScholarshipController(ScholarshipView view, MainFrame mainFrame, ScholarshipDAO dao) {
        this.view = view;
        this.dao = dao;

        initStudentNameAutoFill();
        initActions(mainFrame);
        loadStudentCombo();

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cellClick();
        });

        loadTable();
    }

    // ===== load table =====
    private void loadTable() {
        view.model.setRowCount(0);
        List<Scholarship> list = dao.findAll();

        for (Scholarship s : list) {
            view.model.addRow(new Object[]{
                    s.getScholarshipId(),
                    s.getStudentId(),
                    s.getStudentName(),
                    s.getScoreLevel(),
                    s.getDrlLevel(),
                    s.getScholarshipLevel(),
                    s.getSemester()
            });
        }
    }

    // ===== load table search =====
    private void onSearch() {
        String studentId = view.txtStudentIdSearch.getText().trim();

        try {
            List<Scholarship> list = dao.search(studentId);

            view.model.setRowCount(0);
            for (Scholarship s : list) {
                view.model.addRow(new Object[]{
                        s.getScholarshipId(),
                        s.getStudentId(),
                        s.getStudentName(),
                        s.getScoreLevel(),
                        s.getDrlLevel(),
                        s.getScholarshipLevel(),
                        s.getSemester()
                });
            }

            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Không tìm thấy kết quả");
            }

            view.table.clearSelection();
            clearSearchInput();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
            }

    // ===== load student_id vào combobox =====
    private void loadStudentCombo() {
        view.cboStudentId.removeAllItems();
        view.cboStudentId.addItem("");

        for (String id : dao.getAllStudentIds()) {
            view.cboStudentId.addItem(id);
        }
        view.cboStudentId.setEditable(true);
    }

    private void initStudentNameAutoFill() {
        view.cboStudentId.addActionListener(e -> fillStudentName());

        JTextField editor =
                (JTextField) view.cboStudentId.getEditor().getEditorComponent();

        editor.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                fillStudentName();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                fillStudentName();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                fillStudentName();
            }
        });
    }

    private void fillStudentName() {
        if (!view.cboStudentId.isEnabled()) return;

        String msv = getStudentIdFromCombo();
        if (msv.isEmpty()) {
            view.txtStudentName.setText("");
            return;
        }

        view.txtStudentName.setText(dao.findStudentNameById(msv));
    }

    private String getStudentIdFromCombo() {
        Object o = view.cboStudentId.getEditor().getItem();
        return o == null ? "" : o.toString().trim();
    }

    private String getScoreLevelCombo() {
        Object o = view.cboScoreLevel.getSelectedItem();
        return o == null ? "" : o.toString();
    }

    private String getDrlLevelCombo() {
        Object o = view.cboScoreLevel.getSelectedItem();
        return o == null ? "" : o.toString();
    }

    private String getScholashipLevelCombo() {
        Object o = view.cboScholarshipLevel.getSelectedItem();
        return o == null ? "" : o.toString();
    }

    private String getSemesterLevelCombo() {
        Object o = view.cboSemester.getSelectedItem();
        return o == null ? "" : o.toString();
    }

    // ===== action =====
    private void initActions(MainFrame mainFrame) {

        // THEM
        view.btnAdd.addActionListener(e -> {
            try {
                Scholarship s = new Scholarship();
                s.setStudentId(getStudentIdFromCrudCombo());
                s.setScoreLevel(getScoreLevelCombo());
                s.setDrlLevel(getDrlLevelCombo());
                s.setScholarshipLevel(getScholashipLevelCombo());
                s.setSemester(getSemesterLevelCombo());

                if (s.getStudentId().isEmpty()
                        || s.getScholarshipLevel() == null
                        || s.getSemester() == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin");
                    return;
                }

                dao.insert(s);
                loadTable();
                clearForm();
                JOptionPane.showMessageDialog(view, "Thêm thành công");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Thêm thất bại");
            }
        });

        // XOA
        view.btnDelete.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng để xóa");
                return;
            }

            int id = (int) view.model.getValueAt(row, 0);

            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Bạn có chắc chắn muốn xóa học bổng này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                int affected = dao.delete(id);
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

        // SUA
        view.btnEdit.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(view, "Vui lòng dòng để sửa");
                return;
            }

            int id = (int) view.model.getValueAt(row, 0);
            try {
                Scholarship s = new Scholarship();
                s.setScholarshipId(id);
                s.setStudentId(getStudentIdFromCrudCombo());
                s.setScoreLevel(getScoreLevelCombo());
                s.setDrlLevel(getDrlLevelCombo());
                s.setScholarshipLevel(getScholashipLevelCombo());
                s.setSemester(getSemesterLevelCombo());

                if (s.getStudentId().isEmpty()
                        || s.getScholarshipLevel() == null
                        || s.getSemester() == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin");
                    return;
                }

                int affected = dao.update(s);
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

        // TIM KIEM
        view.btnSearch.addActionListener(e -> onSearch());

        //Excel
        view.btnExport.addActionListener(e -> onExport());

        // RESET
        view.btnBack.addActionListener(e -> {
            mainFrame.showView("HOME");
            clearForm();
            loadTable();
        });
    }

    // ===== cell click =====
    private void cellClick() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.cboStudentId.setSelectedItem(view.model.getValueAt(row, 1));
        view.txtStudentName.setText(view.model.getValueAt(row, 2).toString());
        view.cboScoreLevel.setSelectedItem(view.model.getValueAt(row, 3));
        view.cboDrlLevel.setSelectedItem(view.model.getValueAt(row, 4));
        view.cboScholarshipLevel.setSelectedItem(view.model.getValueAt(row, 5));
        view.cboSemester.setSelectedItem(view.model.getValueAt(row, 6));

    }

    // ===== clear =====
    private void clearForm() {
        view.cboStudentId.setSelectedItem("");
        view.cboDrlLevel.setSelectedItem(0);
        view.cboSemester.setSelectedItem(0);
        view.cboScoreLevel.setSelectedItem(0);
        view.cboScholarshipLevel.setSelectedItem(0);

        view.table.clearSelection();
    }
    private void clearSearchInput() {
        view.txtStudentIdSearch.setText("");
    }

    // ===== utils =====
    private String getStudentIdFromCrudCombo() {
        Object o = view.cboStudentId.getEditor().getItem();
        return o == null ? "" : o.toString().trim();
    }

    private void onExport() {
        List<Scholarship> list = dao.findAll();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("Danh_sach_hoc_bong.xlsx")); //
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            java.io.File filePath = fileChooser.getSelectedFile();
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Danh sách học bổng");

                // === TITLE ===
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("DANH SÁCH HỌC BỔNG");

                // Style title
                CellStyle titleStyle = workbook.createCellStyle();
                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleCell.setCellStyle(titleStyle);

                // Merge title across all columns
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

                // === HEADER ===
                Row headerRow = sheet.createRow(1);
                String[] headers = {"STT", "Mã SV", "Tên SV", "Học lực", "RL", "Mức HB", "Học kỳ"};
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
                for (Scholarship s : list) {
                    Row row = sheet.createRow(rowIndex++);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(no++);
                    cell0.setCellStyle(dataStyle);

                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(s.getStudentId());
                    cell1.setCellStyle(dataStyle);

                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue(s.getStudentName());
                    cell2.setCellStyle(dataStyle);

                    Cell cell3 = row.createCell(3);
                    cell3.setCellValue(s.getScoreLevel());
                    cell3.setCellStyle(dataStyle);

                    Cell cell4 = row.createCell(4);
                    cell4.setCellValue(s.getDrlLevel());
                    cell4.setCellStyle(dataStyle);

                    Cell cell5 = row.createCell(5);
                    cell5.setCellValue(s.getScholarshipLevel());
                    cell5.setCellStyle(dataStyle);

                    Cell cell6 = row.createCell(6);
                    cell6.setCellValue(s.getSemester());
                    cell6.setCellStyle(dataStyle);
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
