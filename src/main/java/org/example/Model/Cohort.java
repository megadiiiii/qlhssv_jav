package org.example.Model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cohort {
    int cohortId;
    String cohortName;
    int cohortStartYear;
    int cohortEndYear;

    public Cohort(String name, int start, int end) {
        this.cohortName = name;
        this.cohortStartYear = start;
        this.cohortEndYear = end;
    }
}
