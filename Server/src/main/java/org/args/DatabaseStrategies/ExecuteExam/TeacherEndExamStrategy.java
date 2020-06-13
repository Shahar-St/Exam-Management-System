package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.TeacherEndExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.TeacherEndExamResponse;
import Notifiers.ExamEndedNotifier;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.ExecutedExam;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeacherEndExamStrategy extends DatabaseStrategy implements IExamInProgress {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {
        TeacherEndExamRequest request1 = (TeacherEndExamRequest)request;
        if (client.getInfo("userName") == null)
            return new TeacherEndExamResponse(UNAUTHORIZED, request1);
        return new TeacherEndExamResponse(0,request1);
    }

    @Override
    public void handle(DatabaseRequest request, DatabaseResponse response, Map<Integer, ExamManager> examManagers, ConnectionToClient client, Session session) {
        TeacherEndExamRequest request1 = (TeacherEndExamRequest)request;
        ConcreteExam concreteExam = getTypeById(ConcreteExam.class,request1.getConcreteExamId(),session);
        List<ExecutedExam> executedExamList = concreteExam.getExecutedExamsList();
        List<String> studentsList = new ArrayList<>();
        for(ExecutedExam executedExam: executedExamList){
            studentsList.add(executedExam.getStudent().getUserName());
        }
        ExamEndedNotifier notifier = new ExamEndedNotifier();
        ExamManager manager = examManagers.get(Integer.parseInt(request1.getConcreteExamId()));
        manager.notifyAboutExamEnd(notifier,studentsList);

    }
}
