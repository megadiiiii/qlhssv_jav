package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Reward;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RewardDAO {
    public List<Reward> findAll() {
        List<Reward> list = new ArrayList<>();
        String sql = "SELECT * FROM reward";

        try (Connection c = dbConn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Reward(
                        rs.getInt("reward_id"),
                        rs.getString("student_id"),
                        rs.getDate("reward_date"),
                        rs.getString("reward_note"),
                        rs.getString("reward_quyetdinh")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getStudentIds() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT student_id FROM student";
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

    public void insert(Reward r) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "INSERT INTO reward (student_id, reward_date, reward_note, reward_quyetdinh) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, r.getStudentId());
        ps.setDate(2, r.getRewardDate());
        ps.setString(3, r.getRewardNote());
        ps.setString(4, r.getRewardQuyetDinh());
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    public int delete(Reward r) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "DELETE FROM reward WHERE reward_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, r.getRewardId());
        int row = ps.executeUpdate();
        ps.close();
        conn.close();
        return row;
    }
    public int update(Reward r) throws SQLException {
        Connection conn = dbConn.getConnection();
        String sql = "UPDATE reward SET student_id = ?, reward_date = ?, reward_note = ?, reward_quyetdinh = ? WHERE reward_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, r.getStudentId());
        ps.setDate(2, r.getRewardDate());
        ps.setString(3, r.getRewardNote());
        ps.setString(4, r.getRewardQuyetDinh());
        ps.setInt(5, r.getRewardId());
        int row = ps.executeUpdate();

        ps.close();
        conn.close();
        return row;
    }
}
