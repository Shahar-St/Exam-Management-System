package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.TimeExtensionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.ConfirmTimeExtensionResponse;
import DatabaseAccess.Responses.ExecuteExam.ExecuteExamResponse;
import DatabaseAccess.Responses.ExecuteExam.TimeExtensionResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
//TODO
public class ConfirmTimeExtensionStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {
        //get a TimeExtension request from teacher, send a ConfirmTime response to dean.
        TimeExtensionRequest timeRequest = (TimeExtensionRequest)request;
        if (client.getInfo("userName") == null)
            return new ConfirmTimeExtensionResponse(UNAUTHORIZED,request,"",0,"");
        return new ConfirmTimeExtensionResponse(SUCCESS,request,timeRequest.getExamId(),timeRequest.getDurationInMinutes(),timeRequest.getReasonForExtension());
    }
}
