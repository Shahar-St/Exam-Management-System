package org.args.DatabaseStrategies.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Statistics.GetAllPastExamsRequest;
import DatabaseAccess.Requests.Statistics.TeacherStatisticsConcreteRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Statistics.GetAllPastExamsResponse;
import DatabaseAccess.Responses.Statistics.TeacherStatisticsConcreteResponse;
import Util.Pair;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.ExecutedExam;
import org.args.Entities.Student;
import org.args.Entities.Teacher;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;

public class TeacherStatisticsConcreteStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {

        TeacherStatisticsConcreteRequest teacherStatisticsConcreteRequest = (TeacherStatisticsConcreteRequest)request;

        if (client.getInfo("userName") == null)
            return new TeacherStatisticsConcreteResponse(UNAUTHORIZED, teacherStatisticsConcreteRequest, null);

        HashMap<String, Pair<String, Double>> map = new HashMap<>();

        Teacher teacher = (Teacher) getUser((String) client.getInfo("userName"), session);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, teacherStatisticsConcreteRequest.getConcreteId(),
                session);

        for (ExecutedExam executedExam: concreteExam.getExecutedExamsList())
        {
            if(executedExam.isChecked())
            map.put(String.valueOf(executedExam.getId()),
                    new Pair<>(executedExam.getConcreteExam().getExam().getTitle(), executedExam.getGrade()));
        }

        return new TeacherStatisticsConcreteResponse(SUCCESS, request, map);
    }
}
