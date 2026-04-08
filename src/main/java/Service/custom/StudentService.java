package Service.custom;

import Dto.StudentDto;
import Service.SuperService;

import java.util.ArrayList;

public interface StudentService extends SuperService {
    public String saveStudent(StudentDto studentDto) throws Exception;
    public String updateStudent(StudentDto studentDto) throws Exception;
    public String deleteStudent(String StudentId) throws Exception;
    public StudentDto searchStudent(String StudentId) throws Exception;
    public ArrayList<StudentDto> getAllStudent() throws Exception;
}
