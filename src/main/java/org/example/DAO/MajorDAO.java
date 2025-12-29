package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Major;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MajorDAO {

    public List<Major> getAll() {
        List<Major> list = new ArrayList<>();
        String sql = "SELECT major_id, major_name, facu_id FROM major";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Major(
                        rs.getString("major_id"),
                        rs.getString("major_name"),
                        rs.getString("facu_id")
                ));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return list;
    }

    public List<Major> getByFacu(String facuId) {
        List<Major> list = new ArrayList<>();
        String sql = "SELECT major_id, major_name, facu_id FROM major WHERE facu_id=?";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, facuId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Major(
                        rs.getString("major_id"),
                        rs.getString("major_name"),
                        rs.getString("facu_id")
                ));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return list;
    }

    public boolean insert(Major major) {
        String sql = "INSERT INTO major(major_id, major_name, facu_id) VALUES(?,?,?)";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, major.getMajorId());
            ps.setString(2, major.getMajorName());
            ps.setString(3, major.getFacuId());

            return ps.executeUpdate() > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public boolean update(Major major) {
        String sql = "UPDATE major SET major_name=?, facu_id=? WHERE major_id=?";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, major.getMajorName());
            ps.setString(2, major.getFacuId());
            ps.setString(3, major.getMajorId());

            return ps.executeUpdate() > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public boolean delete(String majorId) {
        String sql = "DELETE FROM major WHERE major_id=?";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, majorId);
            return ps.executeUpdate() > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public Major findById(String majorId) {
        String sql = "SELECT major_id, major_name, facu_id FROM major WHERE major_id=?";
        Major major = null;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, majorId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                major = new Major(
                        rs.getString("major_id"),
                        rs.getString("major_name"),
                        rs.getString("facu_id")
                );
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return major;
    }
}
