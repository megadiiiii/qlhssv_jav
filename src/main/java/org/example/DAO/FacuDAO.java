package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Faculties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FacuDAO {

    public List<Faculties> findAll() {
        List<Faculties> list = new ArrayList<>();
        String sql = "SELECT * FROM faculties";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Faculties(
                        rs.getString("facu_id"),
                        rs.getString("facu_name")
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public int insert(Faculties facu) {
        String sql = "INSERT INTO faculties (facu_id, facu_name) VALUES (?, ?)";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, facu.getFacuId());
            ps.setString(2, facu.getFacuName());

            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int update(Faculties facu) {
        String sql = "UPDATE faculties SET facu_name = ? WHERE facu_id = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, facu.getFacuName());
            ps.setString(2, facu.getFacuId());

            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int delete(Faculties facu) {
        String sql = "DELETE FROM faculties WHERE facu_id = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, facu.getFacuId());
            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}