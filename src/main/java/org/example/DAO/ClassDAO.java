package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.ClassInfo;
import org.example.Model.Faculties;
import org.example.Model.Major;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO {

    public List<Faculties> getAllFaculties() {
        List<Faculties> list = new ArrayList<>();
        String sql = "SELECT facu_id, facu_name FROM faculties";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Faculties(
                        rs.getString("facu_id"),
                        rs.getString("facu_name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Major> getMajorByFaculty(String facuId) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<ClassInfo> getAllClass() {
        List<ClassInfo> list = new ArrayList<>();

        String sql = """
                SELECT c.class_id, c.class_name,
                       f.facu_id, f.facu_name,
                       m.major_id, m.major_name
                FROM class c
                JOIN faculties f ON f.facu_id = c.facu_id
                JOIN major m ON m.major_id = c.major_id
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new ClassInfo(
                        rs.getString("class_id"),
                        rs.getString("class_name"),
                        rs.getString("facu_id"),
                        rs.getString("major_id"),
                        rs.getString("facu_name"),
                        rs.getString("major_name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(ClassInfo c) {
        String sql = "INSERT INTO class(class_id, class_name, facu_id, major_id) VALUES (?,?,?,?)";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getClassId());
            ps.setString(2, c.getClassName());
            ps.setString(3, c.getFacuId());
            ps.setString(4, c.getMajorId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean update(ClassInfo c) {
        String sql = """
                UPDATE class
                SET class_name=?,
                    facu_id=?,
                    major_id=?
                WHERE class_id=?
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getClassName());
            ps.setString(2, c.getFacuId());
            ps.setString(3, c.getMajorId());
            ps.setString(4, c.getClassId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String classId) {
        String sql = "DELETE FROM class WHERE class_id=?";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, classId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ClassInfo> search(String id, String name) {
        List<ClassInfo> list = new ArrayList<>();

        String sql = """
                SELECT c.class_id, c.class_name,
                       f.facu_id, f.facu_name,
                       m.major_id, m.major_name
                FROM class c
                JOIN faculties f ON f.facu_id = c.facu_id
                JOIN major m ON m.major_id = c.major_id
                WHERE (?='' OR c.class_id LIKE ?)
                  AND (?='' OR c.class_name LIKE ?)
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, "%" + id + "%");
            ps.setString(3, name);
            ps.setString(4, "%" + name + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ClassInfo(
                        rs.getString("class_id"),
                        rs.getString("class_name"),
                        rs.getString("facu_id"),
                        rs.getString("major_id"),
                        rs.getString("facu_name"),
                        rs.getString("major_name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
