package Dto;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDto {
    private String studentId;
    private String name;
    private String regNumber;
    private String gender;
    private Date dob;
    private String email;
    private String contact;
    private String courseId;


}