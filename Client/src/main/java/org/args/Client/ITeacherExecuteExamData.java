package org.args.Client;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public interface ITeacherExecuteExamData {
    
    String getCurrentExecutedExamTitle();
    String getCurrentExecutedExamLaunchTime();
    void sendTimeExtensionRequest(int parseInt, String text);
    ObservableList<String> getCurrentHandsRaised();
    void solveRaisedHand(String currentStudentName);
    StringProperty getCurrentExecutedExamEndTimeProperty();
}
