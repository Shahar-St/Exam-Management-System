package org.args.DatabaseStrategies.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Statistics.GetAllPastExamsRequest;
import DatabaseAccess.Requests.Statistics.TeacherGetAllPastExamsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Statistics.GetAllPastExamsResponse;
import DatabaseAccess.Responses.Statistics.TeacherGetAllPastExamsResponse;
import Util.Pair;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.*;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class TeacherGetAllPastExamsStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        TeacherGetAllPastExamsRequest teacherGetAllPastExamsRequest = (TeacherGetAllPastExamsRequest)request;

        if (client.getInfo("userName") == null)
            return new TeacherGetAllPastExamsResponse(UNAUTHORIZED, teacherGetAllPastExamsRequest, null);

        HashMap<String, Pair<LocalDateTime, String>> map = new HashMap<>();

        User user = getUser((String) client.getInfo("userName"), session);
        boolean needToCheck;
        if( user instanceof Teacher)
        {
            Teacher teacher = (Teacher) user;
            for(Exam exam : teacher.getExamsList())
            {
                for(ConcreteExam concreteExam : teacher.getConcreteExamsList())
                {
                    needToCheck = true;
                    for (int i = 0; i < concreteExam.getExecutedExamsList().size() && needToCheck; i++)
                        if(concreteExam.getExecutedExamsList().get(i).isChecked())
                        {
                            map.put(String.valueOf(concreteExam.getId()),
                                    new Pair<>(concreteExam.getExamForExecutionInitDate(),
                                            concreteExam.getExam().getTitle()));
                            needToCheck = false;
                        }
                }
            }
        }
        else
        {
            Dean dean = (Dean) user;
            List<Exam> exams = getAllOfType(session, Exam.class);
            for(Exam exam : exams)
            {
                for(ConcreteExam concreteExam : exam.getConcreteExamsList())
                {
                    needToCheck = true;
                    for (int i = 0; i < concreteExam.getExecutedExamsList().size() && needToCheck; i++)
                        if(concreteExam.getExecutedExamsList().get(i).isChecked())
                        {
                            map.put(String.valueOf(concreteExam.getId()),
                                    new Pair<>(concreteExam.getExamForExecutionInitDate(),
                                            concreteExam.getExam().getTitle()));
                            needToCheck = false;
                        }
                }
            }
        }

        return new TeacherGetAllPastExamsResponse(SUCCESS, request, map);

    }
}
