package Service;

import Service.custom.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        if(serviceFactory == null){
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
    }

    public SuperService getService(ServiceType type){
        switch (type) {
            case STUDENT: return new StudentServiceImpl();
            case LECTURER: return new LecturerServiceImpl();
            case SUBJECT: return new SubjectServiceImpl();
            case ATTENDANCE : return new AttendanceServiceImpl();
            case COURSE: return new CourseServiceImpl();
            default: return null;
        }
    }

    public enum ServiceType{
        STUDENT, LECTURER, SUBJECT,ATTENDANCE,COURSE
    }
}