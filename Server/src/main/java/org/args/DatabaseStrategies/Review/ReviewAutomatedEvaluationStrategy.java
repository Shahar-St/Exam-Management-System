package org.args.DatabaseStrategies.Review;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ReviewExam.ReviewAutomatedEvaluationRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ReviewExam.PendingExamResponse;
import DatabaseAccess.Responses.ReviewExam.ReviewAutomatedEvaluationResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ExecutedExam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
//TODO
public class ReviewAutomatedEvaluationStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {
        ReviewAutomatedEvaluationRequest request1 = (ReviewAutomatedEvaluationRequest)request;
        if (client.getInfo("userName") == null)
            return new ReviewAutomatedEvaluationResponse(UNAUTHORIZED,request1);
        ExecutedExam executedExam = getTypeById(ExecutedExam.class,request1.getExecutedExamId(),session);
        if(executedExam==null)
            return new ReviewAutomatedEvaluationResponse(ERROR2,request1);
        executedExam.setGrade(request1.getGrade());
        executedExam.setCommentsAfterCheck(request1.getComments());
        executedExam.setReasonsForChangeGrade(request1.getGradeChangeReason());
        session.save(executedExam);
        session.flush();
        return new ReviewAutomatedEvaluationResponse(0,request1);
    }
}
