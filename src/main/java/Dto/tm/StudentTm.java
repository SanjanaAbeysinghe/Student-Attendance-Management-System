package Dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentTm {
    private String studentId;
    private String name;
    private String regNumber;
    private String gender;
    private Date dob;
    private String email;
    private String contact;
    private String courseId;
}