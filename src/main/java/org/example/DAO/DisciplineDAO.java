package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Discipline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplineDAO {

    // loadtable
    public List<Discipline> findAll() {
        List<Discipline> list = new ArrayList<>();

        String sql =
                "SELECT k.idkyluat, k.student_id, " +
                        "CONCAT(s.student_lastName, ' ', s.student_firstName) AS student_name, " +
                        "k.hinhThuc, k.soQuyetDinh, k.kyluat_date, k.ngayKetThuc, k.lyDo " +
                        "FROM kyluat k " +
                        "LEFT JOIN student s ON k.student_id = s.student_id";
        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Discipline(
                        rs.getInt("idkyluat"),
                        rs.getString("student_id"),
                        rs.getString("student_name"),
                        rs.getString("hinhThuc"),
                        rs.getString("soQuyetDinh"),
                        rs.getDate("kyluat_date"),
                        rs.getDate("ngayKetThuc"),
                        rs.getString("lyDo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    //load msv
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

    // tensv theo msv
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


    public void insert(Discipline d) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql =
                "INSERT INTO kyluat (student_id, hinhThuc, soQuyetDinh, kyluat_date, ngayKetThuc, lyDo) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, d.getStudentId());
        ps.setString(2, d.getHinhThuc());
        ps.setString(3, d.getSoQuyetDinh());
        ps.setDate(4, d.getKyluatDate());
        ps.setDate(5, d.getNgayKetThuc());
        ps.setString(6, d.getLyDo());

        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public int delete(Discipline d) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "DELETE FROM kyluat WHERE idkyluat = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, d.getIdkyluat());

        int row = ps.executeUpdate();
        ps.close();
        conn.close();
        return row;
    }

    public int update(Discipline d) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql =
                "UPDATE kyluat SET student_id = ?, hinhThuc = ?, soQuyetDinh = ?, " +
                        "kyluat_date = ?, ngayKetThuc = ?, lyDo = ? WHERE idkyluat = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, d.getStudentId());
        ps.setString(2, d.getHinhThuc());
        ps.setString(3, d.getSoQuyetDinh());
        ps.setDate(4, d.getKyluatDate());
        ps.setDate(5, d.getNgayKetThuc());
        ps.setString(6, d.getLyDo());
        ps.setInt(7, d.getIdkyluat());

        int row = ps.executeUpdate();
        ps.close();
        conn.close();
        return row;
    }
}
