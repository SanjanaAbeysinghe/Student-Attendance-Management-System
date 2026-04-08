package Service.custom;

import Dto.AttendanceDto;
import Service.SuperService;

import java.util.ArrayList;

public interface AttendanceService extends SuperService {
    public String saveAttendance(AttendanceDto attendanceDto) throws Exception;
    public String updateAttendance(AttendanceDto attendanceDto) throws Exception;
    public AttendanceDto searchAttendance (String Id) throws Exception;
    public ArrayList<AttendanceDto> getAllAttendance() throws Exception;
}
