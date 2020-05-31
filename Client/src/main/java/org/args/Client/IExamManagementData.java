package org.args.Client;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Set;

public interface IExamManagementData {
    List getExams(String courseName);
    void deleteExam(String examId);
    void addExam();
    Set<String> getSubjects();
    List getCourses(String subjectName);
    void viewExam(String examId);
    public ObservableList<String> getObservableExamList();
    boolean dataWasAlreadyInitialized();
    String getCurrentSubject();
    void setCurrentSubject(String subjectName);
    String getCurrentCourse();
    void setCurrentCourse(String courseName);
    void fillExamList(String courseName);
    BooleanProperty isCourseSelected();
    void setCourseSelected(boolean courseSelected);
    List<String> getCoursesOfSubject(String subjectName);
    void saveExamDetails(String examId);
    void clearExamList();
    void fillQuestionsList(String courseName);
}
