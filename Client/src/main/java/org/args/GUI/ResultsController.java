package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import org.args.Client.ITeacherViewStatsData;

import java.util.List;
import java.util.Set;

public class ResultsController {

    @FXML
    private MenuButton subjectsDropdown;

    @FXML
    private MenuButton coursesDropdown;

    @FXML
    private ListView<String> examListView;

    @FXML
    private Button detailsButton;

    @FXML
    private Button backButton;

    ITeacherViewStatsData model;

    public void setModel(ITeacherViewStatsData model) {
        this.model = model;
    }

    @FXML
    public void initialize()
    {
        setModel(ClientApp.getModel());
        examListView.setItems(model.getPastExamsResultsObservableList());
        if (model.dataWasAlreadyInitialized()) {
            for (String subjectName : model.getSubjects())
            {
                addSubjectToSubjectDropdown(subjectName);
            }
            subjectsDropdown.setText(model.getCurrentSubject());
            coursesDropdown.setText(model.getCurrentCourseId());
            initializeCoursesDropdown();
            fillCoursesDropdown(model.getCurrentSubject());
            model.loadResults();
        } else {
            fillSubjectsDropDown(model.getSubjects());
        }

    }

    @FXML
    void back(ActionEvent event) {
        clearScreen();
        ClientApp.setRoot("MainScreen");
    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        detailsButton.setDisable(false);

    }

    @FXML
    void showExamDetails(ActionEvent event) {
        String selectedItem = examListView.getSelectionModel().getSelectedItem();
        String examId = selectedItem.substring(0,selectedItem.indexOf(":"));
        model.showGradesOf(examId);
    }

    void switchToMainScreen(MouseEvent event) {
        ClientApp.setRoot("MainScreen");
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
        course.setOnAction(event -> {
            String text = ((MenuItem) event.getSource()).getText();
            coursesDropdown.setText(text);
            model.setCurrentCourseId(text.substring(0,2));
            model.loadResults();
        });
        coursesDropdown.getItems().add(course);
    }

    public void fillSubjectsDropDown(Set<String> subjects) {
        for (String subject : subjects) //iterate through every subject in the hashmap
        {
            addSubjectToSubjectDropdown(subject);
        }
    }

    void clearScreen()
    {
        subjectsDropdown.getItems().clear();
        coursesDropdown.getItems().clear();
        model.setCurrentSubject(null);
        model.setCourseSelected(false);
        model.getPastExamsResultsObservableList().clear();
    }


}
