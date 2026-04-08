package Controller.LectuerController;

import DB.DBConnection;
import Dto.tm.SubjectTm;
import Service.ServiceFactory;
import Service.custom.SubjectService;
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

public class LecSubjectController implements Initializable {
    public Button CourseControlbtn;
    public Button StudentControlbtn;
    public Button LectureControlbtn;
    public Button SubjectControlbtn;
    public Button AttendenceControlbtn;
    public Button signOutbtn;
    public Button Homebtn;
    public AnchorPane ancSubject;
    public TextField txtSubjectId;
    public TextField txtSubjectName;
    public TextField txtSubjectCreditHours;
    public TextField txtSubjectCourseId;
    public TableView<SubjectTm> tblSubject;
    public TableColumn<SubjectTm, String> subjectIdcol;
    public TableColumn<SubjectTm, String> subjectNamecol;
    public TableColumn<SubjectTm, String> subjectCreditHourscol;
    public TableColumn<SubjectTm, String> subjectCourseIdcol;

    private final SubjectService subjectService = (SubjectService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.SUBJECT);
    private ToggleGroup genderGroup;

    Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        subjectIdcol.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        subjectNamecol.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        subjectCreditHourscol.setCellValueFactory(new PropertyValueFactory<>("creditHours"));
        subjectCourseIdcol.setCellValueFactory(new PropertyValueFactory<>("courseId"));

        loadSubjects();

        tblSubject.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtSubjectId.setText(newVal.getSubjectId());
                txtSubjectName.setText(newVal.getSubjectName());
                txtSubjectCreditHours.setText(newVal.getCreditHours());
                txtSubjectCourseId.setText(newVal.getCourseId());
            }
        });
    }

    private void loadSubjects() {
        ObservableList<SubjectTm> subjects = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Subject");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                subjects.add(new SubjectTm(
                        rs.getString("subjectId"),
                        rs.getString("subjectName"),
                        rs.getString("creditHours"),
                        rs.getString("courseId")
                ));
            }
            tblSubject.setItems(subjects);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void signOut(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logout Confirmation");
            alert.setContentText("Logout?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
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

    public void subCourseM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CoursePageL.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void subStudentM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/StudentPageL.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void subLectureM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LecturePageL.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void stuSubjectM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SubjectPageL.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }

    public void subAttendanceM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AttendancePageL.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void subHomeM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LecturerDashboard.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void SubjectSaveOnAction(ActionEvent actionEvent) {
        String subjectId = txtSubjectId.getText().trim();
        String subjectName = txtSubjectName.getText().trim();
        String creditHours = txtSubjectCreditHours.getText().trim();
        String courseId = txtSubjectCourseId.getText().trim();

        if (subjectId.isEmpty() || subjectName.isEmpty() || creditHours.isEmpty() || courseId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Fill All Fields").show();
            return;
        }

        String sql = "INSERT INTO Subject(subjectId, subjectName, creditHours, courseId) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, subjectId);
            pst.setString(2, subjectName);
            pst.setString(3, creditHours);
            pst.setString(4, courseId);

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully").show();
                clearFields();
                loadSubjects();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error occurred, Try again").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

//only access to admin

//    public void SubjectUpdateOnAction(ActionEvent actionEvent) {
//        String subjectId = txtSubjectId.getText().trim();
//        String subjectName = txtSubjectName.getText().trim();
//        String creditHours = txtSubjectCreditHours.getText().trim();
//        String courseId = txtSubjectCourseId.getText().trim();
//
//        if (subjectId.isEmpty() || subjectName.isEmpty() || creditHours.isEmpty() || courseId.isEmpty()) {
//            new Alert(Alert.AlertType.WARNING, "Please Fill All Fields").show();
//            return;
//        }
//
//        String sql = "UPDATE Subject SET subjectName = ?, creditHours = ?, courseId = ? WHERE subjectId = ?";
//
//        try (PreparedStatement pst = connection.prepareStatement(sql)) {
//            pst.setString(1, subjectName);
//            pst.setString(2, creditHours);
//            pst.setString(3, courseId);
//            pst.setString(4, subjectId);
//
//            int affectedRows = pst.executeUpdate();
//
//            if (affectedRows > 0) {
//                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully").show();
//                clearFields();
//                loadSubjects();
//            } else {
//                new Alert(Alert.AlertType.WARNING, "Error occurred, Try again").show();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
//        }
//    }

//only access to admin

//    public void SubjectDeleteOnAction(ActionEvent actionEvent) {
//        String subjectId = txtSubjectId.getText().trim();
//
//        if (subjectId.isEmpty()) {
//            new Alert(Alert.AlertType.WARNING, "Please select the Subject").show();
//            return;
//        }
//
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete?", ButtonType.YES, ButtonType.NO);
//        Optional<ButtonType> option = alert.showAndWait();
//
//        if (option.isPresent() && option.get() == ButtonType.YES) {
//            String sql = "DELETE FROM Subject WHERE subjectId = ?";
//
//            try (PreparedStatement pst = connection.prepareStatement(sql)) {
//                pst.setString(1, subjectId);
//
//                int affectedRows = pst.executeUpdate();
//
//                if (affectedRows > 0) {
//                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully").show();
//                    clearFields();
//                    loadSubjects();
//                } else {
//                    new Alert(Alert.AlertType.WARNING, "Error occurred, Try again").show();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
//            }
//        }
//    }


    // clear Fields
    private void clearFields() {
        txtSubjectId.clear();
        txtSubjectName.clear();
        txtSubjectCreditHours.clear();
        txtSubjectCourseId.clear();
    }

}