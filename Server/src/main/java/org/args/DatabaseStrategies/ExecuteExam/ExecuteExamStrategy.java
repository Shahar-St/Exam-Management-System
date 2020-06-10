package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.ExecuteExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.ExecuteExamResponse;

import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.*;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */
//TODO
public class ExecuteExamStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client,
                                   Session session, List<String> loggedInUsers) {

        ExecuteExamRequest request1 = (ExecuteExamRequest) request;

        if (client.getInfo("userName") == null)
            return new ExecuteExamResponse(UNAUTHORIZED, request);

        Exam exam = getTypeById(Exam.class, request1.getExamID(), session);
        List<Student> students = exam.getCourse().getStudentsList();

        Teacher teacher = (Teacher) getUser((String) client.getInfo("userName"), session);
        ConcreteExam concreteExam = new ConcreteExam(exam, teacher, request1.getExamCode());
        session.saveOrUpdate(concreteExam);

        for (Student student : students)
        {
            ExecutedExam executedExam = new ExecutedExam(concreteExam, student, "",
                    null, "");
            session.save(executedExam);
            student.setIdExecutedExamCurrent(executedExam.getId());
            session.update(executedExam);
        }
        session.flush();

        return new ExecuteExamResponse(SUCCESS, request);
    }
}
