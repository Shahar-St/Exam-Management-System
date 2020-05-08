package org.args.GUI;

import DatabaseAccess.Responses.AllQuestionsResponse;
import DatabaseAccess.Responses.Pair;
import DatabaseAccess.Responses.QuestionResponse;
import DatabaseAccess.Responses.SubjectsAndCoursesResponse;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.args.Client.EMSClient;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
            scene = new Scene(loadFXML("LoginScreen"), 640, 480);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Failed to start the app.. exiting");
            e.printStackTrace();
            System.exit(1);
        }

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

    public static void loginSuccess() throws IOException {
        try {
            setRoot("TeacherMainScreen");
        } catch (IOException e) {
            System.out.println("Failed to switch scene on login success");
            e.printStackTrace();
            System.exit(1);
        }

    }

    public static void fillEditQuestionScreen(QuestionResponse response) {

        String lastModified = response.getLastModified();

        String author = response.getAuthor();

        String content = response.getQuestionContent();

        String[] answers = response.getAnswers();

        int correctAnswer = response.getCorrectAnswer();

        EditQuestionScreenController.initScreen(lastModified, author, content, answers, correctAnswer);
    }

    public static void fillSubjectsDropdown(SubjectsAndCoursesResponse response) {

        QuestionManagementScreenController.setSubjectsAndCoursesState(response.getSubjectsAndCourses()); //set the hashmap in the controllers state to later fill the courses dropdown list according to selected subject
        for (String subjectName : response.getSubjectsAndCourses().keySet()) //iterate through every subject in the hashmap
        {
            MenuItem subject = new MenuItem(subjectName);
            subject.setOnAction(QuestionManagementScreenController.displayCoursesFromSubject);
            QuestionManagementScreenController.addSubjectToSubjectDropdown(subject);
        }


    }

    public static void fillQuestionsList(AllQuestionsResponse response) {

        HashMap<Integer, Pair<LocalDateTime, String>> questions = response.getQuestionList(); //Response: hashmap: key = question id, value = pair{date modified, description}

        for (Map.Entry<Integer, Pair<LocalDateTime, String>> question : questions.entrySet())
        {
            String questionId = Integer.toString(question.getKey());
            String questionDescription = question.getValue().getSecond();
            String menuItemText = "#" + questionId + ": " + questionDescription;

            QuestionManagementScreenController.addQuestionToQuestionsList(menuItemText);
        }
    }

    public static void main(String[] args) {
        launch();
    }






}