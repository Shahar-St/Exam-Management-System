package org.args.GUI;

import java.io.IOException;

import DatabaseAccess.Requests.LoginRequest;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        LoginRequest request = new LoginRequest("adar","poper");
        ClientApp.sendRequest(request);

    }
}
