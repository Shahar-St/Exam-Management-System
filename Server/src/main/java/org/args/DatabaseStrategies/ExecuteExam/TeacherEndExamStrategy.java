package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.TeacherEndExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.TeacherEndExamResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class TeacherEndExamStrategy extends DatabaseStrategy implements IExamInProgress {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {

        TeacherEndExamRequest request1 = (TeacherEndExamRequest) request;


        if (client.getInfo("userName") == null)
            return new TeacherEndExamResponse(UNAUTHORIZED, request1);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getConcreteExamId(), session);
        if (concreteExam == null)
            return new TeacherEndExamResponse(ERROR2, request);

        return new TeacherEndExamResponse(SUCCESS, request1);
    }

    @Override
    public void handle(DatabaseRequest request, DatabaseResponse response,
                       Map<Integer, ExamManager> examManagers, ConnectionToClient client, Session session) {

        TeacherEndExamRequest request1 = (TeacherEndExamRequest) request;

        ExamManager manager = examManagers.get(Integer.parseInt(request1.getConcreteExamId()));
        manager.notifyAboutExamEnd();
    }
}
