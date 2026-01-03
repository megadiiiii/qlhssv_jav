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

    public ClassInfo(String classId, String className, Cohort cohort, Major major) {
        this.classId = classId;
        this.className = className;
        this.cohort = cohort;
        this.major = major;
    }

    public ClassInfo(String classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    @Override
    public String toString() {
        return className;
    }
}
