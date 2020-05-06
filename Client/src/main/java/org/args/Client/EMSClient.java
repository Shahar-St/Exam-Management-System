package org.args.Client;

import DatabaseAccess.Requests.*;
import DatabaseAccess.Responses.*;
import org.args.GUI.ClientApp;

import java.io.IOException;
import java.util.logging.Logger;

public class EMSClient extends AbstractClient {
	private static final Logger LOGGER =
			Logger.getLogger(EMSClient.class.getName());

	private  boolean isLoggedIn = false;

	private boolean isRunning = false;

	private  String userName;

	private  String password;

	private String permission;

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

	public EMSClient(String host, int port) throws IOException {
		super(host, port);
		this.openConnection();
	}


	@Override
	public void sendToServer(Object msg) throws IOException {
		super.sendToServer(msg);
	}

	@Override
	protected void connectionClosed() {
		super.connectionClosed();
	}

	@Override
	protected void connectionException(Exception exception) {
		super.connectionException(exception);
	}

	@Override
	protected void connectionEstablished() {
		super.connectionEstablished();
		this.isRunning = true;
	}

	@Override
	protected void handleMessageFromServer(Object msg) {

		if(msg instanceof LoginResponse){
			LoginResponse response = (LoginResponse)msg;
			if(response.isStatus()){
				// login success
				this.isLoggedIn = true;
				this.permission = response.getPermission();
				LoginRequest request = (LoginRequest) response.getRequest();
				this.userName = request.getUserName();
				this.password = request.getPassword();
				// do some stuff with the gui
			}else{
				//login failed
				//notify the user the login was not successful
			}

		}else if(msg instanceof EditQuestionResponse){
			EditQuestionResponse response = (EditQuestionResponse)msg;
			if(response.isStatus()){
				// edit successful
				// switch to other scene and

			}else{

			}

		}else if(msg instanceof AllQuestionsResponse){
			AllQuestionsResponse response = (AllQuestionsResponse)msg;
			if(response.isStatus()){

			}else{

			}

		}else if(msg instanceof QuestionResponse){
			QuestionResponse response = (QuestionResponse)msg;
			if(response.isStatus()){

			}else{

			}

		}else if(msg instanceof SubjectsAndQuestionResponse){
			SubjectsAndQuestionResponse response = (SubjectsAndQuestionResponse)msg;
			if(response.isStatus()){

			}else{

			}

		}


	}

	public void clientLogin(String userName,String password) throws IOException {
		LoginRequest request = new LoginRequest(userName,password);
		this.sendToServer(request);

	}

	public void clientEditQuestion(int questionID, String newDescription, String[] newAnswers, int correctAnswer) throws IOException {
		EditQuestionRequest request = new EditQuestionRequest(questionID,newDescription,newAnswers,correctAnswer);
		this.sendToServer(request);

	}

	public void clientViewQuestion(int questionID) throws IOException {
		QuestionRequest request = new QuestionRequest(questionID);
		this.sendToServer(request);
	}

	public void clientViewSubjectsAndCourses() throws IOException {
		SubjectsAndCoursesRequest request = new SubjectsAndCoursesRequest();
		this.sendToServer(request);
	}

	public void clientViewAllQuestions(String course) throws IOException {
		AllQuestionsRequest request = new AllQuestionsRequest(course);
		this.sendToServer(request);

	}




}
