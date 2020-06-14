package org.args.GUI;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.args.Client.ITeacherViewStatsData;

public class TeacherStatisticsController {


    private ITeacherViewStatsData model;

    @FXML
    private Label examTitleLabel;

    @FXML
    private TableView<StudentGrade> gradesTableView;

    @FXML
    private TableColumn<String, Double> idColumn;

    @FXML
    private TableColumn<String, Double> gradeColumn;

    @FXML
    private Button okButton;

    @FXML
    void okButtonOnAction(ActionEvent event) {
        //TODO: back?
        clearScreen();
        ClientApp.setRoot("ResultsScreen");
    }

    private void clearScreen() {
        model.clearStudentGradesList();
    }

    @FXML
    void initialize() {
        setModel(ClientApp.getModel());
        examTitleLabel.textProperty().bind(model.currentExamTitleProperty());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradesTableView.setItems(model.getStudentGradesObservableList());
    }

    private void setModel(ITeacherViewStatsData newModel){
        if(model==null)
            model = newModel;
    }
}
