package org.args.Client;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Exams.*;
import DatabaseAccess.Requests.ExecuteExam.*;
import DatabaseAccess.Requests.LoginRequest;
import DatabaseAccess.Requests.Questions.*;
import DatabaseAccess.Requests.ReviewExam.CheckedExamRequest;
import DatabaseAccess.Requests.ReviewExam.EvaluateManualExamRequest;
import DatabaseAccess.Requests.ReviewExam.PendingExamRequest;
import DatabaseAccess.Requests.SubjectsAndCoursesRequest;
import DatabaseAccess.Responses.Exams.AllExamsResponse;
import DatabaseAccess.Responses.Exams.ViewExamResponse;
import DatabaseAccess.Responses.ExecuteExam.*;
import DatabaseAccess.Responses.LoginResponse;
import DatabaseAccess.Responses.Questions.AllQuestionsResponse;
import DatabaseAccess.Responses.Questions.QuestionResponse;
import DatabaseAccess.Responses.ReviewExam.CheckedExamResponse;
import DatabaseAccess.Responses.ReviewExam.EvaluateManualExamResponse;
import DatabaseAccess.Responses.ReviewExam.PendingExamResponse;
import DatabaseAccess.Responses.Statistics.TeacherStatisticsResponse;
import DatabaseAccess.Responses.SubjectsAndCoursesResponse;
import LightEntities.LightExam;
import LightEntities.LightQuestion;
import Notifiers.ConfirmTimeExtensionNotifier;
import Notifiers.TimeExtensionRequestNotifier;
import Util.Pair;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.args.GUI.ClientApp;
import org.args.GUI.StudentExamType;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataModel implements IMainScreenData, IQuestionManagementData, IQuestionData, IStudentExamExecutionData,
        IDeanViewStatsData, IStudentViewStatsData, IExamData, ITeacherViewStatsData, IExamManagementData, IExamReviewData, ITeacherExecuteExamData, IViewDeanTimeExtensionData {

    private final ClientApp app;

    private final WordGenerator wordGenerator;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public DataModel(ClientApp clientApp) {
        app = clientApp;
        wordGenerator = new WordGenerator();
        EventBus.getDefault().register(this);
    }


    //main screen data
    private String name;

    private String permission;

    private String userName;

    @Subscribe
    public void handleLoginResponse(LoginResponse response) {
        if (response.getStatus() == 0)
        {
            setName(response.getName());
            permission = response.getPermission();
        }

    }


    public String getName() { //used by multiple screens to identify user's name
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void login(String userName, String password) {
        ClientApp.sendRequest(new LoginRequest(userName, password));
    }

    @Override
    public void loadSubjects() { //used to load subjects and courses to the model before switching screens
        ClientApp.sendRequest(new SubjectsAndCoursesRequest());
    }

    public String getPermission() {
        return permission;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void studentTakeComputerizedExam(String examCode, String id) {
        ClientApp.sendRequest(new TakeExamRequest(Integer.parseInt(id), examCode, true));
    }

    @Override
    public void studentTakeManualExam(String code) {
        ClientApp.sendRequest(new TakeExamRequest(0, code, false));
        setManualExam(true);
    }

    //question management - subjects and courses dropdowns and screen init

    @Subscribe
    public void handleSubjectsAndCoursesResponse(SubjectsAndCoursesResponse response) {
        if (response.getStatus() == 0)
            setSubjectsAndCourses(response.getSubjectsAndCourses());
    }

    String currentQuestionId;

    public String getCurrentQuestionId() {
        return currentQuestionId;
    }

    @Override
    public void setCurrentQuestionId(String currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    private HashMap<String, HashMap<String, String>> subjectsAndCourses;

    private String currentSubject;

    private String currentCourseId;
    /* bound to buttons that shouldn't be visible/enabled while a course isn't selected */
    private final BooleanProperty courseSelected = new SimpleBooleanProperty(false);

    public void setSubjectsAndCourses(HashMap<String, HashMap<String, String>> mapFromResponse) {
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
        HashMap<String, String> coursesEntries = subjectsAndCourses.get(subject);
        List<String> listOfCourses = new Vector<>();
        for (Map.Entry<String, String> course : coursesEntries.entrySet())
        {
            String id = course.getKey();
            String name = course.getValue();
            listOfCourses.add(id + " - " + name);
        }
        return listOfCourses;
    }

    public String getCurrentSubject() {
        return currentSubject;
    }

    public void setCurrentSubject(String currentSubject) {
        this.currentSubject = currentSubject;
    }

    public String getCurrentCourseId() {
        return currentCourseId;
    }

    public void setCurrentCourseId(String currentCourseId) {
        this.currentCourseId = currentCourseId;
        courseSelected.setValue(true);
    }

    /*used to verify if data is present before init of question management screen*/
    public boolean dataWasAlreadyInitialized() {
        return currentSubject != null && !subjectsAndCourses.isEmpty();
    }

    //question management - questions list data

    private final ObservableList<String> observableQuestionsList = FXCollections.observableArrayList();

    private void addToObservableQuestionsList(String text) {
        Platform.runLater(() -> observableQuestionsList.add(text));
    }

    @Subscribe
    public void handleAllQuestionsResponse(AllQuestionsResponse response) {
        if (response.getStatus() == 0)
        {
            if (observableQuestionsList.size() > 0)
            {
                Platform.runLater(observableQuestionsList::clear);
            }
            generateQuestionDescriptors(response.getQuestionList());
        }
    }

    /*sends the request needed to fill the questions list, which is then filled by the response handler*/
    public void fillQuestionsList(String courseId) {
        ClientApp.sendRequest(new AllQuestionsRequest(courseId));
    }

    /*sends the request needed to view the details of the selected question*/
    public void loadQuestionDetails(String questionId) {
        ClientApp.sendRequest(new QuestionRequest(questionId));
    }

    /*break down the response and show strings that represents question in an -#id: content- format*/
    public void generateQuestionDescriptors(HashMap<String, Pair<LocalDateTime, String>> questionList) {
        for (Map.Entry<String, Pair<LocalDateTime, String>> question : questionList.entrySet())
        {
            String questionId = question.getKey();
            String questionDescription = question.getValue().getSecond();
            String menuItemText = "#" + questionId + ": " + questionDescription;
            addToObservableQuestionsList(menuItemText); //add to observable list upon generation
        }

    }

    public ObservableList<String> getObservableQuestionsList() {
        return observableQuestionsList;
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
    private ObservableList<String> choiceItems;

    @Override
    public void deleteQuestion(String questionId) {
        ClientApp.sendRequest(new DeleteQuestionRequest(questionId));
    }

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
        DatabaseRequest request;
        if (isCreating)
        {
            request = new AddQuestionRequest(newContent, Arrays.asList(answer_1, answer_2, answer_3, answer_4), correctAnswer, currentCourseId);

        }
        else
        {
            request = new EditQuestionRequest(questionId, newContent, Arrays.asList(answer_1, answer_2, answer_3, answer_4), correctAnswer);
        }
        ClientApp.sendRequest(request);
    }


    @Subscribe
    public void handleQuestionResponse(QuestionResponse response) {

        if (response.getStatus() == 0)
        {
            setQuestionId(((QuestionRequest) response.getRequest()).getQuestionID());
            setLastModified(response.getLastModified().format(dateTimeFormatter));
            setAuthor(response.getAuthorUserName());
            setContent(response.getQuestionContent());
            setAnswers(response.getAnswers());
            setCorrectAnswer(response.getCorrectAnswer());
        }
    }

    //Add Exam data

    //used to determine whether the screen is used for creating an exam or editing one. gets values EDIT and ADD
    String viewMode;

    @Override
    public String getViewMode() {
        return viewMode;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
        if (viewMode.equals("ADD"))
        {
            if (!observableQuestionsScoringList.isEmpty())
                observableQuestionsScoringList.clear();
        }
    }


    private LightExam currentExam;

    public List<LightQuestion> getLightQuestionListFromCurrentExam() {
        return currentExam.getLightQuestionList();
    }


    public List<Double> getCurrentExamQuestionsScoreList() {
        return currentExam.getQuestionsScores();
    }

    /*properties bound to the various exam screens*/
    private final ObservableList<String> observableExamQuestionsList = FXCollections.observableArrayList();
    private final ObservableList<String> observableQuestionsScoringList = FXCollections.observableArrayList();
    private final StringProperty currentExamTitle = new SimpleStringProperty();
    private final StringProperty currentExamTeacherNotes = new SimpleStringProperty();
    private final StringProperty currentExamStudentNotes = new SimpleStringProperty();
    private final StringProperty currentExamDuration = new SimpleStringProperty();
    private final StringProperty currentExamTotalScore = new SimpleStringProperty();

    @Subscribe
    public void handleViewExamResponse(ViewExamResponse response) {
        currentExam = response.getLightExam();
        setCurrentExamTitle(currentExam.getTitle());
        setCurrentExamStudentNotes(currentExam.getStudentNotes());
        setCurrentExamTeacherNotes(currentExam.getTeacherNotes());
        setCurrentExamDuration(Integer.toString(currentExam.getDurationInMinutes()));
        observableExamQuestionsList.clear();
        observableQuestionsScoringList.clear();
        for (LightQuestion question : currentExam.getLightQuestionList())
        {
            observableExamQuestionsList.add(question.toString());
        }
        for (Double score : currentExam.getQuestionsScores())
        {
            observableQuestionsScoringList.add(Double.toString(score));
        }
    }


    public void startExamEdit() {
        setViewMode("EDIT");
    }

    @Override
    public boolean isExamDeletable() {
        return getUserName().equals(currentExam.getAuthorUserName());
    }

    @Override
    public void deleteExam() {
        ClientApp.sendRequest(new DeleteExamRequest(currentExam.getId()));
    }

    public String getCurrentExamTotalScore() {
        return currentExamTotalScore.get();
    }

    @Override
    public StringProperty currentExamTotalScoreProperty() {
        return currentExamTotalScore;
    }

    public void setCurrentExamTotalScore(String currentExamTotalScore) {
        this.currentExamTotalScore.set(currentExamTotalScore);
    }

    public ObservableList<String> getObservableQuestionsScoringList() {
        return observableQuestionsScoringList;
    }

    public void initQuestionsScoringList() {
//        if (observableQuestionsScoringList.isEmpty() && !observableExamQuestionsList.isEmpty()) {
//            for (String str : observableExamQuestionsList) {
//                observableQuestionsScoringList.add("0");
//            }
//        } else if (observableExamQuestionsList.isEmpty()) {
//            observableQuestionsScoringList.clear();
//
//        }else if( observableQuestionsScoringList.size() != observableExamQuestionsList.size()){
//            for(int i=observableQuestionsScoringList.size();i<observableExamQuestionsList.size();i++){
//                observableQuestionsScoringList.add("0");
//            }
//        }
        setCurrentExamTotalScore(String.valueOf(calcQuestionsScoringListValue()));

    }

    public double calcQuestionsScoringListValue() {
        double sum = 0;
        for (String str : observableQuestionsScoringList)
        {
            sum += Double.parseDouble(str);
        }
        return sum;
    }

    public boolean checkQuestionScoringList() {
        if (observableQuestionsScoringList.isEmpty())
            return false;
        for (String str : observableQuestionsScoringList)
        {
            if (Double.parseDouble(str) == 0)
                return false;
        }
        return true;
    }

    public StringProperty currentExamTitleProperty() {
        return currentExamTitle;
    }

    @Override
    public String getCurrentExamTitle() {
        return currentExamTitle.get();
    }

    @Override
    public String getCurrentExamTeacherNotes() {
        return currentExamTeacherNotes.get();
    }

    public StringProperty currentExamTeacherNotesProperty() {
        return currentExamTeacherNotes;
    }

    public String getCurrentExamStudentNotes() {
        return currentExamStudentNotes.get();
    }

    public StringProperty currentExamStudentNotesProperty() {
        return currentExamStudentNotes;
    }

    public void setCurrentExamTitle(String currentExamTitle) {
        this.currentExamTitle.set(currentExamTitle);
    }

    public void setCurrentExamTeacherNotes(String currentExamTeacherNotes) {
        this.currentExamTeacherNotes.set(currentExamTeacherNotes);
    }

    public void setCurrentExamStudentNotes(String currentExamStudentNotes) {
        this.currentExamStudentNotes.set(currentExamStudentNotes);
    }

    @Override
    public String getCurrentExamDuration() {
        return currentExamDuration.get();
    }

    @Override
    public StringProperty currentExamDurationProperty() {
        return currentExamDuration;
    }

    public void setCurrentExamDuration(String currentExamDuration) {
        this.currentExamDuration.set(currentExamDuration);
    }

    @Override
    public void addToExamQuestionsList(String question) {
        Platform.runLater(() -> {
            observableExamQuestionsList.add(question);
            // check if new score need to be added
            if (observableExamQuestionsList.size() > observableQuestionsScoringList.size())
                observableQuestionsScoringList.add("0");
        });

    }

    @Override
    public void removeFromExamQuestionsList(String question) {
        Platform.runLater(() -> {
            observableQuestionsScoringList.remove(observableExamQuestionsList.indexOf(question));
            observableExamQuestionsList.remove(question);
        });

    }

    public ObservableList<String> getObservableExamQuestionsList() {
        return observableExamQuestionsList;
    }

    @Override
    public void viewExam(String examId) {
        fillQuestionsList(currentCourseId);
        ClientApp.sendRequest(new ViewExamRequest(examId));
    }

    @Override
    public void cancelExamAddition() {
        observableExamQuestionsList.clear();
    }

    @Override
    public void clearDetailsScreen() {
        currentExamDuration.setValue("");
        currentExamTitle.setValue("");
        currentExamStudentNotes.setValue("");
        currentExamTeacherNotes.setValue("");
        cancelExamAddition();

    }

    @Override
    public void showQuestionInfo(String questionId) {

    }


    //exam management data

    private final ObservableList<String> observableExamList = FXCollections.observableArrayList();

    String currentExamId;

    public String getCurrentExamId() {
        return currentExamId;
    }

    public void setCurrentExamId(String currentExamId) {
        this.currentExamId = currentExamId;
    }

    @Subscribe
    public void handleAllExamsResponse(AllExamsResponse response) {
        if (response.getStatus() == 0)
        {
            if (observableExamList.size() > 0)
            {
                Platform.runLater(observableExamList::clear);

            }
            generateExamDescriptors(response.getExamList());
        }
    }


    /*used to represent the exams as strings in the list view in the format: -#id: content-
        (same as questions in question management*/
    public void generateExamDescriptors(HashMap<String, Pair<LocalDateTime, String>> examList) {
        for (Map.Entry<String, Pair<LocalDateTime, String>> exam : examList.entrySet())
        {
            String examId = exam.getKey();
            String examDescription = exam.getValue().getSecond();
            String menuItemText = "#" + examId + ": " + examDescription;
            addToObservableExamList(menuItemText); //add to observable list upon generation
        }

    }

    private void addToObservableExamList(String text) {
        Platform.runLater(() -> observableExamList.add(text));
    }


    @Override
    public void clearExamList() {
        observableExamList.clear();
    }

    public ObservableList<String> getObservableExamList() {
        return observableExamList;
    }

    @Override
    public void fillExamList(String courseId) {
        ClientApp.sendRequest(new AllExamsRequest(courseId));
    }

    @Override
    public void addNote(String note) {

    }

    // TODO: implement method for ITeacherViewStatsData
    private final HashMap<String, Double> currentExamForStats = new HashMap<>();

    public HashMap<String, Double> getCurrentExamForStats() {
        return currentExamForStats;
    }

    @Subscribe
    public void handleTeacherStatisticsResponse(TeacherStatisticsResponse response) {
        getCurrentExamForStats().putAll(response.getExamHashMap());
    }

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


    //TODO: implement IStudentExamExecutionData methods

    private boolean raisedHand = false;

    private boolean isManualExam = false;

    private LightExam examForStudentExecution;

    private HashMap<Integer, Integer> correctAnswersMap;

    private File manualExamFile;

    public LightExam getExamForStudentExecution() {
        return examForStudentExecution;
    }

    public void setExamForStudentExecution(LightExam examForStudentExecution) {
        this.examForStudentExecution = examForStudentExecution;
    }

    public HashMap<Integer, Integer> getCorrectAnswersMap() {
        return correctAnswersMap;
    }

    public boolean isManualExam() {
        return isManualExam;
    }

    public void setManualExam(boolean manualExam) {
        isManualExam = manualExam;
    }

    public void setCorrectAnswersMap(HashMap<Integer, Integer> correctAnswersMap) {
        this.correctAnswersMap = correctAnswersMap;
    }

    public File getManualExamFile() {
        return manualExamFile;
    }

    public void setManualExamFile(File manualExamFile) {
        this.manualExamFile = manualExamFile;
    }

    @Subscribe
    public void handleTakeExamResponse(TakeExamResponse response) {
        if (response.getStatus() == 0)
        {
            setExamForStudentExecution(response.getLightExam());
            if (correctAnswersMap == null)
                correctAnswersMap = new HashMap<>();
            else
                correctAnswersMap.clear();
        }
    }

    @Override
    public void storeAnswer(int questionNumber, int answerNumber) {
        correctAnswersMap.put(questionNumber, answerNumber);
    }

    @Override
    public void displayExam() {

    }

    @Override
    public void submitExam() {
        if (isManualExam())
        {
            ClientApp.sendRequest(new SubmitManualExamRequest(getExamForStudentExecution().getId(), getManualExamFile()));
            setManualExam(false);
        }
        else
        {
            List<Integer> correctAnswersList = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : getCorrectAnswersMap().entrySet())
                correctAnswersList.add(entry.getKey(), entry.getValue());
            ClientApp.sendRequest(new SubmitExamRequest(examForStudentExecution.getId(), correctAnswersList));
        }

    }

    @Override
    public void raiseHand() {
        if (!raisedHand)
        {
            ClientApp.sendRequest(new RaiseHandRequest());
            raisedHand = true;
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFO");
                alert.setHeaderText(null);
                alert.setContentText("A Massage Has Been Sent To You're Teacher");
                alert.show();
            });
        }
        else
        {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFO");
                alert.setHeaderText(null);
                alert.setContentText("A Massage Has Been Sent Already, Please Wait To You're Teacher Response");
                alert.show();
            });
        }

    }

    @Override
    public void createManualTest(File path) {
        // test of word generator
        try
        {
            wordGenerator.createWordFile(getExamForStudentExecution(), path);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void handleRaiseHandResponse(RaiseHandResponse response) {
        if (response.getStatus() == 0)
        {
            raisedHand = false;
        }
    }

    //TODO: implement IExamData Method
    @Override
    public void saveExam(String title, int duration, String teacherNotes, String studentNotes, List<String> questionList, List<Double> questionsScoreList, String examId) {
        if (getViewMode().equals("ADD"))
        {
            ClientApp.sendRequest(new AddExamRequest(title, generateListOfIds(questionList), questionsScoreList, teacherNotes, studentNotes, duration, currentCourseId));
        }
        else
        {
            ClientApp.sendRequest(new EditExamRequest(examId, title, generateListOfIds(questionList), questionsScoreList, teacherNotes, studentNotes, duration));
        }
        ClientApp.popLastScene();
    }

    private List<String> generateListOfIds(List<String> questions) {
        List<String> questionIds = new Vector<>();
        for (String question : questions)
        {
            questionIds.add(question.substring(1, 6));
        }
        return questionIds;

    }

    //TODO: implement ITeacherExamExecutionData Methods
    //Teacher Execute Exam Data

    String currentExecutedExamLaunchTime;
    String currentExecutedExamTitle;
    final ObservableList<String> currentHandsRaised = FXCollections.observableArrayList();

    public ObservableList<String> getCurrentHandsRaised() {
        return currentHandsRaised;
    }

    public String getCurrentExecutedExamLaunchTime() {
        return currentExecutedExamLaunchTime;
    }

    public void setCurrentExecutedExamLaunchTime(String currentExecutedExamLaunchTime) {
        this.currentExecutedExamLaunchTime = currentExecutedExamLaunchTime;
    }

    public String getCurrentExecutedExamTitle() {
        return currentExecutedExamTitle;
    }

    public void setCurrentExecutedExamTitle(String currentExecutedExamTitle) {
        this.currentExecutedExamTitle = currentExecutedExamTitle;
    }

    @Override
    public void executeExam(String examId, String examCode) {
        setCurrentExamId(examId);
        ClientApp.sendRequest(new ExecuteExamRequest(examId, examCode));
    }

    @Override
    public void sendTimeExtensionRequest(int timeExtension, String reason) {
        ClientApp.sendRequest(new TimeExtensionRequest(currentExamId, timeExtension, reason));
    }

    @Subscribe
    public void handleRaisedHandResponse(RaiseHandResponse response) {
        Platform.runLater(() -> currentHandsRaised.add(response.getStudentName()));
    }

    @Override
    public void solveRaisedHand(String currentStudentName) {
        Platform.runLater(() -> currentHandsRaised.remove(currentStudentName));
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

    //Teacher pending exams data

    ObservableList<String> pendingExamsObservableList = FXCollections.observableArrayList();

    public ObservableList<String> getPendingExamsObservableList() {
        return pendingExamsObservableList;
    }

    @Subscribe
    public void handlePendingExamResponse(PendingExamResponse response) {
        if (response.getStatus() == 0)
        {
            for (Map.Entry<Integer, String> entry : response.getCheckedExamsList().entrySet())
            {
                Platform.runLater(() -> {
                    int examId = entry.getKey();
                    String examTitle = entry.getValue();
                    String fullExamDescription = "#" + examId + ":" + examTitle;
                    pendingExamsObservableList.add(fullExamDescription);
                });
            }
        }
    }

    @Override
    public void showPendingExamGrades(String examId) {
        ClientApp.sendRequest(new CheckedExamRequest(examId));
    }

    @Override
    public void loadPendingExams() {
        ClientApp.sendRequest(new PendingExamRequest());
    }

    @Override
    public void clearPendingExams() {
        pendingExamsObservableList.clear();
    }

    //TODO: implements the manual exam review

    private File manualExamForReview;

    private String manualExamForReviewStudentId;

    public File getManualExamForReview() {
        return manualExamForReview;
    }

    public void setManualExamForReview(File manualExamForReview) {
        this.manualExamForReview = manualExamForReview;
    }

    @Override
    public String getManualExamForReviewStudentId() {
        return manualExamForReviewStudentId;
    }

    public void setManualExamForReviewStudentId(String manualExamForReviewStudentId) {
        this.manualExamForReviewStudentId = manualExamForReviewStudentId;
    }

    public void saveWordFile(File filePath) {
        try
        {
            wordGenerator.saveWordFile(getManualExamForReview(), filePath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void submitExamReview(double grade, String notes, File manualExamFile) {
        ClientApp.sendRequest(new EvaluateManualExamRequest(grade, notes, manualExamFile));

    }

    @Subscribe
    public void handleEvaluateManualExamResponse(EvaluateManualExamResponse response) {
        if (response.getStatus() == 0)
        {
            // clean all the relevant attributes

        }
    }

    ObservableList<StudentExamType> studentsGradesToReview = FXCollections.observableArrayList();

    public ObservableList<StudentExamType> getStudentsGradesToReview() {
        return studentsGradesToReview;
    }

    @Subscribe
    public void handleCheckedExamResponse(CheckedExamResponse response) {
        if (response.getStatus() == 0)
        {
            Platform.runLater(() -> {
                //HashMap<studentID,isComputerized>
                for (Map.Entry<String, Boolean> entry : response.getCheckedExamsList().entrySet())
                {
                    String method = entry.getValue() ? "Computerized" : "Manual";
                    studentsGradesToReview.add(new StudentExamType(entry.getKey(), method));
                }
            });
        }
    }

    @Override
    public void reviewComputerizedExam(String id) {

    }

    @Override
    public void reviewManualExam(String id) {

    }

    // dean time extension

    ObservableList<String> ObservableTimeExtensionRequestsList = FXCollections.observableArrayList();

    public ObservableList<String> getObservableTimeExtensionRequestsList() {
        return ObservableTimeExtensionRequestsList;
    }

    @Subscribe
    public void handleConfirmTimeExtensionResponse(TimeExtensionRequestNotifier notifier) {
        Platform.runLater(() -> {
            String requestDescription = notifier.getExamId() + ": " + notifier.getDurationInMinutes() +
                    "minutes request - " + notifier.getReasonForExtension();
            ObservableTimeExtensionRequestsList.add(requestDescription);
        });
    }

    @Override
    public void removeRequest(String selectedItem) {
        Platform.runLater(() -> getObservableTimeExtensionRequestsList().remove(selectedItem));
    }

    @Override
    public void rejectExtension(String reason, String examId) {
        ClientApp.sendRequest(new ConfirmTimeExtensionRequest(false, reason, 0, examId));
    }

    @Override
    public void acceptExtension(String extension, String examId) {
        ClientApp.sendRequest(new ConfirmTimeExtensionRequest(true, "", Integer.parseInt(extension), examId));

    }
}
