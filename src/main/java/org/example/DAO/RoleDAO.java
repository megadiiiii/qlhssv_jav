package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Role;
import org.example.Model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    /* ================= FIND ALL ================= */
    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();

        String sql = """
                SELECT r.role_id, r.student_role,
                       s.student_id, s.first_name, s.last_name,
                       c.class_id, c.class_name
                FROM role r
                JOIN student s ON r.student_id = s.student_id
                JOIN class c ON s.class_id = c.class_id
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Student st = new Student();
                st.setStudentId(rs.getString("student_id"));
                st.setFirstName(rs.getString("first_name"));
                st.setLastName(rs.getString("last_name"));
                st.setClassId(rs.getString("class_id"));
                st.setClassName(rs.getString("class_name"));

                Role r = new Role();
                r.setRoleId(rs.getInt("role_id"));
                r.setStudent(st);
                r.setStudentRole(rs.getString("student_role"));

                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ================= INSERT ================= */
    public int insert(Role r) {
        String sql = """
                INSERT INTO role (student_id, student_role)
                VALUES (?, ?)
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getStudent().getStudentId());
            ps.setString(2, r.getStudentRole());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* ================= UPDATE ================= */
    public int update(Role r) {
        String sql = """
                UPDATE role
                SET student_id = ?,
                    student_role = ?
                WHERE role_id = ?
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getStudent().getStudentId());
            ps.setString(2, r.getStudentRole());
            ps.setInt(3, r.getRoleId());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* ================= DELETE ================= */
    public int delete(int roleId) {
        String sql = """
                DELETE FROM role
                WHERE role_id = ?
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roleId);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
