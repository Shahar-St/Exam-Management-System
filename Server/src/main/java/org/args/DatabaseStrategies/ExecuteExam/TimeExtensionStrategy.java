package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.ConfirmTimeExtensionRequest;
import DatabaseAccess.Requests.ExecuteExam.TimeExtensionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.ConfirmTimeExtensionResponse;
import DatabaseAccess.Responses.ExecuteExam.ExecuteExamResponse;
import DatabaseAccess.Responses.ExecuteExam.TimeExtensionResponse;
import Notifiers.TimeExtensionRequestNotifier;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.Dean;
import org.args.Entities.Exam;
import org.args.Entities.Teacher;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

//TODO
public class TimeExtensionStrategy extends DatabaseStrategy implements IExamInProgress {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {
        //get a ConfirmTime request from dean, send TimeExtension response to teacher.
        ConfirmTimeExtensionRequest cteRequest = (ConfirmTimeExtensionRequest) request;
        if (client.getInfo("userName") == null)
            return new TimeExtensionResponse(UNAUTHORIZED, request, false, "", 0);

        return new TimeExtensionResponse(SUCCESS, request, cteRequest.isAccepted(), cteRequest.getDeanResponse(), cteRequest.getAuthorizedTimeExtension());
        //add more error handling if needed - Ronnie

    }

    @Override
    public void handle(DatabaseRequest request, DatabaseResponse response, Map<ConcreteExam, ExamManager> examManagers,
                       ConnectionToClient client, Session session) {

        TimeExtensionRequest request1 = (TimeExtensionRequest) request;
        ConcreteExam exam = getTypeById(ConcreteExam.class, request1.getExamId(), session);
        ExamManager manager = examManagers.get(exam);

        manager.askForTimeExtension(new TimeExtensionRequestNotifier());
    }
}
