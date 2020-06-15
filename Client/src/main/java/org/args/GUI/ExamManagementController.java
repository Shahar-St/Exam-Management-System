package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.args.Client.IExamManagementData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

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

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

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
        if (model.dataWasAlreadyInitialized())
        {
            initializeFormerData();
            model.fillExamList(model.getCurrentCourseId());
        }
        else
        {
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
        for (String course : coursesToAdd)
        {
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
            model.fillExamList(text.substring(0, 2));
        });
        coursesDropdown.getItems().add(course);
    }


    @FXML
    void switchToAddExamScreen(ActionEvent event) {
        model.fillQuestionsList(model.getCurrentCourseId());
        model.setViewMode("ADD");
        ClientApp.pushLastScene("ExamManagementScreen");
        ClientApp.setRoot("ExamDetailsScreen");
    }

    @FXML
    void switchToExamScreen(ActionEvent event) {
        viewSelectedExamDetails();
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
    void clearScreen() {
        subjectsDropdown.getItems().clear();
        coursesDropdown.getItems().clear();
        model.setCurrentSubject(null);
        model.setCourseSelected(false);
        model.clearExamList();
    }


    @FXML
    void handleMouseEvent(MouseEvent event) {
        enableDetailsAndExecuteButtons();
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
        {
            viewSelectedExamDetails();
        }
    }

    private void viewSelectedExamDetails() {
        disableDetailsAndExecuteButtons();
        String examId = getExamIdFromSelected();
        model.setCurrentExamId(examId);
        if (examId != null) {
            ClientApp.pushLastScene("ExamManagementScreen");
            model.viewExam(examId);
        }
    }

    private String getExamIdFromSelected() {
        if (examListView.getSelectionModel().getSelectedItem() != null)
        {
            String currentItem = examListView.getSelectionModel().getSelectedItem();
            int indexOfColon = currentItem.indexOf(':');
            return currentItem.substring(1, indexOfColon);
        }
        return null;
    }


    @FXML
    void executeExam(ActionEvent event) {
        if (examListView.getSelectionModel().getSelectedItem() != null) {
            disableDetailsAndExecuteButtons();
            String examId = getExamIdFromSelected();
            if (examId != null) {
                AtomicBoolean advance = new AtomicBoolean(false);
                TextInputDialog examCodeDialog = new TextInputDialog();
                examCodeDialog.setTitle("Exam Code");
                examCodeDialog.setContentText("Please enter exam code:");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                while (!advance.get()) {
                    Optional<String> result = examCodeDialog.showAndWait();
                    result.ifPresent(code ->
                            {
                                if (code.length() != 4) {
                                    alert.setHeaderText("Wrong number of digits");
                                    alert.setContentText("Please enter a 4-digit code!");
                                    alert.showAndWait();
                                } else if (!ClientApp.isNumeric(code)) {
                                    alert.setHeaderText("Invalid exam code");
                                    alert.setContentText("Code must only contain digits!");
                                    alert.showAndWait();
                                } else {
                                    model.executeExam(examId, code);
                                    advance.set(true);
                                }
                            }
                    );
                    if (result.isEmpty())
                        advance.set(true);
                }
            }
            model.setCurrentExecutedExamLaunchTime(LocalDateTime.now().format(formatter));
            String currentTitle = examListView.getSelectionModel().getSelectedItem();
            currentTitle = currentTitle.substring(currentTitle.indexOf(":") + 1);
            model.setCurrentExecutedExamTitle(currentTitle);
        }
    }

    private void disableDetailsAndExecuteButtons()
    {
        detailsButton.setDisable(true);
        executeButton.setDisable(true);
    }

    private void enableDetailsAndExecuteButtons()
    {
        detailsButton.setDisable(false);
        executeButton.setDisable(false);
    }

}
