package org.args.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.args.Client.IExamReviewData;

import java.io.File;

public class TeacherReviewManualExamController {

    private IExamReviewData model;

    @FXML
    private Button downloadButton;

    @FXML
    private TextArea notes;

    @FXML
    private TextField grade;

    @FXML
    private Button submitButton;

    @FXML
    void initialize() {
        setModel(ClientApp.getModel());
        assert downloadButton != null;
        assert notes != null;
        assert grade != null;
        assert submitButton != null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Microsoft Word Open XML Format Document", "*.docx","*.doc"));
        downloadButton.setOnAction(event -> {
            fileChooser.setTitle("Download Exam");
            File defaultDirectory = new File("/home");
            fileChooser.setInitialDirectory(defaultDirectory);
            fileChooser.setInitialFileName(model.getManualExamForReviewStudentId()+"_exam.docx");
            File selectedFile = fileChooser.showSaveDialog(ClientApp.primaryStage);
            if(!selectedFile.getName().contains(".")){
                selectedFile = new File(selectedFile.getAbsoluteFile()+".docx");
            }else if(!selectedFile.getName().contains(".docx")){
                selectedFile = new File(selectedFile.getAbsoluteFile().toString().substring(0,selectedFile.getAbsoluteFile().toString().indexOf("."))+".docx");
            }
            model.saveWordFile(selectedFile);
        });
        submitButton.setOnAction(event -> {
            if(!ClientApp.isDouble(grade.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("Only Positive Decimal Are Allowed");
                alert.showAndWait();
                return;
            }
            fileChooser.setTitle("Upload Exam");
            File defaultDirectory = new File("/home");
            fileChooser.setInitialDirectory(defaultDirectory);
            File selectedFile = fileChooser.showOpenDialog(ClientApp.primaryStage);
            model.submitExamReview(Double.parseDouble(grade.getText()),notes.getText(),selectedFile);
        });

    }

    private void setModel(IExamReviewData newModel){
        if(model==null)
            model = newModel;
    }
}
