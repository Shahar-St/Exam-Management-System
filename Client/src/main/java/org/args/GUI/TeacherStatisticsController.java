package org.args.GUI;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.args.Client.ITeacherViewStatsData;

public class TeacherStatisticsController {


    private ITeacherViewStatsData model;

    @FXML
    private TableView<?> gradesTableView;

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

    }

    private void setModel(ITeacherViewStatsData newModel){
        if(model==null)
            model = newModel;
    }
}
