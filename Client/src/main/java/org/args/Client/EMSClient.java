package org.args.Client;

import DatabaseAccess.Requests.LoginRequest;
import DatabaseAccess.Responses.*;
import org.args.GUI.ClientApp;

import java.io.IOException;
import java.util.logging.Logger;

public class EMSClient extends AbstractClient {
    private static final Logger LOGGER =
            Logger.getLogger(EMSClient.class.getName());

    private boolean isLoggedIn = false;

    private boolean isRunning = false;

    private String userName;

    private String password;

    private String permission;

    private ClientApp app;

    public EMSClient(String host, int port, ClientApp clientApp) throws IOException {
        super(host, port);
        this.app = clientApp;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPermission() {
        return permission;
    }

    public ClientApp getApp() {
        return app;
    }

    @Override
    public void sendToServer(Object msg) throws IOException {
        // check if the client is not connected to the server then connect
        // good for initial connection and for disconnections
        if(!super.isConnected()){
            super.openConnection();
        }
        super.sendToServer(msg);
        System.out.println("Message Has Been Sent To The Server");
        System.out.println(msg.toString());
    }

    @Override
    protected void connectionClosed() {
        super.connectionClosed();
        System.out.println("Disconnected From Server");
    }

    @Override
    protected void connectionException(Exception exception) {
        super.connectionException(exception);
        System.out.println("Connection Exception : "+exception.toString());
    }

    @Override
    protected void connectionEstablished() {
        super.connectionEstablished();
        System.out.println("Established Connection To Server ");
        this.isRunning = true;
    }

    @Override
    protected void handleMessageFromServer(Object msg) throws IOException {

        if (msg instanceof LoginResponse) {
            LoginResponse response = (LoginResponse) msg;
            if (response.getStatus()) {
                try {
                    loginSuccessful(response);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }


            } else {
                loginFailed(response);
            }

        } else if (msg instanceof EditQuestionResponse) {
            EditQuestionResponse response = (EditQuestionResponse) msg;
            if (response.getStatus()) {
                // edit successful
                // switch to other scene and

            } else {

            }

        } else if (msg instanceof AllQuestionsResponse) {
            AllQuestionsResponse response = (AllQuestionsResponse) msg;
            if (response.getStatus()) {
                app.fillQuestionsList(response);


            } else {

            }

        } else if (msg instanceof QuestionResponse) {
            QuestionResponse response = (QuestionResponse) msg;
            if (response.getStatus()) {
                viewQuestionSuccessful(response);

            } else {

            }

        } else if (msg instanceof SubjectsAndCoursesResponse) {
            SubjectsAndCoursesResponse response = (SubjectsAndCoursesResponse) msg;
            if (response.getStatus()) {
                getSubjectsAndCoursesSuccess(response);

            } else {

            }

        }


    }


    public void loginSuccessful(LoginResponse response) throws IOException {
        try {
            this.isLoggedIn = true;
            this.permission = response.getPermission();
            LoginRequest request = (LoginRequest) response.getRequest();
            this.userName = request.getUserName();
            this.password = request.getPassword();
            app.loginSuccess();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }

    public void loginFailed(LoginResponse response) {
        // call app function to notify the user
    }


    public void viewQuestionSuccessful(QuestionResponse response) {
        try{
            app.fillEditQuestionScreen(response);
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public void viewQuestionFailed(QuestionResponse response){

    }

    public void getSubjectsAndCoursesSuccess(SubjectsAndCoursesResponse response){
        try {
            app.fillSubjectsDropdown(response);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void getSubjectsAndCoursesFailed(SubjectsAndCoursesResponse response){

    }

    public void editQuestionSuccessful(EditQuestionResponse response){

    }

    public void editQuestionFailed(EditQuestionResponse response){

    }

    public void getAllQuestionsSuccessful(AllQuestionsResponse response){

    }

    public void getAllQuestionsFailed(AllQuestionsResponse response){

    }


}
