package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Faculties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacuDAO {
    public List<Faculties> findAll() {
        List<Faculties> list = new ArrayList<>();
        String sql = "SELECT * FROM faculties";

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Faculties(
                        rs.getString("facu_id"),
                        rs.getString("facu_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Faculties facu) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "insert into faculties (facu_id, facu_name) values (?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, facu.getFacuId());
        ps.setString(2, facu.getFacuName());
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public int delete(Faculties facu) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "DELETE FROM faculties WHERE facu_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, facu.getFacuId());
        int row = ps.executeUpdate(); // trả về số row bị ảnh hưởng
        ps.close();
        conn.close();
        return row;
    }

    public int update(Faculties facu) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "UPDATE faculties SET facu_name = ? WHERE facu_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, facu.getFacuName()); // giá trị mới
        ps.setString(2, facu.getFacuId());   // WHERE id
        int row = ps.executeUpdate();         // trả về số row bị ảnh hưởng
        ps.close();
        conn.close();
        return row;
    }

}
