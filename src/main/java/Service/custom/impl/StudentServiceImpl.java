package Service.custom.impl;

import Dao.custom.StudentDao;
import Dao.DaoFactory;
import Dto.StudentDto;
import Entity.StudentEntity;
import Service.custom.StudentService;

import java.util.ArrayList;

public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao = (StudentDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.STUDENT);
    @Override
    public String saveStudent(StudentDto studentDto) throws Exception {
        StudentEntity studentEntity = new StudentEntity(
                studentDto.getStudentId(),
                studentDto.getName(),
                studentDto.getRegNumber(),
                studentDto.getGender(),
                studentDto.getDob(),
                studentDto.getEmail(),
                studentDto.getContact(),
                studentDto.getCourseId());
        boolean isSaved = studentDao.save(studentEntity);
        return isSaved ? "Saved" : "Failed";
    }

    @Override
    public String updateStudent(StudentDto studentDto) throws Exception {
        StudentEntity studentEntity = new StudentEntity(
                studentDto.getStudentId(),
                studentDto.getName(),
                studentDto.getRegNumber(),
                studentDto.getGender(),
                studentDto.getDob(),
                studentDto.getEmail(),
                studentDto.getContact(),
                studentDto.getCourseId());
        boolean isUpdated = studentDao.update(studentEntity);
        return isUpdated ? "Updated" : "Failed";
    }

    @Override
    public String deleteStudent(String StudentId) throws Exception {
        boolean isDeleted = studentDao.delete(StudentId);
        return isDeleted ? "Deleted" : "Failed";
    }

    @Override
    public StudentDto searchStudent(String StudentId) throws Exception {
        StudentEntity studentEntity = studentDao.search(StudentId);
        if(studentEntity != null){
            return new StudentDto(studentEntity.getStudentId(),
                    studentEntity.getName(),
                    studentEntity.getRegNumber(),
                    studentEntity.getGender(),
                    studentEntity.getDob(),
                    studentEntity.getEmail(),
                    studentEntity.getContact(),
                    studentEntity.getCourseId());
        }
        return null;
    }

    @Override
    public ArrayList<StudentDto> getAllStudent() throws Exception {
        ArrayList<StudentEntity> studentEntities = studentDao.getALL();
        ArrayList<StudentDto> studentDtos = new ArrayList<>();
        if (studentEntities != null){
            for (StudentEntity studentEntity : studentEntities){
                studentDtos.add(new StudentDto(studentEntity.getStudentId(),
                        studentEntity.getName(),
                        studentEntity.getRegNumber(),
                        studentEntity.getGender(),
                        studentEntity.getDob(),
                        studentEntity.getEmail(),
                        studentEntity.getContact(),
                        studentEntity.getCourseId()));
            }
        }
        return studentDtos;
    }
}