package org.args.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.args.Client.ITeacherExecuteExamData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;


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

    @FXML
    private ImageView imageBackButton;

    @FXML
    private Label endTimeLabel;

    ITeacherExecuteExamData model;


    public void setModel(ITeacherExecuteExamData model) {
        this.model = model;
    }

    public void initialize() {
        setModel(ClientApp.getModel());
        setTimer();
        examNameLabel.setText(model.getCurrentExecutedExamTitle());
        launchTimeLabel.setText(model.getCurrentExecutedExamLaunchTime());
        raisedHandsListView.setItems(model.getCurrentHandsRaised());
        endTimeLabel.textProperty().bind(model.getCurrentExecutedExamEndTimeProperty());
    }

    @FXML
    void sendTimeExtensionRequest(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid input");
        if (!ClientApp.isNumeric(extensionTimeField.getText())) {
            alert.setContentText("Time field must contain an integer!");
            alert.showAndWait();
        } else if (extensionReasonField.getText().isEmpty()) {
            alert.setContentText("Reason field cannot be empty!");
            alert.showAndWait();
        } else {
            model.sendTimeExtensionRequest(Integer.parseInt(extensionTimeField.getText()), extensionReasonField.getText());
            sendButton.setDisable(true);
            extensionTimeField.setDisable(true);
            extensionReasonField.setDisable(true);
        }
    }

    @FXML
    void solveSelected(ActionEvent event) {
        String currentStudentName = raisedHandsListView.getSelectionModel().getSelectedItem();
        model.solveRaisedHand(currentStudentName);
        if (model.getCurrentHandsRaised().isEmpty())
            solveButton.setDisable(true);
    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        if (model.getCurrentHandsRaised().size() > 0)
            solveButton.setDisable(false);
    }

    @FXML
    void backButtonClicked(MouseEvent event) {
        ClientApp.setRoot("MainScreen");
    }

    private void setTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!ClientApp.isRunning()) {
                    // in case that the window has been closed
                    timer.cancel();
                    timer.purge();
                    return;
                } else if (LocalDateTime.now().isEqual(model.getCurrentExecutedExamEndLocalDateTime())
                || LocalDateTime.now().isAfter(model.getCurrentExecutedExamEndLocalDateTime())) {
                    timer.cancel();
                    timer.purge();
                    model.endExam();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Attention!");
                        alert.setContentText("Exam has ended!");
                        alert.showAndWait();
                        ClientApp.setRoot("MainScreen");
                    });
                }
            }
        }, model.getCurrentExecutedExamDuration() * 60 * 1000, 60000 ); //initial check is original duration, then check every minute.
    }
}
