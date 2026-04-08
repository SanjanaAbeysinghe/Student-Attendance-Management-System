package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseEntity {
    private String courseId;
    private String name;
    private String description;
    private int durationWeeks;
}
