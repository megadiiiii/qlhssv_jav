package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {

    // loadtable
    public List<Teacher> findAll() {
        List<Teacher> list = new ArrayList<>();

        String sql =
                "SELECT t.teacher_id, t.teacher_name, t.facu_id, f.facu_name " +
                        "FROM teacher t " +
                        "LEFT JOIN faculties f ON t.facu_id = f.facu_id " +
                        "ORDER BY t.teacher_id";

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("teacher_name"),
                        rs.getString("facu_id"),
                        rs.getString("facu_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Timkiem
    public List<Teacher> search(String teacherId, String teacherName, String facuId) {
        List<Teacher> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT t.teacher_id, t.teacher_name, t.facu_id, f.facu_name " +
                        "FROM teacher t " +
                        "LEFT JOIN faculties f ON t.facu_id = f.facu_id " +
                        "WHERE 1=1 "
        );

        List<String> params = new ArrayList<>();

        if (teacherId != null && !teacherId.trim().isEmpty()) {
            sql.append(" AND t.teacher_id LIKE ? ");
            params.add("%" + teacherId.trim() + "%");
        }

        if (teacherName != null && !teacherName.trim().isEmpty()) {
            sql.append(" AND t.teacher_name LIKE ? ");
            params.add("%" + teacherName.trim() + "%");
        }

        if (facuId != null && !facuId.trim().isEmpty()) {
            sql.append(" AND t.facu_id LIKE ? ");
            params.add("%" + facuId.trim() + "%");
        }

        sql.append(" ORDER BY t.teacher_id");

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Teacher(
                            rs.getString("teacher_id"),
                            rs.getString("teacher_name"),
                            rs.getString("facu_id"),
                            rs.getString("facu_name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // load facu_id
    public List<String> getAllFacultyIds() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT facu_id FROM faculties ORDER BY facu_id";

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("facu_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    // facu_name theo facu_id
    public String findFacultyNameById(String facuId) {
        String sql = "SELECT facu_name FROM faculties WHERE facu_id = ?";

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, facuId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("facu_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    // them
    public void insert(Teacher t) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "INSERT INTO teacher (teacher_id, teacher_name, facu_id) VALUES (?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, t.getTeacherId());
        ps.setString(2, t.getTeacherName());
        ps.setString(3, t.getFacuId());

        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    //xoa
    public int delete(String teacherId) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "DELETE FROM teacher WHERE teacher_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, teacherId);

        int row = ps.executeUpdate();
        ps.close();
        conn.close();
        return row;
    }
    // sua
    public int update(Teacher t) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "UPDATE teacher SET teacher_name = ?, facu_id = ? WHERE teacher_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, t.getTeacherName());
        ps.setString(2, t.getFacuId());
        ps.setString(3, t.getTeacherId());

        int row = ps.executeUpdate();
        ps.close();
        conn.close();
        return row;
    }
}
