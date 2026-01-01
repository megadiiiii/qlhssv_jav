//package org.example.DAO;
//
//import org.example.Config.dbConn;
//import org.example.Model.Student;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class StudentDAO {
//
//    public StudentDAO() {
//    }
//
//    public List<Student> getAllStudents() throws SQLException {
//        Connection conn = dbConn.getConnection();
//        List<Student> list = new ArrayList<>();
//        String sql = """
//                SELECT s.student_id, s.student_lastName, s.student_firstName, s.dob,
//                       s.gender, s.email, s.hometown, s.phone, s.id_no,
//                       c.class_name, c.cohort, m.major_name, f.facu_name, s.status
//                FROM student s
//                JOIN class c ON c.class_id = s.class_id
//                JOIN major m ON m.major_id = c.major_id
//                JOIN faculties f ON f.facu_id = m.facu_id
//                ORDER BY s.student_firstName ASC
//            """;
//
//        try (PreparedStatement ps = conn.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                Student st = new Student(
//                        rs.getString("student_id"),
//                        rs.getString("student_lastName"),
//                        rs.getString("student_firstName"),
//                        rs.getDate("dob"),
//                        rs.getString("gender"),
//                        rs.getString("class_name"),
//                        rs.getString("cohort"),
//                        rs.getString("major_name"),
//                        rs.getString("facu_name"),
//                        rs.getString("email"),
//                        rs.getString("status"),
//                        rs.getString("hometown"),
//                        rs.getString("phone"),
//                        rs.getString("id_no")
//                );
//                list.add(st);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public void insert(Student st) throws SQLException {
//        Connection conn = dbConn.getConnection();
//        String sql = """
//                INSERT INTO student (student_id, student_lastName, student_firstName, dob,
//                gender, email, hometown, phone, id_no, class_id, status)
//                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
//                """;
//
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ps.setString(1, st.getStudentId());
//        ps.setString(2, st.getLastName());
//        ps.setString(3, st.getFirstName());
//        ps.setDate(4, new java.sql.Date(st.getDob().getTime()));
//        ps.setString(5, st.getGender());
//        ps.setString(6, st.getEmail());
//        ps.setString(7, st.getHometown());
//        ps.setString(8, st.getPhone());
//        ps.setString(9, st.getIdNo());
//        ps.setString(10, st.getClassId());
//        ps.setString(11, st.getStatus());
//        ps.executeUpdate();
//        ps.close();
//        conn.close();
//    }
//
//    public void update(Student st) throws SQLException {
//        Connection conn = dbConn.getConnection();
//        String sql = """
//            UPDATE student
//            SET student_lastName = ?, student_firstName = ?, dob = ?,
//                gender = ?, email = ?, hometown = ?, phone = ?, id_no = ?,
//                class_id = ?, status = ?
//            WHERE student_id = ?
//            """;
//
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ps.setString(1, st.getLastName());
//        ps.setString(2, st.getFirstName());
//        ps.setDate(3, new java.sql.Date(st.getDob().getTime()));
//        ps.setString(4, st.getGender());
//        ps.setString(5, st.getEmail());
//        ps.setString(6, st.getHometown());
//        ps.setString(7, st.getPhone());
//        ps.setString(8, st.getIdNo());
//        ps.setString(9, st.getClassId());
//        ps.setString(10, st.getStatus());
//        ps.setString(11, st.getStudentId()); // WHERE condition
//
//        ps.executeUpdate();
//        ps.close();
//        conn.close();
//    }
//
//    public void delete(String studentId) throws SQLException {
//        Connection conn = dbConn.getConnection();
//        String sql = "DELETE FROM student WHERE student_id = ?";
//
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ps.setString(1, studentId);
//        ps.executeUpdate();
//        ps.close();
//        conn.close();
//    }
//}
