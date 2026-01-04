package org.example.Model;

import java.sql.Date;

public class Reward {
    private int rewardId;
    private String studentId;
    private String studentName;
    private Date rewardDate;
    private String rewardNote;
    private String rewardQuyetDinh;

    public int getRewardId() {
        return rewardId; }
    public void setRewardId(int rewardId) { this.rewardId = rewardId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Date getRewardDate() { return rewardDate; }
    public void setRewardDate(Date rewardDate) { this.rewardDate = rewardDate; }

    public String getRewardNote() { return rewardNote; }
    public void setRewardNote(String rewardNote) { this.rewardNote = rewardNote; }

    public String getRewardQuyetDinh() { return rewardQuyetDinh; }
    public void setRewardQuyetDinh(String rewardQuyetDinh) { this.rewardQuyetDinh = rewardQuyetDinh; }
}
