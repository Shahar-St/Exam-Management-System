/*
  Sample Skeleton for 'QuestionManagementScreen.fxml' Controller Class
 */

package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.args.Client.IQuestionManagementData;

import java.util.List;
import java.util.Set;

public class QuestionManagementController {


    @FXML // fx:id="coursesDropdown"
    private MenuButton coursesDropdown; // Value injected by FXMLLoader
    @FXML // fx:id="subjectsDropdown"
    private MenuButton subjectsDropdown; // Value injected by FXMLLoader

    @FXML // fx:id="questionsList"
    private ListView<String> questionsList; // Value injected by FXMLLoader

    @FXML // fx:id="questionDetailsButton"
    private Button questionDetailsButton; // Value injected by FXMLLoader

    @FXML //fx:id="backButton"
    private ImageView backButton;

    @FXML
    private Button addButton;

    private IQuestionManagementData model;

    public void setModel(IQuestionManagementData dataModel) {
        if (model == null)
            this.model = dataModel;
    }

    @FXML
    public void initialize() {
        setModel(ClientApp.getModel());
        questionsList.setItems(model.getObservableQuestionsList());
        if (model.getObservableQuestionsList().size() > 0 && !model.isCourseSelected().get())
            model.clearQuestionsList();
        bindButtonVisibility();
        if (model.dataWasAlreadyInitialized()) {
            initializeFormerData();
            model.fillQuestionsList(model.getCurrentCourseId());
        } else {
            fillSubjectsDropDown(model.getSubjects());
        }
    }

    private void initializeFormerData() {
        for (String subjectName : model.getSubjects()) //iterate through every subject in the hashmap
        {
            addSubjectToSubjectDropdown(subjectName);
        }
        subjectsDropdown.setText(model.getCurrentSubject());
        coursesDropdown.setText(model.getCurrentCourseName());
        initializeCoursesDropdown();
        fillCoursesDropdown(model.getCurrentSubject());
    }

    private void bindButtonVisibility() {
        questionDetailsButton.visibleProperty().bind(model.isCourseSelected());
        if(model.getPermission().equals("dean"))
            addButton.setVisible(false);
        else
            addButton.visibleProperty().bind(model.isCourseSelected());

    }

    @FXML
    public void addCourseToDropdown(String courseName) {
        MenuItem course = new MenuItem(courseName);
        course.setOnAction(event -> {
            String text = ((MenuItem) event.getSource()).getText();
            coursesDropdown.setText(text);
            model.setCurrentCourseName(text.substring(5));
            model.setCurrentCourseId(text.substring(0,2));
            model.fillQuestionsList(text.substring(0,2));
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
        List<String> coursesToAdd = model.getCoursesOfSubject(subject);
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
    public final EventHandler<ActionEvent> displayCoursesFromSubject = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            initializeCoursesDropdown();
            model.setCurrentSubject(((MenuItem) event.getSource()).getText());
            subjectsDropdown.setText(model.getCurrentSubject());
            fillCoursesDropdown(model.getCurrentSubject());
        }
    };


    @FXML
    void switchToQuestionViewScreen(ActionEvent event) {
        viewSelectedQuestionDetails();
    }


    @FXML
    void switchToAddQuestionScreen(ActionEvent event) {
        model.prepareAddQuestion();
        questionDetailsButton.setDisable(true);
        ClientApp.pushLastScene("QuestionManagementScreen");
        ClientApp.setRoot("QuestionScreen");
    }


    @FXML
    void switchToMainScreen(MouseEvent event) {
        clearScreen();
        ClientApp.setRoot("MainScreen");
    }

    @FXML
    void clearScreen() {
        subjectsDropdown.getItems().clear();
        coursesDropdown.getItems().clear();
        model.setCurrentSubject(null);
        model.clearQuestionsList();
        model.setCourseSelected(false);
    }

    public void fillSubjectsDropDown(Set<String> subjects) {
        for (String subject : subjects) //iterate through every subject in the hashmap
        {
            addSubjectToSubjectDropdown(subject);
        }
    }


    @FXML
    void handleMouseEvent(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            viewSelectedQuestionDetails();
        }
        if (questionDetailsButton.isDisabled())
            questionDetailsButton.setDisable(false);

    }

    private void viewSelectedQuestionDetails() {
        if (questionsList.getSelectionModel().getSelectedItem() != null) {
            questionDetailsButton.setDisable(true);
            int indexOfColon = questionsList.getSelectionModel().getSelectedItem().indexOf(':');
            String questionId = questionsList.getSelectionModel().getSelectedItem().substring(1, indexOfColon);
            model.setCurrentQuestionId(questionId);
            model.loadQuestionDetails(questionId);
            ClientApp.pushLastScene("QuestionManagementScreen");
        }
    }

}
