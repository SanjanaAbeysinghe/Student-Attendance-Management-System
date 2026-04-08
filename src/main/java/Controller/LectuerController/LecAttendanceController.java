package Controller.LectuerController;

import DB.DBConnection;
import Dto.tm.AttendanceTm;
import Service.ServiceFactory;
import Service.custom.AttendanceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class LecAttendanceController implements Initializable {
    public Button CourseControlbtn;
    public Button StudentControlbtn;
    public Button LectureControlbtn;
    public Button SubjectControlbtn;
    public Button AttendanceControlbtn;
    public Button signOutbtn;
    public Button Homebtn;
    public AnchorPane ancAttendance;

    public TextField txtAttendanceStudentId;
    public TextField txtAttendanceClassId;
    public TextField txtAttendanceStatus;
    public TextField txtAttendanceMarkedTime;
    public TableView<AttendanceTm> tblAttendance;
    public TableColumn<AttendanceTm, String> attendanceIdcol;
    public TableColumn<AttendanceTm, String> attendanceStudentIdcol;
    public TableColumn<AttendanceTm, String> attendanceClassIdcol;
    public TableColumn<AttendanceTm, String> attendanceStatuscol;
    public TableColumn<AttendanceTm, String> attendanceMarkedTimecol;
    public TextField txtAttendanceId;

    private final AttendanceService attendanceService = (AttendanceService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.ATTENDANCE);

    private ToggleGroup genderGroup;
    Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        attendanceIdcol.setCellValueFactory(new PropertyValueFactory<>("AId"));
        attendanceStudentIdcol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        attendanceClassIdcol.setCellValueFactory(new PropertyValueFactory<>("classId"));
        attendanceStatuscol.setCellValueFactory(new PropertyValueFactory<>("status"));
        attendanceMarkedTimecol.setCellValueFactory(new PropertyValueFactory<>("markedTime"));

        loadAttendances();

        tblAttendance.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtAttendanceId.setText(newVal.getAId());
                txtAttendanceStudentId.setText(newVal.getStudentId());
                txtAttendanceClassId.setText(newVal.getClassId());
                txtAttendanceStatus.setText(newVal.getStatus());
                txtAttendanceMarkedTime.setText(newVal.getMarkedTime());
            }
        });
    }

    // load attendance to table
    private void loadAttendances() {
        ObservableList<AttendanceTm> attendances = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Attendance");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                attendances.add(new AttendanceTm(
                        rs.getString("AId"),
                        rs.getString("studentId"),
                        rs.getString("classId"),
                        rs.getString("status"),
                        rs.getString("markedTime")
                ));
            }
            tblAttendance.setItems(attendances);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void signOut(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logout Confirmation");
            alert.setContentText("logout?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                signOutbtn.getScene().getWindow().hide();
                Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginPage.fxml")));
                Scene scene = new Scene(load);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void ACourseM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CoursePageL.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void AStudentM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/StudentPageL.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void ALectureM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LecturePageL.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void ASubjectM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SubjectPageL.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void AHomeM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LecturersDashboard.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }

    // Save
    public void AttendanceSaveOnAction(ActionEvent actionEvent) {
        String id = txtAttendanceId.getText();
        String studentId = txtAttendanceStudentId.getText();
        String classId = txtAttendanceClassId.getText();
        String status = txtAttendanceStatus.getText();
        String markedTime = txtAttendanceMarkedTime.getText();

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Attendance (AId, studentId, classId, status, markedTime) VALUES (?, ?, ?, ?, ?)"
            );
            ps.setString(1, id);
            ps.setString(2, studentId);
            ps.setString(3, classId);
            ps.setString(4, status);
            ps.setString(5, markedTime);

            if (ps.executeUpdate() > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully").show();
                loadAttendances();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    // Search
    public void AttendanceSearchOnAction(ActionEvent actionEvent) {
        String id = txtAttendanceId.getText();

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Attendance WHERE AId = ?"
            );
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtAttendanceStudentId.setText(rs.getString("studentId"));
                txtAttendanceClassId.setText(rs.getString("classId"));
                txtAttendanceStatus.setText(rs.getString("status"));
                txtAttendanceMarkedTime.setText(rs.getString("markedTime"));
            } else {
                new Alert(Alert.AlertType.WARNING, "Error occurred, Try again").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    // update
    public void AttendanceUpdateOnAction(ActionEvent actionEvent) {
        String id = txtAttendanceId.getText();
        String studentId = txtAttendanceStudentId.getText();
        String classId = txtAttendanceClassId.getText();
        String status = txtAttendanceStatus.getText();
        String markedTime = txtAttendanceMarkedTime.getText();

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE Attendance SET studentId = ?, classId = ?, status = ?, markedTime = ? WHERE AId = ?"
            );
            ps.setString(1, studentId);
            ps.setString(2, classId);
            ps.setString(3, status);
            ps.setString(4, markedTime);
            ps.setString(5, id);

            if (ps.executeUpdate() > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully").show();
                loadAttendances();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Update failed. ID not found").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    //  clear fields
    private void clearFields() {
        txtAttendanceId.clear();
        txtAttendanceStudentId.clear();
        txtAttendanceClassId.clear();
        txtAttendanceStatus.clear();
        txtAttendanceMarkedTime.clear();
    }

    public void stuAttendanceM(ActionEvent actionEvent){
    }
}