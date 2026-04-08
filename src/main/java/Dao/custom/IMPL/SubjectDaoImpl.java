package Dao.custom.IMPL;

import Dao.CrudUtil;
import Dao.custom.SubjectDao;
import Entity.SubjectEntity;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SubjectDaoImpl implements SubjectDao {
    @Override
    public boolean save(SubjectEntity subjectEntity) throws Exception {
        return CrudUtil.executeUpdate
                ("INSERT INTO Subject(subject_id, name, credit_hours, course_id) VALUES (?, ?, ?, ?)",
                subjectEntity.getSubjectId(),
                subjectEntity.getSubjectName(),
                subjectEntity.getCreditHours(),
                subjectEntity.getCourseId());
    }

    @Override
    public boolean update(SubjectEntity subjectEntity) throws Exception {
        return CrudUtil.executeUpdate
                ( "UPDATE Subject SET name = ?, credit_hours = ?, course_id = ? WHERE subject_id = ?",
                subjectEntity.getSubjectName(),
                subjectEntity.getCreditHours(),
                subjectEntity.getCourseId(),
                subjectEntity.getSubjectId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.executeUpdate
                ("DELETE FROM Subject WHERE subject_id = ?",s);
    }

    @Override
    public SubjectEntity search(String s) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT FROM Subject WHERE subject_id = ?");
        if(rst.next()) {
            return new SubjectEntity(rst.getString("subject_id"),
                    rst.getString("name"),
                    rst.getString("credit_hours"),
                    rst.getString("course_id"));
        }
        return null;
    }

    @Override
    public ArrayList<SubjectEntity> getALL() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT FROM Subject");
        ArrayList<SubjectEntity> subjectEntities = new ArrayList<>();
        while (rst.next()){
            subjectEntities.add(new  SubjectEntity(rst.getString("subject_id"),
                    rst.getString("name"),
                    rst.getString("credit_hours"),
                    rst.getString("course_id")));
        }
        return subjectEntities;
    }
}