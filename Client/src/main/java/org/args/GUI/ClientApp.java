package org.args.GUI;


import DatabaseAccess.Responses.*;
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


/**
 * JavaFX App
 */
public class ClientApp extends Application {

    private static Scene scene;
    private static EMSClient client;
    private static DataModel model;
    // specify the server defaults
    private static String host = "127.0.0.1";

    private static int port = 3000;

    private final String[] errors = {"SUCCESS", "UNAUTHORIZED", "NOT_FOUND", "NO_ACCESS", "WRONG_INFO"};

    protected String getErrorMessage(int error_code) {
        return errors[error_code];
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            System.out.println("Failed to change the root of the scene: " + e.toString());

        }
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
        } catch (Exception e) {
            System.out.println("Failed to init app.. exiting");
            e.printStackTrace();

        }
    }

    @Override
    public void start(Stage stage) {
        try {
            EventBus.getDefault().register(this);
            FXMLLoader loader = fxmlLoader("LoginScreen");
            scene = new Scene(loader.load());
            //scene.getStylesheets().add(getClass().getResource("/org/args/bootstrap3.css").toExternalForm());
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
        EventBus.getDefault().unregister(this);
        super.stop();
    }

    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ...");
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


    public void popupAlert(String message) {
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


    public static String getHost() {
        return host;
    }

    public static int getPort() {
        return port;
    }

    @Subscribe
    public void handleLoginResponse(LoginResponse response) {
        if (response.getStatus() == 0) {
            FXMLLoader loader = fxmlLoader("MainScreen");
            Parent screen = null;
            try {
                screen = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            scene.setRoot(screen);
            Platform.runLater(() -> {
                ((Stage) scene.getWindow()).setResizable(true);
                scene.getWindow().setWidth(800);
                scene.getWindow().setHeight(600);
                scene.getWindow().centerOnScreen();
            });
        } else {
            popupAlert("Login Failed, Please Try Again.");
        }

    }

    @Subscribe
    public void handleAllQuestionsResponse(AllQuestionsResponse response) {
        if (response.getStatus() != 0)
            popupAlert("Failed To Fetch Question List, Please Try Again. " + getErrorMessage(response.getStatus()));
    }

    @Subscribe
    public void handleEditQuestionResponse(EditQuestionResponse response) {
        if (response.getStatus() == 0) {
            popupAlert("Edit Question Success");
        } else {
            popupAlert("Edit Question Failed, Please Try Again." + getErrorMessage(response.getStatus()));
        }

    }

    @Subscribe
    public void handleQuestionResponse(QuestionResponse response) {
        if (response.getStatus() == 0) {
            FXMLLoader loader = fxmlLoader("QuestionScreen");
            Parent screen = null;
            try {
                screen = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            QuestionController screenController = loader.getController();
            scene.setRoot(screen);
        } else {
            popupAlert("Failed To Fetch The Question, Please Try Again." + getErrorMessage(response.getStatus()));
        }

    }

    @Subscribe
    public void handleViewExamResponse(ViewExamResponse response){
        if(response.getStatus()==0){
            FXMLLoader loader = fxmlLoader("ViewExamScreen");
            Parent screen = null;
            try {
                screen = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ViewExamController screenController = loader.getController();
            scene.setRoot(screen);
        }
        else{
            popupAlert("Failed to Fetch Exam");
        }
    }


}