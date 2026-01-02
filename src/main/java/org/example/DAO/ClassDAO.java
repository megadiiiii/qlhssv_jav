package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO {
    public List<ClassInfo> findAllClasses() {
        List<ClassInfo> classList = new ArrayList<>();
        String sql = """
                    SELECT c.*, m.facu_id, f.facu_name, m.major_name, t.teacher_name, ch.cohort_name
                    FROM class c
                    JOIN major m ON m.major_id = c.major_id
                    JOIN faculties f ON f.facu_id = m.facu_id
                    JOIN teacher t ON t.teacher_id = c.teacher_id
                    JOIN cohort ch ON ch.cohort_id = c.cohort_id                
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("teacher_name")
                );

                Cohort cohort = new Cohort(
                        rs.getInt("cohort_id"),
                        rs.getString("cohort_name")
                );

                Faculties faculty = new Faculties(
                        rs.getString("facu_id"),
                        rs.getString("facu_name")
                );

                Major major = new Major(
                        rs.getString("major_id"),
                        rs.getString("major_name"),
                        faculty
                );

                ClassInfo classInfo = new ClassInfo();
                classInfo.setClassId(rs.getString("class_id"));
                classInfo.setClassName(rs.getString("class_name"));
                classInfo.setStudentCurrent(rs.getInt("student_current"));
                classInfo.setStudentMax(rs.getInt("student_max"));
                classInfo.setTeacher(teacher);
                classInfo.setCohort(cohort);
                classInfo.setMajor(major);
                classInfo.setFaculty(faculty);

                classList.add(classInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classList;
    }

    public int countClasses(String facuId, String majorId, int cohortId) {
        String sql = """
                SELECT COUNT(*) FROM class c 
                JOIN major m ON c.major_id = m.major_id 
                JOIN faculties f ON m.facu_id = f.facu_id 
                WHERE m.facu_id = ? AND c.major_id = ? AND c.cohort_id = ?
                """;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, facuId);
            ps.setString(2, majorId);
            ps.setInt(3, cohortId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insert(ClassInfo c) {
        String sql = """
                INSERT INTO class (class_id, class_name, major_id, teacher_id, cohort_id, student_max, student_current)           
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getClassId());
            ps.setString(2, c.getClassName());
            ps.setString(3, c.getMajor().getMajorId());
            ps.setString(4, c.getTeacher().getTeacherId());
            ps.setInt(5, c.getCohort().getCohortId());
            ps.setInt(6, c.getStudentMax());
            ps.setInt(7, c.getStudentCurrent());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(ClassInfo c) {
        String sql = """
                    UPDATE class
                    SET class_name = ?,
                        major_id = ?,
                        teacher_id = ?,
                        cohort_id = ?,
                        student_max = ?,
                        student_current = ?
                    WHERE class_id = ?
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getClassName());
            ps.setString(2, c.getMajor().getMajorId());
            ps.setString(3, c.getTeacher().getTeacherId());
            ps.setInt(4, c.getCohort().getCohortId());
            ps.setInt(5, c.getStudentMax());
            ps.setInt(6, c.getStudentCurrent());
            ps.setString(7, c.getClassId());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String classId) {
        String sql = """
                DELETE FROM class
                WHERE class_id = ?;
                """;
        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, classId);

            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public List<ClassInfo> search(String classId, String className, Faculties facu, Major major, Cohort cohort, Teacher teacher) {
        List<ClassInfo> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
                SELECT c.*, m.facu_id, f.facu_name, m.major_name, t.teacher_name, ch.cohort_name
                    FROM class c
                    JOIN major m ON m.major_id = c.major_id
                    JOIN faculties f ON f.facu_id = m.facu_id
                    JOIN teacher t ON t.teacher_id = c.teacher_id
                    JOIN cohort ch ON ch.cohort_id = c.cohort_id                
                """);

        List<Object> params = new ArrayList<>();

        if (classId != null && !classId.isEmpty()) {
            sql.append(" AND class_id LIKE ?");
            params.add("%" + classId.trim() + "%");
        }

        if (className != null && !className.isEmpty()) {
            sql.append(" AND class_name LIKE ?");
            params.add("%" + className.trim() + "%");
        }

        if (facu != null && !facu.getFacuId().isBlank()) {
            sql.append(" AND m.facu_id = ?");
            params.add(facu.getFacuId());
        }

        if (major != null && !major.getMajorId().isBlank()) {
            sql.append(" AND m.major_id = ?");
            params.add(major.getMajorId());
        }

        if (teacher != null && !teacher.getTeacherId().isBlank()) {
            sql.append(" AND t.teacher_id = ?");
            params.add(teacher.getTeacherId());
        }

        if (cohort != null && cohort.getCohortId() != 0) {
            sql.append(" AND ch.cohort_id = ?");
            params.add(cohort.getCohortId());
        }

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Teacher t = new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("teacher_name")
                );

                Cohort c = new Cohort(
                        rs.getInt("cohort_id"),
                        rs.getString("cohort_name")
                );

                Faculties f = new Faculties(
                        rs.getString("facu_id"),
                        rs.getString("facu_name")
                );

                Major m = new Major(
                        rs.getString("major_id"),
                        rs.getString("major_name"),
                        f
                );

                ClassInfo classInfo = new ClassInfo();
                classInfo.setClassId(rs.getString("class_id"));
                classInfo.setClassName(rs.getString("class_name"));
                classInfo.setStudentCurrent(rs.getInt("student_current"));
                classInfo.setStudentMax(rs.getInt("student_max"));
                classInfo.setTeacher(t);
                classInfo.setCohort(c);
                classInfo.setMajor(m);
                classInfo.setFaculty(f);

                list.add(classInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
