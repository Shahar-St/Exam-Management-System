package org.args.DatabaseStrategies.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Exams.ViewExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Exams.ViewExamResponse;
import LightEntities.LightExam;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Exam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

public class ViewExamStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {

        ViewExamRequest viewExamRequest = (ViewExamRequest) request;

        if (client.getInfo("userName") == null)
            return new ViewExamResponse(UNAUTHORIZED, viewExamRequest);

        Exam exam = getTypeById(Exam.class, viewExamRequest.getExamId(), session);

        if (exam == null)
            return new ViewExamResponse(ERROR2, viewExamRequest);

        //LightExam lightExam = null;
//        int res = SUCCESS;
//        try
//        {
        LightExam lightExam = exam.createLightExam();
//        } catch (CloneNotSupportedException e)
//        {
//            res = NO_ACCESS;
//            e.printStackTrace();
//        }

        return new ViewExamResponse(SUCCESS, request, lightExam);
    }
}
