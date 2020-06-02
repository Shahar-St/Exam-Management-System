package org.args.DatabaseStrategies.QuestionStrategy;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Questions.EditQuestionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Questions.EditQuestionResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Question;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

public class EditQuestionStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {
        EditQuestionRequest editRequest = (EditQuestionRequest) request;
        if (client.getInfo("userName") == null)
            return new EditQuestionResponse(UNAUTHORIZED, request);

        Question question = getQuestion(editRequest.getQuestionID(), session);

        if (question == null)
            return new EditQuestionResponse(NOT_FOUND, request);

        if (question.getAuthor() != getUser((String) client.getInfo("userName"), session))
            return new EditQuestionResponse(NO_ACCESS, request);

        question.setQuestionContent(editRequest.getNewDescription());
        question.setAnswersArray(editRequest.getNewAnswers());
        question.setCorrectAnswer(editRequest.getCorrectAnswer());
        question.setLastModified();
        session.update(question);
        session.flush();

        return new EditQuestionResponse(SUCCESS, request);
    }
}
