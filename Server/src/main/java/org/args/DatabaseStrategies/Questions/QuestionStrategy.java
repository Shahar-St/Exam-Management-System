package org.args.DatabaseStrategies.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Questions.QuestionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Questions.QuestionResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Question;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - Question wasn't found
 */
public class QuestionStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        QuestionRequest questionRequest = (QuestionRequest) request;

        if (client.getInfo("userName") == null)
            return new QuestionResponse(UNAUTHORIZED, questionRequest);

        Question question = getTypeById(Question.class, questionRequest.getQuestionID(), session);

        if (question == null)
            return new QuestionResponse(ERROR2, questionRequest);

        List<String> answers = new ArrayList<>(question.getAnswersArray());
        return new QuestionResponse(SUCCESS, request, question.getQuestionContent(),
                answers, question.getCorrectAnswer(), question.getAuthor().getUserName(),
                question.getLastModified());
    }
}
