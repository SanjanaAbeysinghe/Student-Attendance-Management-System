package Controller;

import DB.DBConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public AnchorPane ancMain;
    public TextField txtuserName;
    public PasswordField txtpassword;
    public Button loginbtn;
    public Button regbtn;

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet rst;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> txtuserName.requestFocus());
    }

    public void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    public void loginAdmin() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
        try {
            if (txtuserName.getText().isEmpty() && txtpassword.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please Enter Username and Password");
                return;
            } else if (txtuserName.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please Enter Username");
                return;
            } else if (txtpassword.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please Enter Password");
                return;
            }

            String checkSql = "SELECT * FROM User WHERE userName = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setString(1, txtuserName.getText());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {

                String dbPassword = rs.getString("password");
                String role = rs.getString("role");


                if (!dbPassword.equals(txtpassword.getText())) {
                    showAlert(Alert.AlertType.ERROR, "Wrong Password");
                    return;
                }

                showAlert(Alert.AlertType.INFORMATION, "Login Successful!");

                loginbtn.getScene().getWindow().hide();

                if (role.equalsIgnoreCase("admin")) {

                    Parent load = FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(load));
                    stage.show();

                } else if (role.equalsIgnoreCase("lecturer")) {

                    Parent load = FXMLLoader.load(getClass().getResource("/view/LecturerDashboard.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(load));
                    stage.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleRegister(javafx.event.ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/RegisterPage.fxml"));

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}