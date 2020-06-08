package org.args.Client;

import LightEntities.LightExam;

public interface IStudentExamExecutionData {
    void storeAnswer(int questionNumber,int answerNumber);
    void displayExam();
    void submitExam();
    void raiseHand();
    LightExam getExamForStudentExecution();
}
