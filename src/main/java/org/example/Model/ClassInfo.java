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
    private String facuId;
    private String majorId;
    private int cohort;
    private String facuName;
    private String majorName;
}

