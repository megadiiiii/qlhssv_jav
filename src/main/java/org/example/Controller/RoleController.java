package org.example.Controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DAO.RoleDAO;
import org.example.Model.Role;
import org.example.View.MainFrame;
import org.example.View.RoleView;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class RoleController {
    private final RoleView view;
    private final RoleDAO dao;

    public RoleController(RoleView view, MainFrame mainFrame, RoleDAO dao) {
        this.view = view;
        this.dao = dao;

        loadTable();
        initActions(mainFrame);
        loadClassCombo();
        initClassListener();
        initStudentListener();

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cellclick();
        });
    }

    private void initActions(MainFrame mainFrame) {
        view.btnThem.addActionListener(e -> insert());
        view.btnXoa.addActionListener(e -> delete());
        view.btnSua.addActionListener(e -> update());
        view.btnExport.addActionListener(e -> export());
        view.btnQuayLai.addActionListener(e -> {
            mainFrame.showView("HOME");
            clearInput();
        });
    }

    private void loadClassCombo() {
        view.cboMaLop.removeAllItems();

        List<String> classIds = dao.getAllClassIds();
        for (String id : classIds) {
            view.cboMaLop.addItem(id);
        }

        if (view.cboMaLop.getItemCount() > 0) {
            view.cboMaLop.setSelectedIndex(0);
        }
    }

    private void loadStudentComboByClass(String classId) {
        view.cboMaSinhVien.removeAllItems();

        List<String> ids = dao.getStudentIdsByClass(classId);
        for (String id : ids) {
            view.cboMaSinhVien.addItem(id);
        }

        if (view.cboMaSinhVien.getItemCount() > 0) {
            view.cboMaSinhVien.setSelectedIndex(0);
        }
    }

    private void initClassListener() {
        view.cboMaLop.addActionListener(e -> {
            Object o = view.cboMaLop.getSelectedItem();
            if (o == null) return;

            String classId = o.toString();

            view.txtTenLop.setText(
                    dao.findClassNameById(classId)
            );

            loadStudentComboByClass(classId);
        });
    }

    private void initStudentListener() {
        view.cboMaSinhVien.addActionListener(e -> {
            Object o = view.cboMaSinhVien.getSelectedItem();
            if (o == null) {
                view.txtTenSinhVien.setText("");
                return;
            }

            view.txtTenSinhVien.setText(
                    dao.findStudentNameById(o.toString())
            );
        });
    }


    private void insert() {
        String classId = view.cboMaLop.getSelectedItem().toString();
        String studentId = view.cboMaSinhVien.getSelectedItem().toString();
        String studentRole = view.cboVaiTro.getSelectedItem().toString();

        Role role = new Role(0, studentId, null, classId, null, studentRole);
        try {
            int result = dao.insert(role);
            if (result > 0) {
                JOptionPane.showMessageDialog(view, "Thêm thành công");
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

    private void delete() {
        int row = view.table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn chuyên ngành để xóa");
            return;
        }

        int roleId = Integer.parseInt(view.model.getValueAt(row, 0).toString());
        if (JOptionPane.showConfirmDialog(view, "Xác nhận xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int result = dao.delete(roleId);
            if (result > 0) {
                JOptionPane.showMessageDialog(view, "Xóa thành công");
                loadTable();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại");
            }
        }
    }

    private void update() {
        int row = view.table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn chuyên ngành để xóa");
            return;
        }

        int roleId = Integer.parseInt(view.model.getValueAt(row, 0).toString());
        String classId = view.cboMaLop.getSelectedItem().toString();
        String studentId = view.cboMaSinhVien.getSelectedItem().toString();
        String studentRole = view.cboVaiTro.getSelectedItem().toString();

        Role role = new Role(roleId, studentId, null, classId, null, studentRole);
        try {
            int result = dao.update(role);
            if (result > 0) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công");
                loadTable();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật: " + ex.getMessage());
        }
    }

    private void export() {
        List<Role> list = dao.findAll();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("Danh_sach_can_bo_lop.xlsx")); //
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            java.io.File filePath = fileChooser.getSelectedFile();
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Danh sách cán bộ lớp");

                // === TITLE ===
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("DANH SÁCH CÁN BỘ LỚP");

                // Style title
                CellStyle titleStyle = workbook.createCellStyle();
                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleCell.setCellStyle(titleStyle);

                // Merge title across all columns
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

                // === HEADER ===
                Row headerRow = sheet.createRow(1);
                String[] headers = {"STT", "Mã lớp", "Tên lớp", "Mã sinh viên", "Tên sinh viên", "Vai trò"};
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
                for (Role r : list) {
                    Row row = sheet.createRow(rowIndex++);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(no++);
                    cell0.setCellStyle(dataStyle);

                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(r.getClassId());
                    cell1.setCellStyle(dataStyle);

                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue(r.getClassName());
                    cell2.setCellStyle(dataStyle);

                    Cell cell3 = row.createCell(3);
                    cell3.setCellValue(r.getStudentId());
                    cell3.setCellStyle(dataStyle);

                    Cell cell4 = row.createCell(4);
                    cell4.setCellValue(r.getStudentName());
                    cell4.setCellStyle(dataStyle);

                    Cell cell5 = row.createCell(5);
                    cell5.setCellValue(r.getStudentRole());
                    cell5.setCellStyle(dataStyle);
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

    private void loadTable() {
        List<Role> list = dao.findAll();

        view.model.setRowCount(0);
        for (Role r : list) {
            view.model.addRow(new Object[]{
                    r.getRoleId(),
                    r.getClassId(),
                    r.getClassName(),
                    r.getStudentId(),
                    r.getStudentName(),
                    r.getStudentRole()
            });
        }
    }

    public void clearInput() {
        view.txtTenLop.setText("");
        view.txtTenSinhVien.setText("");
        view.cboMaLop.setSelectedIndex(0);
        view.cboMaSinhVien.setSelectedIndex(0);
        view.cboVaiTro.setSelectedIndex(0);
    }

    private void cellclick() {
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        view.cboMaLop.setSelectedItem(view.model.getValueAt(row, 1));
        view.txtTenLop.setText(view.model.getValueAt(row, 2).toString());
        view.cboMaSinhVien.setSelectedItem(view.model.getValueAt(row, 3));
        view.txtTenSinhVien.setText(view.model.getValueAt(row, 4).toString());
        view.cboVaiTro.setSelectedItem(view.model.getValueAt(row, 5));

        view.txtTenSinhVien.setEditable(false);
        view.txtTenLop.setEditable(false);
    }
}
