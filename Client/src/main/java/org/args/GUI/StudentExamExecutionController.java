package org.args.GUI;

import LightEntities.LightQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.args.Client.IStudentExamExecutionData;

import java.time.format.DateTimeFormatter;

public class StudentExamExecutionController {

    private IStudentExamExecutionData model;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    private Pagination questionsPagination;

    @FXML
    void initialize() {
        setModel(ClientApp.getModel());
        assert questionsPagination != null ;
        questionsPagination.setPageCount(model.getExamForStudentExecution().getLightQuestionList().size());
        questionsPagination.setCurrentPageIndex(0);
        questionsPagination.setMaxPageIndicatorCount(5);
        questionsPagination.setPageFactory((pageIndex) -> {
            if(pageIndex==0){
                Label title_label = new Label("Exam Title:");

                Label title = new Label(model.getExamForStudentExecution().getTitle());

                Label studentNotes_label = new Label("Student Private Notes:");

                Label sNotes = new Label(model.getExamForStudentExecution().getStudentNotes());

                Label duration_label = new Label("Exam Duration In Minutes:");

                Label duration = new Label(String.valueOf(model.getExamForStudentExecution().getDurationInMinutes()));

                return new ScrollPane(new VBox(title_label,title,studentNotes_label,sNotes,duration_label,duration));
            }

            LightQuestion currentQuestion = model.getExamForStudentExecution().getLightQuestionList().get(pageIndex-1);

            Label date_label = new Label("Last Modified:");

            Label lastModified = new Label(currentQuestion.getLastModified().format(formatter));

            Label content_label = new Label("Content:");

            Label questionContent = new Label(currentQuestion.getQuestionContent());

            Label answer1_label = new Label("Answer 1:");

            Label answer1 = new Label(currentQuestion.getAnswers().get(0));

            Label answer2_label = new Label("Answer 2:");

            Label answer2 = new Label(currentQuestion.getAnswers().get(1));

            Label answer3_label = new Label("Answer 3:");

            Label answer3 = new Label(currentQuestion.getAnswers().get(2));

            Label answer4_label = new Label("Answer 4:");

            Label answer4 = new Label(currentQuestion.getAnswers().get(3));

            if(pageIndex==model.getExamForStudentExecution().getLightQuestionList().size()-1){
                Button done = new Button();
                done.setText("Done");
                done.setOnMouseClicked(e->{

                });
                return new ScrollPane(new VBox(date_label,lastModified,content_label,questionContent,answer1_label,answer1,answer2_label,answer2,answer3_label,answer3,answer4_label,answer4,done));
            }

            return new ScrollPane(new VBox(date_label,lastModified,content_label,questionContent,answer1_label,answer1,answer2_label,answer2,answer3_label,answer3,answer4_label,answer4));



        });

    }

    private void setModel(IStudentExamExecutionData newModel){
        if(model==null){
            model=newModel;

        }
    }

}

