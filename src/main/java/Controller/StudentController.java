package Controller;

import DB.DBConnection;
import Dto.tm.StudentTm;
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
import Service.custom.StudentService;
import Service.ServiceFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    public Button CourseControlbtn;
    public Button StudentControlbtn;
    public Button LectureControlbtn;
    public Button SubjectControlbtn;
    public Button AttendanceControlbtn;
    public Button Homebtn;
    public Button signOutbtn;
    public TextField txtStudentId;
    public TextField txtStudentName;
    public TextField txtStudentRegNumber;
    public TextField txtStudentEmail;
    public TextField txtStudentContact;
    public TextField txtStudentCourseId;
    public DatePicker dateDob;
    public ChoiceBox<String> choiceStudentGender;
    public AnchorPane ancStudent;
    public TableView<StudentTm> tblStudent;
    public TableColumn<StudentTm, String> studentIdcol;
    public TableColumn<StudentTm, String> studentNamecol;
    public TableColumn<StudentTm, String> studentRegNumbercol;
    public TableColumn<StudentTm, String> studentGendercol;
    public TableColumn<StudentTm, Date> studentDobcol;
    public TableColumn<StudentTm, String> studentEmailcol;
    public TableColumn<StudentTm, String> studentContactcol;
    public TableColumn<StudentTm, String> studentCourseIdcol;

    private Connection connection;

    private ObservableList<StudentTm> addStudentsListD;
    private final StudentService studentService = (StudentService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.STUDENT);
    private ToggleGroup genderGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadGenderOptions();
        addStudentsShowListData();

        tblStudent.setOnMouseClicked(event -> onStudentTableClick());
    }

    private void loadGenderOptions() {
        choiceStudentGender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
    }

    public ObservableList<StudentTm> addStudentsListData() {
        ObservableList<StudentTm> listStudents = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Student";

        try (PreparedStatement prepare = connection.prepareStatement(sql);
             ResultSet rst = prepare.executeQuery()) {

            while (rst.next()) {
                StudentTm studentTm = new StudentTm(
                        rst.getString("studentId"),
                        rst.getString("name"),
                        rst.getString("regNumber"),
                        rst.getString("gender"),
                        rst.getDate("dob"),
                        rst.getString("email"),
                        rst.getString("contact"),
                        rst.getString("courseId")
                );
                listStudents.add(studentTm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listStudents;
    }

    public void addStudentsShowListData() {
        addStudentsListD = addStudentsListData();

        studentIdcol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentNamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentRegNumbercol.setCellValueFactory(new PropertyValueFactory<>("regNumber"));
        studentGendercol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        studentDobcol.setCellValueFactory(new PropertyValueFactory<>("dob"));
        studentEmailcol.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentContactcol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        studentCourseIdcol.setCellValueFactory(new PropertyValueFactory<>("courseId"));

        tblStudent.setItems(addStudentsListD);
    }

    public void onStudentTableClick() {
        StudentTm selectedStudent = tblStudent.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            txtStudentId.setText(selectedStudent.getStudentId());
            txtStudentName.setText(selectedStudent.getName());
            txtStudentRegNumber.setText(selectedStudent.getRegNumber());
            txtStudentEmail.setText(selectedStudent.getEmail());
            txtStudentContact.setText(selectedStudent.getContact());
            txtStudentCourseId.setText(selectedStudent.getCourseId());

            if (selectedStudent.getDob() != null) {
                dateDob.setValue(LocalDate.parse(selectedStudent.getDob().toLocaleString()));
            }

            choiceStudentGender.setValue(selectedStudent.getGender());
        }
    }

    public void signOut(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Logout Confirmation");
            alert.setContentText("Logout?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                signOutbtn.getScene().getWindow().hide();
                Parent load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
                new Stage() {{
                    setScene(new Scene(load));
                    show();
                }};
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stuCourseM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CourseControlPage.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void stuLectureM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LecturePageA.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void stuSubjectM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SubjectPageA.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void stuAttendanceM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AttendancePageA.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
    public void CHomeM(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminDashboard.fxml")));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }

    public void StudentSearchOnAction(ActionEvent actionEvent) {
        String sql = "SELECT * FROM Student WHERE studentId=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, txtStudentId.getText());
            ResultSet rst = stmt.executeQuery();

            if (rst.next()) {
                txtStudentName.setText(rst.getString("name"));
                txtStudentRegNumber.setText(rst.getString("regNumber"));
                choiceStudentGender.setValue(rst.getString("gender"));
                dateDob.setValue(rst.getDate("dob").toLocalDate());
                txtStudentEmail.setText(rst.getString("email"));
                txtStudentContact.setText(rst.getString("contact"));
                txtStudentCourseId.setText(rst.getString("courseId"));
            } else {
                new Alert(Alert.AlertType.WARNING, "Student Not Found").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred, Try again").show();
        }
    }

    public void StudentDeleteOnAction(ActionEvent actionEvent) {
        String sql = "DELETE FROM Student WHERE studentId=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, txtStudentId.getText());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully!").show();
                clearStudentFields();
                addStudentsShowListData();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred, Try again").show();
        }
    }

    public void StudentUpdateOnAction(ActionEvent actionEvent) {
        String sql = "UPDATE Student SET name=?, regNumber=?, gender=?, dob=?, email=?, contact=?, courseId=? WHERE studentId=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, txtStudentName.getText());
            stmt.setString(2, txtStudentRegNumber.getText());
            stmt.setString(3, choiceStudentGender.getValue());
            stmt.setDate(4, Date.valueOf(dateDob.getValue()));
            stmt.setString(5, txtStudentEmail.getText());
            stmt.setString(6, txtStudentContact.getText());
            stmt.setString(7, txtStudentCourseId.getText());
            stmt.setString(8, txtStudentId.getText());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully").show();
                addStudentsShowListData();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred, Try again").show();
        }
    }

    public void StudentSaveOnAction(ActionEvent actionEvent) {
        String sql = "INSERT INTO Student (studentId, name, regNumber, gender, dob, email, contact, courseId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, txtStudentId.getText());
            stmt.setString(2, txtStudentName.getText());
            stmt.setString(3, txtStudentRegNumber.getText());
            stmt.setString(4, choiceStudentGender.getValue());
            stmt.setDate(5, Date.valueOf(dateDob.getValue()));
            stmt.setString(6, txtStudentEmail.getText());
            stmt.setString(7, txtStudentContact.getText());
            stmt.setString(8, txtStudentCourseId.getText());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully").show();
                addStudentsShowListData();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred, Try again").show();
        }
    }

    // clear Fields
    private void clearStudentFields() {
        txtStudentId.clear();
        txtStudentName.clear();
        txtStudentRegNumber.clear();
        txtStudentEmail.clear();
        txtStudentContact.clear();
        txtStudentCourseId.clear();
        dateDob.setValue(null);
        choiceStudentGender.setValue(null);
    }


}
