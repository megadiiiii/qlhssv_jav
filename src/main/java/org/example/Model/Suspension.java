package org.example.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Suspension {
    private String suspensionId;
    private String studentId;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String status;
}

