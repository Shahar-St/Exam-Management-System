package org.args.Client;

import DatabaseAccess.Requests.EditQuestionRequest;
import DatabaseAccess.Requests.LoginRequest;
import DatabaseAccess.Responses.*;
import org.args.GUI.ClientApp;

import java.io.IOException;
import java.util.logging.Logger;

public class EMSClient extends AbstractClient {
    private static final Logger LOGGER =
            Logger.getLogger(EMSClient.class.getName());

    private boolean isLoggedIn = false;

    // error codes
    private final int SUCCESS = 0;
    private final int UNAUTHORIZED = 1;
    private final int NOT_FOUND = 2;
    private final int NO_ACCESS = 3;
    private final int WRONG_INFO = 4;

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
            if (response.getStatus() == SUCCESS) {

                loginSuccess(response);


            } else {
                loginFailed(response);
            }

        } else if (msg instanceof EditQuestionResponse) {
            EditQuestionResponse response = (EditQuestionResponse) msg;
            if (response.getStatus() == SUCCESS) {
                editQuestionSuccess(response);

            } else {
                editQuestionFailed(response);
            }

        } else if (msg instanceof AllQuestionsResponse) {
            AllQuestionsResponse response = (AllQuestionsResponse) msg;
            if (response.getStatus() == SUCCESS) {
                getAllQuestionsSuccess(response);

            } else {
                getAllQuestionsFailed(response);
            }

        } else if (msg instanceof QuestionResponse) {
            QuestionResponse response = (QuestionResponse) msg;
            if (response.getStatus() == SUCCESS) {
                viewQuestionSuccess(response);

            } else {
                viewQuestionFailed(response);
            }

        } else if (msg instanceof SubjectsAndCoursesResponse) {
            SubjectsAndCoursesResponse response = (SubjectsAndCoursesResponse) msg;
            if (response.getStatus() == SUCCESS) {
                getSubjectsAndCoursesSuccess(response);

            } else {
                getSubjectsAndCoursesFailed(response);
            }

        }


    }


    public void loginSuccess(LoginResponse response) {
        this.isLoggedIn = true;
        this.permission = response.getPermission();
        LoginRequest request = (LoginRequest) response.getRequest();
        this.userName = request.getUserName();
        this.password = request.getPassword();
        app.loginSuccess(response.getName());


    }

    public void loginFailed(LoginResponse response) {
        app.popupAlert("Login Failed, Please Try Again. "+response.getStatus());
    }


    public void viewQuestionSuccess(QuestionResponse response) {
        app.fillEditQuestionScreen(response);


    }

    public void viewQuestionFailed(QuestionResponse response) {
        app.popupAlert("Failed To Fetch The Question, Please Try Again."+response.getStatus());

    }

    public void getSubjectsAndCoursesSuccess(SubjectsAndCoursesResponse response) {
        app.fillSubjectsDropdown(response);


    }

    public void getSubjectsAndCoursesFailed(SubjectsAndCoursesResponse response) {
        app.popupAlert("Failed To Fetch The Subjects And Courses, Please Try Again."+response.getStatus());

    }

    public void editQuestionSuccess(EditQuestionResponse response) {
        app.popupAlert("Edit Question Success");
        app.updateEditedQuestionOnQuestionManagementScreen(((EditQuestionRequest)response.getRequest()).getNewDescription());

    }

    public void editQuestionFailed(EditQuestionResponse response) {
        app.popupAlert("Edit Question Failed, Please Try Again."+response.getStatus());

    }

    public void getAllQuestionsSuccess(AllQuestionsResponse response) {
        app.fillQuestionsList(response);

    }

    public void getAllQuestionsFailed(AllQuestionsResponse response) {
        app.popupAlert("Failed To Fetch Question List, Please Try Again. "+response.getStatus());

    }


}
