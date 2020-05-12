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
        if (!super.isConnected()) {
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
        System.out.println("Connection Exception : " + exception.toString());
        exception.printStackTrace();
    }

    @Override
    protected void connectionEstablished() {
        super.connectionEstablished();
        System.out.println("Established Connection To Server ");
    }

    @Override
    protected void handleMessageFromServer(Object msg) {

        if (msg instanceof LoginResponse) {
            LoginResponse response = (LoginResponse) msg;
            if (response.getStatus() == 0) {

                loginSuccessful(response);


            } else {
                loginFailed(response);
            }

        } else if (msg instanceof EditQuestionResponse) {
            EditQuestionResponse response = (EditQuestionResponse) msg;
            if (response.getStatus() == 0) {
                // edit successful
                // switch to other scene and

            } else {

            }

        } else if (msg instanceof AllQuestionsResponse) {
            AllQuestionsResponse response = (AllQuestionsResponse) msg;
            if (response.getStatus() == 0) {
                getAllQuestionsSuccessful(response);

            } else {

            }

        } else if (msg instanceof QuestionResponse) {
            QuestionResponse response = (QuestionResponse) msg;
            if (response.getStatus() == 0) {
                viewQuestionSuccessful(response);

            } else {

            }

        } else if (msg instanceof SubjectsAndCoursesResponse) {
            SubjectsAndCoursesResponse response = (SubjectsAndCoursesResponse) msg;
            if (response.getStatus() == 0) {
                getSubjectsAndCoursesSuccess(response);

            } else {

            }

        }


    }


    public void loginSuccessful(LoginResponse response) {
        this.isLoggedIn = true;
        this.permission = response.getPermission();
        LoginRequest request = (LoginRequest) response.getRequest();
        this.userName = request.getUserName();
        this.password = request.getPassword();
        app.loginSuccess(response.getName());


    }

    public void loginFailed(LoginResponse response) {
        // call app function to notify the user
    }


    public void viewQuestionSuccessful(QuestionResponse response) {
        try {
            app.fillEditQuestionScreen(response);
        } catch (IOException e) {
            System.out.println("Exception while handling view question success");
            e.printStackTrace();
        }


    }

    public void viewQuestionFailed(QuestionResponse response) {

    }

    public void getSubjectsAndCoursesSuccess(SubjectsAndCoursesResponse response) {
        try {
            app.fillSubjectsDropdown(response);
        } catch (IOException e) {
            System.out.println("Exception while handling view subjects and courses success");
            e.printStackTrace();
        }


    }

    public void getSubjectsAndCoursesFailed(SubjectsAndCoursesResponse response) {

    }

    public void editQuestionSuccessful(EditQuestionResponse response) {

    }

    public void editQuestionFailed(EditQuestionResponse response) {

    }

    public void getAllQuestionsSuccessful(AllQuestionsResponse response) {
        try {
            app.fillQuestionsList(response);
        } catch (IOException e) {
            System.out.println("Exception while handling get all questions success");
            e.printStackTrace();
        }

    }

    public void getAllQuestionsFailed(AllQuestionsResponse response) {

    }


}
