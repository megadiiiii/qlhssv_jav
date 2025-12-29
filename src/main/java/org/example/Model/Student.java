package org.example.Model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Student {
    String studentId;
    String lastName;
    String firstName;
    Date dob;
    String gender;
    String email;
    String hometown;
    String phone;
    String idNo;
    String classId;
    String majorId;
    String facuId;
    String status;

    // Display fields
    String className;
    String cohort;
    String majorName;
    String facuName;

    // Constructor cũ
    public Student(String studentId, String lastName, String firstName, Date dob,
                   String gender, String email, String hometown, String phone,
                   String idNo, String classId, String majorId, String facuId, String status) {
        this.studentId = studentId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.hometown = hometown;
        this.phone = phone;
        this.idNo = idNo;
        // Nếu có các field classId, majorId, facuId thì gán vào
        this.classId = classId;
        this.majorId = majorId;
        this.facuId = facuId;
        this.status = status;
    }

    // Constructor đầy đủ
    @Builder
    public Student(String studentId, String lastName, String firstName, Date dob,
                   String gender, String className, String cohort, String majorName,
                   String facuName, String email, String status, String hometown,
                   String phone, String idNo) {
        this.studentId = studentId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dob = dob;
        this.gender = gender;
        this.className = className;
        this.cohort = cohort;
        this.majorName = majorName;
        this.facuName = facuName;
        this.email = email;
        this.status = status;
        this.hometown = hometown;
        this.phone = phone;
        this.idNo = idNo;
    }
}