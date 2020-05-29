/**
 * Sample Skeleton for 'LoginScreen.fxml' Controller Class
 */

package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.args.Client.IMainScreenData;


public class LoginController {


    private IMainScreenData model;

    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader


    @FXML
    private Button networkButton;

    @FXML // fx:id="usernameField"
    private TextField usernameField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordField"
    private PasswordField passwordField; // Value injected by FXMLLoader

    @FXML
    void loginAttempt(ActionEvent event) {
        loginAttempt();
    }

    @FXML
    void paneKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loginAttempt();
        }
    }

    @FXML
    void switchToNetworkConfigScreen(ActionEvent event) {

        ClientApp.setRoot("NetworkConfigScreen");
    }

    public void setModel(IMainScreenData dataModel)
    {
        if (model == null)
            this.model = dataModel;
    }

    private void loginAttempt() {
        String userName = usernameField.getText();
        String password = passwordField.getText();
        model.login(userName,password);
    }


    public void initialize() {
        setModel(ClientApp.getModel());

    }

}
