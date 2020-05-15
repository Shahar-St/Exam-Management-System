/**
 * Sample Skeleton for 'AlertPopUp.fxml' Controller Class
 */

package org.args.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AlertPopUpController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="showText"
    private Label showText; // Value injected by FXMLLoader

    @FXML // fx:id="okBtn"
    private Button okBtn; // Value injected by FXMLLoader

    @FXML
    void okBtnAction(ActionEvent event) {
        okBtn.getScene().getWindow().hide();

    }

    public void setShowText(String message) {
        this.showText.setText(message);
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert showText != null : "fx:id=\"showText\" was not injected: check your FXML file 'AlertPopUp.fxml'.";
        assert okBtn != null : "fx:id=\"okBtn\" was not injected: check your FXML file 'AlertPopUp.fxml'.";
    }
}