/**
 * Sample Skeleton for 'MainScreen.fxml' Controller Class
 */

package org.args.GUI;

import DatabaseAccess.Requests.ExecuteExam.TakeExamRequest;
import DatabaseAccess.Requests.Statistics.TeacherStatisticsRequest;
import DatabaseAccess.Requests.SubjectsAndCoursesRequest;
import Util.Pair;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.args.Client.IMainScreenData;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainScreenController {

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Label welcomeLabel;

    @FXML
    void switchToQuestionManagement(ActionEvent event) throws IOException {
        model.loadSubjects();
        ClientApp.setRoot("QuestionManagementScreen");
    }

    private IMainScreenData model;

    public void setModel(IMainScreenData dataModel)
    {
        if(model == null)
            this.model = dataModel;
    }

    private void generateGreeting()
    {
        Date dt = new Date();
        int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting = null;
        if (hours >= 5 && hours <= 12) {
            greeting = "Good Morning";
        } else if (hours >= 12 && hours < 16) {
            greeting = "Good Afternoon";
        } else if (hours >= 16 && hours < 21) {
            greeting = "Good Evening";
        } else {
            greeting = "Good Night";
        }
        String fullGreeting = greeting + ", " + model.getName();
        welcomeLabel.setText(fullGreeting);
    }

    private void initTeacher() throws IOException
    {
        button1.setText("Question Management");
        button1.setOnAction(event -> {
            try {
                switchToQuestionManagement(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button2.setText("Statistical Analysis");
        button2.setOnAction(this::switchToStatsScreen);
        button3.setText("Exam Management");
        button3.setOnAction(this::switchToExamManagementScreen);
    }

    private void initStudent()
    {
        button1.setText("Exam Execution");
        button1.setOnAction(this::switchToStudentExamExecutionScreen);
        button2.setText("Past Exams");
        button2.setOnAction(this::switchToStatsScreen);
        button3.setVisible(false);
    }

    private void initDean()
    {
        button1.setText("Statistical Analysis");
        button1.setOnAction(this::switchToStatsScreen);
        button2.setText("View Reports");
        button2.setOnAction(this::switchToReportsScreen);
        button3.setText("Pending Requests");
        button3.setOnAction(this::switchToExtensionRequestsScreen);
    }



    private void initAccordingToPermission() throws IOException {
        switch (model.getPermission()){
            case "teacher":
                initTeacher();
                break;
            case "student":
                initStudent();
                break;
            case "dean":
                initDean();
                break;
        }
    }

    @FXML
    public void initialize() throws IOException {
        setModel(ClientApp.getModel());
        generateGreeting();
        initAccordingToPermission();
    }

    @FXML
    void switchToExamManagementScreen (ActionEvent event)
    {
        model.loadSubjects();
        ClientApp.setRoot("ExamManagementScreen");
    }
    @FXML
    void switchToStatsScreen (ActionEvent event)
    {

    }
    @FXML
    void switchToStudentExamExecutionScreen (ActionEvent event)
    {
        List<String> choices = new ArrayList<>();
        choices.add("Computerized Exam");
        choices.add("Manual Exam");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Computerized Exam", choices);
        dialog.setTitle("Perform Exam");
        dialog.setContentText("Select exam method:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(choice -> {
            if(choice.equals("Computerized Exam"))
            {
                prepCompExam();
            }
            else
                {
                prepManualExam();
            }

        });
    }

    private void prepManualExam() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Exam Code");
        dialog.setContentText("Please enter exam code:");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        AtomicBoolean advance = new AtomicBoolean(false);
        while (!advance.get())
        {
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(code -> {
                if (result.toString().length() != 4) {
                    alert.setHeaderText("Wrong number of digits");
                    alert.setContentText("Please enter a 4-digit code!");
                    alert.showAndWait();
                } else if (!ClientApp.isNumeric(result.toString())) {
                    alert.setHeaderText("Invalid exam code");
                    alert.setContentText("Code must only contain digits!");
                    alert.showAndWait();
                }else {
                    model.studentTakeManualExam(code);
                    advance.set(true);
                }
            });
            if (!result.isPresent())
                advance.set(true);
        }
    }


    void prepCompExam() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Personal Details");
        dialog.setHeaderText("Please enter your personal details:");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField code = new TextField();
        code.setPromptText("Code");
        TextField id = new TextField();
        id.setPromptText("ID");

        grid.add(new Label("Exam Code:"), 0, 0);
        grid.add(code, 1, 0);
        grid.add(new Label("ID:"), 0, 1);
        grid.add(id, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node OkButton = dialog.getDialogPane().lookupButton(loginButtonType);
        OkButton.setDisable(true);
        code.textProperty().addListener((observable, oldValue, newValue) -> {
            OkButton.setDisable(newValue.trim().isEmpty());
        });
        id.textProperty().addListener((observable, oldValue, newValue) -> {
            OkButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(code::requestFocus);

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(code.getText(), id.getText());
            }
            return null;
        });
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        AtomicBoolean advance = new AtomicBoolean(false);
        while (!advance.get())
        {
            Optional<Pair<String, String>> result = dialog.showAndWait();
            result.ifPresent(codeId -> {
                if (codeId.getFirst().length() != 4) {
                    alert.setHeaderText("Wrong number of digits");
                    alert.setContentText("Please enter a 4-digit code!");
                    alert.showAndWait();
                } else if (!ClientApp.isNumeric(codeId.getFirst())) {
                    alert.setHeaderText("Invalid exam code");
                    alert.setContentText("Code must only contain digits!");
                    alert.showAndWait();
                } else if (!ClientApp.isNumeric(codeId.getSecond())) {
                    alert.setHeaderText("Invalid ID");
                    alert.setContentText("ID must only contain digits!");
                    alert.showAndWait();
                }
                else {
                    model.studentTakeComputerizedExam(codeId.getFirst(),codeId.getSecond());
                    advance.set(true);
                }
            });
            if (!result.isPresent())
                advance.set(true);
        }
    }

    @FXML
    void switchToReportsScreen (ActionEvent event)
    {
        ClientApp.setRoot("ReportsScreen");
    }
    @FXML
    void switchToExtensionRequestsScreen (ActionEvent event)
    {
        ClientApp.setRoot("ExtensionRequestsScreen");
    }

}
