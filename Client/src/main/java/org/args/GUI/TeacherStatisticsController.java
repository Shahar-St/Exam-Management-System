package org.args.GUI;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.args.Client.ITeacherViewStatsData;

import java.util.Map;

public class TeacherStatisticsController {


    private ITeacherViewStatsData model;

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

    }

    @FXML
    void initialize() {
        setModel(ClientApp.getModel());
        assert gradesTableView != null ;
        assert okButton != null ;
        ObservableList<StudentGrade> studentsGrades = FXCollections.observableArrayList();
        for(Map.Entry<String,Double> e: model.getCurrentExamForStats().entrySet()){
            studentsGrades.add(new StudentGrade(e.getKey(),e.getValue()));

        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradesTableView.setItems(studentsGrades);




    }

    private void setModel(ITeacherViewStatsData newModel){
        if(model==null)
            model = newModel;
    }
}
