/**
 * Sample Skeleton for 'QuestionManagementScreen.fxml' Controller Class
 */

package org.args.GUI;

import DatabaseAccess.Requests.AllQuestionsRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionManagementScreenController {


    @FXML // fx:id="coursesDropDown"
    private static MenuButton coursesDropDown; // Value injected by FXMLLoader
    @FXML // fx:id="subjectsDropdown"
    private static MenuButton subjectsDropdown; // Value injected by FXMLLoader
    @FXML // fx:id="showQuestionList"
    private Button showQuestionList; // Value injected by FXMLLoader

    @FXML // fx:id="questionsList"
    private static ListView<String> questionsList; // Value injected by FXMLLoader

    @FXML // fx:id="questionDetailsButton"
    private Button questionDetailsButton; // Value injected by FXMLLoader

    private static HashMap<String, List<String>> subjectsAndCourses;

    @FXML
    public static void addCourseToDropdown(String coursename) {
        coursesDropDown.getItems().add(new MenuItem(coursename));
    }

    @FXML
    public static void addQuestionToQuestionsList(String questionDescription) {
        questionsList.getItems().add(questionDescription);

    }

    @FXML
    public static void addSubjectToSubjectDropdown (MenuItem subject){
        subjectsDropdown.getItems().add(subject);
    }

    @FXML
    public static EventHandler<ActionEvent> displayCoursesFromSubject = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        if (coursesDropDown.isDisabled())
            coursesDropDown.setDisable(false);
        String currentSubject = subjectsDropdown.getText();
        List<String> coursesToAdd = subjectsAndCourses.get(currentSubject);
        for (String course : coursesToAdd)
        {
            coursesDropDown.getItems().add(new MenuItem(course));
        }
        }
    };




    @FXML
    void getQuestionsList(ActionEvent event) {
        ClientApp.sendRequest(new AllQuestionsRequest(coursesDropDown.getText()));
    }

    @FXML
    void switchToQuestionEditScreen(ActionEvent event) throws IOException {

        ClientApp.setRoot("EditQuestionScreen");

    }

    public static void setSubjectsAndCoursesState (HashMap<String,List<String>> mapFromResponse)
    {
        subjectsAndCourses = mapFromResponse;
    }


}
