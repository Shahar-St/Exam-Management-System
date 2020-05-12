package org.args.GUI;

import DatabaseAccess.Requests.EditQuestionRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditQuestionScreenController {


    private boolean isEditing = false;

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
    private Button CancelButton;

    @FXML
    private Button EditButton;


    public void initScreen(String lastModified, String author, String content, String[] answers, int correctAnswer) {
        LastModified.setText(lastModified);
        Author.setText(author);
        Content.setText(content);
        Answer1.setText(answers[0]);
        Answer2.setText(answers[1]);
        Answer3.setText(answers[2]);
        Answer4.setText(answers[3]);
        // set background color of the correct answer to green
        switch (correctAnswer) {
            case 0:
                Answer1.setStyle("-fx-background-color: #00ff00 ;");
                break;
            case 1:
                Answer2.setStyle("-fx-background-color: #00ff00 ;");
                break;
            case 2:
                Answer3.setStyle("-fx-background-color: #00ff00 ;");
                break;
            case 3:
                Answer4.setStyle("-fx-background-color: #00ff00 ;");
                break;
            default:
                System.out.println("Undefined correct answer");
                break;
        }

    }

    @FXML
    void CancelButtonClicked(ActionEvent event) {
        try {
            ClientApp.setRoot("QuestionManagementScreen");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @FXML
    void EditButtonClicked(ActionEvent event) {
        if (!isEditing) {
            LastModified.setEditable(true);
            Author.setEditable(true);
            Content.setEditable(true);
            Answer1.setEditable(true);
            Answer2.setEditable(true);
            Answer3.setEditable(true);
            Answer4.setEditable(true);
            EditButton.setText("Save");
            isEditing = true;

        } else {
            LastModified.setEditable(false);
            Author.setEditable(false);
            Content.setEditable(false);
            Answer1.setEditable(false);
            Answer2.setEditable(false);
            Answer3.setEditable(false);
            Answer4.setEditable(false);
            EditButton.setText("Edit");
            isEditing = false;
            EditQuestionRequest request = new EditQuestionRequest(1, "new content", new String[]{"1", "2", "3", "4"}, 1);
            ClientApp.sendRequest(request);
        }


    }

}