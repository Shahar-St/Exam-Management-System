package org.args.GUI;

import DatabaseAccess.Responses.SubjectsAndQuestionResponse;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import org.args.Client.EMSClient;

import java.io.IOException;

/**
 * JavaFX App
 */
public class ClientApp extends Application {

    private static Scene scene;
    private static EMSClient client;

    private final String host = "127.0.0.1";

    private final int port = 1337;


    @Override
    public void init() throws Exception {
        try{
            super.init();
            client = new EMSClient(this.host,this.port,this);
        }catch (Exception e){
            System.out.println("Failed to init app.. exiting");
            e.printStackTrace();
            System.exit(1);
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            scene = new Scene(loadFXML("primary"), 640, 480);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println("Failed to start the app.. exiting");
            e.printStackTrace();
            System.exit(1);
        }

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource( "/org/args/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public  void loginSuccess() throws IOException {
        try{
            setRoot("secondary");
        }catch (IOException e){
            System.out.println("Failed to switch scene on login success");
            e.printStackTrace();
            System.exit(1);
        }

    }

    public static void sendRequest(Object data){
        try{
            client.sendToServer(data);
        }catch (IOException e){
            System.out.println("Failed to send request to server");
            e.printStackTrace();
            System.exit(1);
        }

    }



    public static void main(String[] args) {
        launch();
    }



}