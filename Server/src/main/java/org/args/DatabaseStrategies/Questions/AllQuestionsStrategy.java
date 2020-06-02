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

public class AllQuestionsStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        AllQuestionsRequest allQuestionsRequest = (AllQuestionsRequest) request;

        HashMap<String, Pair<LocalDateTime, String>> map = new HashMap<>();

        if (client.getInfo("userName") == null)
            return new AllQuestionsResponse(UNAUTHORIZED, request);

        User user = getUser((String) client.getInfo("userName"), session);

        if (user == null)
            return new AllQuestionsResponse(NOT_FOUND, request);


        List<Question> questionList = new ArrayList<>();
        if (user instanceof Teacher)
        {
            Teacher teacher = (Teacher) user;
            for (Course tCourse : teacher.getCoursesList())
            {
                if (tCourse.getName().equals(allQuestionsRequest.getCourseID()))
                    questionList.addAll(tCourse.getQuestionsList());
            }
        }
        else if (user instanceof Dean)  // user is dean
            questionList = getAllOfType(session, Question.class);
        else if (user instanceof Student)
        {

        }


        for (Question question : questionList)
            map.put(question.getId(),
                    new Pair<>(question.getLastModified(), question.getQuestionContent()));

        return new AllQuestionsResponse(SUCCESS, request, map);
    }
}
