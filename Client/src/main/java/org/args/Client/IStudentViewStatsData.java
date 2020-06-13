package org.args.Client;

import javafx.collections.ObservableList;
import org.args.GUI.StudentPastExam;

import java.util.List;
import java.util.Set;

public interface IStudentViewStatsData {
    void loadPastExams();

    public ObservableList<StudentPastExam> getStudentPastExamsObservableList();

    public void loadSubjects();

    public boolean dataWasAlreadyInitialized();

    Set<String> getSubjects();

    List<String> getCoursesOfSubject(String subject);

    String getCurrentSubject();

    void setCurrentSubject(String currentSubject);

    String getCurrentCourseId();

    void setCurrentCourseId(String currentCourseId);

    void clearStudentPastExamsList();
}
