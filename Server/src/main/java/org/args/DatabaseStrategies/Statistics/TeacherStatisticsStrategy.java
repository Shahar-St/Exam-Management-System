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

public class TeacherStatisticsStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        TeacherStatisticsRequest teacherStatisticsRequest = (TeacherStatisticsRequest)request;

        if (client.getInfo("userName") == null)
            return new TeacherStatisticsResponse(UNAUTHORIZED, teacherStatisticsRequest, null);

        HashMap<String, Double> map = new HashMap<>();

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, teacherStatisticsRequest.getConcreteExamID(),
                session);

        for (ExecutedExam executedExam : concreteExam.getExecutedExamsList())
        {
            if(executedExam.isChecked())
                map.put(executedExam.getStudent().getSocialId(), executedExam.getGrade());
        }

        return new TeacherStatisticsResponse(SUCCESS, teacherStatisticsRequest, map);

    }
}
