package org.args.DatabaseStrategies.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Exams.ViewExamRequest;
import DatabaseAccess.Requests.Questions.viewExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Exams.ViewExamResponse;
import DatabaseAccess.Responses.Questions.QuestionResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Question;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ViewExamStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {

        ViewExamRequest viewExamRequest = (ViewExamRequest) request;

        if (client.getInfo("userName") == null)
            return new ViewExamResponse(UNAUTHORIZED, viewExamRequest);

        Question question = getTypeById(Question.class, viewExamRequest.getQuestionID(), session);

        if (question == null)
            return new QuestionResponse(NOT_FOUND, viewExamRequest);

        List<String> answers = new ArrayList<>(question.getAnswersArray());
        return new QuestionResponse(SUCCESS, request, question.getQuestionContent(),
                answers, question.getCorrectAnswer(), question.getAuthor().getFullName(),
                question.getLastModified());
    }
}
