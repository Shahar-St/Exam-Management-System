package org.args.Client;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Exams.*;
import DatabaseAccess.Requests.ExecuteExam.*;
import DatabaseAccess.Requests.LoginRequest;
import DatabaseAccess.Requests.Questions.*;
import DatabaseAccess.Requests.ReviewExam.EvaluateExamRequest;
import DatabaseAccess.Requests.ReviewExam.GetExecutedExamRequest;
import DatabaseAccess.Requests.ReviewExam.PendingExamsRequest;
import DatabaseAccess.Requests.ReviewExam.UncheckedExecutesOfConcreteRequest;
import DatabaseAccess.Requests.Statistics.GetAllPastExamsRequest;
import DatabaseAccess.Requests.Statistics.TeacherGetAllPastExamsRequest;
import DatabaseAccess.Requests.Statistics.TeacherStatisticsRequest;
import DatabaseAccess.Requests.SubjectsAndCoursesRequest;
import DatabaseAccess.Responses.Exams.AllExamsResponse;
import DatabaseAccess.Responses.Exams.ViewExamResponse;
import DatabaseAccess.Responses.ExecuteExam.ExecuteExamResponse;
import DatabaseAccess.Responses.ExecuteExam.TakeExamResponse;
import DatabaseAccess.Responses.LoginResponse;
import DatabaseAccess.Responses.Questions.AllQuestionsResponse;
import DatabaseAccess.Responses.Questions.QuestionResponse;
import DatabaseAccess.Responses.ReviewExam.EvaluateExamResponse;
import DatabaseAccess.Responses.ReviewExam.GetExecutedExamResponse;
import DatabaseAccess.Responses.ReviewExam.PendingExamsResponse;
import DatabaseAccess.Responses.ReviewExam.UncheckedExecutesOfConcreteResponse;
import DatabaseAccess.Responses.Statistics.GetAllPastExamsResponse;
import DatabaseAccess.Responses.Statistics.TeacherGetAllPastExamsResponse;
import DatabaseAccess.Responses.Statistics.TeacherStatisticsResponse;
import DatabaseAccess.Responses.SubjectsAndCoursesResponse;
import LightEntities.LightExam;
import LightEntities.LightExecutedExam;
import LightEntities.LightQuestion;
import Notifiers.ConfirmTimeExtensionNotifier;
import Notifiers.ExamEndedNotifier;
import Notifiers.RaiseHandNotifier;
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
import org.args.GUI.StudentGrade;
import org.args.GUI.StudentPastExam;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataModel implements IMainScreenData, IQuestionManagementData, IQuestionData, IStudentExamExecutionData,
        IViewPastExamsData, IExamData, IViewResultsData, IExamManagementData, IExamReviewData, ITeacherExecuteExamData, IViewDeanTimeExtensionData {


    final ObservableList<StudentGrade> studentGradesObservableList = FXCollections.observableArrayList();
    final ObservableList<String> currentHandsRaised = FXCollections.observableArrayList();
    final StringProperty currentExecutedExamEndTime = new SimpleStringProperty();

    //pending exams for teacher
    final ObservableList<String> pendingExamsObservableList = FXCollections.observableArrayList();
    final HashMap<String, String> pendingExamsMap = new HashMap<>();

    //reviewing the grades for a specific auto-checked exam
    final ObservableList<StudentExamType> studentsGradesToReview = FXCollections.observableArrayList();

    //holds the current time extension requests for the dean
    final ObservableList<String> ObservableTimeExtensionRequestsList = FXCollections.observableArrayList();

    //past exam results
    final ObservableList<String> pastExamsResultsObservableList = FXCollections.observableArrayList();

    //generator for manual exam files
    private final WordGenerator wordGenerator;

    //formatters used to format Dates throughout the application
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final DateTimeFormatter hourMinutesFormatter = DateTimeFormatter.ofPattern("HH:mm");

    //denotes whether a course is selected or not.
    private final BooleanProperty courseSelected = new SimpleBooleanProperty(false);

    //holds the questions of the selected course (question management screen)
    private final ObservableList<String> observableQuestionsList = FXCollections.observableArrayList();

    /*properties bound to the various exam creation/edit screens*/
    private final ObservableList<String> observableExamQuestionsList = FXCollections.observableArrayList();
    private final ObservableList<String> observableQuestionsScoringList = FXCollections.observableArrayList();
    private final StringProperty currentExamTitle = new SimpleStringProperty();
    private final StringProperty currentExamTeacherNotes = new SimpleStringProperty();
    private final StringProperty currentExamStudentNotes = new SimpleStringProperty();
    private final StringProperty currentExamDuration = new SimpleStringProperty();
    private final StringProperty currentExamTotalScore = new SimpleStringProperty();
    private final ObservableList<String> observableExamList = FXCollections.observableArrayList();

    //holds past exams information (student)
    private final HashMap<String, String> pastExamsMap = new HashMap<>();
    final ObservableList<StudentPastExam> studentPastExamsObservableList = FXCollections.observableArrayList();


    //when adding an exam, viewMode is used to determine whether the screen is used for creating an exam or editing one.
    // gets values EDIT and ADD.
    String viewMode;

    String currentQuestionId;
    String currentExamId;
    String currentExecutedExamLaunchTime;
    String currentExecutedExamTitle;
    LocalDateTime currentExecutedExamStartLocalDateTime;
    LocalDateTime currentExecutedExamEndLocalDateTime;

    //main screen data
    private String name;
    private String permission;
    private String userName;
    private String id;

    //general subjects and courses the user is a part of. used throughout the application.
    private HashMap<String, HashMap<String, String>> subjectsAndCourses;

    //last selected subject and course info
    private String currentSubject;
    private String currentCourseId;
    private String currentCourseName;


    //question view variables
    private String questionId;
    private String lastModified;
    private String author;
    private String content;
    private List<String> answers;
    private int correctAnswer;
    private boolean isCreating;
    private ObservableList<String> choiceItems;

    //current exam executed
    private LightExam currentExam;
    private boolean raisedHand = false;
    private boolean isManualExam = false;
    private boolean finishedOnTime = true;
    private LightExam examForStudentExecution;
    private HashMap<Integer, Integer> correctAnswersMap;
    private File manualExamFile;
    private LocalDateTime examForStudentExecutionInitDate;
    private boolean timeExtensionGranted = false;
    private int timeExtensionDuration = 0;
    private boolean isSubmitted = false;
    private String currentConcreteExamId;
    private String currentConcreteExamTitle;
    private byte[] manualExamBytes;
    private LightExecutedExam currentLightExecutedExam;
    private String manualExamForReviewStudentId;
    private boolean hasEnded =false;

    public boolean hasEnded(){
        return hasEnded;
    }

    public void setHasEnded(boolean bol){
        this.hasEnded = bol;
    }

    public DataModel() {
        wordGenerator = new WordGenerator();
        EventBus.getDefault().register(this);
    }

    // handle LoginResponse
    @Subscribe
    public void handleLoginResponse(LoginResponse response) {
        if (response.getStatus() == 0) {
            setName(response.getName());
            permission = response.getPermission();
            id = response.getId();
        }
    }

    @Override
    public void clearSubjectsAndCourses() {
        subjectsAndCourses = null;
    }

    public String getId() {
        return id;
    }

    public String getName() { //used by multiple screens to identify user's name
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void login(String userName, String password) {
        ClientApp.sendRequest(new LoginRequest(userName, password));
    }

    public void loadSubjects() {
        //used to load subjects and courses to the model before switching screens
        if (subjectsAndCourses == null)
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
        ClientApp.sendRequest(new TakeExamRequest(id, examCode, true));
    }

    @Override
    public void studentTakeManualExam(String code) {
        // social id is not required in manual exam execution.
        ClientApp.sendRequest(new TakeExamRequest("0", code, false));
        setManualExam(true);
    }


    public String getCurrentCourseName() {
        return currentCourseName;
    }

    public void setCurrentCourseName(String currentCourseName) {
        this.currentCourseName = currentCourseName;
    }

    @Subscribe
    public void handleSubjectsAndCoursesResponse(SubjectsAndCoursesResponse response) {
        if (response.getStatus() == 0)
            setSubjectsAndCourses(response.getSubjectsAndCourses());
    }

    @Override
    public void setCurrentQuestionId(String currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    private void setSubjectsAndCourses(HashMap<String, HashMap<String, String>> mapFromResponse) {
        subjectsAndCourses = mapFromResponse;
    }

    //used to differentiate between editing a question and adding one.
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
        for (Map.Entry<String, String> course : coursesEntries.entrySet()) {
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

    private void addToObservableQuestionsList(String text) {
        Platform.runLater(() -> observableQuestionsList.add(text));
    }

    @Subscribe
    public void handleAllQuestionsResponse(AllQuestionsResponse response) {
        if (response.getStatus() == 0) {
            if (observableQuestionsList.size() > 0) {
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
    private void generateQuestionDescriptors(HashMap<String, Pair<LocalDateTime, String>> questionList) {
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

    public void clearQuestionsList() {
        observableQuestionsList.clear();
    }

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

    public String getQuestionId() {
        return questionId;
    }

    private void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getLastModified() {
        return lastModified;
    }

    private void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getAuthor() {
        return author;
    }

    private void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    private void setContent(String content) {
        this.content = content;
    }

    public List<String> getAnswers() {
        return answers;
    }

    private void setAnswers(List<String> answers) {
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

    //used to send the question to the server after creating/editing one
    public void saveQuestion(String questionId, String answer_1, String answer_2, String answer_3, String answer_4, String newContent) {
        DatabaseRequest request;
        if (isCreating) {
            request = new AddQuestionRequest(newContent, Arrays.asList(answer_1, answer_2, answer_3, answer_4), correctAnswer, currentCourseId);

        } else {
            request = new EditQuestionRequest(questionId, newContent, Arrays.asList(answer_1, answer_2, answer_3, answer_4), correctAnswer);
        }
        ClientApp.sendRequest(request);
    }

    @Subscribe
    public void handleQuestionResponse(QuestionResponse response) {

        if (response.getStatus() == 0) {
            setQuestionId(((QuestionRequest) response.getRequest()).getQuestionID());
            setLastModified(response.getLastModified().format(dateTimeFormatter));
            setAuthor(response.getAuthorUserName());
            setContent(response.getQuestionContent());
            setAnswers(response.getAnswers());
            setCorrectAnswer(response.getCorrectAnswer());
        }
    }

    public String getViewMode() {
        return viewMode;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
        if (viewMode.equals("ADD")) {
            clearDetailsScreen();
            if (!observableQuestionsScoringList.isEmpty())
                observableQuestionsScoringList.clear();
        }
    }

    public List<LightQuestion> getLightQuestionListFromCurrentExam() {
        return currentExam.getLightQuestionList();
    }

    public List<Double> getCurrentExamQuestionsScoreList() {
        return currentExam.getQuestionsScores();
    }

    @Subscribe
    public void handleViewExamResponse(ViewExamResponse response) {
        currentExam = response.getLightExam();
        setCurrentExamTitle(currentExam.getTitle());
        setCurrentExamStudentNotes(currentExam.getStudentNotes());
        setCurrentExamTeacherNotes(currentExam.getTeacherNotes());
        setCurrentExamDuration(Integer.toString(currentExam.getDurationInMinutes()));
        observableExamQuestionsList.clear();
        observableQuestionsScoringList.clear();
        for (LightQuestion question : currentExam.getLightQuestionList()) {
            observableExamQuestionsList.add(question.toString());
        }
        for (Double score : currentExam.getQuestionsScores()) {
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
        setCurrentExamTotalScore(String.valueOf(calcQuestionsScoringListValue()));

    }

    public double calcQuestionsScoringListValue() {
        double sum = 0;
        for (String str : observableQuestionsScoringList) {
            sum += Double.parseDouble(str);
        }
        return sum;
    }

    public boolean checkQuestionScoringList() {
        if (observableQuestionsScoringList.isEmpty())
            return false;
        for (String str : observableQuestionsScoringList) {
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

    public void setCurrentExamTitle(String currentExamTitle) {
        this.currentExamTitle.set(currentExamTitle);
    }

    @Override
    public String getCurrentExamTeacherNotes() {
        return currentExamTeacherNotes.get();
    }


    public void setCurrentExamTeacherNotes(String currentExamTeacherNotes) {
        this.currentExamTeacherNotes.set(currentExamTeacherNotes);
    }

    public StringProperty currentExamTeacherNotesProperty() {
        return currentExamTeacherNotes;
    }

    public String getCurrentExamStudentNotes() {
        return currentExamStudentNotes.get();
    }

    public void setCurrentExamStudentNotes(String currentExamStudentNotes) {
        this.currentExamStudentNotes.set(currentExamStudentNotes);
    }

    public StringProperty currentExamStudentNotesProperty() {
        return currentExamStudentNotes;
    }

    @Override
    public String getCurrentExamDuration() {
        return currentExamDuration.get();
    }

    public void setCurrentExamDuration(String currentExamDuration) {
        this.currentExamDuration.set(currentExamDuration);
    }

    @Override
    public StringProperty currentExamDurationProperty() {
        return currentExamDuration;
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

    public void viewExam(String examId) {
        fillQuestionsList(currentCourseId);
        ClientApp.sendRequest(new ViewExamRequest(examId));
    }

    private void cancelExamAddition() {
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

    public String getCurrentExamId() {
        return currentExamId;
    }

    public void setCurrentExamId(String currentExamId) {
        this.currentExamId = currentExamId;
    }

    @Subscribe
    public void handleAllExamsResponse(AllExamsResponse response) {
        if (response.getStatus() == 0) {
            if (observableExamList.size() > 0) {
                Platform.runLater(observableExamList::clear);
            }
            generateExamDescriptors(response.getExamList());
        }
    }

    /*used to represent the exams as strings in the list view in the format: -#id: content-
        (same as questions in question management*/
    public void generateExamDescriptors(HashMap<String, Pair<LocalDateTime, String>> examList) {
        for (Map.Entry<String, Pair<LocalDateTime, String>> exam : examList.entrySet()) {
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

    public ObservableList<StudentGrade> getStudentGradesObservableList() {
        return studentGradesObservableList;
    }

    @Subscribe
    public void handleTeacherStatisticsResponse(TeacherStatisticsResponse response) {
        Platform.runLater(() -> {
            for (Map.Entry<String, Double> e : response.getStudentGrades().entrySet()) {
                studentGradesObservableList.add(new StudentGrade(e.getKey(), e.getValue()));
            }
        });
    }

    @Override
    public void clearTeacherPastExamsData() {
        pastExamsResultsObservableList.clear();
        pastExamsMap.clear();
    }

    @Override
    public void clearStudentGradesList() {
        studentGradesObservableList.clear();
    }

    @Override
    public void showGradesOf(String examId) {
        ClientApp.sendRequest(new TeacherStatisticsRequest(examId));
    }

    public Set<String> getSubjects() {
        return subjectsAndCourses.keySet();
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public boolean isTimeExtensionGranted() {
        return timeExtensionGranted;
    }

    public void setTimeExtensionGranted(boolean timeExtensionGranted) {
        this.timeExtensionGranted = timeExtensionGranted;
    }

    public int getTimeExtensionDuration() {
        return timeExtensionDuration;
    }

    public void setTimeExtensionDuration(int timeExtensionDuration) {
        this.timeExtensionDuration = timeExtensionDuration;
    }

    public LocalDateTime getExamForStudentExecutionInitDate() {
        return examForStudentExecutionInitDate;
    }

    public void setExamForStudentExecutionInitDate(LocalDateTime examForStudentExecutionInitDate) {
        this.examForStudentExecutionInitDate = examForStudentExecutionInitDate;
    }


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

    public File getManualExamFile() {
        return manualExamFile;
    }

    public void setManualExamFile(File manualExamFile) {
        this.manualExamFile = manualExamFile;
    }

    public boolean isFinishedOnTime() {
        return finishedOnTime;
    }

    private void setFinishedOnTime(boolean finishedOnTime) {
        this.finishedOnTime = finishedOnTime;
    }

    @Subscribe
    public void handleTakeExamResponse(TakeExamResponse response) {
        if (response.getStatus() == 0) {
            setSubmitted(false);
            setFinishedOnTime(true);
            setExamForStudentExecution(response.getLightExam());
            setExamForStudentExecutionInitDate(response.getInitExamForExecutionDate());
            setTimeExtensionGranted(false);
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

    // take care of empty byte array
    private byte[] fileToByteArray(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            System.out.println("Failed to convert file to bytes");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void submitExam() {
        if (isManualExam()) {
            if (getManualExamFile() != null)
                ClientApp.sendRequest(new SubmitManualExamRequest(getExamForStudentExecution().getId(), fileToByteArray(getManualExamFile())));
            else
                ClientApp.sendRequest(new SubmitManualExamRequest(getExamForStudentExecution().getId(), null));
            setManualExam(false);
        } else {
            List<Integer> correctAnswersList = new ArrayList<>();
            for (int i = 0; i < examForStudentExecution.getLightQuestionList().size(); ++i) {
                correctAnswersList.add(getCorrectAnswersMap().getOrDefault(i, -1));
            }
            setRaisedHand(false);
            ClientApp.sendRequest(new SubmitExamRequest(examForStudentExecution.getId(), correctAnswersList, isFinishedOnTime()));
        }
        setSubmitted(true);

    }

    public boolean isHandRaised() {
        return raisedHand;
    }


    public void setRaisedHand(boolean raisedHand) {
        this.raisedHand = raisedHand;
    }

    @Override
    public void raiseHand() {
        ClientApp.sendRequest(new RaiseHandRequest());
    }

    @Override
    public void createManualTest(File path) {
        // test of word generator
        try {
            wordGenerator.createWordFile(getExamForStudentExecution(), path);
        } catch (IOException e) {
            System.out.println("Failed To Create Manual Exam File.");
            e.printStackTrace();
        }
    }

    public void submitAndQuit() {
        if (isManualExam())
            setManualExamFile(null);
        setFinishedOnTime(false);
        submitExam();
    }

    @Override
    public void saveExam(String title, int duration, String teacherNotes, String studentNotes, List<String> questionList, List<Double> questionsScoreList, String examId) {
        if (getViewMode().equals("ADD")) {
            ClientApp.sendRequest(new AddExamRequest(title, generateListOfIds(questionList), questionsScoreList, teacherNotes, studentNotes, duration, currentCourseId));
        } else {
            ClientApp.sendRequest(new EditExamRequest(examId, title, generateListOfIds(questionList), questionsScoreList, teacherNotes, studentNotes, duration));
        }
        ClientApp.popLastScene();
    }

    //turn whole question descriptors into question IDs
    private List<String> generateListOfIds(List<String> questions) {
        List<String> questionIds = new Vector<>();
        for (String question : questions) {
            questionIds.add(question.substring(1, 6));
        }
        return questionIds;

    }

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
        setHasEnded(false);
        ClientApp.sendRequest(new ExecuteExamRequest(examId, examCode));
    }

    @Override
    public void sendTimeExtensionRequest(int timeExtension, String reason) {
        ClientApp.sendRequest(new TimeExtensionRequest(getCurrentConcreteExamId(), timeExtension, reason));
    }

    @Subscribe
    public void handleRaiseHandNotifier(RaiseHandNotifier notifier) {
        System.out.println("raise hand notifier");
        Platform.runLater(() -> currentHandsRaised.add(notifier.getStudentName()));
    }

    @Subscribe
    public void handleExamEndedNotifier(ExamEndedNotifier notifier) {
        if (getPermission().equals("teacher")) {
            currentHandsRaised.clear();
        }
    }

    @Override
    public void solveRaisedHand(String currentStudentName) {
        Platform.runLater(() -> currentHandsRaised.remove(currentStudentName));
    }

    public LocalDateTime getCurrentExecutedExamEndLocalDateTime() {
        return currentExecutedExamEndLocalDateTime;
    }

    public StringProperty getCurrentExecutedExamEndTimeProperty() {
        return currentExecutedExamEndTime;
    }

    @Subscribe
    public void handleExecuteExamResponse(ExecuteExamResponse response) {
        if (response.getStatus() == 0) {
            setCurrentConcreteExamId(response.getConcreteExamID());
            currentExecutedExamStartLocalDateTime = LocalDateTime.now();
            currentExecutedExamLaunchTime = currentExecutedExamStartLocalDateTime.format(hourMinutesFormatter);
            currentExecutedExamEndLocalDateTime = currentExecutedExamStartLocalDateTime.plusMinutes(response.getDuration());
            currentExecutedExamEndTime.setValue(currentExecutedExamEndLocalDateTime.format(hourMinutesFormatter));

        }
    }

    @Subscribe
    public void handleConfirmTimeExtensionNotifier(ConfirmTimeExtensionNotifier notifier) {
        if (notifier.isAccepted()) {
            if (getPermission().equals("student")) {
                setTimeExtensionGranted(true);
                setTimeExtensionDuration(notifier.getAuthorizedTimeExtension());
            } else if (getPermission().equals("teacher")) {
                currentExecutedExamEndLocalDateTime = currentExecutedExamEndLocalDateTime.plusMinutes(notifier.getAuthorizedTimeExtension());
                Platform.runLater(() -> currentExecutedExamEndTime.setValue(getCurrentExecutedExamEndLocalDateTime().format(hourMinutesFormatter)));
            }

        }

    }

    @Override
    public void endExam() {
        ClientApp.sendRequest(new TeacherEndExamRequest(getCurrentConcreteExamId()));
    }

    public String getCurrentConcreteExamTitle() {
        return currentConcreteExamTitle;
    }

    public void setCurrentConcreteExamTitle(String currentConcreteExamTitle) {
        this.currentConcreteExamTitle = currentConcreteExamTitle;
    }


    public String getCurrentConcreteExamId() {
        return currentConcreteExamId;
    }

    public void setCurrentConcreteExamId(String currentConcreteExamId) {
        this.currentConcreteExamId = currentConcreteExamId;
    }

    public ObservableList<String> getPendingExamsObservableList() {
        return pendingExamsObservableList;
    }

    @Subscribe
    public void handlePendingExamResponse(PendingExamsResponse response) {
        if (response.getStatus() == 0) {
            // hashMap: concreteExamID, (date,title)
            for (Map.Entry<String, Pair<LocalDateTime, String>> entry : response.getConcreteExamsList().entrySet()) {
                Platform.runLater(() -> {
                    String examId = entry.getKey();
                    String examTitle = entry.getValue().getSecond();
                    String examDate = entry.getValue().getFirst().format(dateTimeFormatter);
                    pendingExamsMap.put(examTitle, examId);
                    String examToAdd = examTitle + "-" + " Executed at: " + examDate;
                    pendingExamsObservableList.add(examToAdd);
                });
            }
        }
    }

    public String getPendingExamId(String title) {
        return pendingExamsMap.get(title);
    }

    @Override
    public void showPendingExamGrades(String examId) {
        setCurrentConcreteExamId(examId);
        ClientApp.sendRequest(new UncheckedExecutesOfConcreteRequest(examId));
    }

    @Override
    public void loadPendingExams() {
        ClientApp.sendRequest(new PendingExamsRequest());
    }

    @Override
    public void clearPendingExams() {
        pendingExamsObservableList.clear();
    }

    public LightExecutedExam getCurrentLightExecutedExam() {
        return currentLightExecutedExam;
    }

    public void setCurrentLightExecutedExam(LightExecutedExam currentLightExecutedExam) {
        this.currentLightExecutedExam = currentLightExecutedExam;
    }

    public byte[] getManualExamBytes() {
        return manualExamBytes;
    }

    public void setManualExamBytes(byte[] manualExamBytes) {
        this.manualExamBytes = manualExamBytes;
    }

    @Override
    public String getManualExamForReviewStudentId() {
        return manualExamForReviewStudentId;
    }

    public void setManualExamForReviewStudentId(String manualExamForReviewStudentId) {
        this.manualExamForReviewStudentId = manualExamForReviewStudentId;
    }

    public void saveWordFile(File filePath) {
        try {
            wordGenerator.saveWordFile(getManualExamBytes(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //send the data that the teacher inputs when reviewing a certain exam executed by a student
    public void submitExamReview(double grade, String notes, String reason, File manualExamFile) {

        if (!currentLightExecutedExam.isComputerized())
            currentLightExecutedExam.setManualExam(fileToByteArray(manualExamFile));
        else
            currentLightExecutedExam.setManualExam(null);

        currentLightExecutedExam.setGrade(grade);
        currentLightExecutedExam.setCommentsAfterCheck(notes);
        currentLightExecutedExam.setReasonsForChangeGrade(reason);
        currentLightExecutedExam.setChecked(true);
        ClientApp.sendRequest(new EvaluateExamRequest(currentLightExecutedExam));
    }

    public ObservableList<StudentExamType> getStudentsGradesToReview() {
        return studentsGradesToReview;
    }

    public void clearStudentsGradesToReview() {
        if (!studentsGradesToReview.isEmpty())
            studentsGradesToReview.clear();
    }


    @Subscribe
    public void handleUncheckedExecutesOfConcreteResponse(UncheckedExecutesOfConcreteResponse response) {
        if (response.getStatus() == 0) {
            Platform.runLater(() -> {
                //HashMap<ConcreteID,isComputerized>
                for (Map.Entry<String, Boolean> entry : response.getCheckedExamsList().entrySet()) {
                    String method = entry.getValue() ? "Computerized" : "Manual";
                    studentsGradesToReview.add(new StudentExamType(entry.getKey(), method));
                }
            });
        }
    }

    @Override
    public void reviewExam(String studentId) {
        ClientApp.sendRequest(new GetExecutedExamRequest(getCurrentConcreteExamId(), studentId));
    }

    @Subscribe
    public void handleGetExecutedExamResponse(GetExecutedExamResponse response) {
        if (response.getStatus() == 0) {
            setCurrentLightExecutedExam(response.getExam());
            setManualExamForReviewStudentId(response.getExam().getStudentID());
            if (!response.getExam().isComputerized())
                setManualExamBytes(response.getExam().getManualExam());

        }
    }

    @Subscribe
    public void handleEvaluateExamResponse(EvaluateExamResponse response) {
        if (response.getStatus() == 0) {
            EvaluateExamRequest request = (EvaluateExamRequest) response.getRequest();
            getStudentsGradesToReview().removeIf(type -> type.getId().equals(request.getExam().getStudentID()));
        }
    }

    public ObservableList<String> getObservableTimeExtensionRequestsList() {
        return ObservableTimeExtensionRequestsList;
    }

    @Subscribe
    public void handleTimeExtensionRequestNotifier(TimeExtensionRequestNotifier notifier) {
        setCurrentConcreteExamId(notifier.getExamId());
        Platform.runLater(() -> {
            String requestDescription = notifier.getExamId() + ": " + notifier.getExamTitle() + " " + notifier.getDurationInMinutes() +
                    "minutes request - " + notifier.getReasonForExtension();
            ObservableTimeExtensionRequestsList.add(requestDescription);
        });
    }

    @Override
    public void removeRequest(String selectedItem) {
        Platform.runLater(() -> getObservableTimeExtensionRequestsList().remove(selectedItem));
    }

    @Override
    public void rejectExtension(String reason) {
        ClientApp.sendRequest(new ConfirmTimeExtensionRequest(false, reason, 0, getCurrentConcreteExamId()));
    }

    @Override
    public void acceptExtension(String extension) {
        ClientApp.sendRequest(new ConfirmTimeExtensionRequest(true, "", Integer.parseInt(extension), getCurrentConcreteExamId()));

    }

    public ObservableList<StudentPastExam> getStudentPastExamsObservableList() {
        return studentPastExamsObservableList;
    }

    //for student
    public void loadPastExams() {
        ClientApp.sendRequest(new GetAllPastExamsRequest(currentCourseId));
    }

    @Subscribe
    public void handleGetAllPastExamsResponse(GetAllPastExamsResponse response) {
        studentPastExamsObservableList.clear();
        Platform.runLater(() -> {
            // hashMap: executedExamId, (title,grade)
            for (Map.Entry<String, Pair<String, Double>> entry : response.getExecutedExamsList().entrySet()) {
                String id = entry.getKey();
                String examDate = response.getExecutedExamsDates().get(id).format(dateTimeFormatter);
                String examTitle = entry.getValue().getFirst();
                Double grade = entry.getValue().getSecond();
                studentPastExamsObservableList.add(new StudentPastExam(examTitle, id, grade, examDate));
            }
        });
    }

    public void clearStudentPastExamsList() {
        Platform.runLater(studentPastExamsObservableList::clear);
    }

    @Override
    public void reviewStudentExam(String examId) {
        ClientApp.sendRequest(new GetExecutedExamRequest(examId, getId()));
    }

    @Override
    public void loadResults() {
        ClientApp.sendRequest(new TeacherGetAllPastExamsRequest(currentCourseId));
    }

    public ObservableList<String> getPastExamsResultsObservableList() {
        return pastExamsResultsObservableList;
    }

    @Subscribe
    public void handleTeacherGetAllPastExamsResponse(TeacherGetAllPastExamsResponse response) {
        Platform.runLater(() -> {
            clearTeacherPastExamsData();
            //hashMap: executedExamId, (date, title)
            for (Map.Entry<String, Pair<LocalDateTime, String>> entry : response.getConcreteExamsList().entrySet()) {
                String examId = entry.getKey();
                String date = entry.getValue().getFirst().format(dateTimeFormatter);
                String title = entry.getValue().getSecond();
                pastExamsMap.put(title, examId);
                String examToAdd = title + "-" + " Executed at: " + date;
                pastExamsResultsObservableList.add(examToAdd);
            }
        });
    }

    public String getExamIdFromTitle(String title) {
        return pastExamsMap.get(title);
    }
}
