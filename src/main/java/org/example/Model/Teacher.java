package org.example.Model;

public class Teacher {
    private String teacherId;
    private String teacherName;
    private String facuId;
    private String facuName;

    public Teacher() {}

    public Teacher(String teacherId, String teacherName, String facuId, String facuName) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.facuId = facuId;
        this.facuName = facuName;
    }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public String getFacuId() { return facuId; }
    public void setFacuId(String facuId) { this.facuId = facuId; }

    public String getFacuName() { return facuName; }
    public void setFacuName(String facuName) { this.facuName = facuName; }
}
