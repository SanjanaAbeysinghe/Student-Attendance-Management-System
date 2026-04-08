package Dao;

import Dao.custom.IMPL.StudentDaoImpl;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory(){

    }
    public static DaoFactory getInstance(){
        if (daoFactory==null){
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }
    public Dao.SuperDao getDao(DaoTypes type){
        switch (type){
            case STUDENT: return new StudentDaoImpl();
            case COURSE: return null;
            case ATTENDANCE: return null;
            case LECTURER: return null;
            case USER: return null;
            case SUBJECT: return null;
            default: return null;
        }

    }
    public enum DaoTypes{
        STUDENT, COURSE,ATTENDANCE,CLASS,SUBJECT,LECTURER,USER,CLASS_LECTURER

    }
}