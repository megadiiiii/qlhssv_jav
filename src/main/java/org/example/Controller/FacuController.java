package org.example.Controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DAO.FacuDAO;
import org.example.Model.Faculties;
import org.example.View.FacultiesView;
import org.example.View.MainFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
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
                    f.getFacuId(),
                    f.getFacuName()
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

        view.btnFacuExport.addActionListener(e -> onExport());

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

        if (JOptionPane.showConfirmDialog(view, "Xác nhận xóa khoa?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
    }

    private void searchFacu() {
        String majorId = view.txtFacuIdSearch.getText().trim();
        String majorName = view.txtFacuNameSearch.getText().trim();

        if (majorId.isEmpty() && majorName.isEmpty()) {
            loadTable();
            clearInput();
            return;
        }
        try {
            List<Faculties> result = dao.searchFacu(majorId, majorName);

            view.model.setRowCount(0);
            for (Faculties facu : result) {
                view.model.addRow(new Object[]{
                        facu.getFacuId(),
                        facu.getFacuName()
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

    private void onExport() {
        List<Faculties> facuList = dao.findAll();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("Danh_sach_khoa.xlsx")); //
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            java.io.File filePath = fileChooser.getSelectedFile();
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Danh sách khoa");

                // === TITLE ===
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("DANH SÁCH KHOA");

                // Style title
                CellStyle titleStyle = workbook.createCellStyle();
                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleCell.setCellStyle(titleStyle);

                // Merge title across all columns
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

                // === HEADER ===
                Row headerRow = sheet.createRow(1);
                String[] headers = {"STT", "Mã khoa", "Tên khoa"};
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
                for (Faculties facu : facuList) {
                    Row row = sheet.createRow(rowIndex++);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(no++);
                    cell0.setCellStyle(dataStyle);

                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(facu.getFacuId());
                    cell1.setCellStyle(dataStyle);

                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue(facu.getFacuName());
                    cell2.setCellStyle(dataStyle);
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

    private void clearInput() {
        view.txtFacuId.setText("");
        view.txtFacuName.setText("");
        view.txtFacuId.setEditable(true); // mở lại để nhập khi thêm
    }

    private void clearSearchInput() {
        view.txtFacuIdSearch.setText("");
        view.txtFacuNameSearch.setText("");
    }
}
