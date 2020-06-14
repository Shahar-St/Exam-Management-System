package org.args.DatabaseStrategies.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ReviewExam.EvaluateExamRequest;
import DatabaseAccess.Requests.Statistics.GetAllPastExamsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Questions.AllQuestionsResponse;
import DatabaseAccess.Responses.ReviewExam.EvaluateExamResponse;
import DatabaseAccess.Responses.Statistics.GetAllPastExamsResponse;
import Util.Pair;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.*;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class GetAllPastExamsStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        GetAllPastExamsRequest getAllPastExamsRequest = (GetAllPastExamsRequest)request;

        if (client.getInfo("userName") == null)
            return new GetAllPastExamsResponse(UNAUTHORIZED, getAllPastExamsRequest, null);

        HashMap<String, Pair<String, Double>> map = new HashMap<>();

        Student student = (Student) getUser((String) client.getInfo("userName"), session);

        for(ExecutedExam executedExam : student.getExecutedExamsList())
        {
            if(executedExam.isChecked() && executedExam.isComputerized())
            {
                if (executedExam.getConcreteExam().getExam().getCourse().getId().equals(getAllPastExamsRequest.getCourseId()))
                    map.put(String.valueOf(executedExam.getId()),
                            new Pair<>(executedExam.getConcreteExam().getExam().getTitle(), executedExam.getGrade()));
            }
        }

        return new GetAllPastExamsResponse(SUCCESS, request, map);
    }
}
