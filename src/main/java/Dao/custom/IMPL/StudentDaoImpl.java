package Dao.custom.IMPL;

import Dao.CrudUtil;
import Entity.StudentEntity;
import Dao.custom.StudentDao;

import java.sql.ResultSet;
import java.util.ArrayList;

public class StudentDaoImpl implements StudentDao {
    @Override
    public boolean save(StudentEntity studentEntity) throws Exception {
        return CrudUtil.executeUpdate
                ("INSERT INTO Student (student_id, name, reg_number, gender, dob, email, contact, course_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                studentEntity.getStudentId(),
                studentEntity.getName(),
                studentEntity.getRegNumber(),
                studentEntity.getGender(),
                studentEntity.getDob(),
                studentEntity.getEmail(),
                studentEntity.getContact(),
                studentEntity.getCourseId());
    }

    @Override
    public boolean update(StudentEntity studentEntity) throws Exception {
        return CrudUtil.executeUpdate
                ("UPDATE Student SET name=?, reg_number=?, gender=?, dob=?, email=?, contact=?, course_id=? WHERE student_id=?",
                studentEntity.getName(),
                studentEntity.getRegNumber(),
                studentEntity.getGender(),
                studentEntity.getDob(),
                studentEntity.getEmail(),
                studentEntity.getContact(),
                studentEntity.getCourseId(),
                studentEntity.getStudentId());
    }

    @Override
    public boolean delete(String s) throws Exception {

        return CrudUtil.executeUpdate("DELETE FROM Student WHERE student_id=?",s);
    }

    @Override
    public StudentEntity search(String s) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Student WHERE student_id=?",s);
        if(rst.next()){
            return new StudentEntity( rst.getString("student_id"),
                    rst.getString("name"),
                    rst.getString("reg_number"),
                    rst.getString("gender"),
                    rst.getDate("dob"),
                    rst.getString("email"),
                    rst.getString("contact"),
                    rst.getString("course_id"));
        }
        return null;
    }

    @Override
    public ArrayList<StudentEntity> getALL() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Student ");
        ArrayList<StudentEntity> studentEntities = new ArrayList<>();
        while (rst.next()){
            studentEntities.add(new StudentEntity(rst.getString("student_id"),
                    rst.getString("name"),
                    rst.getString("reg_number"),
                    rst.getString("gender"),
                    rst.getDate("dob"),
                    rst.getString("email"),
                    rst.getString("contact"),
                    rst.getString("course_id") ));
        }
        return studentEntities;
    }
}
