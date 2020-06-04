package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TeacherExamExecutionController {

    @FXML
    private ListView<?> raisedHandsListView;

    @FXML
    private Button solveButton;

    @FXML
    private Label examNameLabel;

    @FXML
    private Label launchTimeLabel;

    @FXML
    private TextField extensionTimeField;

    @FXML
    private TextArea extensionReasonField;

    @FXML
    private Button sendButton;

    @FXML
    void sendTimeExtensionRequest(ActionEvent event) {

    }

    @FXML
    void solveSelected(ActionEvent event) {

    }

}
