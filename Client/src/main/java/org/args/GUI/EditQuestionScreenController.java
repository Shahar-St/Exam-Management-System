package org.args.GUI;

import DatabaseAccess.Requests.EditQuestionRequest;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EditQuestionScreenController {


    private boolean isEditing = false;

    private int correctAnswer;

    private String questionID;

    private static ObservableList choiceItems;

    @FXML
    private TextField LastModified;

    @FXML
    private TextField Author;

    @FXML
    private TextArea Content;

    @FXML
    private TextField Answer1;

    @FXML
    private TextField Answer2;

    @FXML
    private TextField Answer3;

    @FXML
    private TextField Answer4;

    @FXML
    private ChoiceBox<String> correctAnswerChoice;

    @FXML
    private Button CancelButton;

    @FXML
    private Button EditButton;


    public void initScreen(String questionId,String lastModified, String author, String content, List<String> answers, int correctAnswer) {
        this.questionID = questionId;
        LastModified.setText(lastModified);
        Author.setText(author);
        Content.setText(content);
        Answer1.setText(answers.get(0));
        Answer2.setText(answers.get(1));
        Answer3.setText(answers.get(2));
        Answer4.setText(answers.get(3));
        this.correctAnswer = correctAnswer;

    }

    @FXML
    private void initialize() {
        if (choiceItems == null)
        {
            String[] answers = {"Answer 1", "Answer 2", "Answer 3", "Answer 4"};
            choiceItems = FXCollections.observableArrayList(answers);


        }
        correctAnswerChoice.getItems().addAll(choiceItems);
        switch (this.correctAnswer)
        {
            case 0:
                Answer1.setStyle("-fx-background-color: #00ff00 ;");
                correctAnswerChoice.setValue("Answer 1");
                break;
            case 1:
                Answer2.setStyle("-fx-background-color: #00ff00 ;");
                correctAnswerChoice.setValue("Answer 2");
                break;
            case 2:
                Answer3.setStyle("-fx-background-color: #00ff00 ;");
                correctAnswerChoice.setValue("Answer 3");
                break;
            case 3:
                Answer4.setStyle("-fx-background-color: #00ff00 ;");
                correctAnswerChoice.setValue("Answer 4");
                break;
            default:
                System.out.println("Undefined correct answer");
                break;
        }


    }

    public void editSuccess() {
        try
        {
            Scene scene = new Scene(ClientApp.loadFXML("AlertPopUp"));
            Stage popup = new Stage();
            popup.setScene(scene);
            popup.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void editFailed() {

    }

    @FXML
    void choiceBoxClicked(MouseEvent event) {
        correctAnswerChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                switch (t1.intValue())
                {
                    case 0:
                        Answer1.setStyle("-fx-background-color: #00ff00 ;");
                        Answer2.setStyle("-fx-background-color: #ffffff ;");
                        Answer3.setStyle("-fx-background-color: #ffffff ;");
                        Answer4.setStyle("-fx-background-color: #ffffff ;");
                        correctAnswer = 0;
                        break;
                    case 1:
                        Answer1.setStyle("-fx-background-color: #ffffff ;");
                        Answer2.setStyle("-fx-background-color: #00ff00 ;");
                        Answer3.setStyle("-fx-background-color: #ffffff ;");
                        Answer4.setStyle("-fx-background-color: #ffffff ;");
                        correctAnswer = 1;
                        break;
                    case 2:
                        Answer1.setStyle("-fx-background-color: #ffffff ;");
                        Answer2.setStyle("-fx-background-color: #ffffff ;");
                        Answer3.setStyle("-fx-background-color: #00ff00 ;");
                        Answer4.setStyle("-fx-background-color: #ffffff ;");
                        correctAnswer = 2;
                        break;
                    case 3:
                        Answer1.setStyle("-fx-background-color: #ffffff ;");
                        Answer2.setStyle("-fx-background-color: #ffffff ;");
                        Answer3.setStyle("-fx-background-color: #ffffff ;");
                        Answer4.setStyle("-fx-background-color: #00ff00 ;");
                        correctAnswer = 3;
                        break;
                    default:
                        System.out.println("Undefined correct answer");
                        break;
                }
            }
        });
    }


    @FXML
    void CancelButtonClicked(ActionEvent event) {
        ClientApp.setRoot("QuestionManagementScreen");
    }

    @FXML
    void EditButtonClicked(ActionEvent event) {
        if (!isEditing)
        {
            LastModified.setEditable(true);
            Author.setEditable(true);
            Content.setEditable(true);
            Answer1.setEditable(true);
            Answer2.setEditable(true);
            Answer3.setEditable(true);
            Answer4.setEditable(true);
            EditButton.setText("Save");
            isEditing = true;
            correctAnswerChoice.setDisable(false);
            switch (this.correctAnswer)
            {
                case 0:
                    Answer1.setStyle("-fx-background-color: #ffffff ;");
                    break;
                case 1:
                    Answer2.setStyle("-fx-background-color: #ffffff ;");
                    break;
                case 2:
                    Answer3.setStyle("-fx-background-color: #ffffff ;");
                    break;
                case 3:
                    Answer4.setStyle("-fx-background-color: #ffffff ;");
                    break;
                default:
                    System.out.println("Undefined correct answer");
                    break;
            }

        }
        else
        {
            LastModified.setEditable(false);
            Author.setEditable(false);
            Content.setEditable(false);
            Answer1.setEditable(false);
            Answer2.setEditable(false);
            Answer3.setEditable(false);
            Answer4.setEditable(false);
            EditButton.setText("Edit");
            isEditing = false;
            correctAnswerChoice.setDisable(true);
            EditQuestionRequest request = new EditQuestionRequest(this.questionID, Content.getText(),
                    Arrays.asList(Answer1.getText(), Answer2.getText(), Answer3.getText(),
                            Answer4.getText()), correctAnswer);
            ClientApp.sendRequest(request);
        }
    }


}