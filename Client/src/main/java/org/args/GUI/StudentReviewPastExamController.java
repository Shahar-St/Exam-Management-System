package org.args.GUI;

import LightEntities.LightExecutedExam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.args.Client.IViewPastExamsData;

@SuppressWarnings("DuplicatedCode")
public class StudentReviewPastExamController {

    private IViewPastExamsData model;


    @FXML
    private Pagination pageView;

    @FXML
    private Button cancelButton;


    @FXML
    void onBackClick(ActionEvent event) {
        ClientApp.setRoot("StudentPastExamsScreen");
    }

    @SuppressWarnings("DuplicatedCode")
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

        Label grade_label = new Label("Grade:");

        grade_label.setFont(Font.font(fontStyle, fontSize));

        Label grade = new Label(String.valueOf(exam.getGrade()));

        grade.setFont(Font.font(fontStyle, fontSize));

        Label notes_label = new Label("Notes:");

        Label notes = new Label(exam.getCommentsAfterCheck());

        assert pageView != null;
        pageView.setPageCount(exam.getLightQuestionList().size() + 1);
        pageView.setCurrentPageIndex(0);
        pageView.setMaxPageIndicatorCount(5);
        pageView.setPageFactory((pageIndex) -> {

            if (pageIndex == 0) {
                VBox details = new VBox(title_label, title, tester_label, tester, grade_label, grade,notes_label,notes);
                details.setPadding(new Insets(20, 20, 20, 20));
                details.setSpacing(20);
                return new ScrollPane(details);
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

    public void setModel(IViewPastExamsData model) {
        this.model = model;
    }


}
