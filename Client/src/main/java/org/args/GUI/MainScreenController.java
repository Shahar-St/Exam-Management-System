/**
 * Sample Skeleton for 'MainScreen.fxml' Controller Class
 */

package org.args.GUI;

import DatabaseAccess.Requests.Statistics.TeacherStatisticsRequest;
import DatabaseAccess.Requests.SubjectsAndCoursesRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.args.Client.IMainScreenData;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

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

    @FXML
    void onAction(ActionEvent event) {
        model.viewExam();
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
        button1.setText("Question Management Screen");
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
            case "Teacher":
                initTeacher();
                break;
            case "Student":
                initStudent();
                break;
            case "Dean":
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
        ClientApp.sendRequest(new TeacherStatisticsRequest("111"));
    }
    @FXML
    void switchToStudentExamExecutionScreen (ActionEvent event)
    {
        ClientApp.setRoot("StudentExamExecutionScreen");
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
