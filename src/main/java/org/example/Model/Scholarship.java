package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Scholarship {
    int scholarshipId;
    String studentId;
    String studentName;
    String scoreLevel;
    String drlLevel;
    String scholarshipLevel;
    String semester;

    public Scholarship(int scholarshipId, String studentId, String studentName, String scoreLevel, String drlLevel, String scholarshipLevel, String semester) {
        this.scholarshipId = scholarshipId;
        this.studentId = studentId;
        this.studentName = "";
        this.scoreLevel = scoreLevel;
        this.drlLevel = drlLevel;
        this.scholarshipLevel = scholarshipLevel;
        this.semester = semester;
    }

    public Scholarship(int scholarshipId, String studentId, String scoreLevel, String drlLevel, String scholarshipLevel, String semester) {
        this.scholarshipId = scholarshipId;
        this.studentId = studentId;
        this.scoreLevel = scoreLevel;
        this.drlLevel = drlLevel;
        this.scholarshipLevel = scholarshipLevel;
        this.semester = semester;
    }
}