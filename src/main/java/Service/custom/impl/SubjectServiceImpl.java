package Service.custom.impl;

import Dao.custom.SubjectDao;
import Dao.DaoFactory;
import Dto.SubjectDto;
import Entity.SubjectEntity;
import Service.custom.SubjectService;

import java.util.ArrayList;

public class SubjectServiceImpl implements SubjectService {
    private SubjectDao subjectDao = (SubjectDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.SUBJECT);

    @Override
    public String saveSubject(SubjectDto subjectDto) throws Exception {
        SubjectEntity subjectEntity = new SubjectEntity(
                subjectDto.getSubjectId(),
                subjectDto.getSubjectName(),
                subjectDto.getCreditHours(),
                subjectDto.getCourseId());

        boolean isSaved = subjectDao.save(subjectEntity);
        return isSaved ? "Saved" : "Failed";
    }

    @Override
    public String updateSubject(SubjectDto subjectDto) throws Exception {
        SubjectEntity subjectEntity = new SubjectEntity(
                subjectDto.getSubjectId(),
                subjectDto.getSubjectName(),
                subjectDto.getCreditHours(),
                subjectDto.getCourseId());

        boolean isUpdated = subjectDao.update(subjectEntity);
        return isUpdated ? "Updated" : "Failed";
    }

    @Override
    public String deleteSubject(String SubjectId) throws Exception {
        boolean isDeleted = subjectDao.delete(SubjectId);
        return isDeleted ? "Deleted" : "Failed";
    }

    @Override
    public SubjectDto searchSubject(String SubjectId) throws Exception {
        SubjectEntity subjectEntity = subjectDao.search(SubjectId);
        if(subjectEntity != null){
            return new SubjectDto(
                    subjectEntity.getSubjectId(),
                    subjectEntity.getSubjectName(),
                    subjectEntity.getCreditHours(),
                    subjectEntity.getCourseId());
        }
        return null;
    }

    @Override
    public ArrayList<SubjectDto> getAllSubject() throws Exception {
        ArrayList<SubjectEntity> subjectEntities = subjectDao.getALL();
        ArrayList<SubjectDto> subjectDtos = new ArrayList<>();
        if (subjectEntities != null){
            for (SubjectEntity subjectEntity : subjectEntities){
                subjectDtos.add(new SubjectDto( subjectEntity.getSubjectId(),
                        subjectEntity.getSubjectName(),
                        subjectEntity.getCreditHours(),
                        subjectEntity.getCourseId()));
            }
        }
        return subjectDtos;
    }
}