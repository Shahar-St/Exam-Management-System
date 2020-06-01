package org.args.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.args.Client.ITeacherViewStatsData;

public class TeacherStatisticsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="gradesChart"
    private StackedBarChart<?, ?> gradesChart; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private ImageView backButton; // Value injected by FXMLLoader

    private ITeacherViewStatsData model;

    public void setModel(ITeacherViewStatsData teacherViewStatsDataModel){
        if(model == null){
            model = teacherViewStatsDataModel;
        }

    }

    @FXML
    void backButtonClicked(MouseEvent event) {
      ClientApp.backToLastScene();

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert gradesChart != null;
        assert backButton != null ;
        setModel(ClientApp.getModel());

    }
}