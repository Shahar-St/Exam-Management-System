package org.args.Client;

import LightEntities.LightExecutedExam;
import javafx.collections.ObservableList;
import org.args.GUI.StudentPastExam;

import java.util.List;
import java.util.Set;

public interface IViewPastExamsData {
    void loadPastExams();

    ObservableList<StudentPastExam> getStudentPastExamsObservableList();

    boolean dataWasAlreadyInitialized();

    Set<String> getSubjects();

    List<String> getCoursesOfSubject(String subject);

    String getCurrentSubject();

    void setCurrentSubject(String currentSubject);

    String getCurrentCourseId();

    void setCurrentCourseId(String currentCourseId);

    void clearStudentPastExamsList();

    void setCourseSelected(boolean b);

    LightExecutedExam getCurrentLightExecutedExam();

    void reviewStudentExam(String examId);

    String getCurrentCourseName();

    void setCurrentCourseName(String currentCourseName);
}
