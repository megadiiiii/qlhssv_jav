package org.example.Model;

public class Role {
    private int roleId;
    private String studentId;
    private String studentName;   // từ JOIN
    private String studentRole;

    public Role() {}

    public Role(int roleId, String studentId, String studentName, String studentRole) {
        this.roleId = roleId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentRole = studentRole;
    }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentRole() { return studentRole; }
    public void setStudentRole(String studentRole) { this.studentRole = studentRole; }
}
