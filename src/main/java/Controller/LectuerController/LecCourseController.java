package Controller.LectuerController;

import DB.DBConnection;
import Dto.tm.CourseTm;
import Service.ServiceFactory;
import Service.custom.CourseService;
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

public class LecCourseController implements Initializable {
    public Button CourseControlbtn;
    public Button StudentControlbtn;
    public Button LectureControlbtn;
    public Button SubjectControlbtn;
    public Button AttendanceControlbtn;
    public Button signOutbtn;
    public Button Homebtn;
    public TableColumn<CourseTm, String> courseIdcol;
    public TableColumn<CourseTm, String> courseNamecol;
    public TableColumn<CourseTm, String> courseDescriptioncol;
    public TableColumn<CourseTm, Integer> courseDurationWeekscol;
    public TableView<CourseTm> tblCourse;
    public TextField txtCourseId;
    public TextField txtCourseName;
    public TextField txtCourseDescription;
    public TextField txtCourseDurationWeeks;
    public AnchorPane ancCourse;
    public Button CHomebtn;

    private Connection connection;
    private final CourseService courseService = (CourseService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.COURSE);
    private ToggleGroup genderGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadCourseTable();
        setTableRowListener();
    }

    // load data to table
    private void loadCourseTable() {
        ObservableList<CourseTm> courseList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Course";

        try (PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                courseList.add(new CourseTm(
                        rs.getString("course_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("durationWeeks")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        courseIdcol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseNamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        courseDescriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        courseDurationWeekscol.setCellValueFactory(new PropertyValueFactory<>("durationWeeks"));

        tblCourse.setItems(courseList);
    }

    private void setTableRowListener() {
        tblCourse.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtCourseId.setText(newVal.getCourseId());
                txtCourseName.setText(newVal.getName());
                txtCourseDescription.setText(newVal.getDescription());
                txtCourseDurationWeeks.setText(String.valueOf(newVal.getDurationWeeks()));
            }
        });
    }

    public void signOut(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logout Confirmation");
            alert.setContentText("Logout?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                signOutbtn.getScene().getWindow().hide();
                Parent load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
                Scene scene = new Scene(load);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void CStudentM(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent,"/view/StudentPageL.fxml");
    }

    public void CLectureM(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent,"/view/LecturePageL.fxml");
    }

    public void CSubjectM(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent,"/view/SubjectPageL.fxml");
    }

    public void CAttendenceM(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent,"/view/AttendancePageL.fxml");
    }

    public void CHomeM(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent,"/view/LecturerDashboard.fxml");
    }

    private void loadPage(ActionEvent event, String path) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }

    // save
    public void CourseSaveOnAction(ActionEvent actionEvent) {
        String courseId = txtCourseId.getText().trim();
        String name = txtCourseName.getText().trim();
        String description = txtCourseDescription.getText().trim();
        String durationWeeks = txtCourseDurationWeeks.getText().trim();
        String sql = "INSERT INTO Course(course_id, name, description, durationWeeks) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, courseId);
            pst.setString(2, name);
            pst.setString(3, description);
            pst.setInt(4, Integer.parseInt(durationWeeks));

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully").show();
                clearFields();      // Clear fields after saving
                loadCourseTable();  // Refresh table data
            } else {
                new Alert(Alert.AlertType.ERROR, "Save Failed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error : " + e.getMessage()).show();
        }

    }

    // update
    public void CourseUpdateOnAction(ActionEvent actionEvent) {
        String courseId = txtCourseId.getText().trim();
        String name = txtCourseName.getText().trim();
        String description = txtCourseDescription.getText().trim();
        String durationStr = txtCourseDurationWeeks.getText().trim();
        if (courseId.isEmpty() || name.isEmpty() || description.isEmpty() || durationStr.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all Fields").show();
            return;
        }

        int durationWeeks;
        try {
            durationWeeks = Integer.parseInt(durationStr);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Duration must be a Number").show();
            return;
        }

        String sql = "UPDATE Course SET name = ?, description = ?, durationWeeks = ? WHERE course_id = ?";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, description);
            pst.setInt(3, durationWeeks);
            pst.setString(4, courseId);

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully").show();
                clearFields();
                loadCourseTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Update failed. ID not found").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }

    }

    // delete
    public void CourseDeleteOnAction(ActionEvent actionEvent) {
        String courseId = txtCourseId.getText().trim();

        if (courseId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a course").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.YES) {
            String sql = "DELETE FROM Course WHERE course_id = ?";

            try (PreparedStatement pst = connection.prepareStatement(sql)) {
                pst.setString(1, courseId);

                int affectedRows = pst.executeUpdate();

                if (affectedRows > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted successfully").show();
                    clearFields();
                    loadCourseTable();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Delete failed. ID not found").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
            }
        }

    }

    // clear Fields
    private void clearFields() {
        txtCourseId.clear();
        txtCourseName.clear();
        txtCourseDescription.clear();
        txtCourseDurationWeeks.clear();
    }
}