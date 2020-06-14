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

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class UncheckedExecutesOfConcreteStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        UncheckedExecutesOfConcreteRequest request1 = (UncheckedExecutesOfConcreteRequest) request;

        if (client.getInfo("userName") == null)
            return new UncheckedExecutesOfConcreteResponse(UNAUTHORIZED, request1);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getExamId(), session);
        if (concreteExam == null)
            return new UncheckedExecutesOfConcreteResponse(ERROR2, request);

        HashMap<String, Boolean> map = new HashMap<>();
        for (ExecutedExam executedExam : concreteExam.getExecutedExamsList())
        {
            if (!executedExam.isChecked() && executedExam.isSubmitted())
                map.put(executedExam.getStudent().getSocialId(), executedExam.isComputerized());
        }

        return new UncheckedExecutesOfConcreteResponse(SUCCESS, request1, map);
    }
}
