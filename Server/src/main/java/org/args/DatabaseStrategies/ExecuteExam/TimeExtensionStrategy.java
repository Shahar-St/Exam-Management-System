package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.TimeExtensionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.ConfirmTimeExtensionResponse;
import DatabaseAccess.Responses.ExecuteExam.TimeExtensionResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.io.IOException;
import java.util.Map;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 * 3 - dean isn't logged in
 * 4 - error in communication with dean
 */

public class TimeExtensionStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, Map<String,
            ConnectionToClient> loggedInUsers) {

        TimeExtensionRequest request1 = (TimeExtensionRequest) request;

        if (client.getInfo("userName") == null)
            return new TimeExtensionResponse(UNAUTHORIZED, request);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getExamId(), session);
        if (concreteExam == null)
            return new TimeExtensionResponse(ERROR2, request);

        ConnectionToClient dean = loggedInUsers.get("DeanConnection");
        if (dean == null || !dean.isAlive())
            return new TimeExtensionResponse(ERROR3, request);

        ConfirmTimeExtensionResponse response = new ConfirmTimeExtensionResponse(SUCCESS, request,
                String.valueOf(concreteExam.getId()), request1.getDurationInMinutes(), request1.getExamId());
        try
        {
            dean.sendToClient(response);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return new TimeExtensionResponse(ERROR4, request1);
        }
        return null;
    }

}
