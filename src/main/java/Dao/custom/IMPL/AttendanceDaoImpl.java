package Dao.custom.IMPL;

import Dao.custom.AttendanceDao;
import DB.DBConnection;
import Entity.AttendanceEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class AttendanceDaoImpl implements AttendanceDao {

    @Override
    public boolean save(AttendanceEntity attendanceEntity) throws Exception {
        String sql = "INSERT INTO attendance (id, student_id, class_id, status, marked_time) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, attendanceEntity.getAId());
        ps.setString(2, attendanceEntity.getStudentId());
        ps.setString(3, attendanceEntity.getClassId());
        ps.setString(4, attendanceEntity.getStatus());
        ps.setString(5, attendanceEntity.getMarkedTime());

        return ps.executeUpdate() > 0;
    }

    @Override
    public boolean update(AttendanceEntity attendanceEntity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public AttendanceEntity search(String s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<AttendanceEntity> getALL() throws Exception {
        return null;
    }
}