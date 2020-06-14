package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.RaiseHandRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.RaiseHandResponse;
import Notifiers.RaiseHandNotifier;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ExecutedExam;
import org.args.Entities.Student;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */

public class RaiseHandStrategy extends DatabaseStrategy implements IExamInProgress {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {

        RaiseHandRequest raiseHandRequest = (RaiseHandRequest) request;

        if (client.getInfo("userName") == null)
            return new RaiseHandResponse(UNAUTHORIZED, raiseHandRequest);

        return new RaiseHandResponse(SUCCESS, raiseHandRequest);
    }

    public void handle(DatabaseRequest request, DatabaseResponse response, Map<Integer, ExamManager> examManagers,
                       ConnectionToClient client, Session session) {

        Student student = (Student) getUser((String) client.getInfo("userName"), session);
        ExecutedExam executedExam =
                getTypeById(ExecutedExam.class, String.valueOf(student.getCurrentlyExecutedID()), session);
        ExamManager manager = examManagers.get(executedExam.getConcreteExam().getId());
        manager.notifyTeacherAboutRaisedHand(new RaiseHandNotifier(student.getFullName()));
    }
}
