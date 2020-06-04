package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.args.Client.IExamData;

import java.util.List;

public class ExamDetailsController {


    @FXML
    private Label pageTitle;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField durationText;

    @FXML
    private TextArea studentNoteText;

    @FXML
    private TextArea teacherNoteText;

    @FXML
    private Button cancelButton;

    @FXML
    private Button nextButton;

    String tempTitle;
    String tempStudentNote;
    String tempTeacherNote;
    String tempDuration;
    List<String> tempQuestions;

    private IExamData model;

    public void setModel(IExamData model) {
        this.model = model;
    }

    @FXML
    public void initialize()
    {
        setModel(ClientApp.getModel());
        bindTextFields();
        if(model.getViewMode().equals("EDIT"))
        {
            pageTitle.setText("Edit An Existing Exam");
            saveOriginalExamData();
        }
    }

    private void saveOriginalExamData() {
        tempTitle = model.getCurrentExamTitle();
        tempStudentNote = model.getCurrentExamStudentNotes();
        tempTeacherNote = model.getCurrentExamTeacherNotes();
        tempDuration = model.getCurrentExamDuration();
        tempQuestions = model.getObservableExamQuestionsList();
    }

    private void restoreOriginalExamData ()
    {
        model.setCurrentExamTitle(tempTitle);
        model.setCurrentExamStudentNotes(tempStudentNote);
        model.setCurrentExamTeacherNotes(tempTeacherNote);
        model.setCurrentExamDuration(tempDuration);
        model.getObservableExamQuestionsList().setAll(tempQuestions);
    }


    private void bindTextFields() {
            titleTextField.textProperty().bindBidirectional(model.currentExamTitleProperty());
            studentNoteText.textProperty().bindBidirectional(model.currentExamStudentNotesProperty());
            teacherNoteText.textProperty().bindBidirectional(model.currentExamTeacherNotesProperty());
            durationText.textProperty().bindBidirectional(model.currentExamDurationProperty());
    }

    @FXML
    void back(ActionEvent event)
    {
        if (model.getViewMode().equals("ADD"))
        {
            ClientApp.setRoot("ExamManagementScreen");
            model.clearDetailsScreen();
        }
        else
        {
            restoreOriginalExamData();
            ClientApp.backToLastScene();
        }
    }

    @FXML
    void verifyAndProceed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        if (titleTextField.getText() == null)
        {
            alert.setContentText("Title field can't be empty!");
            alert.showAndWait();
        }
        else if (titleTextField.getText().isEmpty())
        {
            alert.setContentText("Title field can't be empty!");
            alert.showAndWait();
        }
        else if (!isNumeric(durationText.getText()))
        {
            alert.setContentText("Duration field must contain a number!");
            alert.showAndWait();
        }
        else
        {
            ClientApp.setRoot("ExamQuestionsScreen");
        }
    }


    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
