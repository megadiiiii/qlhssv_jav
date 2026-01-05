package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.Cohort;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CohortDAO {
    public List<Cohort> getAllCohorts() {
        List<Cohort> list = new ArrayList<>();
        String sql = "SELECT * FROM cohort order by cohort_name asc";

        try {
            Connection conn = dbConn.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Cohort(
                        rs.getInt("cohort_id"),
                        rs.getString("cohort_name"),
                        rs.getInt("cohort_start_year"),
                        rs.getInt("cohort_end_year")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Cohort> getDataExport() {
        List<Cohort> list = new ArrayList<>();
        String sql = "SELECT cohort_name, cohort_start_year, cohort_end_year FROM cohort";

        try {
            Connection conn = dbConn.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Cohort(
                        rs.getString("cohort_name"),
                        rs.getInt("cohort_start_year"),
                        rs.getInt("cohort_end_year")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insert(Cohort c) {
        String sql = """
                insert into cohort (cohort_name, cohort_start_year, cohort_end_year) values (?, ?, ?);
                """;
        try {
            Connection conn = dbConn.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getCohortName());
            ps.setInt(2, c.getCohortStartYear());
            ps.setInt(3, c.getCohortEndYear());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int removeById(int id) {
        String sql = "delete from cohort where cohort_id = ?;";
        try {
            Connection conn = dbConn.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateById(Cohort c) {
        String sql = "update cohort set cohort_name = ?, cohort_start_year = ?, cohort_end_year = ? where cohort_id = ?;";
        try {
            Connection conn = dbConn.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getCohortName());
            ps.setInt(2, c.getCohortStartYear());
            ps.setInt(3, c.getCohortEndYear());
            ps.setInt(4, c.getCohortId());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Cohort> search(String cohortName, Integer startYear, Integer endYear) {
        List<Cohort> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
                    SELECT cohort_id, cohort_name, cohort_start_year, cohort_end_year
                    FROM cohort
                    WHERE 1=1
                """);

        List<Object> params = new ArrayList<>();
        if (cohortName != null && !cohortName.isEmpty()) {
            sql.append(" AND cohort_name LIKE ?");
            params.add("%" + cohortName.trim() + "%");
        }

        if (startYear != null) {
            sql.append(" AND cohort_start_year = ?");
            params.add(startYear);
        }

        if (endYear != null) {
            sql.append(" AND cohort_end_year = ?");
            params.add(endYear);
        }

        sql.append(" ORDER BY cohort_start_year");

        try {
            Connection conn = dbConn.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Cohort(
                        rs.getInt("cohort_id"),
                        rs.getString("cohort_name"),
                        rs.getInt("cohort_start_year"),
                        rs.getInt("cohort_end_year")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}