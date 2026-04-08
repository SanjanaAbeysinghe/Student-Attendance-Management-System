module sams {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;


    opens com.cmjd.ijse to javafx.fxml;
    exports com.cmjd.ijse;
    exports Controller;
    opens Controller to javafx.fxml;
    opens Dto.tm to javafx.base;
    exports Dto.tm;
    exports Controller.LectuerController;
    opens Controller.LectuerController to javafx.fxml;
    exports Controller.AdminController;
    opens Controller.AdminController to javafx.fxml;
}