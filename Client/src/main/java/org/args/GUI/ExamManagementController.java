package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.args.Client.IExamManagementData;

import java.util.List;
import java.util.Set;

public class ExamManagementController {

    @FXML
    private ImageView backButton;

    @FXML
    private MenuButton subjectsDropdown;

    @FXML
    private MenuButton coursesDropdown;

    @FXML
    private Button detailsButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ListView<String> examListView;

    private IExamManagementData model;

    public void setModel(IExamManagementData model) {
        this.model = model;
    }

    @FXML
    private void bindButtonVisibility() {
        deleteButton.visibleProperty().bind(model.isCourseSelected());
        addButton.visibleProperty().bind(model.isCourseSelected());
        detailsButton.visibleProperty().bind(model.isCourseSelected());
    }

    @FXML
    public void initialize() {
        setModel(ClientApp.getModel());
        examListView.setItems(model.getObservableExamList());
        bindButtonVisibility();
        if (model.dataWasAlreadyInitialized()) {
            for (String subjectName : model.getSubjects()) //iterate through every subject in the hashmap
            {
                addSubjectToSubjectDropdown(subjectName);
            }
            subjectsDropdown.setText(model.getCurrentSubject());
            coursesDropdown.setText(model.getCurrentCourse());
            initializeCoursesDropdown();
            fillCoursesDropdown(model.getCurrentSubject());
            model.fillExamList(model.getCurrentCourse());
        }
        else
        {
            fillSubjectsDropDown(model.getSubjects());
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
            model.setCurrentSubject(((MenuItem) event.getSource()).getText());
            subjectsDropdown.setText(model.getCurrentSubject());
            fillCoursesDropdown(model.getCurrentSubject());
        }
    };


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
    public void addCourseToDropdown(String courseName) {
        MenuItem course = new MenuItem(courseName);
        course.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                coursesDropdown.setText(((MenuItem) event.getSource()).getText());
                model.setCurrentCourse(((MenuItem) event.getSource()).getText());
                model.fillExamList(((MenuItem) event.getSource()).getText());
            }
        });
        coursesDropdown.getItems().add(course);
    }

    @FXML
    void deleteExam(ActionEvent event) {

    }

    @FXML
    void switchToAddExamScreen(ActionEvent event) {

    }

    @FXML
    void switchToExamScreen(ActionEvent event) {
        if (examListView.getSelectionModel().getSelectedItem() != null) {
            String currentItem = examListView.getSelectionModel().getSelectedItem();
            int selectedItemIndex = model.getObservableExamList().indexOf(examListView.getSelectionModel().getSelectedItem());
            int indexOfColon = currentItem.indexOf(':');
            String examId = currentItem.substring(1, indexOfColon);
            model.saveExamDetails(examId);
        }


    }

    @FXML
    void switchToMainScreen(MouseEvent event) {
        clearScreen();
        ClientApp.setRoot("MainScreen");

    }

    public void fillSubjectsDropDown(Set<String> subjects) {
        for (String subject : subjects) //iterate through every subject in the hashmap
        {
            addSubjectToSubjectDropdown(subject);
        }
    }

    @FXML
    void clearScreen()
    {
        subjectsDropdown.getItems().clear();
        coursesDropdown.getItems().clear();
        model.setCurrentSubject(null);
        model.clearExamList();
    }

}
