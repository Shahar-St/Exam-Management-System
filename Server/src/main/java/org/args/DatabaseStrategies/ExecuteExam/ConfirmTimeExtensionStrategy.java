package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.ConfirmTimeExtensionRequest;
import DatabaseAccess.Requests.ExecuteExam.TimeExtensionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.ConfirmTimeExtensionResponse;
import DatabaseAccess.Responses.ExecuteExam.ExecuteExamResponse;
import DatabaseAccess.Responses.ExecuteExam.TimeExtensionResponse;
import Notifiers.ConfirmTimeExtensionNotifier;
import Notifiers.TimeExtensionRequestNotifier;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

//TODO
public class ConfirmTimeExtensionStrategy extends DatabaseStrategy implements IExamInProgress {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {
        //get a TimeExtension request from teacher, send a ConfirmTime response to dean.
        TimeExtensionRequest timeRequest = (TimeExtensionRequest) request;
        if (client.getInfo("userName") == null)
            return new ConfirmTimeExtensionResponse(UNAUTHORIZED, request, "", 0, "");
        return new ConfirmTimeExtensionResponse(SUCCESS, request, timeRequest.getExamId(), timeRequest.getDurationInMinutes(), timeRequest.getReasonForExtension());
    }

    @Override
    public void handle(DatabaseRequest request, DatabaseResponse response, Map<ConcreteExam, ExamManager> examManagers,
                       ConnectionToClient client, Session session) {

        ConfirmTimeExtensionRequest request1 = (ConfirmTimeExtensionRequest) request;
        ConcreteExam exam = getTypeById(ConcreteExam.class, request1.getExamId(), session);
        ExamManager manager = examManagers.get(exam);

        manager.respondToTimeExtension(new ConfirmTimeExtensionNotifier());
    }
}
