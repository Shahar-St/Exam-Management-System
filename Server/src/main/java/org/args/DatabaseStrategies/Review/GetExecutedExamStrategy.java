package org.args.DatabaseStrategies.Review;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ReviewExam.GetExecutedExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ReviewExam.GetExecutedExamResponse;
import LightEntities.LightExecutedExam;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.ExecutedExam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

public class GetExecutedExamStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        GetExecutedExamRequest getExecutedExamRequest = (GetExecutedExamRequest) request;

        if (client.getInfo("userName") == null)
            return new GetExecutedExamResponse(UNAUTHORIZED, getExecutedExamRequest, null);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, getExecutedExamRequest.getConcreteID(), session);

        List<ExecutedExam> executedExamsList = concreteExam.getExecutedExamsList();
        LightExecutedExam lightExecutedExam = null;
        boolean flag = true;
        for (int i = 0; i < executedExamsList.size() && flag; i++)
        {
            if (executedExamsList.get(i).getStudent().getSocialId().equals(getExecutedExamRequest.getStudentID()))
            {
                lightExecutedExam = executedExamsList.get(i).getLightExecutedExam();
                flag = false;
            }
        }

        return new GetExecutedExamResponse(SUCCESS, getExecutedExamRequest, lightExecutedExam);
        }
    }
