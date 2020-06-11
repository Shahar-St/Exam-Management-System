package org.args;

import Notifiers.ConfirmTimeExtensionNotifier;
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
    private Map<String, ConnectionToClient> students = new HashMap<>();

    private static List<TimeExtensionRequestNotifier> deanNotifications = new ArrayList<>();
    private ConfirmTimeExtensionNotifier confirmTimeExtensionNotifier = null;

    private final static Lock deanLock = new ReentrantLock();

    public ExamManager(ConnectionToClient teacher) {
        this.teacher = teacher;
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
    public Map<String, ConnectionToClient> getStudents() {
        return students;
    }
    public void setStudents(Map<String, ConnectionToClient> students) {
        this.students = students;
    }
    public ConfirmTimeExtensionNotifier getConfirmTimeExtensionNotifier() {
        return confirmTimeExtensionNotifier;
    }
    public void setConfirmTimeExtensionNotifier(ConfirmTimeExtensionNotifier confirmTimeExtensionNotifier) {
        this.confirmTimeExtensionNotifier = confirmTimeExtensionNotifier;
    }
    public static List<TimeExtensionRequestNotifier> getDeanNotifications() {
        return deanNotifications;
    }
    public static void setDeanNotifications(List<TimeExtensionRequestNotifier> deanNotifications) {
        ExamManager.deanNotifications = deanNotifications;
    }
    public static Lock getDeanLock() {
        return deanLock;
    }

    //TODO
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
}
