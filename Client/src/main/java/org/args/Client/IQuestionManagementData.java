package org.args.Client;

import Util.Pair;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface IQuestionManagementData {

    void setSubjectsAndCourses(HashMap<String, HashMap<String, String>> mapFromResponse);

    Set<String> getSubjects();

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


    void generateQuestionDescriptors(HashMap<String, Pair<LocalDateTime, String>> questionList);

    ObservableList<String> getObservableQuestionsList();


    void clearQuestionsList();

    void fillQuestionsList(String courseId);

    void loadQuestionDetails(String questionId);

    void setCurrentQuestionId(String questionId);
}
