/**
 * Sample Skeleton for 'TeacherMainScreen.fxml' Controller Class
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

public class TeacherMainScreenController{

    @FXML // fx:id="questionManagementButton"
    private Button questionManagementButton; // Value injected by FXMLLoader

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

    @FXML
    public void initialize() {
        setModel(ClientApp.getModel());
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

}
