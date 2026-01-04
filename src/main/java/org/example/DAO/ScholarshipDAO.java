package org.example.DAO;


import org.example.Config.dbConn;
import org.example.Model.Scholarship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScholarshipDAO {
    // ===== LOAD TABLE =====
    public List<Scholarship> findAll() {
        List<Scholarship> list = new ArrayList<>();

        String sql =
                """
                SELECT scholarship_id, st.student_id,
                       CONCAT(st.student_lastName, ' ', st.student_firstName) AS student_name,
                       score_level, drl_level, scholarship_level, semester 
                FROM scholarship ss
                JOIN student st on st.student_id = ss.student_id
                ORDER BY scholarship_id
                        """;

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Scholarship s = new Scholarship();
                s.setScholarshipId(rs.getString("scholarship_id"));
                s.setStudentId(rs.getString("student_id"));
                s.setStudentName(rs.getString("student_name"));;
                s.setScoreLevel(rs.getString("score_level"));
                s.setDrlLevel(rs.getString("drl_level"));
                s.setScholarshipLevel(rs.getString("scholarship_level"));
                s.setSemester(rs.getString("semester"));

                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== TÌM KIẾM =====
    public List<Scholarship> search(String studentId, String semester, String scholarshipLevel) {
        List<Scholarship> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT scholarship_id, student_id, score_level, drl_level, scholarship_level, semester " +
                        "FROM scholarship WHERE 1=1 "
        );

        List<String> params = new ArrayList<>();

        if (studentId != null && !studentId.trim().isEmpty()) {
            sql.append(" AND student_id LIKE ? ");
            params.add("%" + studentId.trim() + "%");
        }

        if (semester != null && !semester.trim().isEmpty()) {
            sql.append(" AND semester LIKE ? ");
            params.add("%" + semester.trim() + "%");
        }

        if (scholarshipLevel != null && !scholarshipLevel.trim().isEmpty()) {
            sql.append(" AND scholarship_level LIKE ? ");
            params.add("%" + scholarshipLevel.trim() + "%");
        }

        sql.append(" ORDER BY scholarship_id");

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Scholarship s = new Scholarship();
                    s.setScholarshipId(rs.getString("scholarship_id"));
                    s.setStudentId(rs.getString("student_id"));
                    s.setScoreLevel(rs.getString("score_level"));
                    s.setDrlLevel(rs.getString("drl_level"));
                    s.setScholarshipLevel(rs.getString("scholarship_level"));
                    s.setSemester(rs.getString("semester"));

                    list.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== LOAD STUDENT_ID (đổ combobox) =====
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

    // ===== THÊM =====
    public void insert(Scholarship s) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql =
                "INSERT INTO scholarship (student_id, score_level, drl_level, scholarship_level, semester) " +
                        "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, s.getStudentId());
        ps.setString(2, s.getScoreLevel());
        ps.setString(3, s.getDrlLevel());
        ps.setString(4, s.getScholarshipLevel());
        ps.setString(5, s.getSemester());

        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    // ===== XÓA =====
    public int delete(String scholarshipId) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "DELETE FROM scholarship WHERE scholarship_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, scholarshipId);

        int row = ps.executeUpdate();
        ps.close();
        conn.close();
        return row;
    }

    // ===== SỬA =====
    public int update(Scholarship s) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql =
                "UPDATE scholarship SET student_id = ?, score_level = ?, drl_level = ?, " +
                        "scholarship_level = ?, semester = ? WHERE scholarship_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, s.getStudentId());
        ps.setString(2, s.getScoreLevel());
        ps.setString(3, s.getDrlLevel());
        ps.setString(4, s.getScholarshipLevel());
        ps.setString(5, s.getSemester());
        ps.setString(6, s.getScholarshipId());

        int row = ps.executeUpdate();
        ps.close();
        conn.close();
        return row;
    }
}
