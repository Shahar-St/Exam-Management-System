/**
 * Sample Skeleton for 'QuestionManagementScreen.fxml' Controller Class
 */

package org.args.GUI;

import DatabaseAccess.Requests.AllQuestionsRequest;
import DatabaseAccess.Requests.QuestionRequest;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;

import java.io.IOException;
import java.util.*;

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

    private static List<String> questions = new Vector<>();

    private static String currentSubject = null;

    public void setClientApp(ClientApp clientApp) {
        if(this.clientApp == null){
            this.clientApp = clientApp;
        }

    }

    @FXML
    public void initialize() {
        if (questions.size() > 0)
            questionsList.getItems().addAll(questions);

        if (subjectsAndCourses != null)
        {
            for (String subjectName : subjectsAndCourses.keySet()) //iterate through every subject in the hashmap
            {
                MenuItem subject = new MenuItem(subjectName);
                subject.setOnAction(displayCoursesFromSubject);
                addSubjectToSubjectDropdown(subject);
            }
            subjectsDropdown.setText(currentSubject);
            initializeCoursesdropdown();
            fillCoursesDropdown(currentSubject);
        }
    }

    @FXML
    public void addCourseToDropdown(String coursename) {
        MenuItem course = new MenuItem(coursename);
        course.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                coursesDropdown.setText(((MenuItem) event.getSource()).getText());
                ClientApp.sendRequest(new AllQuestionsRequest(((MenuItem) event.getSource()).getText()));
            }
        });
        coursesDropdown.getItems().add(course);
    }

    @FXML
    void initializeCoursesdropdown() {
        if (coursesDropdown.isDisabled())
            coursesDropdown.setDisable(false);
        if (coursesDropdown.getItems().size() > 0)
            coursesDropdown.getItems().clear();
    }

    @FXML
    void fillCoursesDropdown (String subject)
    {
        List<String> coursesToAdd = subjectsAndCourses.get(subject);
        for (String course : coursesToAdd)
        {
            addCourseToDropdown(course);
        }
    }


    @FXML
    public void addSubjectToSubjectDropdown (MenuItem subject){
        subjectsDropdown.getItems().add(subject);
    }

    @FXML
    public EventHandler<ActionEvent> displayCoursesFromSubject = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            initializeCoursesdropdown();
            currentSubject = ((MenuItem) event.getSource()).getText();
            subjectsDropdown.setText(currentSubject);
            fillCoursesDropdown(currentSubject);
        }
    };

    @FXML
    void getQuestionsList(ActionEvent event) {
        if (questionsList.getItems().size() > 0)
            questionsList.getItems().clear();
        questionsList.getItems().addAll(questions);
    }



    public void setSubjectsAndCoursesState (HashMap<String,List<String>> mapFromResponse)
    {
        subjectsAndCourses = mapFromResponse;
    }


    @FXML
    public void addToList(ObservableList<String> observableSet) {
        questions = observableSet;
    }


    @FXML
    void switchToQuestionEditScreen(ActionEvent event) throws IOException {
        int indexOfColon = questionsList.getSelectionModel().getSelectedItem().indexOf(':');
        int questionId = Integer.parseInt(questionsList.getSelectionModel().getSelectedItem().substring(1,indexOfColon));
        ClientApp.sendRequest(new QuestionRequest(questionId));
        ClientApp.setRoot("EditQuestionScreen");
    }

    @FXML
    void switchToStatisticalAnalysisScreen(ActionEvent event) {

    }

    @FXML
    void switchToTestsManagementScreen(ActionEvent event) {

    }

}
