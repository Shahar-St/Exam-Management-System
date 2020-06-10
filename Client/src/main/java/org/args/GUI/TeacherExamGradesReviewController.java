package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button acceptButton;

    @FXML
    private Button changeButton;

    @FXML
    private Button detailsButton;

    private IExamReviewData model;

    public void setModel(IExamReviewData model) {
        this.model = model;
    }

    @FXML
    void initialize()
    {
        setModel(ClientApp.getModel());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        examTypeColumn.setCellValueFactory(new PropertyValueFactory<>("examType"));
        gradesTableView.setItems(model.getStudentsGradesToReview());
    }


    @FXML
    void reviewExam(ActionEvent event) {
        StudentExamType studentExam = gradesTableView.getSelectionModel().getSelectedItem();
        if (studentExam != null) {
            if (studentExam.getExamType().equals("Computerized"))
                model.reviewComputerizedExam(studentExam.getId());
            else
                model.reviewManualExam(studentExam.getId());
        }
    }

}
