package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.ConfirmTimeExtensionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.ConfirmTimeExtensionResponse;
import Notifiers.ConfirmTimeExtensionNotifier;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * status dictionary:
 * 0 - success (message sent successfully)
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class ConfirmTimeExtensionStrategy extends DatabaseStrategy implements IExamInProgress {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {

        ConfirmTimeExtensionRequest request1 = (ConfirmTimeExtensionRequest) request;

        if (client.getInfo("userName") == null)
            return new ConfirmTimeExtensionResponse(UNAUTHORIZED, request);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getExamId(), session);
        if (concreteExam == null)
            return new ConfirmTimeExtensionResponse(ERROR2, request);

        return new ConfirmTimeExtensionResponse(SUCCESS, request);
    }

    @Override
    public void handle(DatabaseRequest request, DatabaseResponse response, Map<Integer, ExamManager> examManagers,
                       ConnectionToClient client, Session session) {

        ConfirmTimeExtensionRequest request1 = (ConfirmTimeExtensionRequest) request;
        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getExamId(), session);
        ExamManager manager = examManagers.get(concreteExam.getId());

        manager.respondToTimeExtension(new ConfirmTimeExtensionNotifier(request1.getDeanResponse(),
                request1.getAuthorizedTimeExtension(), request1.isAccepted()));
    }
}
