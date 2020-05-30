/**
 * Sample Skeleton for 'ViewExamScreen.fxml' Controller Class
 */

package org.args.GUI;

import LightEntities.LightQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.args.Client.IExamData;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewExamController {

    private IExamData model;

    void setModel(IExamData dataModel) {
        if (model == null) {
            model = dataModel;
        }
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="pageView"
    private Pagination pageView; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        setModel(ClientApp.getModel());
        List<LightQuestion> questionList = model.getLightQuestionListFromCurrentExam();
        List<Double> questionsScores = model.getCurrentExamQuestionsScoreList();
        String examDescription = model.getCurrentExamDescription();
        String examTeacherPrivateNotes = model.getCurrentExamTeacherPrivateNotes();
        String examDuration = Integer.toString(model.getCurrentExamDurationOnMinutes());
        assert pageView != null : "fx:id=\"pageView\" was not injected: check your FXML file 'ViewExamScreen.fxml'.";
        pageView.setPageCount(questionList.size());
        pageView.setCurrentPageIndex(0);
        pageView.setMaxPageIndicatorCount(5);
        pageView.setPageFactory((pageIndex) -> {
            Label description_label = new Label("Exam Description:");

            TextField description = new TextField(examDescription);

            Label privateNotes_label = new Label("Teacher Private Notes:");

            TextField privateNotes = new TextField(examTeacherPrivateNotes);

            Label duration_label = new Label("Exam Duration In Minutes:");

            TextField duration = new TextField(examDuration);

            Label date_label = new Label("Last Modified:");

            TextField lastModified = new TextField(questionList.get(pageIndex).getLastModified().toString());

            Label author_label = new Label("Author:");

            TextField author = new TextField(questionList.get(pageIndex).getAuthor());

            Label content_label = new Label("Content:");

            TextArea content = new TextArea(questionList.get(pageIndex).getQuestionContent());

            Label ans1_label = new Label("Answer 1:");

            TextField answer1 = new TextField(questionList.get(pageIndex).getAnswers().get(0));

            Label ans2_label = new Label("Answer 2:");

            TextField answer2 = new TextField(questionList.get(pageIndex).getAnswers().get(1));

            Label ans3_label = new Label("Answer 3:");

            TextField answer3 = new TextField(questionList.get(pageIndex).getAnswers().get(2));

            Label ans4_label = new Label("Answer 4:");

            TextField answer4 = new TextField(questionList.get(pageIndex).getAnswers().get(3));

            Label score_label = new Label("Question Score:");

            TextField score = new TextField(questionsScores.get(pageIndex).toString());

            description.setEditable(false);
            privateNotes.setEditable(false);
            duration.setEditable(false);
            answer1.setEditable(false);
            answer2.setEditable(false);
            answer3.setEditable(false);
            answer4.setEditable(false);
            score.setEditable(false);

            return new VBox(description_label, description, privateNotes_label, privateNotes, duration_label, duration,
                    date_label, lastModified, author_label, author, content_label, content, ans1_label, answer1, ans2_label,
                    answer2, ans3_label, answer3, ans4_label, answer4, score_label, score);

        });

    }
}
