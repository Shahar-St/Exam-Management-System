package org.args.DatabaseStrategies.Review;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ReviewExam.UncheckedExecutesOfConcreteRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ReviewExam.CheckedExamResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.ExecutedExam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
//TODO
public class CheckedExamStrartegy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {
        UncheckedExecutesOfConcreteRequest checkedRequest = (UncheckedExecutesOfConcreteRequest) request;
        if (client.getInfo("userName") == null)
            return new CheckedExamResponse(UNAUTHORIZED,request,null);
        ConcreteExam exam = getTypeById(ConcreteExam.class,checkedRequest.getExamId(),session);
        HashMap<String, Boolean> result = new HashMap<>();
        for (ExecutedExam executed : exam.getExecutedExamsList())
        {
            result.put(executed.getStudent().getSocialId(),executed.isComputerized());
        }
        return new CheckedExamResponse(SUCCESS,request,result);
    }
}
