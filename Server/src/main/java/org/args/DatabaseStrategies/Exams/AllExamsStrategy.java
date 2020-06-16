package org.args.DatabaseStrategies.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Exams.AllExamsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Exams.AllExamsResponse;
import Util.Pair;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.*;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */

public class AllExamsStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        AllExamsRequest allExamsRequest = (AllExamsRequest) request;

        HashMap<String, Pair<LocalDateTime, String>> map = new HashMap<>();

        if (client.getInfo("userName") == null)
            return new AllExamsResponse(UNAUTHORIZED, request);

        questionsAndExamsLock.lock();
        Course course = getTypeById(Course.class, allExamsRequest.getCourseID(), session);

        for (Exam exam : course.getExamsList())
            map.put(exam.getId(), new Pair<>(exam.getLastModified(), exam.getTitle()));

        questionsAndExamsLock.unlock();
        return new AllExamsResponse(SUCCESS, request, map);
    }
}
