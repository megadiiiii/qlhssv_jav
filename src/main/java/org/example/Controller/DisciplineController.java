package org.example.Controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DAO.DisciplineDAO;
import org.example.Model.Discipline;
import org.example.View.DisciplineView;
import org.example.View.MainFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DisciplineController {
    private DisciplineView view;
    private DisciplineDAO dao;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DisciplineController(DisciplineView view, MainFrame mainFrame, DisciplineDAO dao) {
        this.view = view;
        this.dao = dao;
        initActions(mainFrame);
        loadStudentCombo();
        initStudentNameAutoFill();

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cellclick();
        });

        loadTable();
    }

    // load table
    private void loadTable() {
        view.model.setRowCount(0);
        List<Discipline> list = dao.findAll();

        for (Discipline d : list) {
            view.model.addRow(new Object[]{
                    d.getIdkyluat(),
                    d.getStudentId(),
                    d.getStudentName(),
                    d.getHinhThuc(),
                    sdf.format(d.getKyluatDate()),
                    sdf.format(d.getNgayKetThuc()),
                    d.getSoQuyetDinh(),
                    d.getLyDo()
            });
        }
    }

    // load cbo
    private void loadStudentCombo() {
        view.cboMaSinhVien.removeAllItems();
        view.cboMaSinhVien.addItem("");

        for (String id : dao.getAllStudentIds()) {
            view.cboMaSinhVien.addItem(id);
        }

        view.cboMaSinhVien.setEditable(true);
    }

    // ===== auto fill tên SV =====
    private void initStudentNameAutoFill() {
        view.cboMaSinhVien.addActionListener(e -> fillStudentName());

        JTextField editor =
                (JTextField) view.cboMaSinhVien.getEditor().getEditorComponent();

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
        if (!view.cboMaSinhVien.isEnabled()) return;

        String msv = getStudentIdFromCombo();
        if (msv.isEmpty()) {
            view.txtTenSinhVien.setText("");
            return;
        }

        view.txtTenSinhVien.setText(dao.findStudentNameById(msv));
    }

    // cac su kiem
    private void initActions(MainFrame mainFrame) {

        view.btnThem.addActionListener(e -> {
            try {
                Discipline dis = new Discipline(
                        0,
                        getStudentIdFromCombo(),
                        getHinhThucFromCombo(),
                        emptyToNull(view.txtSoQuyetDinh.getText()),
                        getSqlDate(view.dateNgayKyLuat),
                        getSqlDate(view.dateNgayKetThuc),
                        view.txtLyDo.getText()
                );

                dao.insert(dis);
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
            if (row == -1) return;

            int id = Integer.parseInt(view.model.getValueAt(row, 0).toString());

            if (JOptionPane.showConfirmDialog(view, "Xác nhận xóa kỉ luật?") == JOptionPane.YES_OPTION) {
                try {
                    int result = dao.delete(new Discipline(id, null, null, null, null, null, null));
                    if (result > 0) {
                        JOptionPane.showMessageDialog(view, "Xóa thành công");
                        loadTable();
                        clearForm();
                    } else {
                        JOptionPane.showMessageDialog(view, "Xóa thất bại");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        view.btnSua.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row == -1) return;
            int id = Integer.parseInt(view.model.getValueAt(row, 0).toString());

            if (JOptionPane.showConfirmDialog(view, "Xác nhận cập nhật thông tin kỉ luật?") == JOptionPane.YES_OPTION) {
                try {
                    int result = dao.update(new Discipline(
                            id,
                            getStudentIdFromCombo(),
                            getHinhThucFromCombo(),
                            emptyToNull(view.txtSoQuyetDinh.getText()),
                            getSqlDate(view.dateNgayKyLuat),
                            getSqlDate(view.dateNgayKetThuc),
                            view.txtLyDo.getText()
                    ));
                    if (result > 0) {
                        JOptionPane.showMessageDialog(view, "Cập nhật thành công");
                        loadTable();
                        clearForm();
                    } else {
                        JOptionPane.showMessageDialog(view, "Cập nhật thất bại");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        view.btnQuayLai.addActionListener(e -> mainFrame.showView("HOME"));

        view.btnExport.addActionListener(e -> onExport());
    }

    // cellclick
    private void cellclick() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.cboMaSinhVien.setSelectedItem(view.model.getValueAt(row, 1));
        view.txtTenSinhVien.setText(view.model.getValueAt(row, 2).toString());
        view.cboHinhThuc.setSelectedItem(view.model.getValueAt(row, 3));

        Object dobObj = view.model.getValueAt(row, 4);
        if (dobObj != null && !dobObj.toString().isEmpty()) {
            try {
                java.util.Date dob = new SimpleDateFormat("dd/MM/yyyy").parse(dobObj.toString());
                view.dateNgayKyLuat.setDate(dob);
            } catch (ParseException ex) {
                view.dateNgayKyLuat.setDate(null);
                ex.printStackTrace();
            }
        } else {
            view.dateNgayKyLuat.setDate(null);
        }
        view.dateNgayKyLuat.setDateFormatString("dd/MM/yyyy");

        Object kt = view.model.getValueAt(row, 5);
        if (kt != null && !kt.toString().isEmpty()) {
            try {
                java.util.Date dob = new SimpleDateFormat("dd/MM/yyyy").parse(kt.toString());
                view.dateNgayKetThuc.setDate(dob);
            } catch (ParseException ex) {
                view.dateNgayKetThuc.setDate(null);
                ex.printStackTrace();
            }
        } else {
            view.dateNgayKetThuc.setDate(null);
        }
        view.dateNgayKetThuc.setDateFormatString("dd/MM/yyyy");

        view.txtSoQuyetDinh.setText(view.model.getValueAt(row, 6).toString());
        view.txtLyDo.setText(view.model.getValueAt(row, 7).toString());

        // khoa 3 truong dl
        view.cboMaSinhVien.setEnabled(false);
        view.txtTenSinhVien.setEditable(false);
    }

    private void clearForm() {
        view.cboMaSinhVien.setEnabled(true);
        view.cboMaSinhVien.setSelectedItem("");
        view.txtTenSinhVien.setText("");
        view.cboHinhThuc.setSelectedIndex(0);
        view.txtSoQuyetDinh.setText("");
        view.txtLyDo.setText("");
        view.dateNgayKyLuat.setDate(null);
        view.dateNgayKetThuc.setDate(null);
    }

    // ===== utils =====
    private String getStudentIdFromCombo() {
        Object o = view.cboMaSinhVien.getEditor().getItem();
        return o == null ? "" : o.toString().trim();
    }

    private String getHinhThucFromCombo() {
        Object o = view.cboHinhThuc.getSelectedItem();
        return o == null ? "" : o.toString();
    }

    private Date getSqlDate(com.toedter.calendar.JDateChooser c) {
        return c.getDate() == null ? null : new Date(c.getDate().getTime());
    }

    private String emptyToNull(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }

    private void onExport() {
        List<Discipline> disciplineList = dao.findAll();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("Danh_sach_ki_luat.xlsx")); //
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            java.io.File filePath = fileChooser.getSelectedFile();
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Danh sách kỉ luật");

                // === TITLE ===
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("DANH SÁCH KỈ LUẬT");

                // Style title
                CellStyle titleStyle = workbook.createCellStyle();
                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleCell.setCellStyle(titleStyle);

                // Merge title across all columns
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

                // === HEADER ===
                Row headerRow = sheet.createRow(1);
                String[] headers = {"STT", "Mã sinh viên", "Tên sinh viên", "Hình thức",
                        "Ngày kỷ luật", "Ngày kết thúc", "Số quyết định", "Lý do"};
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
                for (Discipline dc : disciplineList) {
                    Row row = sheet.createRow(rowIndex++);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(no++);
                    cell0.setCellStyle(dataStyle);

                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(dc.getStudentId());
                    cell1.setCellStyle(dataStyle);

                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue(dc.getStudentName());
                    cell2.setCellStyle(dataStyle);

                    Cell cell3 = row.createCell(3);
                    cell3.setCellValue(dc.getHinhThuc());
                    cell3.setCellStyle(dataStyle);

                    Cell cell4 = row.createCell(4);
                    cell4.setCellValue((sdf.format(dc.getKyluatDate())));
                    cell4.setCellStyle(dataStyle);

                    Cell cell5 = row.createCell(5);
                    cell5.setCellValue((sdf.format(dc.getNgayKetThuc())));
                    cell5.setCellStyle(dataStyle);

                    Cell cell6 = row.createCell(6);
                    cell6.setCellValue(dc.getSoQuyetDinh());
                    cell6.setCellStyle(dataStyle);

                    Cell cell7 = row.createCell(7);
                    cell7.setCellValue(dc.getLyDo());
                    cell7.setCellStyle(dataStyle);
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
