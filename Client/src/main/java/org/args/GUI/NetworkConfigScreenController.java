package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class NetworkConfigScreenController {

    @FXML
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    private Button okButton;

    @FXML
    void paneKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            switchToLoginScreen();
        }

    }

    @FXML
    void switchToLoginScreen() {
        if (!ipField.getText().equals(""))
        {
            ClientApp.setHost(ipField.getText());
        }

        if(!portField.getText().equals(""))
        {
            ClientApp.setPort(Integer.parseInt(portField.getText()));
        }

        ClientApp.setRoot("LoginScreen");
    }

    @FXML
    public void initialize() {
        ipField.setText(ClientApp.getHost());
        portField.setText(Integer.toString(ClientApp.getPort()));
    }

}
