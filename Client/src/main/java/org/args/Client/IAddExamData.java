package org.args.Client;

import javafx.collections.ObservableList;

public interface IAddExamData {
    void addToExamQuestionsList (String question);
    void removeFromExamQuestionsList (String question);
    void showQuestionInfo(String questionId);
    void done();
    void cancel();
    ObservableList<String> getObservableQuestionsList();
    ObservableList<String> getObservableExamQuestionsList();
    void saveQuestionDetails(String questionId);
    String getCurrentCourse();


}
