//package org.example.Controller;
//
//import org.example.DAO.FacuDAO;
//import org.example.DAO.StudentDAO;
//import org.example.Model.Faculties;
//import org.example.Model.Student;
//import org.example.View.MainFrame;
//import org.example.View.StudentView;
//
//import javax.swing.*;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//
//public class StudentController {
//    private final StudentView view;
//    private final StudentDAO dao;
//
//    public StudentController(StudentView view, MainFrame mainFrame, StudentDAO dao) {
//        this.view = view;
//        this.dao = dao;
//        initAction();
//        loadTable();
//
//        view.table.getSelectionModel().addListSelectionListener(e -> {
//            if (!e.getValueIsAdjusting()) onRowSelected();
//        });
//    }
//
//    private void initAction() {
//        view.btnAdd.addActionListener(e -> {
//            try {
//                // Validate dữ liệu trước khi thêm
//                if (view.sfp.txtId.getText().trim().isEmpty() ||
//                        view.sfp.txtLastName.getText().trim().isEmpty() ||
//                        view.sfp.txtFirstName.getText().trim().isEmpty()) {
//                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin bắt buộc!");
//                    return;
//                }
//
//                // DEBUG: In ra giá trị để kiểm tra
//                System.out.println("cboFacu index: " + view.sfp.cboFacu.getSelectedIndex());
//                System.out.println("cboMajor index: " + view.sfp.cboMajor.getSelectedIndex());
//                System.out.println("cboClass index: " + view.sfp.cboClass.getSelectedIndex());
//                System.out.println("currentClassList: " + view.sfp.currentClassList);
//                System.out.println("currentMajorList: " + view.sfp.currentMajorList);
//                System.out.println("currentFacuList: " + view.sfp.currentFacuList);
//
//                // VALIDATION CHO COMBO BOX
//                if (view.sfp.cboFacu.getSelectedIndex() == -1) {
//                    JOptionPane.showMessageDialog(view, "Vui lòng chọn Khoa/Viện!");
//                    return;
//                }
//
//                if (view.sfp.cboMajor.getSelectedIndex() == -1) {
//                    JOptionPane.showMessageDialog(view, "Vui lòng chọn Ngành!");
//                    return;
//                }
//
//                if (view.sfp.cboClass.getSelectedIndex() == -1) {
//                    JOptionPane.showMessageDialog(view, "Vui lòng chọn Lớp!");
//                    return;
//                }
//
//                // THÊM NULL CHECK CHO CURRENT LISTS
//                if (view.sfp.currentClassList == null || view.sfp.currentClassList.isEmpty()) {
//                    JOptionPane.showMessageDialog(view, "Lỗi: Danh sách lớp chưa được load!");
//                    return;
//                }
//
//                if (view.sfp.currentMajorList == null || view.sfp.currentMajorList.isEmpty()) {
//                    JOptionPane.showMessageDialog(view, "Lỗi: Danh sách ngành chưa được load!");
//                    return;
//                }
//
//                if (view.sfp.currentFacuList == null || view.sfp.currentFacuList.isEmpty()) {
//                    JOptionPane.showMessageDialog(view, "Lỗi: Danh sách khoa chưa được load!");
//                    return;
//                }
//
//                // Lấy các ID từ combo box
//                String classId = view.sfp.currentClassList.get(view.sfp.cboClass.getSelectedIndex()).getClassId();
//                String majorId = view.sfp.currentMajorList.get(view.sfp.cboMajor.getSelectedIndex()).getMajorId();
//                String facuId = view.sfp.currentFacuList.get(view.sfp.cboFacu.getSelectedIndex()).getFacuId();
//
//                // DEBUG: In ra các ID
//                System.out.println("classId: " + classId);
//                System.out.println("majorId: " + majorId);
//                System.out.println("facuId: " + facuId);
//
//                // Tạo đối tượng Student
//                Student student = new Student(
//                        view.sfp.txtId.getText().trim(),
//                        view.sfp.txtLastName.getText().trim(),
//                        view.sfp.txtFirstName.getText().trim(),
//                        new java.sql.Date(view.sfp.txtDob.getDate().getTime()),
//                        view.sfp.cboGender.getSelectedItem().toString(),
//                        view.sfp.txtEmail.getText().trim(),
//                        view.sfp.txtHometown.getText().trim(),
//                        view.sfp.txtPhone.getText().trim(),
//                        view.sfp.txtIdNo.getText().trim(),
//                        classId,
//                        majorId,
//                        facuId,
//                        view.sfp.cboStatus.getSelectedItem().toString()
//                );
//
//                // Gọi DAO để insert
//                dao.insert(student);
//                JOptionPane.showMessageDialog(view, "Thêm sinh viên thành công!");
//                loadTable();
//                clearForm();
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(view, "Thêm thất bại: " + ex.getMessage());
//            }
//        });
//        view.btnEdit.addActionListener(e -> {
//            try {
//                // Kiểm tra xem có chọn dòng nào không
//                int selectedRow = view.table.getSelectedRow();
//                if (selectedRow == -1) {
//                    JOptionPane.showMessageDialog(view, "Vui lòng chọn sinh viên cần sửa!");
//                    return;
//                }
//
//                // Validate dữ liệu
//                if (view.sfp.txtId.getText().trim().isEmpty() ||
//                        view.sfp.txtLastName.getText().trim().isEmpty() ||
//                        view.sfp.txtFirstName.getText().trim().isEmpty()) {
//                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin bắt buộc!");
//                    return;
//                }
//
//                // Validate combo box
//                if (view.sfp.cboFacu.getSelectedIndex() == -1) {
//                    JOptionPane.showMessageDialog(view, "Vui lòng chọn Khoa/Viện!");
//                    return;
//                }
//
//                if (view.sfp.cboMajor.getSelectedIndex() == -1) {
//                    JOptionPane.showMessageDialog(view, "Vui lòng chọn Ngành!");
//                    return;
//                }
//
//                if (view.sfp.cboClass.getSelectedIndex() == -1) {
//                    JOptionPane.showMessageDialog(view, "Vui lòng chọn Lớp!");
//                    return;
//                }
//
//                // Kiểm tra null cho current lists
//                if (view.sfp.currentClassList == null || view.sfp.currentClassList.isEmpty()) {
//                    JOptionPane.showMessageDialog(view, "Lỗi: Danh sách lớp chưa được load!");
//                    return;
//                }
//
//                if (view.sfp.currentMajorList == null || view.sfp.currentMajorList.isEmpty()) {
//                    JOptionPane.showMessageDialog(view, "Lỗi: Danh sách ngành chưa được load!");
//                    return;
//                }
//
//                if (view.sfp.currentFacuList == null || view.sfp.currentFacuList.isEmpty()) {
//                    JOptionPane.showMessageDialog(view, "Lỗi: Danh sách khoa chưa được load!");
//                    return;
//                }
//
//                // Lấy các ID từ combo box
//                String classId = view.sfp.currentClassList.get(view.sfp.cboClass.getSelectedIndex()).getClassId();
//                String majorId = view.sfp.currentMajorList.get(view.sfp.cboMajor.getSelectedIndex()).getMajorId();
//                String facuId = view.sfp.currentFacuList.get(view.sfp.cboFacu.getSelectedIndex()).getFacuId();
//
//                // Tạo đối tượng Student
//                Student student = new Student(
//                        view.sfp.txtId.getText().trim(),
//                        view.sfp.txtLastName.getText().trim(),
//                        view.sfp.txtFirstName.getText().trim(),
//                        new java.sql.Date(view.sfp.txtDob.getDate().getTime()),
//                        view.sfp.cboGender.getSelectedItem().toString(),
//                        view.sfp.txtEmail.getText().trim(),
//                        view.sfp.txtHometown.getText().trim(),
//                        view.sfp.txtPhone.getText().trim(),
//                        view.sfp.txtIdNo.getText().trim(),
//                        classId,
//                        majorId,
//                        facuId,
//                        view.sfp.cboStatus.getSelectedItem().toString()
//                );
//
//                // Gọi DAO để update
//                dao.update(student);
//                JOptionPane.showMessageDialog(view, "Cập nhật sinh viên thành công!");
//                loadTable();
//                clearForm();
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(view, "Cập nhật thất bại: " + ex.getMessage());
//            }
//        });
//        view.btnDelete.addActionListener(e -> {
//            try {
//                // Kiểm tra xem có chọn dòng nào không
//                int selectedRow = view.table.getSelectedRow();
//                if (selectedRow == -1) {
//                    JOptionPane.showMessageDialog(view, "Vui lòng chọn sinh viên cần xóa!");
//                    return;
//                }
//
//                // Lấy mã sinh viên từ dòng được chọn
//                String studentId = view.sfp.txtId.getText().trim();
//
//                // Xác nhận trước khi xóa
//                int confirm = JOptionPane.showConfirmDialog(
//                        view,
//                        "Bạn có chắc chắn muốn xóa sinh viên " + studentId + "?",
//                        "Xác nhận xóa",
//                        JOptionPane.YES_NO_OPTION,
//                        JOptionPane.WARNING_MESSAGE
//                );
//
//                if (confirm == JOptionPane.YES_OPTION) {
//                    // Gọi DAO để xóa
//                    dao.delete(studentId);
//                    JOptionPane.showMessageDialog(view, "Xóa sinh viên thành công!");
//                    loadTable();
//                    clearForm();
//                }
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(view, "Xóa thất bại: " + ex.getMessage());
//            }
//        });
//    }
//
//    private void loadTable() {
//        view.model.setRowCount(0);
//        try {
//            for (Student st : dao.getAllStudents()) {
//                view.model.addRow(new Object[]{
//                        st.getStudentId(),
//                        st.getLastName(),
//                        st.getFirstName(),
//                        new SimpleDateFormat("dd/MM/yyyy").format(st.getDob()),
//                        st.getGender(),
//                        st.getClassName(),      // Thay vì getClassId()
//                        st.getCohort(),         // Thay vì getMajorId()
//                        st.getMajorName(),      // Thay vì getFacuId()
//                        st.getFacuName(),       // Thêm cột Khoa
//                        st.getStatus(),
//                        st.getHometown(),
//                        st.getPhone(),
//                        st.getEmail(),
//                        st.getIdNo()
//                });
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void onRowSelected() {
//        int row = view.table.getSelectedRow();
//        if (row < 0) return;
//
//        // ===== Nhập thông tin cá nhân =====
//        view.sfp.txtId.setText(String.valueOf(view.model.getValueAt(row, 0)));
//        view.sfp.txtLastName.setText(String.valueOf(view.model.getValueAt(row, 1)));
//        view.sfp.txtFirstName.setText(String.valueOf(view.model.getValueAt(row, 2)));
//
//        try {
//            String dobStr = String.valueOf(view.model.getValueAt(row, 3));
//            view.sfp.txtDob.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(dobStr));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        view.sfp.cboGender.setSelectedItem(String.valueOf(view.model.getValueAt(row, 4)));
//        view.sfp.txtHometown.setText(String.valueOf(view.model.getValueAt(row, 10)));
//        view.sfp.txtPhone.setText(String.valueOf(view.model.getValueAt(row, 11)));
//        view.sfp.txtEmail.setText(String.valueOf(view.model.getValueAt(row, 12)));
//        view.sfp.txtIdNo.setText(String.valueOf(view.model.getValueAt(row, 13)));
//
//        // ===== Nhập thông tin học tập =====
//        String className = String.valueOf(view.model.getValueAt(row, 5));
//        String facuName = String.valueOf(view.model.getValueAt(row, 8));
//        String majorName = String.valueOf(view.model.getValueAt(row, 7));
//
////        // Chọn Khoa theo tên
////        for (int i = 0; i < view.sfp.currentFacuList.size(); i++) {
////            if (view.sfp.currentFacuList.get(i).getFacuName().equals(facuName)) {
////                view.sfp.cboFacu.setSelectedIndex(i);
////                String facuId = view.sfp.currentFacuList.get(i).getFacuId();
////                view.sfp.loadMajorCombo(facuId);
////                break;
////            }
////        }
////
////        // Chọn Ngành theo tên
////        for (int i = 0; i < view.sfp.currentMajorList.size(); i++) {
////            if (view.sfp.currentMajorList.get(i).getMajorName().equals(majorName)) {
////                view.sfp.cboMajor.setSelectedIndex(i);
////                String majorId = view.sfp.currentMajorList.get(i).getMajorId();
////                view.sfp.loadClassCombo(majorId);
////                break;
////            }
////        }
//
//        // Chọn Lớp theo tên
//        for (int i = 0; i < view.sfp.currentClassList.size(); i++) {
//            if (view.sfp.currentClassList.get(i).getClassName().equals(className)) {
//                view.sfp.cboClass.setSelectedIndex(i);
//                break;
//            }
//        }
//
//        // Trạng thái
//        view.sfp.cboStatus.setSelectedItem(String.valueOf(view.model.getValueAt(row, 9)));
//    }
//
//    private void clearForm() {
//        view.sfp.txtId.setText("");
//        view.sfp.txtLastName.setText("");
//        view.sfp.txtFirstName.setText("");
//        view.sfp.txtDob.setDate(new java.util.Date());
//        view.sfp.cboGender.setSelectedIndex(0);
//        view.sfp.txtHometown.setText("");
//        view.sfp.txtPhone.setText("");
//        view.sfp.txtEmail.setText("");
//        view.sfp.txtIdNo.setText("");
//        view.sfp.cboFacu.setSelectedIndex(-1);
//        view.sfp.cboMajor.removeAllItems();
//        view.sfp.cboClass.removeAllItems();
//        view.sfp.cboStatus.setSelectedIndex(0);
//        view.table.clearSelection();
//    }
//}
