package org.args.GUI;

import DatabaseAccess.Requests.ExecuteExam.RaiseHandRequest;
import LightEntities.LightQuestion;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.args.Client.IStudentExamExecutionData;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class StudentExamExecutionController {

    private final Label timeElapsed = new Label();
    private final int fontSize = 24;
    private final String fontStyle = "Cambria";
    private final int smallSpacing = 5;
    private final int largeSpacing = 20;
    private IStudentExamExecutionData model;
    private long examDuration;
    @FXML
    private ImageView raiseHandImage;

    @FXML
    private Pagination questionsPagination;

    @FXML
    void initialize() {
        setModel(ClientApp.getModel());
        LocalDateTime start = model.getExamForStudentExecutionInitDate();
        LocalDateTime now = LocalDateTime.now();
        long dateDelta = start.until(now, ChronoUnit.SECONDS);
        examDuration = (model.getExamForStudentExecution().getDurationInMinutes() * 60) - dateDelta; // convert into seconds
        timeElapsed.setFont(Font.font(fontStyle, fontSize));
        assert questionsPagination != null;
        questionsPagination.setPageCount(model.getExamForStudentExecution().getLightQuestionList().size() + 1);
        questionsPagination.setCurrentPageIndex(0);
        questionsPagination.setMaxPageIndicatorCount(10);
        questionsPagination.setPageFactory((pageIndex) -> {
            VBox details;
            if (pageIndex == 0) {
                Label title_label = new Label("Title:");

                Label title = new Label(model.getExamForStudentExecution().getTitle());

                Label studentNotes_label = new Label("Notes:");

                Label sNotes = new Label(model.getExamForStudentExecution().getStudentNotes());

                Label duration_label = new Label("Duration:");

                Label duration = new Label(model.getExamForStudentExecution().getDurationInMinutes() + " Min");

                title_label.setFont(Font.font(fontStyle, fontSize));

                title.setFont(Font.font(fontStyle, fontSize));

                studentNotes_label.setFont(Font.font(fontStyle, fontSize));

                sNotes.setFont(Font.font(fontStyle, fontSize));

                duration_label.setFont(Font.font(fontStyle, fontSize));

                duration.setFont(Font.font(fontStyle, fontSize));

                HBox title_box = new HBox(title_label, title);

                title_box.setSpacing(smallSpacing);

                HBox student_box = new HBox(studentNotes_label, sNotes);

                student_box.setSpacing(smallSpacing);

                HBox duration_box = new HBox(duration_label, duration);

                duration_box.setSpacing(smallSpacing);

                details = new VBox(timeElapsed, title_box, student_box, duration_box);

                details.setSpacing(largeSpacing);

                details.setPadding(new Insets(20, 20, 20, 20));

                return details;
            }

            LightQuestion currentQuestion = model.getExamForStudentExecution().getLightQuestionList().get(pageIndex - 1);

            Label content_label = new Label("Content:");

            content_label.setFont(Font.font(fontStyle, fontSize));

            Label questionContent = new Label(currentQuestion.getQuestionContent());

            questionContent.setFont(Font.font(fontStyle, fontSize));

            RadioButton answer1 = new RadioButton(currentQuestion.getAnswers().get(0));

            answer1.setFont(Font.font(fontStyle, fontSize));

            RadioButton answer2 = new RadioButton(currentQuestion.getAnswers().get(1));

            answer2.setFont(Font.font(fontStyle, fontSize));

            RadioButton answer3 = new RadioButton(currentQuestion.getAnswers().get(2));

            answer3.setFont(Font.font(fontStyle, fontSize));

            RadioButton answer4 = new RadioButton(currentQuestion.getAnswers().get(3));

            answer4.setFont(Font.font(fontStyle, fontSize));

            Label questionScore = new Label("Question Score: " + model.getExamForStudentExecution().getQuestionsScores().get(pageIndex - 1));

            questionScore.setFont(Font.font(fontStyle, fontSize));

            details = new VBox(timeElapsed, content_label, questionContent, answer1, answer2, answer3, answer4, questionScore);

            details.setSpacing(largeSpacing);

            details.setPadding(new Insets(20, 20, 20, 20));

            answer1.setOnAction(e -> {
                answer2.setSelected(false);
                answer3.setSelected(false);
                answer4.setSelected(false);
                model.storeAnswer(pageIndex - 1, 0);

            });
            answer2.setOnAction(e -> {
                answer1.setSelected(false);
                answer3.setSelected(false);
                answer4.setSelected(false);
                model.storeAnswer(pageIndex - 1, 1);
            });
            answer3.setOnAction(e -> {
                answer1.setSelected(false);
                answer2.setSelected(false);
                answer4.setSelected(false);
                model.storeAnswer(pageIndex - 1, 2);
            });
            answer4.setOnAction(e -> {
                answer1.setSelected(false);
                answer2.setSelected(false);
                answer3.setSelected(false);
                model.storeAnswer(pageIndex - 1, 3);
            });

            if (model.getCorrectAnswersMap() != null && model.getCorrectAnswersMap().get(pageIndex - 1) != null) {
                switch (model.getCorrectAnswersMap().get(pageIndex - 1)) {
                    case 0:
                        answer1.setSelected(true);
                        break;
                    case 1:
                        answer2.setSelected(true);
                        break;
                    case 2:
                        answer3.setSelected(true);
                        break;
                    case 3:
                        answer4.setSelected(true);
                }
            }

            if (pageIndex == model.getExamForStudentExecution().getLightQuestionList().size()) {
                Button done = new Button();
                done.setText("Done");
                done.setFont(Font.font(fontStyle, fontSize));
                done.setOnMouseClicked(e -> {
                    if (model.getExamForStudentExecution().getLightQuestionList().size() > model.getCorrectAnswersMap().size()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Attention!");
                        alert.setHeaderText("Attention! , Please Confirm the following:");
                        alert.setContentText("You have left some answers unmarked are you sure you want to continue?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            model.submitExam();
                        }
                    } else
                        model.submitExam();


                });
                details = new VBox(timeElapsed, content_label, questionContent, answer1, answer2, answer3, answer4, questionScore, done);
                details.setSpacing(largeSpacing);
                details.setPadding(new Insets(20, 20, 20, 20));
            }

            return details;


        });
        // start countdown to submission.
        setTimer();

    }

    @FXML
    void onRaiseHandClicked(MouseEvent event) {
        if (!model.isHandRaised()) {
            model.raiseHand();
            model.setRaisedHand(true);
        } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Can't Raise Hand Again");
                alert.setHeaderText(null);
                alert.setContentText("You can only raise your hand once!");
                alert.show();
        }
    }

    private void setModel(IStudentExamExecutionData newModel) {
        if (model == null) {
            model = newModel;
        }
    }

    private void setTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!ClientApp.isRunning() || model.isSubmitted()) {
                    // in case that the window has been closed
                    timer.cancel();
                    timer.purge();
                    return;
                } else if (examDuration <= 0) {
                    timer.cancel();
                    timer.purge();
                    // submit and quit
                    model.submitAndQuit();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Attention!");
                        alert.setHeaderText(null);
                        alert.setContentText("Attention! \nExam Time Has Ended, Your'e Exam Has Been Submitted And \nYou're Now Being Redirected To The Main Screen");
                        alert.showAndWait();
                        ClientApp.setRoot("MainScreen"); // redirect client to main screen because of exam timeout.
                    });

                    return;
                } else if (model.isTimeExtensionGranted()) {
                    examDuration += model.getTimeExtensionDuration() * 60;
                    // set to false to prevent multiple additions for the same extension
                    model.setTimeExtensionGranted(false);
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Attention!");
                        alert.setHeaderText(null);
                        alert.setContentText("A Time Extension Has Been Approved For Your'e Exam, You Have Another: " + model.getTimeExtensionDuration() + " Minutes, Good Luck!");
                        alert.showAndWait();
                    });
                }
                Platform.runLater(() -> timeElapsed.setText("Remaining Time: " + (examDuration / 60) + ":" + (examDuration % 60)));
                examDuration--;

            }
        }, 1000, 1000);
    }


}

