package org.args.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.args.Client.IStudentExamExecutionData;

import java.io.File;

public class StudentManualExamController {

    private IStudentExamExecutionData model;

    @FXML
    private Button downloadButton;

    @FXML
    private Button submitButton;

    @FXML
    void initialize(){
        setModel(ClientApp.getModel());
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Microsoft Word Open XML Format Document", "*.docx","*.doc"));
        downloadButton.setOnAction(event->{
            fileChooser.setTitle("Download Exam");
            File defaultDirectory = new File("/home/");
            fileChooser.setInitialDirectory(defaultDirectory);
            fileChooser.setInitialFileName(model.getExamForStudentExecution().getId()+"_exam.docx");
            File selectedFile = fileChooser.showSaveDialog(ClientApp.primaryStage);
            if(selectedFile!=null) {
                if (!selectedFile.getName().contains(".")) {
                    selectedFile = new File(selectedFile.getAbsoluteFile() + ".docx");
                } else if (!selectedFile.getName().contains(".docx")) {
                    selectedFile = new File(selectedFile.getAbsoluteFile().toString().substring(0, selectedFile.getAbsoluteFile().toString().indexOf(".")) + ".docx");
                }
                model.createManualTest(selectedFile);
            }
        });

        submitButton.setOnAction(event -> {
            fileChooser.setTitle("Upload Exam");
            File defaultDirectory = new File("/home/");
            fileChooser.setInitialDirectory(defaultDirectory);
            File selectedFile = fileChooser.showOpenDialog(ClientApp.primaryStage);
            model.setManualExamFile(selectedFile);
            model.submitExam();
        });
    }

    private void setModel(IStudentExamExecutionData newModel){
        if(model==null)
            model = newModel;
    }
}
