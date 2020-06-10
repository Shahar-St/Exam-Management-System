package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.ExecuteExamRequest;
import DatabaseAccess.Requests.ExecuteExam.TakeExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.ExecuteExamResponse;
import DatabaseAccess.Responses.ExecuteExam.TakeExamResponse;
import LightEntities.LightExam;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Course;
import org.args.Entities.Exam;
import org.args.Entities.ExecutedExam;
import org.args.Entities.Student;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

public class TakeExamStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        TakeExamRequest takeExamRequest = (TakeExamRequest) request;

        Student student = (Student) getUser((String) client.getInfo("userName"), session);
        ExecutedExam executedExam = getTypeById(ExecutedExam.class, String.valueOf(student.getIdExecutedExamCurrent())
                                    , session);

        if(takeExamRequest.getSocialId() != 0)
            executedExam.setComputerized(true);

        Exam exam = executedExam.getConcreteExam().getExam();
        LightExam lightExam = exam.createLightExam();

        if (client.getInfo("userName") == null)
            return new TakeExamResponse(UNAUTHORIZED, takeExamRequest, lightExam);

        return new TakeExamResponse(SUCCESS, takeExamRequest, lightExam);

    }
}
