package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.args.Client.IExamManagementData;

import java.util.List;
import java.util.Optional;
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
    private ListView<String> examListView;

    @FXML
    private Button executeButton;



    private IExamManagementData model;

    public void setModel(IExamManagementData model) {
        this.model = model;
    }

    @FXML
    private void bindButtonVisibility() {
        addButton.visibleProperty().bind(model.isCourseSelected());
        detailsButton.visibleProperty().bind(model.isCourseSelected());
        executeButton.visibleProperty().bind(model.isCourseSelected());
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
            coursesDropdown.setText(model.getCurrentCourseId());
            initializeCoursesDropdown();
            fillCoursesDropdown(model.getCurrentSubject());
            model.fillExamList(model.getCurrentCourseId());
        } else {
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
                model.setCurrentCourseId(((MenuItem) event.getSource()).getText());
                model.fillExamList(((MenuItem) event.getSource()).getText());
            }
        });
        coursesDropdown.getItems().add(course);
    }



    @FXML
    void switchToAddExamScreen(ActionEvent event) {
        model.fillQuestionsList(model.getCurrentCourseId());
        model.setViewMode("ADD");
        ClientApp.setRoot("ExamDetailsScreen");
    }

    @FXML
    void switchToExamScreen(ActionEvent event) {
        viewSelectedExamDetails();
    }

    @FXML
    void switchToMainScreen(MouseEvent event) {
        clearScreen();
        ClientApp.backToLastScene();

    }

    public void fillSubjectsDropDown(Set<String> subjects) {
        for (String subject : subjects) //iterate through every subject in the hashmap
        {
            addSubjectToSubjectDropdown(subject);
        }
    }

    @FXML
    void clearScreen() {
        subjectsDropdown.getItems().clear();
        coursesDropdown.getItems().clear();
        model.setCurrentSubject(null);
        model.setCourseSelected(false);
        model.clearExamList();

    }


    @FXML
    void handleMouseEvent(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            viewSelectedExamDetails();
        }
    }

    private void viewSelectedExamDetails() {
        String examId = getExamIdFromSelected();
        if (examId != null)
            model.saveExamDetails(examId);
    }

    private String getExamIdFromSelected() {
        if (examListView.getSelectionModel().getSelectedItem() != null) {
            String currentItem = examListView.getSelectionModel().getSelectedItem();
            int indexOfColon = currentItem.indexOf(':');
            return currentItem.substring(1, indexOfColon);
        }
        return null;
    }


    @FXML
    void executeExam(ActionEvent event) {
        String examId = getExamIdFromSelected();
        if (examId != null)
        {
            TextInputDialog examCodeDialog = new TextInputDialog();
            examCodeDialog.setTitle("Exam Code");
            examCodeDialog.setHeaderText("Exam Code");
            examCodeDialog.setContentText("Please enter exam code:");

            Optional<String> result = examCodeDialog.showAndWait();
            result.ifPresent(code -> model.deployExam(examId, code));
        }
    }

}
