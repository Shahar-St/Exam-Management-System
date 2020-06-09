package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.args.Client.IViewDeanTimeExtensionData;

public class ViewDeanTimeExtensionController {

    @FXML
    private ListView<String> requestListView;

    @FXML
    private Button acceptButton;

    @FXML
    private Button rejectButton;

    IViewDeanTimeExtensionData model;

    public void setModel(IViewDeanTimeExtensionData model) {
        this.model = model;
    }

    @FXML
    void acceptExtension(ActionEvent event) {

    }

    @FXML
    void rejectExtension(ActionEvent event) {

    }

}
