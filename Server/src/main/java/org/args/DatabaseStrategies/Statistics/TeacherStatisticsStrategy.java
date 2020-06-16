package org.args.DatabaseStrategies.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Statistics.TeacherStatisticsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Statistics.TeacherStatisticsResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.ExecutedExam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class TeacherStatisticsStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        TeacherStatisticsRequest request1 = (TeacherStatisticsRequest) request;

        if (client.getInfo("userName") == null)
            return new TeacherStatisticsResponse(UNAUTHORIZED, request1);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getConcreteExamID(),
                session);

        if (concreteExam == null)
            return new TeacherStatisticsResponse(ERROR2, request);

        HashMap<String, Double> map = new HashMap<>();
        for (ExecutedExam executedExam : concreteExam.getExecutedExamsList())
            map.put(executedExam.getStudent().getSocialId(), executedExam.getGrade());

        return new TeacherStatisticsResponse(SUCCESS, request1, map);
    }
}
