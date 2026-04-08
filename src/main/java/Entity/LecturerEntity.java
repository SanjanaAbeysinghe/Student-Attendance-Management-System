package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LecturerEntity {
    private String lecturerId;
    private String name;
    private String qualification;
    private String department;
    private String email;
    private String contact;
}
