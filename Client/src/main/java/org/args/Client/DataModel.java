package org.args.Client;

import DatabaseAccess.Requests.*;
import DatabaseAccess.Requests.Exams.AllExamsRequest;
import DatabaseAccess.Requests.Exams.DeleteExamRequest;
import DatabaseAccess.Requests.Exams.ViewExamRequest;
import DatabaseAccess.Requests.Questions.AllQuestionsRequest;
import DatabaseAccess.Requests.Questions.EditQuestionRequest;
import DatabaseAccess.Requests.Questions.QuestionRequest;
import DatabaseAccess.Responses.*;
import DatabaseAccess.Responses.Exams.AllExamsResponse;
import DatabaseAccess.Responses.Exams.ViewExamResponse;
import DatabaseAccess.Responses.Questions.AllQuestionsResponse;
import DatabaseAccess.Responses.Questions.QuestionResponse;
import DatabaseAccess.Responses.SubjectsAndCoursesResponse;
import LightEntities.LightExam;
import LightEntities.LightQuestion;
import Util.Pair;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.args.GUI.ClientApp;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.LocalDateTime;
import java.util.*;

public class DataModel implements IMainScreenData, IQuestionManagementData, IQuestionData, IStudentExamExecutionData,
        IDeanViewStatsData, IStudentViewStatsData, IExamData, IAddExamData, ITeacherExamExecutionData, IDeanExamExecutionData,
        ITeacherViewStatsData, IExamManagementData, IExamReviewData {

    private ClientApp app;

    public DataModel(ClientApp clientApp) {
        app = clientApp;
        EventBus.getDefault().register(this);
    }


    //teacher main screen data
    @Subscribe
    public void handleLoginResponse(LoginResponse response) {
        if (response.getStatus() == 0) {
            setName(response.getName());
            permission = response.getPermission();
        }

    }

    private String name;

    private String permission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void login(String userName, String password) {
        ClientApp.sendRequest(new LoginRequest(userName, password));
    }

    @Override
    public void loadSubjects() {
        ClientApp.sendRequest(new SubjectsAndCoursesRequest());
    }

    public String getPermission() {
        return permission;
    }

    //question management screen data - subjects and courses dropdowns

    @Subscribe
    public void handleSubjectsAndCoursesResponse(SubjectsAndCoursesResponse response) {
        if (response.getStatus() == 0)
            setSubjectsAndCourses(response.getSubjectsAndCourses());
    }

    private HashMap<String, List<String>> subjectsAndCourses;

    private String currentSubject;

    private String currentCourse;

    private BooleanProperty courseSelected = new SimpleBooleanProperty(false);

    public void setSubjectsAndCourses(HashMap<String, List<String>> mapFromResponse) {
        subjectsAndCourses = mapFromResponse;
    }

    public void prepareAddQuestion() {
        setCreating(true);
    }

    public BooleanProperty isCourseSelected() {
        return courseSelected;
    }

    public void setCourseSelected(boolean courseSelected) {
        this.courseSelected.set(courseSelected);
    }

    public List<String> getCoursesOfSubject(String subject) {
        return subjectsAndCourses.get(subject);
    }

    public String getCurrentSubject() {
        return currentSubject;
    }

    public void setCurrentSubject(String currentSubject) {
        this.currentSubject = currentSubject;
    }

    public String getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(String currentCourse) {
        this.currentCourse = currentCourse;
        courseSelected.setValue(true);
    }

    public boolean dataWasAlreadyInitialized() {
        return currentSubject != null && !subjectsAndCourses.isEmpty();
    }

    //question management screen data - questions list data

    @Subscribe
    public void handleAllQuestionsResponse(AllQuestionsResponse response) {
        if (response.getStatus() == 0) {
            if (observableQuestionsList.size() > 0) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        observableQuestionsList.clear();
                    }
                });

            }
            generateQuestionDescriptors(response.getQuestionList());
        }
    }

    public void fillQuestionsList(String courseName) {
        ClientApp.sendRequest(new AllQuestionsRequest(courseName));
    }

    public void saveQuestionDetails(String questionId) {
        ClientApp.sendRequest(new QuestionRequest(questionId));
    }

    private int selectedIndex;

    private final ObservableList<String> observableQuestionsList = FXCollections.observableArrayList();

    private void addToObservableQuestionsList(String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                observableQuestionsList.add(text);
            }
        });
    }

    public void generateQuestionDescriptors(HashMap<String, Pair<LocalDateTime, String>> questionList) {
        for (Map.Entry<String, Pair<LocalDateTime, String>> question : questionList.entrySet()) {
            String questionId = question.getKey();
            String questionDescription = question.getValue().getSecond();
            String menuItemText = "#" + questionId + ": " + questionDescription;
            addToObservableQuestionsList(menuItemText); //add to observable list upon generation
        }

    }



    public ObservableList<String> getObservableQuestionsList() {
        return observableQuestionsList;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }


    public void clearQuestionsList() {
        observableQuestionsList.clear();
    }

    //question screen section
    private String questionId;
    private String lastModified;
    private String author;
    private String content;
    private List<String> answers;
    private int correctAnswer;
    private boolean isCreating;

    @Override
    public boolean isCreating() {
        return isCreating;
    }

    public void setCreating(boolean creating) {
        isCreating = creating;
    }

    @Override
    public void alert(String message) {
        app.popUpAlert(message);
    }

    private ObservableList<String> choiceItems;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ObservableList<String> getChoiceItems() {
        return choiceItems;
    }

    public void setChoiceItems(ObservableList<String> choiceItems) {
        this.choiceItems = choiceItems;
    }

    public void saveQuestion(String questionId, String answer_1, String answer_2, String answer_3, String answer_4, String newContent) {


        EditQuestionRequest request = new EditQuestionRequest(questionId,newContent, Arrays.asList(answer_1, answer_2, answer_3,answer_4), correctAnswer);
        ClientApp.sendRequest(request);
    }


    @Subscribe
    public void handleQuestionResponse(QuestionResponse response) {
        if (response.getStatus() == 0) {
            setQuestionId(((QuestionRequest) response.getRequest()).getQuestionID());
            setLastModified(response.getLastModified().toString());
            setAuthor(response.getAuthor());
            setContent(response.getQuestionContent());
            setAnswers(response.getAnswers());
            setCorrectAnswer(response.getCorrectAnswer());
        }

    }

    //Add Exam data

    private final ObservableList<String> observableExamQuestionsList = FXCollections.observableArrayList();

    @Override
    public void addToExamQuestionsList(String question) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                observableExamQuestionsList.add(question);
            }
        });

    }

    @Override
    public void removeFromExamQuestionsList(String question) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                observableExamQuestionsList.remove(question);
            }
        });

    }

    public ObservableList<String> getObservableExamQuestionsList() {
        return observableExamQuestionsList;
    }

    @Override
    public void saveExamDetails(String examId) {
        ClientApp.sendRequest(new ViewExamRequest(examId));
    }



    @Override
    public void showQuestionInfo(String questionId) {

    }

    @Override
    public void done() {

    }

    @Override
    public void cancelExamAddition() {
        observableQuestionsList.clear();
        observableExamQuestionsList.clear();
    }

    //TODO: implement IStudentExamExecutionData methods
    @Override
    public void storeAnswer(int questionNumber, int answerNumber) {

    }

    @Override
    public void displayExam() {

    }

    @Override
    public void submitExam() {

    }

    @Override
    public void raiseHand() {

    }

    //TODO: implement IExamData Method
    @Override
    public void saveExam(String title, List<String> questionList, String examId) {

    }

    //TODO: implement ITeacherExamExecutionData Methods
    @Override
    public void timeExtensionRequest(int minutes) {

    }

    @Override
    public void handleRaisedHand(String studentId) {

    }

    //TODO: implement IDeanExamExecutionData method NOTE: the request should be other type then String!!
    @Override
    public void handleTimeExtensionRequest(String request) {

    }

    private LightExam currentExam;

    public List<LightQuestion> getLightQuestionListFromCurrentExam() {
        return currentExam.getLightQuestionList();
    }

    public String getCurrentExamTitle(){
        return currentExam.getTitle();
    }

    public String getCurrentExamTeacherNotes(){
        return currentExam.getTeacherNotes();
    }

    public int getCurrentExamDurationOnMinutes(){
        return currentExam.getDurationInMinutes();
    }

    public List<Double> getCurrentExamQuestionsScoreList(){
        return currentExam.getQuestionsScores();
    }

    public void viewExam() {
        ClientApp.sendRequest(new ViewExamRequest("1111"));
    }

    @Subscribe
    public void handleViewExamResponse(ViewExamResponse response) {
        currentExam = response.getLightExam();
    }


    //TODO: implement IStudentViewStatsData methods

    @Override
    public List getExams(String courseName) {
        return null;
    }

    @Override
    public void confirmGrade(String examId) {

    }

    @Override
    public void changeGrade(double newGrade, String reason, String examId) {

    }

    //exam management data

    private final ObservableList<String> observableExamList = FXCollections.observableArrayList();

    @Subscribe
    public void handleAllExamsResponse(AllExamsResponse response) {
        if (response.getStatus() == 0) {
            if (observableExamList.size() > 0) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        observableExamList.clear();
                    }
                });

            }
            generateExamDescriptors(response.getExamList());
        }
    }

    @Override
    public void deployExam(String examId,String examCode) {

    }

    public void generateExamDescriptors(HashMap<String, Pair<LocalDateTime, String>> examList) {
        for (Map.Entry<String, Pair<LocalDateTime, String>> exam : examList.entrySet()) {
            String examId = exam.getKey();
            String examDescription = exam.getValue().getSecond();
            String menuItemText = "#" + examId + ": " + examDescription;
            addToObservableExamList(menuItemText); //add to observable list upon generation
        }

    }

    private void addToObservableExamList(String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                observableExamList.add(text);
            }
        });
    }


    @Override
    public void deleteExam(String examId) {
        Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDelete.setContentText("Are you sure you want to delete exam number " + examId + "?");
        Optional<ButtonType> result = confirmDelete.showAndWait();
        if (result.get() == ButtonType.OK)
            ClientApp.sendRequest(new DeleteExamRequest(examId));
    }


    @Override
    public void clearExamList() {
        observableExamList.clear();
    }

    // TODO: decide if the function return void or list, conflict between istudentexam and iexammanagement
    @Override
    public void viewExam(String examId) {
        ClientApp.sendRequest(new ViewExamRequest(examId));
    }

    public ObservableList<String> getObservableExamList() {
        return observableExamList;
    }

    @Override
    public void fillExamList(String courseName) {
        ClientApp.sendRequest(new AllExamsRequest(courseName));
    }

    @Override
    public void addNote(String note) {

    }

    // TODO: implement method for ITeacherViewStatsData
    @Override
    public List getTeacherExams() {
        return null;
    }

    public Set<String> getSubjects() {
        return subjectsAndCourses.keySet();
    }

    @Override
    public List getCourses(String subjectName) {
        return null;
    }

    @Override
    public double getAverageGrade(String courseName) {
        return 0;
    }

    @Override
    public void viewExamStatistics(String examId) {

    }

    @Override
    public void viewReport(String report) {

    }
}
