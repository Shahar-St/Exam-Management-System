/**
 * Sample Skeleton for 'TeacherMainScreen.fxml' Controller Class
 */

package org.args.GUI;

import DatabaseAccess.Requests.SubjectsAndCoursesRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class TeacherMainScreenController {

    private static String fullGreeting;

    @FXML // fx:id="questionMangementButton"
    private Button questionMangementButton; // Value injected by FXMLLoader

    @FXML
    private Label welcomeLabel;

    @FXML
    void switchToQuestionManagement(ActionEvent event) throws IOException {
        ClientApp.sendRequest(new SubjectsAndCoursesRequest());
    }

    @FXML
    public void initialize() {
        Date dt = new Date();
        int hours = dt.getHours();
        String greeting = null;
        if (hours >= 1 && hours <= 12) {
            greeting = "Good Morning";
        } else if (hours >= 12 && hours <= 16) {
            greeting = "Good Afternoon";
        } else if (hours >= 16 && hours <= 21) {
            greeting = "Good Evening";
        } else if (hours >= 21 && hours <= 24) {
            greeting = "Good Night";
        }
        fullGreeting = greeting + ", " + ClientApp.getFullName();
        welcomeLabel.setText(fullGreeting);
    }




}
