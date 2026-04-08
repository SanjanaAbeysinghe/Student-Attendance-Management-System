package Service.custom;

import Dto.SubjectDto;
import Service.SuperService;

import java.util.ArrayList;

public interface SubjectService extends SuperService {
    public String saveSubject(SubjectDto subjectDto) throws Exception;
    public String updateSubject(SubjectDto subjectDto) throws Exception;
    public String deleteSubject(String SubjectId) throws Exception;
    public SubjectDto searchSubject(String SubjectId) throws Exception;
    public ArrayList<SubjectDto> getAllSubject() throws Exception;
}
