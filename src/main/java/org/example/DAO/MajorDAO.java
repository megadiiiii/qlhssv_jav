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
    public List<Major> findAll() {
        List<Major> list = new ArrayList<>();
        String sql = "SELECT * FROM Major";

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Major(
                        rs.getString("major_id"),
                        rs.getString("major_name"),
                        rs.getString("facu_id"),
                        rs.getString("facu_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Major major) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "insert into major (major_id, major_name,facu_id, facu_name) values (?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, major.getMajorId());
        ps.setString(2, major.getMajorName());
        ps.setString(3, major.getFacuId());
        ps.setString(4, major.getFacuName());
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
        String sql = "UPDATE major SET major_name = ? WHERE major_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, major.getMajorName());
        ps.setString(2, major.getMajorId());
        ps.setString(3, major.getFacuId());
        ps.setString(4, major.getFacuName());
        int row = ps.executeUpdate();
        ps.close();
        conn.close();
        return row;
    }

}