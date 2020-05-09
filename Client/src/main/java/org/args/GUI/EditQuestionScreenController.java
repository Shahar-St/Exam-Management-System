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

import java.io.IOException;

public class EditQuestionScreenController {

    private ClientApp clientApp=null;

    private boolean isEditing=false;

    @FXML
    private  TextField LastModified;

    @FXML
    private  TextField Author;

    @FXML
    private  TextArea Content;

    @FXML
    private  TextField Answer1;

    @FXML
    private  TextField Answer2;

    @FXML
    private  TextField Answer3;

    @FXML
    private  TextField Answer4;

    @FXML
    private  Button CancelButton;

    @FXML
    private  Button EditButton;


    public void initScreen(String lastModified, String author, String content, String[] answers, int correctAnswer) {
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
        try {
            ClientApp.setRoot("QuestionManagementScreen");
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    @FXML
    void EditButtonClicked(ActionEvent event) {
       if(!isEditing){
           LastModified.setEditable(true);
           Author.setEditable(true);
           Content.setEditable(true);
           Answer1.setEditable(true);
           Answer2.setEditable(true);
           Answer3.setEditable(true);
           Answer4.setEditable(true);
           EditButton.setText("Save");

       }else{
           LastModified.setEditable(false);
           Author.setEditable(false);
           Content.setEditable(false);
           Answer1.setEditable(false);
           Answer2.setEditable(false);
           Answer3.setEditable(false);
           Answer4.setEditable(false);
           EditButton.setText("Edit");
       }


    }

    public void setClientApp(ClientApp clientApp) {
        if(this.clientApp == null){
            this.clientApp = clientApp;
        }

    }
}