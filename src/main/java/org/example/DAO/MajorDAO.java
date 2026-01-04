package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Faculties;
import org.example.Model.Major;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MajorDAO {
    public List<Major> findAllMajors() {
        List<Major> majorsList = new ArrayList<>();
        String sql = """
                select m.*, f.*
                from major m
                join faculties f on m.facu_id = f.facu_id
                order by f.facu_name, m.major_id asc
                """;

        try {
            Connection conn = dbConn.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Faculties f = new Faculties(rs.getString("facu_id"), rs.getString("facu_name"));

                Major m = new Major(rs.getString("major_id"), rs.getString("major_name"), f);
                majorsList.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return majorsList;
    }

    public int insert(Major m) {

        String sql = """
                insert into major(major_id,major_name, facu_id)
                values (?,?,?)
                """;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getMajorId());
            ps.setString(2, m.getMajorName());
            ps.setString(3, m.getFaculty().getFacuId());

            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int update(Major m) {
        if (m == null || m.getFaculty() == null) {
            return 0;
        }

        String sql = """
                UPDATE major
                SET major_name = ?,
                    facu_id = ?
                WHERE major_id = ?;
                """;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getMajorName());
            ps.setString(2, m.getFaculty().getFacuId());
            ps.setString(3, m.getMajorId());

            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int delete(String majorId) {
        String sql = """
                DELETE FROM major
                WHERE major_id = ?;
                """;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, majorId);

            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public List<Major> search(Faculties facu, String majorId, String majorName) {
        List<Major> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
                SELECT m.*, f.facu_name
                FROM major m
                JOIN faculties f on m.facu_id = f.facu_id 
                WHERE 1 = 1
                """);

        List<Object> params = new ArrayList<>();

        if (facu != null && !facu.getFacuId().isBlank()) {
            sql.append(" AND m.facu_id = ?");
            params.add(facu.getFacuId());
        }

        if (majorId != null && !majorId.isBlank()) {
            sql.append(" AND m.major_id LIKE ?");
            params.add("%" + majorId + "%");
        }

        if (majorName != null && !majorName.isBlank()) {
            sql.append(" AND m.major_name LIKE ?");
            params.add("%" + majorName + "%");
        }

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Faculties f = new Faculties(
                        rs.getString("facu_id"),
                        rs.getString("facu_name")
                );

                Major m = new Major(
                        rs.getString("major_id"),
                        rs.getString("major_name"),
                        f
                );
                list.add(m);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<Major> findAllMajorsByFaculty(String facuId) {
        List<Major> list = new ArrayList<>();
        String sql = """
                SELECT m.major_id, m.major_name, f.facu_id, f.facu_name
                FROM major m LEFT JOIN faculties f ON f.facu_id = m.facu_id 
                WHERE f.facu_id = ?
                """;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, facuId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Faculties f = new Faculties(rs.getString("facu_id"), rs.getString("facu_name"));
                list.add(new Major(rs.getString("major_id"), rs.getString("major_name"), f));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
