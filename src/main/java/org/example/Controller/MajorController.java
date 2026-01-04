package org.example.Controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DAO.FacuDAO;
import org.example.DAO.MajorDAO;
import org.example.Model.Faculties;
import org.example.Model.Major;
import org.example.View.MainFrame;
import org.example.View.MajorView;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class MajorController {
    private MajorView view;
    private MajorDAO majorDAO;

    public MajorController(MajorView view, MainFrame mainFrame, MajorDAO majorDAO) {
        this.view = view;
        this.majorDAO = majorDAO;

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
        view.btnExport.addActionListener(e -> onExport());
        view.btnBack.addActionListener(e -> {
            mainFrame.showView("HOME");
            clearSearchInput();
            clearInput();
            loadTable();
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

        List<Faculties> facuList = new FacuDAO().findAll();

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

    private void onExport() {
        List<Major> list = majorDAO.findAllMajors();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("Danh_sach_chuyen_nganh.xlsx")); //
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            java.io.File filePath = fileChooser.getSelectedFile();
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Danh sách chuyên ngành");

                // === TITLE ===
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("DANH SÁCH CHUYÊN NGÀNH");

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
                String[] headers = {"STT", "Khoa", "Mã chuyên ngành", "Tên chuyên ngành"};
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
                for (Major m : list) {
                    Row row = sheet.createRow(rowIndex++);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(no++);
                    cell0.setCellStyle(dataStyle);

                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(m.getFaculty().getFacuName());
                    cell1.setCellStyle(dataStyle);

                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue(m.getMajorId());
                    cell2.setCellStyle(dataStyle);

                    Cell cell3 = row.createCell(3);
                    cell3.setCellValue(m.getMajorName());
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
