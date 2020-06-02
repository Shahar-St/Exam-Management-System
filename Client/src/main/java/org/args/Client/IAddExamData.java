package org.args.Client;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public interface IAddExamData {
    void addToExamQuestionsList(String question);

    void removeFromExamQuestionsList(String question);

    void showQuestionInfo(String questionId);

    void done();

    void cancelExamAddition();

    ObservableList<String> getObservableQuestionsList();

    ObservableList<String> getObservableExamQuestionsList();

    void loadQuestionDetails(String questionId);

    String getCurrentCourse();

    String getCurrentExamTitle();

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

    void setViewMode(String viewMode);

    boolean checkQuestionScoringList();

    StringProperty currentExamTotalScoreProperty();

    void setCurrentExamTotalScore(String currentExamTotalScore);


}
