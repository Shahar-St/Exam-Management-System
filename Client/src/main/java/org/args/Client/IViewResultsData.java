package org.args.Client;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.args.GUI.StudentGrade;

import java.util.List;
import java.util.Set;

public interface IViewResultsData {

    Set<String> getSubjects();

    boolean dataWasAlreadyInitialized();

    List<String> getCoursesOfSubject(String subject);

    String getCurrentSubject();

    void setCurrentSubject(String currentSubject);

    String getCurrentCourseId();

    void setCurrentCourseId(String currentCourseId);

    void loadResults();

    ObservableList<String> getPastExamsResultsObservableList();

    void setCourseSelected(boolean courseSelected);

    ObservableList<StudentGrade> getStudentGradesObservableList();

    void clearStudentGradesList();

    void showGradesOf(String examId);

    StringProperty currentExamTitleProperty();

    void setCurrentExamTitle(String currentExamTitle);

    String getExamIdFromTitle(String examId);

    void clearTeacherPastExamsData();

    String getCurrentCourseName();

    void setCurrentCourseName(String currentCourseName);
}
