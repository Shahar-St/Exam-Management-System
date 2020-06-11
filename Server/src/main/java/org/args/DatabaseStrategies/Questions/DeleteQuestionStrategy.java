package org.args.DatabaseStrategies.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Questions.DeleteQuestionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Questions.DeleteQuestionResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Question;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - Question wasn't found
 * 3 - user isn't the question's author
 * 4 - question is part of an exam
 */

public class DeleteQuestionStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   Map<String, ConnectionToClient> loggedInUsers) {

        DeleteQuestionRequest request1 = (DeleteQuestionRequest) request;
        if (client.getInfo("userName") == null)
            return new DeleteQuestionResponse(UNAUTHORIZED, request);

        Question question = getTypeById(Question.class, request1.getQuestionID(), session);

        if (question == null)
            return new DeleteQuestionResponse(ERROR2, request);

        if (question.getAuthor() != getUser((String) client.getInfo("userName"), session))
            return new DeleteQuestionResponse(ERROR3, request);

        if (!question.getContainedInExams().isEmpty())
            return new DeleteQuestionResponse(ERROR4, request);

        question.getCourse().getAvailableQuestionCodes().add(Integer.parseInt(question.getId().substring(2)));
        session.remove(question);
        session.flush();
        return new DeleteQuestionResponse(SUCCESS, request);
    }
}
