package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.ConfirmTimeExtensionRequest;
import DatabaseAccess.Requests.ExecuteExam.TimeExtensionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.ConfirmTimeExtensionResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.Map;

//get a TimeExtension request from teacher, send a ConfirmTime response to dean.
//TODO
public class ConfirmTimeExtensionStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   Map<String, ConnectionToClient> loggedInUsers) {

        ConfirmTimeExtensionRequest request1 = (ConfirmTimeExtensionRequest) request;

        if (client.getInfo("userName") == null)
            return new ConfirmTimeExtensionResponse(UNAUTHORIZED, request);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getExamId(), session);
        if (concreteExam == null)
            return new ConfirmTimeExtensionResponse(ERROR2, request);




        return new ConfirmTimeExtensionResponse(SUCCESS, request, timeRequest.getExamId(), timeRequest.getDurationInMinutes(), timeRequest.getReasonForExtension());
    }
}
