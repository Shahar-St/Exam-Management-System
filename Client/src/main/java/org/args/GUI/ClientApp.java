package org.args.GUI;


import DatabaseAccess.Requests.ExecuteExam.SubmitExamRequest;
import DatabaseAccess.Responses.Exams.AddExamResponse;
import DatabaseAccess.Responses.Exams.DeleteExamResponse;
import DatabaseAccess.Responses.Exams.EditExamResponse;
import DatabaseAccess.Responses.Exams.ViewExamResponse;
import DatabaseAccess.Responses.ExecuteExam.*;
import DatabaseAccess.Responses.LoginResponse;
import DatabaseAccess.Responses.Questions.*;
import DatabaseAccess.Responses.ReviewExam.EvaluateExamResponse;
import DatabaseAccess.Responses.ReviewExam.GetExecutedExamResponse;
import DatabaseAccess.Responses.ReviewExam.UncheckedExecutesOfConcreteResponse;
import DatabaseAccess.Responses.Statistics.TeacherStatisticsResponse;
import Notifiers.ConfirmTimeExtensionNotifier;
import Notifiers.ExamEndedNotifier;
import Notifiers.TimeExtensionRequestNotifier;
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
import java.util.Stack;


/**
 * JavaFX App
 */
public class ClientApp extends Application {

    public static Stage primaryStage;
    private static Scene scene;
    private static EMSClient client;
    private static DataModel model;
    // specify the server defaults
    private static String host = "127.0.0.1";
    private static boolean isRunning;
    private static int port = 3000;
    private static Stack<String> lastScenes;
    private final String[] errors = {"SUCCESS", "UNAUTHORIZED", "NOT_FOUND", "NO_ACCESS", "WRONG_INFO"};

    public static boolean isRunning() {
        return isRunning;
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            System.out.println("Failed to change the root of the scene: " + e.toString());

        }
    }

    public static void backToLastScene() {
        try {
            scene.setRoot(loadFXML(popLastScene()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String popLastScene() {
        if (!lastScenes.empty())
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

    public static void main(String[] args) {
        launch();
    }

    public static void sendRequest(Object data) {
        try {
            client.sendToServer(data);
        } catch (IOException e) {
            System.out.println("Failed to send request to server");
            e.printStackTrace();

        }
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        ClientApp.host = host;
        client.setHost(host);
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        ClientApp.port = port;
        client.setPort(port);
    }

    protected String getErrorMessage(int error_code) {
        return errors[error_code];
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
            primaryStage = stage;
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
        isRunning = false;
        EventBus.getDefault().unregister(this);
        super.stop();
    }

    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ...");
        // set false
        isRunning = false;
        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Platform.exit();
    }

    public FXMLLoader fxmlLoader(String fxml) {
        return new FXMLLoader(getClass().getResource("/org/args/" + fxml + ".fxml"));
    }

    public void infoAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public void errorAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public void logOut() {
        client.logOut();
        infoAlert("You have logged out successfully.");
        setRoot("LoginScreen");
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
            errorAlert("Login Failed, Please Try Again.");
        }

    }

    @Subscribe
    public void handleAllQuestionsResponse(AllQuestionsResponse response) {
        if (response.getStatus() != 0)
            errorAlert("Failed To Fetch Question List, Please Try Again. " + getErrorMessage(response.getStatus()));
    }

    @Subscribe
    public void handleEditQuestionResponse(EditQuestionResponse response) {
        if (response.getStatus() == 0) {
            infoAlert("Edit Question Success");
            popLastScene();
            setRoot("QuestionManagementScreen");
        } else {
            errorAlert("Edit Question Failed, Please Try Again." + getErrorMessage(response.getStatus()));
        }

    }

    @Subscribe
    public void handleQuestionResponse(QuestionResponse response) {
        if (response.getStatus() == 0) {
            setRoot("QuestionScreen");
        } else {
            errorAlert("Failed To Fetch The Question, Please Try Again." + getErrorMessage(response.getStatus()));
        }

    }

    @Subscribe
    public void handleAddQuestionResponse(AddQuestionResponse response) {
        Platform.runLater(() -> {
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
    public void handleDeleteQuestionResponse(DeleteQuestionResponse response) {
        Platform.runLater(() -> {
            Alert alert;
            if (response.getStatus() == 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Question Deleted");
                alert.setContentText("Question Deleted Successfully!");
                popLastScene();
                setRoot("QuestionManagementScreen");
            } else if (response.getStatus() == 4)
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Question could not be deleted because it's already a part of an exam!");

            }
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Ooops, Question could not be deleted!");
            }
            alert.showAndWait();
        });

    }


    @Subscribe
    public void handleDeleteExamResponse(DeleteExamResponse response) {
        Platform.runLater(() -> {
            Alert alert;
            if (response.getStatus() == 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exam Deleted");
                alert.setContentText("Exam Deleted Successfully!");
            } else if (response.getStatus() == 4)
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("exam could not be deleted because it was already executed!");
            }
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Ooops, exam could not be deleted!");
            }
            alert.showAndWait();
        });
        setRoot("ExamManagementScreen");
    }

    @Subscribe
    public void handleViewExamResponse(ViewExamResponse response) {
        if (response.getStatus() == 0) {
            setRoot("ViewExamScreen");
        } else {
            infoAlert("Failed to Fetch Exam");
        }
    }

    @Subscribe
    public void handleAddExamResponse(AddExamResponse response) {
        if (response.getStatus() == 0) {
            infoAlert("Add Exam Successfully");
            setRoot("ExamManagementScreen");
        } else {
            infoAlert("Add Exam Failed");
        }

    }

    @Subscribe
    public void handleEditExamResponse(EditExamResponse response) {
        if (response.getStatus() == 0) {
            infoAlert("Exam was successfully edited!");
            setRoot("ExamManagementScreen");
        } else {
            infoAlert("Exam editing failed");
        }
    }

    @Subscribe
    public void handleExamEndedNotifier(ExamEndedNotifier notifier)
    {
        if(model.getPermission().equals("student"))
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Attention!");
                alert.setHeaderText(null);
                alert.setContentText("Attention! \nExam Time Has Ended, Your'e Exam Has Been Submitted And You're Now Being Redirected To The Main Screen");
                alert.showAndWait();
                setRoot("MainScreen"); // redirect client to main screen because of exam timeout.
            });
        else if(model.getPermission().equals("teacher")){
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Attention!");
                alert.setHeaderText(null);
                alert.setContentText("Attention! \nExam Has Ended, All Exams Has Been Submitted, You're Now Being Redirected To The Main Screen");
                alert.showAndWait();
                setRoot("MainScreen");
            });
        }
    }

    @Subscribe
    public void handleExecuteExamResponse(ExecuteExamResponse response) {
        if (response.getStatus() == 0)
        {
            infoAlert("Exam Initiated. Students Can Now Start!");
            setRoot("TeacherExamExecutionScreen");
        }
        else {
            errorAlert("Exam failed to start! (Server Error)");
        }
    }

    @Subscribe
    public void handleStudentTakeExamResponse(TakeExamResponse response) {
        if (response.getStatus() == 0) {
            if (model.isManualExam()) {
                setRoot("StudentManualExamScreen");
            } else {
                setRoot("StudentExamExecutionScreen");
            }

        } else {
            errorAlert("Something went wrong while trying to take exam." + getErrorMessage(response.getStatus()));
        }
    }

    @Subscribe
    public void handleTimeExtensionResponse(TimeExtensionResponse response) {
        if(response.getStatus()==0){
            infoAlert("Time Extension Request Was Successfully Sent.");

        }else{
            errorAlert("Something Went Wrong With Time Extension Request, Please Try Again. ");
        }
    }
    @Subscribe
    public void handleTimeExtensionRequestNotifier(TimeExtensionRequestNotifier notifier)
    {
        infoAlert("New Time Extension Request Received!");
    }

    @Subscribe
    public void handleRaisedHandResponse(RaiseHandResponse response) {
        if(response.getStatus()==0)
        {
            infoAlert("Your've Successfully Raised Your Hand :)");
        }else {
            errorAlert("Failed To Send Raise Hand Request, Please Try Again.");
        }
    }

    @Subscribe
    public void handleSubmitManualExamResponse(SubmitManualExamResponse response) {
        if (response.getStatus() == 0) {
            setRoot("MainScreen");
            infoAlert("Exam Was Successfully Submitted.");
        } else {
            errorAlert("Submission Failed, Please Try Again.");
        }
    }

    @Subscribe
    public void handleSubmitExamResponse(SubmitExamResponse response){
        if(response.getStatus()==0){
            setRoot("MainScreen");
            SubmitExamRequest request = (SubmitExamRequest)response.getRequest();
            if(request.isFinishedOnTime())
                infoAlert("Exam Was Successfully Submitted.");
        }else{
            errorAlert("Submission Failed, Please Try Again.");
        }
    }

    @Subscribe
    public void handleDeanTimeExtensionResponse(ConfirmTimeExtensionResponse response) {
        if (response.getStatus() != 0) {
            errorAlert("Failed to send time extension reply!");
        }
    }

    @Subscribe
    public void handleUncheckedExecutesOfConcreteResponse(UncheckedExecutesOfConcreteResponse response) {
        if (response.getStatus() == 0) {
            setRoot("TeacherExamGradesReviewScreen");
        }
    }

    @Subscribe
    public void handleGetExecutedExamResponse(GetExecutedExamResponse response) {
        if (response.getStatus() == 0) {
            if(model.getPermission().equals("teacher")){
                if (!response.getExam().isComputerized())
                    setRoot("TeacherReviewManualExamScreen");
                else
                    setRoot("TeacherReviewCompExamScreen");
            }else if(model.getPermission().equals("student"))
                setRoot("StudentReviewPastExamScreen");



        } else {
            errorAlert("Failed to fetch exam from the sever");
        }
    }

    @Subscribe
    public void handleEvaluateExamResponse(EvaluateExamResponse response) {
        if (response.getStatus() == 0) {
            setRoot("TeacherExamGradesReviewScreen");
        }else{
            errorAlert("Failed To Submit Exam Evaluation, Please Try Again.");
        }
    }

    @Subscribe
    public void handleConfirmTimeExtensionNotifier(ConfirmTimeExtensionNotifier notifier)
    {
        if(notifier.isAccepted())
        {
            System.out.println("time extension confirmed");
            infoAlert("Time Extension Confirmed!");

        }
        else
        {
            System.out.println("time extension rejected");
            errorAlert("Time Extension Rejected!");
        }
    }

    @Subscribe
    public void handleTeacherStatisticsResponse(TeacherStatisticsResponse response)
    {
        if (response.getStatus() == 0)
            ClientApp.setRoot("TeacherStatisticsScreen");
        else
            errorAlert("Failed to fetch required results!");


    }



}