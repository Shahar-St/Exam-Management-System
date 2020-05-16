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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class QuestionManagementScreenController {


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

    @FXML //fx:id="backButton"
    private ImageView backButton;

    private static HashMap<String, List<String>> subjectsAndCourses;

    private static List<String> questions = new Vector<>();

    private static String currentSubject = null;

    private static String currentCourse = null;

    private static int selectedIndex;


    @FXML
    public void initialize() {
        if (questions.size() > 0)
            questionsList.getItems().addAll(questions);

        if (currentSubject != null && !subjectsAndCourses.isEmpty()) {
            for (String subjectName : subjectsAndCourses.keySet()) //iterate through every subject in the hashmap
            {
                addSubjectToSubjectDropdown(subjectName);
            }
            subjectsDropdown.setText(currentSubject);
            coursesDropdown.setText(currentCourse);
            initializeCoursesDropdown();
            fillCoursesDropdown(currentSubject);
        }
    }

    @FXML
    public void addCourseToDropdown(String courseName) {
        MenuItem course = new MenuItem(courseName);
        course.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                coursesDropdown.setText(((MenuItem) event.getSource()).getText());
                currentCourse = ((MenuItem) event.getSource()).getText();
                ClientApp.sendRequest(new AllQuestionsRequest(((MenuItem) event.getSource()).getText()));
            }
        });
        coursesDropdown.getItems().add(course);
    }

    @FXML
    void initializeCoursesDropdown() {
        if (coursesDropdown.isDisabled())
            coursesDropdown.setDisable(false);
        if (coursesDropdown.getItems().size() > 0)
            coursesDropdown.getItems().clear();
    }

    @FXML
    void fillCoursesDropdown(String subject) {
        List<String> coursesToAdd = subjectsAndCourses.get(subject);
        for (String course : coursesToAdd) {
            addCourseToDropdown(course);
        }
    }


    @FXML
    public void addSubjectToSubjectDropdown(String subjectName) {
        MenuItem subject = new MenuItem(subjectName);
        subject.setOnAction(displayCoursesFromSubject);
        subjectsDropdown.getItems().add(subject);
    }

    @FXML
    public EventHandler<ActionEvent> displayCoursesFromSubject = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            initializeCoursesDropdown();
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


    public void setSubjectsAndCoursesState(HashMap<String, List<String>> mapFromResponse) {
        subjectsAndCourses = mapFromResponse;
    }

    public void changeQuestionContent(String newContent) {
        String oldContent = questions.get(selectedIndex);
        String finalContent = oldContent.substring(0,oldContent.indexOf(':')+2) + newContent;
        questions.set(selectedIndex, finalContent);
    }


    @FXML
    public void addToList(ObservableList<String> observableSet) {
        questions = observableSet;
    }


    @FXML
    void switchToQuestionEditScreen(ActionEvent event) throws IOException {
        if(questionsList.getSelectionModel().getSelectedItem() != null)
        {
        selectedIndex = questions.indexOf(questionsList.getSelectionModel().getSelectedItem());
        int indexOfColon = questionsList.getSelectionModel().getSelectedItem().indexOf(':');
        String questionId = questionsList.getSelectionModel().getSelectedItem().substring(1, indexOfColon);
        ClientApp.sendRequest(new QuestionRequest(questionId));
        ClientApp.setRoot("EditQuestionScreen");
        }
    }

    @FXML
    void switchToStatisticalAnalysisScreen(ActionEvent event) {

    }

    @FXML
    void switchToTestsManagementScreen(ActionEvent event) {

    }

    @FXML
    void switchToTeacherMainScreen(MouseEvent event) throws IOException {
        clearScreen();
        ClientApp.setRoot("TeacherMainScreen");
    }

    void clearScreen()
    {
        questions.clear();
        subjectsDropdown.getItems().clear();
        coursesDropdown.getItems().clear();
        currentSubject = null;
    }

}
