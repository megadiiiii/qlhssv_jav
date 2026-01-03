package org.example.Controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DAO.CohortDAO;
import org.example.Model.Cohort;
import org.example.View.CohortView;
import org.example.View.MainFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class CohortController {
    private final CohortView view;
    private final CohortDAO dao;

    public CohortController(CohortView cohortView, MainFrame mainFrame, CohortDAO dao) {
        this.view = cohortView;
        this.dao = dao;

        initActions(mainFrame);

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) onRowSelected();
        });

        loadTable();
    }

    private void initActions(MainFrame mainFrame) {
        view.btnCohortAdd.addActionListener(e -> onInsertCohort());

        view.btnCohortDelete.addActionListener(e -> onDeleteCohort());

        view.btnCohortUpdate.addActionListener(e -> onUpdateCohort());

        view.btnSearch.addActionListener(e -> onSearchCohort());

        view.btnExport.addActionListener(e -> onExport());

        view.btnBack.addActionListener(e -> {
            mainFrame.showView("HOME");
        });
    }

    private void loadTable() {
        view.model.setRowCount(0);
        List<Cohort> cohorts = dao.getAllCohorts();
        for (Cohort c : cohorts) {
            view.model.addRow(new Object[]{
                    c.getCohortId(), c.getCohortName(), c.getCohortStartYear(), c.getCohortEndYear()
            });
        }
    }

    public void onInsertCohort() {
        String cohortName = view.txtCohortName.getText().trim();
        String cohortStartStr = view.txtCohortStartYear.getText().trim();
        String cohortEndStr = view.txtCohortEndYear.getText().trim();
        if (cohortName.isEmpty() || cohortStartStr.isEmpty() || cohortEndStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đủ thông tin");
        }

        int cohortStartYear, cohortEndYear;
        try {
            cohortStartYear = Integer.parseInt(cohortStartStr);
            cohortEndYear = Integer.parseInt(cohortEndStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Năm bắt đầu/kết thúc phải là số");
            return;
        }

        if (cohortStartYear < 1900 || cohortStartYear > 3000 ||
                cohortEndYear < 1900 || cohortEndYear > 3000) {
            JOptionPane.showMessageDialog(view, "Năm không hợp lệ");
            return;
        }

        if (cohortEndYear < cohortStartYear) {
            JOptionPane.showMessageDialog(view, "Năm kết thúc phải lớn hơn năm nhập học");
            return;
        }

        Cohort c = new Cohort(cohortName, cohortStartYear, cohortEndYear);
        try {
            int row = dao.insert(c);
            if (row > 0) {
                JOptionPane.showMessageDialog(view, "Thêm thành công");
                loadTable();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    public void onDeleteCohort() {
        int selectedRow = view.table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "Chọn 1 khóa đào tạo để xóa");
            return;
        }
        int id = (int) view.model.getValueAt(selectedRow, 0);
        if (JOptionPane.showConfirmDialog(view, "Xác nhận xóa khóa đào tạo", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                int row = dao.removeById(id);
                if (row > 0) {
                    JOptionPane.showMessageDialog(view, "Thêm thành công");
                    loadTable();
                    clearInput();
                } else JOptionPane.showMessageDialog(view, "Thêm thất bại");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, e.getMessage());
            }
        }
    }

    public void onUpdateCohort() {
        int selectedRow = view.table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "Chọn 1 khóa đào tạo để xóa");
            return;
        }
        int id = (int) view.model.getValueAt(selectedRow, 0);

        String cohortName = view.txtCohortName.getText().trim();
        String cohortStartStr = view.txtCohortStartYear.getText().trim();
        String cohortEndStr = view.txtCohortEndYear.getText().trim();
        if (cohortName.isEmpty() || cohortStartStr.isEmpty() || cohortEndStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đủ thông tin");
            return;
        }

        int cohortStartYear, cohortEndYear;
        try {
            cohortStartYear = Integer.parseInt(cohortStartStr);
            cohortEndYear = Integer.parseInt(cohortEndStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Năm bắt đầu/kết thúc phải là số");
            return;
        }

        if (cohortStartYear < 1900 || cohortStartYear > 3000 ||
                cohortEndYear < 1900 || cohortEndYear > 3000) {
            JOptionPane.showMessageDialog(view, "Năm không hợp lệ");
            return;
        }

        if (cohortEndYear < cohortStartYear) {
            JOptionPane.showMessageDialog(view, "Năm kết thúc phải lớn hơn năm nhập học");
            return;
        }

        Cohort c = new Cohort(id, cohortName, cohortStartYear, cohortEndYear);

        if (JOptionPane.showConfirmDialog(view, "Xác nhận cập nhật khóa đào tạo", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                int row = dao.updateById(c);
                if (row > 0) {
                    loadTable();
                    JOptionPane.showMessageDialog(view, "Cập nhật thành công");
                    clearInput();
                } else JOptionPane.showMessageDialog(view, "Cập nhật thất bại");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, e.getMessage());
            }
        }
    }

    private void onSearchCohort() {
        String name = view.txtCohortNameSearch.getText().trim();

        Integer startYear = null;
        Integer endYear = null;

        String startStr = view.txtCohortStartYearSearch.getText().trim();
        String endStr = view.txtCohortEndYearSearch.getText().trim();

        try {
            if (!startStr.isEmpty()) startYear = Integer.parseInt(startStr);
            if (!endStr.isEmpty()) endYear = Integer.parseInt(endStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Năm tìm kiếm phải là số");
            return;
        }

        if (name.isBlank() && startYear == null && endYear == null) {
            loadTable();
            return;
        }

        try {
            List<Cohort> result = dao.search(name, startYear, endYear);

            view.model.setRowCount(0);
            for (Cohort c : result) {
                view.model.addRow(new Object[]{
                        c.getCohortId(),
                        c.getCohortName(),
                        c.getCohortStartYear(),
                        c.getCohortEndYear()
                });
            }

            if (result.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Không tìm thấy kết quả");
            }

            view.table.clearSelection();
            clearSearchInput();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    private void clearInput() {
        view.txtCohortName.setText("");
        view.txtCohortStartYear.setText("");
        view.txtCohortEndYear.setText("");
    }

    private void clearSearchInput() {
        view.txtCohortNameSearch.setText("");
        view.txtCohortStartYearSearch.setText("");
        view.txtCohortEndYearSearch.setText("");
    }

    private void onRowSelected() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.txtCohortName.setText(view.table.getValueAt(row, 1).toString());
        view.txtCohortStartYear.setText(view.table.getValueAt(row, 2).toString());
        view.txtCohortEndYear.setText(view.table.getValueAt(row, 3).toString());
    }

    private void onExport() {
        List<Cohort> cohortList = dao.getDataExport();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("Danh_sach_khoa_dao_tao.xlsx")); //
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            java.io.File filePath = fileChooser.getSelectedFile();
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Danh sách khóa đào tạo");

                // === TITLE ===
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("BẢNG DANH SÁCH KHÓA ĐÀO TẠO");

                // Style title
                CellStyle titleStyle = workbook.createCellStyle();
                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleCell.setCellStyle(titleStyle);

                // Merge title across all columns
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

                // === HEADER ===
                Row headerRow = sheet.createRow(1);
                String[] headers = {"STT", "Tên khóa", "Năm bắt đầu", "Năm kết thúc"};
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
                for (Cohort c : cohortList) {
                    Row row = sheet.createRow(rowIndex++);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(no++);
                    cell0.setCellStyle(dataStyle);

                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(c.getCohortName());
                    cell1.setCellStyle(dataStyle);

                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue(c.getCohortStartYear());
                    cell2.setCellStyle(dataStyle);

                    Cell cell3 = row.createCell(3);
                    cell3.setCellValue(c.getCohortEndYear());
                    cell3.setCellStyle(dataStyle);
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
