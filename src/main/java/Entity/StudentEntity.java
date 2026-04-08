package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentEntity {
    private String studentId;
    private String name;
    private String regNumber;
    private String gender;
    private Date dob;
    private String email;
    private String contact;
    private String courseId;
}