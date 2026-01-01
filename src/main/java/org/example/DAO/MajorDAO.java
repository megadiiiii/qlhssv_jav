package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Major;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MajorDAO {
    public List<Major> findAllWithFaculty() {
        List<Major> list = new ArrayList<>();

        String sql = """
        select m.major_id, m.major_name, m.facu_id, f.facu_name
        from major m
        join faculties f on f.facu_id = m.facu_id
    """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Major m = new Major();
                m.setMajorId(rs.getString("major_id"));
                m.setMajorName(rs.getString("major_name"));
                m.setFacuId(rs.getString("facu_id"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public void insert(Major major) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "insert into major (major_id, major_name,facu_id) values (?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, major.getMajorId());
        ps.setString(2, major.getMajorName());
        ps.setString(3, major.getFacuId());
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public int delete(Major major) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "DELETE FROM major WHERE major_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, major.getMajorId());
        int row = ps.executeUpdate(); // trả về số row bị ảnh hưởng
        ps.close();
        conn.close();
        return row;
    }

    public int update(Major major) throws SQLException {
        Connection conn = dbConn.getConnection();

        String sql = """
        UPDATE major
        SET major_name = ?, facu_id = ?
        WHERE major_id = ?
    """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, major.getMajorName());
        ps.setString(2, major.getFacuId());
        ps.setString(3, major.getMajorId());

        int row = ps.executeUpdate();
        ps.close();
        conn.close();
        return row;
    }


}