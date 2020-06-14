package org.args.DatabaseStrategies.Review;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ReviewExam.EvaluateExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ReviewExam.EvaluateExamResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ExecutedExam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class EvaluateExamStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        EvaluateExamRequest request1 = (EvaluateExamRequest) request;

        if (client.getInfo("userName") == null)
            return new EvaluateExamResponse(UNAUTHORIZED, request1);

        ExecutedExam executedExam = getTypeById(ExecutedExam.class, request1.getExam().getExecutedID(),
                session);

        if (executedExam == null)
            return new EvaluateExamResponse(ERROR2, request1);

        executedExam.setGrade(request1.getExam().getGrade());
        executedExam.setCommentsAfterCheck(request1.getExam().getCommentsAfterCheck());
        executedExam.setReasonsForChangeGrade(request1.getExam().getReasonsForChangeGrade());
        executedExam.setChecked(request1.getExam().isChecked());
        session.saveOrUpdate(executedExam);
        session.flush();

        return new EvaluateExamResponse(SUCCESS, request1);
    }
}
