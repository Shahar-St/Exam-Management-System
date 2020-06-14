package org.args.GUI;

import LightEntities.LightExecutedExam;
import LightEntities.LightQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.args.Client.IExamReviewData;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TeacherReviewCompExamController {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private IExamReviewData model;
    private double finalScore = 0;
    private boolean isChangingGrade = false;
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

        int fontSize = 18;

        String fontStyle = "Cambria";

        LightExecutedExam exam = model.getCurrentLightExecutedExam();

        Label title_label = new Label("Exam Title:");

        title_label.setFont(Font.font(fontStyle, fontSize));

        Label title = new Label(exam.getTitle());

        title.setFont(Font.font(fontStyle, fontSize));

        Label tester_label = new Label("Tester:");

        tester_label.setFont(Font.font(fontStyle, fontSize));

        Label tester = new Label(exam.getTesterUserName());

        tester.setFont(Font.font(fontStyle, fontSize));

        Label duration_label = new Label("Exam Duration In Minutes:");

        duration_label.setFont(Font.font(fontStyle, fontSize));

        Label duration = new Label(String.valueOf(exam.getDuration()));

        duration.setFont(Font.font(fontStyle, fontSize));

        assert pageView != null;
        pageView.setPageCount(exam.getLightQuestionList().size() + 2);
        pageView.setCurrentPageIndex(0);
        pageView.setMaxPageIndicatorCount(5);
        pageView.setPageFactory((pageIndex) -> {

            if (pageIndex == 0) {
                VBox details = new VBox(title_label, title, tester_label, tester, duration_label, duration);
                details.setPadding(new Insets(20, 20, 20, 20));
                details.setSpacing(20);
                return new ScrollPane(details);
            }
            if (pageIndex == exam.getLightQuestionList().size() + 1) {

                calcFinalGrade(exam);

                Label finalGrade_label = new Label("Final Grade: ");

                finalGrade_label.setFont(Font.font(fontStyle, fontSize));

                TextField finalGrade = new TextField(String.valueOf(finalScore));

                finalGrade.setFont(Font.font(fontStyle, fontSize));

                finalGrade.setEditable(false);

                Label notes_label = new Label("Notes: ");

                notes_label.setFont(Font.font(fontStyle, fontSize));

                TextArea notes = new TextArea();

                notes.setFont(Font.font(fontStyle, fontSize));

                Label reasonForChangeGrade_label = new Label("Reasons For Grade Change:");

                reasonForChangeGrade_label.setFont(Font.font(fontStyle, fontSize));

                TextArea reasonForChangeGrade = new TextArea();

                reasonForChangeGrade.setFont(Font.font(fontStyle, fontSize));

                reasonForChangeGrade.setEditable(false);

                Button confirmButton = new Button("Confirm Grade");

                confirmButton.setFont(Font.font(fontStyle, fontSize));

                Button editButton = new Button(("Edit Grade"));

                editButton.setFont(Font.font(fontStyle, fontSize));

                confirmButton.setOnAction(event -> {
                    if (!ClientApp.isDouble(finalGrade.getText())) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Alert");
                        alert.setHeaderText(null);
                        alert.setContentText("Only Positive Decimal Are Allowed");
                        alert.showAndWait();
                        return;
                    }

                    //model.submitExamReview(finalScore, notes.getText(), null);
                    model.submitExamReview(Integer.valueOf(finalGrade.getText()), notes.getText(),reasonForChangeGrade.getText(), null);
                });

                editButton.setOnAction(event -> {
                    if (!isChangingGrade) {
                        isChangingGrade = true;
                        finalGrade.setEditable(true);
                        reasonForChangeGrade.setEditable(true);
                        confirmButton.setDisable(true);
                        editButton.setText("Done Editing.");
                    } else {
                        isChangingGrade = false;
                        finalGrade.setEditable(false);
                        reasonForChangeGrade.setEditable(false);
                        confirmButton.setDisable(false);
                        editButton.setText("Edit");
                    }
                });

                ButtonBar buttonBar = new ButtonBar();

                buttonBar.getButtons().add(editButton);

                buttonBar.getButtons().add(confirmButton);

                VBox details = new VBox(finalGrade_label, finalGrade, notes_label, notes, reasonForChangeGrade_label, reasonForChangeGrade, buttonBar);

                details.setPadding(new Insets(20, 20, 20, 20));

                details.setSpacing(20);

                return details;


            }

            Label content_label = new Label("Content:");

            content_label.setFont(Font.font(fontStyle, fontSize));

            Label content = new Label(exam.getLightQuestionList().get(pageIndex - 1).getQuestionContent());

            content.setFont(Font.font(fontStyle, fontSize));

            Label ans1_label = new Label("Answer 1:");

            ans1_label.setFont(Font.font(fontStyle, fontSize));

            TextField answer1 = new TextField(exam.getLightQuestionList().get(pageIndex - 1).getAnswers().get(0));

            answer1.setFont(Font.font(fontStyle, fontSize));

            Label ans2_label = new Label("Answer 2:");

            ans2_label.setFont(Font.font(fontStyle, fontSize));

            TextField answer2 = new TextField(exam.getLightQuestionList().get(pageIndex - 1).getAnswers().get(1));

            answer2.setFont(Font.font(fontStyle, fontSize));

            Label ans3_label = new Label("Answer 3:");

            ans3_label.setFont(Font.font(fontStyle, fontSize));

            TextField answer3 = new TextField(exam.getLightQuestionList().get(pageIndex - 1).getAnswers().get(2));

            answer3.setFont(Font.font(fontStyle, fontSize));

            Label ans4_label = new Label("Answer 4:");

            ans4_label.setFont(Font.font(fontStyle, fontSize));

            TextField answer4 = new TextField(exam.getLightQuestionList().get(pageIndex - 1).getAnswers().get(3));

            answer4.setFont(Font.font(fontStyle, fontSize));

            Label score_label = new Label("Question Score:");

            score_label.setFont(Font.font(fontStyle, fontSize));

            Label score = new Label(exam.getQuestionsScores().get(pageIndex - 1).toString());

            score.setFont(Font.font(fontStyle, fontSize));

            answer1.setEditable(false);
            answer2.setEditable(false);
            answer3.setEditable(false);
            answer4.setEditable(false);

            if (exam.getAnswersByStudent().isEmpty() || (pageIndex-1) >= exam.getAnswersByStudent().size())
                answer1.setStyle("-fx-background-color: #ff0000 ;");

            else if (exam.getLightQuestionList().get(pageIndex - 1).getCorrectAnswer() != exam.getAnswersByStudent().get(pageIndex - 1)) {

                switch (exam.getAnswersByStudent().get(pageIndex - 1)) {
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
                switch (exam.getLightQuestionList().get(pageIndex - 1).getCorrectAnswer()) {
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

            VBox details = new VBox(content_label, content, ans1_label, answer1, ans2_label,
                    answer2, ans3_label, answer3, ans4_label, answer4, score_label, score);
            details.setSpacing(20);
            details.setPadding(new Insets(20, 20, 20, 20));

            return new ScrollPane(details);
        });


    }

    public void setModel(IExamReviewData model) {
        this.model = model;
    }

    public void calcFinalGrade(LightExecutedExam exam) {
        finalScore = 0;
        for (LightQuestion question : exam.getLightQuestionList()) {
            int index = exam.getLightQuestionList().indexOf(question);
            if (index < exam.getAnswersByStudent().size() && question.getCorrectAnswer() == exam.getAnswersByStudent().get(index))
                finalScore += exam.getQuestionsScores().get(index);
        }
    }
}
