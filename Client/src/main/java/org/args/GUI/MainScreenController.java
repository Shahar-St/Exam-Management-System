/**
 * Sample Skeleton for 'MainScreen.fxml' Controller Class
 */

package org.args.GUI;

import DatabaseAccess.Requests.SubjectsAndCoursesRequest;
import javafx.event.ActionEvent;
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

    private void initTeacher()
    {
        button1.setText("Question Management Screen");
        button2.setText("Statistical Analysis");
        button3.setText("Exam Management");
    }

    private void initStudent()
    {
        button1.setText("Exam Execution");
        button2.setText("Past Exams");
        button3.setVisible(false);
    }

    private void initDean()
    {
        button1.setText("Statistical Analysis");
        button2.setText("View Reports");
        button3.setText("Pending Requests");
    }



    private void initAccordingToPermission()
    {
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
    public void initialize() {
        setModel(ClientApp.getModel());
        generateGreeting();
        initAccordingToPermission();


    }

}
