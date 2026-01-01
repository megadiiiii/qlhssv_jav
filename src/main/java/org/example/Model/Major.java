package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Major {
    String majorId;
    String majorName;
    Faculties faculty;

    @Override
    public String toString() {
        return majorName;
    }
}

