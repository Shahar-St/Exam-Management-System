package org.args.Client;

import LightEntities.LightExam;

import java.util.List;

public interface IStudentExamExecutionData {
    void storeAnswer(int questionNumber,int answerNumber);
    void displayExam();
    void submitExam();
    void raiseHand();
    LightExam getExamForStudentExecution();
    List<Integer> getCorrectAnswersList();
}
