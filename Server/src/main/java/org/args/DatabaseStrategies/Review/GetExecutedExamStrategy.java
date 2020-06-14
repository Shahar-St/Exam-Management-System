package org.args.DatabaseStrategies.Review;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ReviewExam.GetExecutedExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ReviewExam.GetExecutedExamResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.ExecutedExam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - concrete exam wasn't found
 * 3 - didn't find student's exam
 */

public class GetExecutedExamStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        GetExecutedExamRequest getExecutedExamRequest = (GetExecutedExamRequest) request;

        if (client.getInfo("userName") == null)
            return new GetExecutedExamResponse(UNAUTHORIZED, request);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, getExecutedExamRequest.getConcreteID(),
                session);

        if (concreteExam == null)
            return new GetExecutedExamResponse(ERROR2, request);

        for (ExecutedExam executedExam : concreteExam.getExecutedExamsList())
        {
            if (executedExam.getStudent().getSocialId().equals(getExecutedExamRequest.getStudentID()))
                return new GetExecutedExamResponse(SUCCESS, getExecutedExamRequest,
                        executedExam.getLightExecutedExam());
        }

        return new GetExecutedExamResponse(ERROR3, request);
    }
}
