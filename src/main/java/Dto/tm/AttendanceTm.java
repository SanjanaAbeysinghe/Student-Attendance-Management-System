package Dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceTm {
    private String AId;
    private String studentId;
    private String classId;
    private String status;
    private String markedTime;



}