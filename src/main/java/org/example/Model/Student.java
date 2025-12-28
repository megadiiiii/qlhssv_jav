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
    String status;
}
