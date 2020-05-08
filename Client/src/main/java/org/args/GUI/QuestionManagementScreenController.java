/**
 * Sample Skeleton for 'QuestionManagementScreen.fxml' Controller Class
 */

package org.args.GUI;

import DatabaseAccess.Requests.AllQuestionsRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.util.Map;

public class QuestionManagementScreenController {

    @FXML // fx:id="coursesDropDown"
    private static MenuButton coursesDropDown; // Value injected by FXMLLoader
    @FXML // fx:id="subjectsDropdown"
    private MenuButton subjectsDropdown; // Value injected by FXMLLoader
    @FXML // fx:id="showQuestionList"
    private Button showQuestionList; // Value injected by FXMLLoader

    @FXML // fx:id="questionsList"
    private ListView<?> questionsList; // Value injected by FXMLLoader

    @FXML // fx:id="questionDetailsButton"
    private Button questionDetailsButton; // Value injected by FXMLLoader

    @FXML
    public static void addCourseToDropdown(String coursename) {
        coursesDropDown.getItems().add(new MenuItem(coursename));
    }

    @FXML
    public static void addQuestionsToQuestionsList(Map.Entry question) {


    }

    @FXML
    void getQuestionsList(ActionEvent event) {
        ClientApp.sendRequest(new AllQuestionsRequest(coursesDropDown.getText()));
    }

    @FXML
    void switchToQuestionEditScreen(ActionEvent event) throws IOException {

        ClientApp.setRoot("EditQuestionScreen");

    }


}
