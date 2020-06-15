package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.args.Client.IExamData;

import java.util.ArrayList;
import java.util.List;

import static org.args.GUI.ClientApp.isNumeric;

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

    String origTitle;
    String origStudentNote;
    String origTeacherNote;
    String origDuration;
    final List<String> origQuestions = new ArrayList<>();
    final List<String> origScores = new ArrayList<>();

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
        origTitle = model.getCurrentExamTitle();
        origStudentNote = model.getCurrentExamStudentNotes();
        origTeacherNote = model.getCurrentExamTeacherNotes();
        origDuration = model.getCurrentExamDuration();
        origQuestions.addAll(model.getObservableExamQuestionsList());
        origScores.addAll(model.getObservableQuestionsScoringList());
    }

    private void restoreOriginalExamData ()
    {
        model.setCurrentExamTitle(origTitle);
        model.setCurrentExamStudentNotes(origStudentNote);
        model.setCurrentExamTeacherNotes(origTeacherNote);
        model.setCurrentExamDuration(origDuration);
        model.getObservableExamQuestionsList().clear();
        model.getObservableQuestionsScoringList().clear();
        for(String question : origQuestions)
            model.addToExamQuestionsList(question);
        model.getObservableQuestionsScoringList().addAll(origScores);
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
//            model.clearDetailsScreen();
        }
        else
        {
            restoreOriginalExamData();
        }
        ClientApp.backToLastScene();
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


}
