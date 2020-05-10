package org.args.GUI;

import DatabaseAccess.Responses.AllQuestionsResponse;
import DatabaseAccess.Responses.Pair;
import DatabaseAccess.Responses.QuestionResponse;
import DatabaseAccess.Responses.SubjectsAndCoursesResponse;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.args.Client.EMSClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * JavaFX App
 */
public class ClientApp extends Application {

    private static Scene scene;
    private static EMSClient client;
    // specify the server details
    private final String host = "127.0.0.1";

    private final int port = 1337;

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("/org/args/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void sendRequest(Object data) {
        try {
            client.sendToServer(data);
        } catch (IOException e) {
            System.out.println("Failed to send request to server");
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void fillSubjectsDropdown(SubjectsAndCoursesResponse response) throws IOException {

        FXMLLoader loader = fxmlLoader("QuestionManagementScreen");
        Parent screen = loader.load();
        QuestionManagementScreenController screenController = loader.getController();
        screenController.setClientApp(this);
        screenController.setSubjectsAndCoursesState(response.getSubjectsAndCourses()); //set the hashmap in the controllers state to later fill the courses dropdown list according to selected subject
        for (String subjectName : response.getSubjectsAndCourses().keySet()) //iterate through every subject in the hashmap
        {
            MenuItem subject = new MenuItem(subjectName);
            subject.setOnAction(screenController.displayCoursesFromSubject);
            screenController.addSubjectToSubjectDropdown(subject);
        }
        scene.setRoot(screen);


    }

    public void fillQuestionsList(AllQuestionsResponse response) throws IOException {

        FXMLLoader loader = fxmlLoader("QuestionManagementScreen");
        loader.load();
        QuestionManagementScreenController screenController = loader.getController();
        screenController.setClientApp(this);
        HashMap<Integer, Pair<LocalDateTime, String>> questions = response.getQuestionList();
        ObservableList<String> observableSet = FXCollections.observableArrayList();

        for (Map.Entry<Integer, Pair<LocalDateTime, String>> question : questions.entrySet()) {
            String questionId = Integer.toString(question.getKey());
            String questionDescription = question.getValue().getSecond();
            String menuItemText = "#" + questionId + ": " + questionDescription;
            observableSet.add(menuItemText);
        }

        screenController.addToList(observableSet);
    }

    public static void main(String[] args) {
        launch();
    }

    public FXMLLoader fxmlLoader(String fxml) {
        return new FXMLLoader(getClass().getResource("/org/args/" + fxml + ".fxml"));
    }

    @Override
    public void init() throws Exception {
        try {
            super.init();
            client = new EMSClient(this.host, this.port, this);
        } catch (Exception e) {
            System.out.println("Failed to init app.. exiting");
            e.printStackTrace();
            System.exit(1);
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = fxmlLoader("LoginScreen");
            scene = new Scene(loader.load(), 640, 480);
            LoginScreenController screenController = loader.getController();
            screenController.setClientApp(this);
            scene.getStylesheets().add(getClass().getResource("/org/args/bootstrap3.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Failed to start the app.. exiting");
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void loginSuccess() throws IOException {
        try {
            FXMLLoader loader = fxmlLoader("TeacherMainScreen");
            scene.setRoot(loader.load());
        } catch (IOException e) {
            System.out.println("Failed to switch scene on login success");
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void fillEditQuestionScreen(QuestionResponse response) throws IOException {

        String lastModified = response.getLastModified().toString();

        String author = response.getAuthor();

        String content = response.getQuestionContent();

        String[] answers = response.getAnswers();

        int correctAnswer = response.getCorrectAnswer();

        FXMLLoader loader = fxmlLoader("EditQuestionScreen");

        Parent screen = loader.load();

        EditQuestionScreenController screenController = loader.getController();

        screenController.setClientApp(this);

        screenController.initScreen(lastModified, author, content, answers, correctAnswer);

        scene.setRoot(screen);

    }




}