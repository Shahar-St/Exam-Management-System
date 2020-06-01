package org.args.Client;

import javafx.collections.ObservableList;

public interface IAddExamData {
    void addToExamQuestionsList(String question);

    void removeFromExamQuestionsList(String question);

    void showQuestionInfo(String questionId);

    void done();

    void cancelExamAddition();

    ObservableList<String> getObservableQuestionsList();

    ObservableList<String> getObservableExamQuestionsList();

    void saveQuestionDetails(String questionId);

    String getCurrentCourse();

    public String getCurrentExamTitle();

    public void setCurrentExamTitle(String currentExamTitle);

    public String getCurrentExamTeacherNotes();

    public void setCurrentExamTeacherNotes(String currentExamTeacherNotes);

    public String getCurrentExamStudentNotes();

    public void setCurrentExamStudentNotes(String currentExamStudentNotes);

    double calcQuestionsScoringListValue();

    void initQuestionsScoringList();

    ObservableList<String> getObservableQuestionsScoringList();


}
