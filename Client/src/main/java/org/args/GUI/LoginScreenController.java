/**
 * Sample Skeleton for 'LoginScreen.fxml' Controller Class
 */

package org.args.GUI;

import DatabaseAccess.Requests.LoginRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginScreenController {

    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @FXML // fx:id="usernameField"
    private TextField usernameField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordField"
    private PasswordField passwordField; // Value injected by FXMLLoader

    @FXML
    void loginAttempt(ActionEvent event) {
        ClientApp.setRoot(QuestionManagementScreen.fxml);



    }

}
