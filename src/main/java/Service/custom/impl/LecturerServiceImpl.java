package Service.custom.impl;

import Dao.custom.LecturerDao;
import Dao.DaoFactory;
import Dto.LecturerDto;
import Entity.LecturerEntity;
import Service.custom.LecturerService;

import java.util.ArrayList;

public class LecturerServiceImpl implements LecturerService {
    private LecturerDao lecturerDao = (LecturerDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.LECTURER);

    @Override
    public String saveLecturer(LecturerDto lecturerDto) throws Exception {
        LecturerEntity lecturerEntity = new LecturerEntity(
                lecturerDto.getLecturerId(),
                lecturerDto.getName(),
                lecturerDto.getQualification(),
                lecturerDto.getDepartment(),
                lecturerDto.getEmail(),
                lecturerDto.getContact());

        boolean isSaved = lecturerDao.save(lecturerEntity);
        return isSaved ? "Saved" : "Failed";
    }

    @Override
    public String updateLecturer(LecturerDto lecturerDto) throws Exception {
        LecturerEntity lecturerEntity = new LecturerEntity(
                lecturerDto.getLecturerId(),
                lecturerDto.getName(),
                lecturerDto.getQualification(),
                lecturerDto.getDepartment(),
                lecturerDto.getEmail(),
                lecturerDto.getContact());

        boolean isUpdated = lecturerDao.update(lecturerEntity);
        return isUpdated ? "Updated" : "Failed";
    }

    @Override
    public String deleteLecturer(String lectureId) throws Exception {
        boolean isDeleted = lecturerDao.delete(lectureId);
        return isDeleted ? "Deleted" : "Failed";
    }

    @Override
    public LecturerDto searchLecturer(String CourseId) throws Exception {
        LecturerEntity lecturerEntity = lecturerDao.search(CourseId);
        if(lecturerEntity != null){
            return new LecturerDto( lecturerEntity.getLecturerId(),
                    lecturerEntity.getName(),
                    lecturerEntity.getQualification(),
                    lecturerEntity.getDepartment(),
                    lecturerEntity.getEmail(),
                    lecturerEntity.getContact());
        }
        return null;
    }

    @Override
    public ArrayList<LecturerDto> getAllLecturer() throws Exception {
        ArrayList<LecturerEntity> lecturerEntities = lecturerDao.getALL();
        ArrayList<LecturerDto> lecturerDtos = new ArrayList<>();
        if (lecturerEntities != null){
            for (LecturerEntity lecturerEntity : lecturerEntities){
                lecturerDtos.add(new LecturerDto(lecturerEntity.getLecturerId(),
                        lecturerEntity.getName(),
                        lecturerEntity.getQualification(),
                        lecturerEntity.getDepartment(),
                        lecturerEntity.getEmail(),
                        lecturerEntity.getContact()));
            }
        }
        return lecturerDtos;
    }
}
