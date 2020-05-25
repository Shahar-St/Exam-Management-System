package org.args.Client;

import DatabaseAccess.Requests.*;
import DatabaseAccess.Responses.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.args.GUI.ClientApp;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.LocalDateTime;
import java.util.*;

public class DataModel implements IMainScreenData, IQuestionManagementData,IEditQuestionScreenData {

    public DataModel() {
        EventBus.getDefault().register(this);
    }


    //teacher main screen data
    @Subscribe
    public void handleLoginResponse(LoginResponse response){
        if(response.getStatus()==0){
            setName(response.getName());
        }

    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void login(String userName,String password){
        ClientApp.sendRequest(new LoginRequest(userName,password));
    }

    @Override
    public void loadSubjects() {
        ClientApp.sendRequest(new SubjectsAndCoursesRequest());
    }

    //question management screen data - subjects and courses dropdowns

    @Subscribe
    public void handleSubjectsAndCoursesResponse(SubjectsAndCoursesResponse response){
        if (response.getStatus() == 0)
            setSubjectsAndCourses(response.getSubjectsAndCourses());
    }

    private HashMap<String, List<String>> subjectsAndCourses;

    private String currentSubject;

    private String currentCourse;

    public void setSubjectsAndCourses(HashMap<String, List<String>> mapFromResponse) {
        subjectsAndCourses = mapFromResponse;
    }
    public Set<String> getSubjects()
    {
        return subjectsAndCourses.keySet();
    }

    public List<String> getCoursesOfSubject(String subject)
    {
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
    }

    public boolean dataWasAlreadyInitialized()
    {
        return currentSubject != null && !subjectsAndCourses.isEmpty();
    }

    //question management screen data - questions list data

    @Subscribe
    public void handleAllQuestionsResponse(AllQuestionsResponse response){
        if (response.getStatus() == 0)
        {
            if(observableQuestionsList.size() > 0)
                observableQuestionsList.clear();
            generateQuestionDescriptors(response.getQuestionList());
        }
    }

    public void fillQuestionsList(String text) {
        ClientApp.sendRequest(new AllQuestionsRequest(text));
    }

    public void saveQuestionDetails(String questionId) {
        ClientApp.sendRequest(new QuestionRequest(questionId));
    }

    private int selectedIndex;

    private final ObservableList<String> observableQuestionsList = FXCollections.observableArrayList();

    private void addToObservableList(String text)
    {
        observableQuestionsList.add(text);
    }

    public void generateQuestionDescriptors(HashMap<String, Pair<LocalDateTime, String>> questionList) {
        for (Map.Entry<String, Pair<LocalDateTime, String>> question : questionList.entrySet()) {
            String questionId = question.getKey();
            String questionDescription = question.getValue().getSecond();
            String menuItemText = "#" + questionId + ": " + questionDescription;
            addToObservableList(menuItemText); //add to observable list upon generation
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

    //editquestionscreen section
    private String questionId ;
    private String lastModified;
    private String author;
    private String content ;
    private List<String> answers ;
    private int correctAnswer ;

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

    public void saveQuestion(String questionId,String answer_1,String answer_2,String answer_3,String answer_4,String newContent){
        EditQuestionRequest request = new EditQuestionRequest(questionId, newContent,
                Arrays.asList(answer_1, answer_2, answer_3,
                        answer_4), correctAnswer);
        ClientApp.sendRequest(request);
    }


    @Subscribe
    public void handleQuestionResponse(QuestionResponse response){
        if(response.getStatus()==0){
            setQuestionId(((QuestionRequest)response.getRequest()).getQuestionID());
            setLastModified(response.getLastModified().toString());
            setAuthor(response.getAuthor());
            setContent(response.getQuestionContent());
            setAnswers(response.getAnswers());
            setCorrectAnswer(response.getCorrectAnswer());
        }

    }


}
