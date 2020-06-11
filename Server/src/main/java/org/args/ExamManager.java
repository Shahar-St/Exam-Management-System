package org.args;

import Notifiers.ConfirmTimeExtensionNotifier;
import Notifiers.TimeExtensionRequestNotifier;
import org.args.Entities.ConcreteExam;
import org.args.OCSF.ConnectionToClient;

import java.util.List;

public class ExamManager {

    private final ConcreteExam exam;

    private static ConnectionToClient dean;
    private ConnectionToClient teacher;
    private List<ConnectionToClient> students;

    private TimeExtensionRequestNotifier timeExtensionRequestNotifier = null;
    private ConfirmTimeExtensionNotifier confirmTimeExtensionNotifier = null;


    public ExamManager(ConcreteExam exam, ConnectionToClient teacher) {
        this.exam = exam;
        this.teacher = teacher;
    }

    public ConcreteExam getExam() {
        return exam;
    }
    public static ConnectionToClient getDean() {
        return dean;
    }
    public static void setDean(ConnectionToClient dean) {
        ExamManager.dean = dean;
    }
    public ConnectionToClient getTeacher() {
        return teacher;
    }
    public void setTeacher(ConnectionToClient teacher) {
        this.teacher = teacher;
    }
    public List<ConnectionToClient> getStudents() {
        return students;
    }
    public void setStudents(List<ConnectionToClient> students) {
        this.students = students;
    }

    public TimeExtensionRequestNotifier getTimeExtensionRequestNotifier() {
        return timeExtensionRequestNotifier;
    }
    public void setTimeExtensionRequestNotifier(TimeExtensionRequestNotifier timeExtensionRequestNotifier) {
        this.timeExtensionRequestNotifier = timeExtensionRequestNotifier;
    }
    public ConfirmTimeExtensionNotifier getConfirmTimeExtensionNotifier() {
        return confirmTimeExtensionNotifier;
    }
    public void setConfirmTimeExtensionNotifier(ConfirmTimeExtensionNotifier confirmTimeExtensionNotifier) {
        this.confirmTimeExtensionNotifier = confirmTimeExtensionNotifier;
    }

    //TODO
    public void askForTimeExtension(TimeExtensionRequestNotifier notifier) {

    }

    public void respondToTimeExtension(ConfirmTimeExtensionNotifier notifier) {

    }
}
