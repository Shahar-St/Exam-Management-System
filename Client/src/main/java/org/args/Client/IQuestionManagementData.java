package org.args.Client;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Set;

public interface IQuestionManagementData {

    Set<String> getSubjects();

    String getPermission();

    List<String> getCoursesOfSubject(String subject);

    String getCurrentSubject();

    void setCurrentSubject(String currentSubject);

    String getCurrentCourseId();

    void setCurrentCourseId(String currentCourseId);

    boolean dataWasAlreadyInitialized();

    void prepareAddQuestion();

    BooleanProperty isCourseSelected();

    void setCourseSelected(boolean courseSelected);

    String getCurrentCourseName();
    void setCurrentCourseName(String currentCourseName);

    //questions list data


    ObservableList<String> getObservableQuestionsList();


    void clearQuestionsList();

    void fillQuestionsList(String courseId);

    void loadQuestionDetails(String questionId);

    void setCurrentQuestionId(String questionId);
}
