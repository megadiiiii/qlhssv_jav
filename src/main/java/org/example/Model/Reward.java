package org.example.Model;
import java.sql.Date;
public class Reward {
    private int rewardId;
    private String studentId;
    private Date rewardDate;
    private String rewardNote;
    private String rewardQuyetDinh;
    public Reward() {
    }
    public Reward(int rewardId, String studentId, Date rewardDate, String rewardNote, String rewardQuyetDinh) {
        this.rewardId = rewardId;
        this.studentId = studentId;
        this.rewardDate = rewardDate;
        this.rewardNote = rewardNote;
        this.rewardQuyetDinh = rewardQuyetDinh;
    }
    public int getRewardId() {
        return rewardId;
    }
    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public Date getRewardDate() {
        return rewardDate;
    }
    public void setRewardDate(Date rewardDate) {
        this.rewardDate = rewardDate;
    }
    public String getRewardNote() {
        return rewardNote;
    }
    public void setRewardNote(String rewardNote) {
        this.rewardNote = rewardNote;
    }
    public String getRewardQuyetDinh() {
        return rewardQuyetDinh;
    }
    public void setRewardQuyetDinh(String rewardQuyetDinh) {
        this.rewardQuyetDinh = rewardQuyetDinh;
    }
}
