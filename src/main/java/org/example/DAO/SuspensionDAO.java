package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Suspension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuspensionDAO {
    public List<Suspension> findAllSus() {
        List<Suspension> list = new ArrayList<>();

        String sql = """
                SELECT  suspension_id, s.student_id, 
                      CONCAT(s.student_lastName, ' ', s.student_firstName) AS student_name,
                      startdate,enddate, reason, sp.status                
                FROM suspension sp
                JOIN student s on s.student_id = sp.student_id
                """;

        try {
            Connection conn = dbConn.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Suspension s = new Suspension(
                        rs.getInt("suspension_id"),
                        rs.getString("student_id"),
                        rs.getString("student_name"),
                        rs.getDate("startdate"),
                        rs.getDate("enddate"),
                        rs.getString("reason"),
                        rs.getString("status")
                );
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== INSERT =====
    public int insert(Suspension s) {
        String sql = """
                INSERT INTO suspension
                (student_id, startdate, enddate, reason, status)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getStudentId());
            ps.setDate(2, new java.sql.Date(s.getStartDate().getTime()));
            ps.setDate(3, new java.sql.Date(s.getEndDate().getTime()));
            ps.setString(4, s.getReason());
            ps.setString(5, s.getStatus());

            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // ===== UPDATE STATUS =====
    public int updateStatus(int suspensionId, String status) {
        String sql = """
                UPDATE suspension
                SET status = ?
                WHERE suspension_id = ?
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, suspensionId);

            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // ===== DELETE =====
    public int delete(String suspensionId) {
        String sql = """
                DELETE FROM suspension
                WHERE suspension_id = ?
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, suspensionId);
            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // ===== SEARCH =====
    public List<Suspension> search(String studentId) {
        List<Suspension> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
                SELECT  suspension_id, s.student_id, 
                      CONCAT(s.student_lastName, ' ', s.student_firstName) AS student_name,
                      startdate,enddate, reason, sp.status                
                FROM suspension sp
                JOIN student s on s.student_id = sp.student_id
                """);

        List<Object> params = new ArrayList<>();

        if (studentId != null && !studentId.isBlank()) {
            sql.append(" AND s.student_id LIKE ?");
            params.add("%" + studentId + "%");
        }

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Suspension s = new Suspension(
                        rs.getInt("suspension_id"),
                        rs.getString("student_id"),
                        rs.getString("student_name"),
                        rs.getDate("startdate"),
                        rs.getDate("enddate"),
                        rs.getString("reason"),
                        rs.getString("status")
                );
                list.add(s);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<String> getAllStudentIds() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT student_id FROM student ORDER BY student_id";
        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("student_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String findStudentNameById(String studentId) {
        String sql =
                "SELECT CONCAT(student_lastName, ' ', student_firstName) AS student_name " +
                        "FROM student WHERE student_id = ?";

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("student_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int delete(int suspensionId) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "DELETE FROM suspension WHERE suspension_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, suspensionId);

        int row = ps.executeUpdate();
        ps.close();
        conn.close();
        return row;
    }
}
