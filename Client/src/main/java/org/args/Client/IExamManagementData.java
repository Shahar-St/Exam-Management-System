package org.args.Client;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Set;

public interface IExamManagementData {
    List getExams(String courseName);
    Set<String> getSubjects();
    List getCourses(String subjectName);

    ObservableList<String> getObservableExamList();
    boolean dataWasAlreadyInitialized();
    String getCurrentSubject();
    void setCurrentSubject(String subjectName);
    String getCurrentCourseId();
    void setCurrentCourseId(String courseName);
    void fillExamList(String courseName);
    BooleanProperty isCourseSelected();
    void setCourseSelected(boolean courseSelected);
    List<String> getCoursesOfSubject(String subjectName);
    void viewExam(String examId);
    void clearExamList();
    void fillQuestionsList(String courseName);
    void executeExam(String examId, String examCode);
    String getViewMode();
    void setViewMode(String viewMode);
    void setCurrentExamId(String examId);
}
