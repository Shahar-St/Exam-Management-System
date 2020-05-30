/**
 * Sample Skeleton for 'ViewExamScreen.fxml' Controller Class
 */

package org.args.GUI;

import LightEntities.LightQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.args.Client.IExamData;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewExamController {

    private IExamData model;

    void setModel(IExamData dataModel) {
        if (model == null) {
            model = dataModel;
        }
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="pageView"
    private Pagination pageView; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        setModel(ClientApp.getModel());
        List<LightQuestion> questionList = model.getLightQuestionListFromCurrentExam();
        assert pageView != null : "fx:id=\"pageView\" was not injected: check your FXML file 'ViewExamScreen.fxml'.";
        pageView.setPageCount(questionList.size());
        pageView.setCurrentPageIndex(0);
        pageView.setMaxPageIndicatorCount(5);
        pageView.setPageFactory((pageIndex) -> {
            TextField lastModified = new TextField(questionList.get(pageIndex).getLastModified().toString());

            TextField author = new TextField(questionList.get(pageIndex).getAuthor());

            TextArea content = new TextArea(questionList.get(pageIndex).getQuestionContent());

            TextField answer1 = new TextField(questionList.get(pageIndex).getAnswers().get(0));

            TextField answer2 = new TextField(questionList.get(pageIndex).getAnswers().get(1));

            TextField answer3 = new TextField(questionList.get(pageIndex).getAnswers().get(2));

            TextField answer4 = new TextField(questionList.get(pageIndex).getAnswers().get(3));

            return new VBox(lastModified,author,content,answer1,answer2,answer3,answer4);

        });

    }
}
