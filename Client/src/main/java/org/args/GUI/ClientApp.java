package org.args.GUI;


import DatabaseAccess.Responses.*;
import DatabaseAccess.Responses.Exams.AddExamResponse;
import DatabaseAccess.Responses.Exams.DeleteExamResponse;
import DatabaseAccess.Responses.Exams.EditExamResponse;
import DatabaseAccess.Responses.Exams.ViewExamResponse;
import DatabaseAccess.Responses.ExecuteExam.ExecuteExamResponse;
import DatabaseAccess.Responses.ExecuteExam.RaiseHandResponse;
import DatabaseAccess.Responses.ExecuteExam.TakeExamResponse;
import DatabaseAccess.Responses.ExecuteExam.TimeExtensionResponse;
import DatabaseAccess.Responses.Questions.*;
import DatabaseAccess.Responses.Statistics.TeacherStatisticsResponse;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.args.Client.DataModel;
import org.args.Client.EMSClient;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;


/**
 * JavaFX App
 */
public class ClientApp extends Application {

    private static Scene scene;
    private static EMSClient client;
    private static DataModel model;
    // specify the server defaults
    private static String host = "127.0.0.1";

    private static boolean isRunning;

    private static int port = 3000;

    private final String[] errors = {"SUCCESS", "UNAUTHORIZED", "NOT_FOUND", "NO_ACCESS", "WRONG_INFO"};

    private static Stack<String> lastScenes;

    public static boolean isRunning() {
        return isRunning;
    }

    protected String getErrorMessage(int error_code) {
        return errors[error_code];
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
            System.out.println("Stack State:");
            System.out.println("Stack Size:"+lastScenes.size());
            System.out.println(Arrays.toString(lastScenes.toArray()));
        } catch (IOException e) {
            System.out.println("Failed to change the root of the scene: " + e.toString());

        }
    }

    public static void backToLastScene(){
        try {
            scene.setRoot(loadFXML(popLastScene()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Stack State:");
        System.out.println("Stack Size:"+lastScenes.size());
        System.out.println(Arrays.toString(lastScenes.toArray()));
    }

    public static String popLastScene() {
        if(!lastScenes.empty())
            return lastScenes.pop();
        return null;
    }

    public static void pushLastScene(String fxml) {
        lastScenes.push(fxml);
    }


    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("/org/args/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static DataModel getModel() {
        return model;
    }

    @Override
    public void init() {
        try {
            super.init();
            client = new EMSClient(host, port, this);
            model = new DataModel(this);
            lastScenes = new Stack<>();
        } catch (Exception e) {
            System.out.println("Failed to init app.. exiting");
            e.printStackTrace();

        }
    }

    @Override
    public void start(Stage stage) {
        try {
            isRunning = true;
            EventBus.getDefault().register(this);
            FXMLLoader loader = fxmlLoader("LoginScreen");
            scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/org/args/bootstrap3.css").toExternalForm());
            stage.setScene(scene);
            stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
            stage.setResizable(false);
            stage.setTitle("HSTS");
            stage.show();
        } catch (Exception e) {
            System.out.println("Failed to start the app.. exiting: " + e.toString());


        }

    }

    @Override
    public void stop() throws Exception {
        isRunning=false;
        EventBus.getDefault().unregister(this);
        super.stop();
    }

    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ...");
        // set false
        isRunning=false;
        try {
            client.closeConnection();
        } catch (IOException e) {
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
        try {
            client.sendToServer(data);
        } catch (IOException e) {
            System.out.println("Failed to send request to server");
            e.printStackTrace();

        }
    }


    public void popUpAlert(String message) {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });

    }


    public static void setHost(String host) {
        ClientApp.host = host;
        client.setHost(host);
    }

    public static void setPort(int port) {
        ClientApp.port = port;
        client.setPort(port);
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


    public static String getHost() {
        return host;
    }

    public static int getPort() {
        return port;
    }



    @Subscribe
    public void handleLoginResponse(LoginResponse response) {
        if (response.getStatus() == 0) {
            setRoot("MainScreen");
            Platform.runLater(() -> {
                ((Stage) scene.getWindow()).setResizable(true);
                scene.getWindow().setWidth(800);
                scene.getWindow().setHeight(600);
                scene.getWindow().centerOnScreen();
            });
        } else {
            popUpAlert("Login Failed, Please Try Again.");
        }

    }

    @Subscribe
    public void handleAllQuestionsResponse(AllQuestionsResponse response) {
        if (response.getStatus() != 0)
            popUpAlert("Failed To Fetch Question List, Please Try Again. " + getErrorMessage(response.getStatus()));
    }

    @Subscribe
    public void handleEditQuestionResponse(EditQuestionResponse response) {
        if (response.getStatus() == 0) {
            popUpAlert("Edit Question Success");
            popLastScene();
            setRoot("QuestionManagementScreen");
        } else {
            popUpAlert("Edit Question Failed, Please Try Again." + getErrorMessage(response.getStatus()));
        }

    }

    @Subscribe
    public void handleQuestionResponse(QuestionResponse response) {
        if (response.getStatus() == 0) {
            setRoot("QuestionScreen");
        } else {
            popUpAlert("Failed To Fetch The Question, Please Try Again." + getErrorMessage(response.getStatus()));
        }

    }

    @Subscribe
    public void handleAddQuestionResponse(AddQuestionResponse response){
        Platform.runLater(()->{
            Alert alert;
            if (response.getStatus() == 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Question Added");
                alert.setContentText("Question Added Successfully!");
                popLastScene();
                setRoot("QuestionManagementScreen");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Ooops, Question could not be added!");
            }
            alert.showAndWait();
        });
    }

    @Subscribe
    public void handleDeleteQuestionResponse(DeleteQuestionResponse response){
        Platform.runLater(()->{
            Alert alert;
            if (response.getStatus() == 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Question Deleted");
                alert.setContentText("Question Deleted Successfully!");
                popLastScene();
                setRoot("QuestionManagementScreen");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Ooops, Question could not be deleted!");
            }
            alert.showAndWait();
        });

    }


    @Subscribe
    public void handleDeleteExamResponse(DeleteExamResponse response) {
        Platform.runLater(()->{
            Alert alert;
            if (response.getStatus() == 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exam Deleted");
                alert.setContentText("Exam Deleted Successfully!");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Ooops, exam could not be deleted!");
            }
            alert.showAndWait();
        });
        setRoot("ExamManagementScreen");
    }

    @Subscribe
    public void handleViewExamResponse(ViewExamResponse response){
        if(response.getStatus()==0){
            setRoot("ViewExamScreen");
        }
        else{
            popUpAlert("Failed to Fetch Exam");
        }
    }

    @Subscribe
    public void handleAddExamResponse(AddExamResponse response){
        if(response.getStatus() == 0){
            popUpAlert("Add Exam Successfully");
            setRoot("ExamManagementScreen");
        }else{
            popUpAlert("Add Exam Failed");
        }

    }

    @Subscribe
    public void handleEditExamResponse(EditExamResponse response){
        if(response.getStatus() == 0){
            popUpAlert("Exam was successfully edited!");
            setRoot("ExamManagementScreen");
        }else{
            popUpAlert("Exam editing failed");
        }
    }

    @Subscribe
    public void handleTeacherStatisticsResponse(TeacherStatisticsResponse response){
        setRoot("TeacherStatisticsScreen");
    }
    @Subscribe
    public void handleExecuteExamResponse(ExecuteExamResponse response)
    {
        if (response.getStatus() != 0)
            popUpAlert("Exam execution failed!");
        else {
            popUpAlert("Exam is being initiated...");
            setRoot("TeacherExamExecutionScreen");
        }
    }

    @Subscribe
    public void handleStudentTakeExamResponse(TakeExamResponse response){
        if(response.getStatus() == 0){
            setRoot("StudentExamExecutionScreen");
        }else{
            popUpAlert("Something went wrong while trying to take exam."+getErrorMessage(response.getStatus()));
        }
    }

    @Subscribe
    public void handleTimeExtensionResponse(TimeExtensionResponse response)
    {
        if(response.getStatus() != 0)
            popUpAlert("Network Error: Failed to fetch Dean's response!");
        else
        {
            if(!response.isAccepted())
                popUpAlert("Time extension request was denied by the dean. \nReason: " + response.getDeanResponse());
            else
                popUpAlert("Time extension request was accepted by the dean. \nApproved added time: " + response.getAuthorizedTimeExtension());
        }
    }

    @Subscribe
    public void handleRaisedHandResponse(RaiseHandResponse response)
    {
        popUpAlert(response.getStudentName());
    }

}