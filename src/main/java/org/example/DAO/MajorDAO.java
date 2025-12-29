package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Major;
import java.sql.*;
import java.util.*;

public class MajorDAO {
    public List<Major> findByFaculty(String facuId) throws SQLException {
        List<Major> list = new ArrayList<>();
        String sql = "SELECT major_id, major_name FROM major WHERE facu_id = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, facuId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Major(rs.getString("major_id"), rs.getString("major_name"), facuId));
                }
            }
        }
        return list;
    }
}
