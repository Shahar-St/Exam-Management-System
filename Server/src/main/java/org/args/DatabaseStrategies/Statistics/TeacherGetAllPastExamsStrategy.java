package org.args.DatabaseStrategies.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Statistics.TeacherGetAllPastExamsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Statistics.TeacherGetAllPastExamsResponse;
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

public class TeacherGetAllPastExamsStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        TeacherGetAllPastExamsRequest request1 = (TeacherGetAllPastExamsRequest) request;

        if (client.getInfo("userName") == null)
            return new TeacherGetAllPastExamsResponse(UNAUTHORIZED, request1, null);

        Course course = getTypeById(Course.class, request1.getCourseId(), session);
        User user = getUser((String) client.getInfo("userName"), session);

        HashMap<String, Pair<LocalDateTime, String>> map = new HashMap<>();
        List<Exam> exams = course.getExamsList();

        if (user instanceof Teacher)
        {
            Teacher teacher = (Teacher) user;

            for (Exam exam : exams)
                if (exam.getAuthor().getUserName().equals(teacher.getUserName()))
                    getExamsInMap(map, exam);
        }
        else    // user is dean
            for (Exam exam : exams)
                getExamsInMap(map, exam);


        return new TeacherGetAllPastExamsResponse(SUCCESS, request, map);
    }

    private void getExamsInMap(HashMap<String, Pair<LocalDateTime, String>> map, Exam exam) {

        boolean needToCheck;
        for (ConcreteExam concreteExam : exam.getConcreteExamsList())
        {
            needToCheck = true;
            for (int i = 0; i < concreteExam.getExecutedExamsList().size() && needToCheck; i++)
                if (concreteExam.getExecutedExamsList().get(i).isChecked() &&
                        concreteExam.getExecutedExamsList().get(i).isComputerized())
                {
                    map.put(String.valueOf(concreteExam.getId()),
                            new Pair<>(concreteExam.getExamForExecutionInitDate(),
                                    concreteExam.getExam().getTitle()));
                    needToCheck = false;
                }
        }
    }
}
