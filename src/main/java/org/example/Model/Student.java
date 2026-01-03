package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Student {
    private String studentId;
    private String lastName;
    private String firstName;
    private Date dob;
    private String gender;
    private String email;
    private String hometown;
    private String phone;
    private String citizenId;
    private Faculties faculty;
    private Major major;
    private ClassInfo classInfo;
    private Cohort cohort;
    private String status;
}