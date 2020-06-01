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
    }

    @FXML
    void backToExamManagement(ActionEvent event) {
        ClientApp.setRoot("ExamManagementScreen");
    }

    @FXML
    void verifyAndProceed(ActionEvent event) {
        if (titleTextField.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Title field can't be empty!");
            alert.showAndWait();
        }
        else
        {
            model.setCurrentExamTitle(titleTextField.getText());
            model.setCurrentExamStudentNotes(studentNoteText.getText());
            model.setCurrentExamTeacherNotes(teacherNoteText.getText());
            ClientApp.setRoot("AddExamQuestionsScreen");
        }
    }
}
