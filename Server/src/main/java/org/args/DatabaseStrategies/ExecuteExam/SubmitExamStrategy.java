package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.SubmitExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.SubmitExamResponse;
import Notifiers.ExamEndedNotifier;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.*;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class SubmitExamStrategy extends DatabaseStrategy implements IExamInProgress {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        SubmitExamRequest request1 = (SubmitExamRequest) request;

        if (client.getInfo("userName") == null)
            return new SubmitExamResponse(UNAUTHORIZED, request);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getExamID(), session);
        Student student = (Student) getUser((String) client.getInfo("userName"), session);
        ExecutedExam executedExam = getTypeById(ExecutedExam.class,
                String.valueOf(student.getCurrentlyExecutedID()), session);

        if (concreteExam == null || executedExam == null || concreteExam != executedExam.getConcreteExam())
            return new SubmitExamResponse(ERROR2, request);

        List<Integer> answersList = new ArrayList<>(request1.getAnswersList());
        executedExam.setAnswersByStudent(answersList);
        executedExam.setComputerized(true);
        executedExam.setSubmitted(true);
        student.getExecutedExamsList().add(executedExam);
        student.setCurrentlyExecutedID(-1);

        if (request1.isFinishedOnTime())
            concreteExam.addFinishedOnTime();
        else
            concreteExam.addUnfinishedOnTime();

        session.saveOrUpdate(executedExam);
        session.flush();

        return new SubmitExamResponse(SUCCESS, request1);
    }

    @Override
    public void handle(DatabaseRequest request, DatabaseResponse response, Map<Integer, ExamManager> examManagers, ConnectionToClient client, Session session) {

        SubmitExamRequest request1 = (SubmitExamRequest) request;

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getExamID(), session);
        ExamManager manager = examManagers.get(concreteExam.getId());
        manager.getStudents().remove((String) client.getInfo("userName"), client);
        if(concreteExam.getExecutedExamsList().size() == concreteExam.getFinishedOnTime()){
            List<String> studentsList = new ArrayList<>();
            for(ExecutedExam executedExam: concreteExam.getExecutedExamsList()){
                studentsList.add(executedExam.getStudent().getUserName());
            }
            manager.notifyAboutExamEnd(new ExamEndedNotifier(),studentsList);
        }

    }
}
