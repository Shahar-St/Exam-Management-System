package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.args.Client.IExamReviewData;

public class TeacherExamGradesReviewController {

    @FXML
    private TableView<StudentExamType> gradesTableView;

    @FXML
    private TableColumn<String, String> idColumn;

    @FXML
    private TableColumn<String, String> examTypeColumn;

    @FXML
    private Label titleLabel;

    @FXML
    private Button detailsButton;

    @FXML
    private Button backButton;


    private IExamReviewData model;

    public void setModel(IExamReviewData model) {
        this.model = model;
    }

    @FXML
    void initialize()
    {
        setModel(ClientApp.getModel());
        titleLabel.setText(model.getCurrentConcreteExamTitle());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        examTypeColumn.setCellValueFactory(new PropertyValueFactory<>("examType"));
        gradesTableView.setItems(model.getStudentsGradesToReview());
    }


    @FXML
    void reviewExam(ActionEvent event) {
        StudentExamType studentExam = gradesTableView.getSelectionModel().getSelectedItem();
        if (studentExam != null) {
            model.reviewExam(studentExam.getId());
        }
    }

    @FXML
    void back(ActionEvent event) {
        ClientApp.setRoot("TeacherPendingExamsScreen");
        model.clearStudentsGradesToReview();
    }

}
