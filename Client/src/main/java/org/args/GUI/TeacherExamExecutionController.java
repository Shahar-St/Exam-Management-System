package org.args.GUI;

import DatabaseAccess.Requests.ExecuteExam.RaiseHandRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.args.Client.ITeacherExecuteExamData;

public class TeacherExamExecutionController {

    @FXML
    private ListView<String> raisedHandsListView;

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

    ITeacherExecuteExamData model;

    public void setModel(ITeacherExecuteExamData model) {
        this.model = model;
    }

    public void initialize()
    {
        setModel(ClientApp.getModel());
        examNameLabel.setText(model.getCurrentExecutedExamTitle());
        launchTimeLabel.setText(model.getCurrentExecutedExamLaunchTime());
        raisedHandsListView.setItems(model.getCurrentHandsRaised());
    }

    @FXML
    void sendTimeExtensionRequest(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid input");
        if (!ClientApp.isNumeric(extensionTimeField.getText()))
        {
            alert.setContentText("Time field must contain an integer!");
            alert.showAndWait();
        }
        else if (extensionReasonField.getText().isEmpty())
        {
            alert.setContentText("Reason field cannot be empty!");
            alert.showAndWait();
        }
        else
        {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Time Extension Request");
            alert.setHeaderText(null);
            alert.setContentText("Time Extension Request sent to the dean successfully!");
            alert.showAndWait();
            model.sendTimeExtensionRequest(Integer.parseInt(extensionTimeField.getText()),extensionReasonField.getText());
            sendButton.setDisable(true);
            extensionTimeField.setDisable(true);
            extensionReasonField.setDisable(true);
        }
    }

    @FXML
    void solveSelected(ActionEvent event) {
        String currentStudentName = raisedHandsListView.getSelectionModel().getSelectedItem();
        model.solveRaisedHand(currentStudentName);
        if(model.getCurrentHandsRaised().isEmpty())
            solveButton.setDisable(true);
    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        if (model.getCurrentHandsRaised().size() > 0)
            solveButton.setDisable(false);

    }
}
