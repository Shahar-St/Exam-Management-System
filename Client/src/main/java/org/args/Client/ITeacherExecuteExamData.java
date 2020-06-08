package org.args.Client;

import javafx.collections.ObservableList;

public interface ITeacherExecuteExamData {
    
    public String getCurrentExecutedExamTitle();
    public String getCurrentExecutedExamLaunchTime();
    void sendTimeExtensionRequest(int parseInt, String text);
    public ObservableList<String> getCurrentHandsRaised();

    void solveRaisedHand(String currentStudentName);
}
