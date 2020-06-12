package org.args.GUI;

import LightEntities.LightExecutedExam;
import LightEntities.LightQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.args.Client.IExamReviewData;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TeacherReviewCompExamController {

    private IExamReviewData model;

    private double finalScore = 0;

    private boolean isChangingGrade=false;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pagination pageView;

    @FXML
    private Button cancelButton;


    @FXML
    void onBackClick(ActionEvent event) {
        ClientApp.setRoot("TeacherExamGradesReviewScreen");
    }

    @FXML
    void initialize() {
        setModel(ClientApp.getModel());

        int fontSize = 32;

        String fontStyle = "Cambria";

        LightExecutedExam exam = model.getCurrentLightExecutedExam();

        Label title_label = new Label("Exam Title:");

        title_label.setFont(Font.font(fontStyle, fontSize));

        TextField title = new TextField(exam.getTitle());

        title.setFont(Font.font(fontStyle, fontSize));

        Label tester_label = new Label("Tester:");

        tester_label.setFont(Font.font(fontStyle, fontSize));

        TextField tester = new TextField(exam.getTesterUserName());

        tester.setFont(Font.font(fontStyle, fontSize));

        Label duration_label = new Label("Exam Duration In Minutes:");

        duration_label.setFont(Font.font(fontStyle, fontSize));

        TextField duration = new TextField(String.valueOf(exam.getDuration()));

        duration.setFont(Font.font(fontStyle, fontSize));

        assert pageView != null;
        pageView.setPageCount(exam.getLightQuestionList().size() + 2);
        pageView.setCurrentPageIndex(0);
        pageView.setMaxPageIndicatorCount(5);
        pageView.setPageFactory((pageIndex) -> {

            if (pageIndex == 0) {
                return new VBox(title_label, title,tester_label,tester,duration_label, duration);
            }
            if (pageIndex == exam.getLightQuestionList().size()+1) {

                calcFinalGrade(exam);

                Label finalGrade_label = new Label("Final Grade: ");

                TextField finalGrade = new TextField(String.valueOf(finalScore));

                finalGrade.setEditable(false);

                Label notes_label = new Label("Notes: ");

                TextArea notes = new TextArea();

                Label reasonForChangeGrade_label = new Label("Reasons For Grade Change:");

                TextArea reasonForChangeGrade = new TextArea();

                reasonForChangeGrade.setEditable(false);

                Button confirmButton = new Button("Confirm Grade");

                Button editButton = new Button(("Edit Grade"));

                confirmButton.setOnAction(event->{
                    if(!ClientApp.isDouble(finalGrade.getText())){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Alert");
                        alert.setHeaderText(null);
                        alert.setContentText("Only Positive Decimal Are Allowed");
                        alert.showAndWait();
                        return;
                    }

                    model.submitExamReview(finalScore,notes.getText(),null);
                });

                editButton.setOnAction(event -> {
                    if(!isChangingGrade){
                        isChangingGrade = true;
                        finalGrade.setEditable(true);
                        reasonForChangeGrade.setEditable(true);
                        confirmButton.setDisable(true);
                        editButton.setText("Done Editing.");
                    }else {
                        isChangingGrade=false;
                        finalGrade.setEditable(false);
                        reasonForChangeGrade.setEditable(false);
                        confirmButton.setDisable(false);
                        editButton.setText("Edit");
                    }
                });

                ButtonBar buttonBar = new ButtonBar();

                buttonBar.getButtons().add(editButton);

                buttonBar.getButtons().add(confirmButton);

                return new VBox(finalGrade_label,finalGrade,notes_label,notes,reasonForChangeGrade_label,reasonForChangeGrade,buttonBar);


            }
            Label date_label = new Label("Last Modified:");

            TextField lastModified = new TextField(exam.getLightQuestionList().get(pageIndex - 1).getLastModified().format(formatter));

            Label author_label = new Label("Author:");

            TextField author = new TextField(exam.getLightQuestionList().get(pageIndex - 1).getAuthor());

            Label content_label = new Label("Content:");

            TextArea content = new TextArea(exam.getLightQuestionList().get(pageIndex - 1).getQuestionContent());

            Label ans1_label = new Label("Answer 1:");

            TextField answer1 = new TextField(exam.getLightQuestionList().get(pageIndex - 1).getAnswers().get(0));

            Label ans2_label = new Label("Answer 2:");

            TextField answer2 = new TextField(exam.getLightQuestionList().get(pageIndex - 1).getAnswers().get(1));

            Label ans3_label = new Label("Answer 3:");

            TextField answer3 = new TextField(exam.getLightQuestionList().get(pageIndex - 1).getAnswers().get(2));

            Label ans4_label = new Label("Answer 4:");

            TextField answer4 = new TextField(exam.getLightQuestionList().get(pageIndex - 1).getAnswers().get(3));

            Label score_label = new Label("Question Score:");

            TextField score = new TextField(exam.getQuestionsScores().get(pageIndex - 1).toString());

            title.setEditable(false);
            duration.setEditable(false);
            lastModified.setEditable(false);
            author.setEditable(false);
            content.setEditable(false);
            answer1.setEditable(false);
            answer2.setEditable(false);
            answer3.setEditable(false);
            answer4.setEditable(false);
            score.setEditable(false);

            if (exam.getAnswersByStudent().get(pageIndex-1) == null || exam.getLightQuestionList().get(pageIndex-1).getCorrectAnswer() != exam.getAnswersByStudent().get(pageIndex-1)) {

                switch (exam.getAnswersByStudent().get(pageIndex-1)) {
                    case 0:
                        answer1.setStyle("-fx-background-color: #ff0000 ;");

                        break;
                    case 1:
                        answer2.setStyle("-fx-background-color: #ff0000 ;");

                        break;
                    case 2:
                        answer3.setStyle("-fx-background-color: #ff0000 ;");

                        break;
                    case 3:
                        answer4.setStyle("-fx-background-color: #ff0000 ;");

                        break;
                    default:
                        System.out.println("Undefined correct answer");
                        break;
                }

            } else {
                switch (exam.getLightQuestionList().get(pageIndex-1).getCorrectAnswer()) {
                    case 0:
                        answer1.setStyle("-fx-background-color: #00ff00 ;");

                        break;
                    case 1:
                        answer2.setStyle("-fx-background-color: #00ff00 ;");

                        break;
                    case 2:
                        answer3.setStyle("-fx-background-color: #00ff00 ;");

                        break;
                    case 3:
                        answer4.setStyle("-fx-background-color: #00ff00 ;");

                        break;
                    default:
                        System.out.println("Undefined correct answer");
                        break;
                }
            }


            return new VBox(date_label, lastModified, author_label, author, content_label, content, ans1_label, answer1, ans2_label,
                    answer2, ans3_label, answer3, ans4_label, answer4, score_label, score);
        });


    }

    public void setModel(IExamReviewData model) {
        this.model = model;
    }

    public void calcFinalGrade(LightExecutedExam exam){
        finalScore = 0;
        for(LightQuestion question:exam.getLightQuestionList()){
            int index = exam.getLightQuestionList().indexOf(question);
            if(question.getCorrectAnswer()==exam.getAnswersByStudent().get(index))
                finalScore+=exam.getQuestionsScores().get(index);
        }
    }
}
