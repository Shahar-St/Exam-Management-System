package org.args.DatabaseStrategies.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Statistics.GetAllPastExamsRequest;
import DatabaseAccess.Requests.Statistics.StudentStatisticsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Statistics.GetAllPastExamsResponse;
import DatabaseAccess.Responses.Statistics.StudentStatisticsResponse;
import com.sun.net.httpserver.Authenticator;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ExecutedExam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

public class StudentStatisticsStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        StudentStatisticsRequest studentStatisticsRequest = (StudentStatisticsRequest)request;

        if (client.getInfo("userName") == null)
            return new StudentStatisticsResponse(UNAUTHORIZED, studentStatisticsRequest, null);

        ExecutedExam executedExam = getTypeById(ExecutedExam.class, studentStatisticsRequest.getExecutedExamId(),
                                    session);

        return new StudentStatisticsResponse(SUCCESS, studentStatisticsRequest, executedExam.getLightExecutedExam());

    }
}
