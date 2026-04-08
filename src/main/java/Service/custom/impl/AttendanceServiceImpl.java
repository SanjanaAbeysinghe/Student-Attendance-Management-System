package Service.custom.impl;

import Dao.custom.IMPL.AttendanceDaoImpl;
import Dao.custom.AttendanceDao;
import DB.DBConnection;
import Dto.AttendanceDto;
import Entity.AttendanceEntity;
import Service.custom.AttendanceService;

import java.sql.Connection;
import java.util.ArrayList;

public class AttendanceServiceImpl implements AttendanceService {
    AttendanceDao attendanceDao = new AttendanceDaoImpl();

    @Override
    public String saveAttendance(AttendanceDto attendanceDto) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            AttendanceEntity attendanceEntity = new AttendanceEntity(
                    attendanceDto.getAId(),
                    attendanceDto.getStudentId(),
                    attendanceDto.getClassId(),
                    attendanceDto.getStatus(),
                    attendanceDto.getMarkedTime()
            );

            boolean isSaved = attendanceDao.save(attendanceEntity);

            if (isSaved) {
                connection.commit();
                return "Success";
            } else {
                connection.rollback();
                return "Fail";
            }

        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return "Error";
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public String updateAttendance(AttendanceDto attendanceDto) throws Exception {return null;}

    @Override
    public AttendanceDto searchAttendance(String attendanceId) throws Exception {return null;}

    @Override
    public ArrayList<AttendanceDto> getAllAttendance() throws Exception {return null;}
}
