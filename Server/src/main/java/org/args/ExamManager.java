package org.args;

import Notifiers.ConfirmTimeExtensionNotifier;
import Notifiers.ExamEndedNotifier;
import Notifiers.RaiseHandNotifier;
import Notifiers.TimeExtensionRequestNotifier;
import org.args.OCSF.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExamManager {

    private static ConnectionToClient dean;
    private ConnectionToClient teacher;
    private final Map<String, ConnectionToClient> students = new HashMap<>();

    private static final List<TimeExtensionRequestNotifier> deanNotifications = new ArrayList<>();

    private final static Lock deanLock = new ReentrantLock();

    public ExamManager(ConnectionToClient teacher) {
        this.teacher = teacher;
    }

    //Group setters and getters
    public static void setDean(ConnectionToClient dean) {
        ExamManager.dean = dean;
    }

    public ConnectionToClient getTeacher() {
        return teacher;
    }
    public void setTeacher(ConnectionToClient teacher) {
        this.teacher = teacher;
    }

    public Map<String, ConnectionToClient> getStudents() {
        return students;
    }

    public static Lock getDeanLock() {
        return deanLock;
    }

    public static void askForTimeExtension(TimeExtensionRequestNotifier notifier) {

        deanLock.lock();
        if (dean != null && dean.isAlive())
        {
            try
            {
                dean.sendToClient(notifier);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
            deanNotifications.add(notifier);
        deanLock.unlock();
    }

    public static void notifyDean() {

        deanLock.lock();
        for (TimeExtensionRequestNotifier notifier : deanNotifications)
        {
            try
            {
                dean.sendToClient(notifier);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        deanNotifications.clear();
        deanLock.unlock();

    }

    public void respondToTimeExtension(ConfirmTimeExtensionNotifier notifier) {
        try
        {
            teacher.sendToClient(notifier);
            notifier.setDeanResponse(null);
            for (Map.Entry<String, ConnectionToClient> entry : students.entrySet())
            {
                ConnectionToClient client = entry.getValue();
                client.sendToClient(notifier);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void notifyTeacherAboutRaisedHand(RaiseHandNotifier raiseHandNotifier) {
        try
        {
            teacher.sendToClient(raiseHandNotifier);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void notifyAboutExamEnd() {

        ExamEndedNotifier notifier = new ExamEndedNotifier();
        for (Map.Entry<String, ConnectionToClient> entry : students.entrySet())
        {
            try
            {
                entry.getValue().sendToClient(notifier);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void notifyAllSubmittedExamEnd() {

        notifyAboutExamEnd();
        try
        {
            teacher.sendToClient(new ExamEndedNotifier());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
