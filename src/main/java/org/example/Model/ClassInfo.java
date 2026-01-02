package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ClassInfo {
    private String classId;
    private String className;
    private Faculties faculty;
    private Major major;
    private Teacher teacher;
    private Cohort cohort;
    private int studentCurrent;
    private int studentMax;
}
