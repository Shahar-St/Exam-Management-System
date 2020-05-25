package org.args.Client;

import javafx.collections.ObservableList;

import java.util.List;

public interface IEditQuestionScreenData {

    String getCurrentCourse();

    String getQuestionId();

    void setQuestionId(String questionId);

    String getLastModified();

    void setLastModified(String lastModified);

    String getAuthor();

    void setAuthor(String author);

    String getContent();

    void setContent(String content);

    List<String> getAnswers();

    void setAnswers(List<String> answers);

    int getCorrectAnswer();

    void setCorrectAnswer(int correctAnswer);

    ObservableList<String> getChoiceItems();

    void setChoiceItems(ObservableList<String> choiceItems);

    void saveQuestion(String questionId, String answer_1, String answer_2, String answer_3, String answer_4, String newContent);
}
