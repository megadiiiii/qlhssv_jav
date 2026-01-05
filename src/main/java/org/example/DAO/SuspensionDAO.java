package org.example.DAO;
import org.example.Config.dbConn;
import org.example.Model.Suspension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SuspensionDAO {
    public List<Suspension> findAllSus() {
        List<Suspension> list = new ArrayList<>();

        String sql = """
                SELECT *
                FROM suspension
                ORDER BY startdate DESC
                """;

        try {
            Connection conn = dbConn.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Suspension s = new Suspension(
                        rs.getString("suspension_id"),
                        rs.getString("student_id"),
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
                (suspension_id,student_id, startdate, enddate, reason, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSuspensionId());
            ps.setString(2, s.getStudentId());
            ps.setDate(3, s.getStartDate());
            ps.setDate(4, s.getEndDate());
            ps.setString(5, s.getReason());
            ps.setString(6, s.getStatus());

            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // ===== UPDATE STATUS =====
    public int updateStatus(String suspensionId ,String status) {
        String sql = """
                UPDATE suspension
                SET status = ?
                WHERE suspension_id = ?
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, suspensionId);

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
    public List<Suspension> search(String studentId, String status) {
        List<Suspension> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
                SELECT *
                FROM suspension
                WHERE 1 = 1
                """);

        List<Object> params = new ArrayList<>();

        if (studentId != null && !studentId.isBlank()) {
            sql.append(" AND student_id LIKE ?");
            params.add("%" + studentId + "%");
        }

        if (status != null) {
            sql.append(" AND status = ?");
            params.add(status);
        }

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Suspension s = new Suspension(
                        rs.getString("suspension_id"),
                        rs.getString("student_id"),
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
}
