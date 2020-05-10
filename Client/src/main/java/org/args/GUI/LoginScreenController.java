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

import java.io.IOException;

public class LoginScreenController {

    private ClientApp clientApp=null;

    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @FXML // fx:id="usernameField"
    private TextField usernameField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordField"
    private PasswordField passwordField; // Value injected by FXMLLoader

    @FXML
    void loginAttempt(ActionEvent event) throws IOException {
        String userName = usernameField.getText();
        String password = passwordField.getText();
        LoginRequest request = new LoginRequest(userName,password);
        ClientApp.sendRequest(request);


    }

    public void setClientApp(ClientApp clientApp) {
        if(this.clientApp == null){
            this.clientApp = clientApp;
        }

    }

}
