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
public class Discipline {
    private int idkyluat;
    private String studentId;
    private String studentName;
    private String hinhThuc;
    private String soQuyetDinh;
    private Date kyluatDate;
    private Date ngayKetThuc;
    private String lyDo;
// them khoi tao de k nhan ten, để cái dao nó gắn ý
    public Discipline(int idkyluat, String studentId, String hinhThuc,String soQuyetDinh, Date kyluatDate,Date ngayKetThuc,  String lyDo)
    {
        this.idkyluat = idkyluat;
        this.studentId = studentId;
        this.studentName = "";
        this.hinhThuc = hinhThuc;
        this.soQuyetDinh = soQuyetDinh;
        this.kyluatDate = kyluatDate;
        this.ngayKetThuc = ngayKetThuc;
        this.lyDo = lyDo;
    }
}
