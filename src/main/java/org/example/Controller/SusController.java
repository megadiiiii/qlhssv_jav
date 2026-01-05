package org.example.Controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DAO.SuspensionDAO;
import org.example.Model.Suspension;
import org.example.View.MainFrame;
import org.example.View.SuspensionView;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SusController {

    private final SuspensionView view;
    private final SuspensionDAO dao;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public SusController(SuspensionView view, MainFrame mainFrame, SuspensionDAO dao) {
        this.view = view;
        this.dao = dao;

        loadStudentCombo();
        initAction(mainFrame);
        loadTable();
        initStudentNameAutoFill();

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) onRowSelected();
        });
    }

    // ================= INIT ACTION =================
    private void initAction(MainFrame mainFrame) {

        // ===== ADD =====
        view.btnAdd.addActionListener(e -> {
            try {
                if (!validateForm()) return;

                Date startDate = view.dcStartDate.getDate();
                if (startDate == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày bắt đầu");
                    return;
                }

                Date endDate = view.dcEndDate.getDate();
                if (endDate == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày kết thúc");
                    return;
                }

                String reason = view.txtReason.getText().trim();
                String status = view.cboStatus.getSelectedItem().toString();

                Suspension s = new Suspension(0, getStudentIdFromCombo(), startDate, endDate, reason, status);
                dao.insert(s);

                JOptionPane.showMessageDialog(view, "Thêm bảo lưu thành công!");
                loadTable();
                clearForm();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Lỗi thêm bảo lưu!");
            }
        });

        // ===== APPROVE =====
        view.btnApprove.addActionListener(e -> updateStatus("Đã duyệt"));

        // ===== REJECT =====
        view.btnReject.addActionListener(e -> updateStatus("Bị từ chối"));

        // ===== SEARCH =====
        view.btnSearch.addActionListener(e -> search());

        view.btnDelete.addActionListener(e -> delete());

        view.btnExport.addActionListener(e -> onExport());

        // ===== BACK =====
        view.btnBack.addActionListener(e -> {
            mainFrame.showView("HOME");
            clearForm();
        });
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

    // ================= LOAD TABLE =================
    private void loadTable() {
        view.model.setRowCount(0);

        List<Suspension> list = dao.findAllSus();
        for (Suspension s : list) {
            view.model.addRow(new Object[]{
                    s.getSuspensionId(),
                    s.getStudentId(),
                    s.getStudentName(),
                    sdf.format(s.getStartDate()),
                    sdf.format(s.getEndDate()),
                    s.getReason(),
                    s.getStatus()
            });
        }
    }

    // ================= SEARCH =================
    private void search() {
        String studentId = view.txtStudentIdSearch.getText().trim();
        view.model.setRowCount(0);
        List<Suspension> list = dao.search(studentId);

        for (Suspension s : list) {
            view.model.addRow(new Object[]{
                    s.getSuspensionId(),
                    s.getStudentId(),
                    s.getStudentName(),
                    sdf.format(s.getStartDate()),
                    sdf.format(s.getEndDate()),
                    s.getReason(),
                    s.getStatus()
            });
        }
    }

    // ================= UPDATE STATUS =================
    private void updateStatus(String status) {
        int row = view.table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một dòng!");
            return;
        }

        int suspensionId = (int) view.model.getValueAt(row, 0);
        dao.updateStatus(suspensionId, status);

        JOptionPane.showMessageDialog(view, "Cập nhật trạng thái thành công!");
        loadTable();
    }

    // ================= ROW SELECT =================
    private void onRowSelected() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.cboStudentId.setSelectedItem(view.model.getValueAt(row, 1));
        view.txtStudentName.setText(view.model.getValueAt(row, 2).toString());

        Object startObj = view.model.getValueAt(row, 3);
        if (startObj != null && !startObj.toString().isEmpty()) {
            try {
                Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startObj.toString());
                view.dcStartDate.setDate(startDate);
            } catch (ParseException ex) {
                view.dcStartDate.setDate(null);
                ex.printStackTrace();
            }
        } else {
            view.dcStartDate.setDate(null);
        }
        view.dcStartDate.setDateFormatString("dd/MM/yyyy");

        Object endDateObj = view.model.getValueAt(row, 4);
        if (endDateObj != null && !endDateObj.toString().isEmpty()) {
            try {
                Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDateObj.toString());
                view.dcEndDate.setDate(endDate);
            } catch (ParseException ex) {
                view.dcEndDate.setDate(null);
                ex.printStackTrace();
            }
        } else {
            view.dcStartDate.setDate(null);
        }
        view.dcStartDate.setDateFormatString("dd/MM/yyyy");

        view.txtReason.setText(view.model.getValueAt(row, 5).toString());
        view.cboStatus.setSelectedItem(view.model.getValueAt(row, 6).toString());
    }

//    // ================= BUILD MODEL =================
//    private Suspension buildSuspensionFromForm() {
//        return new Suspension(
//                view.txtSuspensionId.getText().trim(),
//                view.txtStudentId.getText().trim(),
//                view.txtStudentName.getText().trim(),
//                new java.sql.Date(view.dcStartDate.getDate().getTime()),
//                new java.sql.Date(view.dcEndDate.getDate().getTime()),
//                view.txtReason.getText().trim(),
//                view.cboStatus.getSelectedItem().toString()
//        );
//    }

    // ================= VALIDATE =================
    private boolean validateForm() {
        if (view.cboStudentId.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(view, "Chưa chọn mã sinh viên!");
            return false;
        }

        Date start = view.dcStartDate.getDate();
        Date end = view.dcEndDate.getDate();

        if (!start.before(end)) {
            JOptionPane.showMessageDialog(view, "Ngày bắt đầu phải trước ngày kết thúc!");
            return false;
        }
        return true;
    }

    private void clearForm() {
        view.cboStudentId.setSelectedItem(0);
        view.dcStartDate.setDate(new Date());
        view.dcEndDate.setDate(new Date());
        view.txtReason.setText("");
        view.txtStudentName.setText("");
        view.cboStatus.setSelectedIndex(0);
        view.table.clearSelection();
    }

    private String getStudentIdFromCombo() {
        Object o = view.cboStudentId.getEditor().getItem();
        return o == null ? "" : o.toString().trim();
    }

    private void loadStudentCombo() {
        view.cboStudentId.removeAllItems();
        view.cboStudentId.addItem("");

        for (String id : dao.getAllStudentIds()) {
            view.cboStudentId.addItem(id);
        }

        view.cboStudentId.setEditable(true);
    }

    public void delete() {
        int row = view.table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng để xóa");
            return;
        }

        String susId = view.model.getValueAt(row, 0).toString();
        if (JOptionPane.showConfirmDialog(view, "Xác nhận xóa bảo lưu?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int result = dao.delete(susId);
            if (result > 0) {
                JOptionPane.showMessageDialog(view, "Xóa bảo lưu thành công");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa bảo lưu thất bại");
            }
        }
    }

    private void onExport() {
        List<Suspension> suspensionList = dao.findAllSus();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("Danh_sach_bao_luu.xlsx")); //
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            java.io.File filePath = fileChooser.getSelectedFile();
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Danh sách bảo lưu");

                // === TITLE ===
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("DANH SÁCH BẢO LƯU");

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
                String[] headers = {"STT", "Mã SV", "Tên SV", "Ngày bắt đầu", "Ngày kết thúc", "Lý do", "Trạng thái"};
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
                for (Suspension s : suspensionList) {
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
                    cell3.setCellValue(sdf.format(s.getStartDate()));
                    cell3.setCellStyle(dataStyle);

                    Cell cell4 = row.createCell(4);
                    cell4.setCellValue(sdf.format(s.getEndDate()));
                    cell4.setCellStyle(dataStyle);

                    Cell cell5 = row.createCell(5);
                    cell5.setCellValue(s.getReason());
                    cell5.setCellStyle(dataStyle);

                    Cell cell6 = row.createCell(6);
                    cell6.setCellValue(s.getStatus());
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

