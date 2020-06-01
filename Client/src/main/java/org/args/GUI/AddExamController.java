package org.args.GUI;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.args.Client.IAddExamData;

public class AddExamController {

    @FXML
    private Label questionsListTitle;

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
    public void initialize() {
        setModel(ClientApp.getModel());
        questionsListTitle.setText("Questions from " + model.getCurrentCourse() + " course");
        courseQuestionsListView.setItems(model.getObservableQuestionsList());
        examQuestionsListView.setItems(model.getObservableExamQuestionsList());
        examQuestionsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        courseQuestionsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    void addToExamQuestionList(ActionEvent event) {
        for (String selectedItem : courseQuestionsListView.getSelectionModel().getSelectedItems()) {
            if (!model.getObservableExamQuestionsList().contains(selectedItem))
                model.addToExamQuestionsList(selectedItem);
        }
    }

    @FXML
    void allQuestionsAdded(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {
        model.cancelExamAddition();
        ClientApp.backToLastScene();
    }

    @FXML
    void removeFromExamQuestionList(ActionEvent event) {
        for (String selectedItem : examQuestionsListView.getSelectionModel().getSelectedItems())
            model.removeFromExamQuestionsList(selectedItem);
    }

    @FXML
    void showQuestionDetails(ActionEvent event) {
        if (courseQuestionsListView.getSelectionModel().getSelectedItem() != null) {
            int indexOfColon = courseQuestionsListView.getSelectionModel().getSelectedItem().indexOf(':');
            String questionId = courseQuestionsListView.getSelectionModel().getSelectedItem().substring(1, indexOfColon);
            model.saveQuestionDetails(questionId);
        }
    }

    @FXML
    void handleMouseEventCourseQuestionsList(MouseEvent event) {
        if (!removeButton.isDisabled())
            removeButton.setDisable(true);
        if (addButton.isDisabled())
            addButton.setDisable(false);
        examQuestionsListView.getSelectionModel().clearSelection();
        detailsButton.setDisable(courseQuestionsListView.getSelectionModel().getSelectedItems().size() > 1);
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 &&
                event.getTarget() instanceof ListCell && courseQuestionsListView.getSelectionModel().getSelectedItems().size() == 1)
        {
            String selectedItem = courseQuestionsListView.getSelectionModel().getSelectedItem();
            if (!model.getObservableExamQuestionsList().contains(selectedItem))
                model.addToExamQuestionsList(selectedItem);

        }


    }

    @FXML
    void handleMouseEventExamQuestionsList(MouseEvent event) {
        if (removeButton.isDisabled())
            removeButton.setDisable(false);
        if (!addButton.isDisabled())
            addButton.setDisable(true);
        courseQuestionsListView.getSelectionModel().clearSelection();
        detailsButton.setDisable(examQuestionsListView.getSelectionModel().getSelectedItems().size() > 1);
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 &&
                event.getTarget() instanceof ListCell && examQuestionsListView.getSelectionModel().getSelectedItems().size() == 1)
            model.removeFromExamQuestionsList(examQuestionsListView.getSelectionModel().getSelectedItem());

    }

}
