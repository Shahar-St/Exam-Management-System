package org.args.Client;

import LightEntities.LightQuestion;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.List;

public interface IExamData {
    void saveExam(String title,int duration,String teacherNotes,String studentNotes ,List<String> questionList,List<Double> questionsScoreList ,String examId);

    List<LightQuestion> getLightQuestionListFromCurrentExam();

    String getCurrentExamTitle();

    List<Double> getCurrentExamQuestionsScoreList();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isExamDeletable();

    void deleteExam();

    void startExamEdit();

    void addToExamQuestionsList(String question);

    void removeFromExamQuestionsList(String question);

    ObservableList<String> getObservableQuestionsList();

    ObservableList<String> getObservableExamQuestionsList();

    void loadQuestionDetails(String questionId);

    String getCurrentCourseId();

    StringProperty currentExamTitleProperty();

    String getCurrentExamDuration();

    StringProperty currentExamDurationProperty();

    String getCurrentExamTeacherNotes();

    StringProperty currentExamTeacherNotesProperty();

    String getCurrentExamStudentNotes();

    StringProperty currentExamStudentNotesProperty();

    void setCurrentExamTitle(String currentExamTitle);

    void setCurrentExamTeacherNotes(String currentExamTeacherNotes);

    void setCurrentExamStudentNotes(String currentExamStudentNotes);

    void setCurrentExamDuration(String duration);

    double calcQuestionsScoringListValue();

    void initQuestionsScoringList();

    ObservableList<String> getObservableQuestionsScoringList();

    String getViewMode();

    boolean checkQuestionScoringList();

    StringProperty currentExamTotalScoreProperty();

    void setCurrentExamTotalScore(String currentExamTotalScore);

    void clearDetailsScreen();

    String getCurrentExamId();

    void fillQuestionsList(String courseId);

    String getCurrentCourseName();

}
