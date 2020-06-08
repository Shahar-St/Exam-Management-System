package org.args.GUI;

import LightEntities.LightQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.args.Client.IStudentExamExecutionData;

import java.time.format.DateTimeFormatter;

public class StudentExamExecutionController {

    private IStudentExamExecutionData model;


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
            VBox details = null;
            if(pageIndex==0){
                Label title_label = new Label("Exam Title:");

                Label title = new Label(model.getExamForStudentExecution().getTitle());

                Label studentNotes_label = new Label("Student Private Notes:");

                Label sNotes = new Label(model.getExamForStudentExecution().getStudentNotes());

                Label duration_label = new Label("Exam Duration In Minutes:");

                Label duration = new Label(String.valueOf(model.getExamForStudentExecution().getDurationInMinutes()));

                title_label.setFont(Font.font("Cambria", 32));

                title.setFont(Font.font("Cambria", 32));

                studentNotes_label.setFont(Font.font("Cambria", 32));

                sNotes.setFont(Font.font("Cambria", 32));

                duration_label.setFont(Font.font("Cambria", 32));

                duration.setFont(Font.font("Cambria", 32));

                HBox title_box = new HBox(title_label,title);

                title_box.setSpacing(5);

                HBox student_box = new HBox(studentNotes_label,sNotes);

                student_box.setSpacing(5);

                HBox duration_box = new HBox(duration_label,duration);

                duration_box.setSpacing(5);

                details = new VBox(title_box,student_box,duration_box);

                details.setSpacing(20);

                return new ScrollPane(details);
            }

            LightQuestion currentQuestion = model.getExamForStudentExecution().getLightQuestionList().get(pageIndex-1);

            Label content_label = new Label("Content:");

            content_label.setFont(Font.font("Cambria", 32));

            Label questionContent = new Label(currentQuestion.getQuestionContent());

            questionContent.setFont(Font.font("Cambria", 32));

            Label answer1_label = new Label("Answer 1:");

            answer1_label.setFont(Font.font("Cambria", 32));

            RadioButton answer1 = new RadioButton(currentQuestion.getAnswers().get(0));

            answer1.setFont(Font.font("Cambria", 32));

            HBox ans1_hbox = new HBox(answer1_label,answer1);

            ans1_hbox.setSpacing(5);

            Label answer2_label = new Label("Answer 2:");

            answer2_label.setFont(Font.font("Cambria", 32));

            RadioButton answer2 = new RadioButton(currentQuestion.getAnswers().get(1));

            answer2.setFont(Font.font("Cambria", 32));

            HBox ans2_hbox = new HBox(answer2_label,answer2);

            ans2_hbox.setSpacing(5);

            Label answer3_label = new Label("Answer 3:");

            answer3_label.setFont(Font.font("Cambria", 32));

            RadioButton answer3 = new RadioButton(currentQuestion.getAnswers().get(2));

            answer3.setFont(Font.font("Cambria", 32));

            HBox ans3_hbox = new HBox(answer3_label,answer3);

            ans3_hbox.setSpacing(5);

            Label answer4_label = new Label("Answer 4:");

            answer4_label.setFont(Font.font("Cambria", 32));

            RadioButton answer4 = new RadioButton(currentQuestion.getAnswers().get(3));

            answer4.setFont(Font.font("Cambria", 32));

            HBox ans4_hbox = new HBox(answer4_label,answer4);

            ans4_hbox.setSpacing(5);

            VBox ans12_vbox = new VBox(ans1_hbox,ans2_hbox);

            ans12_vbox.setSpacing(10);

            VBox ans34_vobx = new VBox(ans3_hbox,ans4_hbox);

            ans34_vobx.setSpacing(10);

            HBox answers_hbox = new HBox(ans12_vbox,ans34_vobx);

            answers_hbox.setSpacing(20);

            details = new VBox(content_label,questionContent,answers_hbox);

            details.setSpacing(20);

            answer1.setOnAction(e->{
                answer2.setSelected(false);
                answer3.setSelected(false);
                answer4.setSelected(false);
                model.storeAnswer(pageIndex-1,0);

            });
            answer2.setOnAction(e->{
                answer1.setSelected(false);
                answer3.setSelected(false);
                answer4.setSelected(false);
                model.storeAnswer(pageIndex-1,1);
            });
            answer3.setOnAction(e->{
                answer1.setSelected(false);
                answer2.setSelected(false);
                answer4.setSelected(false);
                model.storeAnswer(pageIndex-1,2);
            });
            answer4.setOnAction(e->{
                answer1.setSelected(false);
                answer2.setSelected(false);
                answer3.setSelected(false);
                model.storeAnswer(pageIndex-1,3);
            });

            if(model.getCorrectAnswersList()!=null && model.getCorrectAnswersList().size()>=pageIndex) {
                switch (model.getCorrectAnswersList().get(pageIndex - 1)) {
                    case 0: answer1.setSelected(true);
                    break;
                    case 1: answer2.setSelected(true);
                    break;
                    case 2: answer3.setSelected(true);
                    break;
                    case 3: answer4.setSelected(true);
                }
            }

            if(pageIndex==model.getExamForStudentExecution().getLightQuestionList().size()-1){
                Button done = new Button();
                done.setText("Done");
                done.setFont(Font.font("Cambria", 32));
                done.setOnMouseClicked(e->{
                    model.submitExam();

                });
                details = new VBox(content_label,questionContent,answers_hbox,done);
                details.setSpacing(20);
            }

            return new ScrollPane(details);



        });

    }

    private void setModel(IStudentExamExecutionData newModel){
        if(model==null){
            model=newModel;

        }
    }

}

