package Service.custom;

import Dto.LecturerDto;
import Service.SuperService;

import java.util.ArrayList;

public interface LecturerService extends SuperService {
    public String saveLecturer(LecturerDto lecturerDto) throws Exception;
    public String updateLecturer(LecturerDto lecturerDto) throws Exception;
    public String deleteLecturer(String lectureId) throws Exception;
    public LecturerDto searchLecturer(String CourseId) throws Exception;
    public ArrayList<LecturerDto> getAllLecturer() throws Exception;
}