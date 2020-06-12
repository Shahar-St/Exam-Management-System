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
//TODO
public class EvaluateExamStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        EvaluateExamRequest evaluateExamStrategy = (EvaluateExamRequest)request;

        if (client.getInfo("userName") == null)
            return new EvaluateExamResponse(UNAUTHORIZED, evaluateExamStrategy);

        ExecutedExam executedExam = getTypeById(ExecutedExam.class, evaluateExamStrategy.getExam().getExecutedID(),
                                    session);

        if(executedExam == null)
            return new EvaluateExamResponse(ERROR2,evaluateExamStrategy);

        executedExam.setGrade(evaluateExamStrategy.getExam().getGrade());
        executedExam.setCommentsAfterCheck(evaluateExamStrategy.getExam().getCommentsAfterCheck());
        executedExam.setReasonsForChangeGrade(evaluateExamStrategy.getExam().getReasonsForChangeGrade());
        executedExam.setChecked(evaluateExamStrategy.getExam().isChecked());
        session.saveOrUpdate(executedExam);
        session.flush();

        return new EvaluateExamResponse(SUCCESS,evaluateExamStrategy);
    }
}
