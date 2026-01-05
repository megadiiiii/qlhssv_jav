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
public class Suspension {
    private int suspensionId;
    private String studentId;
    private String studentName;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String status;

    public Suspension(int suspensionId, String studentId, Date startDate, Date endDate, String reason, String status) {
        this.suspensionId = suspensionId;
        this.studentId = studentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
    }
}

