package Dao.custom.IMPL;

import Dao.CrudUtil;
import Dao.custom.CourseDao;
import Entity.CourseEntity;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CourseDaoImpl implements CourseDao {
    @Override
    public boolean save(CourseEntity courseEntity) throws Exception {
        return CrudUtil.executeUpdate
                ("INSERT INTO Course(course_id, name, description, duration_weeks) " + "VALUES (?, ?, ?, ?)",
                courseEntity.getCourseId(),
                courseEntity.getName(),
                courseEntity.getDescription(),
                courseEntity.getDurationWeeks());
    }

    @Override
    public boolean update(CourseEntity courseEntity) throws Exception {
        return CrudUtil.executeUpdate
                ("UPDATE Course SET name = ?, description = ?, duration_weeks = ? " +
                        "WHERE course_id = ?",
                courseEntity.getName(),
                courseEntity.getDescription(),
                courseEntity.getDurationWeeks(),
                courseEntity.getCourseId());
    }

    @Override
    public boolean delete(String A) throws Exception {
        return CrudUtil.executeUpdate("DELETE FROM Course WHERE course_id = ?",A);
    }

    @Override
    public CourseEntity search(String A) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT FROM Course WHERE course_id = ?",A);
        if(rst.next()){
            return new CourseEntity(rst.getString("course_id"),
                    rst.getString("name"),
                    rst.getString("description"),
                    rst.getInt("duration_weeks"));
        }
        return null;
    }

    @Override
    public ArrayList<CourseEntity> getALL() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT FROM Course");
        ArrayList<CourseEntity> courseEntities = new ArrayList<>();
        while (rst.next()){
            courseEntities.add(new CourseEntity(rst.getString("course_id"),
                    rst.getString("name"),
                    rst.getString("description"),
                    rst.getInt("duration_weeks")));
        }
        return courseEntities;
    }
}