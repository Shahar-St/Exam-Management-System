package org.args.Client;

import javafx.collections.ObservableList;
import org.args.GUI.StudentGrade;
import org.args.GUI.StudentPastExam;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ITeacherViewStatsData {
    List getTeacherExams();

    Set<String> getSubjects();

    void viewExamStatistics(String examId);


    void loadPastExams();

    public ObservableList<StudentPastExam> getStudentPastExamsObservableList();

    public void loadSubjects();

    public boolean dataWasAlreadyInitialized();

    List<String> getCoursesOfSubject(String subject);

    String getCurrentSubject();

    void setCurrentSubject(String currentSubject);

    String getCurrentCourseId();

    void setCurrentCourseId(String currentCourseId);

    void clearStudentPastExamsList();

    void loadResults();

    public ObservableList<String> getPastExamsResultsObservableList();

    void setCourseSelected(boolean courseSelected);

    ObservableList<StudentGrade> getStudentGradesObservableList();

    void clearStudentGradesList();

    void showGradesOf(String examId);
}
