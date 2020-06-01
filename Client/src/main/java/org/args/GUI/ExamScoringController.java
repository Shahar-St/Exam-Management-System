package org.args.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.args.Client.IAddExamData;

import java.net.URL;
import java.util.ResourceBundle;

public class ExamScoringController {

    private IAddExamData model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> questionListView;

    @FXML
    private ListView<String> scoreListView;

    @FXML
    private Label scoreLabel;

    @FXML
    void initialize() {
        setModel(ClientApp.getModel());
        assert questionListView != null;
        assert scoreListView != null;
        assert scoreLabel != null;
        questionListView.setItems(model.getObservableExamQuestionsList());
        model.initQuestionsScoringList();
        scoreListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> var1 = new TextFieldListCell<>(new StringConverter<String>() {

                    @Override
                    public String toString(String object) {
                        return object;
                    }

                    @Override
                    public String fromString(String string) {
                        return string;
                    }


                });
                var1.setOnMouseClicked(e -> {
                    var1.startEdit();
                    e.consume();

                });
                return var1;
            }
        });
        scoreListView.setItems(model.getObservableQuestionsScoringList());
        scoreListView.setEditable(true);


    }


    public void setModel(IAddExamData newModel) {
        if (model == null)
            model = newModel;
    }
}

