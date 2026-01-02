package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.*;

import java.sql.*;
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
}
