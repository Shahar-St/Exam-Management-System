package org.args.DatabaseStrategies.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Statistics.GetAllPastExamsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Statistics.GetAllPastExamsResponse;
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

public class GetAllPastExamsStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        GetAllPastExamsRequest getAllPastExamsRequest = (GetAllPastExamsRequest) request;

        if (client.getInfo("userName") == null)
            return new GetAllPastExamsResponse(UNAUTHORIZED, getAllPastExamsRequest);

        HashMap<String, Pair<String, Double>> map1 = new HashMap<>();
        HashMap<String, LocalDateTime> map2 = new HashMap<>();

        Student student = (Student) getUser((String) client.getInfo("userName"), session);

        for (ExecutedExam executedExam : student.getExecutedExamsList())
        {
            pastExamsLock.lock();
            if (executedExam.isChecked() && executedExam.isComputerized() &&
                    executedExam.getConcreteExam().getExam().getCourse()
                            .getId().equals(getAllPastExamsRequest.getCourseId()))
            {
                map1.put(String.valueOf(executedExam.getConcreteExam().getId()),
                        new Pair<>(executedExam.getConcreteExam().getExam().getTitle(), executedExam.getGrade()));
                map2.put(String.valueOf(executedExam.getConcreteExam().getId()),
                        executedExam.getConcreteExam().getExamForExecutionInitDate());
            }
            pastExamsLock.unlock();
        }

        return new GetAllPastExamsResponse(SUCCESS, request, map1, map2);
    }
}
