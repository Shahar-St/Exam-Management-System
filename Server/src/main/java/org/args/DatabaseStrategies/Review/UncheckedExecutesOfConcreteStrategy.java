package org.args.DatabaseStrategies.Review;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ReviewExam.UncheckedExecutesOfConcreteRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ReviewExam.UncheckedExecutesOfConcreteResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.ExecutedExam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
//TODO
public class UncheckedExecutesOfConcreteStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        UncheckedExecutesOfConcreteRequest uncheckedExecutesOfConcreteRequest =
                                    (UncheckedExecutesOfConcreteRequest) request;

        if (client.getInfo("userName") == null)
            return new UncheckedExecutesOfConcreteResponse(UNAUTHORIZED, uncheckedExecutesOfConcreteRequest, null);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class,
                                    uncheckedExecutesOfConcreteRequest.getExamId(), session);

        HashMap<String, Boolean> map = new HashMap<>();
        for (ExecutedExam executedExam : concreteExam.getExecutedExamsList())
        {
            map.put(executedExam.getStudent().getSocialId(), executedExam.isComputerized());
        }

        return new UncheckedExecutesOfConcreteResponse(SUCCESS, uncheckedExecutesOfConcreteRequest, map);
    }
}
