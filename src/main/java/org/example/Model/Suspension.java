package org.example.Model;
import com.mysql.cj.x.protobuf.MysqlxCrud;
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
    public static MysqlxCrud.Find.RowLock SuspensionStatus;
    private String suspensionId;
    private String studentId;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String status;

}

