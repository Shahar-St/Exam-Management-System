package org.args.DatabaseStrategies.Review;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ReviewExam.PendingExamsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ReviewExam.PendingExamsResponse;
import Util.Pair;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.ExecutedExam;
import org.args.Entities.Teacher;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */

public class PendingExamsStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        PendingExamsRequest pendingExamsRequest = (PendingExamsRequest) request;

        if (client.getInfo("userName") == null)
            return new PendingExamsResponse(UNAUTHORIZED, pendingExamsRequest);

        Teacher teacher = (Teacher) getUser((String) client.getInfo("userName"), session);

        HashMap<String, Pair<LocalDateTime, String>> map = new HashMap<>();

        boolean needToCheck;
        for (ConcreteExam concreteExam : teacher.getConcreteExamsList())
        {
            List<ExecutedExam> executedExamList = concreteExam.getExecutedExamsList();
            needToCheck = true;
            for (int i = 0; i < executedExamList.size() && needToCheck; i++)
            {
                if ((!executedExamList.get(i).isChecked()) && (executedExamList.get(i).isSubmitted()))
                {
                    map.put(String.valueOf(concreteExam.getId()),
                            new Pair<>(concreteExam.getExamForExecutionInitDate(), concreteExam.getExam().getTitle()));
                    needToCheck = false;
                }
            }
        }

        return new PendingExamsResponse(SUCCESS, pendingExamsRequest, map);
    }
}
