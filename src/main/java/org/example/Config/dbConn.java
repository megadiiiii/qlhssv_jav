package org.example.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConn {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/qlhssv";
        String user = "root";
        String password = "123456";
        return DriverManager.getConnection(url, user, password);
    };
}
