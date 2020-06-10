package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.SubmitExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.SubmitExamResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.*;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
//TODO
public class SubmitExamStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {

        SubmitExamRequest request1 = (SubmitExamRequest)request;
        if (client.getInfo("userName") == null)
            return new SubmitExamResponse(ERROR2, request);
        Student student = getTypeById(Student.class,getUser((String) client.getInfo("userName"),session).getSocialId(),session);
        Exam exam = getTypeById(Exam.class,request1.getExamID(),session);
        ConcreteExam concreteExam = exam.getConcreteExamsList().get(exam.getConcreteExamsList().size()-1);
        ExecutedExam executedExam = new ExecutedExam(concreteExam,student,"",request1.getAnswersList(),"");
        session.save(executedExam);
        session.flush();
        concreteExam.addExecutedExam(executedExam);
        if(student == null)
            return new SubmitExamResponse(ERROR2,request1);
        return new SubmitExamResponse(0,request1);
    }
}
