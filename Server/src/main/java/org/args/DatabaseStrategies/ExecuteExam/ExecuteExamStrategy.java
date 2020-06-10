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

public class ExecuteExamStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client,
                                   Session session, List<String> loggedInUsers) {

        ExecuteExamRequest request1 = (ExecuteExamRequest) request;

        if (client.getInfo("userName") == null)
            return new ExecuteExamResponse(UNAUTHORIZED, request);

        Exam exam = getTypeById(Exam.class, request1.getExamID(), session);
        Course course = exam.getCourse();
        List<Student> students = course.getStudentsList();

        if (exam == null)
            return new ExecuteExamResponse(ERROR2, request);

        Teacher teacher = (Teacher) getUser((String) client.getInfo("userName"), session);
        ConcreteExam concreteExam = new ConcreteExam(exam, teacher, request1.getExamCode());
        session.save(concreteExam);

        for (int i = 0; i < students.size(); i++)
        {
            ExecutedExam executedExam = new ExecutedExam(concreteExam, students.get(i),"", null, "");
            students.get(i).setIdExecutedExamCurrent(executedExam.getId());

            session.save(concreteExam);
        }

        session.flush();

        return new ExecuteExamResponse(SUCCESS, request);
    }
}
