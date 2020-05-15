package org.args.GUI;

import DatabaseAccess.Requests.QuestionRequest;
import DatabaseAccess.Responses.AllQuestionsResponse;
import DatabaseAccess.Responses.Pair;
import DatabaseAccess.Responses.QuestionResponse;
import DatabaseAccess.Responses.SubjectsAndCoursesResponse;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.args.Client.EMSClient;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JavaFX App
 */
public class ClientApp extends Application {

    private static Scene scene;
    private static EMSClient client;
    private static String fullName;
    // specify the server details

    // change ip to be auto generated
    //private final String host = "127.0.0.1";

    private final int port = 3000;

    static void setRoot(String fxml) {
        try
        {
            scene.setRoot(loadFXML(fxml));
            // lambda
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    scene.getWindow().setWidth(((Pane) scene.getRoot()).getPrefWidth());
                    scene.getWindow().setHeight(((Pane) scene.getRoot()).getPrefHeight());
                }
            });
        }
        catch (IOException e)
        {
            System.out.println("Failed to change the root of the scene");
            e.printStackTrace();
        }
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("/org/args/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void init() {
        try
        {
            super.init();
            client = new EMSClient(InetAddress.getLocalHost().getHostAddress(), this.port, this);
        }
        catch (Exception e)
        {
            System.out.println("Failed to init app.. exiting");
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) {
        try
        {
            FXMLLoader loader = fxmlLoader("LoginScreen");
            scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/org/args/bootstrap3.css").toExternalForm());
            stage.setTitle("HSTS");
            stage.setScene(scene);
            stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
            stage.show();
        }
        catch (Exception e)
        {
            System.out.println("Failed to start the app.. exiting");
            e.printStackTrace();

        }

    }

    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ...");
        try
        {
            client.closeConnection();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }

    public FXMLLoader fxmlLoader(String fxml) {
        return new FXMLLoader(getClass().getResource("/org/args/" + fxml + ".fxml"));
    }

    public static void sendRequest(Object data) {
        try
        {
            client.sendToServer(data);
        }
        catch (IOException e)
        {
            System.out.println("Failed to send request to server");
            e.printStackTrace();

        }

    }

    public void fillSubjectsDropdown(SubjectsAndCoursesResponse response) {

        FXMLLoader loader = fxmlLoader("QuestionManagementScreen");
        Parent screen = null;
        try
        {
            screen = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        QuestionManagementScreenController screenController = loader.getController();
        screenController.setSubjectsAndCoursesState(response.getSubjectsAndCourses()); //set the hashmap in the controllers state to later fill the courses dropdown list according to selected subject
        for (String subjectName : response.getSubjectsAndCourses().keySet()) //iterate through every subject in the hashmap
        {
            MenuItem subject = new MenuItem(subjectName);
            subject.setOnAction(screenController.displayCoursesFromSubject);
            screenController.addSubjectToSubjectDropdown(subject);
        }
        scene.setRoot(screen);
        //resizeWindow();
    }

    public void fillQuestionsList(AllQuestionsResponse response) {

        FXMLLoader loader = fxmlLoader("QuestionManagementScreen");
        try
        {
            loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        QuestionManagementScreenController screenController = loader.getController();
        HashMap<String, Pair<LocalDateTime, String>> questions = response.getQuestionList();
        ObservableList<String> observableSet = FXCollections.observableArrayList();

        for (Map.Entry<String, Pair<LocalDateTime, String>> question : questions.entrySet())
        {
            String questionId = question.getKey();
            String questionDescription = question.getValue().getSecond();
            String menuItemText = "#" + questionId + ": " + questionDescription;
            observableSet.add(menuItemText);
        }
        screenController.addToList(observableSet);
    }

    public void loginSuccess(String name) {
        try
        {
            fullName = name;
            FXMLLoader loader = fxmlLoader("TeacherMainScreen");
            Parent screen = loader.load();
            scene.setRoot(screen);

            resizeWindow();
        }
        catch (IOException e)
        {
            System.out.println("Failed to switch scene on login success");
            e.printStackTrace();
        }
    }

    public void fillEditQuestionScreen(QuestionResponse response) {

        String questionId = ((QuestionRequest) response.getRequest()).getQuestionID();
        String lastModified = response.getLastModified().toString();
        String author = response.getAuthor();
        String content = response.getQuestionContent();
        List<String> answers = response.getAnswers();
        int correctAnswer = response.getCorrectAnswer();
        FXMLLoader loader = fxmlLoader("EditQuestionScreen");
        Parent screen = null;
        try
        {
            screen = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        EditQuestionScreenController screenController = loader.getController();
        screenController.initScreen(questionId, lastModified, author, content, answers, correctAnswer);
        scene.setRoot(screen);
        //resizeWindow();
    }

    public void updateEditedQuestionOnQuestionManagementScreen(String newContent) {
        FXMLLoader loader = fxmlLoader("QuestionManagementScreen");
        try
        {
            loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        QuestionManagementScreenController screenController = loader.getController();
        screenController.changeQuestionContent(newContent);
    }

    public void popupAlert(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try
                {
                    FXMLLoader loader = fxmlLoader("AlertPopUp");
                    Scene scene = new Scene(loader.load());
                    scene.getStylesheets().add(getClass().getResource("/org/args/bootstrap3.css").toExternalForm());
                    AlertPopUpController popUpController = loader.getController();
                    popUpController.setShowText(message);
                    Stage popup = new Stage();
                    popup.setScene(scene);
                    popup.show();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }

    public void resizeWindow() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                scene.getWindow().setWidth(685);
//                scene.getWindow().setHeight(519);
            }
        });
    }

    public static String getFullName() {
        return fullName;
    }
}