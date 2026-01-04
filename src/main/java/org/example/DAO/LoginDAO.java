package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public LoginDAO() {
    }

    public Account login(String username, String password) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "select username, fullname from account where username = ? and password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(rs.getString("username"), null, rs.getString("fullname"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
