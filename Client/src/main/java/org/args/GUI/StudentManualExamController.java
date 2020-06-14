package org.args.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.args.Client.IStudentExamExecutionData;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

public class StudentManualExamController {

    private IStudentExamExecutionData model;

    @FXML
    private Button downloadButton;

    @FXML
    private Button submitButton;

    private long examDuration;

    @FXML
    void initialize(){
        setModel(ClientApp.getModel());
        LocalDateTime start = model.getExamForStudentExecutionInitDate();
        LocalDateTime now = LocalDateTime.now();
        long dateDelta = start.until(now, ChronoUnit.SECONDS);
        examDuration = (model.getExamForStudentExecution().getDurationInMinutes()*60)-dateDelta; // convert into seconds
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

        setTimer();
    }

    private void setModel(IStudentExamExecutionData newModel){
        if(model==null)
            model = newModel;
    }

    private void setTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!ClientApp.isRunning()) {
                    // in case that the window has been closed
                    timer.cancel();
                    timer.purge();
                    return;
                } else if (examDuration <= 0) {
                    timer.cancel();
                    timer.purge();
                    // submit and quit
                    model.setManualExamFile(null);
                    model.submitAndQuit();

                    return;
                }else if(model.isTimeExtensionGranted()){
                    examDuration+=model.getTimeExtensionDuration()*60;
                    // set to false to prevent multiple additions for the same extension
                    model.setTimeExtensionGranted(false);
                }
                examDuration--;

            }
        }, 1000, 1000);
    }
}
