package org.args.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.args.Client.IExamReviewData;

public class TeacherExamGradesReviewController {

    @FXML
    private TableView<StudentGrade> gradesTableView;

    @FXML
    private TableColumn<String, Double> idColumn;

    @FXML
    private TableColumn<String, Double> gradeColumn;

    @FXML
    private Button acceptButton;

    @FXML
    private Button changeButton;

    @FXML
    private Button detailsButton;

    /*
    * todo:
    *  handle response to populate table
    *  accept - popup and remove
    *  change -
    * */


    private IExamReviewData model;

    public void setModel(IExamReviewData model) {
        this.model = model;
    }

    void initialize()
    {
        setModel(ClientApp.getModel());
    }

    @FXML
    void acceptGrade(ActionEvent event) {

    }

    @FXML
    void changeGrade(ActionEvent event) {

    }

    @FXML
    void showGradeDetails(ActionEvent event) {

    }

}
