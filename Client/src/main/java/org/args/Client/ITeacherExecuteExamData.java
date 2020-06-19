package org.args.Client;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public interface ITeacherExecuteExamData {

    String getCurrentExecutedExamTitle();

    String getCurrentExecutedExamLaunchTime();

    void sendTimeExtensionRequest(int parseInt, String text);

    ObservableList<String> getCurrentHandsRaised();

    void solveRaisedHand(String currentStudentName);

    StringProperty getCurrentExecutedExamEndTimeProperty();

    LocalDateTime getCurrentExecutedExamEndLocalDateTime();

    void endExam();

    boolean hasEnded();

    void setHasEnded(boolean bol);
}
