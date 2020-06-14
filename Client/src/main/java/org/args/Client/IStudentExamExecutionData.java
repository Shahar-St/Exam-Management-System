package org.args.Client;

import LightEntities.LightExam;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;

public interface IStudentExamExecutionData {
    void storeAnswer(int questionNumber,int answerNumber);
    void displayExam();
    void submitExam();
    void raiseHand();
    LightExam getExamForStudentExecution();
    HashMap<Integer,Integer> getCorrectAnswersMap();
    void createManualTest(File path);
    void setManualExamFile(File manualExamFile);
    void setFinishedOnTime(boolean finishedOnTime);
    void submitAndQuit();
    LocalDateTime getExamForStudentExecutionInitDate();
    int getTimeExtensionDuration();
    boolean isTimeExtensionGranted();
    void setTimeExtensionGranted(boolean timeExtensionGranted);
}
