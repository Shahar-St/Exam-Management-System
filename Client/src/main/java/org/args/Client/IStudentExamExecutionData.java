package org.args.Client;

import LightEntities.LightExam;

import java.util.HashMap;
import java.util.List;

public interface IStudentExamExecutionData {
    void storeAnswer(int questionNumber,int answerNumber);
    void displayExam();
    void submitExam();
    void raiseHand();
    LightExam getExamForStudentExecution();
    HashMap<Integer,Integer> getCorrectAnswersMap();
    void createManualTest();
}
