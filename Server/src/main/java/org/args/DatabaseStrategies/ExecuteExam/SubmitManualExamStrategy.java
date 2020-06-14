package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.SubmitExamRequest;
import DatabaseAccess.Requests.ExecuteExam.SubmitManualExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.SubmitExamResponse;
import DatabaseAccess.Responses.ExecuteExam.SubmitManualExamResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.ExecutedExam;
import org.args.Entities.Student;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

public class SubmitManualExamStrategy extends DatabaseStrategy implements IExamInProgress {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {
        SubmitManualExamRequest request1 = (SubmitManualExamRequest) request;

        if (client.getInfo("userName") == null)
            return new SubmitExamResponse(UNAUTHORIZED, request);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getExamID(), session);
        Student student = (Student) getUser((String) client.getInfo("userName"), session);
        ExecutedExam executedExam = getTypeById(ExecutedExam.class,
                String.valueOf(student.getCurrentlyExecutedID()), session);
        if (concreteExam == null || executedExam == null || concreteExam != executedExam.getConcreteExam())
            return new SubmitExamResponse(ERROR2, request);

        byte[] file = ((SubmitManualExamRequest) request).getExamFile();
        executedExam.setFileBytes(file);
        executedExam.setComputerized(false);
        executedExam.setSubmitted(true);

        session.saveOrUpdate(executedExam);
        session.flush();
        return new SubmitManualExamResponse(0,request1);
    }

    @Override
    public void handle(DatabaseRequest request, DatabaseResponse response, Map<Integer, ExamManager> examManagers, ConnectionToClient client, Session session) {
        SubmitManualExamRequest request1 = (SubmitManualExamRequest) request;

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getExamID(), session);
        ExamManager manager = examManagers.get(concreteExam.getId());
        manager.getStudents().remove((String) client.getInfo("userName"), client);
    }
}
