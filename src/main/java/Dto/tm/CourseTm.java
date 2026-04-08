package Dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseTm {
    private String courseId;
    private String name;
    private String description;
    private int durationWeeks;
}
