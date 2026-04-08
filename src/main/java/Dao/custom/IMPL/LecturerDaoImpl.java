package Dao.custom.IMPL;

import Dao.CrudUtil;
import Dao.custom.LecturerDao;
import Entity.LecturerEntity;

import java.sql.ResultSet;
import java.util.ArrayList;

public class LecturerDaoImpl implements LecturerDao {
    @Override
    public boolean save(LecturerEntity lecturerEntity) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO Lecturer (lecturer_id, name, qualification, department, email, contact) VALUES (?, ?, ?, ?, ?, ?)",
                lecturerEntity.getLecturerId(),
                lecturerEntity.getName(),
                lecturerEntity.getQualification(),
                lecturerEntity.getDepartment(),
                lecturerEntity.getEmail(),
                lecturerEntity.getContact());
    }

    @Override
    public boolean update(LecturerEntity lecturerEntity) throws Exception {
        return CrudUtil.executeUpdate("UPDATE Lecturer SET name = ?, qualification = ?, department = ?, email = ?, contact = ? WHERE lecturer_id = ?",
                lecturerEntity.getName(),
                lecturerEntity.getQualification(),
                lecturerEntity.getDepartment(),
                lecturerEntity.getEmail(),
                lecturerEntity.getContact(),
                lecturerEntity.getLecturerId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.executeUpdate("DELETE FROM Lecturer WHERE lecturer_id = ?",s);
    }

    @Override
    public LecturerEntity search(String s) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Lecturer WHERE lecturer_id = ?",s);
        if(rst.next()){
            return new LecturerEntity(rst.getString("lecturer_id"),
                    rst.getString("name"),
                    rst.getString("qualification"),
                    rst.getString("department"),
                    rst.getString(" email"),
                    rst.getString("contact"));
        }
        return null;
    }

    @Override
    public ArrayList<LecturerEntity> getALL() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Lecturer");
        ArrayList<LecturerEntity> lecturerEntities = new ArrayList<>();
        while (rst.next()){
            lecturerEntities.add(new LecturerEntity(
                    rst.getString("lecturer_id"),
                    rst.getString("name"),
                    rst.getString("qualification"),
                    rst.getString("department"),
                    rst.getString(" email"),
                    rst.getString("contact")));
        }
        return lecturerEntities;
    }
}