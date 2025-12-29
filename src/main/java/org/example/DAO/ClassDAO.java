package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.ClassInfo;
import java.sql.*;
import java.util.*;

public class ClassDAO {
    public List<ClassInfo> findByMajor(String majorId) throws SQLException {
        List<ClassInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM class WHERE major_id = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, majorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ClassInfo(rs.getString("class_id"), rs.getString("class_name"), rs.getString("major_id"), rs.getInt("cohort")));
                }
            }
        }
        return list;
    }
}
