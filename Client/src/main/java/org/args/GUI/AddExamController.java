package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.args.Client.IAddExamData;

public class AddExamController {

    @FXML
    private ListView<String> courseQuestionsListView;

    @FXML
    private ListView<String> examQuestionsListView;

    @FXML
    private Button detailsButton;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button doneButton;

    @FXML
    private Button cancelButton;

    private IAddExamData model;

    public void setModel(IAddExamData model) {
        this.model = model;
    }

    @FXML
    public void initialize()
    {
        setModel(ClientApp.getModel());
        courseQuestionsListView.setItems(model.getObservableQuestionsList());
        examQuestionsListView.setItems(model.getObservableExamQuestionsList());
    }

    @FXML
    void addToExamQuestionList(ActionEvent event) {
        model.addToExamQuestionsList(courseQuestionsListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void allQuestionsAdded(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {
        model.cancel();
        ClientApp.setRoot("ExamManagementScreen");
    }

    @FXML
    void removeFromExamQuestionList(ActionEvent event) {
        model.removeFromExamQuestionsList(examQuestionsListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void showQuestionDetails(ActionEvent event) {
        if(courseQuestionsListView.getSelectionModel().getSelectedItem() != null)
        {
            int indexOfColon = courseQuestionsListView.getSelectionModel().getSelectedItem().indexOf(':');
            String questionId = courseQuestionsListView.getSelectionModel().getSelectedItem().substring(1, indexOfColon);
            model.saveQuestionDetails(questionId);
        }
    }

}
