package org.args.Client;

import javafx.collections.ObservableList;

import java.util.List;

@SuppressWarnings("unused")
public interface IQuestionData {

    String getQuestionId();

    String getLastModified();

    String getAuthor();

    String getContent();

    List<String> getAnswers();

    int getCorrectAnswer();

    void setCorrectAnswer(int correctAnswer);

    ObservableList<String> getChoiceItems();

    void setChoiceItems(ObservableList<String> choiceItems);

    boolean isCreating();

    void setCreating(boolean creating);

    String getUserName();

    void saveQuestion(String questionId, String answer_1, String answer_2, String answer_3, String answer_4, String newContent);

    void deleteQuestion(String questionId);

}
