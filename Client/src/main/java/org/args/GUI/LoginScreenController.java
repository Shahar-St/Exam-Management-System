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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class LoginScreenController {


    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @FXML // fx:id="usernameField"
    private TextField usernameField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordField"
    private PasswordField passwordField; // Value injected by FXMLLoader

    @FXML
    void loginAttempt(ActionEvent event) throws IOException {
        loginAttempt();


    }

    @FXML
    void paneKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loginAttempt();
        }

    }

    private void loginAttempt() {
        String userName = usernameField.getText();
        String password = passwordField.getText();
        LoginRequest request = new LoginRequest(userName, password);
        ClientApp.sendRequest(request);
    }


    public void initialize() {

    }

}
