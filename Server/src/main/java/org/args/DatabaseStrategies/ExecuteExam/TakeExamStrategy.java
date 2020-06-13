package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.TakeExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.TakeExamResponse;
import LightEntities.LightExam;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.ExecutedExam;
import org.args.Entities.Student;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - the student don't have any exam to take
 * 3 - wrong exam code
 * 4 - wrong ID
 */

public class TakeExamStrategy extends DatabaseStrategy implements IExamInProgress {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        TakeExamRequest takeExamRequest = (TakeExamRequest) request;
        if (client.getInfo("userName") == null)
            return new TakeExamResponse(UNAUTHORIZED, takeExamRequest);

        Student student = (Student) getUser((String) client.getInfo("userName"), session);
        ExecutedExam executedExam = getTypeById(ExecutedExam.class, String.valueOf(student.getCurrentlyExecutedID())
                , session);

        if (executedExam == null)
            return new TakeExamResponse(ERROR2, takeExamRequest);

        if (!student.getSocialId().equals(String.valueOf(takeExamRequest.getSocialId()))&& takeExamRequest.isComputerized())
            return new TakeExamResponse(ERROR4, takeExamRequest);

        if (!executedExam.getConcreteExam().getExamCode().equals(takeExamRequest.getExamCode()))
            return new TakeExamResponse(ERROR3, takeExamRequest);
        // set student currently executed id to concrete id
        student.setCurrentlyExecutedID(executedExam.getConcreteExam().getId());
        executedExam.setComputerized(takeExamRequest.isComputerized());
        LightExam lightExam = executedExam.getConcreteExam().createLightExam();
        TakeExamResponse response = new TakeExamResponse(SUCCESS, takeExamRequest, lightExam);
        response.setInitExamForExecutionDate(executedExam.getConcreteExam().getInitExamForExecutionDate());
        return response;
    }

    @Override
    public void handle(DatabaseRequest request, DatabaseResponse response, Map<Integer, ExamManager> examManagers,
                       ConnectionToClient client, Session session) {

        TakeExamResponse response1 = (TakeExamResponse) response;
        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, response1.getLightExam().getId(), session);
        ExamManager manager = examManagers.get(concreteExam.getId());
        manager.getStudents().put((String) client.getInfo("userName"), client);
    }
}
