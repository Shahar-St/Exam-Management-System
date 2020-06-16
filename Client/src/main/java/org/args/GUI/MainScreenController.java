/*
  Sample Skeleton for 'MainScreen.fxml' Controller Class
 */

package org.args.GUI;

import Util.Pair;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.args.Client.IMainScreenData;

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
    private Button button4;

    @FXML
    private Button logOutButton;


    @FXML
    private Label welcomeLabel;

    //variables used for the student's exam execution details popups
    Alert errorAlert;
    AtomicBoolean advance;

    @FXML
    void switchToQuestionManagement(ActionEvent event) {
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
        int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting;
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

    private void initTeacher() {
        button1.setText("Question Management");
        button1.setOnAction(this::switchToQuestionManagement);
        button2.setText("Statistical Analysis");
        button2.setOnAction(this::switchToResultsScreen);
        button3.setText("Exam Management");
        button3.setOnAction(this::switchToExamManagementScreen);
        button4.setOnAction(this::switchToPendingScreen);
    }

    private void switchToPendingScreen(ActionEvent event) {
        ClientApp.setRoot("TeacherPendingExamsScreen");
    }

    private void initStudent()
    {
        button1.setText("Exam Execution");
        button1.setOnAction(this::switchToStudentExamExecutionScreen);
        button2.setText("Past Exams");
        button2.setOnAction(this::switchToPastExamsScreen);
        button3.setVisible(false);
        button4.setVisible(false);
    }

    private void initDean()
    {
        button1.setText("Statistical Analysis");
        button1.setOnAction(this::switchToResultsScreen);
        button2.setText("Time Extensions");
        button2.setOnAction(this::switchToExtensionRequestsScreen);
        button3.setText("Exam Management");
        button3.setOnAction(this::switchToExamManagementScreen);
        button4.setText("Question Management");
        button4.setOnAction(this::switchToQuestionManagement);
    }

    private void switchToResultsScreen(ActionEvent event) {
        ClientApp.setRoot("ResultsScreen");
    }


    private void initAccordingToPermission() {
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
    public void initialize() {
        setModel(ClientApp.getModel());
        generateGreeting();
        initAccordingToPermission();
        model.loadSubjects();
    }

    @FXML
    void switchToExamManagementScreen (ActionEvent event)
    {
        ClientApp.setRoot("ExamManagementScreen");
    }
    @FXML
    void switchToPastExamsScreen(ActionEvent event)
    {
        ClientApp.setRoot("StudentPastExamsScreen");

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
        errorAlert = new Alert(Alert.AlertType.ERROR);
        advance = new AtomicBoolean(false);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Exam Code");
        dialog.setContentText("Please enter exam code:");
        errorAlert.setTitle("Error");
        while (!advance.get())
        {
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(code -> {
                if (result.get().length() != 4) {
                    codeIdLengthAlert();
                } else if (ClientApp.containsSpecialCharacters(result.get())) {
                    codeIdInvalidInputAlert();
                }else {
                    model.studentTakeManualExam(code);
                    advance.set(true);
                }
            });
            if (result.isEmpty())
                advance.set(true);
        }
    }


    void prepCompExam() {
        errorAlert = new Alert(Alert.AlertType.ERROR);
        advance = new AtomicBoolean(false);
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Personal Details");
        dialog.setHeaderText("Please enter your personal details:");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        //create grid for the login screen
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
        code.textProperty().addListener((observable, oldValue, newValue) -> OkButton.setDisable(newValue.trim().isEmpty()));
        id.textProperty().addListener((observable, oldValue, newValue) -> OkButton.setDisable(newValue.trim().isEmpty()));

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(code::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(code.getText(), id.getText());
            }
            return null;
        });
        errorAlert.setTitle("Error");
        while (!advance.get())
        {
            Optional<Pair<String, String>> result = dialog.showAndWait();
            result.ifPresent(codeId -> {
                if (codeId.getFirst().length() != 4) {
                    codeIdLengthAlert();
                } else if (ClientApp.containsSpecialCharacters(codeId.getFirst())) {
                    codeIdInvalidInputAlert();
                } else if (!ClientApp.isNumeric(codeId.getSecond())) {
                    studentIdInvalidInputAlert();
                }
                else {
                    model.studentTakeComputerizedExam(codeId.getFirst(),codeId.getSecond());
                    advance.set(true);
                }
            });
            if (result.isEmpty())
                advance.set(true);
        }
    }

    private void studentIdInvalidInputAlert() {
        errorAlert.setHeaderText("Invalid ID");
        errorAlert.setContentText("ID must only contain digits!");
        errorAlert.showAndWait();
    }

    private void codeIdInvalidInputAlert() {
        errorAlert.setHeaderText("Invalid exam code");
        errorAlert.setContentText("Code must only contain digits!");
        errorAlert.showAndWait();
    }

    private void codeIdLengthAlert() {
        errorAlert.setHeaderText("Wrong number of digits");
        errorAlert.setContentText("Please enter a 4-digit code!");
        errorAlert.showAndWait();
    }

    @FXML
    void switchToExtensionRequestsScreen (ActionEvent event)
    {
        ClientApp.setRoot("ViewDeanTimeExtensionScreen");
    }

    @FXML
    void logOutClicked(ActionEvent event){
        model.clearSubjectsAndCourses();
        ClientApp.logOut();
    }

}
