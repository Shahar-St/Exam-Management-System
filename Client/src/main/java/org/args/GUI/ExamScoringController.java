package org.args.GUI;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.args.Client.IExamData;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExamScoringController {

    private IExamData model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> questionListView;

    @FXML
    private ListView<String> scoreListView;

    @FXML
    private Label scoreLabel;

    @FXML
    private ImageView backArrow;

    @FXML
    private Button doneButton;

    @FXML
    void initialize() {
        setModel(ClientApp.getModel());
        assert questionListView != null;
        assert scoreListView != null;
        assert scoreLabel != null;
        questionListView.setItems(model.getObservableExamQuestionsList());
        model.initQuestionsScoringList();
        scoreListView.setCellFactory(TextFieldListCell.forListView());
        scoreListView.setItems(model.getObservableQuestionsScoringList());
        scoreListView.setEditable(true);
        scoreLabel.textProperty().bind(model.currentExamTotalScoreProperty());
        scoreListView.setOnEditCommit(editEvent-> {
            //check if the new value is numeric string < 100
            try{
                double newVal = Double.parseDouble(editEvent.getNewValue());
                if (newVal>0){
                    scoreListView.getItems().set(editEvent.getIndex(),editEvent.getNewValue());
                    model.setCurrentExamTotalScore(String.valueOf(model.calcQuestionsScoringListValue()));
                    if(model.checkQuestionScoringList())
                        doneButton.setDisable(false);
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("Only Positive Decimal Are Allowed");
                    alert.showAndWait();
                }

            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("New Value is Not an Number");
                alert.showAndWait();
            }

        });
        if(!model.checkQuestionScoringList())
            doneButton.setDisable(true);


    }


    public void setModel(IExamData newModel) {
        if (model == null)
            model = newModel;
    }

    @FXML
    void backButtonClicked(MouseEvent event) {
        ClientApp.backToLastScene();

    }

    @FXML
    void doneButtonOnAction(ActionEvent event) {
        String title = model.getCurrentExamTitle();
        String tNotes = model.getCurrentExamTeacherNotes();
        String sNotes = model.getCurrentExamStudentNotes();
        List<String> questionsId = new ArrayList<>(model.getObservableExamQuestionsList());
        List<Double> questionsScoringList =  new ArrayList<>();
        int duration = Integer.parseInt(model.getCurrentExamDuration());
        for(String val : model.getObservableQuestionsScoringList()){
            questionsScoringList.add(Double.parseDouble(val));
        }
        model.saveExam(title,duration,tNotes,sNotes,questionsId,questionsScoringList,null);

    }
}

