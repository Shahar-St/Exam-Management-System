package org.args.DatabaseStrategies.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Questions.AllQuestionsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Questions.AllQuestionsResponse;
import Util.Pair;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.*;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * status dictionary:
 *  0 - success
 *  1 - unauthorized access - user isn't logged in
 */

public class AllQuestionsStrategy extends DatabaseStrategy {



    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        AllQuestionsRequest allQuestionsRequest = (AllQuestionsRequest) request;

        HashMap<String, Pair<LocalDateTime, String>> map = new HashMap<>();

        if (client.getInfo("userName") == null)
            return new AllQuestionsResponse(UNAUTHORIZED, request);

        Course course = getTypeById(Course.class, allQuestionsRequest.getCourseID(), session);

        for (Question question : course.getQuestionsList())
            map.put(question.getId(),
                    new Pair<>(question.getLastModified(), question.getQuestionContent()));

        return new AllQuestionsResponse(SUCCESS, request, map);
    }
}
