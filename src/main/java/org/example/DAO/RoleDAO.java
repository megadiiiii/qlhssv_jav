package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    // ===== DTO để fill tên lớp theo mã lớp =====
    public static class ClassInfo {
        public final String classId;
        public final String className;

        public ClassInfo(String classId, String className) {
            this.classId = classId;
            this.className = className;
        }
    }

    // ===== DTO để fill tên SV theo mã SV =====
    public static class StudentInfo {
        public final String studentId;
        public final String studentName;

        public StudentInfo(String studentId, String studentName) {
            this.studentId = studentId;
            this.studentName = studentName;
        }
    }

    // ===== load danh sách lớp (đổ cbo mã lớp) =====
    public List<String> getAllClassIds() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT class_id FROM class ORDER BY class_id";

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(rs.getString("class_id"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== lấy tên lớp theo mã lớp =====
    public String findClassNameById(String classId) {
        String sql = "SELECT class_name FROM class WHERE class_id = ?";

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, classId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("class_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // ===== load danh sách sinh viên theo lớp (đổ cbo mã sv) =====
    public List<String> getStudentIdsByClass(String classId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT student_id FROM student WHERE class_id = ? ORDER BY student_id";

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, classId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(rs.getString("student_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== lấy tên SV theo mã =====
    public String findStudentNameById(String studentId) {
        String sql =
                "SELECT CONCAT(student_lastName, ' ', student_firstName) AS student_name " +
                        "FROM student WHERE student_id = ?";

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("student_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // ===== loadtable theo lớp =====
    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();
        String sql = """
                SELECT r.role_id, s.student_id, 
                       CONCAT(s.student_lastName, ' ', s.student_firstName) AS student_name, 
                       c.class_id, c.class_name, r.student_role 
                FROM role r JOIN student s ON r.student_id = s.student_id 
                JOIN class c ON s.class_id = c.class_id 
                """;

        try {
            Connection conn = dbConn.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Role(
                        rs.getInt("role_id"),
                        rs.getString("student_id"),
                        rs.getString("student_name"),
                        rs.getString("class_id"),
                        rs.getString("class_name"),
                        rs.getString("student_role")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== search trong lớp =====
    public List<Role> search(String classId,String studentRole) {
        List<Role> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT r.role_id, s.student_id, " +
                        "CONCAT(s.student_lastName, ' ', s.student_firstName) AS student_name, " +
                        "c.class_id, c.class_name, " +
                        "r.student_role " +
                        "FROM role r " +
                        "JOIN student s ON r.student_id = s.student_id " +
                        "JOIN class c ON s.class_id = c.class_id " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        // bắt buộc theo lớp đang chọn
        if (classId != null && !classId.trim().isEmpty()) {
            sql.append(" AND c.class_id = ? ");
            params.add(classId.trim());
        }

        if (studentRole != null && !studentRole.trim().isEmpty()) {
            sql.append(" AND r.student_role = ? ");
            params.add(studentRole.trim());
        }

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Role(
                            rs.getInt("role_id"),
                            rs.getString("student_id"),
                            rs.getString("student_name"),
                            rs.getString("class_id"),
                            rs.getString("class_name"),
                            rs.getString("student_role")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===== them =====
    public int insert(Role r) {
        String sql = "INSERT INTO role (student_id, student_role) VALUES (?, ?)";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getStudentId());
            ps.setString(2, r.getStudentRole());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===== xoa =====
    public int delete(int roleId) {
        String sql = "DELETE FROM role WHERE role_id = ?";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===== sua =====
    public int update(Role r){
        String sql = "UPDATE role SET student_id = ?, student_role = ? WHERE role_id = ?";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getStudentId());
            ps.setString(2, r.getStudentRole());
            ps.setInt(3, r.getRoleId());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
