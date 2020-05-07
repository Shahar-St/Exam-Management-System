package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

public class EditQuestionScreenController {

    @FXML
    private static TextField LastModified;

    @FXML
    private static TextField Author;

    @FXML
    private static TextArea Content;

    @FXML
    private static TextField Answer1;

    @FXML
    private static TextField Answer2;

    @FXML
    private static TextField Answer3;

    @FXML
    private static TextField Answer4;

    @FXML
    private static Button CancelButton;

    @FXML
    private static Button EditButton;


    public static void initScreen(String lastModified, String author, String content, String[] answers, int correctAnswer) {
        LastModified.setText(lastModified);
        Author.setText(author);
        Content.setText(content);
        Answer1.setText(answers[0]);
        Answer2.setText(answers[1]);
        Answer3.setText(answers[2]);
        Answer4.setText(answers[3]);
        // set backgroud color of the correct answer to green
        switch (correctAnswer) {
            case 0:
                Answer1.setBackground(new Background(new BackgroundFill(Paint.valueOf("00FF00"), CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case 1:
                Answer2.setBackground(new Background(new BackgroundFill(Paint.valueOf("00FF00"), CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case 2:
                Answer3.setBackground(new Background(new BackgroundFill(Paint.valueOf("00FF00"), CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case 3:
                Answer4.setBackground(new Background(new BackgroundFill(Paint.valueOf("00FF00"), CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            default:
                System.out.println("Undefined correct answer");
                break;
        }

    }

    @FXML
    void CancelButtonClicked(ActionEvent event) {

    }

    @FXML
    void EditButtonClicked(ActionEvent event) {

    }

}