package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.args.Client.IStudentViewStatsData;

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
    private TableColumn<String,Double> titleColumn;

    @FXML
    private TableColumn<String,Double> idColumn;

    @FXML
    private TableColumn<String,Double> gradeColumn;

    IStudentViewStatsData model;

    public void setModel(IStudentViewStatsData model) {
        this.model = model;
    }

    @FXML
    public void initialize()
    {
        setModel(ClientApp.getModel());
        model.loadSubjects();
        initializeGradesTable();
        if(model.getStudentPastExamsObservableList().size() > 0)
        {
            model.clearStudentPastExamsList();
        }
        if (model.dataWasAlreadyInitialized()) {
            for (String subjectName : model.getSubjects()) //iterate through every subject in the hashmap
            {
                addSubjectToSubjectDropdown(subjectName);
            }
            subjectsDropdown.setText(model.getCurrentSubject());
            coursesDropdown.setText(model.getCurrentCourseId());
            initializeCoursesDropdown();
            fillCoursesDropdown(model.getCurrentSubject());
            model.loadPastExams();
        } else {
            fillSubjectsDropDown(model.getSubjects());
        }
    }

    private void initializeGradesTable() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("examTitle"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("examId"));
        gradesTable.setItems(model.getStudentPastExamsObservableList());
    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        //will show a certain exam's details
        reviewExam.setDisable(false);
    }

    @FXML
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


}
