package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import org.args.Client.IViewDeanTimeExtensionData;

import java.util.Optional;

public class ViewDeanTimeExtensionController {

    @FXML
    private ListView<String> requestListView;

    @FXML
    private Button acceptButton;

    @FXML
    private Button rejectButton;

    IViewDeanTimeExtensionData model;

    TextInputDialog dialog;


    public void setModel(IViewDeanTimeExtensionData model) {
        this.model = model;
    }

    private String getSelectedExamId() {
        return requestListView.getSelectionModel().getSelectedItem().substring(1, 7);
    }

    @FXML
    void acceptExtension(ActionEvent event) {

        dialog.setTitle("Grant Time Extension");
        dialog.setHeaderText("Accept request");
        dialog.setContentText("Please enter granted time:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(extension -> {
            if (ClientApp.isNumeric(extension)) {
                model.acceptExtension(extension, getSelectedExamId());
                model.removeRequest(requestListView.getSelectionModel().getSelectedItem());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter a number!");
                alert.showAndWait();
            }
        });
    }

    @FXML
    void rejectExtension(ActionEvent event) {
        dialog.setTitle("Reject Time Extension");
        dialog.setContentText("Please enter reason for rejecting:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(reason -> {
            model.rejectExtension(reason, getSelectedExamId());
            model.removeRequest(requestListView.getSelectionModel().getSelectedItem());
        });
    }

    public void initialize() {
        setModel(ClientApp.getModel());
        requestListView.setItems(model.getObservableTimeExtensionRequestsList());
        dialog = new TextInputDialog();
    }

    @FXML
    void switchToMainScreen() {
        ClientApp.setRoot("MainScreen");
    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        if (!requestListView.getItems().isEmpty()) {
            rejectButton.setDisable(false);
            acceptButton.setDisable(false);
        }
    }

}
