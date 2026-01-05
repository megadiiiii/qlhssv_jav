package org.example.DAO;

import org.example.Config.dbConn;
import org.example.Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = """
                    SELECT s.student_id, s.student_lastName, s.student_firstName, s.dob,
                    s.gender, s.email, s.hometown, s.phone, s.citizen_id,
                    c.class_id, c.class_name, ch.cohort_name,
                    m.major_id, m.major_name,
                    f.facu_id, f.facu_name,
                    s.status
                    FROM student s
                    JOIN class c ON c.class_id = s.class_id
                    JOIN major m ON m.major_id = c.major_id
                    JOIN faculties f ON f.facu_id = m.facu_id
                    JOIN cohort ch on ch.cohort_id = c.cohort_id
                    ORDER BY s.student_firstName ASC
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Faculties faculty = new Faculties();
                faculty.setFacuId(rs.getString("facu_id"));
                faculty.setFacuName(rs.getString("facu_name"));

                Major major = new Major();
                major.setMajorId(rs.getString("major_id"));
                major.setMajorName(rs.getString("major_name"));
                major.setFaculty(faculty);

                Cohort cohort = new Cohort();
                cohort.setCohortName(rs.getString("cohort_name"));

                ClassInfo classInfo = new ClassInfo();
                classInfo.setClassId(rs.getString("class_id"));
                classInfo.setClassName(rs.getString("class_name"));
                classInfo.setCohort(cohort);
                classInfo.setMajor(major);

                Student st = new Student();
                st.setStudentId(rs.getString("student_id"));
                st.setLastName(rs.getString("student_lastName"));
                st.setFirstName(rs.getString("student_firstName"));
                st.setDob(rs.getDate("dob"));
                st.setGender(rs.getString("gender"));
                st.setEmail(rs.getString("email"));
                st.setHometown(rs.getString("hometown"));
                st.setPhone(rs.getString("phone"));
                st.setCitizenId(rs.getString("citizen_id"));
                st.setStatus(rs.getString("status"));
                st.setFaculty(faculty);
                st.setMajor(major);
                st.setClassInfo(classInfo);
                st.setCohort(cohort);

                list.add(st);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insert(Student st) {
        String sql = """
                INSERT INTO student (student_id, student_lastName, student_firstName, dob,
                gender, email, hometown, phone, citizen_id, class_id, status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, st.getStudentId());
            ps.setString(2, st.getLastName());
            ps.setString(3, st.getFirstName());
            ps.setDate(4, new java.sql.Date(st.getDob().getTime()));
            ps.setString(5, st.getGender());
            ps.setString(6, st.getEmail());
            ps.setString(7, st.getHometown());
            ps.setString(8, st.getPhone());
            ps.setString(9, st.getCitizenId());
            ps.setString(10, st.getClassInfo().getClassId());
            ps.setString(11, st.getStatus());

            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }


    public int update(Student st) {
        String sql = """
                UPDATE student
                SET student_lastName = ?, student_firstName = ?, dob = ?,
                    gender = ?, email = ?, hometown = ?, phone = ?, citizen_id = ?,
                    class_id = ?, status = ?
                WHERE student_id = ?
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, st.getLastName());
            ps.setString(2, st.getFirstName());
            ps.setDate(3, new java.sql.Date(st.getDob().getTime()));
            ps.setString(4, st.getGender());
            ps.setString(5, st.getEmail());
            ps.setString(6, st.getHometown());
            ps.setString(7, st.getPhone());
            ps.setString(8, st.getCitizenId());
            ps.setString(9, st.getClassInfo().getClassId());
            ps.setString(10, st.getStatus());
            ps.setString(11, st.getStudentId()); // WHERE condition

            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int delete(String studentId) {
        String sql = "DELETE FROM student WHERE student_id = ?";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);
            return ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int countStudentsByCohort(int cohortId) {
        String sql = """
                    SELECT COUNT(*)
                    FROM student s
                    JOIN class c ON c.class_id = s.class_id
                    WHERE c.cohort_id = ?
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cohortId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isClassFull(String classId) {
        String sql = """
                    SELECT student_current, student_max
                    FROM class
                    WHERE class_id = ?
                """;

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, classId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("student_current") >= rs.getInt("student_max");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public List<Student> search(String studentId, String studentName, Faculties facu, Major major, ClassInfo cl) {
        List<Student> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
                SELECT s.student_id, s.student_lastName, s.student_firstName, s.dob,
                    s.gender, s.email, s.hometown, s.phone, s.citizen_id,
                    c.class_id, c.class_name, ch.cohort_name,
                    m.major_id, m.major_name,
                    f.facu_id, f.facu_name,
                    s.status
                    FROM student s
                    JOIN class c ON c.class_id = s.class_id
                    JOIN major m ON m.major_id = c.major_id
                    JOIN faculties f ON f.facu_id = m.facu_id
                    JOIN cohort ch on ch.cohort_id = c.cohort_id
                    WHERE 1 = 1    
                """);

        List<Object> params = new ArrayList<>();

        if (studentId != null && !studentId.isBlank()) {
            sql.append(" AND s.student_id LIKE ?");
            params.add("%" + studentId + "%");
        }

        if (studentName != null && !studentName.isBlank()) {
            sql.append(" AND CONCAT(s.student_lastName,' ',s.student_firstName) LIKE ?");
            params.add("%" + studentName + "%");
        }

        if (facu != null && !facu.getFacuId().isBlank()) {
            sql.append(" AND m.facu_id = ?");
            params.add(facu.getFacuId());
        }

        if (major != null && !major.getMajorId().isBlank()) {
            sql.append(" AND m.major_id = ?");
            params.add(major.getMajorId());
        }

        if (cl != null && !cl.getClassId().isBlank()) {
            sql.append(" AND c.class_id = ?");
            params.add(cl.getClassId());
        }

        sql.append(" ORDER BY s.student_firstName ASC");

        try (Connection conn = dbConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString());) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Faculties faculty = new Faculties();
                faculty.setFacuId(rs.getString("facu_id"));
                faculty.setFacuName(rs.getString("facu_name"));

                Major m = new Major();
                m.setMajorId(rs.getString("major_id"));
                m.setMajorName(rs.getString("major_name"));
                m.setFaculty(faculty);

                Cohort cohort = new Cohort();
                cohort.setCohortName(rs.getString("cohort_name"));

                ClassInfo classInfo = new ClassInfo();
                classInfo.setClassId(rs.getString("class_id"));
                classInfo.setClassName(rs.getString("class_name"));
                classInfo.setCohort(cohort);
                classInfo.setMajor(m);

                Student st = new Student();
                st.setStudentId(rs.getString("student_id"));
                st.setLastName(rs.getString("student_lastName"));
                st.setFirstName(rs.getString("student_firstName"));
                st.setDob(rs.getDate("dob"));
                st.setGender(rs.getString("gender"));
                st.setEmail(rs.getString("email"));
                st.setHometown(rs.getString("hometown"));
                st.setPhone(rs.getString("phone"));
                st.setCitizenId(rs.getString("citizen_id"));
                st.setStatus(rs.getString("status"));
                st.setFaculty(faculty);
                st.setMajor(m);
                st.setClassInfo(classInfo);
                st.setCohort(cohort);

                list.add(st);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
