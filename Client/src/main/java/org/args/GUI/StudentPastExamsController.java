package org.args.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.args.Client.IStudentViewStatsData;

public class StudentPastExamsController {

    @FXML
    private ImageView backButton;

    @FXML
    private MenuButton subjectsDropdown;

    @FXML
    private MenuButton coursesDropdown;

    @FXML
    private TableView<StudentPastExam> gradesTable;

    @FXML
    private Button reviewExam;

    @FXML
    private TableColumn<String,Double> titleColumn;

    @FXML
    private TableColumn<String,Double> gradeColumn;

    IStudentViewStatsData model;

    public void setModel(IStudentViewStatsData model) {
        this.model = model;
    }

    @FXML
    public void initialize()
    {
        setModel(ClientApp.getModel());
        model.loadPastExams();
        //idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("examTitle"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));


    }

    @FXML
    void handleMouseEvent(MouseEvent event) {

    }

    @FXML
    void switchToMainScreen(MouseEvent event) {

    }

}
