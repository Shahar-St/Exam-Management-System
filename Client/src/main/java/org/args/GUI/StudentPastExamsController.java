package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.args.Client.IViewPastExamsData;

import java.util.List;
import java.util.Set;

public class StudentPastExamsController {

    @FXML
    private ImageView backButton;

    @FXML
    private MenuButton subjectsDropdown;

    @FXML
    private MenuButton coursesDropdown;

    @FXML
    private TableView<StudentPastExam> gradesTable;

    @FXML
    private Button reviewExam;

    @FXML
    private TableColumn<String, Double> titleColumn;

    @FXML
    private TableColumn<String, Double> dateColumn;

    @FXML
    private TableColumn<String, Double> gradeColumn;

    IViewPastExamsData model;

    public void setModel(IViewPastExamsData model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        setModel(ClientApp.getModel());
        initializeGradesTable();
        if (model.dataWasAlreadyInitialized()) {
            restoreInitializedData();
            model.loadPastExams();
        } else {
            fillSubjectsDropDown(model.getSubjects());
        }
    }

    private void restoreInitializedData() {
        for (String subjectName : model.getSubjects()) //iterate through every subject in the hashmap
        {
            addSubjectToSubjectDropdown(subjectName);
        }
        subjectsDropdown.setText(model.getCurrentSubject());
        coursesDropdown.setText(model.getCurrentCourseName());
        initializeCoursesDropdown();
        fillCoursesDropdown(model.getCurrentSubject());
    }

    private void initializeGradesTable() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("examTitle"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        gradesTable.setItems(model.getStudentPastExamsObservableList());

    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        reviewExam.setDisable(false);
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && gradesTable.getSelectionModel().getSelectedItem() != null) {
            showSelectedExamDetails();
        }
    }

    @FXML
    void showExamDetails(ActionEvent event) {
        showSelectedExamDetails();
    }

    private void showSelectedExamDetails() {
        String examId = gradesTable.getSelectionModel().getSelectedItem().getExamId();
        model.reviewStudentExam(examId);
    }

    @FXML
    void switchToMainScreen(MouseEvent event) {
        clearScreen();
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
            model.setCurrentCourseName(text.substring(5));
            model.setCurrentCourseId(text.substring(0, 2));
            model.loadPastExams();
        });
        coursesDropdown.getItems().add(course);
    }

    public void fillSubjectsDropDown(Set<String> subjects) {
        for (String subject : subjects) //iterate through every subject in the hashmap
        {
            addSubjectToSubjectDropdown(subject);
        }
    }

    void clearScreen() {
        subjectsDropdown.getItems().clear();
        coursesDropdown.getItems().clear();
        model.setCurrentSubject(null);
        model.setCourseSelected(false);
        model.clearStudentPastExamsList();
    }


}
