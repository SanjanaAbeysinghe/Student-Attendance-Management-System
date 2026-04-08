package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectDto {
    private String subjectId;
    private String subjectName;
    private String creditHours;
    private String courseId;
}
