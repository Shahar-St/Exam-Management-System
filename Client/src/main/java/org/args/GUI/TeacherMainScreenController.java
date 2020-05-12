/**
 * Sample Skeleton for 'TeacherMainScreen.fxml' Controller Class
 */

package org.args.GUI;

import DatabaseAccess.Requests.SubjectsAndCoursesRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class TeacherMainScreenController {

    private static String fullName = null;

    @FXML // fx:id="questionMangementButton"
    private Button questionMangementButton; // Value injected by FXMLLoader

    @FXML
    void switchToQuestionManagement(ActionEvent event) throws IOException {
        ClientApp.sendRequest(new SubjectsAndCoursesRequest());
    }


}
