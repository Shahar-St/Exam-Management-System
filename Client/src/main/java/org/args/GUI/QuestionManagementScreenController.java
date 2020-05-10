/**
 * Sample Skeleton for 'QuestionManagementScreen.fxml' Controller Class
 */

package org.args.GUI;

import DatabaseAccess.Requests.AllQuestionsRequest;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionManagementScreenController {

    private ClientApp clientApp=null;

    @FXML // fx:id="coursesDropdown"
    private MenuButton coursesDropdown; // Value injected by FXMLLoader
    @FXML // fx:id="subjectsDropdown"
    private MenuButton subjectsDropdown; // Value injected by FXMLLoader
    @FXML // fx:id="showQuestionList"
    private Button showQuestionList; // Value injected by FXMLLoader

    @FXML // fx:id="questionsList"
    private ListView<String> questionsList; // Value injected by FXMLLoader

    @FXML // fx:id="questionDetailsButton"
    private Button questionDetailsButton; // Value injected by FXMLLoader

    private static HashMap<String, List<String>> subjectsAndCourses;

    @FXML
    public void addCourseToDropdown(String coursename) {
        MenuItem course = new MenuItem(coursename);
        course.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                coursesDropdown.setText(((MenuItem) event.getSource()).getText());
            }
        });
        coursesDropdown.getItems().add(course);
    }

    @FXML
    public void addQuestionToQuestionsList(String questionDescription) {
        questionsList.getItems().add(questionDescription);

    }

    @FXML
    public void addSubjectToSubjectDropdown (MenuItem subject){
        subjectsDropdown.getItems().add(subject);
    }

    @FXML
    public EventHandler<ActionEvent> displayCoursesFromSubject = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (coursesDropdown.isDisabled())
                coursesDropdown.setDisable(false);
            String currentSubject = ((MenuItem) event.getSource()).getText();
            subjectsDropdown.setText(currentSubject);
            List<String> coursesToAdd = subjectsAndCourses.get(currentSubject);
            for (String course : coursesToAdd)
            {
                addCourseToDropdown(course);
            }
        }
    };




    @FXML
    void getQuestionsList(ActionEvent event) {
        ClientApp.sendRequest(new AllQuestionsRequest(coursesDropdown.getText()));
    }

    @FXML
    void switchToQuestionEditScreen(ActionEvent event) throws IOException {

        ClientApp.setRoot("EditQuestionScreen");

    }

    public void setSubjectsAndCoursesState (HashMap<String,List<String>> mapFromResponse)
    {
        subjectsAndCourses = mapFromResponse;
    }

    @FXML
    void switchToStatisticalAnalysisScreen(ActionEvent event) {

    }

    @FXML
    void switchToTestsManagementScreen(ActionEvent event) {

    }

    public void setClientApp(ClientApp clientApp) {
        if(this.clientApp == null){
            this.clientApp = clientApp;
        }

    }


}
