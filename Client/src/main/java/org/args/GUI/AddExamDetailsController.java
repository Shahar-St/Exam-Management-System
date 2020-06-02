package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.args.Client.IAddExamData;

public class AddExamDetailsController {

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

    private IAddExamData model;

    public void setModel(IAddExamData model) {
        this.model = model;
    }

    @FXML
    public void initialize()
    {
        setModel(ClientApp.getModel());
        bindTextFields();
    }


    private void bindTextFields() {
            titleTextField.textProperty().bindBidirectional(model.currentExamTitleProperty());
            studentNoteText.textProperty().bindBidirectional(model.currentExamStudentNotesProperty());
            teacherNoteText.textProperty().bindBidirectional(model.currentExamTeacherNotesProperty());
            durationText.textProperty().bindBidirectional(model.currentExamDurationProperty());
    }

    @FXML
    void backToExamManagement(ActionEvent event) {
        ClientApp.setRoot("ExamManagementScreen");
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
            ClientApp.setRoot("AddExamQuestionsScreen");
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
