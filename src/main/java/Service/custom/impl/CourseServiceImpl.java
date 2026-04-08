package Service.custom.impl;

import Dao.custom.CourseDao;
import Dao.DaoFactory;
import Dto.CourseDto;
import Entity.CourseEntity;
import Service.custom.CourseService;

import java.util.ArrayList;

public class CourseServiceImpl implements CourseService {
    private CourseDao courseDao = (CourseDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.COURSE);

    @Override
    public String saveCourse(CourseDto courseDto) throws Exception {
        CourseEntity courseEntity = new CourseEntity(
                courseDto.getCourseId(),
                courseDto.getName(),
                courseDto.getDescription(),
                courseDto.getDurationWeeks());
        boolean isSaved = courseDao.save(courseEntity);
        return isSaved ? "Saved" : "fail";
    }

    @Override
    public String updateCourse(CourseDto courseDto) throws Exception {
        CourseEntity courseEntity = new CourseEntity(
                courseDto.getCourseId(),
                courseDto.getName(),
                courseDto.getDescription(),
                courseDto.getDurationWeeks());
        boolean isUpdated = courseDao.update(courseEntity);
        return isUpdated ? "Updated" : "fail";
    }

    @Override
    public String deleteCourse(String CourseId) throws Exception {
        boolean isDeleted = courseDao.delete(CourseId);
        return isDeleted ? "Deleted" : "Fail";
    }

    @Override
    public CourseDto searchCourse(String CourseId) throws Exception {
        CourseEntity courseEntity = courseDao.search(CourseId);
        if(courseEntity != null){
            return new CourseDto(courseEntity.getCourseId(),
                    courseEntity.getName(),
                    courseEntity.getDescription(),
                    courseEntity.getDurationWeeks());
        }
        return null;
    }

    @Override
    public ArrayList<CourseDto> getAllCourse() throws Exception {
        ArrayList<CourseEntity> courseEntities = courseDao.getALL();
        ArrayList<CourseDto> courseDtos = new ArrayList<>();
        if (courseEntities != null){
            for (CourseEntity courseEntity : courseEntities){
                courseDtos.add(new CourseDto(courseEntity.getCourseId(),
                        courseEntity.getName(),
                        courseEntity.getDescription(),
                        courseEntity.getDurationWeeks()));
            }
        }
        return courseDtos;
    }
}
