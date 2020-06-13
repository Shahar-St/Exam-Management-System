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

//TODO
public class RaiseHandStrategy extends DatabaseStrategy implements IExamInProgress {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {

        RaiseHandRequest raiseHandRequest = (RaiseHandRequest) request;
        if (client.getInfo("userName") == null)
            return new RaiseHandResponse(UNAUTHORIZED, raiseHandRequest,"");
        Student student = (Student) getUser((String) client.getInfo("userName"), session);
        return new RaiseHandResponse(SUCCESS, raiseHandRequest,student.getFullName());
    }

    public void handle(DatabaseRequest request, DatabaseResponse response, Map<Integer, ExamManager> examManagers,
                       ConnectionToClient client, Session session) {
        RaiseHandRequest raiseHandRequest = (RaiseHandRequest) request;
        Student student = (Student) getUser((String) client.getInfo("userName"), session);
        ExamManager manager = examManagers.get(student.getCurrentlyExecutedID());
        manager.notifyTeacherAboutRaisedHand(new RaiseHandNotifier(student.getFullName()));
    }
}
