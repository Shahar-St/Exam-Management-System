package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.RaiseHandRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.ConfirmTimeExtensionResponse;
import DatabaseAccess.Responses.ExecuteExam.RaiseHandResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

public class RaiseHandStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {
        RaiseHandRequest handRequest = (RaiseHandRequest)request;
        if (client.getInfo("userName") == null)
            return new RaiseHandResponse(UNAUTHORIZED,request,"");
        String studentName = client.getInfo("firstName") + " " + client.getInfo("lastName");

        return new RaiseHandResponse(SUCCESS,request,studentName);
        //add more error handling if needed - RONNIE
    }
}
