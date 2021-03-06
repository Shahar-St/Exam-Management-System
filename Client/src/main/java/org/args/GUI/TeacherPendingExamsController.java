package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.args.Client.IExamReviewData;

public class TeacherPendingExamsController {

    IExamReviewData model;

    @FXML
    private ImageView backButton;

    @FXML
    private ListView<String> pendingListView;

    @FXML
    private Button showGradesButton;

    public void setModel(IExamReviewData model) {
        this.model = model;
    }

    @FXML
    void initialize()
    {
        setModel(ClientApp.getModel());
        model.loadPendingExams();
        pendingListView.setItems(model.getPendingExamsObservableList());
    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        if(showGradesButton.isDisabled())
            showGradesButton.setDisable(false);
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && pendingListView.getSelectionModel().getSelectedItem() != null)
        {
            showGradesOfSelectedExam();
        }
    }

    @FXML
    void showGrades(ActionEvent event) {
        showGradesOfSelectedExam();
    }

    private void showGradesOfSelectedExam() {
        String selectedExam = pendingListView.getSelectionModel().getSelectedItem();
        String currentExamTitle = selectedExam.substring(0,selectedExam.indexOf("-"));
        model.setCurrentConcreteExamTitle(currentExamTitle);
        String currentExamId = model.getPendingExamId(currentExamTitle);
        model.showPendingExamGrades(currentExamId);
        model.clearPendingExams();
    }

    @FXML
    void switchToMainScreen(MouseEvent event) {
        ClientApp.setRoot("MainScreen");
        model.clearPendingExams();
    }
}
