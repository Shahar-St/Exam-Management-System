/*
  Sample Skeleton for 'ViewExamScreen.fxml' Controller Class
 */

package org.args.GUI;

import LightEntities.LightQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.args.Client.IExamData;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewExamController {

    private IExamData model;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
    private Button cancelButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    void onBackClick(ActionEvent event) {
        model.clearDetailsScreen();
        ClientApp.backToLastScene();
    }

    @FXML
    void deleteExam(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Exam");
        alert.setHeaderText("Delete Exam " + model.getCurrentExamTitle());
        alert.setContentText("Are you sure you want to delete this exam?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            model.deleteExam();
        }
    }

    @FXML
    void editExam(ActionEvent event) {
        model.startExamEdit();
        ClientApp.setRoot("ExamDetailsScreen");
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        setModel(ClientApp.getModel());
        deleteButton.setDisable(!model.isExamDeletable());
        editButton.setDisable(!model.isExamDeletable());
        List<LightQuestion> questionList = model.getLightQuestionListFromCurrentExam();
        List<Double> questionsScores = model.getCurrentExamQuestionsScoreList();
        String examTitle = model.getCurrentExamTitle();
        String examTeacherPrivateNotes = model.getCurrentExamTeacherNotes();
        String examStudentNotes = model.getCurrentExamStudentNotes();
        String examDuration = model.getCurrentExamDuration();
        Label title_label = new Label("Exam Title:");

        TextField title = new TextField(examTitle);

        Label privateNotes_label = new Label("Teacher Private Notes:");

        TextField privateNotes = new TextField(examTeacherPrivateNotes);

        Label studentNotes_label = new Label("Student Private Notes:");

        TextField sNotes = new TextField(examStudentNotes);

        Label duration_label = new Label("Exam Duration In Minutes:");

        TextField duration = new TextField(examDuration);

        assert pageView != null;
        pageView.setPageCount(questionList.size()+1);
        pageView.setCurrentPageIndex(0);
        pageView.setMaxPageIndicatorCount(5);
        pageView.setPageFactory((pageIndex) -> {

            title.setEditable(false);
            privateNotes.setEditable(false);
            sNotes.setEditable(false);
            duration.setEditable(false);

            if(pageIndex==0){
                return new VBox(title_label, title, privateNotes_label, privateNotes,studentNotes_label,sNotes ,duration_label, duration);
            }
            Label date_label = new Label("Last Modified:");

            TextField lastModified = new TextField(questionList.get(pageIndex-1).getLastModified().format(formatter));

            Label author_label = new Label("Author:");

            TextField author = new TextField(questionList.get(pageIndex-1).getAuthor());

            Label content_label = new Label("Content:");

            TextArea content = new TextArea(questionList.get(pageIndex-1).getQuestionContent());

            Label ans1_label = new Label("Answer 1:");

            TextField answer1 = new TextField(questionList.get(pageIndex-1).getAnswers().get(0));

            Label ans2_label = new Label("Answer 2:");

            TextField answer2 = new TextField(questionList.get(pageIndex-1).getAnswers().get(1));

            Label ans3_label = new Label("Answer 3:");

            TextField answer3 = new TextField(questionList.get(pageIndex-1).getAnswers().get(2));

            Label ans4_label = new Label("Answer 4:");

            TextField answer4 = new TextField(questionList.get(pageIndex-1).getAnswers().get(3));

            Label score_label = new Label("Question Score:");

            TextField score = new TextField(questionsScores.get(pageIndex-1).toString());


            lastModified.setEditable(false);
            author.setEditable(false);
            content.setEditable(false);
            answer1.setEditable(false);
            answer2.setEditable(false);
            answer3.setEditable(false);
            answer4.setEditable(false);
            score.setEditable(false);


            switch (questionList.get(pageIndex-1).getCorrectAnswer()) {
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


            return new VBox(date_label, lastModified, author_label, author, content_label, content, ans1_label, answer1, ans2_label,
                    answer2, ans3_label, answer3, ans4_label, answer4, score_label, score);
        });

    }
}
