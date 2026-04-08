package Controller;

import DB.DBConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class RegisterController {

    @FXML
    private TextField txtuserName;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private TextField txtRole;

    @FXML
    private Button regisbtn;

    private Connection connection;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> txtuserName.requestFocus());
    }

    public void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void registerUser(ActionEvent event) {
        try {
            connection = DBConnection.getInstance().getConnection();

            if (txtuserName.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please Enter Username");
                return;
            }

            if (txtpassword.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please Enter Password");
                return;
            }

            if (txtRole.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please Enter Role");
                return;
            }

            String checkSql = "SELECT * FROM user WHERE userName = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setString(1, txtuserName.getText());

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                showAlert(Alert.AlertType.ERROR, "User already exists!");
                return;
            }

            // register new user
            String insertSql = "INSERT INTO user (userName, password, role) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertSql);
            insertStmt.setString(1, txtuserName.getText());
            insertStmt.setString(2, txtpassword.getText());
            insertStmt.setString(3, txtRole.getText());

            insertStmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "User Registered Successfully!");

            Stage currentStage = (Stage) regisbtn.getScene().getWindow();
            currentStage.close();

            String role = txtRole.getText();

            if (role.equalsIgnoreCase("admin")) {

                Parent root = FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } else if (role.equalsIgnoreCase("lecturer")) {

                Parent root = FXMLLoader.load(getClass().getResource("/view/LecturerDashboard.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }
    }
}